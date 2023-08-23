package com.mv.crud_di.interfaces

interface IGeneric<T> {
    fun Get() : List<T>
    fun Get(id: Int) : T?
    fun Insert(model: T) : Long?
    fun Update(id: Int, model: T) : Boolean
    fun Delete(id: Int) : Boolean
}