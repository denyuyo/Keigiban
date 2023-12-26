package jp.sljacademy.bbs;

// ログイン画面の表示

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			errorMessages = "<span style=\"color: red;\">IDが入力されていません。</span><br>";
			}
			if (password.isEmpty()) {
			errorMessages += "<span style=\"color: red;\">パスワードが入力されていません。</span><br>";
		}	
		return errorMessages;
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
			session.setAttribute("id", id);
			resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}

		
	}
}
