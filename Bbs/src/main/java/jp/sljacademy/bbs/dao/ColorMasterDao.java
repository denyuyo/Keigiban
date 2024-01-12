package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ColorMasterBean;

public class ColorMasterDao {
	private DataSource source;
	
	// throws：メソッドやコンストラクタが特定の例外を呼び出し元に投げることを宣言するために使用
	public ColorMasterDao() throws NamingException {
		/* 
		 * InitialContextを使って「"jdbc/MyDataSource"という名前のデータベースにどうやって接続するか」という情報を探している
		 * JNDI：データベースと話すための電話帳のようなもの。データベース接続の設定を簡単にしたり、外部からデータを見つけてきたりする
		 */
		// 'InitialContext'オブジェクトを初期化し、データベースへの参照を取得
		InitialContext context = new InitialContext();
		// 検索結果のオブジェクトを DataSource 型にキャスト（lookup メソッドはデフォルトで Object タイプのオブジェクトを返すので）
		source = (DataSource) 
			// JNDIを使用して、"java:comp/env/jdbc/datasource" という名前のリソース（通常はデータベース接続）を検索
			context.lookup("java:comp/env/jdbc/datasource");
	}
	
	public List<ColorMasterBean> getAllColors() throws SQLException {
		/* 
		 * source.getConnection()：データベースに接続するための「電話線」を確立している感じ。「データベースに接続してほしい」とプログラムが要求している
		 * getConnection()：実際にデータベースとの接続を確立する命令。データベースに「電話をかける」感じ
		 */
		// DataSource オブジェクト（source）から getConnection() メソッドを呼び出し、データベースへの接続を確立する Connection オブジェクトを取得
		Connection connection = source.getConnection();
		// ColorMasterBean型のオブジェクトをたくさん格納できる、colorsという名前の空のリストを新しく作成している
		List<ColorMasterBean> colors = new ArrayList<>();
		//「COLOR_MASTERテーブルからCOLOR_ID、COLOR_CODE、COLOR_NAMEという３つの列のデータを取り出す」
		String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";
		try {
			// データベースに問い合わせをするための特別な箱（statement）を作り、その中にSQLの命令文（sql）を入れて、データベースに何を聞きたいかを準備している
			PreparedStatement statement = connection.prepareStatement(sql);
			// データベースに対して先ほど準備したSQLの命令（statement）を実際に送り、executeQuery() メソッドでデータベースにSQL命令を実行させ、その答えをresultSetという箱に入れている
			ResultSet resultSet = statement.executeQuery();
			/* 
			 * 結果セット(resultSet)から次の行が存在する限り、ループを続ける
			 * 'resultSet.next()'
			 * データベースから取得した結果セット（表のようなデータの集まり）を一行ずつ見ていくための命令
			 * 呼び出すたびにカーソルは次の行へ移動し、新しい行にデータがあればtrueを返し、行がこれ以上なければfalseを返す
			 * データがなくなるまでループを続けることができる「次へ進むボタン」のようなもの
			 * 
			 * 'ResultSet'
			 * 各行からデータを取得するためには、getString, getInt, getDate などのメソッドを使用する
			 * 基本的に順次アクセス（フォワードオンリー）。つまり、最初の行から最後の行へと順番にデータにアクセスする
			 * データベースのクエリ結果を表す表（テーブル）のようなもので、一度に1行ずつデータにアクセスできる
			 * 
			 * カーソルを次の行に移動するために next() メソッドが一般的に使用される
			 * カーソル：データベースの問い合わせ結果の現在位置を指し示すポインタのようなもの
			 */
			while (resultSet.next()) {
				ColorMasterBean color = new ColorMasterBean();
				color.setColorId(resultSet.getString("COLOR_ID"));
				color.setColorCode(resultSet.getString("COLOR_CODE"));
				color.setColorName(resultSet.getString("COLOR_NAME"));
				colors.add(color);
			}
			// データベースへの問い合わせを行った statement という箱をきちんと閉じて、片付けをしている感じ
			statement.close();
		} catch (SQLException e) {
			/*
			 *  throw：発生した例外（エラー）に関する情報（例えばエラーメッセージやスタックトレース）が保持される
			 *  この場合はeに格納されたSQLExceptionエラーを投げているだけ
			 */
			throw e;
		/*
		 * finallyブロック：try-catch構文の最後に位置し、必ず実行される
		 * もしデータベースとの接続がまだ開いていたら（nullでなければ）、その接続を閉じるという指示出しをしている
		 */
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// List<ColorMasterBean>が格納されたcolorsを呼び出し元に提供するため、返している
		return colors;
	}
	
	public String getColorCode(String colorId) throws SQLException {
		
		// データベース接続を確立
		Connection connection = source.getConnection();
		
		// colorを空文字で初期化しておくことで、変数が未定義（null）の状態を避けられる
		String color = "";
		
		//「COLOR_MASTER テーブルから、指定された COLOR_ID に一致する行の COLOR_CODE の値を取得する」
		String sql = "SELECT COLOR_CODE FROM COLOR_MASTER WHERE COLOR_ID = ?;";
		try {
			/*
			 *  SQL文を実行するための準備が整った PreparedStatement オブジェクトを作成
			 *  connectionでデータベースに接続し、prepareStatementでsplに格納されたsql文を実行するための PreparedStatement を準備している
			 */
			PreparedStatement statement = connection.prepareStatement(sql);
			
			/*
			 * PreparedStatement オブジェクトの setString メソッドを使用して、1番目のプレースホルダ（?）に colorId 変数の値をセットしてる
			 * プレースホルダのインデックスは0ではなく、1から始まる
			 */
			statement.setString(1, colorId);
			
			// statementに格納されたSQL文をexecuteQueryでデータベースに実行し、その結果を保持するためにresultSetを用意
			ResultSet resultSet = statement.executeQuery();
			// ResultSet オブジェクトから取得されたカラーコードを一つずつ処理する
			while (resultSet.next()) {
				// ループ内で取得されたカラーコードを color 変数に代入
				color = resultSet.getString("COLOR_CODE");
			}
			// データベースへの問い合わせを終了させる
			statement.close();
		} catch (SQLException e) {
			throw e;
		// データベースの接続を切る
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// SQLクエリで指定されたカラーデータをデータベースから取得し、最終的なカラーコードを格納した変数 color を返す
		return color;
	}
}