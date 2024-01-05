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
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		
		// セッションを取得
		HttpSession session = request.getSession();
						
		// セッションからArticleBeanを取得
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		
		// 入力フォームの値をArticleBeanにセット
		articleBean.setColorCode(request.getParameter("colorcode"));
		
		//入力情報をセッションに再設定
		session.setAttribute("ArticleBean", articleBean);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// フォームから送信されたアクションを取得
		String action = request.getParameter("action");
		
		// セッションを取得
		HttpSession session = request.getSession();
				
		// セッションからArticleBeanを取得（なければ新しく作成）
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		if (articleBean == null) {
			articleBean = new ArticleBean();
			session.setAttribute("ArticleBean", articleBean);
		}
		
		 request.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html;charset=UTF-8");
		    
		    if ("投稿".equals(action)) {
		        session = request.getSession(false);
		        ArticleBean article = (ArticleBean) session.getAttribute("ArticleBean");
		        
		        if (article != null) {
		            try {
		                ArticleDao dao = new ArticleDao();
		                dao.createArticle(article);
		                session.removeAttribute("ArticleBean");
		            } catch (NamingException | SQLException e) {
		                e.printStackTrace();
		                throw new ServletException("Database error during article creation", e);
		            }
		            response.sendRedirect(PropertyLoader.getProperty("url.bbs.complete"));
		        }
		    } else if ("戻る".equals(action)) {
		        session = request.getSession();
		        articleBean = (ArticleBean) session.getAttribute("ArticleBean");

		        if (articleBean == null) {
		            articleBean = new ArticleBean();
		            session.setAttribute("ArticleBean", articleBean);
		        }

		        // 入力フォームの値をArticleBeanにセット
		        articleBean.setTitle(request.getParameter("title"));
		        articleBean.setText(request.getParameter("text"));
		        articleBean.setColorId(request.getParameter("colorcode"));
		        
		        //入力情報をセッションに再設定
				session.setAttribute("ArticleBean", articleBean);
				
				String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		        response.sendRedirect(resultPage);
		    }
	}
}