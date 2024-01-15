package jp.sljacademy.bbs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.sljacademy.bbs.bean.ArticleBean;

// 共通の機能（項目制限）を提供するクラス

public class CommonFunction {
	// 「@test.co.jp」で終わるメールアドレスのみ許可（@以前は半角英数字のみ）
	private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@test\\.co\\.jp$";
	/*
	 * 正規表現パターンをもとに、Pattern クラスを使って、後で何度でも同じパターンを使えるようにしている
	 * compileしてる理由：毎回正規表現を解析・コンパイルする必要がなくなる。コンパイル済みのパターン使用で高速なマッチングが可能
	 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);	
	
	// メールアドレスが正しい形式かどうかをチェックする checkEmail メソッド
	public static boolean checkEmail(String email) {
		/*
		 * メールアドレスが正しい形式かどうかをチェック
		 * Matcher：文字列の中から特定のパターン（[A-Za-z0-9]+@test\\.co\\.jp$）に一致する部分を見つけたり、照合したりするJavaのクラス
		 */
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		/*
		 * matches：Matcher クラスのメソッドの一つで、正規表現パターン（EMAIL_PATTERN）とemailの文字列を照合し、パターンと一致するかどうかを調べて返す
		 */
		return matcher.matches();
	}
	
	// 文字列の長さが特定の長さ以下かどうかをチェックする checkLen メソッド
	public static boolean checkLen(String text, int maxLength) {
		//「文字列 text が存在し（null でない）、かつその長さが指定された最大長 maxLength 以下である場合に true を返し、それ以外の場合に false を返す」
		return text != null && text.length() <= maxLength;
	}
	
	// 文字列が空でないかをチェックする isNotBlank メソッド
	public static boolean isNotBlank(String text) {
		//「文字列 text が存在し、trim() で前後の空白を取り除いても空でない場合に true を返し、それ以外の場合に false を返す」
		return text != null && !text.trim().isEmpty();
	}
	
	/*
	 * ユーザーの入力に関するバリデーションを行い、問題があればエラーメッセージを作成する validate メソッド
	 * importしてるArticleBeanのデータをarticleに代入して使っている
	 */
	public static String validate(ArticleBean article) {
		/*
		 * StringBuilder：文字列を効率的に操作し、文字列の結合や変更を行う際に使用するJavaのクラス。今回は文字列の結合が頻繁に行われるので使用
		 * append：StringBuilder クラスの文字列を効率的に結合するためのメソッド
		 * toString：オブジェクトを文字列に変換するためのメソッド。最終的に errors を文字列に変換して返す必要があるので使用
		 */
		StringBuilder errors = new StringBuilder();
		
		// メールアドレスが正しい形式
		if (!checkEmail(article.getEmail())) {
			errors.append("正しいEメールアドレスを入力してください。\n");
		}
		// タイトルが50文字以内（タイトルは空でもOK）
		if (isNotBlank(article.getTitle()) && !checkLen(article.getTitle(), 50)) {
			errors.append("タイトルは50文字以内で入力してください。\n");
		}
		
		// errors に格納されている文字列を取り出して、それをメソッドの戻り値として返す
		return errors.toString();
	}
	
	// テキスト内の改行をHTMLの <br> タグに変換する convertLineBreaksToHtml メソッド
	public static String convertLineBreaksToHtml(String text) {
		// text が null の場合に変換する必要がないので、nullを返す
		if (text == null) {
			return null;
		}
		/*
		 *  replace：文字列の中から特定の文字列を検索して、それを指定した別の文字列に置き換えるメソッド
		 */
		// text 内のすべての改行文字 (\n) を HTML の改行タグ (<br>) に置換して返す
		return text.replace("\n", "<br>");
	}
	
	// 文字列が空でない場合はそのまま返し、空の場合は defaultValue として指定された文字列を返す getDefault メソッド
	public static String getDefault(String value, String defaultValue) {
		// value が空でない場合には value の値を、空の場合にはデフォルト値 defaultValue を返す
		return isNotBlank(value) ? value : defaultValue;
	}
	
	// 与えられた文字列 name をそのまま返すだけの t メソッド。aタグに含めるため使用
	 public static String t(String name) {
		return name;
	}
}