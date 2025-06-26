package com.example.readablecode.comments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

public class CommentExamplesTest {
    
    @Nested
    @DisplayName("Good Comments Tests")
    class GoodCommentsTest {
        
        private final CommentExamples.GoodComments goodComments = 
            new CommentExamples.GoodComments();
        
        @Test
        @DisplayName("複利計算が正しく動作する")
        public void shouldCalculateCompoundInterestCorrectly() {
            double principal = 1000.0;
            double rate = 0.05; // 5%
            int frequency = 12; // monthly
            int years = 2;
            
            double result = goodComments.calculateCompoundInterest(principal, rate, frequency, years);
            
            assertTrue(result > principal);
            assertEquals(1104.94, result, 0.01);
        }
        
        @Test
        @DisplayName("複利計算で無効な入力値に対して例外が発生する")
        public void shouldThrowExceptionForInvalidCompoundInterestInput() {
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.calculateCompoundInterest(-1000.0, 0.05, 12, 2);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.calculateCompoundInterest(1000.0, -0.05, 12, 2);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.calculateCompoundInterest(1000.0, 0.05, 0, 2);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.calculateCompoundInterest(1000.0, 0.05, 12, -1);
            });
        }
        
        @Test
        @DisplayName("二分探索が正しく動作する")
        public void shouldPerformBinarySearchCorrectly() {
            int[] sortedArray = {1, 3, 5, 7, 9, 11, 13, 15};
            
            assertEquals(0, goodComments.binarySearch(sortedArray, 1));
            assertEquals(3, goodComments.binarySearch(sortedArray, 7));
            assertEquals(7, goodComments.binarySearch(sortedArray, 15));
            assertEquals(-1, goodComments.binarySearch(sortedArray, 4));
            assertEquals(-1, goodComments.binarySearch(sortedArray, 20));
        }
        
        @Test
        @DisplayName("二分探索で無効な入力に対して-1を返す")
        public void shouldReturnMinusOneForInvalidBinarySearchInput() {
            assertEquals(-1, goodComments.binarySearch(null, 5));
            assertEquals(-1, goodComments.binarySearch(new int[0], 5));
        }
        
        @Test
        @DisplayName("メールアドレスの正規化が正しく動作する")
        public void shouldNormalizeEmailCorrectly() {
            assertEquals("test@example.com", 
                goodComments.normalizeEmail("TEST@EXAMPLE.COM"));
            
            assertEquals("johndoe@gmail.com", 
                goodComments.normalizeEmail("john.doe@gmail.com"));
            
            assertEquals("johndoe@gmail.com", 
                goodComments.normalizeEmail("john.doe+shopping@gmail.com"));
            
            assertEquals("user@company.com", 
                goodComments.normalizeEmail("user@company.com"));
        }
        
        @Test
        @DisplayName("無効なメールアドレスの正規化で例外が発生する")
        public void shouldThrowExceptionForInvalidEmailNormalization() {
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.normalizeEmail(null);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                goodComments.normalizeEmail("invalid-email");
            });
            
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
                goodComments.normalizeEmail("test@");
            });
        }
        
        @Test
        @DisplayName("Gmail以外のドメインでは正規化ルールが適用されない")
        public void shouldNotApplyGmailRulesForOtherDomains() {
            assertEquals("john.doe@yahoo.com", 
                goodComments.normalizeEmail("john.doe@yahoo.com"));
            
            assertEquals("john.doe+tag@company.com", 
                goodComments.normalizeEmail("john.doe+tag@company.com"));
        }
    }
    
    @Nested
    @DisplayName("Bad Comments Tests")
    class BadCommentsTest {
        
        private final CommentExamples.BadComments badComments = 
            new CommentExamples.BadComments();
        
        @Test
        @DisplayName("悪いコメントのメソッドも動作はする")
        public void badCommentMethodsShouldStillWork() {
            badComments.incrementCounter();
            
            assertEquals(30, badComments.calculate(2, 10));
            
            assertTrue(badComments.isValid("test"));
            assertFalse(badComments.isValid(""));
            assertFalse(badComments.isValid(null));
        }
    }
    
    @Nested
    @DisplayName("PaymentResult Tests")
    class PaymentResultTest {
        
        @Test
        @DisplayName("PaymentResultオブジェクトが正しく作成される")
        public void shouldCreatePaymentResultCorrectly() {
            CommentExamples.PaymentResult result = new CommentExamples.PaymentResult();
            
            assertNotNull(result);
            assertNull(result.getTransactionId());
            assertFalse(result.isSuccess());
            assertNull(result.getErrorMessage());
        }
    }
    
    @Nested
    @DisplayName("Exception Tests")
    class ExceptionTest {
        
        @Test
        @DisplayName("PaymentExceptionが正しく作成される")
        public void shouldCreatePaymentExceptionCorrectly() {
            CommentExamples.PaymentException exception = 
                new CommentExamples.PaymentException("Test error");
            
            assertEquals("Test error", exception.getMessage());
        }
        
        @Test
        @DisplayName("PaymentExceptionが原因付きで正しく作成される")
        public void shouldCreatePaymentExceptionWithCauseCorrectly() {
            Throwable cause = new RuntimeException("Root cause");
            CommentExamples.PaymentException exception = 
                new CommentExamples.PaymentException("Test error", cause);
            
            assertEquals("Test error", exception.getMessage());
            assertEquals(cause, exception.getCause());
        }
        
        @Test
        @DisplayName("TransientPaymentExceptionが正しく作成される")
        public void shouldCreateTransientPaymentExceptionCorrectly() {
            CommentExamples.TransientPaymentException exception = 
                new CommentExamples.TransientPaymentException("Transient error");
            
            assertEquals("Transient error", exception.getMessage());
        }
    }
}