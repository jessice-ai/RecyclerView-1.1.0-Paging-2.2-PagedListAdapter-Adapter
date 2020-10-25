package com.example.sun8.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.example.sun8.DataSource.SunDataSourceFactory
import com.example.sun8.data.room.SunUser
import com.example.sun8.data.room.SunUserDataBase
import com.example.sun8.repository.SunRepository


class MyViewModel(application: Application)  : AndroidViewModel(application) {
    //创建一个DataSource
    private val sunfactory = SunDataSourceFactory(application)
    val sunPageListData = sunfactory.toLiveData(30)

    /**
     * Transformations 从一个liveData 观察另一个Livedata，从一个livedata 生成另一个livedata
     * Transformations 变形
     */
    val networkStatus = Transformations.switchMap(sunfactory.sun_DataSource,{it.networkStatus})
    var xun:Int = 1
    /**
     * 下拉刷新，重新加载
     */
    fun resetquery(){
        //工厂重新生成一个DataSource对象
        sunPageListData.value?.dataSource?.invalidate()
    }
    fun sunretry(){
        //invoke 呼叫，执行
        sunfactory.sun_DataSource.value?.retry?.invoke()
    }
    /**
     * var allWords = MutableLiveData<List<SunUser>>()
     * val allWords: LiveData<List<SunUser>>?=null
     * 说明：之前用的是 LiveData 这里用的是 LiveData 中的 MutableLiveData
     * 只有使用 MutableLiveData 才能有 postValue 方法，修改 allWords 的值
     */
    //var allWords = MutableLiveData<List<SunUser>>()
    //var SunRepository: SunRepository? = null
    //val allWords: LiveData<List<SunUser>>?=null

    /**
     * 延迟初始化，一个 Repository 管理数据库操作
     * Repository 是 ViewModel 与 Room 之间的，中间层
     */
//    fun getmAllWords(){
//        SunRepository?.getremotedata()  //插入数据
//        allWords.postValue(SunRepository?.getmAllWords())  //从数据库中获取数据
//    }
//    /**
//     * 添加数据
//     */
//    public fun insert(SunUser: SunUser) {
//        SunRepository?.insert(SunUser)
//    }

    ////
//    /**
//     * 更新数据
//     */
//    fun update_first_name(id:Int, value:String){
//        SunRepository?.update_first_name(id,value)
//    }
//    /**
//     * 查询数据库中所有数据
//     */
//    fun getAllWords(): List<SunUser>? {
//        var listSun = SunRepository?.getAlldata()
//        return listSun;
//    }
//    /**
//     * 删除数据
//     */
//    fun deleteUserById(id:Int){
//        SunRepository?.deleteUserById(id)
//    }
    //var inputAge = MutableLiveData<Int>()
    /**
     * 初始化模块
     */
//    init {
//        /**
//         * 构造函数（constructor） 初始化代码块（init） 伴生对象（companion object），执行顺序
//         *  1、首先执行伴生对象（Companion object）
//         *  2、其次执行初始化代码块 （init）
//         *  3、再执行构造函数 （constructor）
//         */
//        val dao = SunUserDataBase.getInstance(application)?.getUserDao()
//        SunRepository = dao?.let { SunRepository(it) }
//
//    }
}