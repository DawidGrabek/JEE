<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edytuj Pracownika</title>
</head>
<body>
    <h2>Edytuj dane pracownika</h2>

    <form:form method="post" action="${pageContext.request.contextPath}/editsave">
        <form:hidden path="id"/>
        
        <p>
            <label for="nazwisko">Nazwisko:</label>
            <form:input path="nazwisko" id="nazwisko" />
        </p>
        
        <p>
            <label for="pensja">Pensja:</label>
            <form:input path="pensja" id="pensja" />
        </p>
        
        <p>
            <label for="firma">Firma:</label>
            <form:input path="firma" id="firma" />
        </p>
        
        <p>
            <input type="submit" value="Zapisz zmiany" />
            <!--<a href="${pageContext.request.contextPath}/viewAll">Anuluj</a>-->
            <a href="viewAll">Anuluj</a>
            
        </p>
    </form:form>
</body>
</html>
