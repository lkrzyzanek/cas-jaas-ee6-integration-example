<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CAS Integration</title>
</head>
<body>
	<h1>CAS Integration</h1>
    Authenticated user: <c:out value="${pageContext.request.userPrincipal.name}" default="N/A"/>
</body>
</html>
