package jp.sljacademy.bbs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.sljacademy.bbs.bean.ArticleBean;

// 項目制限

public class CommonFunction {
	// 「@test.co.jp」で終わるメールアドレスのみ許可（@以前は半角英数字のみ）
	private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@test\\.co\\.jp$";
	// EMAIL_REGEXをコンパイルして、後で何度でも使えるようにしてる
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);	
	
	// メールアドレスが正しい形式かどうかをチェックするメソッド
	public static boolean checkEmail(String email) {
		// メールアドレスがnullまたは空白のみの場合、不正な形式とみなす
		if (!isNotBlank(email)) {
			return false;
		}
		// メールアドレスが正しい形式かどうかをチェック
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}
	
	// 文字列の長さが特定の長さ以下かどうかをチェックするメソッド
	public static boolean checkLen(String text, int maxLength) {
		return text != null && text.length() <= maxLength;
	}
	
	// 文字列が空でないかをチェックするメソッド
	public static boolean isNotBlank(String text) {
		return text != null && !text.trim().isEmpty();
	}
	
	// ユーザーの入力に関するバリデーションを行い、問題があればエラーメッセージを作成するメソッド
	public static String validateInput(ArticleBean article) {
		StringBuilder errors = new StringBuilder();
		
		// 名前が30文字以内かどうかをチェック
		if (!checkLen(article.getName(), 30)) {
			errors.append("名前は30文字以内で入力してください。\n");
		}
		
		// メールアドレスが正しい形式で、30文字以内かどうかをチェック
		if (!checkEmail(article.getEmail()) || !checkLen(article.getEmail(), 30)) {
			errors.append("正しいEメールアドレスを30文字以内で入力してください。\n");
		}
		// タイトルが50文字以内かどうかをチェック（タイトルは空でもOK）
		if (isNotBlank(article.getTitle()) && !checkLen(article.getTitle(), 50)) {
			errors.append("タイトルは50文字以内で入力してください。\n");
		}
		// 本文が100文字以内かどうかをチェック
		if (!checkLen(article.getText(), 100)) {
			errors.append("本文は100文字以内で入力してください。\n");
		}
		return errors.toString();
	}
	
	// テキスト内の改行をHTMLの <br> タグに変換するメソッド
	public static String convertLineBreaksToHtml(String text) {
		if (text == null) {
			return null;
		}
		return text.replace("\n", "<br>");
	}
	
	// 名前が空の場合に "nobody" を返すメソッド
	public static String getDefaultName(String name) {
		return !isNotBlank(name) ? "nobody" : name;
	}
	
	// タイトルが空の場合に "(no title)" を返すメソッド
	public static String getDefaultTitle(String title) {
		return !isNotBlank(title) ? "(no title)" : title;
	}
}