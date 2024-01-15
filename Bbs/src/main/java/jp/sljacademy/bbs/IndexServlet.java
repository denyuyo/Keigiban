package jp.sljacademy.bbs;

// ログイン画面の表示

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.AccountBean;
import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.dao.AccountDao;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class IndexServlet
 */
// @WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// check：ユーザーが入力したIDとパスワードの妥当性をチェックするメソッド
	private String check(String id, String password) {
		String errorMessages = "";
		
		if (id.isEmpty()) {
			errorMessages = "IDが入力されていません。<br>";
			}
			// += 演算子：errorMessages に新しいエラーメッセージを追加
			if (password.isEmpty()) {
			errorMessages += "パスワードが入力されていません。<br>";
		}
		// 呼び出し元にエラーメッセージを表示
		return errorMessages;
	}
		
		// Javaコンパイラに「未使用の警告を無視する」と指示している
		@SuppressWarnings("unused")
		// isAccountValid：入力されたIDとパスワードが有効かどうかを検証するメソッド
		private boolean isAccountValid(String id, String password) {
		    try {
		    	// 新しい AccountDao オブジェクトを作成し、データベースにアクセスするための dao 変数を用意
		        AccountDao dao = new AccountDao();
		        
		        // dao を使用して、指定された id と password に一致するアカウント情報をデータベースから取得
		        AccountBean account = dao.getAccount(id, password);
		        /*
		         * .equals()：文字列同士の内容を比較する際に使用されるメソッド。もし二つの文字列が等しい場合、このメソッドは true を返す
		         */
		        // アカウントが存在し、かつデータベースのパスワードと入力されたパスワードが一致しているかどうかを検証
		        return (account != null && account.getPassword().equals(password));
		    } catch (NamingException | SQLException e) {
		        // 詳細なエラーメッセージを表示
		        e.printStackTrace();
		        return false;
		    }
		}
		
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// プロパティファイルから "url.bbs.input" というキーに関連付けられたURLを取得
		String resultPage = PropertyLoader.getProperty("url.bbs.input");
		
		HttpSession session = request.getSession(false);
		
		/*
		 * リダイレクト (Redirect)：新しいURLへ移動する
		 */
		// セッションが存在し、かつ "id" という属性がセッションに存在する場合（ユーザーがログイン済み）、一覧画面にリダイレクト
		if (session != null && session.getAttribute("id") != null) {
			response.sendRedirect(resultPage);
			return;
		}
		/*
		 * 転送 (Forward)：URLは変更されず、ブラウザのアドレスバーには元のURLが表示される
		 */
		// ログインしていない場合はログイン画面に転送
		resultPage = PropertyLoader.getProperty("url.jsp.index");
		request.getRequestDispatcher(resultPage).forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.index");
		
		// フォームから送信されたユーザーIDとパスワードを取得
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// エラーメッセージを生成する check メソッドを呼び出す
		String errorMessages = check(id, password);
		
		// 入力エラーメッセージが存在する（errorMessages 変数が空でない）場合
		if (!errorMessages.isEmpty()) {
			// エラーメッセージをリクエスト属性に設定して、次のリクエストやフォワードで利用できるようにする
			request.setAttribute("errorMessages", errorMessages);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			// フォワードしたらメソッドから抜ける
			dispatcher.forward(request, response);
			return;
		}
		// セッションを取得または新しいセッションを作成し、ログイン状態を維持できるようにする
		HttpSession session = request.getSession(true);
		try {
			// AccountDao データクラスが格納された dao 変数を用意
			AccountDao dao = new AccountDao();
			// ユーザーが提供したユーザーIDとパスワードを使用して dao からアカウント情報を取得し、account に格納
			AccountBean account = dao.getAccount(id, password);
			// データベースに該当するアカウントが存在しない場合
			if (account == null) {
				// エラーメッセージをリクエスト属性に設定し、後でログインフォームに表示させる
				request.setAttribute("errorMessages", "ログインできません");
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				// 同じ画面のままエラーメッセージを表示
				dispatcher.forward(request, response);
				return;
			}
			/*
			 * ユーザーログイン後の処理
			 */
			// 新しい ArticleBean オブジェクトを作成し、、ユーザーが入力する記事情報を格納するための articleBean 変数を用意
			ArticleBean articleBean = new ArticleBean();
			// account オブジェクトから取得したユーザーの名前とEメールアドレスを、新しく作成した articleBean オブジェクトにセット
			articleBean.setName(account.getName());
			articleBean.setEmail(account.getEmail());
			
			// セッション内の "ArticleBean" 属性に articleBean オブジェクトを再度設定して、記事情報を更新
			session.setAttribute("ArticleBean", articleBean);
			// セッションにユーザーID（id）を保存して、どのユーザーがログインしているか関連付ける
			session.setAttribute("id", id);
				
		} catch (Exception e) { 
			// エラーメッセージをリクエストに設定
			request.setAttribute("errorMessage", e.getMessage());
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			// エラーページに転送
			request.getRequestDispatcher(resultPage).forward(request, response);
		}
		// 問題がなくログインに成功した場合、一覧画面に遷移
		resultPage = PropertyLoader.getProperty("url.bbs.input");
		response.sendRedirect(resultPage);
	}
}
