package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TarefaItemCellBinding

class ItemTarefaAdapter (
    private val itensTarefa : List<ItemTarefa>,
    private val clickListener: ItemTarefaClickListener
) : RecyclerView.Adapter<ItemTarefaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTarefaViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TarefaItemCellBinding.inflate(from, parent, false)
        return ItemTarefaViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = itensTarefa.size

    override fun onBindViewHolder(holder: ItemTarefaViewHolder, position: Int) {
        holder.bindItemTarefa(itensTarefa[position])
    }
}