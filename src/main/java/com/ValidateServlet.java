package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ValidateServlet
 */
public class ValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ValidateServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("txtusername");
		String password = request.getParameter("txtpassword");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Step 2 -> Get Connection
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema","root","root");
			//Step 3 -> Create Statement 
			PreparedStatement stmt= con.prepareStatement("select * from users where username=? and password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			
			if(rs.next()){
				RequestDispatcher rd = request.getRequestDispatcher("WelcomeServlet");
				rd.forward(request, response);
			} else {
				out.println("Incorect crudentials");
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
			//Step 5 -> Close Connection
			con.close();
		} catch(Exception e1) {
			System.out.println("The problem is:" + e1);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
