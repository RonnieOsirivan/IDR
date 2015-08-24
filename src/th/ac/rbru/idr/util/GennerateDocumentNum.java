package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class GennerateDocumentNum {
	private Connection con;
	public synchronized HashMap<String,Integer> getDocumentNum(){
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		try {
			String sql = "	INSERT INTO DOCUMENTNUM (DOCUMENTRUNNINGNUM,ACADYEAR)	"+
					"	SELECT DOCUMENTRUNNINGNUM+1,ACADYEAR	"+
					"	FROM DOCUMENTNUM	"+
					"	ORDER BY DOCUMENTID DESC	"+
					"	LIMIT 1	";
			
			ConnectionDB.getInstance();
			con = ConnectionDB.getRBRUMySQL();
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
			releaseConnection();
			
			ResultSet rs = getDataMySql("SELECT * FROM DOCUMENTNUM ORDER BY DOCUMENTID DESC LIMIT 1");
			if(rs.next()){
				map.put("docId", rs.getInt(1));
				map.put("runningNum", rs.getInt(2));
				map.put("acadYear", rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
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
