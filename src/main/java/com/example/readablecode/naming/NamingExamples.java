package com.example.readablecode.naming;

import java.time.LocalDate;

/**
 * リーダブルコード 第2章「名前に情報を込める」(p.9-30)のサンプルコード集
 * 
 * このクラスでは以下の命名に関するノウハウを実装しています：
 * - 明確で具体的な名前の使用 (p.11-13)
 * - 汎用的な名前の回避 (p.14-16)
 * - 抽象的でない具体的な名前の使用 (p.17-18)
 * - 情報を名前に含める (p.19-22)
 * - 名前の長さと情報量のバランス (p.23-25)
 * - 誤解されない名前の選択 (p.26-30)
 * 
 * @author Readable Code Examples
 * @version 1.0
 */
public class NamingExamples {
    
    /**
     * 悪い命名例 - リーダブルコード p.14-16「汎用的な名前を避ける」
     * 
     * このクラスは以下の問題を示します：
     * - 抽象的で曖昧な名前の使用
     * - メソッドの目的が名前から分からない
     * - パラメータ名が単文字や汎用的
     */
    public static class BadNaming {
        
        // 悪い例：「calc」は何を計算するのか不明 (p.14)
        public int calc(int x) {
            return x * 2 + 1;
        }
        
        // 悪い例：「check」は何をチェックするのか不明 (p.15)
        public boolean check(String s) {
            return s != null && s.length() > 0;
        }
        
        // 悪い例：「process」は何を処理するのか不明 (p.16)
        public String process(String data) {
            return data.trim().toLowerCase();
        }
    }
    
    /**
     * 良い命名例 - リーダブルコード p.11-13「明確で具体的な名前を使う」
     * 
     * このクラスでは以下の原則を実装しています：
     * - 動詞と目的語を含む具体的なメソッド名
     * - 意図が明確なパラメータ名
     * - boolean型メソッドにはisやhasなどの接頭語を使用
     */
    public static class GoodNaming {
        
        /**
         * 良い例：calculateTotalPriceは「合計価格を計算する」ことが明確 (p.11)
         * @param basePrice 基本価格
         * @return 計算された合計価格
         */
        public int calculateTotalPrice(int basePrice) {
            return basePrice * 2 + 1;
        }
        
        /**
         * 良い例：isValidEmailは「メールアドレスが有効かチェック」することが明確 (p.12)
         * @param email チェックするメールアドレス
         * @return 有効な場合true
         */
        public boolean isValidEmail(String email) {
            return email != null && email.length() > 0 && email.contains("@");
        }
        
        /**
         * 良い例：normalizeUserInputは「ユーザー入力を正規化」することが明確 (p.13)
         * @param userInput 正規化するユーザー入力
         * @return 正規化された文字列
         */
        public String normalizeUserInput(String userInput) {
            return userInput.trim().toLowerCase();
        }
    }
    
    /**
     * リーダブルコード p.19-22「名前に情報を含める」の実装例
     * 
     * このクラスでは以下の命名技法を使用しています：
     * - 単位や状態を名前に含める（Attempts、Active）
     * - 時間的な概念を明確にする（createdAt、Since）
     * - boolean型の状態を明確にする（isActive）
     * - 動作の結果を明確にする（record、reset）
     */
    public static class UserAccount {
        // 情報を含む命名：userIdは「ユーザーの識別子」を明確に示す (p.19)
        private String userId;
        private String email;
        
        // 時間的概念を含む：createdAtは「作成された時刻」を示す (p.20)
        private LocalDate createdAt;
        
        // 状態を含む：isActiveは「アクティブ状態かどうか」を示す (p.21)
        private boolean isActive;
        
        // 数値の種類を含む：loginAttemptsは「ログイン試行回数」を示す (p.22)
        private int loginAttempts;
        
        /**
         * ユーザーアカウントを作成します
         * @param userId ユーザー識別子
         * @param email メールアドレス
         */
        public UserAccount(String userId, String email) {
            this.userId = userId;
            this.email = email;
            this.createdAt = LocalDate.now();
            this.isActive = true;
            this.loginAttempts = 0;
        }
        
        /**
         * ログイン試行が可能かどうかを判定 (p.26-30「誤解されない名前」)
         * canは「能力・可能性」を明確に示す
         * @return ログイン試行可能な場合true
         */
        public boolean canAttemptLogin() {
            return isActive && loginAttempts < 3;
        }
        
        /**
         * ログイン失敗を記録 (p.19「動作を名前に含める」)
         * recordは「記録する」という動作を明確に示す
         */
        public void recordFailedLogin() {
            loginAttempts++;
            if (loginAttempts >= 3) {
                isActive = false;
            }
        }
        
        /**
         * ログイン試行回数をリセット (p.19「動作を名前に含める」)
         * resetは「リセットする」という動作を明確に示す
         */
        public void resetLoginAttempts() {
            loginAttempts = 0;
        }
        
        /**
         * アカウント作成からの日数を取得 (p.20「時間的概念を名前に含める」)
         * getDaysSinceは「〜からの日数を取得」を明確に示す
         * @return 作成からの日数
         */
        public int getDaysSinceCreation() {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(createdAt, LocalDate.now());
        }
        
        // Getter methods - 取得する情報を明確に示す命名
        public String getUserId() { return userId; }
        public String getEmail() { return email; }
        public LocalDate getCreatedAt() { return createdAt; }
        public boolean isActive() { return isActive; }
        public int getLoginAttempts() { return loginAttempts; }
    }
    
    /**
     * リーダブルコード p.23-25「名前の長さを決める」と p.26-30「誤解されない名前を選ぶ」の実装例
     * 
     * このクラスでは以下の命名原則を実装しています：
     * - 適切な長さの名前（短すぎず長すぎず、情報量とのバランス）
     * - 誤解を招かない明確な名前
     * - 定数は大文字とアンダースコアで表現
     */
    public static class EmailValidator {
        // 定数の命名：EMAIL_REGEXは「メール用の正規表現」を明確に示す (p.24)
        private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        
        /**
         * メールアドレスの形式が有効かを検証 (p.26「誤解されない名前」)
         * isValidEmailFormatは「メール形式が有効か」を明確に示し、
         * 単なる「isValid」より具体的で誤解されにくい
         * 
         * @param email 検証するメールアドレス
         * @return 有効な形式の場合true
         */
        public static boolean isValidEmailFormat(String email) {
            return email != null && email.matches(EMAIL_REGEX);
        }
        
        /**
         * ビジネス用メールアドレスかを判定 (p.27「境界条件を明確にする」)
         * isBusinessEmailは「ビジネス用か」を明確に示し、
         * 個人用メールドメインを除外するロジックを含む
         * 
         * @param email 判定するメールアドレス
         * @return ビジネス用メールの場合true、個人用メール（Gmail等）の場合false
         */
        public static boolean isBusinessEmail(String email) {
            if (!isValidEmailFormat(email)) {
                return false;
            }
            
            // 個人用メールドメインのリスト - 配列名で内容を明確に示す (p.25)
            String[] personalEmailDomains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};
            String domain = email.substring(email.indexOf("@") + 1);
            
            for (String personalDomain : personalEmailDomains) {
                if (domain.equalsIgnoreCase(personalDomain)) {
                    return false;
                }
            }
            
            return true;
        }
    }
}