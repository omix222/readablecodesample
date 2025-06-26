package com.example.readablecode.structure;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDateTime;

/**
 * リーダブルコード 第7章「制御フローを読みやすくする」(p.71-86)と第8章「巨大な式を分割する」(p.87-100)のサンプルコード集
 * 
 * このクラスでは以下のコード構造に関するノウハウを実装しています：
 * - 条件式の順序と可読性 (p.73-75)
 * - if/elseブロックの順序 (p.76-78)
 * - 三項演算子の適切な使用 (p.79-81)
 * - do/whileループを避ける (p.82-84)
 * - 関数から早く返す (p.85-86)
 * - 説明変数の活用 (p.89-91)
 * - 要約変数の使用 (p.92-94)
 * - ド・モルガンの法則の活用 (p.95-97)
 * - 巨大な式の分割 (p.98-100)
 * 
 * @author Readable Code Examples
 * @version 1.0
 */
public class CodeStructureExamples {
    
    /**
     * 悪いコード構造の例 - リーダブルコード p.82-84「制御フローの問題」と p.98-100「巨大な式の問題」
     * 
     * この関数の問題点：
     * - 一行に複数の処理を詰め込んでいる (p.98)
     * - ネストが深い条件分岐 (p.82)
     * - 早期returnのガード句を適切に使っていない (p.85)
     * - 複雑な計算ロジックが混在している (p.89)
     * - 巨大な式で可読性が損なわれている (p.100)
     */
    public static class BadStructure {
        
        /**
         * 悪い例：制御フローが複雑で読みにくい関数 (p.82-84, p.98-100)
         * - 一行に複数の処理
         * - 複雑な条件分岐
         * - 巨大な式
         */
        public String processOrder(String customerId, List<String> items, String paymentMethod, String address, boolean priority) {
            // 悪い例：早期returnを使わず条件が複雑 (p.85-86)
            if (customerId == null || customerId.isEmpty()) return "Invalid customer";
            if (items == null || items.isEmpty()) return "No items";
            
            // 悪い例：一行に複数の処理を詰め込み、可読性を損なう (p.98)
            double total = 0; for (String item : items) { if (item.equals("book")) total += 10; else if (item.equals("pen")) total += 2; else total += 5; }
            
            // 悪い例：ネストが深く、条件分岐が複雑 (p.82-84)
            if (paymentMethod.equals("credit")) { if (total > 1000) return "Credit limit exceeded"; } else if (paymentMethod.equals("cash")) { } else return "Invalid payment method";
            
            // 悪い例：巨大な式で文字列を構築 (p.100)
            String result = "Order processed for " + customerId + " with " + items.size() + " items. Total: $" + total;
            if (priority) result += " - PRIORITY ORDER"; return result;
        }
    }
    
    /**
     * 良いコード構造の例 - リーダブルコード p.73-75「条件式の順序」と p.89-91「説明変数の活用」
     * 
     * この設計の改善点：
     * - 適切なクラス設計で責任を分離 (p.73)
     * - 説明変数で複雑な式を分割 (p.89-91)
     * - 要約変数で可読性向上 (p.92-94)
     * - Optional型でnull安全性を確保 (p.76)
     * - Enumで型安全性を向上 (p.78)
     * - 早期returnでネストを軽減 (p.85-86)
     */
    public static class GoodStructure {
        
        /**
         * 良い例：適切な責任分離を行った注文クラス (p.73「データ構造の整理」)
         * - 関連するデータをまとめる
         * - 不変オブジェクトで副作用を防ぐ
         * - 明確な状態管理
         */
        public static class Order {
            private final String customerId;
            private final List<OrderItem> items;
            private final PaymentMethod paymentMethod;
            private final String deliveryAddress;
            private final boolean isPriority;
            private final LocalDateTime createdAt;
            
            public Order(String customerId, List<OrderItem> items, PaymentMethod paymentMethod, 
                        String deliveryAddress, boolean isPriority) {
                this.customerId = customerId;
                this.items = new ArrayList<>(items);
                this.paymentMethod = paymentMethod;
                this.deliveryAddress = deliveryAddress;
                this.isPriority = isPriority;
                this.createdAt = LocalDateTime.now();
            }
            
            public String getCustomerId() { return customerId; }
            public List<OrderItem> getItems() { return new ArrayList<>(items); }
            public PaymentMethod getPaymentMethod() { return paymentMethod; }
            public String getDeliveryAddress() { return deliveryAddress; }
            public boolean isPriority() { return isPriority; }
            public LocalDateTime getCreatedAt() { return createdAt; }
        }
        
