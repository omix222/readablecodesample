package com.example.readablecode.modern;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Java 10で導入されたvar（ローカル変数型推論）のサンプルコード集
 * 
 * このクラスでは以下のvarの適切な使用方法を実装しています：
 * - 型が明確な場合のvar使用 (可読性向上)
 * - 複雑な型宣言の簡素化
 * - イテレータやストリーム処理での活用
 * - 適切でない使用例の回避
 * 
 * 参考：
 * - Java Language Specification (JLS) §14.4
 * - JEP 286: Local-Variable Type Inference
 * - Effective Java 3rd Edition Item 57
 * 
 * @version 1.0
 */
public class VarExamples {
    
    /**
     * varを使わない従来の書き方 - 冗長で可読性が低い例
     * 
     * この例では以下の問題があります：
     * - 型宣言が冗長で読みにくい
     * - 複雑なジェネリクス型の重複
     * - 右辺から型が明らかな場合でも型を明記
     */
    public static class TraditionalVerboseCode {
        
        /**
         * 従来の冗長な型宣言の例
         * - 型情報が重複している
         * - 長い型名で可読性が損なわれる
         */
        public void verboseTypeDeclarations() {
            // 冗長：右辺から型が明らか
            String userName = "John Doe";
            Integer userAge = 30;
            Boolean isActive = true;
            
            // 冗長：複雑なジェネリクス型の重複
            List<String> productNames = new ArrayList<String>();
            Map<String, List<Integer>> categoryToIds = new HashMap<String, List<Integer>>();
            
            // 冗長：コンストラクタから型が明らか
            LocalDateTime createdAt = LocalDateTime.now();
            Path configPath = Paths.get("config", "application.properties");
            
            // 冗長：メソッド戻り値の型が明らか
            Optional<String> result = findUserName("USER001");
            List<String> activeUsers = getActiveUsers();
        }
        
        /**
         * 従来のイテレータ処理 - 型宣言が冗長
         */
        public void verboseIterators() {
            Map<String, List<String>> categoriesMap = getCategoriesMap();
            
            // 冗長：イテレータの型宣言が長い
            for (Map.Entry<String, List<String>> entry : categoriesMap.entrySet()) {
                String category = entry.getKey();
                List<String> items = entry.getValue();
                System.out.println(category + ": " + items.size() + " items");
            }
        }
        
        private Optional<String> findUserName(String userId) {
            return Optional.of("John Doe");
        }
        
        private List<String> getActiveUsers() {
            return List.of("user1", "user2", "user3");
        }
        
        private Map<String, List<String>> getCategoriesMap() {
            Map<String, List<String>> map = new HashMap<>();
            map.put("electronics", List.of("phone", "laptop"));
            map.put("books", List.of("java", "python"));
            return map;
        }
    }
    
    /**
     * varを適切に使用した例 - 可読性が向上し、保守性が高い
     * 
     * この設計の改善点：
     * - 型宣言の冗長性を排除
     * - 右辺の値に注目が集まり意図が明確
     * - リファクタリング時の影響範囲を限定
     * - コードの簡潔性と可読性のバランス
     */
    public static class ImprovedVarUsage {
        
        /**
         * 良い例：varで型宣言を簡潔にする
         * 右辺から型が明らかな場合はvarを使用して可読性向上
         */
        public void improvedTypeDeclarations() {
            // 良い例：リテラルから型が明らか
            var userName = "John Doe";           // String
            var userAge = 30;                    // int
            var isActive = true;                 // boolean
            var score = 85.5;                    // double
            
            // 良い例：コンストラクタから型が明らか  
            var productNames = new ArrayList<String>();
            var categoryToIds = new HashMap<String, List<Integer>>();
            var userProfiles = new ArrayList<UserProfile>();
            
            // 良い例：ファクトリメソッドの戻り値
            var createdAt = LocalDateTime.now();
            var configPath = Paths.get("config", "application.properties");
            var emptyList = List.<String>of();
            
            // 良い例：メソッド戻り値が明確な場合
            var result = findUserName("USER001");    // Optional<String>
            var activeUsers = getActiveUsers();       // List<String>
        }
        
        /**
         * 良い例：複雑な型をvarで簡素化
         * ジェネリクスが複雑な場合でも可読性を保つ
         */
        public void complexTypeSimplification() {
            // 良い例：複雑なジェネリクス型をvarで簡素化
            var usersByCategory = Map.of(
                "premium", List.of("user1", "user2"),
                "standard", List.of("user3", "user4")
            );
            
            // 良い例：ストリーム処理の中間結果
            var filteredUsers = getUsers()
                .stream()
                .filter(user -> user.isActive())
                .collect(Collectors.toList());
                
            // 良い例：Builder パターンでの使用
            var order = new OrderBuilder()
                .customerId("CUST001")
                .addItem("BOOK001", 2)
                .deliveryAddress("123 Main St")
                .build();
        }
        
