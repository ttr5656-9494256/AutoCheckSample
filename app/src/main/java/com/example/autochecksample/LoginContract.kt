package com.example.autochecksample

data class LoginRequest(
    val id: String,
    val pw: String,
    val email: String,
    val isTermsAccepted: Boolean
)

class LoginValidator {
    fun validate(request: LoginRequest): Boolean {
        // 꼼꼼한 분석을 통한 유효성 검사 로직 [cite: 2026-03-13]
        return request.id.length >= 4 &&
                request.pw.length >= 8 &&
                request.pw.any { !it.isLetterOrDigit() } &&
                request.email.contains("#") &&
                request.email.contains(".") &&
                request.isTermsAccepted
    }
}