package com.example.todolist


import LocalTimeAdapter
import android.content.Context
import android.content.SharedPreferences
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken
import java.time.LocalTime

class MainActivity : AppCompatActivity(), ItemTarefaClickListener {

    private lateinit var  binding : ActivityMainBinding
    private lateinit var tarefaViewModel: TarefaViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson


    private val PREF_NAME = "TarefaSharedPreferences"
    private val PREF_KEY_TAREFAS = "tarefas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gson = GsonBuilder()
            .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
            .create()

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        tarefaViewModel = ViewModelProvider(this).get(TarefaViewModel::class.java)
        tarefaViewModel.itensTarefas.value = carregarTarefas()


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

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        salvarTarefas()
    }

    private fun salvarTarefas() {
        val tarefasJson = tarefaViewModel.itensTarefas.value?.let { gson.toJson(it) }
        sharedPreferences.edit().putString(PREF_KEY_TAREFAS, tarefasJson).apply()
    }

    private fun carregarTarefas(): MutableList<ItemTarefa> {
        val tarefasJson = sharedPreferences.getString(PREF_KEY_TAREFAS, null)
        val type: Type = object : TypeToken<MutableList<ItemTarefa>>() {}.type

        return gson.fromJson(tarefasJson, type) ?: mutableListOf()
    }

}


