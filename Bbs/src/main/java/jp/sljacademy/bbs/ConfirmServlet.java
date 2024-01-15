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
	// 目的：ユーザーが特定の条件を満たしている場合にセッション内のデータを利用してページを表示する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// PropertyLoader クラスの getProperty メソッドを呼び出して、"url.jsp.confirm" プロパティの値を取得し、resultPage に格納
		String resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
				
		// セッションが存在し、かつセッション属性 "id" が存在している場合
		if (session != null && session.getAttribute("id") != null) {
			
			// セッションから "ArticleBean" という名前の属性を取得し、それを articleBean 変数に代入
			ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
			
			// articleBean オブジェクトからユーザーが選択した色のID（colorId）を取得
			String colorId = articleBean.getColorId();
			
			// ユーザーが選択した色に関連する情報をデータベースから取得し、それをセッション内の articleBean オブジェクトに設定する
			try {
				// ColorMasterDao クラスの新しいインスタンス（オブジェクト）を作成
				ColorMasterDao colorDao = new ColorMasterDao();
				// colorDao インスタンスを使用して、ユーザーが選択した色のID (colorId) をもとに、データベースから色のコード (colorCode) を取得
				String colorCode = colorDao.getColorCode(colorId);
				// 取得した colorCode を articleBean オブジェクトに設定して、articleBean オブジェクトに選択した色のコードを関連付ける
				articleBean.setColorCode(colorCode);
			} catch(NamingException | SQLException e) {
				/*
				 * printStackTrace()：エラーが発生した際にスタックトレース（エラーの詳細な情報）を表示するために使用されるメソッド
				 */
				e.printStackTrace();
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				response.sendRedirect(resultPage);
				
				/*
				 * セッション（HttpSession）にデータを保存。このデータの名前（キー）は "ArticleBean" で、値は articleBean オブジェクト
				 */
				// ユーザーが選択した色情報をsessionに関連付け、articleBean オブジェクトに保存する
				session.setAttribute("ArticleBean", articleBean);
			}
			
			// 設定したJSPページにリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		// url直書きでの遷移を阻止
		} else {
			// セッションが無効な場合は、ログインページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字エンコーディングをUTF-8（多言語対応）に設定
		request.setCharacterEncoding("UTF-8");
		// ユーザーに表示するHTMLのコンテンツタイプもUTF-8に設定
		response.setContentType("text/html;charset=UTF-8");
		
		// セッションを取得し、存在しない場合は新しく作る
		HttpSession session = request.getSession();
		
		String resultPage = PropertyLoader.getProperty("url.bbs.input");
		
		/*
		 * 'setAttribute'
		 * セッションに情報を保存するための命令をするメソッド
		 * 2つの引数がある。最初はデータを取り出すための名前（キー）、2番目に実際のデータ（この場合は articleBean オブジェクト）を指定する
		 */
				
		// セッションからArticleBeanオブジェクトを取得、存在しない場合は新しく作る
		ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
		if (articleBean == null) {
			articleBean = new ArticleBean();
			// セッションに ArticleBean ラベル名で articleBean オブジェクト（ユーザーが投稿しようとしている記事情報）を保存して、後でセッションから取り出せるようにしている
			session.setAttribute("ArticleBean", articleBean);
		}
		 
		// "Submit" ボタンが押された場合
		 if (request.getParameter("Submit") != null) {
			// セッションを取得し、存在しない場合は null を返す
			 session = request.getSession(false);
			 // ArticleBean クラス型の変数 article に、セッションから取得した "ArticleBean"（記事情報）を代入
			 ArticleBean article = (ArticleBean) session.getAttribute("ArticleBean");
			 
			 // ユーザーが入力した記事情報がある場合、データベースに保存
			 if (article != null) {
				 try {
					// 新しい ArticleDao オブジェクトを作成し、データベースにアクセスするための dao 変数を用意
					ArticleDao dao = new ArticleDao();
					// dao オブジェクトの createArticle メソッドを呼び出し、article オブジェクトを引数として渡してデータベースに新しい記事を作成
					dao.createArticle(article);
					// セッション内の "ArticleBean" 属性に articleBean オブジェクトを再度設定して、記事情報を更新
					session.setAttribute("ArticleBean", articleBean);
				 } catch (NamingException | SQLException e) {
					 // 詳細なエラーメッセージを表示
					 e.printStackTrace();
					 resultPage = PropertyLoader.getProperty("url.jsp.error");
					 // エラーページに転送
					 response.sendRedirect(resultPage);
				}
				 
				// CommonFunction クラスの validate メソッドを呼び出して、articleBean オブジェクトのバリデーションを実行し、エラーメッセージを取得
				String  validationErrors = CommonFunction.validate(articleBean);
				
				// ユーザーが入力したEメールアドレスの形式が正しいかどうかをチェック
				if (!CommonFunction.checkEmail(articleBean.getEmail())) {
					// Eメールの形式が不正な場合、エラーメッセージをリクエストオブジェクトに設定して表示
					request.setAttribute("emailError", "不正なEメールアドレス形式です。");
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				}
				
				// 本文が空でないかチェック
				if (!CommonFunction.isNotBlank(articleBean.getText())) {
					request.setAttribute("textError", "本文を入力してください。");
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				}
				
				/*
				 *  バリデーションエラーがある場合、エラーメッセージをリクエストに設定し、エラーページに転送
				 */
				// validationErrors 変数に格納されたバリデーションエラーメッセージが空でないかを確認
				if (!validationErrors.isEmpty()) {
					request.setAttribute("validationErrors", validationErrors);
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				}
				// 記事が作成できたら、完了画面に遷移
				resultPage = PropertyLoader.getProperty("url.bbs.complete");
				response.sendRedirect(resultPage);
			}
		// // "Back" ボタンが押された場合
		} else if (request.getParameter("Back") != null) {
			resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
		 
	}
}