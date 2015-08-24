package th.ac.rbru.idr.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertTimstamp {

	public static void main(String[] args) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(timeStamp.getTime());
		SimpleDateFormat sFormat = new SimpleDateFormat("dd/MM/yyyy",new Locale("th","th"));
		String sDate = sFormat.format(date);
		System.out.println(sDate);
	}
}
