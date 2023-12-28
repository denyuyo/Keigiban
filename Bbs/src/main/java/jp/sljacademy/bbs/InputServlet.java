package jp.sljacademy.bbs;

// 一覧画面の表示

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
 * Servlet implementation class InputServlet
 */
// @WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public InputServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// input.jspのURLを取得
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// 現在のセッションを取得（新しいセッションは作成しない）
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、ユーザーIDがセッションにセットされているかを確認
		if (session != null && session.getAttribute("id") != null) {
			// 色の情報を取得してリクエストスコープにセット
			
			
			// 設定したJSPページにリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
		} else {
			// セッションが無効な場合は、ログインページなどの別のページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// フォームから送信された各パラメータを取得
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String color = request.getParameter("color");
		
		// 新しいArticleBeanインスタンスを作成し、取得したデータをセット
		ArticleBean articleBean = new ArticleBean();
		articleBean.setName(name);
		articleBean.setEmail(email);
		articleBean.setTitle(title);
		articleBean.setText(text);
		articleBean.setColorId(color);
		
		// 新しいセッションを取得（存在しない場合は新しく作成）
		HttpSession session = request.getSession();
		// セッションにArticleBeanインスタンスをセット
		session.setAttribute("ArticleBean", articleBean);
		
		// 確認ページのURLを取得し、そのページにリダイレクト
		String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		response.sendRedirect(resultPage);
	}
}
