package th.ac.rbru.idr.test;

import java.text.DecimalFormat;

public class NumberFormat {

	public static void main(String[] args) {
		customFormat("000", 14);
	}
	
	public static void customFormat(String pattern, double value ) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		System.out.println(value + " " + pattern + " " + output);
	}


}
