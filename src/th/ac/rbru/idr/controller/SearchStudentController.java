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

import oracle.jdbc.proxy.annotation.GetDelegate;
import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.model.StudentStatus;
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("checkStatus".equalsIgnoreCase(request.getParameter("method"))){
			checkStatus(request, response);
		}else{
			getStudent(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void checkStatus(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
				"	AND STDM.STUDENTCODE LIKE "+request.getParameter("studentCode")+
				"	ORDER BY STDS.ACADYEAR DESC,STDS.SEMESTER DESC)	"+
				"	WHERE ROWNUM = 1";
		
		ResultSetMapper<StudentStatus> stdsMapper = new ResultSetMapper<StudentStatus>();
		StudentStatus stds = stdsMapper.mapRersultSetToObject(getData(sql), StudentStatus.class).get(0);
		
		String acadSQL = "	SELECT DFS.SEMESTER,	"+
				"	TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') + DFS.ACADYEARADJ AS ACADYEAR	"+
				"	FROM DEFAULTSEMESTER DFS	"+
				"	WHERE DFS.SYSAPPID = 25	"+
				"	AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')";
		
		int semester = 0;
		int acadYear = 0;
		try {
			ResultSet result = getData(acadSQL);
			while (result.next()) {
				semester = result.getInt("SEMESTER");
				acadYear = result.getInt("ACADYEAR");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(stds.getStudentStatus() == 10){
			stds.setCanReq("Y");
		}else if(stds.getStudentStatus() == 11 || stds.getStudentStatus() == 14){
			if(semester == stds.getSemester() && acadYear == stds.getAcadYear()){
				stds.setCanReq("Y");
			}else{
				stds.setCanReq("N");
			}
		}else{
			stds.setCanReq("N");
		}
		
		if(stds.getCanReq().equals("N")){
			stds.setMsgThai("สถานะ "+stds.getStatusNameThai()+" ในเทอม "+stds.getSemester()+"/"+stds.getAcadYear());
			stds.setMsgEng("Status is "+stds.getStatusNameEng()+" in semester "+stds.getSemester()+"/"+stds.getAcadYear());
		}
		
		
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(stds);
		sendResponse(request, response, resultJson);
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
