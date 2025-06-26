package com.example.readablecode.comments;

/**
 * リーダブルコード 第5章「コメントすべきことを知る」(p.51-60)と第6章「コメントは正確で簡潔に」(p.61-70)のサンプルコード集
 * 
 * このクラスでは以下のコメントに関するノウハウを実装しています：
 * - コメントすべきことと、すべきでないことの区別 (p.53-55)
 * - 自分の思考を記録する (p.56-58)
 * - 読み手の立場になって考える (p.59-60)
 * - ライターズブロックを乗り越える (p.62-64)
 * - 要約コメントの活用 (p.65-67)
 * - 曖昧な代名詞を避ける (p.68-70)
 * 
 * @author Readable Code Examples
 * @version 1.0
 */
public class CommentExamples {
    
    /**
     * 悪いコメントの例 - リーダブルコード p.53-55「コメントすべきでないこと」
     * 
     * このクラスでは以下の問題のあるコメントを示します：
     * - コードと同じことを繰り返すコメント (p.53)
     * - 意味のない説明コメント (p.54)
     * - 関数名やコードから明らかなことのコメント (p.55)
     */
    public static class BadComments {
        
        /**
         * 悪い例：コードと同じことを繰り返すだけのコメント (p.53)
         * incrementCounterという関数名から処理内容は明らか
         */
        // increment i
        public void incrementCounter() {
            int i = 0;
            i++; // add 1 to i - 悪い例：コードを読めば分かることをコメント
        }
        
        /**
         * 悪い例：曖昧で意味のないコメント (p.54)
         * 「何かを計算する」では何の情報も提供していない
         */
        // this method calculates something
        public int calculate(int x, int y) {
            return x * y + 10; // multiply x and y and add 10 - 悪い例：計算式をそのまま文章化
        }
        
        /**
         * 悪い例：意味のないTODOと自明なコメント (p.55)
         * - 「後で修正」は具体性がない
         * - if文の内容をそのまま説明している
         */
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
    
    /**
     * 良いコメントの例 - リーダブルコード p.56-58「自分の思考を記録する」と p.59-60「読み手の立場になって考える」
     * 
     * このクラスでは以下の有効なコメント技法を実装しています：
     * - なぜその決定をしたのかを記録 (p.56)
     * - コードの欠陥や改善点を明記 (p.57)
     * - 定数の意味や理由を説明 (p.58)
     * - 複雑なアルゴリズムの動作原理を説明 (p.59)
     * - ビジネスルールの詳細を記録 (p.60)
     */
    public static class GoodComments {
        // 良い例：定数の意味と根拠を説明 (p.58)
        private static final int DEFAULT_RETRY_COUNT = 3; // 一般的なネットワーク障害は3回以内に回復することが多い
        private static final int CONNECTION_TIMEOUT_MS = 5000; // UX調査により5秒が許容限界と判明
        
