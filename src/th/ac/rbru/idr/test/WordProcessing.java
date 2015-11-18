package th.ac.rbru.idr.test;

import java.text.BreakIterator;
import java.util.Locale;

import org.apache.lucene.util.Counter;

public class WordProcessing {
	public static void main(String[] args) {
		Locale thaiLocale = new Locale("th");
		 
		String input = "          ขอรับรองว่า นางสาวกนกวรรณ สร้อยศรี รหัสประจำตัวนักศึกษา ๕๘๑๔๔๔๔๐๐๑ เป็นนักศึกษา ภาคปกติ ระดับปริญญาตรี หลักสูตรรัฐประศาสนศาสตรบัณฑิต (รป.บ. ๔ ปี) สาขาวิชารัฐประศาสนศาสตร์ กำลังศึกษาอยู่ปี ๑ ที่มหาวิทยาลัยราชภัฏรำไพพรรณี จริง";
		 
		BreakIterator boundary = BreakIterator.getWordInstance(thaiLocale);
		 
		//BreakIterator boundary = DictionaryBasedBreakIterator.getWordInstance(thaiLocale);
		 
		boundary.setText(input);
		
		 
		printEachForward(boundary, input);
		 
	}
	
	public static void printEachForward(BreakIterator boundary, String source) {
		int lineLength = 74;
		int counter = 0;
		StringBuffer strout = new StringBuffer();
		StringBuffer strWithNewLine = new StringBuffer();
		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
			
			System.out.println(source.substring(start,end));
			System.out.println(counterText(source.substring(start,end)));
			
			if((counter+counterText(source.substring(start, end))) <= lineLength){
				strWithNewLine.append(source.substring(start, end));
				counter += counterText(source.substring(start, end));
			}else{
				strWithNewLine.append("\n");
				if(!source.substring(start, end).equals(" ")){
					strWithNewLine.append(source.substring(start, end)); 
				}
				counter = 0;
			}
			
			strout.append(source.substring(start, end));
			
		}
		System.out.println(strWithNewLine.toString());
	}
	
	public static boolean isMark(char ch)
	{
	    int type = Character.getType(ch);
	    return type == Character.NON_SPACING_MARK ||
	           type == Character.ENCLOSING_MARK ||
	           type == Character.COMBINING_SPACING_MARK;
	}
	
	private static int counterText(String text){
		int count = 0;
		for(int i=0;i < text.length(); i++){
			if(!isMark(text.charAt(i))){
				count++;
			}
		}
		return count;
	}
}
