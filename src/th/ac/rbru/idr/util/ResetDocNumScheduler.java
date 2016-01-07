package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
@WebListener
public class ResetDocNumScheduler implements ServletContextListener{
	
	private Connection con;
	
	@Scheduled(cron="0 0 0 1 * ?")
	public void resetDocNum(){
		excecuteResetDocNum();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		excecuteResetDocNum();
//		ApplicationContext p = (ApplicationContext)arg0.getServletContext();
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+p.getContextPath());
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
	private void excecuteResetDocNum(){
		try {
			int oldAcadYear = 0;
			int newAcadYear = 0;
			ResultSet rs = getDataMySql("SELECT ACADYEAR FROM DOCUMENTNUM ORDER BY DOCUMENTID DESC LIMIT 1");
			if(rs.next()){
				oldAcadYear = rs.getInt(1);
			}
			
			// By Acad year
//			rs = getData(" SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') + DFS.ACADYEARADJ AS ACADYEAR "+
//					  " FROM DEFAULTSEMESTER DFS "+
//					  " WHERE DFS.SYSAPPID = 25 "+
//					  " AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')");
			
			// By System year
			rs = getData(" SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') "
					+" FROM DUAL");
			
			if(rs.next()){
				newAcadYear = rs.getBigDecimal(1).intValue();
			}
			
			if(oldAcadYear != newAcadYear){
				String sql = "	INSERT INTO DOCUMENTNUM(REPORTTYPEID,DOCUMENTRUNNINGNUM,ACADYEAR,DOCUMENTNUMBER)	"+
						"	SELECT REPORTTYPEID,0,ACADYEAR+1,'0'	"+
						"	FROM DOCUMENTNUM	"+
						"	GROUP BY REPORTTYPEID	";
				ConnectionDB.getInstance();
				con = ConnectionDB.getRBRUMySQL();
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
			}
			releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet getData(String sql) throws SQLException {
		ResultSet result = null;
		
		try {
			ConnectionDB.getInstance();
			con = ConnectionDB.getRegConnection();
			Statement statement = con.createStatement();
			result = statement.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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
	
	private void releaseConnection(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}
	}
}
