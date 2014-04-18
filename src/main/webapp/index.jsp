<%@ page import="org.jasig.cas.client.util.AbstractCasFilter" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CAS Integration</title>


</head>
<body>
	<h1>CAS Integration</h1>
    Authenticated user: <%=request.getUserPrincipal().getName()%>
</body>
</html>
