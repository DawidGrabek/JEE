<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.pai_lab3.CountryBean"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista Krajów</title>
</head>
<body>
    <h1>Lista Krajów Europy</h1>
    
    <%
        ArrayList<CountryBean> list = (ArrayList<CountryBean>) session.getAttribute("list");
        
        if (list != null && !list.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>Kod</th>
                <th>Nazwa</th>
                <th>Populacja</th>
                <th>Akcja</th> <!-- Nowa kolumna na link -->
            </tr>
            <%
                for (CountryBean country : list) {
                    int index = list.indexOf(country);
            %>
                <tr>
                    <td><%= country.getCode() %></td>
                    <td><%= country.getName() %></td>
                    <td><%= country.getPopulation() %></td>
                    <td><a href="details.jsp?index=<%= index %>">Szczegóły</a></td> <!-- Link do szczegółów -->
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>Brak danych do wyświetlenia.</p>
    <%
        }
    %>
</body>
</html>