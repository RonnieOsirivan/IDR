package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
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

import th.ac.rbru.idr.model.Semester;
import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ConvertDataType;
import th.ac.rbru.idr.util.ResultSetMapper;


/**
 * Servlet implementation class RequestController
 */
@WebServlet("/RequestController")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection con = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		try {
			String studentCode = "";
			if(!request.getParameter("studentCode").equals("null") && !(request.getParameter("studentCode")).isEmpty()){
				studentCode = request.getParameter("studentCode");
			}else{
				Principal principal = request.getUserPrincipal();
				studentCode = principal.getName();
			}
			
			if("getSemester".equalsIgnoreCase(request.getParameter("method"))){
				getSemester(request, response,studentCode);
			}else{
				ResultSetMapper<Student> student = new ResultSetMapper<Student>();
				List<Student> studentList = student.mapRersultSetToObject(getStudentInfo(studentCode), Student.class);
				String resultJson = ConvertDataType.getInstance().objectToJasonArray(studentList);
				sendResponse(request, response, resultJson);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	private ResultSet getStudentInfo(String userName) throws SQLException{
		String sql = " 	SELECT STDM.STUDENTCODE AS STUDENTCODE ,	"
				+" 	  STDM.STUDENTNAME      AS STUDENTNAME,	"
				+" 	  STDM.STUDENTSURNAME ,	"
				+"	  STDM.STUDENTNAMEENG AS STUDENTNAMEENG,"
				+"	  STDM.STUDENTSURNAMEENG AS STUDENTSURNAMEENG,"
				+" 	  FAC.FACULTYNAME,	"
				+" 	  PRE.PREFIXNAME,	"
				+" 	  CASE WHEN STDM.STUDYPERIOD = 1 THEN 'ภาคปกติ'	"
				+" 	       ELSE 'ภาคพิเศษ'	"
				+" 	  END AS PERIOD,	"
				+" 	  LE.LEVELNAME AS LEVELNAME,	"
				+" 	  'หลักสูตร' ||DE.DEGREENAME AS DEGREENAME,	"
				+" 	  DE.DEGREEABB AS DEGREEABB,	"
				+" 	  PRO.STUDYYEAR AS STUDYYEAR,	"
				+" 	  'สาขาวิชา' ||PRO.PROGRAMNAME AS PROGRAMNAME,	"
				+" 	  STDM.STUDENTYEAR AS STUDENTYEAR	"
				+" 	FROM STUDENTMASTER STDM,	"
				+" 	  FACULTY FAC,	"
				+" 	  PREFIX PRE,	"
				+" 	  LEVELID LE,	"
				+" 	  PROGRAM PRO,	"
				+" 	  DEGREE DE	"
				+" 	WHERE STDM.STUDENTCODE LIKE '"+userName+"'"
				+" 	AND STDM.FACULTYID = FAC.FACULTYID	"
				+" 	AND STDM.PREFIXID  = PRE.PREFIXID	"
				+" 	AND STDM.LEVELID = LE.LEVELID	"
				+" 	AND STDM.PROGRAMID = PRO.PROGRAMID	"
				+" 	AND PRO.DEGREEID = DE.DEGREEID";
		return getData(sql);
	}
	
	private void getSemester(HttpServletRequest request, HttpServletResponse response,String studentCode) throws IOException{
		ResultSetMapper<Semester> mapper = new ResultSetMapper<Semester>();
		List<Semester> semList = mapper.mapRersultSetToObject(getSemesterSQL(studentCode), Semester.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(semList);
		sendResponse(request, response, resultJson);
	}
	
	private ResultSet getSemesterSQL(String studentCode){
		String sql = "	SELECT DISTINCT EN.SEMESTER, EN.ACADYEAR	"+
				"	FROM STUDENTMASTER STDM, ENROLLSUMMARY EN	"+
				"	WHERE STDM.STUDENTID = EN.STUDENTID	"+
				"	AND EN.GRADE IS NOT NULL	"+
				"	AND STDM.STUDENTCODE LIKE "+studentCode+
				"	ORDER BY EN.ACADYEAR,EN.SEMESTER ";
		return getData(sql);
	}
	
	private ResultSet getData(String sql) {
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
