<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
   	import="jp.sljacademy.bbs.bean.ArticleBean"
   	import="jp.sljacademy.bbs.bean.ColorMasterBean"
   	import="jp.sljacademy.bbs.util.CommonFunction"
	import="java.text.SimpleDateFormat"
   	import="java.util.List"
   	import="java.util.Date"
%>

<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
	// 選択された色のIDを取得
	List<ColorMasterBean> colors = (List<ColorMasterBean>) request.getAttribute("colors");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/master.css" type="text/css">
<title>記事入力画面</title>
<script>
    function changeColor(colorCode) {
        var elements = document.querySelectorAll('.inputArticle td input, .inputArticle td textarea');
        for (var i = 0; i < elements.length; i++) {
            elements[i].style.color = '#' + colorCode;
        }
    }
</script>
</head>
<body>
	<header> 掲示板 </header>
	
	<form action="/Bbs/InputServlet" method="post" id="form">
		<table class="inputArticle">
		<!-- もしリクエストの属性に "validationErrors" という名前で何かが設定されていたら -->
		<% if (request.getAttribute("validationErrors") != null) { %>
			<p class="error" style="color: red;"><%= request.getAttribute("validationErrors") %></p>
		<% } %>
		<% if (request.getAttribute("textError") != null) { %>
			<p class="error" style="color: red;"><%= request.getAttribute("textError") %></p>
		<% } %>
		<% if (request.getAttribute("emailError") != null) { %>
			<p class="error" style="color: red;"><%= request.getAttribute("emailError") %></p>
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
					
						<input class="radio" type="radio" name="color" value="<%= color.getColorId() %>"
							onclick="changeColor('<%= color.getColorCode() %>')"
							<%-- ユーザが選択したcolorIdとデータベースのが同じかチェックしてる --%>
							<%= articleBean.getColorId().equals(color.getColorId()) ? "checked" : "" %> >
							
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
	<%-- 記事情報のリストをリクエストスコープから取得 --%>
	<% 
		List<ArticleBean> articles = (List<ArticleBean>) request.getAttribute("articles");
		if (articles != null && !articles.isEmpty()) {
			for (ArticleBean article : articles) {
	%>
	<table class="postedArticle"  style="color: #<%= article.getColorCode() %>">
		<tr>
			<td><%= article.getArticleId() %></td>
			<td><%= CommonFunction.getDefaultTitle(article.getTitle()) %></td>
		</tr>
		<tr>
			<td colspan="2"><%= CommonFunction.convertLineBreaksToHtml(article.getText()) %></td>
		</tr>
		<tr>
			<td colspan="2">
				<%= article.getCreateDateView() %>&emsp;
				<a href="mailto:<%= article.getEmail() %>"><%= CommonFunction.getDefaultName(article.getName()) %></a>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>