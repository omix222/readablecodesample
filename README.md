# リーダブルコード サンプルコード集

『リーダブルコード ―より良いコードを書くためのシンプルで実践的なテクニック』のJavaによる実装例とサンプルコード集です。

## 📚 概要

このプロジェクトは、リーダブルコードの各章で紹介されている技法を、Javaで実際に実装したサンプルコード集です。悪い例と良い例を対比させながら、具体的なページ番号と共に学習できます。

## 🎯 学習目標

- 明確で意味のある命名技法
- 理解しやすい関数設計
- 適切なコメントの書き方
- 保守しやすいコード構造

## 📋 必要環境

- **Java**: 17以上
- **Maven**: 3.6以上
- **IDE**: IntelliJ IDEA, Eclipse, VS Code等（任意）

## 🚀 クイックスタート

### 1. プロジェクトのクローン

```bash
git clone <repository-url>
cd readable-code-examples
```

### 2. ビルドとテスト実行

```bash
# コンパイル
mvn compile

# テスト実行
mvn test

# JavaDocの生成
mvn javadoc:javadoc
```

### 3. IDEでの開き方

#### IntelliJ IDEA
1. `File` → `Open` → `pom.xml`を選択
2. 「Open as Project」を選択

#### Eclipse
1. `File` → `Import` → `Existing Maven Projects`
2. プロジェクトフォルダを選択

#### VS Code
1. フォルダを開く
2. Java Extension Packをインストール（未インストールの場合）

## 📁 プロジェクト構成

```
readable-code-examples/
├── README.md
├── pom.xml
├── src/
│   ├── main/java/com/example/readablecode/
│   │   ├── naming/              # 第2章：命名規則
│   │   │   └── NamingExamples.java
│   │   ├── functions/           # 第3-4章：関数設計
│   │   │   └── FunctionDesignExamples.java
│   │   ├── comments/            # 第5-6章：コメント
│   │   │   └── CommentExamples.java
│   │   └── structure/           # 第7-8章：コード構造
│   │       └── CodeStructureExamples.java
│   └── test/java/              # JUnit 5テストコード
│       └── com/example/readablecode/
│           ├── naming/NamingExamplesTest.java
│           ├── functions/FunctionDesignExamplesTest.java
│           ├── comments/CommentExamplesTest.java
│           └── structure/CodeStructureExamplesTest.java
```

## 📖 学習内容とページ対応

### 🏷️ 1. 命名規則 (`naming/NamingExamples.java`)
**第2章「名前に情報を込める」(p.9-30)**

- ✅ 明確で具体的な名前の使用 (p.11-13)
- ✅ 汎用的な名前の回避 (p.14-16)
- ✅ 抽象的でない具体的な名前の使用 (p.17-18)
- ✅ 情報を名前に含める (p.19-22)
- ✅ 名前の長さと情報量のバランス (p.23-25)
- ✅ 誤解されない名前の選択 (p.26-30)

**実装例:**
```java
// 悪い例
public int calc(int x) { return x * 2 + 1; }

// 良い例
public int calculateTotalPrice(int basePrice) { return basePrice * 2 + 1; }
```

### ⚙️ 2. 関数設計 (`functions/FunctionDesignExamples.java`)
**第3章「誤解されない名前」(p.31-40) + 第4章「美しさ」(p.41-50)**

- ✅ 関数は一つのことだけを行う (p.32-34)
- ✅ 関数のパラメータは少なくする (p.35-37)
- ✅ 関数名は動詞句で表現する (p.38-40)
- ✅ 関数の抽象化レベルを統一する (p.42-44)
- ✅ 早期return（ガード句）を使用する (p.45-47)
- ✅ 関数の複雑さを軽減する (p.48-50)

**実装例:**
```java
// 悪い例：パラメータが多すぎる
public String processUserData(String name, String email, int age, 
                             boolean active, String address, String phone, int score)

// 良い例：パラメータオブジェクトを使用
public String formatUserProfile(UserProfile profile)
```

### 💬 3. コメント (`comments/CommentExamples.java`)
**第5章「コメントすべきことを知る」+ 第6章「コメントは正確で簡潔に」**

- ✅ 有効なコメントと不要なコメントの区別
- ✅ JavaDocを使った適切なドキュメント作成
- ✅ 複雑なアルゴリズムの説明
- ✅ ビジネスルールの明記
- ✅ 一時的な対応（FIXME、TODO）の記録

**実装例:**
```java
/**
 * 複利計算を行います。式: A = P(1 + r/n)^(nt)
 * @param principal 元本
 * @param annualRate 年利率（小数点、例：0.05は5%）
 * @return 複利計算結果
 */
public double calculateCompoundInterest(double principal, double annualRate, 
                                       int compoundingFrequency, int years)
```

### 🏗️ 4. コード構造 (`structure/CodeStructureExamples.java`)
**第7章「制御フローを読みやすくする」+ 第8章「巨大な式を分割する」**

- ✅ 適切なクラス設計と責任分離
- ✅ Builderパターンの実装
- ✅ Enumを使った型安全性の向上
- ✅ Optional型を使ったnull安全性
- ✅ 例外処理の適切な設計

**実装例:**
```java
// Builderパターンで複雑なオブジェクト生成を簡潔に
Order order = new OrderBuilder()
    .customerId("CUST001")
    .addItem("BOOK001", 2)
    .paymentMethod(PaymentMethod.CREDIT_CARD)
    .deliveryAddress("123 Main St")
    .priority()
    .build();
```

## 🧪 テスト

各サンプルコードには包括的なJUnit 5テストが含まれています。

```bash
# 全テスト実行
mvn test

# 特定のテストクラスのみ実行
mvn test -Dtest=NamingExamplesTest

# テストレポートの確認
open target/surefire-reports/index.html
```

**テスト統計:**
- 総テスト数: 59個
- 成功率: 100%
- カバレッジ: 主要機能をカバー

## 📚 学習の進め方

### 1. 基礎から始める
1. `NamingExamples.java`から開始
2. 悪い例（`BadNaming`）を確認
3. 良い例（`GoodNaming`）と比較
4. 対応するテストコードを確認

### 2. 実践的な学習
1. 既存コードを悪い例に倣って書いてみる
2. 良い例を参考にリファクタリング
3. テストが通ることを確認

### 3. 応用
1. 自分のプロジェクトに技法を適用
2. コードレビューで指摘事項として活用
3. チーム内での規約策定に参考

## 🔍 JavaDocの確認

詳細な技法説明はJavaDocで確認できます：

```bash
# JavaDoc生成
mvn javadoc:javadoc

# ブラウザで確認
open target/site/apidocs/index.html
```

## 🤝 コントリビューション

このプロジェクトへの改善提案は歓迎します：

1. 新しい技法の実装例
2. より良い悪い例・良い例の提案
3. テストケースの追加
4. ドキュメントの改善

## 📖 参考文献

- **リーダブルコード ―より良いコードを書くためのシンプルで実践的なテクニック**
  - 著者: Dustin Boswell, Trevor Foucher
  - 翻訳: 角征典

## 🏷️ タグ

`Java` `リーダブルコード` `クリーンコード` `設計` `命名規則` `関数設計` `コメント` `JUnit5` `Maven` `教育`

---

**Happy Coding! 🚀**

より読みやすく、保守しやすいコードを書くための第一歩として、このサンプル集をご活用ください。