<%@ page language="Java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SEARCH</title>
 
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
</head>
<body>
    <sf:form method="POST" commandName="searchKeyword">
    	<sf:input path="keyword" type="text" title="请输入关键字" value="${searchKeyword.keyword}"/>
    	<input type="submit" id="sb_form_go" title="查询" value="查询" />
    </sf:form>
    <table>
        <tr>
            <td>TITLE</td><td>CACHED DATE</td><td>ADDRES</td><td>DOMAIN</td><td>PAGEURL</td><td></td>
        </tr>
        <c:forEach items="${videos}" var="video">
            <tr>
            <td>${video.title}</td>
            <td>${video.created}</td>
            <td>${video.downloadurl}</td>
            <td>${video.domain }</td>
            <td>${video.pageurl }</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</body>
</html>
