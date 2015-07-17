package th.ac.rbru.idr.test;

public class NameFormat {

	public static void main(String[] args) {
		String text = "rattasit".toLowerCase();
		text = text.replaceFirst(text.substring(0,1),text.substring(0,1).toUpperCase());
		System.out.println(text);
	}
}
