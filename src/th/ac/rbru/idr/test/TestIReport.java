package th.ac.rbru.idr.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

public class TestIReport {
	public static void main(String[] args) {
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateNumber = new SimpleDateFormat("dd",new Locale("th","th"));
			SimpleDateFormat simpleDateMounth = new SimpleDateFormat("MMMM",new Locale("th","th"));
			SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy",new Locale("th","th"));
			
			int dateNumber = Integer.parseInt(simpleDateNumber.format(date));
			int dateYear = Integer.parseInt(simpleDateYear.format(date));
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("pSequeneReport", "ที่");
			JasperReport jasperReport = JasperCompileManager.compileReport("report/IDRReport.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "test.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public static String thaiNumeral(int number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("####");
		return df.format(number);
	}
}
