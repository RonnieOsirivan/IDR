package th.ac.rbru.idr.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.task.ReportCompleteGraduate;
import th.ac.rbru.idr.task.ReportCompleteOtherTech;
import th.ac.rbru.idr.task.ReportCompleteTech4Year;
import th.ac.rbru.idr.task.ReportCompleteTech5Year;
import th.ac.rbru.idr.task.ReportGradeEachSemester;
import th.ac.rbru.idr.task.ReportLastSemester;
import th.ac.rbru.idr.task.ReportLastSemesterTech5Year;
import th.ac.rbru.idr.task.ReportStudentStatus;
import th.ac.rbru.idr.task.ReportSummaryGPA;


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
		}else if("completeGraduate".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportCompleteGraduate completeGraduate = new ReportCompleteGraduate();
			completeGraduate.generateReport(request, response);
		}else if("completeTech5Year".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportCompleteTech5Year completeTech5Year = new ReportCompleteTech5Year();
			completeTech5Year.generateReport(request, response);
		}else if("lastSemesterTech5Year".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportLastSemesterTech5Year lastSemesterTech5Year = new ReportLastSemesterTech5Year();
			lastSemesterTech5Year.generateReport(request, response);
		}else if("completeTech4Year".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportCompleteTech4Year completeTech4Year = new ReportCompleteTech4Year();
			completeTech4Year.generateReport(request, response);
		}else if("completeOtherTech".equalsIgnoreCase(request.getParameter("reportTypeParam"))){
			ReportCompleteOtherTech completeOtherTech = new ReportCompleteOtherTech();
			completeOtherTech.generateReport(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}