        public static class OrderItem {
            private final String productId;
            private final String name;
            private final double price;
            private final int quantity;
            
            public OrderItem(String productId, String name, double price, int quantity) {
                this.productId = productId;
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }
            
            public double getTotalPrice() {
                return price * quantity;
            }
            
            public String getProductId() { return productId; }
            public String getName() { return name; }
            public double getPrice() { return price; }
            public int getQuantity() { return quantity; }
        }
        
        public enum PaymentMethod {
            CREDIT_CARD("credit"),
            DEBIT_CARD("debit"),
            CASH("cash"),
            BANK_TRANSFER("transfer");
            
            private final String code;
            
            PaymentMethod(String code) {
                this.code = code;
            }
            
            public String getCode() {
                return code;
            }
        }
        
        public static class OrderProcessingResult {
            private final boolean success;
            private final String message;
            private final String orderId;
            private final double totalAmount;
            
            private OrderProcessingResult(boolean success, String message, String orderId, double totalAmount) {
                this.success = success;
                this.message = message;
                this.orderId = orderId;
                this.totalAmount = totalAmount;
            }
            
            public static OrderProcessingResult success(String orderId, double totalAmount) {
                return new OrderProcessingResult(true, "Order processed successfully", orderId, totalAmount);
            }
            
            public static OrderProcessingResult failure(String message) {
                return new OrderProcessingResult(false, message, null, 0.0);
            }
            
            public boolean isSuccess() { return success; }
            public String getMessage() { return message; }
            public String getOrderId() { return orderId; }
            public double getTotalAmount() { return totalAmount; }
        }
        
        /**
         * 良い例：注文処理の責任を単一に絞ったプロセッサ (p.73「責任の分離」)
         * - 検証、計算、ID生成の各処理を明確に分離
         * - Optional型でエラーハンドリングを明確化 (p.76)
         * - 早期returnでネストを軽減 (p.85-86)
         */
        public static class OrderProcessor {
            // 良い例：定数の意味を明確に示す (p.89「説明変数」)
            private static final double CREDIT_LIMIT = 1000.0; // クレジット決済の上限額
            
            /**
             * 注文を処理して結果を返します。 (p.85-86「早期returnの活用」)
             * 
             * 処理フロー：
             * 1. 注文データの検証
             * 2. 合計金額の計算
             * 3. 決済方法の検証
             * 4. 注文IDの生成
             * 
             * @param order 処理対象の注文
             * @return 処理結果（成功/失敗）
             */
            public OrderProcessingResult processOrder(Order order) {
                // 良い例：早期returnでエラーケースを先に処理 (p.85-86)
                // → メインロジックのネストを浅くできる
                Optional<String> validationError = validateOrder(order);
                if (validationError.isPresent()) {
                    return OrderProcessingResult.failure(validationError.get());
                }
                
                // 良い例：説明変数で処理の意図を明確化 (p.89-91)
                double totalAmount = calculateTotalAmount(order);
                
                // 良い例：Optional型でエラーの有無を明確に表現 (p.76)
                Optional<String> paymentError = validatePayment(order.getPaymentMethod(), totalAmount);
                if (paymentError.isPresent()) {
                    return OrderProcessingResult.failure(paymentError.get());
                }
                
                // 正常ケース：すべての検証が通った場合のみ実行
                String orderId = generateOrderId(order);
                return OrderProcessingResult.success(orderId, totalAmount);
            }
            
            /**
             * 注文データの妥当性を検証します。 (p.85-86「早期returnの活用」)
             * 
             * @param order 検証対象の注文
             * @return エラーメッセージ（問題がある場合）、正常な場合は空のOptional
             */
            private Optional<String> validateOrder(Order order) {
                // 良い例：最も基本的な検証から順番に実行 (p.73-75「条件式の順序」)
                // 顧客ID → 商品 → 配送先の順で重要度順に検証
                
                if (order.getCustomerId() == null || order.getCustomerId().trim().isEmpty()) {
                    return Optional.of("Invalid customer ID");
                }
                
                if (order.getItems() == null || order.getItems().isEmpty()) {
                    return Optional.of("Order must contain at least one item");
                }
                
                if (order.getDeliveryAddress() == null || order.getDeliveryAddress().trim().isEmpty()) {
                    return Optional.of("Delivery address is required");
                }
                
                // 良い例：正常ケースは最後に明示的に返す (p.76)
                return Optional.empty();
            }
            
