package com.example.sun8.DataSource

import android.content.Context
import androidx.paging.DataSource
import com.example.sun8.data.room.SunUser

/**
 * 创建一个 DataSourceFactory ，返回一个 DataSource 实例
 */
class SunDataSourceFactory(private val context:Context): DataSource.Factory<Int, SunUser>() {
    //创建一个DataSource对象，并返回
    override fun create(): DataSource<Int, SunUser> {
        return SunDataSource(context)
    }
}