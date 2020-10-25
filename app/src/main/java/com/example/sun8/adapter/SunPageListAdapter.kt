package com.example.sun8.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.sun8.R
import com.example.sun8.data.room.SunUser
import kotlinx.android.synthetic.main.sun_cell.view.*


class SunPageListAdapter : PagedListAdapter<SunUser, SunPageListAdapter.SunMyViewHolder>(DIFFCALLBACK) {

    class SunMyViewHolder(var sunHolder_View : View) : RecyclerView.ViewHolder(sunHolder_View){

    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SunMyViewHolder {
        /**
         * onCreateViewHolder 作用： 加载一个视图出来，并创建一个 viewHolder，而viewHolder作为recyclerView缓存管理的对象
         * 可复用
         * 负责承载每个子项的布局
         */
        val holder = SunMyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sun_cell,parent,false))
        //println("Jessice-Sun-onCreateViewHolder----"+holder.getAdapterPosition());  // 打印一下 onCreateViewHolder 运行次数，recyclerView 可缓存
        holder.itemView.setOnClickListener {
            Bundle().apply {
                /**
                 * 点击图片后，将整一个 SunUser 传过去
                 * 详情页是一张图片
                 */
                //putParcelable("SUNA",getItem(holder.adapterPosition))
                //holder.itemView.findNavController().navigate(R.id.action_oneFragment_to_twoFragment,this)

                /**
                 * 点击图片后，将多个SunUser传递过去，也就是整个列表信息
                 * 详情页中，把 ViewPager2（滑动功能） 当作一个RecyclerView，例用ListPager适配器，滑动，查看所有图片
                 */
                putParcelableArrayList("SUNA_LIST", ArrayList(currentList)) //把整个列表内容传递过去
                putInt("SUNA_POSITION",holder.adapterPosition) //传递过去当前是第几个图片，也就是位置
                holder.itemView.findNavController().navigate(R.id.action_oneFragment_to_pagerViewFragment,this)
            }
        }
        return holder
    }
    /**
     * onBindViewHolder 为每一项赋值
     * 负责将每个子项 holder 绑定数据
     * 运行多次
     */
    override fun onBindViewHolder(holder: SunMyViewHolder, position: Int) {
        //打印出所有的图片地址
        //println("Jessice:Picture----"+getItem(position)?.webformatURL)
        //println("Jessice-Sun-onBindViewHolder————"+position);
        var imgUrl = getItem(position)?.webformatURL
        //给图片(占位符)设置一个高度
        holder.itemView.imageView3.layoutParams.height = getItem(position)?.webformatHeight!!
        /**
         * 使用Glide 加载图片赋值给 holder.itemView.imageView3
         */
        Glide.with(holder.itemView)
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .listener(object : RequestListener<Drawable>{  //说明：这里的 Drawable 固定就好，一个类
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    /**
                     * 图片加载失败时执行这里
                     */
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    /**
                     * 图片加载成功时执行这里
                     */
                    return false.also {
                        //shimmerLayout?.stopShimmerAnimation()
                    }
                }
            })
            .into(holder.itemView.imageView3)
    }
    object DIFFCALLBACK: DiffUtil.ItemCallback<SunUser>(){
        override fun areItemsTheSame(oldItem: SunUser, newItem: SunUser): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SunUser, newItem: SunUser): Boolean {
            return oldItem.id == newItem.id
        }
    }
}