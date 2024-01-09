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
		
		// input.jspのURLを取得
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// 現在のセッションを取得（新しいセッションは作成しない）
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、ユーザーIDがセッションにセットされているかを確認
		if (session != null && session.getAttribute("id") != null) {
			try {
		        ArticleDao dao = new ArticleDao();
		        List<ArticleBean> articles = dao.getAllArticles();
		        request.setAttribute("articles", articles);
		        
		        ColorMasterDao colorDao = new ColorMasterDao();
				List<ColorMasterBean> colors = colorDao.getAllColors();
				request.setAttribute("colors", colors);
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
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.input");
		
		// セッションを取得
		HttpSession session = request.getSession();
		
		// セッションからArticleBeanを取得（なければ新しく作成）
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		if (articleBean == null) {
			articleBean = new ArticleBean();
			session.setAttribute("ArticleBean", articleBean);
		}
		
		// 'クリア'ボタンのパラメータをチェック
		String clear = request.getParameter("clear");
		
		
		// 'クリア'ボタンが押された場合、特定のフィールドをリセット
		if ("クリア".equals(clear)) {
			articleBean.setName("");
			articleBean.setEmail("");
			articleBean.setTitle("");
			articleBean.setText("");
			articleBean.setColorId("");
			
			try {
				
				ColorMasterDao colorDao = new ColorMasterDao();
				List<ColorMasterBean> colors = colorDao.getAllColors();
				request.setAttribute("colors", colors);
			} catch (NamingException | SQLException e) {
				throw new ServletException("Database error", e);
			}
			
			// セッションスコープに更新したBeanをセット
			session.setAttribute("ArticleBean", articleBean);
			
			// 同じページにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
			
		} else {
			
			// 記事のフィールドのバリデーションを実行
			String validationErrors = CommonFunction.validateArticle(articleBean);
			
			// バリデーションエラーがある場合はエラーメッセージをセットし、入力フォームに戻る
			if(!validationErrors.isEmpty()) {
				request.setAttribute("errors", validationErrors);
				request.getRequestDispatcher(resultPage).forward(request, response);
				return;
			}
			
			// Eメールの形式をバリデーション
			if (!CommonFunction.isValidEmail(articleBean.getEmail())) {
				request.setAttribute("emailError", "不正なEメールアドレス形式です。");
				request.getRequestDispatcher(resultPage).forward(request, response);
				return;
			}
			// JNDIを使用してDataSourceを取得
			try {
				// 色情報を取得
				ColorMasterDao colorDao = new ColorMasterDao();
				List<ColorMasterBean> colors = colorDao.getAllColors();
				// request=一画面分やり取りしたら終わり（ユーザからサーバに処理が行って帰ってくる）
				request.setAttribute("colors", colors);
				
				 ArticleDao dao = new ArticleDao();
				 List<ArticleBean> articles = dao.getAllArticles();
				 request.setAttribute("articles", articles);
			} catch (Exception e) {
				throw new ServletException("Database error", e);
			}
					
			// 残りの記事情報をセット
			articleBean.setName(request.getParameter("name"));
			articleBean.setEmail(request.getParameter("email"));
			articleBean.setTitle(request.getParameter("title"));
			articleBean.setText(request.getParameter("text"));
			articleBean.setColorId(request.getParameter("color"));
			
			
			//入力情報をセッションに再設定
			session.setAttribute("ArticleBean", articleBean);
			
			resultPage = PropertyLoader.getProperty("url.bbs.confirm");
			response.sendRedirect(resultPage);
		}
	}
}