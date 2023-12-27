package jp.sljacademy.bbs;

// 一覧画面の表示

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class InputServlet
 */
// @WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InputServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// sessionを取得してる。false=取得できなかったらnullを返す
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、かつログイン状態の場合に一覧画面に遷移
		if (session != null && session.getAttribute("id") != null) {
			
			// urlをセットしてる
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			// forward=データも一緒に転送（jspはforwardでいかなきゃいけない）
			dispatcher.forward(request, response);
			return;
			
		} else {
			// ログインしていない場合はログイン画面にリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			// 転送
			response.sendRedirect(resultPage);
			return;
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Submitボタンが押されたときの処理
		 String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		    response.sendRedirect(resultPage);
		    		    
	}
}
