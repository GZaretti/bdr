<link rel='stylesheet' href='../../bootstrap.css'>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    </head>
    <body>
        <title>Database Set Up</title>
        <%@ include file="../menu.jsp" %>
        <h1>Database index!</h1>
        <p>
        <form action = "create" method = "POST">
            <input type = "submit" value = "create DB" />
        </form>
        <form action = "drop" method = "POST">
            <input type = "submit" value = "drop DB" />
        </form>
    </p>


</body>
</html>
