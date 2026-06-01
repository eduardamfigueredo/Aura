package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        val btnExplorarMapa = view.findViewById<Button>(R.id.btn_explorar_mapa)
        val btnAcessarForum = view.findViewById<Button>(R.id.btn_acessar_forum)
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 1. Configura a navegação direta ao clicar manualmente nos botões da barra inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            val menu = bottomNavigation.menu
            when (item) {
                menu.getItem(0) -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                    true
                }
                menu.getItem(1) -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ForumFragment()).commit()
                    true
                }
                menu.getItem(2) -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, MapaFragment()).commit()
                    true
                }
                menu.getItem(3) -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // 2. Clique para explorar o mapa dentro do Card (Vai para o Mapa -> Terceiro ícone/Posição 2)
        btnExplorarMapa.setOnClickListener {
            // Desativa a barra visualmente para não dar conflito, mas sem usar variáveis externas
            bottomNavigation.menu.getItem(2).isChecked = true

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapaFragment())
                .addToBackStack(null)
                .commit()
        }

        // 3. Clique para acessar o fórum dentro do Card (Vai para o Fórum -> Segundo ícone/Posição 1)
        btnAcessarForum.setOnClickListener {
            // Desativa a barra visualmente para não dar conflito, mas sem usar variáveis externas
            bottomNavigation.menu.getItem(1).isChecked = true

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ForumFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}