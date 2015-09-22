package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class GennerateDocumentNum {
	private Connection con;
	public synchronized HashMap<String,String> getDocumentNum(String docTypeNumber){
		HashMap<String, String> map = new HashMap<String,String>();
		try {
			String sql = "	INSERT INTO DOCUMENTNUM(REPORTTYPEID,DOCUMENTRUNNINGNUM,ACADYEAR,DOCUMENTNUMBER)	"+
					"	SELECT "+docTypeNumber+",DOCUMENTRUNNINGNUM+1,ACADYEAR,concat("+docTypeNumber+",'.',DOCUMENTRUNNINGNUM+1,' / ',ACADYEAR)	"+
					"	FROM DOCUMENTNUM	"+
					"	WHERE REPORTTYPEID="+docTypeNumber+
					"	ORDER BY DOCUMENTID DESC	"+
					"	LIMIT 1";
			ConnectionDB.getInstance();
			con = ConnectionDB.getRBRUMySQL();
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
			releaseConnection();
			
			ResultSet rs = getDataMySql("SELECT * FROM DOCUMENTNUM WHERE REPORTTYPEID ="+docTypeNumber+" ORDER BY DOCUMENTID DESC LIMIT 1");
			if(rs.next()){
				map.put("docId", String.valueOf(rs.getInt(1)));
				map.put("reportTypeId",String.valueOf(rs.getInt(2)));
				map.put("docRuningNum", String.valueOf(rs.getInt(3)));
				map.put("acadYear", String.valueOf(rs.getInt(4)));
				map.put("docNum", rs.getString(5));
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
