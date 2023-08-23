package com.mv.crud_di.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.mv.crud_di.interfaces.IItem
import com.mv.crud_di.models.Item

class ItemRepository(sqLiteHelper: SQLiteOpenHelper): IItem, BaseRepository(sqLiteHelper) {

    override fun Insert(model: Item): Long?{
        val contentValues = ContentValues()
        contentValues.put(Item._Name, model.Name)
        contentValues.put(Item._Description, model.Description)

        return db?.insert(Item._Tbl_Item, null, contentValues)
    }

    @SuppressLint("Range", "Recycle")
    override fun Get(): ArrayList<Item>{
        val itemList: ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT * FROM ${Item._Tbl_Item}"
        //val db = this.writableDatabase
        val cursor: Cursor?

        try{
            cursor = db?.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db?.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var description: String

        if(cursor!!.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex((Item._ID)))
                name = cursor.getString(cursor.getColumnIndex((Item._Name)))
                description = cursor.getString(cursor.getColumnIndex((Item._Description)))

                val item = Item(id, name, description)
                itemList.add(item)
            } while (cursor.moveToNext())
        }
        return itemList
    }

    override fun Update(id: Int, model: Item): Boolean{
        val contentValues = ContentValues()
        contentValues.put(Item._ID, model.Id)
        contentValues.put(Item._Name, model.Name)
        contentValues.put(Item._Description, model.Description)

        val update = db?.update(Item._Tbl_Item, contentValues, "${Item._ID}=$id", null)
        return (update != null) && (update > -1)
    }

    override fun Delete(id: Int): Boolean{
        val contentValues = ContentValues()
        contentValues.put(Item._ID, id)

        val delete = db?.delete(Item._Tbl_Item, "${Item._ID}=$id", null)
        return (delete != null) && (delete > -1)
    }

    // Unnecessary Methods
    override fun Get(id: Int): Item? {
        throw NotImplementedError()
    }
}