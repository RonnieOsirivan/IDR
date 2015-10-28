package th.ac.rbru.idr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.StaticValue;

/**
 * Servlet implementation class ReportLogController
 */
@WebServlet("/ReportLogController")
public class ReportLogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportLogController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getReportLog(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void getReportLog(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("sinceDate", request.getParameter("sinceDate"));
		param.put("untilDate", request.getParameter("untilDate"));
		if("0".equals(request.getParameter("reportType"))){
			param.put("reportType", "1,2,3,4,5,6,7,8");
		}else{
			param.put("reportType", request.getParameter("reportType"));
		}
		
		ConnectionDB.getInstance();
		Connection con = ConnectionDB.getRBRUMySQL();
		
		try {
			//for test localhost
//			JasperDesign jdesign = JRXmlLoader.load("/Users/rattasit/workspace/IDR/WebContent/report/reportLog.jrxml");
			
			JasperDesign jdesign = JRXmlLoader.load(getAbsulutePath()+StaticValue.REPORT_LOG);
			JasperReport jReport = JasperCompileManager.compileReport(jdesign);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Connection", "close");
//			response.setContentType("application/pdf");
			response.setContentType("blob");
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, param, con);
			JasperExportManager.exportReportToPdfStream(jPrint, baos);
			
//			response.setHeader("Content-disposition",
//	                  "inline; filename=" +
//	                  "Report.pdf" );
			
			response.setHeader("Content-disposition",
	                  "attachment; filename=" +
	                  "Report.pdf" );
			response.setContentLength(baos.size());
	
			
			ServletOutputStream out1 = response.getOutputStream();
			baos.writeTo(out1);
			
			baos.flush();
			baos.close();
			out1.flush();
			out1.close();
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		GenerateReport genReport = new GenerateReport();
//		String reportName = genReport.generarteReport("reportLog", "reportLog",param,getAbsulutePath());
//		sendResponse(request, response, reportName);
	}
	
	private String getAbsulutePath(){
		String abPath = getServletContext().getRealPath("/");
		return abPath;
	}
}