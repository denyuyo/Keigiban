package jp.sljacademy.bbs.util;

import java.util.ArrayList;

import jp.sljacademy.bbs.bean.AccountBean;

// 未検査の型変換警告を無視するためのクラス

public class Cast {
	// 未検査の型変換警告を無視して、Object型をArrayList<AccountBean>型に変換するメソッド
	@SuppressWarnings("unchecked")
	public static ArrayList<AccountBean> castList(Object object) {
		return (ArrayList<AccountBean> )object;
	}

}
