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
    <link rel="stylesheet" href="css/complete.css" type="text/css">
</head>
<body>
    <div class="content">
        <h1>投稿完了</h1>
        <p>あなたの投稿は正常に完了しました。</p>
        <a href="/Bbs/InputServlet" class="button">一覧画面に戻る</a>
    </div>
</body>
</html>
