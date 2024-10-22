<%-- 
    Document   : errorPage
    Created on : 9 paź 2024, 12:37:37
    Author     : grabe
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Błąd</title>
    </head>
    <body>
        <h2>Wprowadzono błędne dane!</h2>
        <p>Pojawił się następujący błąd:
            <%= exception.getMessage() %>. <br />
        </p>
        <a href="calc.jsp">Powrót do kalkulatora</a>
    </body>
</html>