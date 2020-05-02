package tools;

import java.io.Serializable;

public class Array implements Serializable {
	private Object[] array = new Object[0];
	private static Array[] instances = new Array[0];
	private static Object[] sum = new Object[0];
	
	public Array() {
		addInstance(this);
	}
	
	public Array(Object object) {
		this.array = new Object[1];
		this.array[0] = object;
		addInstance(this);
	}
	
	public Array(Var var) {
		this.array = new Object[1];
		this.array[0] = var.get();
		addInstance(this);
	}
	
	public Array(Object... objects) {
		this.array = new Object[objects.length];
		System.arraycopy(objects, 0, this.array, 0, objects.length);
		addInstance(this);
	}
	
	public Array(Array array) {
		this.array = new Object[array.len()];
		for (int i = 0; i < array.len(); i++) {
			this.array[i] = array.get(i);
		}
		addInstance(this);
	}
	
	public static void addInstance(Array array) {
		Array[] aux = Array.instances;
		Array.instances = new Array[aux.length + 1];
		if (aux.length >= 0) {
			System.arraycopy(aux, 0, Array.instances, 0, aux.length);
		}
		Array.instances[aux.length] = array;
	}
	
	public static void sum(Object object) {
		Object[] aux = Array.sum;
		Array.sum = new Object[aux.length + 1];
		if (aux.length >= 0) {
			System.arraycopy(aux, 0, Array.sum, 0, aux.length);
		}
		Array.sum[aux.length] = object;
	}
	
	public static void dump() {
		Array.dump(false);
	}
	
	public static void dump(boolean clear) {
		for (int i = 0; i < Array.instances.length; i++) {
			for (int s = 0; s < Array.sum.length; s++) {
				Array.instances[i].add(Array.sum[s]);
			}
		}
		if (clear) {
			Array.sum = new Object[0];
		}
	}
	
	public static void clear() {
		Array.sum = new Object[0];
	}
	
	public static void dump(boolean providedArray, Array... arrays) {
		if (providedArray) {
			for (int i = 0; i < arrays.length; i++) {
				for (int s = 0; s < Array.sum.length; s++) {
					arrays[i].add(Array.sum[s]);
				}
			}
		} else {
			for (int i = 0; i < Array.instances.length; i++) {
				for (Array currentArray: arrays) {
					if (Array.instances[i] != currentArray) {
						for (int s = 0; s < Array.sum.length; s++) {
							Array.instances[i].add(Array.sum[s]);
						}
					}
				}
			}
		}
	}
	
	public void add(Object... objects) {
		Object[] aux = this.array;
		this.array = new Object[aux.length + objects.length];
		int x = 0;
		if (aux.length >= 0) {
			System.arraycopy(aux, 0, this.array, 0, aux.length);
		}
		for (Object currentObject: objects) {
			if (currentObject instanceof Var) {
				this.array[x] = ((Var) currentObject).get();
			}
			if (currentObject instanceof Array) {
				for (int s = 0; s < ((Array) currentObject).len(); s++) {
					this.array[x] = ((Array) currentObject).get(s);
				}
			} else {
				this.array[x] = currentObject;
				x++;
			}
		}
	}
	
	public void addArray(int[] integers) {
		this.addArray(integers, false);
	}
	
	public void addArray(int[] integers, boolean val) {
		Object[] a = new Object[integers.length];
		for (int i = 0; i < integers.length; i++) {
			a[i] = integers[i];
		}
		this.addArray(a, val);
	}
	
	public void addArray(float[] floats) {
		Object[] a = new Object[floats.length];
		for (int i = 0; i < floats.length; i++) {
			a[i] = floats[i];
		}
		this.addArray(a, false);
	}
	
	public void addArray(float[] floats, boolean explode) {
		Object[] a = new Object[floats.length];
		for (int i = 0; i < floats.length; i++) {
			a[i] = floats[i];
		}
		this.addArray(a, explode);
	}
	
	public void addArray(double[] doubles) {
		Object[] a = new Object[doubles.length];
		for (int i = 0; i < doubles.length; i++) {
			a[i] = doubles[i];
		}
		this.addArray(a, false);
	}
	
	public void addArray(double[] doubles, boolean explode) {
		Object[] a = new Object[doubles.length];
		for (int i = 0; i < doubles.length; i++) {
			a[i] = doubles[i];
		}
		this.addArray(a, explode);
	}
	
	public void addArray(String[] strings) {
		this.addArray(strings, false);
	}
	
	public void addArray(String[] strings, boolean explode) {
		Object[] a = new Object[strings.length];
		for (int i = 0; i < strings.length; i++) {
			a[i] = strings[i];
		}
		this.addArray(a, explode);
	}
	
