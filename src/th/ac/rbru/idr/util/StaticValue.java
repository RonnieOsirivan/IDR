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
	public static String REPORT_FILE_DIRECTORY;
	public static String REPORT_TRASH_DIRECTORY;
	public static String REPORT_LOG_FILE_DIRECTORY;
	public static String REPORT_LOG;
	public static String ABSULUTEPATH;
	
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			input = getClass().getResourceAsStream("config.properties");
//			input = new FileInputStream("/Users/rattasit/workspace/IDR/fileconf/config.properties");
			prop.load(input);
			REPORT_TYPE_STUDENT_STATUS_THAI = prop.getProperty("report_type_student_status_thai");
			REPORT_TYPE_STUDENT_STATUS_ENG = prop.getProperty("report_type_student_status_Eng");
			REPORT_FILE_DIRECTORY = prop.getProperty("report_file_directory");
			REPORT_TRASH_DIRECTORY = prop.getProperty("report_trash_directory");
			REPORT_LOG = prop.getProperty("report_log");
			REPORT_LOG_FILE_DIRECTORY = prop.getProperty("report_log_directory");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
			
	}
}
