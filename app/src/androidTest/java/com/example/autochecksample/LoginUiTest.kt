package com.example.autochecksample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 로그인_성공_시나리오() {
        // UI 화면 설정
        composeTestRule.setContent { LoginScreen() }

        // 1. 모든 필드에 유효한 값 입력
        composeTestRule.onNodeWithTag("id_input").performTextInput("kanghj")
        composeTestRule.onNodeWithTag("pw_input").performTextInput("pass1234!")
        composeTestRule.onNodeWithTag("email_input").performTextInput("test@uangel.com")
        composeTestRule.onNodeWithTag("terms_checkbox").performClick()

        // 2. 로그인 버튼 클릭
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 3. "로그인 성공" 텍스트 나타나는지 검증
        composeTestRule.onNodeWithTag("result_text").assertTextEquals("로그인 성공")

        // 4. 발표 시연을 위해 5초 대기
        Thread.sleep(5000)
    }

    @Test
    fun 로그인_실패_시나리오_ID_규격_미달() {
        // UI 화면 설정
        composeTestRule.setContent { LoginScreen() }

        // 1. ID만 3자(규격 미달)로 입력하고 나머지는 올바르게 입력
        composeTestRule.onNodeWithTag("id_input").performTextInput("abc")
        composeTestRule.onNodeWithTag("pw_input").performTextInput("pass1234!")
        composeTestRule.onNodeWithTag("email_input").performTextInput("test@uangel.com")
        composeTestRule.onNodeWithTag("terms_checkbox").performClick()

        // 2. 로그인 버튼 클릭
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 3. "입력 형식을 확인하세요" 텍스트 확인
        composeTestRule.onNodeWithTag("result_text").assertTextEquals("입력 형식을 확인하세요")

        // 4. 발표 시연을 위해 5초 대기
        Thread.sleep(5000)
    }

    @Test
    fun 로그인_실패_시나리오_특수문자_누락() {
        composeTestRule.setContent { LoginScreen() }

        // PW에 특수문자 없이 입력
        composeTestRule.onNodeWithTag("id_input").performTextInput("kanghj")
        composeTestRule.onNodeWithTag("pw_input").performTextInput("pass12345")
        composeTestRule.onNodeWithTag("email_input").performTextInput("test@uangel.com")
        composeTestRule.onNodeWithTag("terms_checkbox").performClick()

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.onNodeWithTag("result_text").assertTextEquals("입력 형식을 확인하세요")

        // 4. 발표 시연을 위해 5초 대기
        Thread.sleep(5000)
    }
}
