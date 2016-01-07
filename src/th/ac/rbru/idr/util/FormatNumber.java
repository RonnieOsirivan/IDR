package th.ac.rbru.idr.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumber {
	
	private DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
	
	public String thaiNumber(String pattern,int value){
		df.applyPattern(pattern);
		return df.format(value);
	}
	
	public String thaiNumber(String pattern,long value){
		df.applyPattern(pattern);
		return df.format(value);
	}
	
	public String thaiNumber(String pattern,float value){
		df.applyPattern(pattern);
		return df.format(value);
	}
	
	public String thaiNumber(String pattern,double value){
		df.applyPattern(pattern);
		return df.format(value);
	}
	
	public String EngNumber(String pattern,int value){
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
	
	public String EngNumber(String pattern,long value){
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
	
	public String EngNumber(String pattern,float value){
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
	
	public String EngNumber(String pattern,double value){
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
}
