package jp.sljacademy.bbs.util;

import java.util.ResourceBundle;

// 外部のプロパティファイルに保存された設定やメッセージなどの情報を読み取るためのクラス

public class PropertyLoader {
	
	/*
	 * ResourceBundle オブジェクト：異なる地域や言語に合わせてアプリケーションのテキストや設定をカスタマイズできるヘルパークラス
	 * ヘルパークラス：特定のタスクや機能を実行するために設計された小規模なユーティリティクラス
	 * ResourceBundle.getBundle() ：指定された名前を基にしてプロパティファイルを探し、それを読み込むメソッド（プロパティファイル名に拡張子 (.properties) は含まない）
	 */
	
	public static String getProperty(String name) {
		// プロパティファイル名が "application.properties" である ResourceBundle オブジェクトを取得
		ResourceBundle resource = ResourceBundle.getBundle("application");
		// name パラメーターで指定された名前のプロパティをプロパティファイルから読み取り、その値を文字列として返す
		return resource.getString(name);
	}
}
