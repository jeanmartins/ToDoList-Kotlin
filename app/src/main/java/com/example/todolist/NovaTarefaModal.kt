package com.example.todolist

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentNovaTarefaBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime


class NovaTarefaModal(var itemTarefa: ItemTarefa?) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentNovaTarefaBinding
    private lateinit var tarefaViewModel: TarefaViewModel
    private var hora : LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(itemTarefa != null){
            binding.tituloModal.text = "Editar tarefa"
            val editavel = Editable.Factory.getInstance()
            binding.name.text = editavel.newEditable(itemTarefa!!.nome)

            if(itemTarefa!!.Hora !=  null){
                hora = itemTarefa!!.Hora
                atualizarHoraBotaoTexto()
            }

        }
        else{
            binding.tituloModal.text = "Nova tarefa"
        }

        tarefaViewModel = ViewModelProvider(activity).get(TarefaViewModel::class.java)
        binding.salvarBotao.setOnClickListener {
            salvar()
        }

        binding.horaBotao.setOnClickListener {
            abrirTimePicker()
        }
    }

    private fun abrirTimePicker() {
        if(hora == null)
            hora = LocalTime.now()
        var listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            hora = LocalTime.of(selectedHour, selectedMinute)
            atualizarHoraBotaoTexto()
        }
        val dialog = TimePickerDialog(activity, listener, hora!!.hour, hora!!.minute, true)
        dialog.setTitle("Horário da tarefa")
        dialog.show()
    }

    private fun atualizarHoraBotaoTexto() {
        binding.horaBotao.text = String.format("%02d:%02d",hora!!.hour, hora!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNovaTarefaBinding.inflate(inflater, container, false)
        return binding.root
    }

    private  fun salvar(){
        val nome = binding.name.text.toString()
        if(itemTarefa == null)
        {
            val novaTarefa = ItemTarefa(nome, hora, null)
            tarefaViewModel.adicionarNovaTarefa(novaTarefa)
        }
        else{
            tarefaViewModel.atualizarNovaTarefa(itemTarefa!!.id, nome, hora)
        }
        binding.name.setText("")
        dismiss()

    }

}