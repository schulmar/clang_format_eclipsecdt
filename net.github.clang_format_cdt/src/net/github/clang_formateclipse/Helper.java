package net.github.clang_formateclipse;

public class Helper {

	static public boolean isInteger(String str) {
		if(str.isEmpty())
			return false;
		try { 
	        Integer.parseInt(str); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
}
