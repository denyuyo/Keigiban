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
		
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// 'クリア'ボタンのパラメータをチェック
		String clear = request.getParameter("clear");
		
		// セッションを取得（新しく作成される可能性あり）
		HttpSession session = request.getSession();
		
		// 'クリア'ボタンが押された場合、特定のフィールドをリセット
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		if ("クリア".equals(clear)) {
			articleBean.setTitle("");
			articleBean.setText("");
			articleBean.setColorId(""); // 初期値にリセット
			
			// 同じページにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}else {
			// 以下は通常のフォーム処理
			articleBean.setName(request.getParameter("name"));
			articleBean.setEmail(request.getParameter("email"));
			articleBean.setTitle(request.getParameter("title"));
			articleBean.setText(request.getParameter("text"));
			articleBean.setColorId(request.getParameter("color"));	
			
			// 確認ページへリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.confirm");
			response.sendRedirect(resultPage);
		}
	}
}