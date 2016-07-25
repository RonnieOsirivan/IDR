package th.ac.rbru.idr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

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
import th.ac.rbru.idr.model.ReportType;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ConvertDataType;
import th.ac.rbru.idr.util.ResultSetMapper;
import th.ac.rbru.idr.util.StaticValue;

/**
 * Servlet implementation class ReportLogController
 */
@WebServlet("/ReportLogController")
public class ReportLogController extends HttpServlet {
	private Connection con = null;
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
		if(request.getParameter("reportType").equalsIgnoreCase("getDropdown")){
			getDropdown(request, response);
		}else{
			getReportLog(request, response);
		}
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
//		if("studentStatus".equals(request.getParameter("reportType"))){
//			param.put("reportType", "AND RL.REPORTTYPEID = 1");
//		}else if("lastSemester".equals(request.getParameter("reportType"))){
//			param.put("reportType", "AND RL.REPORTTYPEID = 2");
//		}else if("completeGraduate".equals(request.getParameter("reportType"))){
//			param.put("reportType", "AND RL.REPORTTYPEID = 3");
//		}else if("gradeEachSemester".equals(request.getParameter("reportType"))){
//			param.put("reportType", "AND RL.REPORTTYPEID = 4");
//		}else if("summaryGPA".equals(request.getParameter("reportType"))){
//			param.put("reportType", "AND RL.REPORTTYPEID = 5");
//		}else{
//			param.put("reportType", "");
//		}

		if("all".equalsIgnoreCase(request.getParameter("reportType"))){
			param.put("reportType", "");
		}else{
			param.put("reportType", "AND RL.REPORTTYPEID = "+request.getParameter("reportType"));
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
		
	}
	
	//get type report for dropdown list
	private void getDropdown(HttpServletRequest request, HttpServletResponse response){
		String sql =" SELECT * FROM IDR.REPORTTYPE ";
		ResultSet resultSet = getData(sql);
		ResultSetMapper<ReportType> reportTypeMapper = new ResultSetMapper<ReportType>();
		List<ReportType> reportTypeList = reportTypeMapper.mapRersultSetToObject(resultSet, ReportType.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(reportTypeList);
		sendResponse(request, response, resultJson);
	}
	
	private ResultSet getData(String sql) {
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
	
	private void sendResponse(HttpServletRequest request,HttpServletResponse response,String resultJson){
		try{
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(resultJson);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String getAbsulutePath(){
		String abPath = getServletContext().getRealPath("/");
		return abPath;
	}
}