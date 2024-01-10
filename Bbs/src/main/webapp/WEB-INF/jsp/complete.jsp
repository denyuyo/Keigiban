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
	<title>完了画面</title>
	<link rel="stylesheet" href="css/master.css" type="text/css">
</head>
<body>
	<div class="content">
		<p>投稿が完了しました。</p>
		<form action="/Bbs/CompleteServlet" method="post">
			<p><input class="button" type="submit" name="Back" value="一覧画面に戻る"></p>
		</form>
	</div>
</body>
</html>