<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/addUser">
    userName:<input name="userName" type="text" maxlength="40"><br>
    password:<input name="password" type="text" maxlength="40"><br>
    <input type="submit" value="Add">
</form>
</body>
</html>