package jp.sljacademy.bbs.util;

import java.util.ArrayList;

import jp.sljacademy.bbs.bean.AccountBean;

// 未検査の型変換に対する警告を非表示にする

public class Cast {
	@SuppressWarnings("unchecked")
	public static ArrayList<AccountBean> castList(Object object) {
		return (ArrayList<AccountBean> )object;
	}

}
