<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="jp.sljacademy.bbs.bean.ArticleBean"
    import="jp.sljacademy.bbs.util.CommonFunction" %>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/master.css" type="text/css">
	<title>掲示板</title>
</head>
<body>
	<header>
		掲示板
	</header>
	<div class="main-content">
		<p>以下の内容で投稿します。</p>
		<form action="/Bbs/ConfirmServlet" method="post"  style="color: #<%= articleBean.getColorCode() %>">
			<table class="inputArticle">
				<tr>
					<td class="itemName">名前</td>
					<!-- getParameter=ユーザーがform等に入力した値を取得 -->
					<td><%= CommonFunction.getDefault(articleBean.getName(), "nobody") %></td>
				</tr>
				<tr>
					<td class="itemName">E-mail</td>
					<td><%= articleBean.getEmail() %></td>
				</tr>
				<tr>
					<td class="itemName">タイトル</td>
					<td><%= CommonFunction.getDefault(articleBean.getTitle(), "(no title)") %></td>
				</tr>
				<tr>
					<td class="itemName">本文</td>
					<td><%= CommonFunction.convertLineBreaksToHtml(articleBean.getText()) %></td>
				</tr>
			</table>
			<input class="button" type="submit" name="Back" value="戻る">
			<input class="button" type="submit" name="Submit" value="投稿">
		</form>
	</div>
</body>
</html>