package th.ac.rbru.idr.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StaticValue implements ServletContextListener{
	
	private static Properties prop = new Properties();
	private static InputStream input = null;
	public static String REPORT_TYPE_STUDENT_STATUS_THAI;
	public static String REPORT_TYPE_STUDENT_STATUS_THAI_DOCX;
	public static String REPORT_TYPE_STUDENT_STATUS_ENG;
	public static String REPORT_TYPE_STUDENT_STATUS_ENG_DOCX;
	public static String REPORT_GRADE_EACH_SEMESTER;
	public static String REPORT_GRADE_EACH_SEMESTER_DOCX;
	public static String REPORT_GRADE_EACH_SEMESTER_ENG;
	public static String REPORT_GRADE_EACH_SEMESTER_ENG_DOCX;
	public static String REPORT_COMPLETE_TECH_5_YEAR_THAI;
	public static String REPORT_COMPLETE_TECH_5_YEAR_THAI_DOCX;
	public static String REPORT_LAST_SEMESTER_TECH_5_YEAR_THAI;
	public static String REPORT_LAST_SEMESTER_TECH_5_YEAR_THAI_DOCX;
	public static String REPORT_COMPLETE_TECH_4_YEAR_THAI;
	public static String REPORT_COMPLETE_TECH_4_YEAR_THAI_DOCX;
	
	public static String PAY_IN_SLIP;
	public static String REPORT_FILE_DIRECTORY;
	public static String PAY_IN_SLIP_FILE_DIRECTORY;
	public static String REPORT_TRASH_DIRECTORY;
	public static String REPORT_LOG_FILE_DIRECTORY;
	public static String REPORT_LOG;
	public static String ABSULUTEPATH;
	public static String GARUDASYMBOL;
	public static String SIGNATURE;
	public static int FIRST_LINE_REPORT;
	public static int OTHER_LINE_REPORT;
	
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			input = getClass().getResourceAsStream("config.properties");
//			input = new FileInputStream("/Users/rattasit/workspace/IDR/fileconf/config.properties");
			prop.load(input);
			REPORT_TYPE_STUDENT_STATUS_THAI = prop.getProperty("report_type_student_status_thai");
			REPORT_TYPE_STUDENT_STATUS_THAI_DOCX = prop.getProperty("report_type_student_status_thai_docx");
			REPORT_TYPE_STUDENT_STATUS_ENG = prop.getProperty("report_type_student_status_eng");
			REPORT_TYPE_STUDENT_STATUS_ENG_DOCX = prop.getProperty("report_type_student_status_eng_docx");
			REPORT_GRADE_EACH_SEMESTER = prop.getProperty("report_type_grade_each_semester");
			REPORT_GRADE_EACH_SEMESTER_DOCX = prop.getProperty("report_type_grade_each_semester_docx");
			REPORT_GRADE_EACH_SEMESTER_ENG = prop.getProperty("report_type_grade_each_semester_eng");
			REPORT_GRADE_EACH_SEMESTER_ENG_DOCX = prop.getProperty("report_type_grade_each_semester_eng_docx");
			REPORT_COMPLETE_TECH_5_YEAR_THAI = prop.getProperty("report_complete_tech_5_year_thai");
			REPORT_COMPLETE_TECH_5_YEAR_THAI_DOCX = prop.getProperty("report_complete_tech_5_year_thai_docx");
			REPORT_LAST_SEMESTER_TECH_5_YEAR_THAI = prop.getProperty("report_last_semester_tech_5_year_thai");
			REPORT_LAST_SEMESTER_TECH_5_YEAR_THAI_DOCX = prop.getProperty("report_last_semester_tech_5_year_thai_docx");
			REPORT_COMPLETE_TECH_4_YEAR_THAI = prop.getProperty("report_complete_tech_4_year_thai");
			REPORT_COMPLETE_TECH_4_YEAR_THAI_DOCX = prop.getProperty("report_complete_tech_4_year_thai_docx");
			PAY_IN_SLIP = prop.getProperty("pay_in_slip");
			
			REPORT_FILE_DIRECTORY = prop.getProperty("report_file_directory");
			PAY_IN_SLIP_FILE_DIRECTORY = prop.getProperty("pay_in_slip_directory");
			REPORT_TRASH_DIRECTORY = prop.getProperty("report_trash_directory");
			REPORT_LOG = prop.getProperty("report_log");
			REPORT_LOG_FILE_DIRECTORY = prop.getProperty("report_log_directory");
			GARUDASYMBOL = prop.getProperty("garuda_symbol_picture");
			SIGNATURE = prop.getProperty("signature_picture");
			
			//401 for localhost ,650 for server ubuntu
			FIRST_LINE_REPORT = Integer.parseInt(prop.getProperty("first_line"));
			//449 for localhost ,740 for server ubuntu
			OTHER_LINE_REPORT = Integer.parseInt(prop.getProperty("other_line"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
			
	}
}