        /**
         * 良い例：イテレータでのvar使用
         * 複雑な型のイテレータを簡潔に記述
         */
        public void improvedIterators() {
            var categoriesMap = getCategoriesMap();
            
            // 良い例：varでイテレータの型宣言を簡素化
            for (var entry : categoriesMap.entrySet()) {
                var category = entry.getKey();
                var items = entry.getValue();
                System.out.println(category + ": " + items.size() + " items");
            }
            
            // 良い例：enhanced for-loopでのvar
            var userList = getUsers();
            for (var user : userList) {
                if (user.isActive()) {
                    System.out.println("Active user: " + user.name());
                }
            }
        }
        
        /**
         * 良い例：ストリーム処理でのvar活用
         * 中間変数の型宣言を簡素化
         */
        public Map<String, Long> analyzeUserActivity() {
            var users = getUsers();
            
            // 良い例：ストリーム処理の各段階でvar使用
            var activeUsers = users.stream()
                .filter(user -> user.isActive())
                .collect(Collectors.toList());
                
            var usersByDomain = activeUsers.stream()
                .collect(Collectors.groupingBy(
                    user -> extractDomain(user.email()),
                    Collectors.counting()
                ));
                
            return usersByDomain;
        }
        
        /**
         * 良い例：try-with-resources でのvar使用
         */
        public void fileProcessingWithVar() {
            var inputPath = Paths.get("input.txt");
            var outputPath = Paths.get("output.txt");
            
            // 良い例：リソース管理でのvar
            try (var reader = java.nio.file.Files.newBufferedReader(inputPath);
                 var writer = java.nio.file.Files.newBufferedWriter(outputPath)) {
                
                var line = reader.readLine();
                while (line != null) {
                    var processedLine = processLine(line);
                    writer.write(processedLine);
                    writer.newLine();
                    line = reader.readLine();
                }
            } catch (Exception e) {
                System.err.println("File processing error: " + e.getMessage());
            }
        }
        
        // サポートメソッド
        private Optional<String> findUserName(String userId) {
            return Optional.of("John Doe");
        }
        
        private List<String> getActiveUsers() {
            return List.of("user1", "user2", "user3");
        }
        
        private Map<String, List<String>> getCategoriesMap() {
            var map = new HashMap<String, List<String>>();
            map.put("electronics", List.of("phone", "laptop"));
            map.put("books", List.of("java", "python"));
            return map;
        }
        
        private List<UserProfile> getUsers() {
            return List.of(
                new UserProfile("john@example.com", "John", true),
                new UserProfile("jane@company.com", "Jane", true),
                new UserProfile("bob@test.org", "Bob", false)
            );
        }
        
        private String extractDomain(String email) {
            return email.substring(email.indexOf('@') + 1);
        }
        
        private String processLine(String line) {
            return line.trim().toUpperCase();
        }
        
        /**
         * 良い例：Java 17のrecordでvar使用の恩恵を最大化
         */
        public record UserProfile(String email, String name, boolean isActive) {
            public String domain() {
                return email.substring(email.indexOf('@') + 1);
            }
        }
        
        /**
         * 良い例：Builder パターンの例（varとの組み合わせで効果的）
         */
        public static class OrderBuilder {
            private String customerId;
            private final List<String> items = new ArrayList<>();
            private String deliveryAddress;
            
            public OrderBuilder customerId(String customerId) {
                this.customerId = customerId;
                return this;
            }
            
            public OrderBuilder addItem(String item, int quantity) {
                items.add(item + " x" + quantity);
                return this;
            }
            
            public OrderBuilder deliveryAddress(String address) {
                this.deliveryAddress = address;
                return this;
            }
            
            public Order build() {
                return new Order(customerId, List.copyOf(items), deliveryAddress);
            }
        }
        
        public record Order(String customerId, List<String> items, String deliveryAddress) {
        }
    }
    
    /**
     * varの適切でない使用例 - 避けるべきパターン
     * 
     * 以下の場合はvarを使用すべきではありません：
     * - 型が不明確になる場合
     * - 可読性が損なわれる場合
     * - メソッドパラメータや戻り値での使用
     */
    public static class InappropriateVarUsage {
        
