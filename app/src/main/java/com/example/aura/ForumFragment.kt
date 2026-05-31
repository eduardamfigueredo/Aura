package com.example.aura.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.PostAdapter
import com.example.aura.PostRepository
import com.example.aura.R
import kotlinx.coroutines.launch

class ForumFragment : Fragment() {

    private val repository = PostRepository()
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Vincula a RecyclerView do XML
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_posts)

        // 2. Inicializa o Adapter vazio
        postAdapter = PostAdapter(emptyList())
        recyclerView.adapter = postAdapter

        // 3. Busca os dados reais vindos lá do Supabase
        carregarPostsDoSupabase()
    }

    private fun carregarPostsDoSupabase() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val listaDePosts = repository.buscarTodosOsPosts()

                Log.d("SupabaseForum", "Posts carregados com sucesso: ${listaDePosts.size} posts encontrados.")

                // 4. Alimenta o adapter com os dados do banco para atualizar a tela
                postAdapter.atualizarLista(listaDePosts)

            } catch (e: Exception) {
                Log.e("SupabaseForum", "Erro ao buscar posts do banco: ${e.message}", e)
            }
        }
    }
}