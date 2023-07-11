package com.example.nonono

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter(private var todoarray: MutableList<users>,private val context: Activity) :RecyclerView.Adapter<myAdapter.myViewholder>(){
    private val filterList = mutableListOf<users>()
    lateinit var myListener:onItemclickListener
    interface onItemclickListener{
        fun onItemclick(position: Int)
    }
    fun setItemclickListener(listener: onItemclickListener){
        myListener = listener
    }
    init {
      filterList.addAll(todoarray)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapter.myViewholder {
      val itemview = LayoutInflater.from(parent.context).inflate(R.layout.eachrow,parent,false)
        return myViewholder(itemview,myListener)
    }

    override fun onBindViewHolder(holder: myAdapter.myViewholder, position: Int) {
       val current = todoarray[position]
        holder.itemView.setOnClickListener {
            myListener.onItemclick(position)
        }
        holder.hTitle.text = current.tit
    }

    override fun getItemCount(): Int {
       return todoarray.size
    }
    class myViewholder(itemView: View,listener: myAdapter.onItemclickListener):RecyclerView.ViewHolder(itemView) {
        val hTitle = itemView.findViewById<TextView>(R.id.ttile)
        init{
            itemView.setOnClickListener{
                listener.onItemclick(adapterPosition)
            }
        }
    }

}








