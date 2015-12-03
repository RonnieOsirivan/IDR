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
	public static String REPORT_TYPE_STUDENT_STATUS_ENG;
	public static String REPORT_GRADE_EACH_SEMESTER;
	public static String REPORT_GRADE_EACH_SEMESTER_ENG;
	public static String REPORT_FILE_DIRECTORY;
	public static String REPORT_TRASH_DIRECTORY;
	public static String REPORT_LOG_FILE_DIRECTORY;
	public static String REPORT_LOG;
	public static String ABSULUTEPATH;
	public static String GARUDASYMBOL;
	public static String SIGNATURE;
	
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			input = getClass().getResourceAsStream("config.properties");
//			input = new FileInputStream("/Users/rattasit/workspace/IDR/fileconf/config.properties");
			prop.load(input);
			REPORT_TYPE_STUDENT_STATUS_THAI = prop.getProperty("report_type_student_status_thai");
			REPORT_TYPE_STUDENT_STATUS_ENG = prop.getProperty("report_type_student_status_Eng");
			REPORT_GRADE_EACH_SEMESTER = prop.getProperty("report_type_grade_each_semester");
			REPORT_GRADE_EACH_SEMESTER_ENG = prop.getProperty("report_type_grade_each_semester_eng");
			
			REPORT_FILE_DIRECTORY = prop.getProperty("report_file_directory");
			REPORT_TRASH_DIRECTORY = prop.getProperty("report_trash_directory");
			REPORT_LOG = prop.getProperty("report_log");
			REPORT_LOG_FILE_DIRECTORY = prop.getProperty("report_log_directory");
			GARUDASYMBOL = prop.getProperty("garuda_symbol_picture");
			SIGNATURE = prop.getProperty("signature_picture");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
			
	}
}
