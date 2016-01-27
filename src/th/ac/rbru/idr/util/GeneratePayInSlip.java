package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import th.ac.rbru.idr.model.Report;
import th.ac.rbru.idr.model.Student;

public class GeneratePayInSlip {
	private Connection con;
	
	public void genPayInSlip(String reportId,Student student,String abPath){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("pPeriod", student.getPeriod());
		
		if(!((student.getAdmitAcadYear() >= 2555 && student.getDegreeID() == 204) || (student.getAdmitAcadYear() >= 2556 && student.getDegreeID() == 205 ))){
			param.put("pPrograme", student.getProgramName());
		}else{
			param.put("pPrograme", student.getDegreeName());
		}
		
		if(student.getStudyYear() >= student.getStudentYear()){
			param.put("pStudentYear", "ชั้นปีที่ "+student.getStudyYear());
		}else{
			param.put("pStudentYear", "ชั้นปีที่ -");
		}
		
		param.put("pGroup", "หมู่ "+(student.getStudentCode()).substring(6, 7));
		
		param.put("pLevel", "ระดับ"+student.getLevelCodeName());
		
		SimpleDateFormat simpleDateFullDate = new SimpleDateFormat("dd  MMMM  yyyy",new Locale("th","th"));
		param.put("pDate", "วันที่  "+simpleDateFullDate.format(new Date()));
		param.put("pName", student.getPrefix()+student.getFirstName()+"  "+student.getLastName());
		
		ResultSetMapper<Report> reportResult = new ResultSetMapper<Report>();
		Report report = reportResult.mapRersultSetToObject(getReportListForAdminSql(reportId), Report.class).get(0);
		
		String pDetail = "	ข้าพเจ้าชื่อ  "+student.getPrefix()+student.getFirstName()+"  "+student.getLastName()+"  ";
		pDetail += "รหัสประจำตัวนักศึกษา  "+student.getStudentCode()+"  ";
		pDetail += "โทรศัพท์  "+report.getTelNum()+"  มีความประสงค์ขอใบรับรอง";
		param.put("pDetail", pDetail);
		
		if(("TH").equalsIgnoreCase(report.getLanguage())){
			param.put("pReportName", report.getReportName()+"(ไทย)");
		}else{
			param.put("pReportName", report.getReportName()+"(ENG)");
		}
		
		if(report.getUseFor().trim().length() != 0){
			param.put("pUserFor","นำไปใช้เพื่อ  "+ report.getUseFor());
		}else{
			param.put("pUserFor", " ");
		}
		
		String reportTypeResourceFile = abPath+StaticValue.PAY_IN_SLIP;
		String reportFileDirectory = abPath+StaticValue.PAY_IN_SLIP_FILE_DIRECTORY+reportId+".pdf";
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTypeResourceFile);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileDirectory);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet getReportListForAdminSql(String reportId){
		String sql = "	SELECT RP.REPORTID AS REPORTID ,	"+
				"	STD.STUDENTCODE    AS STUDENTCODE,	"+
				"	STD.STUDENTNAME    AS STUDENTNAME,	"+
				"	RPT.REPORTNAMETHAI AS REPORTNAMETHAI,	"+
				"	RP.LANGUAGE AS LANGUAGE , "+
				"	RP.REPORTPRINTSTATUS AS REPORTPRINTSTATUS,	"+
				"	RP.CREATEDATE AS CREATEDATE,	"+
				"	RP.TELEPHONENUMBER   AS TELEPHONENUMBER ,"+
				"	RP.USEFOR AS USEFOR"+
				"	FROM STUDENT AS STD ,REPORT AS RP , REPORTTYPE AS RPT	"+
				"	WHERE STD.STUDENTCODE = RP.STUDENTCODE	"+
				"	AND RP.REPORTTYPEID = RPT.REPORTTYPEID "+
				"	AND RP.REPORTID = "+reportId+
				"	ORDER BY RP.CREATEDATE ";
		return getDataMySql(sql);
	}
	
	private ResultSet getDataMySql(String sql){
		ResultSet result = null;
		
		try {
			ConnectionDB.getInstance();
			con = ConnectionDB.getRBRUMySQL();
			Statement statement = con.createStatement();
			result = statement.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}