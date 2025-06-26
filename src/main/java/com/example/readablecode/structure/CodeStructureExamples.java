package com.example.readablecode.structure;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDateTime;

public class CodeStructureExamples {
    
    public static class BadStructure {
        public String processOrder(String customerId, List<String> items, String paymentMethod, String address, boolean priority) {
            if (customerId == null || customerId.isEmpty()) return "Invalid customer";
            if (items == null || items.isEmpty()) return "No items";
            double total = 0; for (String item : items) { if (item.equals("book")) total += 10; else if (item.equals("pen")) total += 2; else total += 5; }
            if (paymentMethod.equals("credit")) { if (total > 1000) return "Credit limit exceeded"; } else if (paymentMethod.equals("cash")) { } else return "Invalid payment method";
            String result = "Order processed for " + customerId + " with " + items.size() + " items. Total: $" + total;
            if (priority) result += " - PRIORITY ORDER"; return result;
        }
    }
    
    public static class GoodStructure {
        
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
        
        public static class OrderProcessor {
            private static final double CREDIT_LIMIT = 1000.0;
            
            public OrderProcessingResult processOrder(Order order) {
                Optional<String> validationError = validateOrder(order);
                if (validationError.isPresent()) {
                    return OrderProcessingResult.failure(validationError.get());
                }
                
                double totalAmount = calculateTotalAmount(order);
                
                Optional<String> paymentError = validatePayment(order.getPaymentMethod(), totalAmount);
                if (paymentError.isPresent()) {
                    return OrderProcessingResult.failure(paymentError.get());
                }
                
                String orderId = generateOrderId(order);
                return OrderProcessingResult.success(orderId, totalAmount);
            }
            
            private Optional<String> validateOrder(Order order) {
                if (order.getCustomerId() == null || order.getCustomerId().trim().isEmpty()) {
                    return Optional.of("Invalid customer ID");
                }
                
                if (order.getItems() == null || order.getItems().isEmpty()) {
                    return Optional.of("Order must contain at least one item");
                }
                
                if (order.getDeliveryAddress() == null || order.getDeliveryAddress().trim().isEmpty()) {
                    return Optional.of("Delivery address is required");
                }
                
                return Optional.empty();
            }
            
            private double calculateTotalAmount(Order order) {
                return order.getItems()
                           .stream()
                           .mapToDouble(OrderItem::getTotalPrice)
                           .sum();
            }
            
            private Optional<String> validatePayment(PaymentMethod paymentMethod, double amount) {
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
                        return Optional.of("Unsupported payment method");
                }
            }
            
            private Optional<String> validateCreditCardPayment(double amount) {
                if (amount > CREDIT_LIMIT) {
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