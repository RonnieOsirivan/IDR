package th.ac.rbru.idr.util;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class GenerateReport {
	public void generarteReport(String reportTypeResource,int reportId,HashMap<String, Object> param){
		if("studentStatusThai".equals(reportTypeResource)){
			reportTypeResource = StaticValue.REPORT_TYPE_STUDENT_STATUS_THAI;
		}else if("studentStatusEng".equals(reportTypeResource)){
			reportTypeResource = StaticValue.REPORT_TYPE_STUDENT_STATUS_ENG;
		}
		String reportFileDirectory = StaticValue.REPORT_FILE_DIRECTORY+reportId+".pdf";
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTypeResource);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileDirectory);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
