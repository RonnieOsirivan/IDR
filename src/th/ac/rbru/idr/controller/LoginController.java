package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.RoleCheck;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RoleCheck roleCheck = new RoleCheck();
		if(roleCheck.hasRole("ROLE_ADMIN")){
			response.sendRedirect("./management_Admin.html");
		}else if(roleCheck.hasRole("ROLE_STUDENT")){
			Principal principal = request.getUserPrincipal();
			String studentCode = principal.getName();
			if(getFinanceStatus(studentCode).equalsIgnoreCase("N")){
				response.sendRedirect("./main.html");
			}else{
				response.sendRedirect("./permissionDenied.html");
			}
		}else{
			response.sendRedirect("./403.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private String getFinanceStatus(String studentCode){
		String financeStatus = "";
		try {
			String sql = " SELECT FINANCESTATUS "+
					" FROM STUDENTMASTER "+
					" WHERE STUDENTCODE LIKE '"+studentCode+"' ";
			ResultSet rs = getData(sql);
			while (rs.next()) {
				financeStatus = rs.getString("FINANCESTATUS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return financeStatus;
	}
	
	private ResultSet getData(String sql) throws SQLException {
		ResultSet result = null;
		try {
			ConnectionDB.getInstance();
			con = ConnectionDB.getRegConnection();
			Statement statement = con.createStatement();
			result = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
