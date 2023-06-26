package com.example.todolist


import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ItemTarefaClickListener {

    private lateinit var  binding : ActivityMainBinding
    private lateinit var tarefaViewModel: TarefaViewModel
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tarefaViewModel = ViewModelProvider(this).get(TarefaViewModel::class.java)
        binding.botaoTarefa.setOnClickListener {
            NovaTarefaModal(null).show(supportFragmentManager, "NovaTarefaModal");
        }
        setRecyclerView()
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val title = "To Do list"
        val spannableTitle = SpannableString(title)
        spannableTitle.setSpan(ForegroundColorSpan(resources.getColor(android.R.color.white)), 0, spannableTitle.length, 0)
        spannableTitle.setSpan(StyleSpan(Typeface.BOLD), 0, spannableTitle.length, 0)
        supportActionBar?.title = spannableTitle

    }


    private  fun setRecyclerView(){
    val mainActivity = this;
        tarefaViewModel.itensTarefas.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = ItemTarefaAdapter(it, mainActivity)
            }
        }
    }

    override fun editarTarefa(itemTarefa: ItemTarefa) {
        NovaTarefaModal(itemTarefa).show(supportFragmentManager, "NovaTarefaModal")
    }

    override fun completarTarefa(itemTarefa: ItemTarefa) {
       tarefaViewModel.concluir(itemTarefa)
    }

}