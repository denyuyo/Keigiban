package jp.sljacademy.bbs;

// 一覧画面の表示

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.bean.ColorMasterBean;
import jp.sljacademy.bbs.dao.ColorMasterDao;
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
			try {
	            // JNDIを使用してDataSourceを取得
	            Context initContext = new InitialContext();
	            Context envContext = (Context) initContext.lookup("java:/comp/env");
	            DataSource dataSource = (DataSource) envContext.lookup("jdbc/datasource");

	            // データベース接続
	            try (Connection connection = dataSource.getConnection()) {
	                ColorMasterDao colorDao = new ColorMasterDao(connection);
	                List<ColorMasterBean> colors = colorDao.getAllColors();
	                request.setAttribute("colors", colors);
	            }
	        } catch (NamingException | SQLException e) {
	            throw new ServletException("Database error", e);
	        }
			
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
		
		 String resultPage;
	        HttpSession session = request.getSession();
	        ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");

	        // 'クリア'ボタンが押された場合の処理
	        String clear = request.getParameter("clear");
	        if ("クリア".equals(clear)) {
	            // 省略（前のコードと同じ）
	        } else {
	            // 通常のフォーム送信処理
	            if (articleBean == null) {
	                articleBean = new ArticleBean();
	                session.setAttribute("ArticleBean", articleBean);
	            }

	            // JNDIを使用してDataSourceを取得
	            try {
	                Context initContext = new InitialContext();
	                Context envContext = (Context) initContext.lookup("java:/comp/env");
	                DataSource dataSource = (DataSource) envContext.lookup("jdbc/datasource");

	                // 色情報を取得
	                try (Connection connection = dataSource.getConnection()) {
	                    ColorMasterDao colorDao = new ColorMasterDao(connection);
	                    List<ColorMasterBean> colors = colorDao.getAllColors();
	                    request.setAttribute("colors", colors);
	                }
	            } catch (Exception e) {
	                throw new ServletException("Database error", e);
	            }

	            // 残りの記事情報をセット
	            articleBean.setName(request.getParameter("username"));
	            articleBean.setEmail(request.getParameter("email"));
	            articleBean.setTitle(request.getParameter("title"));
	            articleBean.setText(request.getParameter("text"));
	            articleBean.setColorId(request.getParameter("color"));

	            resultPage = PropertyLoader.getProperty("url.bbs.confirm");
	            response.sendRedirect(resultPage);
		}
	}
}