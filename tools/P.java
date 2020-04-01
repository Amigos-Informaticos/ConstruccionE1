package tools;

/**
 * Clase P
 * 
 * @author Edson Manuel Carballo Vera
 * @version 2.1.1
 */
public abstract class P {
	/**
	 * Imprime un dato cualquiera sin salto de linea
	 * 
	 * <pre>
	 * Invoca el metodo toString del objeto indicado.
	 * No inserta un salto de linea.
	 * Para insertar salto de linea usar {@link #pln(Object)}
	 * </pre>
	 * 
	 * @see {@link #pln(Object)}
	 * @param o El dato que se desea imprimir
	 * @since 1.0.0
	 */
	public static void p(Object o) {
		System.out.print("" + o.toString());
	}

	/**
	 * Imprime un dato cualquiera con salto de linea
	 * 
	 * <pre>
	 * Invoca el metodo toString del objeto indicado.
	 * El salto de linea se inserta al final de la impresion.
	 * Si no se desea insertar salto de linea usar {@link #p(Object)}
	 * </pre>
	 * 
	 * @see {@link #p(Object)}
	 * @param o El dato que se desea imprimir
	 * @since 1.0.0
	 */
	public static void pln(Object o) {
		System.out.println("" + o.toString());
	}

	/**
	 * Imprime el contenido de un archivo a traves de un objeto tipo Arch
	 * 
	 * <pre>
	 * Se imprime el contenido de un archivo.
	 * Si el objeto Arch no tiene un Path definido, o si el archivo no existe se imprimira un error
	 * </pre>
	 * 
	 * @param a El objeto tipo Arch del cual se desea imprimir su contenido.
	 * @since 1.1.0
	 */
	public static void p(Arch a) {
		if (a.getPath() != null) {
			if (a.existe()) {
				String line;
				while ((line = a.leerLinea()) != null) {
					pln(line);
				}
			} else {
				P.err("El archivo no existe");
			}
		} else {
			P.err("El objeto Arch no tiene un Path definido");
		}
	}

