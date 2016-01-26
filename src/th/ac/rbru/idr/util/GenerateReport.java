package th.ac.rbru.idr.util;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;



public class GenerateReport {
	public void generarteReport(String reportTypeResource,String reportId,HashMap<String, Object> param,String abPath){
		String reportFileDirectory="";
		String reportFileDirectoryDocx="";
		String reportTypeResourceFile = "";
		String reportTypeResourceFileDocx = "";
		
		if("studentStatusThai".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_THAI;
			reportTypeResourceFileDocx = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_THAI_DOCX;
			
		}else if("studentStatusEng".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_ENG;
			reportTypeResourceFileDocx = abPath+StaticValue.REPORT_TYPE_STUDENT_STATUS_ENG_DOCX;
		}else if("reportLog".equals(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_LOG;
		}else if("gradeEachSemesterThai".equalsIgnoreCase(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_GRADE_EACH_SEMESTER;
			reportTypeResourceFileDocx = abPath+StaticValue.REPORT_GRADE_EACH_SEMESTER_DOCX;
		}else if("gradeEachSemesterEng".equalsIgnoreCase(reportTypeResource)){
			reportTypeResourceFile = abPath+StaticValue.REPORT_GRADE_EACH_SEMESTER_ENG;
			reportTypeResourceFileDocx = abPath+StaticValue.REPORT_GRADE_EACH_SEMESTER_ENG_DOCX;
		}
		
		reportFileDirectory = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
		reportFileDirectoryDocx = abPath+StaticValue.REPORT_FILE_DIRECTORY+reportId+".docx";
		
		//for reportFileDirectory test localhost
//		reportFileDirectory = "/Users/rattasit/workspace/IDR/WebContent/reportFile/"+reportId+".pdf"; 
		
		try {
			@SuppressWarnings("static-access")
			Connection con = ConnectionDB.getInstance().getRegConnection();
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTypeResourceFile);
			JasperReport jasperReportDocx = JasperCompileManager.compileReport(reportTypeResourceFileDocx);
			JasperPrint jasperPrint = null;
			JasperPrint jasperPrintDocx = null;
			if("gradeEachSemesterThai".equals(reportTypeResource) || "gradeEachSemesterEng".equals(reportTypeResource)){
				jasperPrint = JasperFillManager.fillReport(jasperReport, param,con);
				jasperPrintDocx = JasperFillManager.fillReport(jasperReportDocx, param,con);
			}else{
				jasperPrint = JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
				jasperPrintDocx = JasperFillManager.fillReport(jasperReportDocx, param,new JREmptyDataSource());
			}
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileDirectory);
			
			//Generate file docx
			JRDocxExporter export = new JRDocxExporter();
			export.setExporterInput(new SimpleExporterInput(jasperPrintDocx));
			export.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportFileDirectoryDocx)));
			SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
			//config.setFlexibleRowHeight(true); //Set desired configuration
			export.setConfiguration(config);
			export.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}