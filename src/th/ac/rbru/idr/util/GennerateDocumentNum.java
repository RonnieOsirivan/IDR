package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class GennerateDocumentNum {
	private Connection con;
	public synchronized HashMap<String,String> getDocumentNum(String docTypeNumber){
		HashMap<String, String> map = new HashMap<String,String>();
		try {
			String selectSQL = " SELECT REPORTTYPEID,DOCUMENTRUNNINGNUM,ACADYEAR	"+
					"	FROM DOCUMENTNUM	"+
					"	WHERE REPORTTYPEID="+docTypeNumber+
					"	ORDER BY DOCUMENTID DESC	"+
					"	LIMIT 1;	";
			
			int docRunNum = 0;
			int acadYear = 0;
			String docNum = "";
			ResultSet resultSet = getDataMySql(selectSQL);
			if(resultSet.next()){
				docRunNum = resultSet.getInt(2)+1;
				acadYear = resultSet.getInt(3);
				docNum = documentNumberFormmat(docTypeNumber, docRunNum, acadYear);
			}
			
			String sql = "	INSERT INTO DOCUMENTNUM(REPORTTYPEID,DOCUMENTRUNNINGNUM,ACADYEAR,DOCUMENTNUMBER) "+
					" VALUES (?,?,?,?) ";
			ConnectionDB.getInstance();
			con = ConnectionDB.getRBRUMySQL();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(docTypeNumber));
			psmt.setInt(2, docRunNum);
			psmt.setInt(3, acadYear);
			psmt.setString(4, docNum);
			
			psmt.executeUpdate();
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
	
	private String documentNumberFormmat(String docType,int docRunNum,int acadYear){
		String docRunNumString = "";
		if(String.valueOf(docRunNum).length() == 1){
			docRunNumString = "00"+String.valueOf(docRunNum);
		}else if(String.valueOf(docRunNum).length() == 2){
			docRunNumString = "0"+String.valueOf(docRunNum);
		}else{
			docRunNumString = String.valueOf(docRunNum);
		}
		return docType+"."+docRunNumString+" / "+acadYear;
	}
}
