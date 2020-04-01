package tools;

/**
 * Clase Util
 * 
 * @author Edson Manuel Carballo Vera
 * @version 1.0.0
 */
public abstract class Util {

	/**
	 * Retorna la cadena en orden inverso
	 * 
	 * @param cad String que se desea revertir
	 * @return String en orden inverso
	 * @since 1.0.0
	 */
	public static String reverse(String cad) {
		if (cad.length() > 0) {
			String rev = "";
			for (int i = cad.length() - 1; i >= 0; i--) {
				rev += cad.charAt(i);
			}
			return rev;
		}
		return "";
	}

	public static String implode(char glue, String[] cads) {
		String ret = "";
		for (int i = 0; i < cads.length; i++) {
			ret += cads[i];
			if (i < cads.length - 1) {
				ret += glue;
			}
		}
		return ret;
	}

	public static String[] explode(String div, String cad) {
		return cad.split(div);
	}
}