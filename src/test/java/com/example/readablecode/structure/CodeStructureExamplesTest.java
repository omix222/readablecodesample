package com.example.readablecode.structure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

public class CodeStructureExamplesTest {
    
    @Nested
    @DisplayName("Order Tests")
    class OrderTest {
        
        @Test
        @DisplayName("注文レコードが正しく作成される")
        public void shouldCreateOrderCorrectly() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 2));
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD,
                    "123 Main St",
                    true,
                    LocalDateTime.now()
                );
            
            assertEquals("CUST001", order.customerId());
            assertEquals(1, order.items().size());
            assertEquals(CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD, order.paymentMethod());
            assertEquals("123 Main St", order.deliveryAddress());
            assertTrue(order.isPriority());
            assertNotNull(order.createdAt());
        }
    }
    
    @Nested
    @DisplayName("OrderItem Tests")
    class OrderItemTest {
        
        @Test
        @DisplayName("注文アイテムの合計価格が正しく計算される")
        public void shouldCalculateOrderItemTotalCorrectly() {
            CodeStructureExamples.GoodStructure.OrderItem item = 
                new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 3);
            
            assertEquals(77.97, item.getTotalPrice(), 0.01);
        }
        
        @Test
        @DisplayName("注文アイテムのプロパティが正しく取得される")
        public void shouldGetOrderItemPropertiesCorrectly() {
            CodeStructureExamples.GoodStructure.OrderItem item = 
                new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 2);
            
            assertEquals("BOOK001", item.productId());
            assertEquals("Java Book", item.name());
            assertEquals(25.99, item.price(), 0.01);
            assertEquals(2, item.quantity());
        }
    }
    
    @Nested
    @DisplayName("PaymentMethod Tests")
    class PaymentMethodTest {
        
        @Test
        @DisplayName("支払い方法のコードが正しく取得される")
        public void shouldGetPaymentMethodCodesCorrectly() {
            assertEquals("credit", CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD.getCode());
            assertEquals("debit", CodeStructureExamples.GoodStructure.PaymentMethod.DEBIT_CARD.getCode());
            assertEquals("cash", CodeStructureExamples.GoodStructure.PaymentMethod.CASH.getCode());
            assertEquals("transfer", CodeStructureExamples.GoodStructure.PaymentMethod.BANK_TRANSFER.getCode());
        }
    }
    
    @Nested
    @DisplayName("OrderProcessingResult Tests")
    class OrderProcessingResultTest {
        
        @Test
        @DisplayName("成功結果が正しく作成される")
        public void shouldCreateSuccessResultCorrectly() {
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = 
                CodeStructureExamples.GoodStructure.OrderProcessingResult.success("ORD123", 100.50);
            
            assertTrue(result.success());
            assertEquals("Order processed successfully", result.message());
            assertEquals("ORD123", result.orderId());
            assertEquals(100.50, result.totalAmount(), 0.01);
        }
        
        @Test
        @DisplayName("失敗結果が正しく作成される")
        public void shouldCreateFailureResultCorrectly() {
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = 
                CodeStructureExamples.GoodStructure.OrderProcessingResult.failure("Invalid payment method");
            
            assertFalse(result.success());
            assertEquals("Invalid payment method", result.message());
            assertNull(result.orderId());
            assertEquals(0.0, result.totalAmount(), 0.01);
        }
    }
    
    @Nested
    @DisplayName("OrderProcessor Tests")
    class OrderProcessorTest {
        
        private final CodeStructureExamples.GoodStructure.OrderProcessor processor = 
            new CodeStructureExamples.GoodStructure.OrderProcessor();
        
        @Test
        @DisplayName("有効な注文が正しく処理される")
        public void shouldProcessValidOrderSuccessfully() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 2));
            
            CodeStructureExamples.GoodStructure.Order order = 
                CodeStructureExamples.GoodStructure.Order.create(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.DEBIT_CARD,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertTrue(result.success());
            assertNotNull(result.orderId());
            assertEquals(51.98, result.totalAmount(), 0.01);
        }
        
        @Test
        @DisplayName("無効な顧客IDで注文作成が失敗する")
        public void shouldFailForInvalidCustomerId() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 1));
            
            // recordのコンパクトコンストラクタで入力検証されるため例外が発生
            assertThrows(IllegalArgumentException.class, () -> {
                CodeStructureExamples.GoodStructure.Order.create(
                    "", // 空の顧客ID
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "123 Main St",
                    false
                );
            });
        }
        
        @Test
        @DisplayName("空のアイテムリストで注文作成が失敗する")
        public void shouldFailForEmptyItemsList() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            
            // recordのコンパクトコンストラクタで入力検証されるため例外が発生
            assertThrows(IllegalArgumentException.class, () -> {
                CodeStructureExamples.GoodStructure.Order.create(
                    "CUST001", 
                    items, // 空のリスト
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "123 Main St",
                    false
                );
            });
        }
        
        @Test
        @DisplayName("配送先住所なしで注文作成が失敗する")
        public void shouldFailForMissingDeliveryAddress() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 1));
            
            // recordのコンパクトコンストラクタで入力検証されるため例外が発生
            assertThrows(IllegalArgumentException.class, () -> {
                CodeStructureExamples.GoodStructure.Order.create(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "", // 空の配送先住所
                    false
                );
            });
        }
        
        @Test
        @DisplayName("クレジット限度額超過で注文処理が失敗する")
        public void shouldFailForCreditLimitExceeded() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("EXPENSIVE", "Expensive Item", 1500.0, 1));
            
            CodeStructureExamples.GoodStructure.Order order = 
                CodeStructureExamples.GoodStructure.Order.create(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertFalse(result.success());
            assertTrue(result.message().contains("credit limit"));
        }
    }
    
    @Nested
    @DisplayName("ProductCatalog Tests")
    class ProductCatalogTest {
        
        @Test
        @DisplayName("商品が正しく見つかる")
        public void shouldFindProductsCorrectly() {
            Optional<CodeStructureExamples.GoodStructure.Product> book = 
                CodeStructureExamples.GoodStructure.ProductCatalog.findProduct("BOOK001");
            
            assertTrue(book.isPresent());
            assertEquals("BOOK001", book.get().id());
            assertEquals("Programming Book", book.get().name());
            assertEquals(25.99, book.get().price(), 0.01);
        }
        
        @Test
        @DisplayName("存在しない商品は見つからない")
        public void shouldReturnEmptyForNonexistentProduct() {
            Optional<CodeStructureExamples.GoodStructure.Product> product = 
                CodeStructureExamples.GoodStructure.ProductCatalog.findProduct("NONEXISTENT");
            
            assertFalse(product.isPresent());
        }
        
        @Test
        @DisplayName("全商品リストが正しく取得される")
        public void shouldGetAllProductsCorrectly() {
            List<CodeStructureExamples.GoodStructure.Product> products = 
                CodeStructureExamples.GoodStructure.ProductCatalog.getAllProducts();
            
            assertEquals(3, products.size());
        }
    }
    
    @Nested
    @DisplayName("OrderBuilder Tests")
    class OrderBuilderTest {
        
        @Test
        @DisplayName("OrderBuilderで注文が正しく作成される")
        public void shouldBuildOrderCorrectly() {
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.OrderBuilder()
                    .customerId("CUST001")
                    .addItem("BOOK001", 2)
                    .addItem("PEN001", 5)
                    .paymentMethod(CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD)
                    .deliveryAddress("123 Main St")
                    .priority()
                    .build();
            
            assertEquals("CUST001", order.customerId());
            assertEquals(2, order.items().size());
            assertEquals(CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD, order.paymentMethod());
            assertEquals("123 Main St", order.deliveryAddress());
            assertTrue(order.isPriority());
        }
        
        @Test
        @DisplayName("存在しない商品を追加しても無視される")
        public void shouldIgnoreNonexistentProducts() {
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.OrderBuilder()
                    .customerId("CUST001")
                    .addItem("NONEXISTENT", 1)
                    .addItem("BOOK001", 1)
                    .paymentMethod(CodeStructureExamples.GoodStructure.PaymentMethod.CASH)
                    .deliveryAddress("123 Main St")
                    .build();
            
            assertEquals(1, order.items().size());
            assertEquals("BOOK001", order.items().get(0).productId());
        }
    }
    
    @Nested
    @DisplayName("Bad vs Good Structure Comparison")
    class StructureComparisonTest {
        
        @Test
        @DisplayName("悪い構造のコードも動作する")
        public void badStructureCodeShouldStillWork() {
            CodeStructureExamples.BadStructure badStructure = 
                new CodeStructureExamples.BadStructure();
            
            List<String> items = new ArrayList<>();
            items.add("book");
            items.add("pen");
            
            String result = badStructure.processOrder("CUST001", items, "credit", "123 Main St", true);
            
            assertNotNull(result);
            assertTrue(result.contains("CUST001"));
            assertTrue(result.contains("PRIORITY ORDER"));
        }
    }
}