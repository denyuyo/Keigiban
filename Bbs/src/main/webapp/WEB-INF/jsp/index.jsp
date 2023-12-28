<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="css/origin.css" type="text/css">
</head>
<body>
    <header>掲示板へようこそ</header>
    <div class="form-container">
        <!-- ログインの案内メッセージ -->
        <p class="login-message">IDとパスワードを入力してログインしてください。</p>
        
        <!-- エラーメッセージがあれば表示 -->
        <% String errorMessages = (String) request.getAttribute("errorMessages");
           if (errorMessages != null) { %>
               <div class="error-messages"><%=errorMessages%></div>
        <% } %>
        
        <!-- ログインフォーム -->
        <form action="/Bbs/IndexServlet" method="post" name="Form1" onSubmit="return chkField1()">
            <div class="form-group">
                <label for="id" class="itemName">ユーザーID:</label>
                <input type="text" name="id" id="id" value="" required>
            </div>
            <div class="form-group">
                <label for="password" class="itemName">パスワード:</label>
                <input type="password" name="password" id="password" required>
            </div>
            <div class="form-group">
                <input class="button" type="submit" name="Login" value="ログイン">
            </div>
        </form>
    </div>
    <script type="text/javascript" src="css/fix.js"></script>
</body>
</html>
