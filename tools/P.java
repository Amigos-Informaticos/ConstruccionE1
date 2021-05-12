package tools;


public abstract class P {
	
	public static void p(Object object) {
		System.out.print(object.toString());
	}
	
	public static void pln(Object object) {
		System.out.println(object.toString());
	}
	
	public static void p(File file) {
		if (file.getPath() != null) {
			if (file.exists()) {
				String line;
				while ((line = file.readLine()) != null) {
					pln(line);
				}
			} else {
				P.err("The file does not exists");
			}
		} else {
			P.err("File's path is not defined");
		}
	}
	
	public static void printArray(int[] integers) {
		printArray(integers, false);
	}
	
	public static void printArray(int[] integers, boolean splitLines) {
		Object[] objects = new Object[integers.length];
		for (int i = 0; i < integers.length; i++) {
			objects[i] = integers[i];
		}
		printArray(objects, splitLines);
	}
	
	public static void printArray(float[] floats) {
		printArray(floats, false);
	}
	
	public static void printArray(float[] floats, boolean splitLines) {
		Object[] objects = new Object[floats.length];
		for (int i = 0; i < floats.length; i++) {
			objects[i] = floats[i];
		}
		printArray(objects, splitLines);
	}
	
	public static void printArray(double[] doubles) {
		printArray(doubles, false);
	}
	
	public static void printArray(double[] doubles, boolean splitLines) {
		Object[] objects = new Object[doubles.length];
		for (int i = 0; i < doubles.length; i++) {
			objects[i] = doubles[i];
		}
		printArray(objects, splitLines);
	}
	
	public static void printArray(boolean[] booleans) {
		printArray(booleans, false);
	}
	
	public static void printArray(boolean[] booleans, boolean splitLines) {
		Object[] objects = new Object[booleans.length];
		for (int i = 0; i < booleans.length; i++) {
			objects[i] = booleans[i];
		}
		printArray(objects, splitLines);
	}
	
	public static void printArray(String[] strings) {
		printArray(strings, false);
	}
	
	public static void printArray(String[] strings, boolean splitLines) {
		Object[] a = new Object[strings.length];
		System.arraycopy(strings, 0, a, 0, strings.length);
		printArray(a, splitLines);
	}
	
	public static void printArray(Object[] objects) {
		printArray(objects, false);
	}
	
	public static void printArray(Object[] objects, boolean splitLines) {
		if (splitLines) {
			for (Object object: objects) {
				pln(object);
			}
		} else {
			p("[");
			for (int i = 0; i < objects.length; i++) {
				p(objects[i]);
				if (i < objects.length - 1) {
					p(", ");
				}
			}
			p("]");
		}
	}
	
	public static void err(Object err) {
		System.err.println(err.toString());
	}
}
