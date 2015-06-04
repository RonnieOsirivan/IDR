package th.ac.rbru.idr.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

public class ResultSetMapper<T> {
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public List<T> mapRersultSetToObject(ResultSet rs, Class outputClass) {
		List<T> outputList = null;
		try {
			// make sure resultset is not null
			if (rs != null) {
				// check if outputClass has 'Entity' annotation
				if (outputClass.isAnnotationPresent(Entity.class)) {
					// get the resultset metadata
					ResultSetMetaData rsmd = rs.getMetaData();
					// get all the attributes of outputClass
					Field[] fields = outputClass.getDeclaredFields();
					while (rs.next()) {
						T bean = (T) outputClass.newInstance();
						for (int iterator = 0; iterator < rsmd.getColumnCount(); iterator++) {
							// getting the SQL column name
							String columnName = rsmd.getColumnName(iterator + 1);
							// reading the value of the SQL column
							Object columnValue = rs.getObject(iterator + 1);
							// iterating over outputClass attributes to check if
							// any attribute has 'Column' annotation with
							// matching 'name' value
							for (Field field : fields) {
								if (field.isAnnotationPresent(Column.class)) {
									Column column = field.getAnnotation(Column.class);
									if (column.name().equalsIgnoreCase(columnName)&& columnValue != null) {
//										BeanUtils.setProperty(bean,field.getName(), columnValue);
										setPropertyByFieldType(bean, field.getName(), columnValue);
										break;
									}
								}
							}
						}
						if (outputList == null) {
							outputList = new ArrayList<T>();
						}
						outputList.add(bean);
					}

				} else {
					// throw some error
				}
			} else {
				return null;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} 
		return outputList;
	}
	
	@SuppressWarnings("unused")
	private void setProperty(Object outputBean, String fieldName, Object columnValue) {
	    try {
	      Field field = outputBean.getClass().getDeclaredField(fieldName);
	      //System.err.println("columnValue Class: " +columnValue.getClass().getName());
	      
	      if(columnValue instanceof BigDecimal){
	    	  
	    	  //tmp = ((BigDecimal) columnValue).intValue();
	    	  //System.err.println("columnValue scale: "+((BigDecimal) columnValue).toString()+" ("+((BigDecimal) columnValue).scale()+")");
	    	  if(((BigDecimal) columnValue).scale() > 0){
	    		  // Value have decimal point, just retain its original type
	    		  field.setAccessible(true);
	    		  field.set(outputBean, columnValue); 
	    	  }else{
	    		  int tmp;
	    		  tmp = ((BigDecimal) columnValue).intValue();
	    		  field.setAccessible(true);
	    		  field.set(outputBean, tmp);
	    	  }
	      }else{
	    	  field.setAccessible(true);
		      field.set(outputBean, columnValue);
	      }
	    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	      e.printStackTrace();
	    }
	  }
	
	private void setPropertyByFieldType(Object outputBean, String fieldName, Object columnValue) {
		try {
			Field field = outputBean.getClass().getDeclaredField(fieldName);
			//System.err.println("columnValue Class: " +columnValue.getClass().getName());

			if(field.getType().isPrimitive()) {
//				System.err.println("field primitive: "+field.getType().getName() + 
//						", rs Type: " + columnValue.getClass().getName());
				if(columnValue instanceof BigDecimal){
					BigDecimal bigTmp = (BigDecimal) columnValue;
					if (field.getType().getTypeParameters().equals("int")){
						field.setAccessible(true);
						field.set(outputBean, bigTmp.intValue());
					}else if(field.getType().getTypeParameters().equals("double")){
						field.setAccessible(true);
						field.set(outputBean, bigTmp.doubleValue());
						
					}
				}
				// Task: there are Java Object that wait for primitive type conversion to long, char, byte, float, short 
			}else{
				field.setAccessible(true);
				field.set(outputBean,  columnValue);
			}

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("Error: "+e.getMessage());
			for(StackTraceElement trace : e.getStackTrace()){
				if(trace.getClassName().equals(this.getClass().getName()) ){
					System.err.println("	Trace: " + trace.toString());
				}
			}
		}
	}
}
