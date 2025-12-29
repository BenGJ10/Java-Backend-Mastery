package com.bengregory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Servlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Service method is working...");

        response.getWriter().println("Response sent!");
    }
}
