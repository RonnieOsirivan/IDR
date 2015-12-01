package th.ac.rbru.idr.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.task.ReportGradeEachSemester;
import th.ac.rbru.idr.task.ReportLastSemester;
import th.ac.rbru.idr.task.ReportStudentStatus;
import th.ac.rbru.idr.task.ReportSummaryGPA;


/**
 * Servlet implementation class GenerateReportController
 */
@WebServlet("/GenerateReportController")
public class GenerateReportController extends HttpServlet {

	private static final long serialVersionUID = 1L;
//	private String abPath = getServletContext().getRealPath("/");

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateReportController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		if("studentStatus".equals(request.getParameter("reportTypeParam"))){
			ReportStudentStatus stdStatus = new ReportStudentStatus();
			stdStatus.generateReport(request, response);
		}else if("summaryGPA".equals(request.getParameter("reportTypeParam"))){
			ReportSummaryGPA sumGPA = new ReportSummaryGPA();
			sumGPA.generateReport(request, response);
		}else if("gradeEachSemester".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportGradeEachSemester gradeEachSem = new ReportGradeEachSemester();
			gradeEachSem.generateReport(request, response);
		}else if("lastSemester".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportLastSemester lastSemester = new ReportLastSemester();
			lastSemester.generateReport(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}