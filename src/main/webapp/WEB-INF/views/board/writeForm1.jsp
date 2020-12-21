<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="/resources/js/common.js"></script>
</head>
<body>
    <form id="form" method="POST" enctype="multipart/form-data" action="<c:url value='/board/upload'/>">
        <table id="fileTable" border=1 class="reg" style="width:95%;margin: 10 6 10 6">
            <tr>
                <td>
                    <input type="file" name="files" accept="image/jpg" />
                </td>
            </tr>
        </table>
        <br/>
        <button type="submit">등록</button>
    </form>
</body>
</html>