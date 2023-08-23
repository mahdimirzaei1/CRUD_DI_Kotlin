package com.mv.crud_di.models

data class Item(
    override var Id: Int? = null,
    override var Name: String = "",
    var Description: String = ""
) : IBaseEntity {
    companion object {
        var _Tbl_Item = "Item"
        val _ID = "ID"
        val _Name = "Name"
        val _Description = "Description"

        fun CreateQuery(): String{
            val createTbl = ("CREATE TABLE " + _Tbl_Item + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + _Name + " TEXT, "
                    + _Description + " TEXT, )")
            return createTbl
        }

        fun DropQuery(): String{
            return "DROP TABLE IF EXISTS $_Tbl_Item"
        }
    }
}