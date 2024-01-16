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
	/*
	 * クラスのシリアル化に関する制御を提供し、異なるバージョン間での互換性を維持するために使用
	 * serialVersionUID フィールド：クラスがシリアル化可能であることを示すために使用
	 * static：修飾子がついているため、すべてのインスタンスが共有する静的変数
	 * final：修飾子がついているため、一度値が設定された後は変更できない定数
	 * long 型の変数で、シリアルバージョンUIDは通常整数値。1L は単なる数値で、異なるクラスバージョンを識別するために一意な値を表す
	 */
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
	
	/*
	 * UTF-8：異なる言語を含むテキストデータを正確に表現するための重要なエンコーディング方式
	 * 
	 * 'request'
	 * クライアントからの要求情報を受け取るオブジェクト
	 * クライアントが要求したURL、送信されたデータ、HTTPメソッド（GETやPOSTなど）、ヘッダー情報などが含まれる
	 * 
	 * 'response'
	 * サーバーからの応答情報をクライアントに送信するオブジェクト
	 * クライアントに送信するデータ、レスポンスのステータスコード（成功、リダイレクト、エラーなど）、コンテンツタイプ（HTML、JSON、画像など）、およびヘッダー情報などが含まれる
	 * 
	 * setContentType：HTTPレスポンスに含まれるデータの種類をクライアントに伝え、クライアントがそれを正しく処理できるようにするメソッド
	 * Content-Type：クライアントに対して送信されるデータの種類を示す。HTMLページの場合は "text/html"、画像の場合は "image/jpeg" などが設定される
	 * 
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// テキストデータがUTF-8でエンコード（符号化）されるよう指定
		request.setCharacterEncoding("UTF-8");
		// レスポンスのコンテンツタイプと文字エンコーディングを設定
		response.setContentType("text/html;charset=UTF-8");
		
		// PropertyLoader クラスの getProperty メソッドを呼び出して、"url.jsp.complete" プロパティの値を取得し、resultPage に格納
		String resultPage = PropertyLoader.getProperty("url.bbs.confirm");
		
		/*
		 * referrer：request.getHeader("referer") を使用してリファラー（直前のページのURL）を取得するための変数
		 */
		// リファラーを取得
		String referrer = request.getHeader("referer");
		
		// リファラーが存在せず、またはリファラーが "resultPage" と一致しない場合
		if (referrer == null || !referrer.contains(resultPage)) {
			// 一覧画面にリダイレクト
			response.sendRedirect(resultPage);
			return;
		}
		
		/*
		 * HttpSession：ユーザーのセッション管理とデータの一時的な保存に使用されるオブジェクト
		 * false：セッションが存在しない場合に新しいセッションを作成しない。既存のセッションがあればそれを返し、ない場合は null を返す
		 */
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
				
		// セッションが存在し、かつセッション属性 "id" が存在している場合
		if (session != null && session.getAttribute("id") != null) {
			
			/*
			 * RequestDispatcher：リクエストを別のリソース（通常は別のJSPページまたはサーブレット）に転送するためのオブジェクト
			 * request.getRequestDispatcher(resultPage) ：resultPage に格納されている文字列を使用して、リクエストを転送先のリソースに送るための RequestDispatcher オブジェクトを作成
			 * dispatcher.forward(request, response)：作成した RequestDispatcher オブジェクトを使用して、リクエストとレスポンスを指定されたリソースに転送
			 */
			resultPage = PropertyLoader.getProperty("url.jsp.complete");
			// 設定したJSPページにリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			// リクエスト転送後にメソッドの実行を終了
			return;
		} else {
			/*
			 * sendRedirect：JavaのServletで使用され、HTTPリクエストを別のURLにリダイレクトするために使用されるメソッド
			 */
			// セッションが無効な場合は、ログインページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.bbs.index");
			response.sendRedirect(resultPage);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// 目的：フォームのデータを処理し、"Back" ボタンが押された場合に特定の処理を行う
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * getParameter：JavaのServletで使用され、HTTPリクエストからクライアントが送信したパラメータを取得するために使用されるメソッド
		 */
		
		// "Back" ボタンが押された場合
		if(request.getParameter("Back") != null) {
			
			// セッションを取得し、存在しない場合は新しく作る
			HttpSession session = request.getSession();
			// セッションから "ArticleBean" という名前の属性を取得し、それを articleBean 変数に代入
			ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");	
			
			// articleBeanに記入がある場合
			if (articleBean != null) {
				// titleとtextを空白に設定
				articleBean.setTitle("");
				articleBean.setText("");	
				
				// 更新したArticleBeanをセッションに再設定
				session.setAttribute("ArticleBean", articleBean);
			}
			String resultPage = PropertyLoader.getProperty("url.bbs.input");
			response.sendRedirect(resultPage);
		}
	}
}