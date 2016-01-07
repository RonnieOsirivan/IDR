package th.ac.rbru.idr.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.model.Student;
import th.ac.rbru.idr.model.StudentEng;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.FormatNumber;
import th.ac.rbru.idr.util.GenerateReport;
import th.ac.rbru.idr.util.GennerateDocumentNum;
import th.ac.rbru.idr.util.ResultSetMapper;
import th.ac.rbru.idr.util.StaticValue;
import th.ac.rbru.idr.util.WordDelimiter;

public class ReportLastSemester {
private Connection con = null;
	
	public void generateReport(HttpServletRequest request,HttpServletResponse response){
		String[] langArray = (request.getParameter("lang")).split(",");
		try {
			for (String lang : langArray) {
				if("thai".equals(lang)){
						generateReportThai(request, response);
				}else{
						generateReportEng(request, response);
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateReportEng(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy",Locale.US);
		ResultSetMapper<StudentEng> studentResult = new ResultSetMapper<StudentEng>();
		StudentEng studentEng = (studentResult.mapRersultSetToObject(getStudentInfoEng(request.getParameter("studentCode")), StudentEng.class)).get(0);
		HashMap<String, String> map = new GennerateDocumentNum().getDocumentNum("2");
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		String detailParam = "	This is to cerify that "+NameFormat(studentEng.getPrefixNameEng())+" "
				+NameFormat(request.getParameter("firstName"))+" "
				+NameFormat(request.getParameter("surName"))+","
				+" student code number "+studentEng.getStudentCode()+" ";
				
		if(request.getParameter("passportNum") != null){
			detailParam += "date of birt is "+formatter.format(studentEng.getBirtDate())+
						" PASSPORTNUMBER "+request.getParameter("passportNum")+" ";
		}
		detailParam += "is currently a student of the "+studentEng.getDegreeNameEng();
		if(!((studentEng.getAdmitAcadYear() >= 2555 && studentEng.getDegreeId() == 204) || (studentEng.getAdmitAcadYear() >= 2556 && studentEng.getDegreeId() == 205 ))){
			if(!(studentEng.getProgramName().trim().substring(studentEng.getProgramName().trim().length()-1, studentEng.getProgramName().trim().length()).equals(")"))){
				detailParam += "  in "+studentEng.getProgramName();
				detailParam += " Program, ";
			}else{
				String proName = studentEng.getProgramName();
				detailParam += "  in "+proName.substring(0,proName.indexOf("(")) +" "+proName.substring(proName.indexOf("("), proName.length());
				detailParam += ", ";
			}
		}else{
			detailParam += " Program, ";
		}
		detailParam += studentEng.getFacultyName()+","+" and presently studying in the final academic semester. ";
		param.put("pSequenceReport", "No. "+documentNumEngFormat(map));
		param.put("pDate", formatter.format(date));
		param.put("pDetail", detailParam);
		
		//Image for test
//		param.put("pGarudaSymbol", "/Users/rattasit/workspace/IDR/WebContent/img/garudaSymbol.jpg");
		param.put("pGarudaSymbol", getAbsulutePath()+StaticValue.GARUDASYMBOL);
		param.put("pSignature", getAbsulutePath()+StaticValue.SIGNATURE);
		
		int reportId = insertReport(studentEng.getStudentCode(),studentEng.getPrefixNameThai()+studentEng.getStudentNameThai()+" "+studentEng.getStudentSurnameThai(),
				request.getParameter("telephoneParam"),request.getParameter("useforParam"),"EN",Integer.parseInt(map.get("docId")),studentEng.getFacultyNameThai(),studentEng.getProgramNameThai(),
				documentNumEngFormat(map),map.get("reportTypeId"));
		GenerateReport genReport = new GenerateReport();
		genReport.generarteReport("studentStatusEng",String.valueOf(reportId), param,getAbsulutePath());
		sendResponse(request, response, "Done!");
	}
	
	private void generateReportThai(HttpServletRequest request,HttpServletResponse response)throws SQLException, IOException{
		Date date = new Date();
//		SimpleDateFormat simpleDateFullDate = new SimpleDateFormat("dd MMMM yyyy",new Locale("th","th"));
		SimpleDateFormat simpleDateNumber = new SimpleDateFormat("dd",new Locale("th","th"));
		SimpleDateFormat simpleDateMounth = new SimpleDateFormat("MMMM",new Locale("th","th"));
		SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy",new Locale("th","th"));
		FormatNumber formatNumber = new FormatNumber();
		
		String stdCode = request.getParameter("studentCode");
		
		ResultSetMapper<Student> resultSet = new ResultSetMapper<Student>();
		List<Student> studentList = resultSet.mapRersultSetToObject(getStudentInfoThai(stdCode), Student.class);
		Student student = studentList.get(0);
		
		int dateNumber = Integer.parseInt(simpleDateNumber.format(date));
		String mounthName = simpleDateMounth.format(date);
		int dateYear = Integer.parseInt(simpleDateYear.format(date));
		String dateParam = formatNumber.thaiNumber("##", dateNumber)+"  "+mounthName+"  "+formatNumber.thaiNumber("####", dateYear);
		HashMap<String, String> map = new GennerateDocumentNum().getDocumentNum("2");
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		String pDetail = "	ขอรับรองว่า  "+student.getPrefix()+student.getFirstName()+"  "+student.getLastName()
				+ "  รหัสประจำตัวนักศึกษา  "+formatNumber.thaiNumber("##########", Long.parseLong(student.getStudentCode()));
				
		if(request.getParameter("passportNum") != null){
			pDetail += "  เกิดวันที่  "+formatNumber.thaiNumber("##", Integer.parseInt(simpleDateNumber.format(student.getBirthDate())))+"  "
					+simpleDateMounth.format(student.getBirthDate())+"  "
					+formatNumber.thaiNumber("####", Integer.parseInt(simpleDateYear.format(student.getBirthDate())))+"  "
					+"เลขที่พาสปอร์ต  "+request.getParameter("passportNum");
		}
				
		pDetail += "  เป็นนักศึกษา"+student.getPeriod()
		+ "  ระดับ"+student.getLevelCodeName()
		+ "  "+student.getDegreeName()
		+ "  ("+student.getDegreeAbb()+"  "+formatNumber.thaiNumber("#", student.getStudyYear())+"  ปี)";
		
		if(!((student.getAdmitAcadYear() >= 2555 && student.getDegreeID() == 204) || (student.getAdmitAcadYear() >= 2556 && student.getDegreeID() == 205 ))){
			pDetail += "  "+student.getProgramName();
		}
			
		
		pDetail += "  กำลังศึกษาอยู่ในภาคการศึกษาสุดท้ายที่มหาวิทยาลัยราชภัฏรำไพพรรณี  จริง ";
		param.put("pSequenceReport", "ที่  "+formatNumber.thaiNumber("##", Integer.parseInt(map.get("reportTypeId")))+"."+formatNumber.thaiNumber("000", Integer.parseInt(map.get("docRuningNum")))+" / "+formatNumber.thaiNumber("####", Integer.parseInt(map.get("acadYear"))));
		param.put("pDate", dateParam);
		
		//Image for test
//		param.put("pGarudaSymbol", "/Users/rattasit/workspace/IDR/WebContent/img/garudaSymbol.jpg");
		param.put("pGarudaSymbol", getAbsulutePath()+StaticValue.GARUDASYMBOL);
		param.put("pSignature", getAbsulutePath()+StaticValue.SIGNATURE);
		
		WordDelimiter wdlt = new WordDelimiter();
		pDetail = wdlt.wordDelimiter(pDetail);
		
		param.put("pDetail", pDetail);
		
		int reportId = insertReport(student.getStudentCode(),student.getPrefix()+student.getFirstName()+" "+student.getLastName(),
				request.getParameter("telephoneParam"),request.getParameter("useforParam"),"TH",Integer.parseInt(map.get("docId")),
				student.getFacultyName(),student.getProgramName(),documentNumEngFormat(map),map.get("reportTypeId"));
		GenerateReport genReport = new GenerateReport();
		genReport.generarteReport("studentStatusThai", String.valueOf(reportId),param,getAbsulutePath());
//		FileInputStream pdfStream = convertPdfToBinary("/Users/rattasit/workspace/IDR/WebContent/reportFile/test.pdf");
		sendResponse(request, response, "Done!");
	}
	
//	private FileInputStream convertPdfToBinary(String path){
//		File pdfFile = new File(path);
//		FileInputStream inputStream = null;
//		try {
//			inputStream = new FileInputStream(pdfFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return inputStream;
//	}
	
	private int insertReport(String stdCode,String stdName,String telephoneNum,String usefor,String language,int docId,String facName,String programName,String docNum,String reportTypeId){
		String stdInsertSql = "INSERT IGNORE INTO STUDENT(STUDENTCODE,STUDENTNAME,TELEPHONENUMBER) VALUES (?,?,?)";
		String pdfInsertSql = "INSERT INTO REPORT(STUDENTCODE,REPORTTYPEID,USEFOR,REPORTFILE,LANGUAGE,DOCUMENTID) VALUES(?,?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		ConnectionDB.getInstance();
		int reportId = 0;
		con = ConnectionDB.getRBRUMySQL();
		try {
			stmt = con.prepareStatement(stdInsertSql);
			stmt.setString(1, stdCode);
			stmt.setString(2, stdName);
			stmt.setString(3, telephoneNum);
			stmt.execute();
			stmt.close();
			
			stmt = con.prepareStatement(pdfInsertSql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, stdCode);
			stmt.setInt(2, Integer.parseInt(reportTypeId));
			stmt.setString(3,usefor);
			stmt.setString(4, programName);
			stmt.setString(5, language);
			stmt.setInt(6, docId);
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()){
				reportId = rs.getInt(1);
			}
			
			String reportPathSql = "UPDATE REPORT SET REPORTFILE = ? WHERE REPORTID = ?";
			String reportPath = StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
			stmt = con.prepareStatement(reportPathSql);
			stmt.setString(1, reportPath);
			stmt.setInt(2, reportId);
			stmt.execute();
			stmt.close();
			
			String reportLogInsertSql = "	INSERT INTO `IDR`.`REPORTLOG` (`REPORTID`, `STUDENTCODE`, 	"+
					"	`FACULTYNAME`,`PROGRAMNAME`,`DOCUMENTNUMBER`, `REPORTTYPEID`,`REQUESTDATE`) 	"+
					"	VALUES (?, ?, ?, ?, ?,?,?)	";
			
			Timestamp timeStamp = new Timestamp(new Date().getTime());
			stmt = con.prepareStatement(reportLogInsertSql);
			stmt.setInt(1, reportId);
			stmt.setString(2, stdCode);
			stmt.setString(3, facName);
			stmt.setString(4, programName);
			stmt.setString(5, docNum);
			stmt.setInt(6, Integer.parseInt(reportTypeId));
			stmt.setTimestamp(7, timeStamp);
			stmt.executeUpdate();
			stmt.close();
			
			releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reportId;
	}
	
//	//overload method
//	private String thaiNumeral(int number){
//		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
//		df.applyPattern("####");
//		return df.format(number);
//	}
//	
//	private String thaiNumeral3Digit(int number){
//		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
//		df.applyPattern("####");
//		String docNum = "";
//		if(df.format(number).length() == 1){
//			docNum = "๐๐"+df.format(number);
//		}else if(df.format(number).length() == 2){
//			docNum = "๐"+df.format(number);
//		}else{
//			docNum = df.format(number);
//		}
//		return docNum;
//	}
//	
//	//overload method
//	private String thaiNumeral(long number){
//		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
//		df.applyPattern("##########");
//		return df.format(number);
//	}
	
	private String documentNumEngFormat(HashMap<String,String> docNum){
		FormatNumber formatNumber = new FormatNumber();
		String docRunnigNum = formatNumber.EngNumber("000", Integer.parseInt(docNum.get("docRuningNum")));
		return docNum.get("reportTypeId")+"."+docRunnigNum+" / "+(Integer.parseInt(docNum.get("acadYear"))-543);
	}
	
	private String NameFormat(String str){
		str = str.toLowerCase();
		str = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
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
		String sql = "	SELECT STDM.STUDENTCODE AS STUDENTCODE ,	"+
				"	  STDM.STUDENTNAME      AS STUDENTNAME,	"+
				"	  STDM.STUDENTSURNAME AS STUDENTSURNAME,	"+
				"	  STDBIO.BIRTHDATE AS BIRTHDATE,	"+
				"	  FAC.FACULTYNAME,	"+
				"	  PRE.PREFIXNAME,	"+
				"	  CASE	"+
				"	    WHEN STDM.STUDYPERIOD = 1	"+
				"	    THEN 'ภาคปกติ'	"+
				"	    ELSE 'ภาคพิเศษ'	"+
				"	  END              AS PERIOD,	"+
				"	  LC.LEVELCODENAME AS LEVELCODENAME,	"+
				"	  'หลักสูตร'	"+
				"	  ||DE.DEGREECERTIFICATE AS DEGREECERTIFICATE,	"+
				"	  DE.DEGREEABB           AS DEGREEABB,	"+
				"	  PRO.STUDYYEAR          AS STUDYYEAR,	"+
				"	  'สาขาวิชา'	"+
				"	  ||PRO.PROGRAMNAME AS PROGRAMNAME,	"+
				"	  STDM.STUDENTYEAR  AS STUDENTYEAR,	"+
				"	STDM.ADMITACADYEAR AS ADMITACADYEAR, "+
				"	DE.DEGREEID AS DEGREEID"+
				"	FROM STUDENTMASTER STDM,	"+
				"	  STUDENTBIO STDBIO,	"+
				"	  FACULTY FAC,	"+
				"	  PREFIX PRE,	"+
				"	  LEVELID LE,	"+
				"	  LEVELCODE LC,	"+
				"	  PROGRAM PRO,	"+
				"	  DEGREE DE	"+
				"	WHERE STDM.STUDENTCODE LIKE "+stdCode+
				"	AND STDM.FACULTYID = FAC.FACULTYID	"+
				"	AND STDM.PREFIXID  = PRE.PREFIXID	"+
				"	AND STDM.LEVELID   = LE.LEVELID	"+
				"	AND STDM.STUDENTID = STDBIO.STUDENTID	"+
				"	AND LE.LEVELCODE   = LC.LEVELCODE	"+
				"	AND STDM.PROGRAMID = PRO.PROGRAMID	"+
				"	AND PRO.DEGREEID   = DE.DEGREEID	";
		return getData(sql);
	}
	
	private ResultSet getStudentInfoEng(String stdCode) throws SQLException{
		String sql = "	SELECT PRE.PREFIXNAMEENG AS PREFIXNAMEENG,	"+
				"	PRE.PREFIXNAME AS PREFIXNAME,	"+
				"	NVL(STDM.STUDENTNAMEENG,'NOT SHOW') AS STUDENTNAMEENG,	"+
				"	NVL(STDM.STUDENTSURNAMEENG,'NOT SHOW') AS STUDENTSURNAMEENG,	"+
				"	STDM.STUDENTNAME      AS STUDENTNAME,	"+
				"	STDM.STUDENTSURNAME AS STUDENTSURNAME,	"+
				"	STDM.STUDENTCODE AS STUDENTCODE,	"+
				"	STDBIO.BIRTHDATE AS BIRTHDATE, "+
				"	DE.DEGREENAMEENG AS DEGREENAMEENG,	"+
				"	  'สาขาวิชา'	||"+
				"	PRO.PROGRAMNAME AS PROGRAMNAME,	"+
				"	PRO.PROGRAMNAMEENG AS PROGRAMNAMEENG,	"+
				"	FAC.FACULTYNAME	AS FACULTYNAMETHAI,"+
				"	FAC.FACULTYNAMEENG AS FACULTYNAMEENG	,"+
				"	STDM.ADMITACADYEAR AS ADMITACADYEAR, "+
				"	DE.DEGREEID AS DEGREEID"+
				"	FROM STUDENTMASTER STDM,	"+
				"	STUDENTBIO	STDBIO, "+
				"	FACULTY FAC,	"+
				"	  PREFIX PRE,	"+
				"	  PROGRAM PRO,	"+
				"	  DEGREE DE	"+
				"	WHERE STDM.STUDENTCODE LIKE "+stdCode+
				"	AND STDM.STUDENTID = STDBIO.STUDENTID "+
				"	AND STDM.FACULTYID = FAC.FACULTYID	"+
				"	AND STDM.PREFIXID  = PRE.PREFIXID	"+
				"	AND STDM.PROGRAMID = PRO.PROGRAMID	"+
				"	AND PRO.DEGREEID   = DE.DEGREEID "+
				"	AND PRO.PROGRAMSTATUS = 40 ";
		return getData(sql);
	}
	
	 private String getAbsulutePath(){
	    	String abPath = StaticValue.ABSULUTEPATH;
	    	return abPath;
	 }
	
//	private ResultSet getDocumentNumber() throws SQLException{
//		String sql = " 	SELECT RE.RUNNINGNUMBER+1 AS RUNNINGNUMBER,	"
//				+" 	  TACADYEAR.ACADYEAR      AS ACADYEAR	"
//				+" 	FROM REQUEST RE,	"
//				+" 	  (SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') - DFS.ACADYEARADJ AS ACADYEAR	"
//				+" 	  FROM DEFAULTSEMESTER DFS	"
//				+" 	  WHERE DFS.SYSAPPID = 25	"
//				+" 	  AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')	"
//				+" 	  ) TACADYEAR	"
//				+" 	WHERE ROWNUM <= 1	"
//				+" 	ORDER BY RE.REQUESTID DESC";
//		System.out.println(sql);
//		return getData(sql);
//	}
	
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
				e.printStackTrace(System.err);
			}
		}
	}
}
