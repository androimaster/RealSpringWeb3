<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
    <meta http-equiv="content-language" content="ko">
    <title>ERROR</title>
</head>
<body>
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%" height="100%" align="center" valign="middle" style="padding-top:150px;">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><h2>오류발생 알림화면(허용되지 않는 요청을 하셨습니다) by Spring</h2></td>
                    </tr>
                    <tr>
                        <td><%= exception %></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>