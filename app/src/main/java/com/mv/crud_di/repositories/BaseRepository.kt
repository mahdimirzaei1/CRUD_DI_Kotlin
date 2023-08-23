package com.mv.crud_di.repositories

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class BaseRepository(sqLiteHelper: SQLiteOpenHelper) {

    var db: SQLiteDatabase? = null

    init {
        db = sqLiteHelper.writableDatabase
    }
}