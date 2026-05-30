package com.example.aura

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    // Cole aqui a sua URL do Supabase dentro das aspas
    const val SUPABASE_URL = "https://wmrkgzqhinqkftkohdsg.supabase.co"

    // Cole aqui a sua Chave Anon (anon key) do Supabase dentro das aspas
    const val SUPABASE_KEY = "sb_publishable__ijIAB0hno9qtuHMbRHXKw_Xwc4rf5l"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}