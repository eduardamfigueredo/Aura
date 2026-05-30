package com.example.aura

import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository {

    // Função para buscar todos os posts do banco de dados
    suspend fun buscarTodosOsPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            SupabaseClientProvider.client.postgrest["posts"]
                .select()
                .decodeList<Post>()
        }
    }

    // Função para salvar um novo post no banco de dados
    suspend fun salvarPost(post: Post) {
        withContext(Dispatchers.IO) {
            SupabaseClientProvider.client.postgrest["posts"]
                .insert(post)
        }
    }
}