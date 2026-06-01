package com.example.aura

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class ForumFragment : Fragment() {

    private val repository = PostRepository()
    private lateinit var postAdapter: PostAdapter
    private var postsDoBanco: MutableList<Post> = mutableListOf()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_posts)
        val btnAdd = view.findViewById<ImageButton>(R.id.btn_add)
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // SOLUÇÃO: Seleciona o terceiro ícone (Fórum/Infinito) de forma segura pela posição 2
        bottomNavigation.menu.getItem(2).isChecked = true

        recyclerView?.layoutManager = LinearLayoutManager(context)

        postAdapter = PostAdapter(emptyList())
        recyclerView?.adapter = postAdapter

        carregarPostsDoSupabase()

        btnAdd.setOnClickListener {
            abrirPopupCriarPost()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            val titulo = item.title.toString().lowercase()
            when {
                titulo.contains("home") || titulo.contains("início") -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                    true
                }
                titulo.contains("map") || titulo.contains("mapa") -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, MapaFragment()).commit()
                    true
                }
                titulo.contains("favorito") || titulo.contains("infinito") || titulo.contains("apoio") -> {
                    true
                }
                titulo.contains("perfil") || titulo.contains("profile") || titulo.contains("config") -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun carregarPostsDoSupabase() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val listaDePosts = repository.buscarTodosOsPosts()
                postsDoBanco = listaDePosts.toMutableList()

                if (postsDoBanco.isEmpty()) {
                    carregarPostsDeExemplo()
                } else {
                    postAdapter.atualizarLista(postsDoBanco)
                }
            } catch (e: Exception) {
                Log.e("SupabaseForum", "Erro ao buscar posts, usando dados locais", e)
                carregarPostsDeExemplo()
            }
        }
    }

    private fun carregarPostsDeExemplo() {
        postsDoBanco = mutableListOf(
            Post(titulo = "Como lidar com a ansiedade pós-trauma?", conteudo = "Respire fundo em ciclos de 4 segundos. O trauma não define o seu presente. — Psicologia Unama", categoria = "Apoio"),
            Post(titulo = "Sentem medo de andar sozinhas à noite?", conteudo = "Tente manter contato com uma amiga via chamada e use caminhos iluminados. — Anônimo", categoria = "Apoio"),
            Post(titulo = "Qual a importância do acolhimento psicológico?", conteudo = "Conversar sobre nossas dores em um ambiente confidencial alivia o peso. — Estudante de Psi", categoria = "Apoio"),
            Post(titulo = "Como apoiar uma amiga que sofreu agressão?", conteudo = "Escute sem julgar, valide os sentimentos dela e ofereça apoio firme. — Rede Aura", categoria = "Apoio"),
            Post(titulo = "Como construir limites saudáveis?", conteudo = "Dizer 'não' é uma forma de proteção e autocuidado essencial para a mente. — Anônimo", categoria = "Apoio")
        )
        postAdapter.atualizarLista(postsDoBanco)
    }

    private fun abrirPopupCriarPost() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Novo Desabafo")

        val layoutPopUp = LayoutInflater.from(context).inflate(R.layout.dialog_criar_post, null)
        builder.setView(layoutPopUp)

        val edtNome = layoutPopUp.findViewById<EditText>(R.id.popup_edt_nome)
        val cbAnonimo = layoutPopUp.findViewById<CheckBox>(R.id.popup_cb_anonimo)
        val edtPergunta = layoutPopUp.findViewById<EditText>(R.id.popup_edt_pergunta)

        cbAnonimo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtNome.visibility = View.GONE
                edtNome.setText("")
            } else {
                edtNome.visibility = View.VISIBLE
            }
        }

        builder.setPositiveButton("Postar") { dialog, _ ->
            val pergunta = edtPergunta.text.toString().trim()
            var autor = edtNome.text.toString().trim()

            if (cbAnonimo.isChecked || autor.isEmpty()) {
                autor = "Anônimo"
            }

            if (pergunta.isNotEmpty()) {
                val identificacaoAutor = "Postado por: $autor"

                val novoPost = Post(
                    titulo = pergunta,
                    conteudo = identificacaoAutor,
                    categoria = "Geral"
                )

                postsDoBanco.add(0, novoPost)

                activity?.runOnUiThread {
                    postAdapter.atualizarLista(postsDoBanco)
                    recyclerView?.scrollToPosition(0)
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        repository.salvarPost(novoPost)
                        activity?.runOnUiThread {
                            Toast.makeText(context, "Salvo no Supabase!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("SupabaseForum", "Erro ao salvar no banco: ${e.message}")
                    }
                }
            } else {
                Toast.makeText(context, "O texto não pode ficar vazio!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}