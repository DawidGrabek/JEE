package com.mycompany.pai_lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ListServlet", urlPatterns = {"/ListServlet"})
public class ListServerlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
            String user = "root";
            String password = "root"; // Ustaw hasło jeśli konieczne
            Connection conn = DriverManager.getConnection(url, user, password);
            
            Statement st = conn.createStatement();
            String query = "SELECT Code, Name, Population FROM Country WHERE Continent = 'Europe'";
            ResultSet rs = st.executeQuery(query);
            
            HttpSession session = request.getSession(true);
            
            ArrayList<CountryBean> list = new ArrayList<>();
            while (rs.next()) {
                CountryBean country = new CountryBean();
                country.setCode(rs.getString("Code"));
                country.setName(rs.getString("Name"));
                country.setPopulation(rs.getInt("Population"));
                list.add(country);
            }
            session.setAttribute("list", list);
            
            response.sendRedirect("countryList.jsp");
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ListServlet wyświetla kraje Europy z bazy danych";
    }
}
