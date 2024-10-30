<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Błąd aplikacji</title>
</head>
<body>
    <h1>Error Page</h1>
    <p>Failed URL: ${url}
        Exception: ${exception.message}
        <c:forEach items="${exception.stackTrace}" var="ste">
            ${ste}
        </c:forEach>
    </p>
</body>
</html>
