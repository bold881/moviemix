<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <form method="POST">
      NICKNAME: <input type="text" name="nickname" /><br/>
      EMAIL: <input type="email" name="email" /><br/>
      <input type="submit" value="Subscribe" />
    </form>
  </body>
</html>
