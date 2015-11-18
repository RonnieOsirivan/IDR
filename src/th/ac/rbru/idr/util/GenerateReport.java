package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;



public class GenerateReport {
	public void generarteReport(String reportTypeResource,String reportId,HashMap<String, Object> param,String abPath){
		String reportFileDirectory="";
		String reportTypeResourceFile = "";
		
		if("studentStatusThai".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_THAI;
			reportFileDirectory = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
			
			//for test
//			reportTypeResource = "/Users/rattasit/workspace/IDR/WebContent/report/IDRReport.jrxml";
		}else if("studentStatusEng".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_ENG;
			reportFileDirectory = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
			
			//for test
//			reportTypeResource = "/Users/rattasit/workspace/IDR/WebContent/report/IDRReportEng.jrxml";
		}else if("reportLog".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_LOG;
			reportFileDirectory = abPath+StaticValue.REPORT_LOG_FILE_DIRECTORY+reportId+".pdf";
		}
		
		//for reportFileDirectory test localhost
//		reportFileDirectory = "/Users/rattasit/workspace/IDR/WebContent/reportFile/"+reportId+".pdf"; 
//		reportFileDirectory = "/Users/rattasit/workspace/IDR/WebContent/reportLogFile/"+reportId+time+".pdf"; 
		
		//for reportTypeResource test localhost
//		reportTypeResource = "/Users/rattasit/workspace/IDR/WebContent/report/IDRReport.jrxml";
//		reportTypeResource = "/Users/rattasit/workspace/IDR/WebContent/report/IDRReportEng.jrxml";
//		reportTypeResourceFile = "/Users/rattasit/workspace/IDR/WebContent/report/reportLog.jrxml";
		
		try {
			@SuppressWarnings("static-access")
			Connection con = ConnectionDB.getInstance().getRBRUMySQL();
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTypeResourceFile);
			JasperPrint jasperPrint;
			if("reportLog".equals(reportTypeResource)){
				jasperPrint = JasperFillManager.fillReport(jasperReport, param,con);
			}else{
				jasperPrint = JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
			}
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileDirectory);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
