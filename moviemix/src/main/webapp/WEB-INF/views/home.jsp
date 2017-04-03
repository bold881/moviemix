<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title>VideoMix</title>
	</head>
	<body>
		<script src="./resources/js/jquery-3.1.1.js"></script>
		<script type="text/javascript">
		window.onload = function() {
		    $("a").click(function(event) {
		    	alert("Thanks for visting!");
		    	event.preventDefault();
		    });
		};
		</script>
		<h2>Welcome to VideoMix!</h2>
		<br >
		<div>
			<form>
				<input type="text" title="请输入关键字"/>
				<input type="submit" id="sb_form_go" title="查询" value="查询"/>
			</form>
		</div>
		<a href="<c:url value="/subscribe" />">Subscribe</a>
	</body>
</html>