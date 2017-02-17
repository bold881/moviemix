<%@ page language="Java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Videos of Today</title>
 
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
 
</head>
 
 
<body>
    <h2>Videos of Today</h2>  
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
