package th.ac.rbru.idr.test;

public class TestCheckParentheses {
	public static void main(String[] args) {
		String t = "testdata(ddd) ";
		System.out.println(t.trim());
		System.out.println(t.trim().length());
		System.out.println(t.trim().substring(t.trim().length()-1, t.trim().length()));
		
		if(t.trim().substring(t.trim().length()-1, t.trim().length()).equals(")")){
			System.out.println("true");
		}
		
		String r = t.substring(0, t.indexOf("(")) + " "+t.substring(t.indexOf("("),t.length());
		
		System.out.println(r);
		
	}
}
