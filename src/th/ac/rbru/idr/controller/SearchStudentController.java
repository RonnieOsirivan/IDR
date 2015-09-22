package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ConvertDataType;
import th.ac.rbru.idr.util.ResultSetMapper;

/**
 * Servlet implementation class SearchStudent
 */
@WebServlet("/SearchStudentController")
public class SearchStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchStudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getStudent(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void getStudent(HttpServletRequest request, HttpServletResponse response){
		try {
			ResultSetMapper<Student> student = new ResultSetMapper<Student>();
			List<Student> studentList = student.mapRersultSetToObject(getStudentSQL(request.getParameter("id"), request.getParameter("firstName"), request.getParameter("lastName")), Student.class);
			String resultJson = ConvertDataType.getInstance().objectToJasonArray(studentList);
			sendResponse(request, response, resultJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet getStudentSQL(String id, String firstName, String lastName) {
		String condition = "";
		if(id != null && !id.isEmpty()){
			condition += " AND STDM.STUDENTCODE LIKE ('"+id+"%') "; 
		}
		if(firstName != null && !firstName.isEmpty()){
			condition += " AND STDM.STUDENTNAME LIKE ('"+firstName+"%') "; 
		}
		if(lastName != null && !lastName.isEmpty()){
			condition += " AND STDM.STUDENTSURNAME LIKE ('"+lastName+"%') "; 
		}
		
		String sql = "	SELECT STDM.STUDENTCODE,PRE.PREFIXNAME, 	"+
		"	STDM.STUDENTNAME,STDM.STUDENTSURNAME	"+
		"	FROM STUDENTMASTER STDM, PREFIX PRE	"+
		"	WHERE STDM.PREFIXID = PRE.PREFIXID	"+condition+
		"	ORDER BY STDM.STUDENTCODE ASC ";
		return getData(sql);
	}
	
	private void sendResponse(HttpServletRequest request,HttpServletResponse response,String resultJson) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(resultJson);
		out.close();
		// Release JDBC pool connection after sent result to client
		releaseConnection();
	}
	
	private ResultSet getData(String sql){
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
	
	private void releaseConnection(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
		}
	}

}
