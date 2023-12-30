<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="jp.sljacademy.bbs.bean.ArticleBean" %>
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
<link rel="stylesheet" href="css/confirm.css" type="text/css">
<title>掲示板</title>
</head>
<body>
	<header>
		掲示板
	</header>
	<div class="main-content">
		<p>以下の内容で投稿します。</p>
		<form action="/Bbs/CompleteServlet" method="post">
			<table class="inputArticle">
				<tr>
					<td class="itemName">名前</td>
					<!-- getAttribute=sessionにセットされた値を取得 -->
					<td style="color: <%  %>>;"><%= articleBean.getName() %></td>
				</tr>
				<tr>
					<td class="itemName">E-mail</td>
					<td  style="color: #ff9900;"><%= articleBean.getEmail() %></td>
				</tr>
				<tr>
					<td class="itemName">タイトル</td>
					<!-- getParameter=ユーザーがform等に入力した値を取得 -->
					<td  style="color: #ff9900;"><%= request.getParameter("title") %></td>
				</tr>
				<tr>
					<td class="itemName">本文</td>
					<td  style="color: #ff9900;"><%= request.getParameter("text") %></td>
				</tr>
			</table>
			<input class="button" type="button" name="Back"   value="戻る">
			<input class="button" type="submit" name="Submit" value="投稿">
		</form>
	 </div>
	<script type="text/javascript" src="css/fix.js"></script>
</body>
</html>