	public void addArray(boolean[] o) {
		this.addArray(o, false);
	}
	
	public void addArray(boolean[] booleans, boolean explode) {
		Object[] a = new Object[booleans.length];
		for (int i = 0; i < booleans.length; i++) {
			a[i] = booleans[i];
		}
		this.addArray(a, explode);
	}
	
	public void addArray(Var[] vars) {
		this.addArray(vars, false);
	}
	
	public void addArray(Var[] vars, boolean explode) {
		Object[] a = new Object[vars.length];
		for (int i = 0; i < vars.length; i++) {
			a[i] = ((Var) vars[i]).get();
		}
		this.addArray(a, explode);
	}
	
	public void addArray(Array array) {
		this.addArray(array, false);
	}
	
	public void addArray(Array array, boolean explode) {
		for (int i = 0; i < array.len(); i++) {
			if (array.get(i) instanceof Array) {
				if (explode) {
					this.addArray((Array) array.get(i));
				} else {
					this.add(array);
				}
			} else {
				this.add(array.get(i));
			}
		}
	}
	
	public void addArray(Object[] o) {
		this.addArray(o, false);
	}
	
	public void addArray(Object[] objects, boolean explode) {
		Object[] aux = this.array;
		if (explode) {
			this.array = new Object[aux.length + objects.length];
			int x = 0;
			while (x < aux.length) {
				this.array[x] = aux[x];
				x++;
			}
			for (Object currentObject: objects) {
				this.array[x] = currentObject;
				x++;
			}
		} else {
			this.array = new Object[aux.length + 1];
			if (aux.length >= 0) {
				System.arraycopy(aux, 0, this.array, 0, aux.length);
			}
			this.array[aux.length] = objects;
		}
	}
	
	public boolean add(Object o, int pos) {
		boolean added = false;
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
			added = true;
		}
		return added;
	}
	
	public void del() {
		this.array = new Object[0];
	}
	
	public boolean del(int pos) {
		boolean deleted = false;
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
			deleted = true;
		}
		return deleted;
	}
	
	public boolean delObject(Object object) {
		return this.delObject(object, false);
	}
	
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
	
	public Object get(int pos) {
		Object returnValue = null;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public int getInt(int pos) {
		int returnValue = -1;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = Integer.parseInt((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public float getFloat(int pos) {
		float returnValue = -1;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = Float.parseFloat((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public double getDouble(int pos) {
		double returnValue = -1;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = Double.parseDouble((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public char getChar(int pos) {
		char returnValue = 0;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = (char) this.array[pos];
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public String getString(int pos) {
		String returnValue = null;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = String.valueOf(this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public boolean getBoolean(int pos) {
		boolean returnValue = false;
		if (pos >= 0 && pos < this.array.length) {
			returnValue = Boolean.parseBoolean((String) this.array[pos]);
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return returnValue;
	}
	
	public boolean modify(Object object, int pos) {
		boolean modified = false;
		if (pos >= 0 && pos < this.array.length) {
			this.array[pos] = object;
			modified = true;
		} else {
			try {
				throw new ArrayIndexOutOfBoundsException();
			} catch (ArrayIndexOutOfBoundsException e) {
				P.err(e);
			}
		}
		return modified;
	}
	
	public int len() {
		return this.array.length;
	}
	
	public String[] types() {
		String[] types = new String[this.array.length];
		for (int i = 0; i < this.array.length; i++) {
			types[i] = this.array[i].getClass().getSimpleName();
		}
		return types;
	}
	
	@Deprecated
	public String toString() {
		StringBuilder cad = new StringBuilder("[");
		for (int i = 0; i < this.array.length; i++) {
			cad.append("").append(this.array[i].toString());
			if (i < this.array.length - 1) {
				cad.append(", ");
			}
		}
		cad.append("]");
		return cad.toString();
	}
	
	@Deprecated
	public void print() {
		this.print(false);
	}
	
	@Deprecated
	public void print(boolean splitLines) {
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
			if (splitLines) {
				System.out.println();
			}
		}
		System.out.print("]");
	}
	
	@Deprecated
	private static void printArray(Object[] objects, boolean splitLines) {
		System.out.print("[");
		for (int i = 0; i < objects.length; i++) {
			System.out.print(objects[i]);
			if (i < objects.length - 1) {
				System.out.print(", ");
			}
			if (splitLines) {
				System.out.println();
			}
		}
		System.out.print("]");
	}
	
	public void describe() {
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i] instanceof Object[]) {
				Array.printArray((Object[]) this.array[i], false);
			} else {
				System.out.print(this.array[i]);
			}
			System.out.print(":" + this.array[i].getClass().getSimpleName());
			if (i < this.array.length - 1) {
				System.out.println();
			}
		}
	}
}