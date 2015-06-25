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
		System.out.println("doget!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		ResultSetMapper<Student> student = new ResultSetMapper<Student>();
		try {
			List<Student> studentList = student.mapRersultSetToObject(getStudentInfo(), Student.class);
			String resultJson = ConvertDataType.getInstance().objectToJasonArray(studentList);
			sendResponse(request, response, resultJson);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dopostttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
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
	
	private ResultSet getStudentInfo() throws SQLException{
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
				+" 	WHERE STDM.STUDENTCODE LIKE '5724442074'	"
				+" 	AND STDM.FACULTYID = FAC.FACULTYID	"
				+" 	AND STDM.PREFIXID  = PRE.PREFIXID	"
				+" 	AND STDM.LEVELID = LE.LEVELID	"
				+" 	AND STDM.PROGRAMID = PRO.PROGRAMID	"
				+" 	AND PRO.DEGREEID = DE.DEGREEID";
		return getData(sql);
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
