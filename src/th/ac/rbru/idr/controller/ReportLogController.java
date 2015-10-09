package th.ac.rbru.idr.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.util.GenerateReport;

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
		System.out.println(request.getParameter("sinceDate"));
		System.out.println(request.getParameter("untilDate"));
		System.out.println(request.getParameter("reportType"));
		
		GenerateReport genReport = new GenerateReport();
		genReport.generarteReport("studentStatusThai", 10,param,getAbsulutePath());
		
		try {
			File f = new File("/Users/rattasit/workspace/IDR/WebContent/reportLogFile/10.pdf");
			System.out.println(getAbsulutePath());
			if(f.exists() && !f.isDirectory()){
				System.out.println("tttttttttttt");
			}
			response.sendRedirect("./reportLogFile/10.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void sendResponse(HttpServletRequest request,HttpServletResponse response,String resultJson) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(resultJson);
		out.close();
	}
	
	 private String getAbsulutePath(){
	    	String abPath = getServletContext().getRealPath("/");
	    	return abPath;
	 }

}
