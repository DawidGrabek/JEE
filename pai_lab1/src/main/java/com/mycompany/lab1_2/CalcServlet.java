/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.lab1_2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grabe
 */
@WebServlet(name = "CalcServlet", urlPatterns = {"/CalcServlet"})
public class CalcServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            ArrayList<String> history = (ArrayList<String>) session.getAttribute("history");

            if (history == null) {
                history = new ArrayList<String>();
                session.setAttribute("history", history);
            }

            
            String welcomeMessage = "Witaj po raz pierwszy!";
            String userId = "UserId";
            Cookie[] cookies = request.getCookies();
            boolean foundUser = false;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (userId.equals(cookie.getName())) {
                        welcomeMessage = "Witaj ponownie!";
                        foundUser = true;
                        break;
                    }
                }
            }

            if (!foundUser) {
                Cookie newCookie = new Cookie(userId, "1");
                newCookie.setMaxAge(60 * 60 * 24); // Ciasteczko ważne 1 dzień
                response.addCookie(newCookie);
            }

            
            out.println("<html>");
            out.println("<head><title>Kalkulator</title></head>");
            out.println("<body>");
            out.println("<h1>" + welcomeMessage + "</h1>");

            
            String num1 = request.getParameter("num1");
            String num2 = request.getParameter("num2");
            String operation = request.getParameter("operation");

            if (request.getParameter("clear") != null) {
                history.clear();
                out.println("<p>Historia operacji została wyczyszczona.</p>");
            } else if (num1 != null && num2 != null && !num1.trim().isEmpty() && !num2.trim().isEmpty()) {
                try {
                    double number1 = Double.parseDouble(num1);
                    double number2 = Double.parseDouble(num2);
                    double result = 0.0;
                    String operator = "";
                    String operationStr = "";

                    switch (operation) {
                        case "add":
                            result = number1 + number2;
                            operator = "+";
                            break;
                        case "subtract":
                            result = number1 - number2;
                            operator = "-";
                            break;
                        case "multiply":
                            result = number1 * number2;
                            operator = "*";
                            break;
                        case "divide":
                            if (number2 == 0) {
                                out.println("<p>Nie można dzielić przez 0!</p>");
                                return;
                            }
                            result = number1 / number2;
                            operator = "/";
                            break;
                    }

                    operationStr = number1 + " " + operator + " " + number2 + " = " + result;
                    history.add(operationStr);

                    out.println("<h2>Wynik: " + operationStr + "</h2>");
                } catch (NumberFormatException e) {
                    out.println("<p>Niepoprawny format liczby!</p>");
                }
            } else {
                out.println("<p>Wprowadź liczby oraz wybierz operację.</p>");
            }

            
            out.println("<h2>Historia operacji:</h2>");
            if (!history.isEmpty()) {
                out.println("<ul>");
                for (String entry : history) {
                    out.println("<li>" + entry + "</li>");
                }
                out.println("</ul>");
            } else {
                out.println("<p>Brak historii operacji.</p>");
            }

            
            out.println("<br><a href='calc.html'>Powrót do kalkulatora</a>");
            out.println("<br><a href='CalcServlet?clear=true'>Wyczyść historię</a>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
