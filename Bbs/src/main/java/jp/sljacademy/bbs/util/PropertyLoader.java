package jp.sljacademy.bbs.util;

import java.util.ResourceBundle;

// プロパティファイルの文字列を参照する

public class PropertyLoader {
	
	public static String getProperty(String name) {
		ResourceBundle resource = ResourceBundle.getBundle("application");
		return resource.getString(name);
	}

}
