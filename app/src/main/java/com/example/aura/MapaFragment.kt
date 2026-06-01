package com.example.aura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import         android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtPesquisa = view.findViewById<EditText>(R.id.edt_pesquisa)
        val btnFiltrar = view.findViewById<Button>(R.id.btn_filtrar)
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Força o terceiro ícone (posição 2) a ficar aceso (Mapa)
        bottomNavigation.menu.getItem(2).isChecked = true

        // Ações de simulação do botão filtrar
        btnFiltrar.setOnClickListener {
            val endereco = edtPesquisa.text.toString().trim()
            if (endereco.isNotEmpty()) {
                Toast.makeText(context, "Simulando busca por: $endereco", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Digite um endereço para pesquisar!", Toast.LENGTH_SHORT).show()
            }
        }

        // Navegação direta pelos ícones físicos da barra
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
                    true // Já está na tela do Mapa
                }
                menu.getItem(3) -> {
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}