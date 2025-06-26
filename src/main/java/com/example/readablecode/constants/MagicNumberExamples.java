package com.example.readablecode.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * リーダブルコード 第2章「名前に情報を込める」(p.19-22)と第4章「美しさ」(p.44-46)のマジックナンバー対策サンプル
 * 
 * このクラスでは以下のマジックナンバーに関するノウハウを実装しています：
 * - マジックナンバーを避けて名前付き定数を使用 (p.19-20)
 * - 定数に意味のある名前を付ける (p.21-22)
 * - 関連する定数をグループ化する (p.44-46)
 * - 定数の値と意味を明確にする (p.20)
 * 
 * @version 1.0
 */
public class MagicNumberExamples {
    
    /**
     * 悪いマジックナンバーの例 - リーダブルコード p.19「数値リテラルの問題」
     * 
     * この関数の問題点：
     * - 数値の意味が不明 (p.19)
     * - 同じ値が複数箇所にある（重複） (p.20)
     * - 変更時に修正漏れが起きやすい (p.20)
     * - コードの意図が読み取れない (p.21)
     */
    public static class BadMagicNumbers {
        
        /**
         * 悪い例：マジックナンバーが多用されている (p.19)
         * - 60、24、7、100、18、65の意味が不明
         * - 同じ値が複数箇所に散らばっている
         */
        public boolean isValidUser(int age, double score, int loginAttempts) {
            // 悪い例：18の意味が不明 (成人年齢？)
            if (age < 18) {
                return false;
            }
            
            // 悪い例：65の意味が不明 (退職年齢？)
            if (age > 65) {
                return false;
            }
            
            // 悪い例：100の意味が不明 (満点？パーセンテージ？)
            if (score < 0 || score > 100) {
                return false;
            }
            
            // 悪い例：3の意味が不明 (試行回数の上限？)
            if (loginAttempts >= 3) {
                return false;
            }
            
            return true;
        }
        
        /**
         * 悪い例：時間計算でマジックナンバーを使用 (p.19-20)
         */
        public long calculateWorkingHoursInSeconds(int days) {
            // 悪い例：8, 60, 60の意味が分からない
            // 8時間 × 60分 × 60秒 × 日数だが、コードから読み取れない
            return days * 8 * 60 * 60;
        }
        
        /**
         * 悪い例：配列サイズやループでマジックナンバー使用 (p.20)
         */
        public List<String> generateReport() {
            List<String> report = new ArrayList<>();
            
            // 悪い例：10の意味が不明
            for (int i = 0; i < 10; i++) {
                report.add("Item " + i);
            }
            
            return report;
        }
        
        /**
         * 悪い例：ステータスコードをマジックナンバーで判定 (p.21)
         */
        public String getHttpStatusMessage(int statusCode) {
            // 悪い例：数値の意味が分からない
            if (statusCode == 200) {
                return "OK";
            } else if (statusCode == 404) {
                return "Not Found";
            } else if (statusCode == 500) {
                return "Internal Server Error";
            }
            return "Unknown";
        }
    }
    
    /**
     * 良いマジックナンバー対策の例 - リーダブルコード p.19-22「名前付き定数の活用」
     * 
     * この設計の改善点：
     * - 意味のある定数名で値の目的を明確化 (p.19-20)
     * - 関連する定数をグループ化 (p.21-22)
     * - 定数の値変更時の影響範囲を限定 (p.20)
     * - コードの意図を明確に表現 (p.21)
     */
    public static class GoodNamedConstants {
        
        // 良い例：年齢関連の定数をグループ化 (p.21-22)
        private static final int MINIMUM_AGE = 18;          // 成人年齢
        private static final int RETIREMENT_AGE = 65;       // 退職年齢
        
        // 良い例：スコア関連の定数を明確に定義 (p.19-20)
        private static final double MIN_SCORE = 0.0;       // 最小スコア
        private static final double MAX_SCORE = 100.0;     // 最大スコア（満点）
        
        // 良い例：セキュリティ関連の定数 (p.20)
        private static final int MAX_LOGIN_ATTEMPTS = 3;   // ログイン試行回数上限
        
        // 良い例：時間関連の定数を階層的に定義 (p.21-22)
        private static final int SECONDS_PER_MINUTE = 60;
        private static final int MINUTES_PER_HOUR = 60;
        private static final int WORK_HOURS_PER_DAY = 8;
        private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
        private static final int WORK_SECONDS_PER_DAY = WORK_HOURS_PER_DAY * SECONDS_PER_HOUR;
        
        // 良い例：レポート関連の定数 (p.20)
        private static final int DEFAULT_REPORT_ITEMS = 10; // デフォルトレポート項目数
        
