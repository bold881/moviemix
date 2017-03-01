<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Subscribe For Latest Videos</title>
    <!-- <link rel="stylesheet" type="text/css" 
          href="<c:url value="/resources/style.css" />" >
    -->
  </head>
  <body>
    <h1>Subscribe, Get Fresh Videos Easily and Free!</h1>
    <sf:form method="POST" commandName="subscriber">
    	<sf:errors path="*" element="div" />
    	
    	<sf:label path="nickname" cssErrorClass="error">NICKNAME</sf:label>
    	<sf:input path="nickname" cssErrorClass="error" /><br />
    	
    	<sf:label path="email" cssErrorClass="error">EMAIL</sf:label>
    	<sf:input path="email" cssErrorClass="error" /><br />
    	<input type="submit" value="Subscribe" />
    </sf:form>
  </body>
</html>
