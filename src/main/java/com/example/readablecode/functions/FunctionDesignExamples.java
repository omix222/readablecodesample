package com.example.readablecode.functions;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * リーダブルコード 第3章「誤解されない名前」(p.31-40)と第4章「美しさ」(p.41-50)のサンプルコード集
 * 
 * このクラスでは以下の関数設計に関するノウハウを実装しています：
 * - 関数は一つのことだけを行う (p.32-34)
 * - 関数のパラメータは少なくする (p.35-37)
 * - 関数名は動詞句で表現する (p.38-40)
 * - 関数の抽象化レベルを統一する (p.42-44)
 * - 早期return（ガード句）を使用する (p.45-47)
 * - 関数の複雑さを軽減する (p.48-50)
 * 
 * @author Readable Code Examples
 * @version 1.0
 */
public class FunctionDesignExamples {
    
    /**
     * 悪い関数設計の例 - リーダブルコード p.35-37「パラメータを少なくする」
     * 
     * この関数の問題点：
     * - パラメータが多すぎる（7個） (p.35)
     * - 複数の責任を持っている (p.32)
     * - 一つの関数で複数のことを行っている (p.33)
     * - 抽象化レベルが混在している (p.42)
     */
    public static class BadFunctionDesign {
        
        /**
         * 悪い例：パラメータが多すぎて、複数の処理を一つの関数で行っている (p.35-37)
         * - 7個のパラメータは覚えきれず、呼び出し時にミスが起きやすい
         * - 名前のフォーマット、メール検証、年齢チェックなど複数の責任を持つ
         */
        public String processUserData(String name, String email, int age, boolean active, 
                                     String address, String phone, int score) {
            // 悪い例：一つの関数で複数の処理を行っている (p.32-34)
            String result = "";
            if (name != null && name.length() > 0) {
                result += name.trim().toUpperCase();
            }
            if (email != null && email.contains("@")) {
                result += " - " + email.toLowerCase();
            }
            if (age > 0 && age < 150) {
                result += " - Age: " + age;
            }
            if (active) {
                result += " - ACTIVE";
            }
            if (address != null) {
                result += " - " + address;
            }
            if (phone != null) {
                result += " - " + phone;
            }
            if (score > 100) {
                result += " - HIGH SCORE";
            }
            return result;
        }
        
        /**
         * 悪い例：曖昧なパラメータ名と複数の責任 (p.38-40)
         * - typeとflagは何を表すのか不明
         * - 一つの関数で異なる種類のデータを取得している
         */
        public List<String> getData(String type, boolean flag, int limit) {
            List<String> data = new ArrayList<>();
            // 悪い例：条件分岐で全く異なる処理を行っている (p.33)
            if (type.equals("users")) {
                for (int i = 0; i < limit; i++) {
                    if (flag) {
                        data.add("Active User " + i);
                    } else {
                        data.add("Inactive User " + i);
                    }
                }
            } else if (type.equals("products")) {
                for (int i = 0; i < limit; i++) {
                    data.add("Product " + i);
                }
            }
            return data;
        }
    }
    
    /**
     * 良い関数設計の例 - リーダブルコード p.32-34「関数は一つのことだけを行う」
     * 
     * この設計の改善点：
     * - UserProfileオブジェクトでパラメータをまとめる (p.36)
     * - 各関数は単一の責任を持つ (p.32)
     * - 小さな関数に分割して理解しやすくする (p.34)
     * - 抽象化レベルを統一する (p.42-44)
     */
    public static class GoodFunctionDesign {
        
        /**
         * 良い例：パラメータオブジェクトでデータをまとめる (p.36)
         * - 多数のパラメータを一つのオブジェクトにまとめる
         * - データの関連性を明確にする
         * - パラメータの順序ミスを防ぐ
         */
        public static class UserProfile {
            private final String name;
            private final String email;
            private final int age;
            private final boolean isActive;
            private final String address;
            private final String phoneNumber;
            private final int score;
            
            public UserProfile(String name, String email, int age, boolean isActive, 
                             String address, String phoneNumber, int score) {
                this.name = name;
                this.email = email;
                this.age = age;
                this.isActive = isActive;
                this.address = address;
                this.phoneNumber = phoneNumber;
                this.score = score;
            }
            
