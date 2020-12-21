<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
session.invalidate();
System.out.println("SESSION ID [" + (session == null ? null : session.getId()) + "]");
%>  
<html>
<head>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="/resources/js/common.js"></script>
</head>
<script>
//$('#btnLogin').click(function(){
//    util.requestSync("<c:url value="/nosession/login" />", reqData, 'POST', result) {}
//});
</script>
<body>
    <form action="<c:url value="login" />" method="post">
            <span>이름:</span><input type="text" name="name" value=""><br/>
            <span>암호:</span><input type="password" name="password" value=""><br/>
        <input type="submit" id="btnLogin" value="로그인">
        <c:if test="${error == 'true'}">
            <font color="red"><strong>이름이나 암호가 일치하지 않습니다.</strong></font>
        </c:if>
    </form>
</body>
</html>