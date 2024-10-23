<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.pai_lab3.CountryBean"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Szczegóły kraju</title>
</head>
<body>
    <h1>Szczegóły kraju</h1>

    <%
        String indexStr = request.getParameter("index");
        if (indexStr != null) {
            int index = Integer.parseInt(indexStr);

            ArrayList<CountryBean> list = (ArrayList<CountryBean>) session.getAttribute("list");

            if (list != null && index >= 0 && index < list.size()) {
                CountryBean country = list.get(index);
    %>
                <p>Kod: <%= country.getCode() %></p>
                <p>Nazwa: <%= country.getName() %></p>
                <p>Populacja: <%= country.getPopulation() %></p>
    <%
            } else {
    %>
                <p>Nie znaleziono kraju o podanym indeksie.</p>
    <%
            }
        } else {
    %>
        <p>Brak parametru indeksu.</p>
    <%
        }
    %>

    <p><a href="countryList.jsp">Powrót do listy krajów</a></p>

</body>
</html>
