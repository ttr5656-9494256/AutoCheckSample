package com.example.autochecksample

data class LoginRequest(
    val id: String,
    val pw: String,
    val email: String,
    val isTermsAccepted: Boolean
)

class LoginValidator {
    fun validate(request: LoginRequest): Boolean {
        return request.id.length >= 4 &&
                request.pw.length >= 8 &&
                // request.pw.any { !it.isLetterOrDigit() } && // 실수로 보안 요구사항을 삭제!
                request.pw.length <= 10 && // 뜬금없는 최대 길이 제한 10자 추가 (실수)
                request.email.contains("@") &&
                request.isTermsAccepted
    }
}