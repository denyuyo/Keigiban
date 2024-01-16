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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// リクエストの処理後にフォワードするページのURLを resultPage 変数に設定
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、かつセッション属性 "id" が存在している場合
		if (session != null && session.getAttribute("id") != null) {
			try {
				// 過去の記事データを取得
				ArticleDao dao = new ArticleDao();
				// データベースからすべての記事情報を取得し、 ArticleBean オブジェクトのリストとして返す
				List<ArticleBean> articles = dao.getAllArticles();
				// 取得した記事情報をリクエスト属性に設定し、JSPで表示できるようにする
				request.setAttribute("articles", articles);
				
				// 色情報を取得
				ColorMasterDao colorDao = new ColorMasterDao();
				// データベースからすべての色情報を取得し、 ColorMasterBean オブジェクトのリストとして返す
				List<ColorMasterBean> colors = colorDao.getAllColors();
				// 取得した色情報をリクエスト属性に設定し、JSPで表示できるようにする
				request.setAttribute("colors", colors);
			} catch (NamingException | SQLException e) {
				// エラーメッセージをリクエストに設定
				request.setAttribute("errorMessage", e.getMessage());
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				// エラーページに転送
				request.getRequestDispatcher(resultPage).forward(request, response);
			}
			// 設定したJSPページにリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		} else {
			// セッションが無効な場合は、ログインページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// セッションを取得し、存在しない場合は新しく作る
		HttpSession session = request.getSession();
		
		/*
		 * 目的：ユーザーが入力した記事情報をセッション内に保存するため、新しい ArticleBean オブジェクトを作成し設定
		 */
		// セッションからArticleBeanオブジェクトを取得、存在しない場合は新しく作る
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		// セッション内に ArticleBean オブジェクトが存在しない場合
		if (articleBean == null) {
			articleBean = new ArticleBean();
			// セッション内に ArticleBean オブジェクトを保存し、後でセッションから取り出せるようにしている
			session.setAttribute("ArticleBean", articleBean);
		}
		
		// 'クリア'ボタンのパラメータをチェック
		String clear = request.getParameter("clear");
		
		
		// ユーザーが「クリア」ボタンをクリックした場合（つまり、clear の値が "クリア" と等しい）、特定のフィールドをリセット
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
				
				// 過去の記事データを取得
				 ArticleDao dao = new ArticleDao();
				 List<ArticleBean> articles = dao.getAllArticles();
				 request.setAttribute("articles", articles);
			} catch (NamingException | SQLException e) {
				// エラーメッセージをリクエストに設定
				request.setAttribute("errorMessage", e.getMessage());
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				// エラーページに転送
				request.getRequestDispatcher(resultPage).forward(request, response);
			}
			// ユーザーがフォームに入力したデータをセッション内に保存し、それを他のページや機能で利用できるようにする
			session.setAttribute("ArticleBean", articleBean);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		
		// 目的：ユーザーが入力した記事をセッションに一時保存し、バリデーション通過で次のステップへ進む
		} else {
					
			// ユーザーがフォームから提供したデータを取得し、それを articleBean オブジェクトの対応するフィールドにセット
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
				
				// 過去の記事データを取得
				 ArticleDao dao = new ArticleDao();
				 List<ArticleBean> articles = dao.getAllArticles();
				 request.setAttribute("articles", articles);
			} catch (NamingException | SQLException e) {
				// エラーメッセージをリクエストに設定
				request.setAttribute("errorMessage", e.getMessage());
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				// エラーページに転送
				request.getRequestDispatcher(resultPage).forward(request, response);
			}
			
			// articleBean オブジェクトのバリデーションを実行し、バリデーションエラーメッセージを取得して validationErrors に格納
			String  validationErrors = CommonFunction.validate(articleBean);
			
			// バリデーションエラーメッセージが空じゃない場合、リクエストにセットして一覧画面に戻る
			if (!validationErrors.isEmpty()) {
				request.setAttribute("validationErrors", validationErrors);
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
			}
			
			//入力情報をセッションに再設定
			session.setAttribute("ArticleBean", articleBean);
			
			resultPage = PropertyLoader.getProperty("url.bbs.confirm");
			response.sendRedirect(resultPage);
		}
	}
}