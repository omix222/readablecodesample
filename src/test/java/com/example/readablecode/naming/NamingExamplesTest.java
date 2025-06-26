package com.example.readablecode.naming;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class NamingExamplesTest {
    
    @Nested
    @DisplayName("UserAccount Tests")
    class UserAccountTest {
        
        @Test
        @DisplayName("新しいユーザーアカウントが正しく作成される")
        void shouldCreateNewUserAccountCorrectly() {
            String userId = "user123";
            String email = "test@example.com";
            
            NamingExamples.UserAccount account = new NamingExamples.UserAccount(userId, email);
            
            assertEquals(userId, account.getUserId());
            assertEquals(email, account.getEmail());
            assertEquals(LocalDate.now(), account.getCreatedAt());
            assertTrue(account.isActive());
            assertEquals(0, account.getLoginAttempts());
        }
        
        @Test
        @DisplayName("ログイン試行が可能な状態を正しく判定する")
        void shouldAllowLoginWhenAccountIsActiveAndAttemptsUnderLimit() {
            NamingExamples.UserAccount account = new NamingExamples.UserAccount("user123", "test@example.com");
            
            assertTrue(account.canAttemptLogin());
        }
        
        @Test
        @DisplayName("ログイン失敗が3回に達するとアカウントが無効になる")
        void shouldDeactivateAccountAfterThreeFailedLogins() {
            NamingExamples.UserAccount account = new NamingExamples.UserAccount("user123", "test@example.com");
            
            account.recordFailedLogin();
            account.recordFailedLogin();
            assertTrue(account.canAttemptLogin());
            assertTrue(account.isActive());
            
            account.recordFailedLogin();
            assertFalse(account.canAttemptLogin());
            assertFalse(account.isActive());
            assertEquals(3, account.getLoginAttempts());
        }
        
        @Test
        @DisplayName("ログイン試行回数をリセットできる")
        void shouldResetLoginAttempts() {
            NamingExamples.UserAccount account = new NamingExamples.UserAccount("user123", "test@example.com");
            
            account.recordFailedLogin();
            account.recordFailedLogin();
            assertEquals(2, account.getLoginAttempts());
            
            account.resetLoginAttempts();
            assertEquals(0, account.getLoginAttempts());
        }
        
        @Test
        @DisplayName("アカウント作成からの日数を正しく計算する")
        void shouldCalculateDaysSinceCreation() {
            NamingExamples.UserAccount account = new NamingExamples.UserAccount("user123", "test@example.com");
            
            int daysSinceCreation = account.getDaysSinceCreation();
            assertEquals(0, daysSinceCreation);
        }
    }
    
    @Nested
    @DisplayName("EmailValidator Tests")
    class EmailValidatorTest {
        
        @Test
        @DisplayName("有効なメールアドレス形式を正しく判定する")
        void shouldValidateCorrectEmailFormats() {
            assertTrue(NamingExamples.EmailValidator.isValidEmailFormat("test@example.com"));
            assertTrue(NamingExamples.EmailValidator.isValidEmailFormat("user.name@domain.co.jp"));
            assertTrue(NamingExamples.EmailValidator.isValidEmailFormat("user+tag@example.org"));
        }
        
        @Test
        @DisplayName("無効なメールアドレス形式を正しく判定する")
        void shouldRejectInvalidEmailFormats() {
            assertFalse(NamingExamples.EmailValidator.isValidEmailFormat(null));
            assertFalse(NamingExamples.EmailValidator.isValidEmailFormat(""));
            assertFalse(NamingExamples.EmailValidator.isValidEmailFormat("invalid-email"));
            assertFalse(NamingExamples.EmailValidator.isValidEmailFormat("@example.com"));
            assertFalse(NamingExamples.EmailValidator.isValidEmailFormat("test@"));
        }
        
        @Test
        @DisplayName("ビジネスメールアドレスを正しく判定する")
        void shouldIdentifyBusinessEmails() {
            assertTrue(NamingExamples.EmailValidator.isBusinessEmail("user@company.com"));
            assertTrue(NamingExamples.EmailValidator.isBusinessEmail("contact@business.org"));
            assertTrue(NamingExamples.EmailValidator.isBusinessEmail("info@corporate.net"));
        }
        
        @Test
        @DisplayName("個人メールアドレスを正しく判定する")
        void shouldIdentifyPersonalEmails() {
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("user@gmail.com"));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("test@yahoo.com"));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("person@hotmail.com"));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("someone@outlook.com"));
        }
        
        @Test
        @DisplayName("無効なメールアドレスはビジネスメールとして判定されない")
        void shouldRejectInvalidEmailsAsBusinessEmails() {
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail(null));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("invalid-email"));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail(""));
        }
        
        @Test
        @DisplayName("大文字小文字を区別せずに判定する")
        void shouldBeCaseInsensitive() {
            assertTrue(NamingExamples.EmailValidator.isValidEmailFormat("Test@Example.COM"));
            assertFalse(NamingExamples.EmailValidator.isBusinessEmail("User@GMAIL.COM"));
            assertTrue(NamingExamples.EmailValidator.isBusinessEmail("Contact@COMPANY.COM"));
        }
    }
    
    @Nested
    @DisplayName("Good vs Bad Naming Comparison")
    class NamingComparisonTest {
        
        @Test
        @DisplayName("良い命名規則のクラスは理解しやすい")
        void goodNamingClassShouldBeUnderstandable() {
            NamingExamples.GoodNaming goodExample = new NamingExamples.GoodNaming();
            
            int result = goodExample.calculateTotalPrice(100);
            assertEquals(201, result);
            
            assertTrue(goodExample.isValidEmail("test@example.com"));
            assertFalse(goodExample.isValidEmail("invalid"));
            
            String normalized = goodExample.normalizeUserInput("  TEST INPUT  ");
            assertEquals("test input", normalized);
        }
        
        @Test
        @DisplayName("悪い命名規則のクラスは理解しづらい")
        void badNamingClassShouldBeDifficultToUnderstand() {
            NamingExamples.BadNaming badExample = new NamingExamples.BadNaming();
            
            int result = badExample.calc(100);
            assertEquals(201, result);
            
            assertTrue(badExample.check("test"));
            assertFalse(badExample.check(""));
            
            String processed = badExample.process("  TEST INPUT  ");
            assertEquals("test input", processed);
        }
    }
}