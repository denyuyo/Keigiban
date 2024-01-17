package jp.sljacademy.bbs.util;

import java.util.ArrayList;

import jp.sljacademy.bbs.bean.AccountBean;

/*
 *  未検査の型変換警告を無視するためのメソッド castList を持つクラス
 *  目的：未検査の型変換警告を無視し、Object 型のデータを ArrayList<AccountBean> 型に変換する
 */

public class Cast {
	// このアノテーションは、このメソッド内で行われている未検査の型変換に対する警告を無視する指示。これにより、メソッドを呼び出す際に型変換に関するコンパイラの警告が表示されなくなる
	@SuppressWarnings("unchecked")
	// ArrayList<AccountBean> 型のオブジェクトを戻り値として返すことができるようになる
	public static ArrayList<AccountBean> castList(Object object) {
		// object に格納されていたデータを ArrayList<AccountBean> 型のデータとして取り出し、その結果を呼び出し元に返す 
		return (ArrayList<AccountBean> )object;
	}
}
