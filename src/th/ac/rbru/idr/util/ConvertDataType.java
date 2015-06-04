package th.ac.rbru.idr.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

public class ConvertDataType {
	private static ConvertDataType instance = null;

	private ConvertDataType() {
	}

	public static ConvertDataType getInstance() {
		if (instance == null) {
			instance = new ConvertDataType();
		}
		return instance;
	}

	public String objectToJasonArray(Object obj) {
		Gson json = new Gson();
		return json.toJson(obj);
	}

	public String resultsetJsonArray(ResultSet rs) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		StringBuilder str = new StringBuilder("[");
		try {
			ResultSetMetaData rsm = rs.getMetaData();
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<String, Object>(
						rsm.getColumnCount());
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					str.append("{key: \"" + rsm.getColumnName(i) + "\","
							+ "value: " + rs.getObject(i).toString() + "}");
					if (i < rsm.getColumnCount()) {
						str.append(",");
					}
					row.put(rsm.getColumnName(i), rs.getObject(i));
				}
				str.append("]");
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
}
