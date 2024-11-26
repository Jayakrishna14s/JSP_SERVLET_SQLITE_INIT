package com.realestate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;

@WebServlet("/helloworld")
public class helloworld extends HttpServlet {
    public helloworld () {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello World");

        try {
            Connection con = DatabaseConnection.getConnection();
            DatabaseConnection.initDB(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
