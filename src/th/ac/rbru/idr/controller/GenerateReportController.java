package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ConvertDataType;
import th.ac.rbru.idr.util.ResultSetMapper;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Servlet implementation class GenerateReportController
 */
@WebServlet("/GenerateReportController")
public class GenerateReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>GenerateReportController");
		try {
			generateReport(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void generateReport(HttpServletRequest request)throws SQLException{
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateNumber = new SimpleDateFormat("dd",new Locale("th","th"));
			SimpleDateFormat simpleDateMounth = new SimpleDateFormat("MMMM",new Locale("th","th"));
			SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy",new Locale("th","th"));
			
			String stdCode = request.getParameter("studentCode");
			
			ResultSetMapper<Student> resultSet = new ResultSetMapper<Student>();
			List<Student> studentList = resultSet.mapRersultSetToObject(getStudentInfo(stdCode), Student.class);
			Student student = studentList.get(0);
			String resultJson = ConvertDataType.getInstance().objectToJasonArray(studentList);
			System.out.println(resultJson);
			
			
			int dateNumber = Integer.parseInt(simpleDateNumber.format(date));
			String mounthName = simpleDateMounth.format(date);
			int dateYear = Integer.parseInt(simpleDateYear.format(date));
			String dateParam = thaiNumeral(dateNumber)+" "+mounthName+" "+thaiNumeral(dateYear);
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("sequenceReport", "ที่");
			param.put("pDate", dateParam);
			param.put("pStdName", "ขอรับรองว่า "+student.getPrefix()+student.getFirstName()+" "+student.getLastName());
			param.put("pStdCode", "รหัสประจำตัวนักศึกษา"+thaiNumeral(Long.parseLong(student.getStudentCode())));
			param.put("pFacultyName", student.getFacultyName());
			param.put("pPeriod", "เป็นนักศึกษา "+student.getPeriod());
			param.put("pLevelName", "ระดับ"+student.getLevelCodeName());
			param.put("pDegreeName", student.getDegreeName());
			param.put("pDegreeAbb", "("+student.getDegreeAbb()+" "+thaiNumeral(student.getStudyYear())+" ปี)");
			param.put("pStudyYear", thaiNumeral(student.getStudyYear()));
			param.put("pProgramName", student.getProgramName());
			param.put("pStudentYear", "กำลังศึกษาอยู่ปี "+thaiNumeral(student.getStudentYear()));
			JasperReport jasperReport = JasperCompileManager.compileReport("D:/workspace/IDR/report/IDRReport.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/workspace/IDR/report/test.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private static String thaiNumeral(int number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("####");
		return df.format(number);
	}
	
	private static String thaiNumeral(long number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("##########");
		return df.format(number);
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
	
	private ResultSet getStudentInfo(String stdCode) throws SQLException{
		String sql = " 	SELECT STDM.STUDENTCODE AS STUDENTCODE ,	"
				+" 	  STDM.STUDENTNAME      AS STUDENTNAME,	"
				+" 	  STDM.STUDENTSURNAME ,	"
				+" 	  FAC.FACULTYNAME,	"
				+" 	  PRE.PREFIXNAME,	"
				+" 	  CASE WHEN STDM.STUDYPERIOD = 1 THEN 'ภาคปกติ'	"
				+" 	       ELSE 'ภาคพิเศษ'	"
				+" 	  END AS PERIOD,	"
				+" 	  LC.LEVELCODENAME AS LEVELCODENAME,	"
				+" 	  'หลักสูตร' ||DE.DEGREENAME AS DEGREENAME,	"
				+" 	  DE.DEGREEABB AS DEGREEABB,	"
				+" 	  PRO.STUDYYEAR AS STUDYYEAR,	"
				+" 	  'สาขาวิชา' ||PRO.PROGRAMNAME AS PROGRAMNAME,	"
				+" 	  STDM.STUDENTYEAR AS STUDENTYEAR	"
				+" 	FROM STUDENTMASTER STDM,	"
				+" 	  FACULTY FAC,	"
				+" 	  PREFIX PRE,	"
				+" 	  LEVELID LE,	"
				+" 	  LEVELCODE LC,	"
				+" 	  PROGRAM PRO,	"
				+" 	  DEGREE DE	"
				+" 	WHERE STDM.STUDENTCODE LIKE "+stdCode
				+" 	AND STDM.FACULTYID = FAC.FACULTYID	"
				+" 	AND STDM.PREFIXID  = PRE.PREFIXID	"
				+" 	AND STDM.LEVELID = LE.LEVELID	"
				+" 	AND LE.LEVELCODE = LC.LEVELCODE	"
				+" 	AND STDM.PROGRAMID = PRO.PROGRAMID	"
				+" 	AND PRO.DEGREEID = DE.DEGREEID ";
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