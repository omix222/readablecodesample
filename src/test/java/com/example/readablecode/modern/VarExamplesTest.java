package com.example.readablecode.modern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VarExamplesTest {
    
    @Nested
    @DisplayName("Traditional Verbose Code Tests")
    class TraditionalVerboseCodeTest {
        
        @Test
        @DisplayName("従来の冗長な書き方が正しく動作する")
        public void traditionalVerboseCodeShouldWork() {
            var traditional = new VarExamples.TraditionalVerboseCode();
            
            // 例外が発生しないことを確認
            assertDoesNotThrow(() -> {
                traditional.verboseTypeDeclarations();
                traditional.verboseIterators();
            });
        }
    }
    
    @Nested
    @DisplayName("Improved Var Usage Tests")
    class ImprovedVarUsageTest {
        
        @Test
        @DisplayName("varを使った型推論が正しく動作する")
        public void varTypeInferenceShouldWork() {
            var improved = new VarExamples.ImprovedVarUsage();
            
            // 例外が発生しないことを確認
            assertDoesNotThrow(() -> {
                improved.improvedTypeDeclarations();
                improved.complexTypeSimplification();
                improved.improvedIterators();
            });
        }
        
        @Test
        @DisplayName("varでコレクション操作が正しく動作する")
        public void varCollectionOperationsShouldWork() {
            var improved = new VarExamples.ImprovedVarUsage();
            
            // varを使ったコレクション操作
            var stringList = new ArrayList<String>();
            stringList.add("test1");
            stringList.add("test2");
            
            var stringMap = new HashMap<String, Integer>();
            stringMap.put("key1", 1);
            stringMap.put("key2", 2);
            
            assertEquals(2, stringList.size());
            assertEquals(2, stringMap.size());
            assertTrue(stringList.contains("test1"));
            assertTrue(stringMap.containsKey("key1"));
        }
        
        @Test
        @DisplayName("varでストリーム処理が正しく動作する")
        public void varStreamProcessingShouldWork() {
            var improved = new VarExamples.ImprovedVarUsage();
            
            var result = improved.analyzeUserActivity();
            
            assertNotNull(result);
            assertTrue(result instanceof Map);
        }
        
        @Test
        @DisplayName("UserProfileレコードが正しく動作する")
        public void userProfileRecordShouldWork() {
            var profile = new VarExamples.ImprovedVarUsage.UserProfile(
                "john@example.com", "John Doe", true
            );
            
            assertEquals("john@example.com", profile.email());
            assertEquals("John Doe", profile.name());
            assertTrue(profile.isActive());
            assertEquals("example.com", profile.domain());
        }
        
        @Test
        @DisplayName("OrderBuilderパターンがvarと組み合わせて正しく動作する")
        public void orderBuilderWithVarShouldWork() {
            var builder = new VarExamples.ImprovedVarUsage.OrderBuilder();
            
            var order = builder
                .customerId("CUST001")
                .addItem("BOOK001", 2)
                .addItem("PEN001", 5)
                .deliveryAddress("123 Main St")
                .build();
            
            assertEquals("CUST001", order.customerId());
            assertEquals(2, order.items().size());
            assertEquals("123 Main St", order.deliveryAddress());
            assertTrue(order.items().contains("BOOK001 x2"));
            assertTrue(order.items().contains("PEN001 x5"));
        }
    }
    
    @Nested
    @DisplayName("Inappropriate Var Usage Tests")
    class InappropriateVarUsageTest {
        
        @Test
        @DisplayName("型が明確な場合のvar使用例")
        public void appropriateVarUsageExamples() {
            var inappropriate = new VarExamples.InappropriateVarUsage();
            
            // 型が明確な場合はvarが有効
            var text = "Hello World";        // String
            var number = 42;                 // int
            var flag = true;                 // boolean
            var list = List.of("a", "b");    // List<String>
            
            assertTrue(text instanceof String);
            assertEquals(Integer.class, ((Object) number).getClass());
            assertEquals(Boolean.class, ((Object) flag).getClass());
            assertTrue(list instanceof List);
            
            assertEquals("Hello World", text);
            assertEquals(42, number);
            assertTrue(flag);
            assertEquals(2, list.size());
        }
        
        @Test
        @DisplayName("不適切でない使用例が正しく動作する")
        public void inappropriateUsageTestShouldWork() {
            var inappropriate = new VarExamples.InappropriateVarUsage();
            
            assertDoesNotThrow(() -> {
                inappropriate.ambiguousTypes();
                inappropriate.unclearMethodChaining();
                inappropriate.invalidVarUsage();
            });
        }
    }
    
    @Nested
    @DisplayName("Var Best Practices Tests")
    class VarBestPracticesTest {
        
        @Test
        @DisplayName("varのベストプラクティスが正しく動作する")
        public void varBestPracticesShouldWork() {
            var bestPractices = new VarExamples.VarBestPractices();
            
            assertDoesNotThrow(() -> {
                bestPractices.descriptiveNaming();
                bestPractices.refactoringResilience();
                bestPractices.reviewGuidelines();
            });
        }
        
        @Test
        @DisplayName("varによる可読性向上の実証")
        public void varReadabilityImprovementDemo() {
            // 従来の書き方 vs var使用
            
            // 従来：冗長
            Map<String, List<String>> traditionalMap = new HashMap<String, List<String>>();
            List<String> traditionalList = new ArrayList<String>();
            LocalDateTime traditionalDateTime = LocalDateTime.now();
            
            // var使用：簡潔
            var modernMap = new HashMap<String, List<String>>();
            var modernList = new ArrayList<String>();
            var modernDateTime = LocalDateTime.now();
            
            // 両方とも同じ型であることを確認
            assertEquals(traditionalMap.getClass(), modernMap.getClass());
            assertEquals(traditionalList.getClass(), modernList.getClass());
            assertEquals(traditionalDateTime.getClass(), modernDateTime.getClass());
        }
        
        @Test
        @DisplayName("varによるリファクタリング耐性の実証")
        public void varRefactoringResilienceDemo() {
            // var使用により、メソッドの戻り値型が変更されても
            // 変数宣言部分は変更不要であることを実証
            
            var path1 = createPath("file.txt");        // Path型
            var path2 = createPathString("file.txt");   // String型
            
            // 異なる型でも変数宣言は同じvarで済む
            assertNotNull(path1);
            assertNotNull(path2);
            assertTrue(path1 instanceof Path);
            assertTrue(path2 instanceof String);
        }
        
        private Path createPath(String filename) {
            return Paths.get(filename);
        }
        
        private String createPathString(String filename) {
            return filename;
        }
    }
    
    @Nested
    @DisplayName("Var vs Traditional Comparison")
    class VarVsTraditionalComparisonTest {
        
        @Test
        @DisplayName("varと従来の書き方で同じ結果が得られる")
        public void varAndTraditionalShouldProduceSameResults() {
            // 従来の書き方
            List<String> traditionalList = new ArrayList<>();
            traditionalList.add("item1");
            traditionalList.add("item2");
            
            Map<String, Integer> traditionalMap = new HashMap<>();
            traditionalMap.put("key1", 1);
            traditionalMap.put("key2", 2);
            
            // var使用
            var modernList = new ArrayList<String>();
            modernList.add("item1");
            modernList.add("item2");
            
            var modernMap = new HashMap<String, Integer>();
            modernMap.put("key1", 1);
            modernMap.put("key2", 2);
            
            // 同じ結果であることを確認
            assertEquals(traditionalList, modernList);
            assertEquals(traditionalMap, modernMap);
            assertEquals(traditionalList.size(), modernList.size());
            assertEquals(traditionalMap.size(), modernMap.size());
        }
        
        @Test
        @DisplayName("varの使用により保守性が向上する")
        public void varImprovesMaintainability() {
            // このテストは概念的なもの
            // 実際の開発では以下のような恩恵がある：
            
            // 1. 型変更時の影響範囲が限定される
            var data = getData();  // List<String> から Set<String> に変更されても影響なし
            
            // 2. 複雑な型宣言が簡潔になる
            var complexData = getComplexData();  // 複雑なジェネリクス型でも簡潔
            
            // 3. IDEの支援により型安全性は保たれる
            assertNotNull(data);
            assertNotNull(complexData);
        }
        
        private List<String> getData() {
            return List.of("data1", "data2");
        }
        
        private Map<String, List<Integer>> getComplexData() {
            return Map.of("key", List.of(1, 2, 3));
        }
    }
    
    @Nested
    @DisplayName("Modern Java Features Integration")
    class ModernJavaFeaturesIntegrationTest {
        
        @Test
        @DisplayName("varとrecord、streamの組み合わせ")
        public void varWithRecordsAndStreams() {
            // recordの作成でvar使用
            var user1 = new VarExamples.ImprovedVarUsage.UserProfile("john@example.com", "John", true);
            var user2 = new VarExamples.ImprovedVarUsage.UserProfile("jane@company.com", "Jane", false);
            var user3 = new VarExamples.ImprovedVarUsage.UserProfile("bob@test.org", "Bob", true);
            
            // リスト作成でvar使用
            var users = List.of(user1, user2, user3);
            
            // ストリーム処理でvar使用
            var activeUsers = users.stream()
                .filter(VarExamples.ImprovedVarUsage.UserProfile::isActive)
                .toList();  // Java 16+
                
            var userDomains = users.stream()
                .map(VarExamples.ImprovedVarUsage.UserProfile::domain)
                .distinct()
                .toList();
            
            assertEquals(2, activeUsers.size());
            assertEquals(3, userDomains.size());
            assertTrue(userDomains.contains("example.com"));
            assertTrue(userDomains.contains("company.com"));
            assertTrue(userDomains.contains("test.org"));
        }
        
        @Test
        @DisplayName("varとtext blocks、switch expressionsの組み合わせ")
        public void varWithModernJavaFeatures() {
            // text blocks (Java 15+) でvar使用
            var jsonTemplate = """
                {
                    "name": "%s",
                    "email": "%s",
                    "active": %b
                }
                """;
            
            var user = new VarExamples.ImprovedVarUsage.UserProfile("john@example.com", "John", true);
            
            // if-else でvar使用（switch expressionは複雑になるため単純化）
            var status = user.isActive() ? "ACTIVE" : "INACTIVE";
            
            var json = jsonTemplate.formatted(user.name(), user.email(), user.isActive());
            
            assertNotNull(jsonTemplate);
            assertEquals("ACTIVE", status);
            assertTrue(json.contains("John"));
            assertTrue(json.contains("john@example.com"));
            assertTrue(json.contains("true"));
        }
    }
}