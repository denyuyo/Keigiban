package jp.sljacademy.bbs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class CompleteServlet
 */
// @WebServlet("/CompleteServlet")
public class CompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.complete");
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
				
		// セッションが存在しない、またはセッション属性 "account" が存在しない場合
		if (session == null || session.getAttribute("account") == null) {
			
			// ログインページにリダイレクトする
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
			return;
		}
		// 完了画面に転送
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*
	 * 一覧画面に戻るボタンが押された場合のみ、一覧画面へ遷移する
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 一覧画面に戻るボタンが押された場合
		String resultPage = PropertyLoader.getProperty("url.bbs.input");
		response.sendRedirect(resultPage);
		return;
	}
}