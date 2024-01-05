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
	// 選択された色のIDを取得
	List<ColorMasterBean> colors = (List<ColorMasterBean>) request.getAttribute("colors");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/input.css" type="text/css">
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
	<form action="/Bbs/ConfirmServlet" method="post" id="form">
		<table class="inputArticle">
			<tr>
				<td class=itemName id="name">名前</td>
				<td><input type="text" name="username" value="<%= articleBean.getName() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="email">E-mail</td>
				<td><input type="text" name="email" value="<%= articleBean.getEmail() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="title">タイトル</td>
				<td><input type="text" name="title" value="<%= articleBean.getTitle() %>"></td>
			</tr>
			<tr>
				<td class="itemName" id="text">本文</td>
				<td><textarea name="text" cols="35" rows="5"><%= articleBean.getText() %></textarea>
				</td>
				
			<tr>
				<td class="itemName">文字色</td>
				<td>
					<% for (ColorMasterBean color : colors) { %>
					<input type="radio" name="colorCode" value="<%= color.getColorCode() %>"
                         onclick="changeColor('<%= color.getColorCode() %>')"
                         <%= color.getColorCode().equals(articleBean.getColorId()) ? "checked" : "" %> >
                     <%= color.getColorName() %>
				    <% } %>
				</td>
			</tr>
			
		</table>
		<input class="button" type="submit" name="Submit" value="確認">
	</form>
	<form action="/Bbs/InputServlet" method="post" id="clearForm">
		<input class="button" type="submit" name="clear" value="クリア">
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
</body>
</html>