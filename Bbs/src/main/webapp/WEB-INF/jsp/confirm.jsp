<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>確認画面</title>
</head>
<body>
	<h1>確認</h1>
	<p>
		以下の内容で登録されました。
	</p>
	<table>
		<tr>
			<td>お名前</td>
			<td>${param.name}</td>
		</tr>
		<tr>
			<td>パスワード</td>
			<td>●●●●●●</td>
		</tr>
	</table>
</body>
</html>