package jp.sljacademy.bbs.util;

import java.util.regex.Pattern;

import jp.sljacademy.bbs.bean.ArticleBean;

// 項目制限

public class CommonFunction {
	// 各フィールドの最大長
	private static final int MAX_NAME_LENGTH = 30;
	private static final int MAX_EMAIL_LENGTH = 30;
	private static final int MAX_TITLE_LENGTH = 50;
	private static final int MAX_TEXT_LENGTH = 100;
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$");
	
	// 記事のバリデーションを行うメソッド
	public static String validateArticle(ArticleBean articleBean) {
		StringBuilder errors = new StringBuilder();	
		
		
		checkLength(articleBean.getName(), MAX_NAME_LENGTH, "名前", errors);
		checkLength(articleBean.getEmail(), MAX_EMAIL_LENGTH, "Eメール", errors);
		checkLength(articleBean.getTitle(), MAX_TITLE_LENGTH, "タイトル", errors);
		checkLength(articleBean.getText(), MAX_TEXT_LENGTH, "本文", errors);
		
		return errors.toString();
	}
	
	private static void checkLength(String value, int maxLen, String fieldName, StringBuilder errors) {
		if (value != null && value.length() > maxLen) {
			errors.append(fieldName).append("は").append(maxLen).append("文字以内で入力してください。\n");
		}
	}

	public static boolean isValidEmail(String email) {
		return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
	}
}