package jp.sljacademy.bbs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.ArticleBean;
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
		
		String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		
		// リファラーを取得
		String referrer = request.getHeader("referer");
		
		// リファラーが存在せず、またはリファラーが "resultPage" と一致しない場合
		if (referrer == null || !referrer.contains(resultPage)) {
			// 一覧画面にリダイレクト
			response.sendRedirect(resultPage);
			return;
		}
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
				
		// セッションが存在しない、またはセッション属性 "account" が存在しない場合
		if (session == null || session.getAttribute("account") == null) {
			
			// もしセッションが無効な場合は、ログインページにリダイレクトする
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
			return;
		}
		resultPage = PropertyLoader.getProperty("url.jsp.complete");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// 目的：フォームのデータを処理し、"Back" ボタンが押された場合に特定の処理を行う
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// "Back" ボタンが押された場合
		if(request.getParameter("Back") != null) {
			
			// セッションを取得し、存在しない場合は新しく作る
			HttpSession session = request.getSession();
			// セッションからユーザーが入力した記事情報を取得して、articleBean に代入
			ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");	
			
			// articleBeanに記入がある場合
			if (articleBean != null) {
				// titleとtextを空白に設定
				articleBean.setTitle("");
				articleBean.setText("");	
				
				// 更新したArticleBeanをセッションに再設定
				session.setAttribute("ArticleBean", articleBean);
			}
			// 問題なければINPUT画面にリダイレクト
			String resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
	}
}