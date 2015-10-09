package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class GenerateReport {
	public void generarteReport(String reportTypeResource,int reportId,HashMap<String, Object> param,String abPath){
		String reportFileDirectory="";
		if("studentStatusThai".equals(reportTypeResource)){
			reportTypeResource = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_THAI;
			reportFileDirectory = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
		}else if("studentStatusEng".equals(reportTypeResource)){
			reportTypeResource = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_ENG;
			reportFileDirectory = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
		}else if("reportLog".equals(reportTypeResource)){
			reportTypeResource = abPath+StaticValue.REPORT_LOG;
			reportFileDirectory = abPath+StaticValue.REPORT_LOG_FILE_DIRECTORY+reportId+".pdf";
		}
		
		//for reportFileDirectory test localhost
		reportFileDirectory = "/Users/rattasit/workspace/IDR/WebContent/reportLogFile/"+reportId+".pdf"; 
		try {
			//for reportTypeResource test localhost
			reportTypeResource = "/Users/rattasit/workspace/IDR/WebContent/report/reportLog.jrxml";
			@SuppressWarnings("static-access")
			Connection con = ConnectionDB.getInstance().getRBRUMySQL();
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTypeResource);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param,con);
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileDirectory);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
