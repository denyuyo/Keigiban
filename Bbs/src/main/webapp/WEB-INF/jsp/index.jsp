<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ログイン画面</title>
	<link rel="stylesheet" href="css/master.css" type="text/css">
</head>
<body>
	<header>掲示板</header>
	<div class="form-container">
		<p class="login-message">あなたのIDとパスワードを入力してログインしてください。</p>
		
		<!--
			request.getAttribute("errorMessages") を使用してServletのリクエスト属性からエラーメッセージを取得し、
			errorMessages を使用して表示する
		-->
		<% String errorMessages = (String) request.getAttribute("errorMessages");
			if (errorMessages != null) { %>
				<div class="error-messages" style="color: red;"><%=errorMessages%></div>
		<% } %>
		
		<!-- ログインフォーム -->
		<form action="/Bbs/IndexServlet" method="post" name="Form1">
			<div class="form-group">
				<label for="id" class="itemName">ID:</label>
				<input type="text" name="id" id="id" value="">
			</div>
			<div class="form-group">
				<label for="password" class="itemName">パスワード:</label>
				<input type="password" name="password" id="password">
			</div>
			<div class="form-group">
				<input class="button" type="submit" name="Login" value="ログイン">
			</div>
		</form>
	</div>
</body>
</html>
