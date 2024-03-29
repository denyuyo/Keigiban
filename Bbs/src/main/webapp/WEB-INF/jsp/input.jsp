<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
   	import="jp.sljacademy.bbs.bean.ArticleBean"
   	import="jp.sljacademy.bbs.bean.ColorMasterBean"
   	import="jp.sljacademy.bbs.util.CommonFunction"
	import="java.text.SimpleDateFormat"
   	import="java.util.ArrayList"
   	import="java.util.Date"
   	import="java.util.Collections"
%>

<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	// セッションから過去記事を取得
	ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
	
	// サーブレットからリクエスト属性として設定された colors リスト（色情報）を取得
	ArrayList<ColorMasterBean> colors = (ArrayList<ColorMasterBean>) request.getAttribute("colors");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/master.css" type="text/css">
<title>記事入力画面</title>
</head>
<body>
	<header> 掲示板 </header>
	
	<form action="/Bbs/InputServlet" method="post" id="form">
		<table class="inputArticle">
			<!-- エラーメッセージ（errorMessages）がリクエスト属性に設定されている場合、エラーメッセージを表示 -->
			<% if (request.getAttribute("errorMessages") != null) { %>
				<p class="error" style="color: red;"><%= request.getAttribute("errorMessages") %></p>
			<% } %>
			<tr>
				<td class=itemName id="name">名前</td>
				<td><input type="text" name="name" value="<%= articleBean.getName() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="email" >E-mail</td>
				<td><input type="text" name="email" value="<%= articleBean.getEmail() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="title">タイトル</td>
				<td><input type="text" name="title" value="<%= articleBean.getTitle() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="text">本文</td>
				<td><textarea name="text" cols="35" rows="5" ><%= articleBean.getText() %></textarea>
				</td>
				
			<tr>
				<td class="itemName" id="color">文字色</td>
				<td>
					<%-- 色情報のリスト（colors）をループして、各色に対するラジオボタンを生成 --%>
					<% for (ColorMasterBean color : colors) { %>
						<%-- ラジオボタンを表現し、name="color"でグループ化することで、同じname属性を持つラジオボタンは1つだけが選択できる --%>
						<input class="radio" type="radio" name="color" id="color_<%= color.getColorId() %>" value="<%= color.getColorId() %>"
							<%-- 
								colorIdとColorMasterBeanオブジェクトのcolorIdが一致する場合、ラジオボタンを選択済み（checked）にする 
								真：checked　偽：空文字 
							--%>
							<%= articleBean.getColorId().equals(color.getColorId()) ? "checked" : "" %>>
							
							<%-- ラジオボタンの id 属性と for 属性を関連付けて、対応するカラーコードを取得する --%>
							<label for="color_<%= color.getColorId() %>" style="color: #<%= color.getColorCode() %>;">
								<%= color.getColorName() %>
							</label>
					<% } %>

				</td>
			</tr>
			
		</table>
		<input class="button" type="submit" name="Submit" value="確認">
		<input class="button" type="submit" name="clear" value="クリア">
	</form>
	<hr>
	<%-- 取得した過去記事リスト内の各記事をループして表示 --%>
	<% 
		ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) request.getAttribute("articles");
		for (ArticleBean article : articles) {
	%>
	<table class="postedArticle"  style="color: #<%= article.getColorCode() %>">
		<tr>
			<td><%= article.getArticleId() %></td>
			<td><%= article.getTitleView() %></td>
		</tr>
		<tr>
			<td colspan="2"><%= article.getTextView() %></td>
		</tr>
		<tr>
			<td colspan="2">
				<%= article.getCreateDateView() %>&emsp;
				<% String email = article.getEmail(); %>
				
				<%-- メールアドレスがnullでなく、空白でないかをチェック --%>
				<% if (email != null && !email.isEmpty()) { %>
				<%-- メールアドレスが有効な場合にはメールリンクを生成し、無効な場合には名前だけを表示する --%>
					<a href="mailto:<%= email %>"><%= article.getNameView() %></a>
				<% } else { %>
					<%= article.getNameView() %>
				<% } %>
			</td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>