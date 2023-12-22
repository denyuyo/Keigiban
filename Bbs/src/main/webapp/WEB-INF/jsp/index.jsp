<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/master.css" type="text/css">
<title>ログイン画面</title>
</head>
<body>
	<header>掲示板</header>
		<p>あなたのIDとパスワードを入力してログインしてください。</p>
		
		<form action="./input.html" method="post"
			name="Form1" onSubmit="return chkField1()">
			<p>
                
				<label class="itemName">ID:</label>
				<input type="text" name="id" value="">
			</p>
			<p>
				<label  class="itemName"s>パスワード:</label>
				<input type="password" name="password">
			</p>
			<input class="button" type="submit" name="Login" value="ログイン">
		</form>
		
		<script type="text/javascript" src="./master.js"></script>

</body>
</html>