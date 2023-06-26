package com.example.todolist

interface ItemTarefaClickListener {

    fun editarTarefa(itemTarefa: ItemTarefa)
    fun completarTarefa(itemTarefa: ItemTarefa)
}