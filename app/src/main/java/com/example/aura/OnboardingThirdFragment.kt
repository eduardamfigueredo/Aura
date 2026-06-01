package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OnboardingThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Vincula ao XML da terceira tela
        return inflater.inflate(R.layout.fragment_onboarding_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encontra o botão "Começar" pelo ID do XML
        val btnComecar = view.findViewById<Button>(R.id.btn_proximo_2)

        btnComecar.setOnClickListener {
            // Avança para a tela de Login do aplicativo
            // NOTA: Se a sua classe de login tiver outro nome (ex: LoginFragment), altere aqui embaixo!
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit() // Não adicionamos ao BackStack aqui para o usuário não voltar ao onboarding por engano
        }
    }
}