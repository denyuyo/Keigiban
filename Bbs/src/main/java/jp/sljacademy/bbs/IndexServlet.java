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
import jp.sljacademy.bbs.dao.AccountDao;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class IndexServlet
 */
// @WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String check(String id, String password) {
		String errorMessages = "";
		
		if (id.isEmpty()) {
			errorMessages = "IDが入力されていません。<br>";
			}
			if (password.isEmpty()) {
			errorMessages += "パスワードが入力されていません。<br>";
		}	
		return errorMessages;
	}
	
	// 入力されたIDとパスワードが有効かどうかを検証するメソッド
		@SuppressWarnings("unused")
		private boolean isAccountValid(String id, String password) {
		    try {
		        // データベースアクセスオブジェクトの作成
		        AccountDao dao = new AccountDao();
		        
		        // データベースから指定されたIDとパスワードに一致するアカウント情報を取得
		        AccountBean account = dao.getAccount(id, password);
		        
		        // アカウント情報が存在し、かつパスワードが一致している場合はtrueを返す
		        return (account != null && account.getPassword().equals(password));
		    } catch (NamingException | SQLException e) {
		        // エラーが発生した場合はログに記録する（適切な処理が必要）
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
		String resultPage = PropertyLoader.getProperty("url.jsp.index");
		
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、かつログイン状態の場合に一覧画面に遷移
		if (session != null && session.getAttribute("id") != null) {
			resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
			return;
			} else {
				// ログインしていない場合はログイン画面にリダイレクト
				resultPage = PropertyLoader.getProperty("url.jsp.index");
				request.getRequestDispatcher(resultPage).forward(request, response);
				return;
			}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.index");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// エラーメッセージ
		String errorMessages = check(id, password);
		
		// エラーがある場合 
		if (!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		// エラーがない場合 
		} else {
			HttpSession session = request.getSession(true);
			try {
				AccountDao dao = new AccountDao();
				AccountBean account = dao.getAccount(id, password);
				// アカウントが存在しない場合
				if (account == null) {
					// ログインできませんというエラーメッセージを設定
					request.setAttribute("errorMessages", "ログインできません");
					// エラーページにフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				}
				// アカウントが存在する場合はセッションに情報を設定
				session.setAttribute("id", id);
				request.setAttribute("Account", account);
				} catch (NamingException e) { 
					request.setAttribute("errorMessage", e.getMessage());
				} catch (SQLException e) { 
					request.setAttribute("errorMessage", e.getMessage());
				}
			
			resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
	}
}
