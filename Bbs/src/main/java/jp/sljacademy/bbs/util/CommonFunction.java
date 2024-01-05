package jp.sljacademy.bbs.util;

import java.util.regex.Pattern;

// 共通関数をまとめた

public class CommonFunction {
	
	public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean checkEmail(String email) {
        // E-mailアドレスの正規表現パターン
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean checkLen(String input, int maxLength) {
        return input.length() <= maxLength;
    }

}
