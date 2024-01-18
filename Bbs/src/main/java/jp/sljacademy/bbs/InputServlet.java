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
	
	private void fetchAndSetData(HttpServletRequest request) throws NamingException, SQLException {
		// 色情報を取得
		ColorMasterDao colorDao = new ColorMasterDao();
		List<ColorMasterBean> colors = colorDao.getAllColors();
		request.setAttribute("colors", colors);
		
		// 過去記事データを取得
		ArticleDao dao = new ArticleDao();
		List<ArticleBean> articles = dao.getAllArticles();
		request.setAttribute("articles", articles);
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
		// ログインしている場合
		try {
			fetchAndSetData(request);
			
		} catch (Exception e) {
			errorMessages = "エラーが発生しました。";
			// もし例外が発生した場合は、エラーページに転送します
			request.setAttribute("errorMessage", errorMessages);
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			request.getRequestDispatcher(resultPage).forward(request, response);
			return;
		}
		// ログイン済みを確認されたので、INPUTJSPに転送します
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
		
		// 記事情報を初期化
		ArticleBean articleBean =  null;
		
		// ユーザーが「クリア」ボタンをクリックした場合、特定のフィールドをリセットします
		if (request.getParameter("clear") != null) {
			// クリア時のデフォルト値
			articleBean = new ArticleBean();
			articleBean.setName("");
			articleBean.setEmail("");
		
			try {
				 fetchAndSetData(request);
			} catch (NamingException | SQLException e) {
				errorMessages = "エラーが発生しました。";
				// もし例外が発生した場合は、エラーページに転送します
				request.setAttribute("errorMessage", errorMessages);
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
		
		// 確認ボタンが押された場合
		} else if(request.getParameter("Submit") != null) {
			// ユーザーがフォームに入力したデータを取得して、それを articleBean オブジェクトの対応するフィールドにセットします
			articleBean = new ArticleBean();
			articleBean.setName(request.getParameter("name"));
			articleBean.setEmail(request.getParameter("email"));
			articleBean.setTitle(request.getParameter("title"));
			articleBean.setText(request.getParameter("text"));
			articleBean.setColorId(request.getParameter("color"));
			
			
			// 記事情報にバリデーションをかけて、エラーメッセージを表示できるよう準備します
			errorMessages = CommonFunction.validate(articleBean);
			
			// エラーメッセージが空じゃない場合、リクエストにセットして一覧画面に戻ります
			if (!errorMessages.isEmpty()) {
				
				request.setAttribute("errorMessages", errorMessages);
				
				try {
					 fetchAndSetData(request);
				} catch (Exception e) {
					errorMessages = "エラーが発生しました。";
					// もし例外が発生した場合は、エラーページに転送します
					request.setAttribute("errorMessage", errorMessages);
					resultPage = PropertyLoader.getProperty("url.jsp.error");
					request.getRequestDispatcher(resultPage).forward(request, response);
					return;
				}
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