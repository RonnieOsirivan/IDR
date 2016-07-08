package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcadYear {
	private int acadYear;
	private Connection con = null;
	
	public int getAcadYear() {
		try {
			String sql = "	SELECT TO_CHAR(sysdate,'YYYY','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') - DFS.ACADYEARADJ AS ACADYEAR	"+
					"	FROM DEFAULTSEMESTER DFS	"+
					"	WHERE DFS.SYSAPPID = 25	"+
					"	AND DFS.SYSMONTH   = TO_CHAR(SYSDATE,'MM') ";
			ResultSet resultSet = getData(sql);
			resultSet.next();
			this.acadYear = Integer.parseInt(resultSet.getString("ACADYEAR"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acadYear;
	}
	
	private ResultSet getData(String sql) {
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
}
