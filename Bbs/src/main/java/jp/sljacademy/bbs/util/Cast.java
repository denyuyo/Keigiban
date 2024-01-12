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
	/*
	 * 'void'
	 * メソッドが何らかの処理を実行して終了するだけで、結果として何も値を返さない
	 * 主に副作用を持つ操作（例: 画面に表示、ファイルへの書き込み、データベースへの変更など）を実行するために使用される
	 * ジェネリック型（IntやString）を使用できない場合などに便利
	 * 
	 * void ではなく ArrayList<AccountBean> 型のデータを返すことで、メソッドを呼び出す側で型変換を行い、ArrayList<AccountBean> 型のデータを利用できるようになる
	 * 
	 * 'Object 型'
	 * すべてのクラスは Object クラスを直接または間接的に継承している。そのため、どんな種類のオブジェクトでも Object 型の変数に代入できる
	 * 
	 * 'castList(Object object)'
	 * 引数として Object 型のデータを受け取り、それを (ArrayList<AccountBean>) object のように型変換して、ArrayList<AccountBean> 型のデータとして返す
	 * 
	 */
	// ArrayList<AccountBean> 型のオブジェクトを戻り値として返すことができるようになる
	public static ArrayList<AccountBean> castList(Object object) {
		// object に格納されていたデータを ArrayList<AccountBean> 型のデータとして取り出し、その結果を呼び出し元に返す 
		return (ArrayList<AccountBean> )object;
	}
}
