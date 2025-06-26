package com.example.readablecode.structure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class CodeStructureExamplesTest {
    
    @Nested
    @DisplayName("Order Tests")
    class OrderTest {
        
        @Test
        @DisplayName("注文オブジェクトが正しく作成される")
        public void shouldCreateOrderCorrectly() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 2));
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD,
                    "123 Main St",
                    true
                );
            
            assertEquals("CUST001", order.getCustomerId());
            assertEquals(1, order.getItems().size());
            assertEquals(CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD, order.getPaymentMethod());
            assertEquals("123 Main St", order.getDeliveryAddress());
            assertTrue(order.isPriority());
            assertNotNull(order.getCreatedAt());
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
            
            assertEquals("BOOK001", item.getProductId());
            assertEquals("Java Book", item.getName());
            assertEquals(25.99, item.getPrice(), 0.01);
            assertEquals(2, item.getQuantity());
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
            
            assertTrue(result.isSuccess());
            assertEquals("Order processed successfully", result.getMessage());
            assertEquals("ORD123", result.getOrderId());
            assertEquals(100.50, result.getTotalAmount(), 0.01);
        }
        
        @Test
        @DisplayName("失敗結果が正しく作成される")
        public void shouldCreateFailureResultCorrectly() {
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = 
                CodeStructureExamples.GoodStructure.OrderProcessingResult.failure("Invalid payment method");
            
            assertFalse(result.isSuccess());
            assertEquals("Invalid payment method", result.getMessage());
            assertNull(result.getOrderId());
            assertEquals(0.0, result.getTotalAmount(), 0.01);
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
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.DEBIT_CARD,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertTrue(result.isSuccess());
            assertNotNull(result.getOrderId());
            assertEquals(51.98, result.getTotalAmount(), 0.01);
        }
        
        @Test
        @DisplayName("無効な顧客IDで注文処理が失敗する")
        public void shouldFailForInvalidCustomerId() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 1));
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertFalse(result.isSuccess());
            assertEquals("Invalid customer ID", result.getMessage());
        }
        
        @Test
        @DisplayName("空のアイテムリストで注文処理が失敗する")
        public void shouldFailForEmptyItemsList() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertFalse(result.isSuccess());
            assertEquals("Order must contain at least one item", result.getMessage());
        }
        
        @Test
        @DisplayName("配送先住所なしで注文処理が失敗する")
        public void shouldFailForMissingDeliveryAddress() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("BOOK001", "Java Book", 25.99, 1));
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CASH,
                    "",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertFalse(result.isSuccess());
            assertEquals("Delivery address is required", result.getMessage());
        }
        
        @Test
        @DisplayName("クレジット限度額超過で注文処理が失敗する")
        public void shouldFailForCreditLimitExceeded() {
            List<CodeStructureExamples.GoodStructure.OrderItem> items = new ArrayList<>();
            items.add(new CodeStructureExamples.GoodStructure.OrderItem("EXPENSIVE", "Expensive Item", 1500.0, 1));
            
            CodeStructureExamples.GoodStructure.Order order = 
                new CodeStructureExamples.GoodStructure.Order(
                    "CUST001", 
                    items, 
                    CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD,
                    "123 Main St",
                    false
                );
            
            CodeStructureExamples.GoodStructure.OrderProcessingResult result = processor.processOrder(order);
            
            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("credit limit"));
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
            assertEquals("BOOK001", book.get().getId());
            assertEquals("Programming Book", book.get().getName());
            assertEquals(25.99, book.get().getPrice(), 0.01);
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
            
            assertEquals("CUST001", order.getCustomerId());
            assertEquals(2, order.getItems().size());
            assertEquals(CodeStructureExamples.GoodStructure.PaymentMethod.CREDIT_CARD, order.getPaymentMethod());
            assertEquals("123 Main St", order.getDeliveryAddress());
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
            
            assertEquals(1, order.getItems().size());
            assertEquals("BOOK001", order.getItems().get(0).getProductId());
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