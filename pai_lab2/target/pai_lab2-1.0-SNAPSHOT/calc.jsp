<%-- 
    Document   : calc
    Created on : 9 paź 2024, 11:51:35
    Author     : grabe
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errorPage.jsp"%>
<jsp:useBean id="loan" class="bp.pai_lab2.LoanBean" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String date = dateFormat.format(now);
                    // skorzystanie z predefiniowanego obiektu strumienia out,
            // znanego z serwletów:
//            out.println(date);
            %>
            <%= date %>
        <h1>Hello World!</h1>
        
            <h1>Kalkulator rat</h1>
    
    <form action="calc.jsp" method="POST">
        <label for="kwota">Kwota pożyczki:</label>
        <input type="text" id="kwota" name="kwota" required><br><br>

        <label for="oprocentowanie">Procent roczny:</label>
        <input type="text" id="oprocentowanie" name="oprocentowanie" required><br><br>

        <label for="rata">Liczba rat:</label>
        <input type="text" id="rata" name="rata" required><br><br>

        <input type="submit" value="Oblicz ratę">
    </form>

    <%
            String kwotaStr = request.getParameter("kwota");
            String oprocentowanieStr = request.getParameter("oprocentowanie");
            String rataStr = request.getParameter("rata");
            
            if (kwotaStr != null && oprocentowanieStr != null && rataStr != null) {
                try {
                    loan.setKwota(Double.parseDouble(kwotaStr));
                    loan.setOprocentowanieRoczne(Double.parseDouble(oprocentowanieStr));
                    loan.setLiczbaRat(Integer.parseInt(rataStr));

                    double p = (loan.getOprocentowanieRoczne() / 100) / 12;

                    // Sprawdzenie, czy mianownik nie wynosi zero
                    double mianownik = 1 - Math.pow(1 + p, -loan.getLiczbaRat());
                    if (mianownik == 0) {
                        throw new Exception("Wartość mianownika wynosi zero, nie można obliczyć raty.");
                    }
                    
                    double rata = loan.getRata();

                    DecimalFormat df = new DecimalFormat("#.00");
                    String rataf = df.format(rata);

                    out.println("<p>Rata miesieczna: " + rataf + " zł</p>");
                } catch (NumberFormatException e) {
                    throw new Exception(e);
                }
            }
        %>

    </body>
</html>
