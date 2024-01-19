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
import jp.sljacademy.bbs.util.CommonFunction;
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
	// ユーザーが特定の条件を満たしている場合にセッション内のデータを利用してページを表示する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// URLのデフォルト値を設定
		String resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		
		String errorMessages = "";
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
		
		// セッションが存在しない、またはセッション属性 "account" が存在しない場合
		if (session == null || session.getAttribute("account") == null) {
			
			// もしセッションが無効な場合は、ログインページにリダイレクトする
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
			return;
		}
		
		/*
		 * referrerを使用。直前のページのURLを取得するための変数
		 */
		// リファラーを取得
		String referrer = request.getHeader("referer");
		resultPage = PropertyLoader.getProperty("url.bbs.input");
		// リファラーが存在せず、またはリファラーがresultPageと一致しない場合
		if (referrer == null || !referrer.contains(resultPage)) {
			// 一覧画面にリダイレクト
			response.sendRedirect(resultPage);
			return;
		}
		
		// セッションからユーザーが入力した記事情報を取得して、articleBean に代入
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		
		// ユーザーが入力したカラーIDを取得
		String colorId = articleBean.getColorId();
		
		try {
			// インスタンス
			ColorMasterDao colorDao = new ColorMasterDao();
			// ユーザーが選択した colorId をもとに、データベースから colorCode を取得
			String colorCode = colorDao.getColorCode(colorId);
			// 取得した colorCode を articleBean に設定します
			articleBean.setColorCode(colorCode);
		} catch(NamingException | SQLException e) {
			errorMessages = "エラーが発生しました。";
			request.setAttribute("errorMessage", errorMessages);
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			request.getRequestDispatcher(resultPage).forward(request, response);
			return;
		}
		// ユーザーが選択した色情報をセッション内に保存します
		session.setAttribute("ArticleBean", articleBean);
		
		resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		// 問題ない場合CONFIRMJSPを表示します
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*
	 * ユーザーがフォームを提出した場合にデータベースに記事を作成し、それが成功した場合には完了画面にリダイレクト
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		
		String errorMessages = "";
		
		// 投稿ボタンが押された場合
		if (request.getParameter("Submit") != null) {
			// セッションを取得し、存在しない場合は null を返す
			HttpSession session = request.getSession(false);
			// セッションから取得した記事情報を article に代入
			ArticleBean article = (ArticleBean) session.getAttribute("ArticleBean");
			
			// 記事情報のバリデーションを実行して、エラーメッセージを取得
			errorMessages = CommonFunction.validate(article);
			
			// エラーメッセージが空じゃない場合、リクエストにセットして一覧画面に戻る
			if (!errorMessages.isEmpty()) {
				resultPage = PropertyLoader.getProperty("url.bbs.input");
				request.setAttribute("errorMessages", errorMessages);
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
			}
			
			try {
				// データベースにアクセスするための dao を用意
				ArticleDao dao = new ArticleDao();
				// ArticleDao の createArticle メソッドを呼び出して、データベースに新しい記事を作成
				dao.createArticle(article);
				
				// まっさらな記事情報が入っている
				ArticleBean art = new ArticleBean();
				
				// 名前とEmailをユーザーが入力したものに設定
				art.setName(article.getName());
				art.setEmail(article.getEmail());
				
				// 更新したArticleBeanをセッションに再設定
				session.setAttribute("ArticleBean", art);
				
			} catch (NamingException | SQLException e) {
				errorMessages = "エラーが発生しました。";
				request.setAttribute("errorMessage", errorMessages);
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				response.sendRedirect(resultPage);
				return;
			}
			
			// 記事が作成できたら、完了画面にリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.complete");
			response.sendRedirect(resultPage);
			return;
			
		// 戻るボタンが押された場合
		} else if (request.getParameter("Back") != null) {
			resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
	}
}