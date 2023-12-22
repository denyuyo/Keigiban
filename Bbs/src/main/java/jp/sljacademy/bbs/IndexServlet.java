package jp.sljacademy.bbs;

// ログイン画面の表示

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class IndexServlet
 */
// @WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.jsp.index");
		
//		try {
//			AccountDao dao = new AccountDao();
//			ArrayList<AccountBean> AccountList = dao.getAccountList();
//			request.setAttribute("AccountList", AccountList);
//			resultPage = PropertyLoader.getProperty("url.jsp.index");
//		} catch (NamingException e) {
//			request.setAttribute("errorMessage", e.getMessage());
//		} catch (SQLException e) {
//			request.setAttribute("errorMessage", e.getMessage());
//		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