        /**
         * 良い例：名前付き定数で意図を明確化 (p.19-20)
         * 各条件の意味が定数名から明確に理解できる
         */
        public boolean isValidUser(int age, double score, int loginAttempts) {
            // 良い例：定数名から「成人年齢未満かチェック」が明確
            if (age < MINIMUM_AGE) {
                return false;
            }
            
            // 良い例：定数名から「退職年齢超過かチェック」が明確
            if (age > RETIREMENT_AGE) {
                return false;
            }
            
            // 良い例：定数名からスコア範囲チェックの意図が明確
            if (score < MIN_SCORE || score > MAX_SCORE) {
                return false;
            }
            
            // 良い例：定数名からログイン試行回数制限の意図が明確
            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                return false;
            }
            
            return true;
        }
        
        /**
         * 良い例：時間計算で名前付き定数を使用 (p.21-22)
         * 計算式の意味が定数名から明確に理解できる
         */
        public long calculateWorkingHoursInSeconds(int days) {
            // 良い例：定数名から「日数 × 1日の労働秒数」が明確
            return days * WORK_SECONDS_PER_DAY;
        }
        
        /**
         * 良い例：配列サイズを名前付き定数で明確化 (p.20)
         */
        public List<String> generateReport() {
            return generateReport(DEFAULT_REPORT_ITEMS);
        }
        
        /**
         * 良い例：パラメータでサイズを指定可能、デフォルト値は定数 (p.20)
         */
        public List<String> generateReport(int itemCount) {
            List<String> report = new ArrayList<>();
            
            // 良い例：ループ条件の意味が明確
            for (int i = 0; i < itemCount; i++) {
                report.add("Item " + i);
            }
            
            return report;
        }
    }
    
    /**
     * HTTPステータスコード定数の例 - リーダブルコード p.21-22「関連定数のグループ化」
     * 
     * この設計のメリット：
     * - ステータスコードの意味が名前から明確 (p.21)
     * - 関連する定数を一箇所にまとめて管理 (p.22)
     * - タイポやコード間違いを防止 (p.20)
     */
    public static class HttpStatusCodes {
        // 良い例：成功レスポンス系 (p.21-22)
        public static final int OK = 200;
        public static final int CREATED = 201;
        public static final int ACCEPTED = 202;
        public static final int NO_CONTENT = 204;
        
        // 良い例：クライアントエラー系 (p.21-22)
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        
        // 良い例：サーバーエラー系 (p.21-22)
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        
        /**
         * 良い例：名前付き定数でHTTPステータスを判定 (p.21)
         * ステータスコードの意味が定数名から明確
         */
        public String getHttpStatusMessage(int statusCode) {
            // 良い例：定数名からレスポンスの意味が明確
            if (statusCode == OK) {
                return "正常に処理されました";
            } else if (statusCode == NOT_FOUND) {
                return "リソースが見つかりません";
            } else if (statusCode == INTERNAL_SERVER_ERROR) {
                return "サーバー内部エラーが発生しました";
            }
            return "不明なステータスコード";
        }
        
        /**
         * 良い例：ステータスコード範囲での判定 (p.22)
         * 範囲判定も定数で明確化
         */
        public boolean isSuccessStatus(int statusCode) {
            return statusCode >= OK && statusCode < 300;
        }
        
        public boolean isClientError(int statusCode) {
            return statusCode >= BAD_REQUEST && statusCode < 500;
        }
        
        public boolean isServerError(int statusCode) {
            return statusCode >= INTERNAL_SERVER_ERROR && statusCode < 600;
        }
    }
    
    /**
     * 設定値定数の例 - リーダブルコード p.20「設定値の集約」
     * 
     * アプリケーション設定に関わる定数を一箇所に集約
     */
    public static class ApplicationConfig {
        // データベース関連設定 (p.20)
        public static final int DEFAULT_CONNECTION_POOL_SIZE = 10;
        public static final int MAX_CONNECTION_POOL_SIZE = 50;
        public static final int CONNECTION_TIMEOUT_SECONDS = 30;
        
        // キャッシュ関連設定 (p.20)
        public static final int CACHE_EXPIRY_MINUTES = 60;
        public static final int MAX_CACHE_SIZE = 1000;
        
        // ファイル処理関連設定 (p.20)
        public static final int MAX_FILE_SIZE_MB = 10;
        public static final int BUFFER_SIZE_BYTES = 8192;  // 8KB
        
        // セキュリティ関連設定 (p.20)
        public static final int PASSWORD_MIN_LENGTH = 8;
        public static final int SESSION_TIMEOUT_MINUTES = 30;
        public static final int TOKEN_EXPIRY_HOURS = 24;
    }
}