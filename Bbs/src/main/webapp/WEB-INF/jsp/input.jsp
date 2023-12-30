<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
   	import="jp.sljacademy.bbs.bean.ArticleBean"
   	import="java.util.List"
	import="jp.sljacademy.bbs.bean.ColorMasterBean"
%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	ArticleBean articleBean = (ArticleBean) session.getAttribute("ArticleBean");
	// 選択された色のIDを取得（String型を想定）
	String selectedColorId = articleBean != null ? articleBean.getColorId() : null;
	List<ColorMasterBean> colorList = (List<ColorMasterBean>) request.getAttribute("colorList");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/input.css" type="text/css">
<title>掲示板</title>
</head>
<body>
	<header> 掲示板 </header>
	<form action="/Bbs/ConfirmServlet" method="post" id="form">
		<table class="inputArticle">
			<tr>
				<td class=itemName id="name">名前</td>
				<td><input type="text" name="username" value="<%= articleBean.getName() %>"
					onFocus="changeColorById('name','yellow')"
					onBlur="changeColorById('name','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="email">E-mail</td>
				<td><input type="text" name="email" value="<%= articleBean.getEmail() %>"
					onFocus="changeColorById('email','yellow')"
					onBlur="changeColorById('email','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="title">タイトル</td>
				<td><input type="text" name="title" value="<%= articleBean.getTitle() %>"
					onFocus="changeColorById('title','yellow')"
					onBlur="changeColorById('title','white')"></td>
			</tr>
			<tr>
				<td class="itemName" id="text">本文</td>
				<td><textarea name="text" cols="35" rows="5"
						onFocus="changeColorById('text','yellow')"
						onBlur="changeColorById('text','white')"><%= articleBean.getText() %></textarea>
				</td>
			
			<tr>
				<td class="itemName">文字色</td>
				<td>
					<% if (colorList != null) {
                        for (ColorMasterBean color : colorList) {
                            String checkedAttribute = color.getColorId().equals(selectedColorId) ? " checked" : ""; %>
                            <input class="radio" type="radio" name="color" value="<%= color.getColorId() %>" id="color_<%= color.getColorId() %>" <%= checkedAttribute %> >
                            <label for="color_<%= color.getColorId() %>" style="color: #<%= color.getColorCode() %>;"><%= color.getColorName() %></label>
                        <% }
                    } %>
				</td>
			</tr>
			
		</table>
		<input class="button" type="submit" name="Submit" value="確認">
	</form>
	<form action="/Bbs/InputServlet" method="post" id="clearForm">
		<input class="button" type="button" name="clear" value="クリア">
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
	<script type="text/javascript" src="css/fix.js"></script>

</body>
</html>