        /**
         * 外部決済ゲートウェイを使用して決済を処理します。 (p.56「なぜその決定をしたのか」)
         * 一時的な障害に対するリトライロジックを実装しています。
         * 
         * 設計判断の理由：
         * - 金額をセント単位で処理：浮動小数点の精度問題を回避 (p.56)
         * - 暗号化トークン使用：PCI DSS準拠のためカード情報を直接扱わない (p.57)
         * - リトライ回数制限：無限ループ防止とサービス保護 (p.57)
         * 
         * @param amount 決済金額（セント単位で指定して精度問題を回避）
         * @param cardToken 決済プロセッサからの暗号化カードトークン
         * @return 取引IDまたは失敗理由を含むPaymentResult
         * @throws PaymentException 全リトライ後に決済処理できない場合
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
                    // 良い例：なぜ待機するのかの理由を説明 (p.59「読み手の立場になって考える」)
                    // 決済サービスへの負荷軽減のためリトライ前に待機
                    waitBeforeRetry(attempt);
                }
            }
            
            throw new PaymentException("Unexpected error in payment processing");
        }
        
        /**
         * 複利を計算します。 (p.59「複雑なアルゴリズムの動作原理を説明」)
         * 
         * 使用する数式：A = P(1 + r/n)^(nt)
         * 各変数の意味：
         * - A = 最終金額
         * - P = 元本
         * - r = 年利率（小数点表記、例：5%なら0.05）
         * - n = 年間複利計算回数
         * - t = 年数
         * 
         * この公式は金融業界の標準的な複利計算式です。 (p.60「ビジネスルールの詳細」)
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
         * ソート済み配列から二分探索で目標値を検索します。 (p.59「アルゴリズムの動作原理」)
         * 
         * アルゴリズムの詳細：
         * - 配列の中央値と目標値を比較
         * - 目標値が小さければ左半分、大きければ右半分を探索
         * - 見つかるまで範囲を半分ずつ狭めていく
         * 
         * 計算量： (p.59「技術的詳細の記録」)
         * - 時間計算量：O(log n) - 毎回探索範囲が半分になるため
         * - 空間計算量：O(1) - 追加メモリを使わない
         */
        public int binarySearch(int[] sortedArray, int target) {
            if (sortedArray == null || sortedArray.length == 0) {
                return -1;
            }
            
            int left = 0;
            int right = sortedArray.length - 1;
            
            while (left <= right) {
                // 良い例：なぜこの計算式を使うのかを説明 (p.56「設計判断の理由」)
                // (left + right) / 2 ではなく left + (right - left) / 2 を使用
                // → 大きな配列でのinteger overflowを防ぐため
                int mid = left + (right - left) / 2;
                
                if (sortedArray[mid] == target) {
                    return mid;
                } else if (sortedArray[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1; // 良い例：戻り値の意味を明記 (p.60「読み手への配慮」)
        }
        
        /**
         * メールアドレスを正規化して一貫した保存・比較を可能にします。 (p.60「ビジネスルールの詳細」)
         * 
         * 適用するビジネスルール：
         * - 小文字変換：大文字小文字を区別しないマッチングのため
         * - Gmailのドット除去：Gmailはユーザー名のドットを無視するため
         * - プラスアドレシング処理：Gmailでは+以降は無視される仕様のため
         * 
         * これらのルールは各メールプロバイダの公式仕様に基づいています。 (p.60)
         */
        public String normalizeEmail(String email) {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            String[] parts = email.toLowerCase().split("@");
            String username = parts[0];
            String domain = parts[1];
            
            // 良い例：特定の処理の理由を説明 (p.60「ビジネスルール」)
            // Gmailのみ特別な正規化ルールを適用
            // 他のプロバイダは独自ルールが異なるため個別対応は行わない
            if ("gmail.com".equals(domain)) {
                username = normalizeGmailUsername(username);
            }
            
            return username + "@" + domain;
        }
        
        /**
         * Gmailユーザー名の正規化を行います。 (p.60「プロバイダ固有ルール」)
         */
        private String normalizeGmailUsername(String username) {
            // プラスアドレシング除去：Gmail仕様により+以降は無視される
            int plusIndex = username.indexOf('+');
            if (plusIndex != -1) {
                username = username.substring(0, plusIndex);
            }
            
            // ドット除去：Gmail仕様によりユーザー名のドットは無視される
            return username.replace(".", "");
        }
        
        // 良い例：具体的で修正可能なFIXME (p.57「コードの欠陥を明記」)
        // FIXME: 決済ゲートウェイAPI v2.0の不整合ステータスコード問題の一時的回避策
        // 問題詳細：ステータス200/201/202が混在して返される（本来は200のみ）
        // 修正予定：ゲートウェイAPI v2.1で修正予定（2024年Q2リリース予定）
        // 課題番号：#1234
        private boolean isSuccessfulPayment(int statusCode) {
            return statusCode == 200 || statusCode == 201 || statusCode == 202;
        }
        
        private void validatePaymentAmount(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
        }
        
        private PaymentResult attemptPayment(long amount, String cardToken) throws TransientPaymentException {
            // 実際の決済処理のシミュレーション
            String transactionId = "TX-" + System.currentTimeMillis();
            return PaymentResult.success(transactionId);
        }
        
        /**
         * リトライ前の待機時間を指数バックオフで制御します。 (p.59「アルゴリズムの説明」)
         */
        private void waitBeforeRetry(int attemptNumber) {
            try {
                // 指数バックオフ：失敗するたびに待機時間を長くする
                // 1回目1秒、2回目2秒、3回目3秒...でサービス負荷を軽減
                Thread.sleep(1000 * attemptNumber);
            } catch (InterruptedException e) {
                // 良い例：なぜinterrupt状態を復元するのかを説明 (p.59)
                // スレッドプールなど上位の仕組みに割り込み状態を伝播
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * 良い例：Java 17のrecordで決済結果データを表現 (Modern Java)
     * - 決済処理の結果を不変オブジェクトとして安全に保持
     * - equals、hashCode、toStringが自動生成される
     * - ボイラープレートコードの削減
     */
    public record PaymentResult(
        String transactionId,
        boolean success,
        String errorMessage
    ) {
        /**
         * 成功した決済結果を作成するファクトリメソッド
         */
        public static PaymentResult success(String transactionId) {
            return new PaymentResult(transactionId, true, null);
        }
        
        /**
         * 失敗した決済結果を作成するファクトリメソッド
         */
        public static PaymentResult failure(String errorMessage) {
            return new PaymentResult(null, false, errorMessage);
        }
        
        /**
         * 決済が成功したかどうかを判定
         */
        public boolean isFailure() {
            return !success;
        }
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