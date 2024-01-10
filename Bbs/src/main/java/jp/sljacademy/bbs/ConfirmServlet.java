package jp.sljacademy.bbs;

// 確認画面の表示

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.dao.ArticleDao;
import jp.sljacademy.bbs.dao.ColorMasterDao;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class ConfirmServlet
 */
// @WebServlet("/ConfirmServlet")
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfirmServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		
		// セッションを取得
		HttpSession session = request.getSession(false);
				
		// セッションが存在し、ユーザーIDがセッションにセットされているかを確認
		if (session != null && session.getAttribute("id") != null) {
			
			// セッションからArticleBeanを取得
			ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
			
			// ユーザが選択したcolorIdを取得
			String colorId = articleBean.getColorId();
			
			//colorIdからcolorcodeを取得
			try {
				ColorMasterDao colorDao = new ColorMasterDao();
				String colorCode = colorDao.getColorCode(colorId);
				// colorCodeをArticleBeanにセット
				articleBean.setColorCode(colorCode);
			} catch(NamingException | SQLException e) {
				e.printStackTrace();
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				response.sendRedirect(resultPage);
				
				//入力情報をセッションに再設定
				session.setAttribute("ArticleBean", articleBean);
			}
			
			// 設定したJSPページにリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		} else {
			// セッションが無効な場合は、ログインページなどの別のページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// セッションを取得
		HttpSession session = request.getSession();
				
		// セッションからArticleBeanオブジェクトを取得（存在しない場合は新規作成）
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		if (articleBean == null) {
			articleBean = new ArticleBean();
			session.setAttribute("ArticleBean", articleBean);
		}
		 
		// ユーザーが「送信」ボタンを押した場合（Submit）
		 if (request.getParameter("Submit") != null) {
			 session = request.getSession(false);
			 ArticleBean article = (ArticleBean) session.getAttribute("ArticleBean");
			 
			 //　データベースに記事情報を保存する
			 if (article != null) {
				 try {
					ArticleDao dao = new ArticleDao();
					dao.createArticle(article);
					session.setAttribute("ArticleBean", articleBean);
				 } catch (NamingException | SQLException e) {
					 e.printStackTrace();
					 String resultPage = PropertyLoader.getProperty("url.jsp.error");
					 response.sendRedirect(resultPage);
				}
				// 記事が作成できたら、CompleteServletに案内する
				 response.sendRedirect(PropertyLoader.getProperty("url.bbs.complete"));
			}
		} else if (request.getParameter("Back") != null) {
			// 「戻る」ボタンを押した場合
	        // InputServletに案内する
			String resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
	}
}