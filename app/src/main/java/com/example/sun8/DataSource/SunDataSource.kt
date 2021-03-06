package com.example.sun8.DataSource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.paging.PageKeyedDataSource
import com.example.sun8.data.remote.Animal
import com.example.sun8.data.room.SunUser
import com.example.sun8.data.room.SunUserDataBase
import com.github.kittinunf.fuel.Fuel

/**
 * PageKeyedDataSource 两个参数，（第一个参数：页码 第二个参数，数据类型）
 * Hit 不是SunUser 是 animals.hits 所对用的类型
 */
//枚举类
enum class NetworkStatus{
    LOADING,
    FAILED,
    COMPLETED
}
class SunDataSource(private val context: Context) : PageKeyedDataSource<Int, SunUser>() {
    var retry : (()->Any)? = null
    private val _networkstatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus> = _networkstatus
    /**
     * loadInitial 加载起始时，运行
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SunUser>
    ) {
        _networkstatus.postValue(NetworkStatus.LOADING)
        var xdikd = "";
        var names = arrayOf("flowers","tea","animal","plant","mountain");
        var vader = names.random()
        val httpAsync = Fuel.get("https://pixabay.com/api/?key=17946669-543fe6c4c313739ab33b63515&q="+vader+"&image_type=photo&pretty=true&per_page=10&page=1")
            .responseObject(Animal.Deserializer()) { request, response, result ->
                val(animals, err) = result   //Kotlin 写法
                //val animals = result.component1() //java写法
                if (animals != null) {
                    retry = null
                    /**
                     * 把远程数据，写入数据库
                     */
                    var xdikd = animals.hits.toList()
                    callback.onResult(xdikd,null,2)
                    var i=1
                    val dao = SunUserDataBase.getInstance(context)?.getUserDao()
                    for (cursor in animals.hits){
                        //println("Jessice:"+i+"------"+cursor.largeImageURL)
                        dao?.insertData(SunUser(i, cursor.largeImageURL,cursor.webformatURL,cursor.webformatHeight))
                        //item = item.map(SunUser(i, cursor.largeImageURL))
                        i++
                    }
                    println("Jessice:第一页")
                }else{
                    //如果加载失败，保存下状态
                    retry = {loadInitial(params, callback)}
                    _networkstatus.postValue(NetworkStatus.FAILED)
                    /**
                     * 出错情况
                     */
                    //println("Jessice:断网了")
                }
            }
    }

    /**
     * 往后一页
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SunUser>) {
        _networkstatus.postValue(NetworkStatus.LOADING)
        var xdikd = "";
        var names = arrayOf("flowers","tea","animal","plant","mountain");
        var vader = names.random()
        val httpAsync = Fuel.get("https://pixabay.com/api/?key=17946669-543fe6c4c313739ab33b63515&q="+vader+"&image_type=photo&pretty=true&per_page=10&page=${params.key}")
            .responseObject(Animal.Deserializer()) { request, response, result ->
                val(animals, err) = result   //Kotlin 写法
                //val animals = result.component1() //java写法
                if (animals != null) {
                    retry = null
                    /**
                     * 把远程数据，写入数据库
                     */
                    var xdikd = animals.hits.toList()
                    callback.onResult(xdikd,params.key+1) //下一页
                    var i=1
                    val dao = SunUserDataBase.getInstance(context)?.getUserDao()
                    for (cursor in animals.hits){
                        //println("Jessice:"+i+"------"+cursor.largeImageURL)
                        dao?.insertData(SunUser(i, cursor.largeImageURL,cursor.webformatURL,cursor.webformatHeight))
                        //item = item.map(SunUser(i, cursor.largeImageURL))
                        i++
                    }
                    println("Jessice:第${params.key}页")
                }else{
                    //保存失败后的状态
                    retry = {loadAfter(params, callback)}
                    /**
                     * 出错情况
                     */
                    _networkstatus.postValue(NetworkStatus.FAILED)
                    //println("Jessice:出错了")
                }
            }
    }

    /**
     * 往前一页，暂时用不到
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SunUser>) {
        TODO("Not yet implemented")
    }

}