package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtNome = view.findViewById<EditText>(R.id.edt_nome)
        val edtEmail = view.findViewById<EditText>(R.id.edt_email)
        val edtSenha = view.findViewById<EditText>(R.id.edt_senha)
        val btnEntrar = view.findViewById<Button>(R.id.btn_entrar)

        btnEntrar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val senha = edtSenha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(context, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Bem-vinda, $nome!", Toast.LENGTH_SHORT).show()

                // Salva na memória do celular que o usuário está logado
                val sharedPreferences = requireActivity().getSharedPreferences("AuraPrefs", android.content.Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("isLogged", true).apply()

                // AGORA VAI PARA A HOME DE VERDADE:
                // Sem addToBackStack para não voltar ao login se apertar o botão voltar do aparelho
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }
    }
}