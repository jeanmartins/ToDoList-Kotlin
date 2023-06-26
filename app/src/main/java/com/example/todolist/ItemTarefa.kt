package com.example.todolist

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class ItemTarefa (
    var nome : String,
    var Hora : LocalTime?,
    var DataCompletada : LocalDate?,
    var id : UUID = UUID.randomUUID()
){
    fun estaCompleto() = DataCompletada != null
    fun imageSrc(): Int = if (estaCompleto()) R.drawable.checked_24 else  R.drawable.unchecked_24
    fun imageColor(context: Context) : Int = if (estaCompleto()) roxo(context) else preto(context)

    private fun roxo(context: Context) = ContextCompat.getColor(context, R.color.purple)
    private fun preto(context: Context) = ContextCompat.getColor(context, R.color.black)

}