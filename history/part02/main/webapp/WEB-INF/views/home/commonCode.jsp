<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="/resources/js/common.js"></script>
</head>
<script>
$(document).ready(function() {
    $('#upper').change(function() {
        util.initSelectBox('lower', $(this).val(), '', 'Y');
    });

    util.initSelectBox('upper', 'master', '', 'Y');
});


</script>
<body>
    상위 코드 : <select id="upper" style="width:140px"></select><br>
    하위 코드 : <select id="lower" style="width:140px"></select><br>
</body>
</html>