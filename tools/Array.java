package tools;

import java.io.Serializable;

/**
 * Clase Array
 *
 * @author Edson Manuel Carballo Vera
 * @version 1.2.1
 */
public class Array implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Arreglo de objetos
	 */
	private Object[] array = new Object[0];
	private static Array[] instances = new Array[0];
	private static Object[] sum = new Object[0];

	/**
	 * Constructor vacio
	 *
	 * @since 1.0.0
	 */
	public Array() {
		addInst(this);
	}

	/**
	 * Constructor con inicializador
	 *
	 * @param o Objeto para anadir a {@linkplain #array}
	 * @since 1.0.1
	 */
	public Array(Object o) {
		this.array = new Object[1];
		this.array[0] = o;
		addInst(this);
	}

	/**
	 * Constructor con inicializador
	 *
	 * @param o Var para anadir a {@linkplain #array}
	 * @see {@link Var}
	 * @since 1.0.2
	 */
	public Array(Var o) {
		this.array = new Object[1];
		this.array[0] = o.get();
		addInst(this);
	}

	/**
	 * Constructor con inicializador para varios objetos
	 *
	 * @param o Arreglo de objetos para anadir a {@linkplain #array}
	 * @since 1.0.5
	 */
	public Array(Object... o) {
		this.array = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			this.array[i] = o[i];
		}
		addInst(this);
	}

	/**
	 * Constructor con inicializador para Array
	 *
	 * @param o Array para anadir a {@linkplain #array}
	 * @since 1.0.6
	 */
	public Array(Array o) {
		this.array = new Object[o.len()];
		for (int i = 0; i < o.len(); i++) {
			this.array[i] = o.get(i);
		}
		addInst(this);
	}

	/**
	 * Anade la instancia actual a la lista
	 *
	 * @param a Array para anadir a {@linkplain #instances}
	 * @since 1.2.0
	 */
	public static void addInst(Array a) {
		Array[] aux = Array.instances;
		Array.instances = new Array[aux.length + 1];
		for (int i = 0; i < aux.length; i++) {
			Array.instances[i] = aux[i];
		}
		Array.instances[aux.length] = a;
	}

	/**
	 * Anade el objeto o a {@linkplain #sum}
	 *
	 * @param o El objeto a ser anadadido a {@linkplain #sum}
	 * @since 1.2.0
	 */
	public static void sum(Object o) {
		Object[] aux = Array.sum;
		Array.sum = new Object[aux.length + 1];
		for (int i = 0; i < aux.length; i++) {
			Array.sum[i] = aux[i];
		}
		Array.sum[aux.length] = o;
	}

	/**
	 * Vacia los valores de {@linkplain #sum} a todas las instancias de
	 * {@linkplain #instances}
	 *
	 * @since 1.2.0
	 */
	public static void dump() {
		Array.dump(false);
	}

	/**
	 * Vacia los valores de {@linkplain #sum} a todas las instancias de
	 * {@linkplain #instances}
	 *
	 * @param val Booleano. <br/>
	 *            true para vaciar {@linkplain #sum}<br/>
	 *            Si se omite es false
	 * @since 1.2.0
	 */
	public static void dump(boolean val) {
		for (int i = 0; i < Array.instances.length; i++) {
			for (int s = 0; s < Array.sum.length; s++) {
				Array.instances[i].add(Array.sum[s]);
			}
		}
		if (val) {
			Array.sum = new Object[0];
		}
	}

	/**
	 * Vacia {@linkplain #sum}
	 *
	 * @since 1.2.0
	 */
	public static void clear() {
		Array.sum = new Object[0];
	}

	/**
	 * Vacia los valores de {@linkplain #sum} a las instancias seleccionadas de
	 * {@linkplain #instances}
	 *
	 * @param val true para elegir, false para omitir
	 * @param a   Las instancias para elegir u omitir
	 * @since 1.2.0
	 */
	public static void dump(boolean val, Array... a) {
		if (val) {
			for (int i = 0; i < a.length; i++) {
				for (int s = 0; s < Array.sum.length; s++) {
					a[i].add(Array.sum[s]);
				}
			}
		} else {
			for (int i = 0; i < Array.instances.length; i++) {
				boolean flag = true;
				for (int s = 0; s < a.length; s++) {
					if (Array.instances[i] == a[s]) {
						flag = false;
					}
				}
				if (flag) {
					for (int s = 0; s < Array.sum.length; s++) {
						Array.instances[i].add(Array.sum[s]);
					}
				}
			}
		}
	}

	/**
	 * Anade uno o varios objetos a {@link #array}
	 *
	 * @param o El objeto a ser anadido
	 * @since 1.0.0
	 */
	public void add(Object... o) {
		Object[] aux = this.array;
		this.array = new Object[aux.length + o.length];
		int x = 0;
		while (x < aux.length) {
			this.array[x] = aux[x];
			x++;
		}
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof Var) {
				this.array[x] = ((Var) o[i]).get();
			}
			if (o[i] instanceof Array) {
				for (int s = 0; s < ((Array) o[i]).len(); s++) {
					this.array[x] = ((Array) o[i]).get(s);
				}
			} else {
				this.array[x] = o[i];
				x++;
			}
		}
	}

	/**
	 * Anade un arreglo de enteros a {@linkplain #array}
	 *
	 * @param o El arreglo a ser anadido a {@linkplain #array}
	 * @since 1.0.2
	 */
	public void addArray(int[] o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un arreglo de enteros a {@link #array}
	 *
	 * @param o   El arreglo a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(int[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un arreglo de flotantes a {@link #array}
	 *
	 * @param o El arreglo de flotantes a anadir
	 * @since 1.0.2
	 */
	public void addArray(float[] o) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, false);
	}

	/**
	 * Anade un arreglo de flotantes a {@link #array}
	 *
	 * @param o   El arreglo de flotantes a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(float[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un arreglo de dobles a {@link #array}
	 *
	 * @param o El arreglo de dobles a anadir
	 * @since 1.0.2
	 */
	public void addArray(double[] o) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, false);
	}

	/**
	 * Anade un arreglo de dobles a {@link #array}
	 *
	 * @param o   El arreglo de dobles a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(double[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un arreglo de cadenas a {@link #array}
	 *
	 * @param o El arreglo de cadenas a anadir
	 * @since 1.0.2
	 */
	public void addArray(String[] o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un arreglo de cadenas a {@link #array}
	 *
	 * @param o   El arreglo de cadenas a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(String[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un arreglo de booleanos a {@link #array}
	 *
	 * @param o El arreglo de booleanos a anadir
	 * @since 1.0.2
	 */
	public void addArray(boolean[] o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un arreglo de booleanos a {@link #array}
	 *
	 * @param o   El arreglo de booleanos a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(boolean[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un arreglo de Var a {@link #array}
	 *
	 * @param o El arreglo de Var a anadir
	 * @see Var
	 * @since 1.0.2
	 */
	public void addArray(Var[] o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un arreglo de Var a {@link #array}
	 *
	 * @param o   El arreglo de Var a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @see Var
	 * @since 1.0.2
	 */
	public void addArray(Var[] o, boolean val) {
		Object[] a = new Object[o.length];
		for (int i = 0; i < o.length; i++) {
			a[i] = ((Var) o[i]).get();
		}
		this.addArray(a, val);
	}

	/**
	 * Anade un Array a {@link #array}
	 *
	 * @param o El Array a ser anadido
	 * @see Array
	 * @since 1.0.3
	 */
	public void addArray(Array o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un Array a {@link #array}
	 *
	 * @param o   El Array a aser anadido
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @see Array
	 * @since 1.0.3
	 */
	public void addArray(Array o, boolean val) {
		for (int i = 0; i < o.len(); i++) {
			if (o.get(i) instanceof Array) {
				this.addArray((Array) o.get(i), val);
			} else {
				this.add(o.get(i));
			}
		}
	}

	/**
	 * Anade un arreglo de Object a {@link #array}
	 *
	 * @param o El arreglo de Object a anadir
	 * @since 1.0.2
	 */
	public void addArray(Object[] o) {
		this.addArray(o, false);
	}

	/**
	 * Anade un arreglo de Object a {@link #array}
	 *
	 * @param o   El arreglo de Object a anadir
	 * @param val true para distribuir los elementos. false para anadir todos en una
	 *            sola posicion. Si se omite es false
	 * @since 1.0.2
	 */
	public void addArray(Object[] o, boolean val) {
		if (val) {
			Object[] aux = this.array;
			this.array = new Object[aux.length + o.length];
			int x = 0;
			while (x < aux.length) {
				this.array[x] = aux[x];
				x++;
			}
			for (int i = 0; i < o.length; i++) {
				this.array[x] = o[i];
				x++;
			}
		} else {
			Object[] aux = this.array;
			this.array = new Object[aux.length + 1];
			for (int i = 0; i < aux.length; i++) {
				this.array[i] = aux[i];
			}
			this.array[aux.length] = o;
		}
	}

	/**
	 * Anade un Object a {@link #array} en una posicion especifica
	 *
	 * @param o   El Object a ser anadido
	 * @param pos La posicion en la cual se desea anadir
	 * @return true => Success. false => failure
	 * @since 1.0.1
	 */
	public boolean add(Object o, int pos) {
		if (pos >= 0 && pos <= this.array.length) {
			Object[] aux = this.array;
			this.array = new Object[aux.length + 1];
			int x1 = 0;
			int x2 = 0;
			while (x1 < this.array.length) {
				if (x1 == pos) {
					this.array[x1] = o;
				} else {
					this.array[x1] = aux[x2];
					x2++;
				}
				x1++;
			}
			return true;
		}
		return false;
	}

	/**
	 * Elimina todos los datos guardados en {@link #array}
	 *
	 * @since 1.0.0
	 */
	public void del() {
		this.array = new Object[0];
	}

	/**
	 * Elimina el dato guardado en {@link #array} en una posicion especifica
	 *
	 * @param pos La posicion del elemento que se desea eliminar
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean del(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			Object[] aux = this.array;
			this.array = new Object[aux.length - 1];
			int cont = 0;
			for (int x = 0; x < aux.length; x++) {
				if (x != pos) {
					this.array[cont] = aux[x];
					cont++;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Elimina un objeto determinado guardado en {@link #array}
	 *
	 * <pre>
	 * Elimina la primera coincidencia del objeto indicado
	 * Si el objeto esta almacenado en mas de una posicion
	 * solo la priemra coincidencia es eliminada
	 * En caso de no existir coincidencias, no ocurre nada y devuelve false
	 * </pre>
	 *
	 * @param object El objeto el cual se desea eliminar
	 * @return true => El objeto fue ubicado y eliminado. false => No se ubico el
	 * objeto
	 * @since 1.2.1
	 */
	public boolean delObject(Object object) {
		return this.delObject(object, false);
	}

	/**
	 * Elimina un objeto determinado guardado en {@link #array}
	 *
	 * <pre>
	 * Si el segundo parametro es true, elimina todas las coincidencias del objeto indicado
	 * En caso de no existir coincidencias, no ocurre nada y devuelve false
	 * </pre>
	 *
	 * @param object          El objeto el cual se desea eliminar
	 * @param allCoincidences true => Eliminar todas las coincidencias. false =>
	 *                        Eliminar solo la primera coincidencia
	 * @return true => El objeto fue ubicado y eliminado. false => No se ubico el
	 * objeto
	 * @since 1.2.1
	 */
	public boolean delObject(Object object, boolean allCoincidences) {
		boolean flag = false;
		if (allCoincidences) {
			for (int i = 0; i < this.len(); i++) {
				if (this.get(i) == object) {
					flag = true;
					this.del(i);
				}
			}
		} else {
			int i = 0;
			while (i < this.len() && !flag) {
				if (this.get(i) == object) {
					flag = true;
					this.del(i);
				}
				i++;
			}
		}
		return flag;
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como Object
	 * @since 1.0.0
	 */
	public Object get(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return null;
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como Int
	 * @since 1.0.0
	 */
	public int getInt(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return Integer.parseInt((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return -1;
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como Float
	 * @since 1.0.0
	 */
	public float getFloat(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return (float) this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return -1;
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como Double
	 * @since 1.0.0
	 */
	public double getDouble(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return (double) this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return -1;
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como Char
	 * @since 1.0.0
	 */
	public char getChar(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return (char) this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return '0';
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como String
	 * @since 1.0.0
	 */
	public String getString(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return (String) this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return "";
	}

	/**
	 * Retorna el dato guardado en {@link #array} de una posicion especifica
	 *
	 * @param pos La posicion del dato que se desea obtener
	 * @return El dato guardado en la posicion indicada casteado como boolean
	 * @since 1.0.0
	 */
	public boolean getBoolean(int pos) {
		if (pos >= 0 && pos < this.array.length) {
			return Boolean.parseBoolean((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return false;
	}

	/**
	 * Reemplaza el dato guardado en la posicion indicada
	 *
	 * @param o   El Object con el cual reemplazara al dato original
	 * @param pos La posicion del dato que desea reemplazar
	 * @return true => Success. false => Failure
	 * @since 1.0.1
	 */
	public boolean mod(Object o, int pos) {
		if (pos >= 0 && pos < this.array.length) {
			this.array[pos] = o;
			return true;
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return false;
	}

	/**
	 * Retorna la longitud de {@linkplain #array}
	 *
	 * @return La longitud de {@linkplain #array}
	 * @since 1.0.0
	 */
	public int len() {
		return this.array.length;
	}

	/**
	 * Retorna un arreglo de String con el tipo de dato de cada dato guardado en
	 * {@link #array}
	 *
	 * @return Un arreglo de String
	 * @since 1.0.3
	 */
	public String[] types() {
		String[] cad = new String[this.array.length];
		for (int i = 0; i < this.array.length; i++) {
			cad[i] = this.array[i].getClass().getSimpleName();
		}
		return cad;
	}

	public String toString() {
		String cad = "[";
		for (int i = 0; i < this.array.length; i++) {
			cad += "" + this.array[i].toString();
			if (i < this.array.length - 1) {
				cad += ", ";
			}
		}
		cad += "]";
		return cad;
	}

	/**
	 * Imprime los datos de {@link #array} en una sola linea
	 *
	 * @since 1.0.2
	 */
	public void print() {
		this.print(false);
	}

	/**
	 * Imprime los datos de {@link #array}
	 *
	 * @param val true => Una linea por dato. false => Una sola linea
	 * @since 1.0.2
	 */
	public void print(boolean val) {
		P.p("[");
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i] instanceof Object[]) {
				this.printArray((Object[]) this.array[i], false);
			} else {
				System.out.print(this.array[i]);
			}
			if (i < this.array.length - 1) {
				System.out.print(", ");
			}
			if (val) {
				System.out.println();
			}
		}
		System.out.print("]");
	}

	/**
	 * Imprime un arreglo de Object
	 *
	 * @param o   El arreglo de Object a imprimir
	 * @param val true => Un Object por linea. false => Una sola linea
	 * @since 1.0.2
	 */
	private void printArray(Object[] o, boolean val) {
		System.out.print("[");
		for (int i = 0; i < o.length; i++) {
			System.out.print(o[i]);
			if (i < o.length - 1) {
				System.out.print(", ");
			}
			if (val) {
				System.out.println();
			}
		}
		System.out.print("]");
	}

	/**
	 * Imprime una descripcion de los datos guardados en {@link #array}
	 *
	 * @since 1.0.2
	 */
	public void desc() {
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i] instanceof Object[]) {
				this.printArray((Object[]) this.array[i], false);
			} else {
				System.out.print(this.array[i]);
			}
			System.out.print(":" + this.array[i].getClass().getSimpleName());
			if (i < this.array.length - 1) {
				System.out.println("");
			}
		}
	}
}