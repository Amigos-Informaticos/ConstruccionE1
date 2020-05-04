package tools;


public abstract class Util {
	
	public static String reverse(String string) {
		StringBuilder reversed = null;
		if (string.length() > 0) {
			reversed = new StringBuilder();
			for (int i = string.length() - 1; i >= 0; i--) {
				reversed.append(string.charAt(i));
			}
		}
		assert reversed != null;
		return reversed.toString();
	}
	
	public static String implode(char glue, String[] strings) {
		StringBuilder returnValue = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			returnValue.append(strings[i]);
			if (i < strings.length - 1) {
				returnValue.append(glue);
			}
		}
		return returnValue.toString();
	}
	
	public static String[] explode(String divisor, String string) {
		return string.split(divisor);
	}
}