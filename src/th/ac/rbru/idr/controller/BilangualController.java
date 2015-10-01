package th.ac.rbru.idr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.rbru.idr.util.ConvertDataType;

@WebServlet("/BilangualController")
public class BilangualController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public BilangualController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getLanguage(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void getLanguage(HttpServletRequest request, HttpServletResponse response){
		InputStream input = getClass().getResourceAsStream(request.getParameter("lang")+".properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
			Map<String, String> map = new HashMap<String, String>();
			for(String key : prop.stringPropertyNames()){
				map.put(key, prop.getProperty(key));
			}
			String resultJson = ConvertDataType.getInstance().objectToJasonArray(map);
			sendResponse(request, response, resultJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendResponse(HttpServletRequest request,HttpServletResponse response,String resultJson) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(resultJson);
		out.close();
	}
}
