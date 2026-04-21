package com.example.autochecksample

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class LoginValidatorTest {
    private val validator = LoginValidator()

    @Test
    fun `아이디_4자_미만_실패`() {
        val request = LoginRequest("abc", "pass1234!", "test@uangel.com", true)
        assertFalse("ID가 4자 미만일 때 실패해야 함", validator.validate(request))
    }

    @Test
    fun `아이디_딱_4자_성공`() {
        val request = LoginRequest("abcd", "pass1234!", "test@uangel.com", true)
        assertTrue("ID가 딱 4자일 때 성공해야 함", validator.validate(request))
    }

    @Test
    fun `아이디_4자_초과_성공`() {
        val request = LoginRequest("abcde", "pass1234!", "test@uangel.com", true)
        assertTrue("ID가 4자 초과일 때 성공해야 함", validator.validate(request))
    }

    @Test
    fun `비밀번호_8자_미만_실패`() {
        val request = LoginRequest("user123", "p1234!", "test@uangel.com", true)
        assertFalse("PW가 8자 미만일 때 실패해야 함", validator.validate(request))
    }

    @Test
    fun `비밀번호_딱_8자_성공`() {
        val request = LoginRequest("user123", "pass123!", "test@uangel.com", true)
        assertTrue("PW가 딱 8자일 때 성공해야 함", validator.validate(request))
    }

    @Test
    fun `비밀번호_8자_초과_성공`() {
        val request = LoginRequest("user123", "password123!", "test@uangel.com", true)
        assertTrue("PW가 8자 초과일 때 성공해야 함", validator.validate(request))
    }

    @Test
    fun `비밀번호_특수문자_없음_실패`() {
        val request = LoginRequest("user123", "pass1234", "test@uangel.com", true)
        assertFalse("PW에 특수문자가 없을 때 실패해야 함", validator.validate(request))
    }

    @Test
    fun `이메일_골뱅이_미포함_실패`() {
        val request = LoginRequest("user123", "pass1234!", "testuangel.com", true)
        assertFalse("Email에 @가 없을 때 실패해야 함", validator.validate(request))
    }

    @Test
    fun `약관_미동의_실패`() {
        val request = LoginRequest("user123", "pass1234!", "test@uangel.com", false)
        assertFalse("약관 미동의 시 실패해야 함", validator.validate(request))
    }

    @Test
    fun `모든_조건_충족_성공`() {
        val request = LoginRequest("kanghj", "pass1234!", "test@uangel.com", true)
        assertTrue("모든 조건 충족 시 성공해야 함", validator.validate(request))
    }
}
