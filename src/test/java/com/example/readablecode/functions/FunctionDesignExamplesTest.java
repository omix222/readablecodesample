package com.example.readablecode.functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class FunctionDesignExamplesTest {
    
    @Nested
    @DisplayName("UserProfile Tests")
    class UserProfileTest {
        
        @Test
        @DisplayName("UserProfileオブジェクトが正しく作成される")
        public void shouldCreateUserProfileCorrectly() {
            FunctionDesignExamples.GoodFunctionDesign.UserProfile profile = 
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    "John Doe", "john@example.com", 30, true, 
                    "123 Main St", "555-1234", 85  // Score within valid range 0-100
                );
            
            assertEquals("John Doe", profile.name());
            assertEquals("john@example.com", profile.email());
            assertEquals(30, profile.age());
            assertTrue(profile.isActive());
            assertEquals("123 Main St", profile.address());
            assertEquals("555-1234", profile.phoneNumber());
            assertEquals(85, profile.score());
        }
    }
    
    @Nested
    @DisplayName("Good Function Design Tests")
    class GoodFunctionDesignTest {
        
        private final FunctionDesignExamples.GoodFunctionDesign goodDesign = 
            new FunctionDesignExamples.GoodFunctionDesign();
        
        @Test
        @DisplayName("ユーザープロファイルを正しくフォーマットする")
        public void shouldFormatUserProfileCorrectly() {
            FunctionDesignExamples.GoodFunctionDesign.UserProfile profile = 
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    "John Doe", "john@example.com", 30, true, 
                    "123 Main St", "555-1234", 85  // Valid score within 0-100 range
                );
            
            String result = goodDesign.formatUserProfile(profile);
            
            assertTrue(result.contains("JOHN DOE"));
            assertTrue(result.contains("john@example.com"));
            assertTrue(result.contains("Age: 30"));
            assertTrue(result.contains("ACTIVE"));
            assertTrue(result.contains("123 Main St"));
            assertTrue(result.contains("555-1234"));
            assertFalse(result.contains("HIGH SCORE")); // Score 85 is not > 100
        }
        
        @Test
        @DisplayName("無効なデータでUserProfileレコード作成が失敗する")
        public void shouldHandleInvalidProfileDataGracefully() {
            // recordのコンパクトコンストラクタで入力検証されるため例外が発生
            
            // 無効な名前
            assertThrows(IllegalArgumentException.class, () -> {
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    null, "john@example.com", 30, true, 
                    "123 Main St", "555-1234", 50
                );
            });
            
            // 無効なメール
            assertThrows(IllegalArgumentException.class, () -> {
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    "John Doe", "invalid-email", 30, true, 
                    "123 Main St", "555-1234", 50
                );
            });
            
            // 無効な年齢
            assertThrows(IllegalArgumentException.class, () -> {
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    "John Doe", "john@example.com", -5, true, 
                    "123 Main St", "555-1234", 50
                );
            });
            
            // 無効なスコア
            assertThrows(IllegalArgumentException.class, () -> {
                new FunctionDesignExamples.GoodFunctionDesign.UserProfile(
                    "John Doe", "john@example.com", 30, true, 
                    "123 Main St", "555-1234", 150  // Score > 100
                );
            });
        }
        
        @Test
        @DisplayName("アクティブユーザーリストを正しく生成する")
        public void shouldGenerateActiveUsersList() {
            List<String> activeUsers = goodDesign.getActiveUsers(3);
            
            assertEquals(3, activeUsers.size());
            for (String user : activeUsers) {
                assertTrue(user.contains("Active User"));
            }
        }
        
        @Test
        @DisplayName("非アクティブユーザーリストを正しく生成する")
        public void shouldGenerateInactiveUsersList() {
            List<String> inactiveUsers = goodDesign.getInactiveUsers(2);
            
            assertEquals(2, inactiveUsers.size());
            for (String user : inactiveUsers) {
                assertTrue(user.contains("Inactive User"));
            }
        }
        
        @Test
        @DisplayName("商品リストを正しく生成する")
        public void shouldGenerateProductsList() {
            List<String> products = goodDesign.getProducts(4);
            
            assertEquals(4, products.size());
            for (String product : products) {
                assertTrue(product.contains("Product"));
            }
        }
    }
    
    @Nested
    @DisplayName("CalculationUtils Tests")
    class CalculationUtilsTest {
        
        @Test
        @DisplayName("合計価格を正しく計算する")
        public void shouldCalculateTotalPriceCorrectly() {
            double basePrice = 100.0;
            double taxRate = 0.1; // 10%
            double discount = 0.2; // 20%
            
            double result = FunctionDesignExamples.CalculationUtils
                .calculateTotalPrice(basePrice, taxRate, discount);
            
            assertEquals(88.0, result, 0.01);
        }
        
        @Test
        @DisplayName("負の基本価格で例外が発生する")
        public void shouldThrowExceptionForNegativeBasePrice() {
            assertThrows(IllegalArgumentException.class, () -> {
                FunctionDesignExamples.CalculationUtils
                    .calculateTotalPrice(-100.0, 0.1, 0.2);
            });
        }
        
        @Test
        @DisplayName("無効な税率で例外が発生する")
        public void shouldThrowExceptionForInvalidTaxRate() {
            assertThrows(IllegalArgumentException.class, () -> {
                FunctionDesignExamples.CalculationUtils
                    .calculateTotalPrice(100.0, -0.1, 0.2);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                FunctionDesignExamples.CalculationUtils
                    .calculateTotalPrice(100.0, 1.5, 0.2);
            });
        }
        
        @Test
        @DisplayName("無効な割引率で例外が発生する")
        public void shouldThrowExceptionForInvalidDiscount() {
            assertThrows(IllegalArgumentException.class, () -> {
                FunctionDesignExamples.CalculationUtils
                    .calculateTotalPrice(100.0, 0.1, -0.1);
            });
            
            assertThrows(IllegalArgumentException.class, () -> {
                FunctionDesignExamples.CalculationUtils
                    .calculateTotalPrice(100.0, 0.1, 1.5);
            });
        }
        
        @Test
        @DisplayName("安全な除算が正しく動作する")
        public void shouldPerformSafeDivisionCorrectly() {
            Optional<Double> result = FunctionDesignExamples.CalculationUtils
                .safeDivide(10.0, 2.0);
            
            assertTrue(result.isPresent());
            assertEquals(5.0, result.get(), 0.01);
        }
        
        @Test
        @DisplayName("ゼロ除算で空のOptionalを返す")
        public void shouldReturnEmptyOptionalForDivisionByZero() {
            Optional<Double> result = FunctionDesignExamples.CalculationUtils
                .safeDivide(10.0, 0.0);
            
            assertFalse(result.isPresent());
        }
        
        @Test
        @DisplayName("偶数判定が正しく動作する")
        public void shouldIdentifyEvenNumbersCorrectly() {
            assertTrue(FunctionDesignExamples.CalculationUtils.isEven(2));
            assertTrue(FunctionDesignExamples.CalculationUtils.isEven(0));
            assertTrue(FunctionDesignExamples.CalculationUtils.isEven(-4));
            
            assertFalse(FunctionDesignExamples.CalculationUtils.isEven(1));
            assertFalse(FunctionDesignExamples.CalculationUtils.isEven(3));
            assertFalse(FunctionDesignExamples.CalculationUtils.isEven(-3));
        }
        
        @Test
        @DisplayName("素数判定が正しく動作する")
        public void shouldIdentifyPrimeNumbersCorrectly() {
            assertTrue(FunctionDesignExamples.CalculationUtils.isPrime(2));
            assertTrue(FunctionDesignExamples.CalculationUtils.isPrime(3));
            assertTrue(FunctionDesignExamples.CalculationUtils.isPrime(5));
            assertTrue(FunctionDesignExamples.CalculationUtils.isPrime(7));
            assertTrue(FunctionDesignExamples.CalculationUtils.isPrime(11));
            
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(1));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(4));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(6));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(8));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(9));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(10));
        }
        
        @Test
        @DisplayName("負の数と0は素数ではない")
        public void shouldReturnFalseForNegativeNumbersAndZero() {
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(0));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(-1));
            assertFalse(FunctionDesignExamples.CalculationUtils.isPrime(-5));
        }
    }
    
    @Nested
    @DisplayName("Bad vs Good Function Design Comparison")
    class FunctionDesignComparisonTest {
        
        @Test
        @DisplayName("悪い関数設計は複雑で理解しづらい")
        public void badFunctionDesignShouldBeComplexAndHardToUnderstand() {
            FunctionDesignExamples.BadFunctionDesign badDesign = 
                new FunctionDesignExamples.BadFunctionDesign();
            
            String result = badDesign.processUserData(
                "john doe", "john@example.com", 25, true, 
                "123 Main St", "555-1234", 150
            );
            
            assertNotNull(result);
            assertTrue(result.length() > 0);
        }
        
        @Test
        @DisplayName("悪い関数設計は曖昧なパラメータを使用する")
        public void badFunctionDesignShouldUseAmbiguousParameters() {
            FunctionDesignExamples.BadFunctionDesign badDesign = 
                new FunctionDesignExamples.BadFunctionDesign();
            
            List<String> users = badDesign.getData("users", true, 3);
            assertEquals(3, users.size());
            
            List<String> products = badDesign.getData("products", false, 2);
            assertEquals(2, products.size());
        }
    }
}