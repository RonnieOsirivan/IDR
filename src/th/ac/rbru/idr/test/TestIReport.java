package th.ac.rbru.idr.test;

import org.apache.commons.collections.map.HashedMap;

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
			JasperReport jasperReport = JasperCompileManager.compileReport("report/IDRReport.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashedMap(),new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "test.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
