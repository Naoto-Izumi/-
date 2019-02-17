<%@ page pageEncoding="Windows-31J"
	isErrorPage="true"
	contentType="text/html;charset=Windows-31J" %>

<%--JSTL 1.1.2 core タグライブラリ--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>入力内容不適合</title></head>
<body>
	<h1>例外のメッセージ:${pageContext.exception.message}</h1>
	<h1>例外のタイプ:${pageContext.exception.getClass().getName()}</h1>
	
	<a href="notfound.jsp">入力内容が不適切です。入力しなおしてください。</a>
	
	<p><font color="#FFFFFF">
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
		テストテストテストテストテストテストテストテストテストテスト
	</font></p>
	
</body>
</html>