            public String getName() { return name; }
            public String getEmail() { return email; }
            public int getAge() { return age; }
            public boolean isActive() { return isActive; }
            public String getAddress() { return address; }
            public String getPhoneNumber() { return phoneNumber; }
            public int getScore() { return score; }
        }
        
        public String formatUserProfile(UserProfile profile) {
            StringBuilder result = new StringBuilder();
            
            appendFormattedName(result, profile.getName());
            appendFormattedEmail(result, profile.getEmail());
            appendAgeIfValid(result, profile.getAge());
            appendActiveStatus(result, profile.isActive());
            appendContactInfo(result, profile.getAddress(), profile.getPhoneNumber());
            appendHighScoreIndicator(result, profile.getScore());
            
            return result.toString();
        }
        
        private void appendFormattedName(StringBuilder result, String name) {
            if (isValidName(name)) {
                result.append(name.trim().toUpperCase());
            }
        }
        
        private void appendFormattedEmail(StringBuilder result, String email) {
            if (isValidEmail(email)) {
                result.append(" - ").append(email.toLowerCase());
            }
        }
        
        private void appendAgeIfValid(StringBuilder result, int age) {
            if (isValidAge(age)) {
                result.append(" - Age: ").append(age);
            }
        }
        
        private void appendActiveStatus(StringBuilder result, boolean isActive) {
            if (isActive) {
                result.append(" - ACTIVE");
            }
        }
        
        private void appendContactInfo(StringBuilder result, String address, String phoneNumber) {
            if (address != null && !address.trim().isEmpty()) {
                result.append(" - ").append(address);
            }
            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                result.append(" - ").append(phoneNumber);
            }
        }
        
        private void appendHighScoreIndicator(StringBuilder result, int score) {
            if (isHighScore(score)) {
                result.append(" - HIGH SCORE");
            }
        }
        
        private boolean isValidName(String name) {
            return name != null && !name.trim().isEmpty();
        }
        
        private boolean isValidEmail(String email) {
            return email != null && email.contains("@");
        }
        
        private boolean isValidAge(int age) {
            return age > 0 && age < 150;
        }
        
        private boolean isHighScore(int score) {
            return score > 100;
        }
        
        public List<String> getActiveUsers(int limit) {
            return generateUserList(limit, true);
        }
        
        public List<String> getInactiveUsers(int limit) {
            return generateUserList(limit, false);
        }
        
        public List<String> getProducts(int limit) {
            List<String> products = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                products.add("Product " + i);
            }
            return products;
        }
        
        private List<String> generateUserList(int limit, boolean isActive) {
            List<String> users = new ArrayList<>();
            String userType = isActive ? "Active" : "Inactive";
            
            for (int i = 0; i < limit; i++) {
                users.add(userType + " User " + i);
            }
            
            return users;
        }
    }
    
    public static class CalculationUtils {
        
        public static double calculateTotalPrice(double basePrice, double taxRate, double discount) {
            validatePositiveValue(basePrice, "Base price");
            validateTaxRate(taxRate);
            validateDiscount(discount);
            
            double discountedPrice = applyDiscount(basePrice, discount);
            return applyTax(discountedPrice, taxRate);
        }
        
        private static void validatePositiveValue(double value, String fieldName) {
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " cannot be negative");
            }
        }
        
        private static void validateTaxRate(double taxRate) {
            if (taxRate < 0 || taxRate > 1) {
                throw new IllegalArgumentException("Tax rate must be between 0 and 1");
            }
        }
        
        private static void validateDiscount(double discount) {
            if (discount < 0 || discount > 1) {
                throw new IllegalArgumentException("Discount must be between 0 and 1");
            }
        }
        
        private static double applyDiscount(double price, double discount) {
            return price * (1 - discount);
        }
        
        private static double applyTax(double price, double taxRate) {
            return price * (1 + taxRate);
        }
        
        public static Optional<Double> safeDivide(double dividend, double divisor) {
            if (divisor == 0) {
                return Optional.empty();
            }
            return Optional.of(dividend / divisor);
        }
        
        public static boolean isEven(int number) {
            return number % 2 == 0;
        }
        
        public static boolean isPrime(int number) {
            if (number < 2) {
                return false;
            }
            
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            
            return true;
        }
    }
}