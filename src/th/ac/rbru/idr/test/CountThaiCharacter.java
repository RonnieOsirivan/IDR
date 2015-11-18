package th.ac.rbru.idr.test;

public class CountThaiCharacter {

	public static void main(String[] args) {
		String olle = "รัฐ ";
		int count = 0;

		for(int i=0; i<olle.length(); i++)
		{
		    if(!isMark(olle.charAt(i)))
		        count++;
		}

		System.out.println(count);
	}
	
	static boolean isMark(char ch)
	{
	    int type = Character.getType(ch);
	    return type == Character.NON_SPACING_MARK ||
	           type == Character.ENCLOSING_MARK ||
	           type == Character.COMBINING_SPACING_MARK;
	}

}
