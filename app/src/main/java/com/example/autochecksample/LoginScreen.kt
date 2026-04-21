package com.example.autochecksample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(validator: LoginValidator = LoginValidator()) {
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isAccepted by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("정보를 입력하세요") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("AutoCheck 로그인", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // 1. 아이디 입력 필드
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("아이디") },
            placeholder = { Text("4자 이상 입력") }, // 가이드 힌트
            modifier = Modifier.fillMaxWidth().testTag("id_input")
        )

        // 2. 비밀번호 입력 필드
        OutlinedTextField(
            value = pw,
            onValueChange = { pw = it },
            label = { Text("비밀번호") },
            placeholder = { Text("8자 이상, 특수문자 포함") }, // 가이드 힌트
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().testTag("pw_input")
        )

        // 3. 이메일 입력 필드 (추가)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            placeholder = { Text("example@uangel.com") }, // 가이드 힌트
            modifier = Modifier.fillMaxWidth().testTag("email_input")
        )

        // 4. 약관 동의 (추가)
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isAccepted,
                onCheckedChange = { isAccepted = it },
                modifier = Modifier.testTag("terms_checkbox")
            )
            Text("개인정보 처리방침에 동의합니다 (필수)")
        }

        Button(
            onClick = {
                // 4개 이상의 데이터를 데이터 클래스로 묶어 전달 [cite: 2026-01-12]
                val request = LoginRequest(id, pw, email, isAccepted)
                resultText = if (validator.validate(request)) "로그인 성공" else "입력 형식을 확인하세요"
            },
            modifier = Modifier.fillMaxWidth().testTag("login_button")
        ) {
            Text("로그인")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = resultText, modifier = Modifier.testTag("result_text"))
    }
}