            /**
             * 注文の合計金額を計算します。 (p.92-94「要約変数の使用」)
             * 
             * @param order 計算対象の注文
             * @return 合計金額
             */
            private double calculateTotalAmount(Order order) {
                // 良い例：Stream APIで関数型プログラミング的に記述 (p.92)
                // 各商品の合計価格を取得し、それらを合計する処理が明確
                return order.getItems()
                           .stream()
                           .mapToDouble(OrderItem::getTotalPrice)
                           .sum();
            }
            
            /**
             * 決済方法と金額の妥当性を検証します。 (p.78-81「switch文の適切な使用」)
             * 
             * @param paymentMethod 決済方法
             * @param amount 決済金額
             * @return エラーメッセージ（問題がある場合）、正常な場合は空のOptional
             */
            private Optional<String> validatePayment(PaymentMethod paymentMethod, double amount) {
                // 良い例：Enumを使ったswitch文で型安全性を確保 (p.78-81)
                // 新しい決済方法が追加されたときにコンパイルエラーで気づける
                switch (paymentMethod) {
                    case CREDIT_CARD:
                        return validateCreditCardPayment(amount);
                    case DEBIT_CARD:
                        return validateDebitCardPayment(amount);
                    case CASH:
                        return validateCashPayment(amount);
                    case BANK_TRANSFER:
                        return validateBankTransferPayment(amount);
                    default:
                        // 良い例：想定外のケースも明示的に処理 (p.81)
                        return Optional.of("Unsupported payment method");
                }
            }
            
            /**
             * クレジットカード決済の妥当性を検証します。 (p.89-91「説明変数の活用」)
             */
            private Optional<String> validateCreditCardPayment(double amount) {
                // 良い例：説明変数で条件の意味を明確化 (p.89-91)
                boolean exceedsCreditLimit = amount > CREDIT_LIMIT;
                
                if (exceedsCreditLimit) {
                    return Optional.of("Amount exceeds credit limit of $" + CREDIT_LIMIT);
                }
                return Optional.empty();
            }
            
            private Optional<String> validateDebitCardPayment(double amount) {
                return Optional.empty();
            }
            
            private Optional<String> validateCashPayment(double amount) {
                return Optional.empty();
            }
            
            private Optional<String> validateBankTransferPayment(double amount) {
                return Optional.empty();
            }
            
            private String generateOrderId(Order order) {
                return "ORD-" + System.currentTimeMillis() + "-" + order.getCustomerId().hashCode();
            }
        }
        
        public static class ProductCatalog {
            private static final Map<String, Product> PRODUCTS = new HashMap<>();
            
            static {
                PRODUCTS.put("BOOK001", new Product("BOOK001", "Programming Book", 25.99));
                PRODUCTS.put("PEN001", new Product("PEN001", "Blue Pen", 1.99));
                PRODUCTS.put("NOTEBOOK001", new Product("NOTEBOOK001", "A4 Notebook", 5.99));
            }
            
            public static Optional<Product> findProduct(String productId) {
                return Optional.ofNullable(PRODUCTS.get(productId));
            }
            
            public static List<Product> getAllProducts() {
                return new ArrayList<>(PRODUCTS.values());
            }
        }
        
        public static class Product {
            private final String id;
            private final String name;
            private final double price;
            
            public Product(String id, String name, double price) {
                this.id = id;
                this.name = name;
                this.price = price;
            }
            
            public String getId() { return id; }
            public String getName() { return name; }
            public double getPrice() { return price; }
        }
        
        public static class OrderBuilder {
            private String customerId;
            private List<OrderItem> items = new ArrayList<>();
            private PaymentMethod paymentMethod;
            private String deliveryAddress;
            private boolean isPriority = false;
            
            public OrderBuilder customerId(String customerId) {
                this.customerId = customerId;
                return this;
            }
            
            public OrderBuilder addItem(String productId, int quantity) {
                Optional<Product> product = ProductCatalog.findProduct(productId);
                if (product.isPresent()) {
                    Product p = product.get();
                    items.add(new OrderItem(p.getId(), p.getName(), p.getPrice(), quantity));
                }
                return this;
            }
            
            public OrderBuilder paymentMethod(PaymentMethod paymentMethod) {
                this.paymentMethod = paymentMethod;
                return this;
            }
            
            public OrderBuilder deliveryAddress(String address) {
                this.deliveryAddress = address;
                return this;
            }
            
            public OrderBuilder priority() {
                this.isPriority = true;
                return this;
            }
            
            public Order build() {
                return new Order(customerId, items, paymentMethod, deliveryAddress, isPriority);
            }
        }
    }
}