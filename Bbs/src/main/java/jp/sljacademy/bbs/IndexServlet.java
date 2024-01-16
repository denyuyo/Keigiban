package jp.sljacademy.bbs;

// ログイン画面の表示

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.AccountBean;
import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.dao.AccountDao;
import jp.sljacademy.bbs.util.CommonFunction;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class IndexServlet
 */
// @WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
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
	/*
	 * 目的：主にユーザーがログインしているかどうかを確認し、適切な画面にリダイレクトまたは転送する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// プロパティファイルから "url.bbs.input" というキーに関連付けられたURLを取得
		String resultPage = PropertyLoader.getProperty("url.bbs.input");
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
		
		/*
		 * リダイレクト (Redirect)：新しいURLへ移動する
		 */
		// セッションが存在し、かつ "id" という属性がセッションに存在する場合（ユーザーがログイン済み）、一覧画面にリダイレクト
		if (session != null && session.getAttribute("account") != null) {
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
	/*
	 * ユーザーが提供したユーザーIDとパスワードを使用して、Webアプリケーションのログインプロセスを処理する
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.index");
		
		// フォームから送信されたユーザーIDとパスワードを取得
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// CommonFunctionのcheckメソッドを呼び出して妥当性チェックを行う
		String errorMessages = CommonFunction.check(id, password);
		
		// バリデーションエラーがある場合、ログイン画面でエラーメッセージを表示します
		if (!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}
		
		try {
			AccountDao dao = new AccountDao();
			// ユーザーが入力したIDとパスワードをデータベースと照合してます
			AccountBean account = dao.getAccount(id, password);
			// もしデータベースに該当のアカウントが存在しない場合
			if (account == null) {
				// エラーメッセージをログインフォームに表示させます
				request.setAttribute("errorMessages", "ログインできません");
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
			}
			/*
			 * ユーザーログインが無事にログイン出来たら
			 */
			
			// ユーザーが入力する記事情報を用意
			ArticleBean articleBean = new ArticleBean();
			// 取得したユーザーの名前とEメールアドレスを、記事情報にセットします
			articleBean.setName(account.getName());
			articleBean.setEmail(account.getEmail());
			
			// セッションを取得または新しいセッションを作成して、ログインできるようにします
			HttpSession session = request.getSession(true);
			
			// セッションに記事情報を再度設定して、更新します
			session.setAttribute("ArticleBean", articleBean);
			// セッションにaccount）を保存して、どのユーザーがログインしているか関連付ける
			session.setAttribute("account", account);
				
		} catch (Exception e) {
			// 例外が発生したらエラー画面に飛ぶようにします
			request.setAttribute("errorMessage", e.getMessage());
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			request.getRequestDispatcher(resultPage).forward(request, response);
		}
		// 問題がなくログインに成功した場合、一覧画面に遷移
		resultPage = PropertyLoader.getProperty("url.bbs.input");
		response.sendRedirect(resultPage);
	}
}