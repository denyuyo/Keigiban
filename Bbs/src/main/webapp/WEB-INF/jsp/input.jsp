<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/master.css" type="text/css">
<title>一覧画面</title>
</head>
<body>
	<header> 一覧画面 </header>
	<form action="./confirm.jsp" method="post" id="form">
		<table class="inputArticle">
			<tr>
				<td class=itemName id="name">名前</td>
				<td><input type="text" name="username" value=""
					onFocus="changeColorById('name','yellow')"
					onBlur="changeColorById('name','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="email">E-mail</td>
				<td><input type="text" name="email" value=""
					onFocus="changeColorById('email','yellow')"
					onBlur="changeColorById('email','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="title">タイトル</td>
				<td><input type="text" name="title" value=""
					onFocus="changeColorById('title','yellow')"
					onBlur="changeColorById('title','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="text">本文</td>
				<td>
					<textarea name="text" cols="35"
						rows="5" onFocus="changeColorById('text','yellow')"
						onBlur="changeColorById('text','white')"></textarea>
				</td>
			</tr>
			<tr>
				<td class="itemName">文字色</td>
				<td><input class="radio" type="radio" name="color" value="0"
					id="color_0" onClick="changeColorByName('black')"> <label
					for="color_0">黒</label> <input class="radio" type="radio"
					name="color" value="1" id="color_1"
					onClick="changeColorByName('blue')"> <label for="color_1"
					style="color: blue;">青</label> <input class="radio" type="radio"
					name="color" value="2" id="color_2"
					onClick="changeColorByName('orange')" checked> <label
					for="color_2" style="color: #ff9900">橙</label></td>
			</tr>
		</table>
		<input class="button" type="reset" name="clear" value="クリア"> <input
			class="button" type="submit" name="Submit" value="確認">
	</form>
	<hr>
	<table class="postedArticle">
		<tr>
			<td class="articleId">4</td>
			<td class="articleTitle">テストタイトル４</td>
		</tr>
		<tr>
			<td class="articleText" colspan="2">テスト本文４</td>
		</tr>
		<tr>
			<td class="articleDate" colspan="2">2007年 4月 4日 12時00分&emsp;
				nobody</td>
		</tr>
	</table>
	<table class="postedArticle" style="color: #ff9900">
		<tr>
			<td class="articleId">3</td>
			<td class="articleTitle">テストタイトル３</td>
		</tr>
		<tr>
			<td class="articleText" colspan="2">テスト本文３</td>
		</tr>
		<tr>
			<td class="articleDate" colspan="2">2007年 4月 2日 15時30分&emsp; <a
				href="mailto:test3@test.co.jp">テスト3</a>
			</td>
		</tr>
	</table>
	<table class="postedArticle" style="color: blue;">
		<tr>
			<td class="articleId">2</td>
			<td class="articleTitle">(no title)</td>
		</tr>
		<tr>
			<td class="articleText" colspan="2">テスト本文２</td>
		</tr>
		<tr>
			<td class="articleDate" colspan="2">2007年 4月 2日 10時00分&emsp;
				テスト2</td>
		</tr>
	</table>
	<table class="postedArticle">
		<tr>
			<td class="articleId">1</td>
			<td class="articleTitle">テストタイトル１</td>
		</tr>
		<tr>
			<td class="articleText" colspan="2">テスト本文１</td>
		</tr>
		<tr>
			<td class="articleDate" colspan="2">2007年 4月 1日 00時00分&emsp; <a
				href="mailto:test1@test.co.jp">テスト1</a>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="css/master.js"></script>

</body>
</html>