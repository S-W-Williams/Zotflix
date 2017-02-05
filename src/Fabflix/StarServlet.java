package Fabflix;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import javax.json.*;
import java.sql.*;

public class StarServlet extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
	    HttpSession session = request.getSession(false);
	    try {
	        if (session.getAttribute("authenticated") == null) {  
	    		request.getRequestDispatcher("login.jsp").forward(request,response);
	    		return;
	        }
	    } catch (Exception e) {
    		request.getRequestDispatcher("login.jsp").forward(request,response);
    		return;
	    }
		int starId = Integer.parseInt(request.getParameter("starId"));
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        DatabaseQueries queries = new DatabaseQueries(dbConn);
        
        Star starInfo = queries.getStarDetails(starId);
        session.setAttribute("star", starInfo);
        
 		request.getRequestDispatcher("/WEB-INF/star.jsp").forward(request,response);
 		return;
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }    
}