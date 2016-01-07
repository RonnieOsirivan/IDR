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

import th.ac.rbru.idr.model.StudentStatus;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ResultSetMapper;
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
			if(checkStatus(studentCode).equalsIgnoreCase("Y")){
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
	
	private String getStudentStatus(String studentCode){
		String status = "N";
		int statusnum = 0;
		try {
			String sql = " SELECT STUDENTSTATUS "+
					" FROM STUDENTMASTER "+
					" WHERE STUDENTCODE LIKE '"+studentCode+"' ";
			ResultSet rs = getData(sql);
			while (rs.next()) {
				statusnum = rs.getInt("STUDENTSTATUS");
				
			}
			if(statusnum==10 || statusnum==11 || statusnum==14){
				status = "Y";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	private String checkStatus(String studentCode){
		String sql = "	SELECT *	"+
				"	FROM(	"+
				"	SELECT STDM.STUDENTSTATUS ,	"+
				"	  SBD.BYTEDES ,	"+
				"	  SBD.BYTEDESENG,	"+
				"	  STDS.SEMESTER,	"+
				"	  STDS.ACADYEAR	"+
				"	FROM STUDENTMASTER STDM,	"+
				"	  STUDENTSTATUS STDS,	"+
				"	  SYSBYTEDES SBD	"+
				"	WHERE SBD.TABLENAME LIKE 'STUDENTSTATUS'	"+
				"	AND SBD.COLUMNNAME LIKE 'STUDENTSTATUS'	"+
				"	AND STDM.STUDENTSTATUS = SBD.BYTECODE	"+
				"	AND STDM.STUDENTID = STDS.STUDENTID	"+
				"	AND STDM.STUDENTCODE LIKE "+studentCode +
				"	ORDER BY STDS.ACADYEAR DESC,STDS.SEMESTER DESC)	"+
				"	WHERE ROWNUM = 1";
		
		ResultSetMapper<StudentStatus> stdsMapper = new ResultSetMapper<StudentStatus>();
		String statusReq = "N";
		try {
			StudentStatus stds = stdsMapper.mapRersultSetToObject(getData(sql), StudentStatus.class).get(0);
			String acadSQL = "	SELECT DFS.SEMESTER,	"+
					"	TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') - DFS.ACADYEARADJ AS ACADYEAR	"+
					"	FROM DEFAULTSEMESTER DFS	"+
					"	WHERE DFS.SYSAPPID = 25	"+
					"	AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')";
			
			int semester = 0;
			int acadYear = 0;
			
			ResultSet result = getData(acadSQL);
			while (result.next()) {
				semester = result.getInt("SEMESTER");
				acadYear = result.getInt("ACADYEAR");
			}
			if(stds.getStudentStatus() == 10){
				statusReq = "Y";
			}else if(stds.getStudentStatus() == 11 || stds.getStudentStatus() == 14){
				if(semester == stds.getSemester() && acadYear == stds.getAcadYear()){
					statusReq = "Y";
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return statusReq;
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
