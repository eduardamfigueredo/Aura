package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aura.R
import kotlinx.coroutines.delay

@Composable
fun ScreenInicio(onTimeout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8EE)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_aura_lilas),
                contentDescription = "Logo Aura",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Seu espaço de\nsegurança e apoio",
                fontSize = 16.sp,
                color = Color(0xFF7F839A),
                textAlign = TextAlign.Center
            )
        }
    }

    // Avança para a próxima tela após 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
}

@Composable
fun ScreenOnboarding1(onNextClick: () -> Unit) {
    OnboardingPage(
        imageRes = R.drawable.logo_aura_perfil, // Notei que a imagem_1 no seu protótipo parece ser a mulher caminhando, ajuste se necessário!
        title = "Bem-Vinda à Aura",
        subtitle = "Conectando você a uma rede de segurança e cuidado",
        buttonText = "Próximo",
        onButtonClick = onNextClick
    )
}

@Composable
fun ScreenOnboarding2(onNextClick: () -> Unit) {
    OnboardingPage(
        imageRes = R.drawable.imagem_2,
        title = "Conheça seu Bairro",
        subtitle = "Verifique a presença de registros de agressão sexual na sua região (com base em dados públicos)",
        buttonText = "Próximo",
        onButtonClick = onNextClick
    )
}

@Composable
fun ScreenOnboarding3(onStartClick: () -> Unit) {
    OnboardingPage(
        imageRes = R.drawable.imagem_3,
        title = "Apoio psicológico\nGratuito",
        subtitle = "Receba orientação de estudantes de psicologia qualificados através do nosso fórum confidencial.",
        buttonText = "Começar",
        onButtonClick = onStartClick
    )
}