        /**
         * 悪い例：型が不明確になるvar使用
         * 右辺から型が推測しにくい場合は明示的な型宣言を使用
         */
        public void ambiguousTypes() {
            // 悪い例：数値リテラルで型が曖昧
            var number = 42;           // int か Integer か不明確
            var decimal = 3.14;        // double か float か不明確
            
            // 悪い例：nullは型推論できない
            // var value = null;       // コンパイルエラー
            
            // 悪い例：ダイアモンド演算子のみでは型が不明
            // var list = new ArrayList<>(); // コンパイルエラー
            
            // 良い改善例：型を明確にする
            int intNumber = 42;
            double doubleDecimal = 3.14;
            List<String> stringList = new ArrayList<>();
        }
        
        /**
         * 悪い例：メソッドチェーンで型が不明確
         */
        public void unclearMethodChaining() {
            // 悪い例：中間結果の型が不明確
            var result = getData()
                .stream()
                .filter(x -> x > 10)
                .map(x -> x * 2)
                .findFirst();  // Optional<何> かわからない
                
            // 良い改善例：適切な箇所で型を明示
            List<Integer> numbers = getData();
            var filtered = numbers.stream()
                .filter(x -> x > 10)
                .map(x -> x * 2)
                .collect(Collectors.toList());
        }
        
        /**
         * 悪い例：適用範囲外でのvar使用
         */
        public void invalidVarUsage() {
            // 注意：以下はコンパイルエラーとなる例
            
            // varはローカル変数のみ使用可能
            // private var field = "value";        // フィールドでは使用不可
            // public var getProperty() { ... }    // 戻り値の型では使用不可
            // public void method(var param) { ... } // パラメータでは使用不可
            
            // varは初期化が必要
            // var uninitialized;  // 初期化なしは使用不可
        }
        
        private List<Integer> getData() {
            return List.of(5, 15, 25, 35);
        }
    }
    
    /**
     * varのベストプラクティス - 効果的な使用ガイドライン
     * 
     * varを使用する際の判断基準：
     * 1. 右辺から型が明確に推測できる
     * 2. 可読性が向上する
     * 3. 保守性が高まる
     */
    public static class VarBestPractices {
        
        /**
         * ベストプラクティス：適切な命名でvarの効果を最大化
         * 型情報がない分、変数名で意図を明確に表現
         */
        public void descriptiveNaming() {
            // 良い例：変数名で型と目的が明確
            var userEmailList = getUserEmails();         // List<String> と推測可能
            var productCountMap = getProductCounts();    // Map<String, Integer> と推測可能
            var isValidUser = validateUser("USER001");   // boolean と推測可能
            
            // 良い例：ビジネスロジックに焦点
            var eligibleCustomers = getCustomers()
                .stream()
                .filter(customer -> customer.isPremium())
                .collect(Collectors.toList());
                
            var monthlyRevenue = calculateRevenue(
                LocalDateTime.now().minusMonths(1),
                LocalDateTime.now()
            );
        }
        
        /**
         * ベストプラクティス：リファクタリング耐性の向上
         * varを使用することで型変更の影響を局所化
         */
        public void refactoringResilience() {
            // 良い例：実装変更に強い
            var users = getUserRepository().findActive();  // 戻り値の型が変わっても影響なし
            var cache = createCache();                      // キャッシュ実装変更に対応
            
            // 良い例：新しいAPIへの移行も容易
            var dateTime = getCurrentDateTime();  // LocalDateTime から ZonedDateTime への変更に対応
            var config = loadConfiguration();     // 設定形式の変更に対応
        }
        
        /**
         * ベストプラクティス：コードレビューでの確認ポイント
         */
        public void reviewGuidelines() {
            // チェック項目1：型推論の妥当性
            var userInput = getUserInput();  // String? Integer? Object? → メソッド名から推測可能か
            
            // チェック項目2：スコープの適切性
            var tempResult = processData();
            // ... 長い処理 ...
            // tempResultの型を覚えていられるスコープか？
            
            // チェック項目3：チーム内での一貫性
            var standardPattern = createStandardObject();  // チーム内で合意されたパターンか
        }
        
        // サポートメソッド（実装は省略）
        private List<String> getUserEmails() { return List.of(); }
        private Map<String, Integer> getProductCounts() { return Map.of(); }
        private boolean validateUser(String userId) { return true; }
        private List<Customer> getCustomers() { return List.of(); }
        private double calculateRevenue(LocalDateTime start, LocalDateTime end) { return 0.0; }
        private UserRepository getUserRepository() { return new UserRepository(); }
        private Object createCache() { return new Object(); }
        private LocalDateTime getCurrentDateTime() { return LocalDateTime.now(); }
        private Object loadConfiguration() { return new Object(); }
        private String getUserInput() { return ""; }
        private Object processData() { return new Object(); }
        private Object createStandardObject() { return new Object(); }
        
        private static class Customer {
            public boolean isPremium() { return true; }
        }
        
        private static class UserRepository {
            public List<String> findActive() { return List.of(); }
        }
    }
}