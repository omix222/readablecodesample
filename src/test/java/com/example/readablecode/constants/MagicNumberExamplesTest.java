package com.example.readablecode.constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MagicNumberExamplesTest {
    
    @Nested
    @DisplayName("BadMagicNumbers Tests")
    class BadMagicNumbersTest {
        
        private final MagicNumberExamples.BadMagicNumbers badExample = 
            new MagicNumberExamples.BadMagicNumbers();
        
        @Test
        @DisplayName("悪い例：マジックナンバーを使ったユーザー検証が動作する")
        public void badMagicNumberUserValidationShouldWork() {
            // 有効なユーザー（18歳以上、65歳以下、スコア0-100、ログイン試行2回以下）
            assertTrue(badExample.isValidUser(25, 75.5, 1));
            
            // 無効なユーザー（年齢制限）
            assertFalse(badExample.isValidUser(17, 75.5, 1)); // 18歳未満
            assertFalse(badExample.isValidUser(66, 75.5, 1)); // 65歳超過
            
            // 無効なユーザー（スコア制限）
            assertFalse(badExample.isValidUser(25, -1, 1));   // スコア範囲外
            assertFalse(badExample.isValidUser(25, 101, 1));  // スコア範囲外
            
            // 無効なユーザー（ログイン試行回数）
            assertFalse(badExample.isValidUser(25, 75.5, 3)); // 試行回数上限
        }
        
        @Test
        @DisplayName("悪い例：マジックナンバーを使った労働時間計算が動作する")
        public void badMagicNumberWorkingHoursCalculationShouldWork() {
            // 1日の労働時間：8時間 = 8 * 60 * 60 = 28800秒
            long oneDaySeconds = badExample.calculateWorkingHoursInSeconds(1);
            assertEquals(28800, oneDaySeconds);
            
            // 5日間の労働時間
            long fiveDaysSeconds = badExample.calculateWorkingHoursInSeconds(5);
            assertEquals(144000, fiveDaysSeconds);
        }
        
        @Test
        @DisplayName("悪い例：マジックナンバーを使ったレポート生成が動作する")
        public void badMagicNumberReportGenerationShouldWork() {
            List<String> report = badExample.generateReport();
            
            assertEquals(10, report.size()); // マジックナンバー10個の項目
            assertEquals("Item 0", report.get(0));
            assertEquals("Item 9", report.get(9));
        }
        
        @Test
        @DisplayName("悪い例：マジックナンバーを使ったHTTPステータス判定が動作する")
        public void badMagicNumberHttpStatusShouldWork() {
            assertEquals("OK", badExample.getHttpStatusMessage(200));
            assertEquals("Not Found", badExample.getHttpStatusMessage(404));
            assertEquals("Internal Server Error", badExample.getHttpStatusMessage(500));
            assertEquals("Unknown", badExample.getHttpStatusMessage(999));
        }
    }
    
    @Nested
    @DisplayName("GoodNamedConstants Tests")
    class GoodNamedConstantsTest {
        
        private final MagicNumberExamples.GoodNamedConstants goodExample = 
            new MagicNumberExamples.GoodNamedConstants();
        
        @Test
        @DisplayName("良い例：名前付き定数を使ったユーザー検証が正しく動作する")
        public void goodNamedConstantsUserValidationShouldWork() {
            // 有効なユーザー
            assertTrue(goodExample.isValidUser(25, 75.5, 1));
            
            // 年齢制限テスト
            assertFalse(goodExample.isValidUser(17, 75.5, 1)); // MINIMUM_AGE未満
            assertTrue(goodExample.isValidUser(18, 75.5, 1));  // MINIMUM_AGE
            assertTrue(goodExample.isValidUser(65, 75.5, 1));  // RETIREMENT_AGE
            assertFalse(goodExample.isValidUser(66, 75.5, 1)); // RETIREMENT_AGE超過
            
            // スコア制限テスト
            assertFalse(goodExample.isValidUser(25, -0.1, 1)); // MIN_SCORE未満
            assertTrue(goodExample.isValidUser(25, 0.0, 1));   // MIN_SCORE
            assertTrue(goodExample.isValidUser(25, 100.0, 1)); // MAX_SCORE
            assertFalse(goodExample.isValidUser(25, 100.1, 1)); // MAX_SCORE超過
            
            // ログイン試行回数テスト
            assertTrue(goodExample.isValidUser(25, 75.5, 2));  // MAX_LOGIN_ATTEMPTS未満
            assertFalse(goodExample.isValidUser(25, 75.5, 3)); // MAX_LOGIN_ATTEMPTS
        }
        
        @Test
        @DisplayName("良い例：名前付き定数を使った労働時間計算が正しく動作する")
        public void goodNamedConstantsWorkingHoursCalculationShouldWork() {
            // 1日の労働時間：8時間 = 28800秒
            long oneDaySeconds = goodExample.calculateWorkingHoursInSeconds(1);
            assertEquals(28800, oneDaySeconds);
            
            // 5日間の労働時間
            long fiveDaysSeconds = goodExample.calculateWorkingHoursInSeconds(5);
            assertEquals(144000, fiveDaysSeconds);
            
            // 0日の場合
            long zeroDaysSeconds = goodExample.calculateWorkingHoursInSeconds(0);
            assertEquals(0, zeroDaysSeconds);
        }
        
        @Test
        @DisplayName("良い例：名前付き定数を使ったレポート生成が正しく動作する")
        public void goodNamedConstantsReportGenerationShouldWork() {
            // デフォルトレポート
            List<String> defaultReport = goodExample.generateReport();
            assertEquals(10, defaultReport.size()); // DEFAULT_REPORT_ITEMS
            assertEquals("Item 0", defaultReport.get(0));
            assertEquals("Item 9", defaultReport.get(9));
            
            // カスタムサイズレポート
            List<String> customReport = goodExample.generateReport(5);
            assertEquals(5, customReport.size());
            assertEquals("Item 0", customReport.get(0));
            assertEquals("Item 4", customReport.get(4));
            
            // 空のレポート
            List<String> emptyReport = goodExample.generateReport(0);
            assertEquals(0, emptyReport.size());
        }
    }
    
    @Nested
    @DisplayName("HttpStatusCodes Tests")
    class HttpStatusCodesTest {
        
        private final MagicNumberExamples.HttpStatusCodes httpExample = 
            new MagicNumberExamples.HttpStatusCodes();
        
        @Test
        @DisplayName("HTTPステータスコード定数が正しい値を持つ")
        public void httpStatusCodeConstantsShouldHaveCorrectValues() {
            // 成功レスポンス系
            assertEquals(200, MagicNumberExamples.HttpStatusCodes.OK);
            assertEquals(201, MagicNumberExamples.HttpStatusCodes.CREATED);
            assertEquals(204, MagicNumberExamples.HttpStatusCodes.NO_CONTENT);
            
            // クライアントエラー系
            assertEquals(400, MagicNumberExamples.HttpStatusCodes.BAD_REQUEST);
            assertEquals(401, MagicNumberExamples.HttpStatusCodes.UNAUTHORIZED);
            assertEquals(404, MagicNumberExamples.HttpStatusCodes.NOT_FOUND);
            
            // サーバーエラー系
            assertEquals(500, MagicNumberExamples.HttpStatusCodes.INTERNAL_SERVER_ERROR);
            assertEquals(502, MagicNumberExamples.HttpStatusCodes.BAD_GATEWAY);
        }
        
        @Test
        @DisplayName("名前付き定数を使ったHTTPステータスメッセージが正しく動作する")
        public void httpStatusMessageWithNamedConstantsShouldWork() {
            assertEquals("正常に処理されました", 
                httpExample.getHttpStatusMessage(MagicNumberExamples.HttpStatusCodes.OK));
            assertEquals("リソースが見つかりません", 
                httpExample.getHttpStatusMessage(MagicNumberExamples.HttpStatusCodes.NOT_FOUND));
            assertEquals("サーバー内部エラーが発生しました", 
                httpExample.getHttpStatusMessage(MagicNumberExamples.HttpStatusCodes.INTERNAL_SERVER_ERROR));
            assertEquals("不明なステータスコード", 
                httpExample.getHttpStatusMessage(999));
        }
        
        @Test
        @DisplayName("HTTPステータスコード範囲判定が正しく動作する")
        public void httpStatusRangeCheckShouldWork() {
            // 成功ステータス（200-299）
            assertTrue(httpExample.isSuccessStatus(200));
            assertTrue(httpExample.isSuccessStatus(201));
            assertTrue(httpExample.isSuccessStatus(299));
            assertFalse(httpExample.isSuccessStatus(300));
            assertFalse(httpExample.isSuccessStatus(404));
            
            // クライアントエラー（400-499）
            assertTrue(httpExample.isClientError(400));
            assertTrue(httpExample.isClientError(404));
            assertTrue(httpExample.isClientError(499));
            assertFalse(httpExample.isClientError(200));
            assertFalse(httpExample.isClientError(500));
            
            // サーバーエラー（500-599）
            assertTrue(httpExample.isServerError(500));
            assertTrue(httpExample.isServerError(502));
            assertTrue(httpExample.isServerError(599));
            assertFalse(httpExample.isServerError(404));
            assertFalse(httpExample.isServerError(600));
        }
    }
    
    @Nested
    @DisplayName("ApplicationConfig Tests")
    class ApplicationConfigTest {
        
        @Test
        @DisplayName("アプリケーション設定定数が適切な値を持つ")
        public void applicationConfigConstantsShouldHaveProperValues() {
            // データベース関連
            assertEquals(10, MagicNumberExamples.ApplicationConfig.DEFAULT_CONNECTION_POOL_SIZE);
            assertEquals(50, MagicNumberExamples.ApplicationConfig.MAX_CONNECTION_POOL_SIZE);
            assertEquals(30, MagicNumberExamples.ApplicationConfig.CONNECTION_TIMEOUT_SECONDS);
            
            // キャッシュ関連
            assertEquals(60, MagicNumberExamples.ApplicationConfig.CACHE_EXPIRY_MINUTES);
            assertEquals(1000, MagicNumberExamples.ApplicationConfig.MAX_CACHE_SIZE);
            
            // ファイル処理関連
            assertEquals(10, MagicNumberExamples.ApplicationConfig.MAX_FILE_SIZE_MB);
            assertEquals(8192, MagicNumberExamples.ApplicationConfig.BUFFER_SIZE_BYTES);
            
            // セキュリティ関連
            assertEquals(8, MagicNumberExamples.ApplicationConfig.PASSWORD_MIN_LENGTH);
            assertEquals(30, MagicNumberExamples.ApplicationConfig.SESSION_TIMEOUT_MINUTES);
            assertEquals(24, MagicNumberExamples.ApplicationConfig.TOKEN_EXPIRY_HOURS);
        }
        
        @Test
        @DisplayName("設定値の論理的整合性が保たれている")
        public void applicationConfigValuesShouldBeLogicallyConsistent() {
            // データベース接続プールサイズの整合性
            assertTrue(MagicNumberExamples.ApplicationConfig.DEFAULT_CONNECTION_POOL_SIZE <= 
                      MagicNumberExamples.ApplicationConfig.MAX_CONNECTION_POOL_SIZE);
            
            // セキュリティ設定の妥当性
            assertTrue(MagicNumberExamples.ApplicationConfig.PASSWORD_MIN_LENGTH > 0);
            assertTrue(MagicNumberExamples.ApplicationConfig.SESSION_TIMEOUT_MINUTES > 0);
            assertTrue(MagicNumberExamples.ApplicationConfig.TOKEN_EXPIRY_HOURS > 0);
            
            // ファイルサイズ設定の妥当性
            assertTrue(MagicNumberExamples.ApplicationConfig.MAX_FILE_SIZE_MB > 0);
            assertTrue(MagicNumberExamples.ApplicationConfig.BUFFER_SIZE_BYTES > 0);
        }
    }
    
    @Nested
    @DisplayName("Magic Numbers vs Named Constants Comparison")
    class MagicNumbersVsNamedConstantsComparisonTest {
        
        @Test
        @DisplayName("マジックナンバーと名前付き定数で同じ結果が得られる")
        public void magicNumbersAndNamedConstantsShouldProduceSameResults() {
            MagicNumberExamples.BadMagicNumbers badExample = 
                new MagicNumberExamples.BadMagicNumbers();
            MagicNumberExamples.GoodNamedConstants goodExample = 
                new MagicNumberExamples.GoodNamedConstants();
            
            // 同じ入力で同じ結果が得られることを確認
            int age = 25;
            double score = 75.5;
            int loginAttempts = 1;
            
            boolean badResult = badExample.isValidUser(age, score, loginAttempts);
            boolean goodResult = goodExample.isValidUser(age, score, loginAttempts);
            
            assertEquals(badResult, goodResult, "マジックナンバーと名前付き定数で異なる結果");
            
            // 労働時間計算でも同様
            long badWorkingHours = badExample.calculateWorkingHoursInSeconds(5);
            long goodWorkingHours = goodExample.calculateWorkingHoursInSeconds(5);
            
            assertEquals(badWorkingHours, goodWorkingHours, "労働時間計算で異なる結果");
        }
        
        @Test
        @DisplayName("名前付き定数の方が保守性が高い")
        public void namedConstantsShouldProvideBetterMaintainability() {
            // この例では実際の保守性の向上は測定できないが、
            // 定数が適切に定義されていることを確認
            MagicNumberExamples.GoodNamedConstants goodExample = 
                new MagicNumberExamples.GoodNamedConstants();
            
            // 定数が使用されていることを間接的に確認
            // 境界値テストで定数の効果を確認
            assertFalse(goodExample.isValidUser(17, 50, 1)); // MINIMUM_AGE - 1
            assertTrue(goodExample.isValidUser(18, 50, 1));  // MINIMUM_AGE
            assertTrue(goodExample.isValidUser(65, 50, 1));  // RETIREMENT_AGE
            assertFalse(goodExample.isValidUser(66, 50, 1)); // RETIREMENT_AGE + 1
        }
    }
}