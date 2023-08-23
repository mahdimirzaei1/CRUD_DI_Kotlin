package com.mv.crud_di.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mv.crud_di.models.Item
import com.mv.crud_di.R

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.StudentViewHolder>(){
    private var itemList: ArrayList<Item> = ArrayList()
    private var onClickItem: ((Item) -> Unit)? = null
    private var onClickDeleteItem: ((Item) -> Unit)? = null

    fun addItems(items: ArrayList<Item>){
        this.itemList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (Item) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (Item) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    )

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = itemList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std) }
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var description = view.findViewById<TextView>(R.id.tvDescription)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(item: Item){
            id.text = item.Id.toString()
            name.text = item.Name
            description.text = item.Description
        }
    }
}