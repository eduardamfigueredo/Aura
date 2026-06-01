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
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.PostAdapter
import com.example.aura.PostRepository
import com.example.aura.R
import kotlinx.coroutines.launch
import com.example.aura.Post

class ForumFragment : Fragment() {

    private val repository = PostRepository()
    private lateinit var postAdapter: PostAdapter
    private var postsDoBanco: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_posts)
        val btnAdd = view.findViewById<ImageButton>(R.id.btn_add)

        postAdapter = PostAdapter(emptyList())
        recyclerView.adapter = postAdapter

        carregarPostsDoSupabase()

        btnAdd.setOnClickListener {
            abrirPopupCriarPost()
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
        // CORREÇÃO: Categoria agora recebe uma String não nula válida ("Apoio")
        postsDoBanco = mutableListOf(
            Post(titulo = "Ansiedade pós-trauma", conteudo = "Respire fundo em ciclos de 4 segundos. O trauma não define o seu presente, você está segura.", categoria = "Apoio"),
            Post(titulo = "Medo de andar à noite", conteudo = "Tente manter contato com uma amiga via chamada e use caminhos iluminados.", categoria = "Apoio"),
            Post(titulo = "Acolhimento psicológico", conteudo = "Conversar sobre nossas dores em um ambiente confidencial alivia o peso emocional.", categoria = "Apoio"),
            Post(titulo = "Apoiar uma amiga", conteudo = "Escute sem julgar, valide os sentimentos dela e ofereça apoio para procurar ajuda.", categoria = "Apoio"),
            Post(titulo = "Limites saudáveis", conteudo = "Dizer 'não' é uma forma de proteção e autocuidado. Impor limites é essencial.", categoria = "Apoio")
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
                val conteudoFinal = "[$autor]: $pergunta"

                // CORREÇÃO: Criando o post com a categoria sendo String comum obrigatória
                val novoPost = Post(
                    titulo = "Pergunta da Comunidade",
                    conteudo = conteudoFinal,
                    categoria = "Geral"
                )

                postsDoBanco.add(0, novoPost)
                postAdapter.atualizarLista(postsDoBanco)

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        repository.salvarPost(novoPost)
                        Toast.makeText(context, "Postado com sucesso!", Toast.LENGTH_SHORT).show()
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