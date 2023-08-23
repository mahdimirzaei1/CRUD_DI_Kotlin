package com.mv.crud_di

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mv.crud_di.adapter.ItemAdapter
import com.mv.crud_di.models.Item
import com.mv.crud_di.repositories.ItemRepository
import com.mv.crud_di.repositories.SQLiteHelper

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edDescription: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var itemRepository: ItemRepository
    private lateinit var recyclerView: RecyclerView
    private var adapter: ItemAdapter? = null
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
        itemRepository = ItemRepository(sqliteHelper)
        btnAdd.setOnClickListener{ addItem() }
        btnView.setOnClickListener{ getItem() }
        btnUpdate.setOnClickListener { updateItem() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.Name, Toast.LENGTH_SHORT).show()
            edName.setText(it.toString())
            edDescription.setText(it.Description)
            item = it
        }

        adapter?.setOnClickDeleteItem {
            it.Id?.let { it1 -> deleteItem(it1) }
        }
    }

    private fun getItem(){
        val itemlist = itemRepository.Get()
        adapter?.addItems(itemlist)
    }

    private fun addItem(){
        val name = edName.text.toString()
        val description = edDescription.text.toString()

        if(name.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Please fill all required field", Toast.LENGTH_SHORT).show()
        }else{
            val item = Item(Name = name, Description = description)
            val status = itemRepository.Insert(item)

            if(status != null && status!! > -1){
                Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show()
                clearEditText()
                getItem()
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateItem(){
        val name = edName.text.toString()
        val description = edDescription.text.toString()

        //Check record not changed
        if(name == item?.Name && description == item?.Description){
            Toast.makeText(this, "Record has not changed...", Toast.LENGTH_SHORT).show()
            return
        }

        if(item == null) return

        val item = Item(Id= item!!.Id, Name = name, Description = description)
        val status = item!!.Id?.let { itemRepository.Update(it, item) }
        if(status!!){
            clearEditText()
            getItem()
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteItem(id: Int){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            itemRepository.Delete(id)
            getItem()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText(){
        edName.setText("")
        edDescription.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        edName = findViewById(R.id.edName)
        edDescription = findViewById(R.id.edDescription)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById((R.id.recyclerView))
    }
}