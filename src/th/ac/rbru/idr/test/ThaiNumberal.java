package th.ac.rbru.idr.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class ThaiNumberal {

	public static void main(String[] args) {
		System.out.println(thaiNumeral(3.11));
	}
	
	private static String thaiNumeral(double number){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("th","TH","TH"));
		df.applyPattern("#.##");
		return df.format(number);
	}

}
