package th.ac.rbru.idr.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import th.ac.rbru.idr.model.Report;
import th.ac.rbru.idr.util.ConnectionDB;
import th.ac.rbru.idr.util.ConvertDataType;
import th.ac.rbru.idr.util.ResultSetMapper;
import th.ac.rbru.idr.util.RoleCheck;
import th.ac.rbru.idr.util.StaticValue;

/**
 * Servlet implementation class ManagementReportController
 */
@WebServlet("/ManagementReportController")
public class ManagementReportController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection con = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementReportController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		RoleCheck roleCheck = new RoleCheck();
		if(request.getParameter("reportId")!=null){
			//view file report type pdf
			getReportPdf(request,response);
		}else{
			//view report list for management page
			if(roleCheck.hasRole("ROLE_ADMIN")){
				getReportListForAdmin(request, response);
			}else{
				getReportListForStudent(userName,request,response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("method")!=null){
			if(request.getParameter("method").equals("delete")){
				deleteReport(request.getParameter("reportId"));
			}
		}
	}
	
	private void getReportListForStudent(String studentCode,HttpServletRequest request, HttpServletResponse response) throws IOException{
		ResultSetMapper<Report> reportResult = new ResultSetMapper<Report>();
		List<Report> reportList = reportResult.mapRersultSetToObject(getReportListForStudentSql(studentCode), Report.class);
		reportList = addReportNameLang(reportList);
//		reportList = addLinkPdf(reportList);
		reportList = convertToBuddhistDate(reportList);
		String resultJson = "";
		if(reportList != null){
			resultJson = ConvertDataType.getInstance().objectToJasonArray(reportList);
		}
		sendResponse(request, response, resultJson);
	}
	
	private void getReportListForAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ResultSetMapper<Report> reportResult = new ResultSetMapper<Report>();
		List<Report> reportList = reportResult.mapRersultSetToObject(getReportListForAdminSql(), Report.class);
		reportList = addReportNameLang(reportList);
		reportList = addLinkPdf(reportList);
		reportList = convertToBuddhistDate(reportList);
		String resultJson = "";
		if(reportList != null){
			resultJson = ConvertDataType.getInstance().objectToJasonArray(reportList);
		}
		sendResponse(request, response, resultJson);
	}
	
	private void getReportPdf(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String sql = "SELECT * FROM REPORT WHERE REPORTID = "+request.getParameter("reportId");
//		
//		File theFile = new File("/Users/rattasit/workspace/IDR/WebContent/reportFile/"+request.getParameter("reportId")+"after.pdf");
//		InputStream input = null;
//		FileOutputStream output = null;
//		ResultSet rs = getDataMySql(sql);
//		
//		try {
//			output = new FileOutputStream(theFile);
//			if(rs.next()){
//				input = rs.getBinaryStream("REPORTFILE");
//				byte[] buffer = new byte[4096];
//				while(input.read(buffer) != -1){
//					output.write(buffer);
//				}
//				output.flush();
//				output.close();
//			}
//			String releativePath = getServletContext().getRealPath("/WebContent/reportFile/"+request.getParameter("reportId")+"after.pdf");
//			File downloadFile = new File("/Users/rattasit/workspace/IDR/WebContent/reportFile/"+request.getParameter("reportId")+"after.pdf");
//			FileInputStream inStream = new FileInputStream(downloadFile);
//			
//			ServletContext servletContext = getServletContext();
//			String mimeType = servletContext.getMimeType("/Users/rattasit/workspace/IDR/WebContent/reportFile/"+request.getParameter("reportId")+"after.pdf");
//			if(mimeType == null){
//				mimeType = "application/octet-stream";
//			}
//			System.out.println("MIME type: "+mimeType);
//			
//			response.setContentType(releativePath);
//			response.setContentLength((int) downloadFile.length());
//			
//			String headerKey = "Content-Disposition";
//			String headerValue = "attachment; filename="+request.getParameter("reportId")+"after.pdf";
//			response.setHeader(headerKey, headerValue);
//			
//			ServletOutputStream outputStram = response.getOutputStream();
//			int byteRead = -1;
//			byte[] buffer = new byte[4096];
//			
//			while((byteRead = inStream.read(buffer)) != -1){
//				outputStram.write(buffer,0,byteRead);
//			}
//			inStream.close();
//			outputStram.flush();
//			outputStram.close();
//		    
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		response.sendRedirect("./reportFile/"+request.getParameter("reportId")+"after.pdf");
		response.sendRedirect("./reportFile/"+request.getParameter("reportId")+".pdf");
	}
	
	private List<Report> addReportNameLang(List<Report> reportList){
		if(reportList != null){
			for (Report report : reportList) {
				if(("TH").equalsIgnoreCase(report.getLanguage())){
					report.setReportName(report.getReportName()+"(ไทย)");
				}else{
					report.setReportName(report.getReportName()+"(ENG)");
				}
			}
		}
		return reportList;
	}
	
	private List<Report> addLinkPdf(List<Report> reportList){
		if(reportList !=null){
			for (Report report : reportList) {
				report.setReportName("<a href=./ManagementReportController?reportId="+report.getReportId()+" target=_blank>"+report.getReportName()+"</a>");
			}
		}
		return reportList;
	}
	
	private List<Report> convertToBuddhistDate(List<Report> reportList){
		Date date;
		SimpleDateFormat sFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm",new Locale("th","th"));
		if(reportList != null){
			for (Report report : reportList) {
				date = new Date((report.getCreateDate()).getTime());
				report.setCreateDateString(sFormat.format(date));
			}
		}
		return reportList;
	}
	
	private ResultSet getReportListForStudentSql(String studentCode){
		String sql = "	SELECT RP.REPORTID AS REPORTID ,	"+
				"	STD.STUDENTCODE    AS STUDENTCODE,"+
				"	STD.STUDENTNAME    AS STUDENTNAME,"+
				"	RPT.REPORTNAMETHAI AS REPORTNAMETHAI,	"+
				"	RP.LANGUAGE AS LANGUAGE , "+
				"	RP.CREATEDATE AS CREATEDATE	"+
				"	FROM STUDENT AS STD ,REPORT AS RP , REPORTTYPE AS RPT	"+
				"	WHERE STD.STUDENTCODE = "+studentCode+
				"	AND STD.STUDENTCODE = RP.STUDENTCODE	"+
				"	AND RP.REPORTTYPEID = RPT.REPORTTYPEID";
		return getDataMySql(sql);
	}
	
	private ResultSet getReportListForAdminSql(){
		String sql = "	SELECT RP.REPORTID AS REPORTID ,	"+
				"	STD.STUDENTCODE    AS STUDENTCODE,	"+
				"	STD.STUDENTNAME    AS STUDENTNAME,	"+
				"	RPT.REPORTNAMETHAI AS REPORTNAMETHAI,	"+
				"	RP.LANGUAGE AS LANGUAGE , "+
				"	RP.CREATEDATE AS CREATEDATE	"+
				"	FROM STUDENT AS STD ,REPORT AS RP , REPORTTYPE AS RPT	"+
				"	WHERE STD.STUDENTCODE = RP.STUDENTCODE	"+
				"	AND RP.REPORTTYPEID = RPT.REPORTTYPEID";
		return getDataMySql(sql);
	}
	
	private int deleteReport(String reportId){
		String sql = "DELETE FROM IDR.REPORT "+ 
					" WHERE REPORTID IN ("+reportId+")";
		int status = 0;
		try {
			con = getConnectionMySQLDB();
			Statement stmt = con.createStatement();
			status = stmt.executeUpdate(sql);
			moveFileReport(reportId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	private void moveFileReport(String reportId){
		String reportIdArray [] = reportId.split(",");
		File afile;
		for (String id : reportIdArray) {
			afile = new File(StaticValue.REPORT_FILE_DIRECTORY+id+".pdf");
			afile.renameTo(new File(StaticValue.REPORT_TRASH_DIRECTORY+afile.getName()));
		}
	}
	
	private void sendResponse(HttpServletRequest request,HttpServletResponse response,String resultJson) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(resultJson);
		out.close();
		// Release JDBC pool connection after sent result to client
		releaseConnection();
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
	
	private Connection getConnectionMySQLDB(){
		con = ConnectionDB.getRBRUMySQL();
		return con;
	}
	
	private void releaseConnection(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
		}
	}

}
