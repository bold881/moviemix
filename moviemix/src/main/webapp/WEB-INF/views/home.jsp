<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>VideoMix</title>
	</head>
	<body>
		<a href="http://www.bing.com">bing</a>
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
		<a href="<c:url value="/subscribe" />">Subscribe</a>
	</body>
</html>