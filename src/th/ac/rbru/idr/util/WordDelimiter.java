package th.ac.rbru.idr.util;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.BreakIterator;
import java.util.Locale;

public class WordDelimiter {
	
	public String wordDelimiter(String source){
		
		Locale thaiLocale = new Locale("th");
		BreakIterator boundary = BreakIterator.getWordInstance(thaiLocale);
		boundary.setText(source);
		
		int lineLength = 401;
		int lineNum =1;
		int counter = 0;
		StringBuffer strWithNewLine = new StringBuffer();
		
		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
			
//			System.out.println(source.substring(start,end));
//			System.out.println(widthPixel(source.substring(start,end)));
			
			if(counter == 0){
				if(!source.substring(start, end).equals(" ") || !source.substring(start, end).equals("  ")){
					strWithNewLine.append(source.substring(start, end));
					counter += widthPixel(source.substring(start, end));
				}
			}
			else if((counter+widthPixel(source.substring(start, end))) <= lineLength){
				strWithNewLine.append(source.substring(start, end));
				counter += widthPixel(source.substring(start, end));
			}else{
				lineLength = 449;
				strWithNewLine.append(" ");
				if(!source.substring(start, end).equals(" ") || !source.substring(start, end).equals("  ")){
					strWithNewLine.append(source.substring(start, end)); 
					counter = widthPixel(source.substring(start, end));
				}else{
					counter = 0;
					lineNum++;
				}
			}
//			System.out.println("count = "+counter);
		}
		
//		System.out.println(strWithNewLine.toString());
		return strWithNewLine.toString();
	}
	
	public boolean isMark(char ch)
	{
	    int type = Character.getType(ch);
	    return type == Character.NON_SPACING_MARK ||
	           type == Character.ENCLOSING_MARK ||
	           type == Character.COMBINING_SPACING_MARK;
	}
	
	private int counterText(String text){
		int count = 0;
		for(int i=0;i < text.length(); i++){
			if(!isMark(text.charAt(i))){
				count++;
			}
		}
		return count;
	}
	
	public int widthPixel(String text){
		Font font = new Font("TH SarabunPSK", Font.PLAIN, 16);
		Canvas c = new Canvas();
		FontMetrics fontMetrics = c.getFontMetrics(font);
		return fontMetrics.stringWidth(text);
	}
}
