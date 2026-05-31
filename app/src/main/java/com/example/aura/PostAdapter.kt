package com.example.aura

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.R // Import essencial adicionado aqui!

class PostAdapter(private var listaDePosts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = listaDePosts[position]
        holder.tvTitulo.text = post.titulo
        holder.tvCategoria.text = post.categoria
    }

    override fun getItemCount(): Int = listaDePosts.size

    fun atualizarLista(novaLista: List<Post>) {
        this.listaDePosts = novaLista
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tv_titulo_post)
        val tvCategoria: TextView = itemView.findViewById(R.id.tv_categoria_post)
    }
}