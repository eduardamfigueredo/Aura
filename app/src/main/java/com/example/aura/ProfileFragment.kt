package com.example.aura

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mapeando a barra de navegação e os novos botões do perfil
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val ivBack = view.findViewById<ImageView>(R.id.ivBack)
        val btnMeusDesabafos = view.findViewById<Button>(R.id.btn_meus_desabafos)
        val btnMinhasZonas = view.findViewById<Button>(R.id.btn_minhas_zonas)
        val btnSairApp = view.findViewById<Button>(R.id.btn_sair_app)

        // AUTOSINCRONIZAÇÃO: Deixa o quarto ícone ativo (Perfil) de forma segura pela posição 3
        bottomNavigation.menu.getItem(3).isChecked = true

        // Botão de voltar joga a usuária de volta para a Home
        ivBack.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        // Clicar em "Meus Desabafos" abre o Fórum (Aba posição 1)
        btnMeusDesabafos.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ForumFragment()).commit()
        }

        // Clicar em "Minhas Zonas" abre o Mapa (Aba posição 2)
        btnMinhasZonas.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, MapaFragment()).commit()
        }

        // AÇÃO DO BOTÃO SAIR: Limpa a memória de login e volta para o fragmento de Login!
        btnSairApp.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("AuraPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isLogged", false).apply() // Desloga

            Toast.makeText(context, "Até logo!", Toast.LENGTH_SHORT).show()

            // Redireciona de volta para a tela de Login
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        // Escutador de cliques nativo dos ícones da barra inferior
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
                    true // Já está no Perfil
                }
                else -> false
            }
        }
    }
}