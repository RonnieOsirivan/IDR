package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;


public class ConnectionDB {
	private static ConnectionDB instance = null;

	private static Context initContext = null;
	private static Context envDbContext = null;
	/*private static DataSource misDs = null;
	private static DataSource regDs = null;
	private static DataSource researchDs = null;*/
	private static HashMap<String,DataSource> registeredDs = null;


		private ConnectionDB() {
			
			try {
				initContext = new InitialContext();
				envDbContext = (Context) initContext.lookup("java:/comp/env");
				registeredDs = new HashMap<>();
			} catch (NamingException e) {
				System.err.println("Cannot create initial context: "+e.getMessage());
			}
			
			System.out.println("ConnectionDB successfully startup.");
		}
		
		public static ConnectionDB getInstance(){
			if(instance == null){
				instance = new ConnectionDB();
			}
			return instance;
		}
		
	    public Connection getRegDB(){
	    	Connection connection = null;
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@10.5.1.203:1521:rbrureg", "AVSREG","vnjoinrbru");
				return connection;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	    }
	    
	    public Connection getMisDB(){
	    	Connection connection = null;
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@10.5.1.202:1521:RBRU", "promis","jaychou");
				return connection;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	    }
	    
	    public static Connection getRBRUMySQL(){
	    	try {
	    		if(! registeredDs.containsKey("jdbc/research_determine")){
//	    			registeredDs.putIfAbsent("jdbc/research_determine", (DataSource) envDbContext.lookup("jdbc/research_determine"));
	    			registeredDs.put("jdbc/research_determine", (DataSource) envDbContext.lookup("jdbc/research_determine"));
	    		}
	    		/*if(researchDs == null){
	    			researchDs = (DataSource) envDbContext.lookup("jdbc/research_determine");
	    		}*/
	    		return registeredDs.get("jdbc/research_determine").getConnection();
			} catch (Exception e) {
				System.err.println("Cannot get connection: " + e.getMessage());
				return null;
			}
	    }
	    
	    public static Connection getMisConnection(){
	    	try {
	    		/*if(misDs == null){
	    			misDs = (DataSource) envDbContext.lookup("jdbc/misdb");
	    		}*/
	    		if(! registeredDs.containsKey("jdbc/misdb")){
//	    			registeredDs.putIfAbsent("jdbc/misdb", (DataSource) envDbContext.lookup("jdbc/misdb"));
	    			registeredDs.put("jdbc/misdb", (DataSource) envDbContext.lookup("jdbc/misdb"));
	    		}
				return registeredDs.get("jdbc/misdb").getConnection();
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.err.println("Cannot get connection: " + e.getMessage());
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Cannot get connection: " + e.getMessage());
				return null;
			}
	    }
	   
	    public static Connection getRegConnection(){
	    	try {
				/*regDs = (regDs == null) ? (DataSource) envDbContext.lookup("jdbc/regdb") : regDs;*/
	    		if(! registeredDs.containsKey("jdbc/regdb")){
//	    			registeredDs.putIfAbsent("jdbc/regdb", (DataSource) envDbContext.lookup("jdbc/regdb"));
	    			registeredDs.put("jdbc/regdb", (DataSource) envDbContext.lookup("jdbc/regdb"));
	    		}
				return registeredDs.get("jdbc/regdb").getConnection();
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.err.println("Cannot get connection: " + e.getMessage());
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Cannot get connection: " + e.getMessage());
				return null;
			}
	    }
	    
	    public static void destroy(){
	    	try {
	    		/*if(misDs != null)
	    			initContext.destroySubcontext("jdbc/misdb");
	    		if(regDs != null)
	    			initContext.destroySubcontext("jdbc/regdb");
	    		if(researchDs != null)
	    			initContext.destroySubcontext("jdbc/research_determine");*/
	    		for(String ds : registeredDs.keySet()){
	    			initContext.destroySubcontext(ds);
	    		}
	    		
	    	} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("ConnectionDB successfully shutdown.");
	    }
}
