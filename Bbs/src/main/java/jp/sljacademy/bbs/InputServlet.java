package jp.sljacademy.bbs;

// 一覧画面の表示

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.bean.ColorMasterBean;
import jp.sljacademy.bbs.dao.ArticleDao;
import jp.sljacademy.bbs.dao.ColorMasterDao;
import jp.sljacademy.bbs.util.CommonFunction;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class InputServlet
 */
// @WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public InputServlet() {
		super();
	}
	
	/*
	 * ユーザーがログイン済みかどうかを確認し、ログインしている場合には過去の記事データと色情報を取得して表示させます
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// URLのデフォルト値を設定
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		String errorMessages = "";
		
		// セッションを取得して、存在しない場合は null を返します
		HttpSession session = request.getSession(false);
		
		// セッションが存在しない、またはセッション属性 "account" が存在しない場合
		if (session == null || session.getAttribute("account") == null) {
			
			// もしセッションが無効な場合は、ログインページにリダイレクトする
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
			return;
		}
		try {
			// 過去記事のデータを取得するためのデータベースを準備します
			ArticleDao dao = new ArticleDao();
			// データベースからすべての記事情報を取得して、（Listなのはデータが複数あるから）
			List<ArticleBean> articles = dao.getAllArticles();
			// リクエストに設定してJSPで表示できるようにします
			request.setAttribute("articles", articles);
			
			// 色情報を取得するためのデータベース準備
			ColorMasterDao colorDao = new ColorMasterDao();
			// データベースからgetAllColorsですべての色情報を取得して、
			List<ColorMasterBean> colors = colorDao.getAllColors();
			// getAllColorsを持ったcolorsをリクエストに設定してJSPで表示できるようにする
			request.setAttribute("colors", colors);
			
		} catch (NamingException | SQLException e) {
			errorMessages = "エラーが発生しました。";
			// もし例外が発生した場合は、エラーページに転送します
			request.setAttribute("errorMessage", errorMessages);
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			request.getRequestDispatcher(resultPage).forward(request, response);
			return;
		}
		// ログイン済みを確認された場合、INPUTJSPに転送します
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}
	
	/*
	 * ユーザーが入力した記事情報を一時的にセッション内に保存して、バリデーションエラーがない場合はCONFIRMに進む
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		String errorMessages = "";
		
		// セッションを取得して、存在しない場合は新しく作ります
		HttpSession session = request.getSession();
		
		// フォームに入力されたユーザーの情報（名前、Eメール、タイトル、本文、色など）を受け取ります
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		// セッション内に ArticleBean オブジェクトが存在しない場合
		if (articleBean == null) {
			// 新しく作って、sessionにセットします
			articleBean = new ArticleBean();
			session.setAttribute("ArticleBean", articleBean);
		}
		
		// 'クリア'ボタンのパラメータをチェック
		String clear = request.getParameter("clear");
		
		// ユーザーが「クリア」ボタンをクリックした場合、特定のフィールドをリセットします
		if ("クリア".equals(clear)) {
			// クリア時のデフォルト値
			articleBean.setName("");
			articleBean.setEmail("");
			articleBean.setTitle("");
			articleBean.setText("");
			articleBean.setColorId("3");
		
			try {
				// 色情報を取得
				ColorMasterDao colorDao = new ColorMasterDao();
				List<ColorMasterBean> colors = colorDao.getAllColors();
				request.setAttribute("colors", colors);
				
				// 過去記事データを取得
				 ArticleDao dao = new ArticleDao();
				 List<ArticleBean> articles = dao.getAllArticles();
				 request.setAttribute("articles", articles);
			} catch (NamingException | SQLException e) {
				request.setAttribute("errorMessage", e.getMessage());
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				request.getRequestDispatcher(resultPage).forward(request, response);
				return;
			}
			// ユーザーがフォームに入力したデータをセッション内に保存します
			session.setAttribute("ArticleBean", articleBean);
			// 問題ない場合INPUTJSPを表示します
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		
		// セッション内に ArticleBean オブジェクトが存在する場合
		} else {
			// ユーザーがフォームに入力したデータを取得して、それを articleBean オブジェクトの対応するフィールドにセットします
			articleBean.setName(request.getParameter("name"));
			articleBean.setEmail(request.getParameter("email"));
			articleBean.setTitle(request.getParameter("title"));
			articleBean.setText(request.getParameter("text"));
			articleBean.setColorId(request.getParameter("color"));
			
			try {
				// 色情報を取得
				ColorMasterDao colorDao = new ColorMasterDao();
				List<ColorMasterBean> colors = colorDao.getAllColors();
				request.setAttribute("colors", colors);
				
				// 過去記事データを取得
				 ArticleDao dao = new ArticleDao();
				 List<ArticleBean> articles = dao.getAllArticles();
				 request.setAttribute("articles", articles);
			} catch (NamingException | SQLException e) {
				errorMessages = "エラーが発生しました。";
				request.setAttribute("errorMessage", errorMessages);
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				request.getRequestDispatcher(resultPage).forward(request, response);
			}
			
			// 記事情報にバリデーションをかけて、エラーメッセージを表示できるよう準備します
			String  validationErrors = CommonFunction.validate(articleBean);
			
			// エラーメッセージが空じゃない場合、リクエストにセットして一覧画面に戻ります
			if (!validationErrors.isEmpty()) {
				request.setAttribute("validationErrors", validationErrors);
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
			}
			
			// 問題ない場合、入力情報をセッションに再設定してCOMFIRMにリダイレクトします
			session.setAttribute("ArticleBean", articleBean);
			
			resultPage = PropertyLoader.getProperty("url.bbs.confirm");
			response.sendRedirect(resultPage);
		}
	}
}