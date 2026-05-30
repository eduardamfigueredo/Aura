@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.example.aura

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long? = null,
    val titulo: String,
    val conteudo: String,
    val categoria: String
)