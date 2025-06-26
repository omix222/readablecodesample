package com.example.readablecode.comments;


public class CommentExamples {
    
    public static class BadComments {
        // increment i
        public void incrementCounter() {
            int i = 0;
            i++; // add 1 to i
        }
        
        // this method calculates something
        public int calculate(int x, int y) {
            return x * y + 10; // multiply x and y and add 10
        }
        
        // TODO: fix this later
        public boolean isValid(String input) {
            // check if input is not null
            if(input != null) {
                // check length
                if(input.length() > 0) {
                    return true; // return true
                }
            }
            return false; // return false
        }
    }
    
    public static class GoodComments {
        private static final int DEFAULT_RETRY_COUNT = 3;
        private static final int CONNECTION_TIMEOUT_MS = 5000;
        
        /**
         * Processes payment using external payment gateway.
         * Implements retry logic for transient failures.
         * 
         * @param amount Payment amount in cents to avoid floating point precision issues
         * @param cardToken Encrypted card token from payment processor
         * @return PaymentResult containing transaction ID or failure reason
         * @throws PaymentException if payment cannot be processed after all retries
         */
        public PaymentResult processPayment(long amount, String cardToken) throws PaymentException {
            validatePaymentAmount(amount);
            
            for (int attempt = 1; attempt <= DEFAULT_RETRY_COUNT; attempt++) {
                try {
                    return attemptPayment(amount, cardToken);
                } catch (TransientPaymentException e) {
                    if (attempt == DEFAULT_RETRY_COUNT) {
                        throw new PaymentException("Payment failed after " + DEFAULT_RETRY_COUNT + " attempts", e);
                    }
                    // Wait before retry to avoid overwhelming the payment service
                    waitBeforeRetry(attempt);
                }
            }
            
            throw new PaymentException("Unexpected error in payment processing");
        }
        
        /**
         * Calculates compound interest using the formula: A = P(1 + r/n)^(nt)
         * Where:
         * - A = final amount
         * - P = principal amount
         * - r = annual interest rate (as decimal, e.g., 0.05 for 5%)
         * - n = number of times interest is compounded per year
         * - t = time in years
         */
        public double calculateCompoundInterest(double principal, double annualRate, 
                                               int compoundingFrequency, int years) {
            if (principal <= 0 || annualRate < 0 || compoundingFrequency <= 0 || years < 0) {
                throw new IllegalArgumentException("Invalid input parameters for compound interest calculation");
            }
            
            double rate = annualRate / compoundingFrequency;
            double exponent = compoundingFrequency * years;
            
            return principal * Math.pow(1 + rate, exponent);
        }
        
        /**
         * Implements binary search to find target value in sorted array.
         * Time complexity: O(log n)
         * Space complexity: O(1)
         */
        public int binarySearch(int[] sortedArray, int target) {
            if (sortedArray == null || sortedArray.length == 0) {
                return -1;
            }
            
            int left = 0;
            int right = sortedArray.length - 1;
            
            while (left <= right) {
                // Prevent integer overflow when calculating middle index
                int mid = left + (right - left) / 2;
                
                if (sortedArray[mid] == target) {
                    return mid;
                } else if (sortedArray[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1; // Target not found
        }
        
        /**
         * Normalizes email address for consistent storage and comparison.
         * 
         * Business rules:
         * - Convert to lowercase for case-insensitive matching
         * - Remove dots from Gmail addresses (gmail ignores dots in usernames)
         * - Handle plus addressing (everything after + is ignored for Gmail)
         */
        public String normalizeEmail(String email) {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            String[] parts = email.toLowerCase().split("@");
            String username = parts[0];
            String domain = parts[1];
            
            // Apply Gmail-specific normalization rules
            if ("gmail.com".equals(domain)) {
                username = normalizeGmailUsername(username);
            }
            
            return username + "@" + domain;
        }
        
        private String normalizeGmailUsername(String username) {
            // Remove everything after '+' (plus addressing)
            int plusIndex = username.indexOf('+');
            if (plusIndex != -1) {
                username = username.substring(0, plusIndex);
            }
            
            // Remove all dots
            return username.replace(".", "");
        }
        
        // FIXME: This is a temporary workaround for issue #1234
        // The payment gateway sometimes returns inconsistent status codes
        // Remove this once the gateway API is fixed (scheduled for v2.1)
        private boolean isSuccessfulPayment(int statusCode) {
            return statusCode == 200 || statusCode == 201 || statusCode == 202;
        }
        
        private void validatePaymentAmount(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
        }
        
        private PaymentResult attemptPayment(long amount, String cardToken) throws TransientPaymentException {
            return new PaymentResult();
        }
        
        private void waitBeforeRetry(int attemptNumber) {
            try {
                // Exponential backoff: wait longer after each failed attempt
                Thread.sleep(1000 * attemptNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static class PaymentResult {
        private String transactionId;
        private boolean success;
        private String errorMessage;
        
        public String getTransactionId() { return transactionId; }
        public boolean isSuccess() { return success; }
        public String getErrorMessage() { return errorMessage; }
    }
    
    public static class PaymentException extends Exception {
        public PaymentException(String message) {
            super(message);
        }
        
        public PaymentException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    public static class TransientPaymentException extends Exception {
        public TransientPaymentException(String message) {
            super(message);
        }
    }
}