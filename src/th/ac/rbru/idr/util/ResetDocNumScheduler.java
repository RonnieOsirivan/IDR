package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
			rs = getData(" SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') - DFS.ACADYEARADJ AS ACADYEAR "+
					  " FROM DEFAULTSEMESTER DFS "+
					  " WHERE DFS.SYSAPPID = 25 "+
					  " AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM')");
			if(rs.next()){
				newAcadYear = rs.getBigDecimal(1).intValue();
			}
			
			if(oldAcadYear != newAcadYear){
				ConnectionDB.getInstance();
				con = ConnectionDB.getRBRUMySQL();
				PreparedStatement psmt = con.prepareStatement("INSERT INTO DOCUMENTNUM (DOCUMENTRUNNINGNUM,ACADYEAR) VALUES(?,?)"); 
				psmt.setInt(1, 0);
				psmt.setInt(2, newAcadYear);
				psmt.executeUpdate();
				psmt.close();
			}
			releaseConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
