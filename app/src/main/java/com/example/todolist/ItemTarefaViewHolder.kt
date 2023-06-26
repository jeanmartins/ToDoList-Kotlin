package com.example.todolist

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TarefaItemCellBinding
import java.time.format.DateTimeFormatter

class ItemTarefaViewHolder(
private val context : Context,
private val binding: TarefaItemCellBinding,
private val clickListener: ItemTarefaClickListener
) : RecyclerView.ViewHolder(binding.root)
{
    val formatoHora = DateTimeFormatter.ofPattern("HH:mm")
    fun bindItemTarefa(itemTarefa: ItemTarefa){
        binding.nome.text = itemTarefa.nome

        if(itemTarefa.estaCompleto()){
            binding.nome.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
            binding.Hora.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.botaoConcluir.setImageResource(itemTarefa.imageSrc())
        binding.botaoConcluir.setColorFilter(itemTarefa.imageColor(context))

        binding.botaoConcluir.setOnClickListener {
            clickListener.completarTarefa(itemTarefa)
        }

        binding.tarefaCellContainer.setOnClickListener {
            clickListener.editarTarefa(itemTarefa)
        }

        if(itemTarefa.Hora != null)
            binding.Hora.text = formatoHora.format(itemTarefa.Hora)
        else
            binding.Hora.text = ""
    }
}