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
    <h1>Kraje Europy</h1>
    <%
        ArrayList<CountryBean> list = (ArrayList<CountryBean>) session.getAttribute("list");
        if (list != null) {
    %>
        <table>
            <tr>
                <th>Kod</th>
                <th>Nazwa</th>
                <th>Populacja</th>
            </tr>
            <%
                for (CountryBean country : list) {
            %>
                <tr>
                    <td><%= country.getCode() %></td>
                    <td><%= country.getName() %></td>
                    <td><%= country.getPopulation() %></td>
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
