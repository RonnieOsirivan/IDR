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

import th.ac.rbru.idr.model.DocumentNumber;
import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.model.StudentEng;
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
			if(request.getParameter("lang0") != null && request.getParameter("lang1") != null){
				generateReportThai(request);
				generateReportEng(request);
			}else if(request.getParameter("lang0") != null){
				generateReportThai(request);
			}else{
				generateReportEng(request);
			}
		} catch (SQLException | JRException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void generateReportEng(HttpServletRequest request) throws SQLException, JRException{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy",Locale.US);
		ResultSetMapper<StudentEng> studentResult = new ResultSetMapper<StudentEng>();
		StudentEng studentEng = (studentResult.mapRersultSetToObject(getStudentInfoEng(request.getParameter("studentCode")), StudentEng.class)).get(0);
		System.out.println(ConvertDataType.getInstance().objectToJasonArray(studentEng));
		
		ResultSetMapper<DocumentNumber> resultSetD = new ResultSetMapper<DocumentNumber>();
		DocumentNumber documentNumber = (resultSetD.mapRersultSetToObject(getDocumentNumber(), DocumentNumber.class)).get(0);
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sequenceReport", "No. "+documentNumber.getRunningNumber()+" / "+documentNumber.getAcadyear());
		param.put("pDate", formatter.format(date));
		param.put("pStdName", "This is to cerify that "+NameFormat(studentEng.getPrefixName())+" "+NameFormat(request.getParameter("firstName"))+" "+NameFormat(request.getParameter("surName"))+",");
		param.put("pStdCode", "student code number "+studentEng.getStudnetCode()+" ");
		param.put("pDegreeName", "is currently a student of the "+studentEng.getDegreeCerificate());
		param.put("pProgramName", "Faculty of "+studentEng.getProgramName());
		param.put("pFacultyName", "in "+studentEng.getFacultyName()+",");
		JasperReport jasperReport = JasperCompileManager.compileReport("/Users/rattasit/workspace/IDR/report/IDRReportEng.jrxml");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/rattasit/workspace/IDR/report/testEng.pdf");
	}
	
	private void generateReportThai(HttpServletRequest request)throws SQLException{
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateNumber = new SimpleDateFormat("dd",new Locale("th","th"));
			SimpleDateFormat simpleDateMounth = new SimpleDateFormat("MMMM",new Locale("th","th"));
			SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy",new Locale("th","th"));
			
			String stdCode = request.getParameter("studentCode");
			
			ResultSetMapper<Student> resultSet = new ResultSetMapper<Student>();
			List<Student> studentList = resultSet.mapRersultSetToObject(getStudentInfoThai(stdCode), Student.class);
			Student student = studentList.get(0);
			String resultJson = ConvertDataType.getInstance().objectToJasonArray(studentList);
			System.out.println(resultJson);
			
			ResultSetMapper<DocumentNumber> resultSetD = new ResultSetMapper<DocumentNumber>();
			List<DocumentNumber> documentNumber = resultSetD.mapRersultSetToObject(getDocumentNumber(), DocumentNumber.class);
			
			int dateNumber = Integer.parseInt(simpleDateNumber.format(date));
			String mounthName = simpleDateMounth.format(date);
			int dateYear = Integer.parseInt(simpleDateYear.format(date));
			String dateParam = thaiNumeral(dateNumber)+" "+mounthName+" "+thaiNumeral(dateYear);
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("sequenceReport", "ที่ "+thaiNumeral(documentNumber.get(0).getRunningNumber())+" / "+thaiNumeral(documentNumber.get(0).getAcadyear()));
			param.put("pDate", dateParam);
			param.put("pStdName", "ขอรับรองว่า "+student.getPrefix()+student.getFirstName()+" "+student.getLastName());
			param.put("pStdCode", "รหัสประจำตัวนักศึกษา "+thaiNumeral(Long.parseLong(student.getStudentCode())));
			param.put("pFacultyName", student.getFacultyName());
			param.put("pPeriod", "เป็นนักศึกษา "+student.getPeriod());
			param.put("pLevelName", "ระดับ"+student.getLevelCodeName());
			param.put("pDegreeName", student.getDegreeName());
			param.put("pDegreeAbb", "("+student.getDegreeAbb()+" "+thaiNumeral(student.getStudyYear())+" ปี)");
			param.put("pStudyYear", thaiNumeral(student.getStudyYear()));
			param.put("pProgramName", student.getProgramName());
			param.put("pStudentYear", "กำลังศึกษาอยู่ปี "+thaiNumeral(student.getStudentYear()));
			JasperReport jasperReport = JasperCompileManager.compileReport("/Users/rattasit/workspace/IDR/report/IDRReport.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/rattasit/workspace/IDR/report/test.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	//overload method
	private String thaiNumeral(int number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("####");
		return df.format(number);
	}
	
	//overload method
	private String thaiNumeral(long number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("##########");
		return df.format(number);
	}
	
	private String NameFormat(String str){
		str = str.toLowerCase();
		str = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		System.out.println(str);
		return str;
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
	
	private ResultSet getStudentInfoThai(String stdCode) throws SQLException{
		String sql = " 	SELECT STDM.STUDENTCODE AS STUDENTCODE ,	"
				+" 	  STDM.STUDENTNAME      AS STUDENTNAME,	"
				+" 	  STDM.STUDENTSURNAME ,	"
				+" 	  FAC.FACULTYNAME,	"
				+" 	  PRE.PREFIXNAME,	"
				+" 	  CASE WHEN STDM.STUDYPERIOD = 1 THEN 'ภาคปกติ'	"
				+" 	       ELSE 'ภาคพิเศษ'	"
				+" 	  END AS PERIOD,	"
				+" 	  LC.LEVELCODENAME AS LEVELCODENAME,	"
				+" 	  'หลักสูตร' ||DE.DEGREECERTIFICATE AS DEGREECERTIFICATE,	"
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
	
	private ResultSet getStudentInfoEng(String stdCode) throws SQLException{
		String sql = "	SELECT PRE.PREFIXNAMEENG AS PREFIXNAMEENG,	"+
				"	NVL(STDM.STUDENTNAMEENG,'NOT SHOW') AS STUDENTNAMEENG,	"+
				"	NVL(STDM.STUDENTSURNAMEENG,'NOT SHOW') AS STUDENTSURNAMEENG,	"+
				"	STDM.STUDENTCODE AS STUDENTCODE,	"+
				"	DE.DEGREECERTIFICATEENG AS DEGREECERTIFICATEENG,	"+
				"	PRO.PROGRAMNAMEENG AS PROGRAMNAMEENG,	"+
				"	'Hummanities and Socail Science' AS FACULTYNAMEENG	"+
				"	FROM STUDENTMASTER STDM,	"+
				"	FACULTY FAC,	"+
				"	  PREFIX PRE,	"+
				"	  PROGRAM PRO,	"+
				"	  DEGREE DE	"+
				"	WHERE STDM.STUDENTCODE LIKE "+stdCode+
				"	AND STDM.FACULTYID = FAC.FACULTYID	"+
				"	AND STDM.PREFIXID  = PRE.PREFIXID	"+
				"	AND STDM.PROGRAMID = PRO.PROGRAMID	"+
				"	AND PRO.DEGREEID   = DE.DEGREEID ";
		return getData(sql);
	}
	
	private ResultSet getDocumentNumber() throws SQLException{
		String sql = " 	SELECT RE.RUNNINGNUMBER+1 AS RUNNINGNUMBER,	"
				+" 	  TACADYEAR.ACADYEAR      AS ACADYEAR	"
				+" 	FROM REQUEST RE,	"
				+" 	  (SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') - DFS.ACADYEARADJ AS ACADYEAR	"
				+" 	  FROM DEFAULTSEMESTER DFS	"
				+" 	  WHERE DFS.SYSAPPID = 25	"
				+" 	  AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')	"
				+" 	  ) TACADYEAR	"
				+" 	WHERE ROWNUM <= 1	"
				+" 	ORDER BY RE.REQUESTID DESC";
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