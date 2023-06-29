package com.example.todolist
import java.lang.reflect.Type
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TarefaViewModel(): ViewModel()
{
    var itensTarefas = MutableLiveData<MutableList<ItemTarefa>>()
    init {
    }

    fun adicionarNovaTarefa( novaTarefa : ItemTarefa){
        val lista = itensTarefas.value
        lista!!.add(novaTarefa)
        itensTarefas.postValue(lista)
    }

    fun atualizarNovaTarefa( id: UUID, nome : String, hora : LocalTime?){
        val lista = itensTarefas.value
        val tarefa = lista!!.find { it.id == id  }!!
        tarefa.nome = nome
        tarefa.Hora = hora
        itensTarefas.postValue(lista)
    }

    fun concluir( novaTarefa : ItemTarefa){
        val lista = itensTarefas.value
        val tarefa = lista!!.find { it.id == novaTarefa.id  }!!
        if(tarefa.DataCompletada == null){
            tarefa.DataCompletada = LocalDate.now()
        }else{
            tarefa.DataCompletada = null
        }
        itensTarefas.postValue(lista)
    }


}