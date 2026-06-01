package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.aura.ui.screens.ForumFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Mapeia os elementos do XML
        val btnExplorarMapa = view.findViewById<Button>(R.id.btn_explorar_mapa)
        val btnAcessarForum = view.findViewById<Button>(R.id.btn_acessar_forum)
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 2. Clique para explorar o mapa dentro do Card
        btnExplorarMapa.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapaFragment())
                .addToBackStack(null)
                .commit()
        }

        // 3. Clique para acessar o fórum dentro do Card
        btnAcessarForum.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ForumFragment())
                .addToBackStack(null)
                .commit()
        }

        // 4. Configura a barra inferior lendo o texto do menu para evitar erros de ID
        bottomNavigation.setOnItemSelectedListener { item ->
            val titulo = item.title.toString().lowercase()

            when {
                titulo.contains("home") || titulo.contains("início") -> {
                    true
                }
                titulo.contains("map") || titulo.contains("mapa") -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MapaFragment())
                        .commit()
                    true
                }
                titulo.contains("favorito") || titulo.contains("infinito") || titulo.contains("apoio") -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ForumFragment()) // Direciona para o Fórum/Apoio
                        .commit()
                    true
                }
                titulo.contains("perfil") || titulo.contains("profile") || titulo.contains("config") -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    } // Fecha o onViewCreated
} // Fecha a classe HomeFragment