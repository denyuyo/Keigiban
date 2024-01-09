package jp.sljacademy.bbs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.sljacademy.bbs.bean.ArticleBean;

// 項目制限

public class CommonFunction {
	// Eメールのバリデーションパターン
	private static final String EMAIL_REGEX = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);	
	
	// Eメールのバリデーションメソッド
	public static boolean checkEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}
	
	// 文字列の長さをチェックするメソッド
	public static boolean checkLen(String text, int maxLength) {
		return text != null && text.length() <= maxLength;
	}
	
	// 文字列が空でないかをチェックするメソッド
	public static boolean isNotBlank(String text) {
		return text != null && !text.trim().isEmpty();
	}
	
	// 入力バリデーションを行うメソッド
	public static String validateInput(ArticleBean article) {
		StringBuilder errors = new StringBuilder();
		
		// 名前の長さチェック
		if (!checkLen(article.getName(), 30)) {
			errors.append("名前は30文字以内で入力してください。\n");
		}
		
		// Eメールの形式と長さチェック
		if (!checkEmail(article.getEmail()) || !checkLen(article.getEmail(), 30)) {
			errors.append("正しいEメールアドレスを30文字以内で入力してください。\n");
		}
		// タイトルの長さチェック（タイトルは空でも可）
		if (isNotBlank(article.getTitle()) && !checkLen(article.getTitle(), 50)) {
			errors.append("タイトルは50文字以内で入力してください。\n");
		}
		// 本文の長さチェック
		if (!checkLen(article.getText(), 100)) {
			errors.append("本文は100文字以内で入力してください。\n");
		}
		return errors.toString();
		}
}