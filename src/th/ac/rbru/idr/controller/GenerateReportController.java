package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>GenerateReportController");
		generateReport(request);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void generateReport(HttpServletRequest request){
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateNumber = new SimpleDateFormat("dd",new Locale("th","th"));
			SimpleDateFormat simpleDateMounth = new SimpleDateFormat("MMMM",new Locale("th","th"));
			SimpleDateFormat simpleDateYear = new SimpleDateFormat("yyyy",new Locale("th","th"));
			
			String stdName = request.getParameter("fname")+"  "+request.getParameter("lname");
			String stdCode = request.getParameter("studentCode");
			
			int dateNumber = Integer.parseInt(simpleDateNumber.format(date));
			String mounthName = simpleDateMounth.format(date);
			int dateYear = Integer.parseInt(simpleDateYear.format(date));
			String dateParam = thaiNumeral(dateNumber)+" "+mounthName+" "+thaiNumeral(dateYear);
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("sequenceReport", "ที่");
			param.put("pDate", dateParam);
			param.put("pStdName", stdName);
			param.put("pStdCode", stdCode);
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
}