	/**
	 * Imprime un arreglo de Int
	 * 
	 * <pre>
	 * Imprime un arreglo de Int en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de Int que se desea imprimir
	 * @see {@link #pArray(int[], boolean)}
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(int[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de Int
	 * 
	 * <pre>
	 * Imprime un arreglo de Int en una o varias lineas.
	 * El ultimo parametro de tipo booleano indica si se debe imprimir en una sola linea o varias.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o   El arreglo de Int que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(int[] o, boolean val) {
		if (val) {
			for (int i = 0; i < o.length; i++) {
				pln(o[i]);
			}
		} else {
			p("[");
			for (int i = 0; i < o.length; i++) {
				p(o[i]);
				if (i < o.length - 1) {
					p(", ");
				}
			}
			p("]");
		}
	}

	/**
	 * Imprime un arreglo de Float
	 * 
	 * <pre>
	 * Imprime un arreglo de Float en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales.
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de Float que se desea imprimir
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(float[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de Float
	 * 
	 * <pre>
	 * Imprime un arreglo de Float en una o varias lineas. El ultimo parametro de
	 * tipo booleano indica si se debe imprimir en una sola linea o varias. Solo se
	 * puede usar con arreglos unidimensionales. En el futuro se agregara soporte
	 * para arreglos multidimensionales. Si se desea imprimir arreglos
	 * multidimensionales, usar {@link Array}
	 * 
	 * </pre>
	 * 
	 * @param o   El arreglo de Float que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(float[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		pArray(a, val);
	}

	/**
	 * Imprime un arreglo de Double
	 * 
	 * <pre>
	 * Imprime un arreglo de Double en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales.
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de Double que se desea imprimir
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(double[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de Double
	 * 
	 * <pre>
	 * Imprime un arreglo de Double en una o varias lineas. El ultimo parametro de
	 * tipo booleano indica si se debe imprimir en una sola linea o varias. Solo se
	 * puede usar con arreglos unidimensionales. En el futuro se agregara soporte
	 * para arreglos multidimensionales. Si se desea imprimir arreglos
	 * multidimensionales, usar {@link Array}
	 * 
	 * </pre>
	 * 
	 * @param o   El arreglo de Double que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(double[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		pArray(a, val);
	}

	/**
	 * Imprime un arreglo de Boolean
	 * 
	 * <pre>
	 * Imprime un arreglo de Boolean en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales.
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de Boolean que se desea imprimir
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(boolean[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de Boolean
	 * 
	 * <pre>
	 * Imprime un arreglo de Boolean en una o varias lineas. El ultimo parametro de
	 * tipo booleano indica si se debe imprimir en una sola linea o varias. Solo se
	 * puede usar con arreglos unidimensionales. En el futuro se agregara soporte
	 * para arreglos multidimensionales. Si se desea imprimir arreglos
	 * multidimensionales, usar {@link Array}
	 * 
	 * </pre>
	 * 
	 * @param o   El arreglo de Boolean que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(boolean[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		pArray(a, val);
	}

	/**
	 * Imprime un arreglo de String
	 * 
	 * <pre>
	 * Imprime un arreglo de String en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales.
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de String que se desea imprimir
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(String[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de String
	 * 
	 * <pre>
	 * Imprime un arreglo de String en una o varias lineas. El ultimo parametro de
	 * tipo booleano indica si se debe imprimir en una sola linea o varias. Solo se
	 * puede usar con arreglos unidimensionales. En el futuro se agregara soporte
	 * para arreglos multidimensionales. Si se desea imprimir arreglos
	 * multidimensionales, usar {@link Array}
	 * 
	 * </pre>
	 * 
	 * @param o   El arreglo de String que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(String[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		pArray(a, val);
	}

	/**
	 * Imprime un arreglo de Object
	 * 
	 * <pre>
	 * Imprime un arreglo de Object en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * Solo se puede usar con arreglos unidimensionales.
	 * En el futuro se agregara soporte para arreglos multidimensionales.
	 * Si se desea imprimir arreglos multidimensionales, usar {@link Array}
	 * </pre>
	 * 
	 * @param o El arreglo de Object que se desea imprimir
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(Object[] o) {
		pArray(o, false);
	}

	/**
	 * Imprime un arreglo de Object
	 * 
	 * <pre>
	 * Imprime un arreglo de Object en una o varias lineas. El ultimo parametro de
	 * tipo booleano indica si se debe imprimir en una sola linea o varias. Solo se
	 * puede usar con arreglos unidimensionales. En el futuro se agregara soporte
	 * para arreglos multidimensionales. Si se desea imprimir arreglos
	 * multidimensionales, usar {@link Array}
	 * 
	 * </pre>
	 * 
	 * @param o   El arreglo de Object que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(Object[] o, boolean val) {
		if (val) {
			for (int i = 0; i < o.length; i++) {
				pln(o[i]);
			}
		} else {
			p("[");
			for (int i = 0; i < o.length; i++) {
				p(o[i]);
				if (i < o.length - 1) {
					p(", ");
				}
			}
			p("]");
		}
	}

	/**
	 * Imprime un Array
	 * 
	 * <pre>
	 * Imprime un Array en una sola linea.
	 * Si se agrega un booleano true al final de los parametros, cada elemento del arreglo se imprimira en una linea.
	 * </pre>
	 * 
	 * @param a El Array que se desea imprimir
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(Array a) {
		pArray(a, false);
	}

	/**
	 * Imprime un Array
	 * 
	 * <pre>
	 * Imprime un Array en una o varias lineas. El ultimo parametro de tipo booleano
	 * indica si se debe imprimir en una sola linea o varias.
	 * 
	 * </pre>
	 * 
	 * @param a   El Array que se desea imprimir
	 * @param val true => Multiples lineas. false => Una sola linea
	 * @see {@link Array}
	 * @see {@link #pArray(Array)}
	 * @since 1.1.0
	 */
	public static void pArray(Array a, boolean val) {
		a.print(val);
	}

	/**
	 * Imprime en formato de error un mensaje u objeto determinado
	 * 
	 * <pre>
	 * Imprime usando el formato predeterminado de error de la consola en cuestion.
	 * Simpre se inserta un salto de linea al final de la impresion.
	 * </pre>
	 * 
	 * @param err El mensaje u objeto que se desea imprimir con formato de error.
	 * @since 1.1.2
	 */
	public static void err(Object err) {
		System.err.println(err.toString());
	}

	/**
	 * Limpia la consola
	 * 
	 * <pre>
	 * No usar.
	 * No funciona.
	 * Buscar la forma de limpiar la consola de acuerdo a la consola en uso.
	 * </pre>
	 * 
	 * @deprecated
	 * @since 1.0.0
	 */
	public static void clear() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}