package tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase Arch
 *
 * @author Edson Manuel Carballo Vera
 * @version 1.6
 */
public class Arch implements Serializable {
	private static final long serialVersionUID = 1L;
	private Path path = null;
	public BufferedReader in = null;
	
	/**
	 * Constructor vacio
	 *
	 * @since 1.0.0
	 */
	public Arch() {
	}
	
	/**
	 * Constructor con inicializador de Path
	 *
	 * @param path Cadena con Path del archivo
	 * @since 1.0.0
	 */
	public Arch(String path) {
		this.path = Paths.get(path);
	}
	
	/**
	 * Constructor con inicializador de Path y de LineReader
	 *
	 * @param path Cadena con Path del archivo
	 * @param var  true => Inicializar lector por lineas
	 * @throws FileNotFoundException En caso de que no se encuentre el archivo
	 *                               indicado
	 * @since 1.0.2
	 * @deprecated Ya no es necesario inicializar el lector por lineas
	 */
	@Deprecated
	public Arch(String path, boolean var) throws FileNotFoundException {
		this.path = Paths.get(path);
		if (var) {
			this.initLineReader();
		}
	}
	
	/**
	 * Constructor con inicializador de Path
	 *
	 * @param p Path del archivo
	 * @since 1.2.0
	 */
	public Arch(Path p) {
		this.path = p;
	}
	
	/**
	 * Constructor con inicializador de tipo Arch
	 *
	 * @param a Instancia de tipo Arch con path del archivo
	 * @since 1.4.0
	 */
	public Arch(Arch a) {
		this.path = a.getPath();
	}
	
	/**
	 * Constructor con inicializador de Path y de LineReader
	 *
	 * @param p   Path del archivo
	 * @param var true => Inicializar lector por lineas
	 * @throws FileNotFoundException En caso de que no se encuentre el archivo
	 *                               indicado
	 * @since 1.2.0
	 * @deprecated Ya no es necesario inicializar el lector por lineas
	 */
	@Deprecated
	public Arch(Path p, boolean var) throws FileNotFoundException {
		this.path = p;
		if (var) {
			this.initLineReader();
		}
	}
	
	/**
	 * Inicializa el lector por lineas
	 *
	 * <pre>
	 * Si no se inicializa aqui ni en el constructor, no es posible leer lineas individuales del archivo.
	 * Desde la version 1.2.0 ya no es necesario para usar el lector por lineas.
	 * </pre>
	 *
	 * @return true => Success. false => Failure.
	 * @since 1.1.2
	 */
	public boolean initLineReader() {
		try {
			this.in = new BufferedReader(new FileReader(this.path.toFile()));
			return true;
		} catch (Exception e) {
			P.err(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Setter del path del archivo
	 *
	 * @param path Cadena con Path del archivo
	 * @since 1.0.0
	 */
	public void setPath(String path) {
		this.path = Paths.get(path);
	}
	
	/**
	 * Setter del path del archivo
	 *
	 * @param path Path del archivo
	 * @since 1.2.4
	 */
	public void setPath(Path path) {
		this.setPath(path.toString());
	}
	
	/**
	 * Getter del path del archivo
	 *
	 * @return String con el path del archivo
	 * @since 1.0.0
	 */
	public String getStringPath() {
		return this.path.toString();
	}
	
	/**
	 * Getter del path del archivo
	 *
	 * @return Path del archivo
	 * @since 1.2.0
	 */
	public Path getPath() {
		return this.path;
	}
	
	/**
	 * Crea el archivo a partir de {@link #path}
	 *
	 * @return true => Success. false => Failure
	 * @since 1.0.2
	 */
	public boolean crear() {
		if (this.path != null) {
			return Arch.crear(this.path.toString());
		}
		return false;
	}
	
	/**
	 * Crea el archivo a partir del Path indicado
	 *
	 * @param p Path en cadena del archivo que se desea crear
	 * @return true => Success. false => Failure
	 * @since 1.0.2
	 */
	public static boolean crear(String p) {
		return Arch.crear(Paths.get(p));
	}
	
	/**
	 * Crea el archivo a partir del Path indicado
	 *
	 * @param p Path del archivo que se desea crear
	 * @return true => Success. false => Failure
	 * @since 1.0.2
	 */
	public static boolean crear(Path p) {
		if (Files.exists(p)) {
			return false;
		}
		try {
			Files.createFile(p);
			return true;
		} catch (IOException e) {
			P.err("ERROR " + e);
			return false;
		}
	}
	
	/**
	 * Crea un directorio con el Path indicado
	 *
	 * @param p Path en cadena para crear el directorio
	 * @return true => Success. false => Failure
	 * @since 1.2.0
	 * @deprecated Usar en su lugar {@link tools.Dir#crear(String) crear}
	 */
	@Deprecated
	public static boolean crearDir(String p) {
		return Arch.crearDir(Paths.get(p));
	}
	
	/**
	 * Crea un directorio a con el Path indicado
	 *
	 * @param p Path para crear el directorio
	 * @return true => Success. false => Failure
	 * @since 1.2.0
	 * @deprecated Usar en su lugar {@link tools.Dir#crear(String) crear}
	 */
	@Deprecated
	public static boolean crearDir(Path p) {
		if (Files.exists(p)) {
			return false;
		}
		try {
			Files.createDirectory(p);
			return true;
		} catch (Exception e) {
			P.err("ERROR " + e);
			return false;
		}
	}
	
	/**
	 * Elimina el archivo indicado
	 *
	 * @param p Path en cadena del archivo que desea eliminar
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean delArchivo(String p) {
		Path path = Paths.get(p);
		try {
			Files.delete(path);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Elimina el archivo con {@link #path}
	 *
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean delArchivo() {
		try {
			Files.delete(path);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Renombra el archivo con el path indicado
	 *
	 * @param path Path en cadena para renombrar al archivo
	 * @return true => Success. false => Failure
	 * @since 1.2.1
	 */
	public boolean renombrar(String path) {
		return this.renombrar(Paths.get(path));
	}
	
	/**
	 * Renombra el archivo con el path indicado
	 *
	 * @param path Path para renombrar al archivo
	 * @return true => Succes. false => Failure
	 * @since 1.2.1
	 */
	public boolean renombrar(Path path) {
		if (this.path != null) {
			try {
				File a = this.path.toFile();
				return a.renameTo(path.toFile());
			} catch (Exception e) {
				P.err(e.getMessage());
			}
		} else {
			P.err("No existe Path en la instancia\n");
		}
		return false;
	}
	
	/**
	 * Verifica si un archivo existe
	 *
	 * <pre>
	 * Utilizar si se desea verficiar la existencia de un archivo con un Path distinto al de la instancia.
	 * Si el archivo existe, retorna true.
	 * Caso contrario, retorna false.
	 * </pre>
	 *
	 * @param path Path del archivo a verificar
	 * @return Boolean. true => Existe. false => No existe
	 * @since 1.2.3
	 */
	public static boolean existe(Path path) {
		return Files.exists(path);
	}
	
	/**
	 * Verifica si un archivo existe
	 *
	 * <pre>
	 * Utilizar si se desea verficiar la existencia de un archivo con un Path distinto al de la instancia.
	 * Si el archivo existe, retorna true.
	 * Caso contrario, retorna false.
	 * </pre>
	 *
	 * @param path Path en cadena del archivo a verificar
	 * @return Boolean. true => Existe. false => No existe
	 * @since 1.2.3
	 */
	public static boolean existe(String path) {
		return Arch.existe(Paths.get(path));
	}
	
	/**
	 * Verifica si el archivo existe con {@link #path}
	 *
	 * @return true => El archivo existe. false => El archivo no existe
	 * @since 1.1.0
	 */
	public boolean existe() {
		if (this.path != null) {
			return Files.exists(this.path);
		}
		P.err("El path no esta definido");
		return false;
	}
	
	/**
	 * Escribe el texto indicado en el archivo
	 *
	 * <pre>
	 * </pre>
	 *
	 * @param text Texto a escribir en el archivo
	 * @return true => El archivo existe y la escritura fue un exito. false => No se
	 * logro escribir
	 * @since 1.0.0
	 */
	public boolean escribir(String text) {
		return this.escribir(text, true);
	}
	
	/**
	 * Escribe el texto indicado en el archivo
	 *
	 * @param text Texto a escribir en el archivo
	 * @param var  true => Agregar al final. false => Sobre-escribir
	 * @return true => La escritura fue un exito. false => No se logro escribir
	 * @since 1.0.0
	 */
	public boolean escribir(String text, boolean var) {
		if (this.path != null) {
			if (!this.existe()) {
				this.crear();
			}
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(this.path.toFile(), var));
				bw.write(text);
				bw.flush();
				bw.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		} else {
			P.err("No se ha indicado un Path para el archivo");
		}
		return false;
	}
	
	/**
	 * Introduce un salto de linea en el archivo
	 *
	 * @return true => Success. false => Failure
	 * @since 1.1.0
	 */
	public boolean newLine() {
		if (this.path != null) {
			if (!this.existe()) {
				this.crear();
			}
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(this.path.toFile(), true));
				bw.newLine();
				bw.flush();
				bw.close();
				return true;
			} catch (Exception e) {
				P.err(e);
			}
		} else {
			P.err("No se ha indicado un Path para el archivo");
		}
		return false;
	}
	
	/**
	 * Lee el archivo indicado con el Path en cadena y retorna el archivo en
	 * una cadena. <br>
	 * No se recomienda su uso. <br>
	 * Usar en su lugar leerLinea
	 *
	 * @param p Path en cadena del archivo que se desea leer.
	 * @return String con el contenido del archivo. Si no se puede leer, retorna
	 * null.
	 * @see {@link #leerLinea()}
	 * @since 1.0.1
	 * @deprecated
	 */
	@Deprecated
	public static String leerArchivo(String p) {
		String cad = "";
		try {
			Reader inputReader = new InputStreamReader(new FileInputStream(p));
			int data = inputReader.read();
			while (data != -1) {
				cad += (char) data;
				data = inputReader.read();
			}
			inputReader.close();
		} catch (Exception e) {
			P.err(e.getMessage());
			cad = null;
		}
		return cad;
	}
	
	/**
	 * Lee el archivo y retorna el archivo en una cadena. <br>
	 * No se recomienda su uso. <br>
	 * Usar en su lugar leerLinea.
	 *
	 * @return String con el contenido del archivo. Si no se puede leer, retorna
	 * null.
	 * @see {@link #leerLinea()}
	 * @since 1.0.1
	 * @deprecated
	 */
	@Deprecated
	public String leerArchivo() {
		return Arch.leerArchivo(this.path.toString());
	}
	
	/**
	 * Lee el archivo y retorna un arreglo de String con el contenido. <br>
	 * No se recomienda su uso. <br>
	 * Usar en su lugar leerLinea
	 *
	 * @return Arreglo de String con el contenido
	 * @see {@link #leerLinea()}
	 * @since 1.1.0
	 */
	public String[] getLineasArchivo() {
		return Arch.getLineasArchivo(this.path.toString());
	}
	
	/**
	 * Lee el archivo y retorna un arreglo de String con el contenido. <br>
	 * No se recomienda su uso. <br>
	 * Usar en su lugar leerLinea
	 *
	 * @param p Path en cadena del archivo que se desea leer
	 * @return Arreglo de String con el contenido
	 * @see {@link #leerLinea()}
	 * @since 1.1.0
	 * @deprecated
	 */
	@Deprecated
	public static String[] getLineasArchivo(String p) {
		String line = "";
		try {
			Reader inputReader = new InputStreamReader(new FileInputStream(p));
			int data = inputReader.read();
			while (data != -1) {
				line += ((char) data);
				data = inputReader.read();
			}
			inputReader.close();
		} catch (Exception e) {
			line = null;
		}
		return line.split("\\r?\\n");
	}
	
	/**
	 * Lee una linea del archivo y la retorna como String.
	 *
	 * <pre>
	 * Guarda la ultima posicion leida, por lo que se puede usar de forma secuencial.
	 * Desde la version 1.2.0 ya no es necesario inicializar el lector por linea (lineReader).
	 * Si se desea volver al inicio del archivo, inicializar el lector por linea (lineReader).
	 * </pre>
	 *
	 * @return String con una linea del archivo
	 * @see {@link #initLineReader()}
	 * @since 1.2.0
	 */
	public String leerLinea() {
		String cad = null;
		if (this.in == null)
			this.initLineReader();
		try {
			if ((cad = this.in.readLine()) == null)
				cad = null;
		} catch (Exception e) {
			System.out.println(e);
		}
		if (cad == null)
			this.in = null;
		return cad;
	}
	
	/**
	 * Encuentra y reemplaza una linea por otra dentro del archivo
	 *
	 * <pre>
	 * Se busca la linea <i>original</i> dentro del archivo. Si se encuentra, es
	 * reemplazada por <i>reemplazo</i>.
	 *
	 *
	 * @param original  La linea a ser reemplazada en el archivo.
	 * @param reemplazo La linea que reemplazara a la original.
	 *
	 * @since 1.4
	 */
	public void mod(String original, String reemplazo) {
		this.mod(original, reemplazo, false);
	}
	
	/**
	 * Encuentra y reemplaza una linea por otra dentro del archivo
	 *
	 * <p>
	 * Se busca la linea <i>original</i> dentro del archivo.
	 * Si se encuentra, es reemplazada por <i>reemplazo</i>.
	 * El parametro <i>todos</i> indica si se reemplazan todas las coincidencias o solo la primera.
	 * </p>
	 *
	 * @param original  La linea a ser reemplazada en el archivo.
	 * @param reemplazo La linea que reemplazara a la original.
	 * @param todos     true => Se reemplazan todas las coincidencias. false => Solo
	 *                  se reemplaza la primera coincidencia.
	 * @since 1.2.2
	 */
	public void mod(String original, String reemplazo, boolean todos) {
		try {
			Files.createFile(Paths.get("a.txt"));
			BufferedReader r = new BufferedReader(new FileReader(this.path.toFile()));
			BufferedWriter w = new BufferedWriter(new FileWriter(Paths.get("a.txt").toFile()));
			String linea;
			boolean flag = true;
			boolean cont = true;
			while ((linea = r.readLine()) != null) {
				if (linea.equals(original) && flag) {
					if (!cont) {
						w.newLine();
					}
					w.write(reemplazo);
					if (!todos) {
						flag = false;
					}
				} else {
					if (!cont) {
						w.newLine();
					}
					w.write(linea);
				}
				cont = false;
			}
			w.flush();
			w.close();
			r.close();
			Files.delete(this.path);
			File a = Paths.get("a.txt").toFile();
			a.renameTo(this.path.toFile());
		} catch (Exception e) {
			P.err(e);
		}
	}
	
	/**
	 * Retorna el nombre de un archivo
	 *
	 * @param path Path del archivo del cual se desea conocer el nombre
	 * @return String con el nombre del archivo
	 * @since 1.3.0
	 */
	public static String getName(Path path) {
		return Arch.getName(path.toString());
	}
	
	/**
	 * Retorna el nombre de un archivo
	 *
	 * @param arch Instacia de tipo Arch de la cual se desea saber el nombre
	 * @return String con el nombre del archivo
	 * @since 1.3.0
	 */
	public static String getName(Arch arch) {
		if (arch.existe()) {
			return arch.getName();
		}
		return null;
	}
	
	/**
	 * Retorna el nombre de un archivo
	 *
	 * @param path String del path del archivo del cual se desea obtener el nombre
	 * @return String con el nombre del archivo
	 * @since 1.3.0
	 */
	public static String getName(String path) {
		if (Arch.existe(path)) {
			File aux = new File(path);
			return aux.getName();
		}
		return null;
	}
	
	/**
	 * Retorna el nombre del archivo
	 *
	 * @return String con el nombre del archivo
	 * @since 1.3.0
	 */
	public String getName() {
		if (this.existe()) {
			return Arch.getName(this.path);
		}
		return null;
	}
	
	/**
	 * Retorna el nombre del archivo sin extension
	 *
	 * @param file Instancia de Arch
	 * @return Cadena con nombre del archivo sin extension
	 */
	public static String getNameNoExt(Arch file) {
		return file.getNameNoExt();
	}
	
	/**
	 * Retorna el nombre del archivo sin extension
	 *
	 * @param path Cadena con Path del archivo cuyo nombre se desea conocer
	 * @return Cadena con nombre del archivo sin extension
	 */
	public static String getNameNoExt(String path) {
		return new Arch(path).getNameNoExt();
	}
	
	/**
	 * Retorna el nombre del archivo sin extension
	 *
	 * @return Cadena con nombre del archivo sin extension
	 */
	public String getNameNoExt() {
		if (this.existe()) {
			return this.getName().replaceFirst("[.][^.]+$", "");
		}
		return null;
	}
	
	/**
	 * Retorna la extension de un archivo
	 *
	 * @param path Path del archivo del cual se desea conocer la extension
	 * @return String con la extension del archivo
	 * @since 1.3.2
	 */
	public static String getExt(Path path) {
		return Arch.getExt(path.toString());
	}
	
	/**
	 * Retorna la extension de un archivo
	 *
	 * @param arch Instancia de tipo Arch del cual se desea saber la extension
	 * @return String con la extension del archivo
	 * @since 1.3.2
	 */
	public static String getExt(Arch arch) {
		return Arch.getExt(arch.getName());
	}
	
	/**
	 * Retorna la extension de un archivo
	 *
	 * @param path String con el path del archivo
	 * @return String con la extension del archivo
	 * @since 1.3.2
	 */
	public static String getExt(String path) {
		if (Arch.existe(path)) {
			String rev = Util.reverse(Arch.getName(path));
			String ret = "";
			int i = 0;
			while (rev.charAt(i) != '.') {
				ret += rev.charAt(i);
				i++;
			}
			return Util.reverse(ret);
		}
		return null;
	}
	
	/**
	 * Retorna la extension de un archivo
	 *
	 * @return String con la extension del archivo
	 * @since 1.3.2
	 */
	public String getExt() {
		return Arch.getExt(this.path);
	}
	
	/**
	 * Imprime el contenido de un archivo
	 *
	 * @param path Path del archivo que se desea imprimir
	 * @since 1.5
	 */
	public static void print(Path path) {
		P.p(new Arch(path));
	}
	
	/**
	 * Imprime el contenido de un archivo
	 *
	 * @param path Cadena con el path del archivo que se desea imprimir
	 * @since 1.5
	 */
	public static void print(String path) {
		Arch.print(Paths.get(path));
	}
	
	/**
	 * Imprime el contenido del archivo
	 *
	 * @since 1.5
	 */
	public void print() {
		Arch.print(this.path);
	}
	
	/**
	 * Loggea información en un archivo
	 *
	 * @param log Información a loggear
	 * @return true => Correcto. false => error
	 */
	public boolean log(String log) {
		return this.escribir(log) && this.newLine();
	}
	
	/**
	 * Retorna la cantidad de lineas que tiene el archivo
	 *
	 * @return int Cantidad de lineas en el archivo
	 * @since 1.6
	 */
	public int getSizeInLines() {
		int size = 0;
		if (this.existe()) {
			this.initLineReader();
			while (this.leerLinea() != null) {
				size++;
			}
		}
		return size;
	}
	
	/**
	 * Retorna el contenido del archivo en un solo String.
	 *
	 * <pre>
	 * Debido a temas de memoria, no se recomienda imprimir directamente el objeto Arch.
	 * En su lugar, usar {@link #leerLinea()} o {@link #print()}
	 * </pre>
	 *
	 * @return String con el contenido del archivo
	 * @see {@link #leerLinea()}
	 * @see {@link #print()}
	 * @see {@link #print(Path)}
	 * @see {@link #print(String)}
	 * @since 1.0.0
	 * @deprecated
	 */
	@Deprecated
	public String toString() {
		String cad = "";
		for (int i = 0; i < this.getLineasArchivo().length; i++) {
			cad += this.getLineasArchivo()[i] + "\n";
		}
		return cad;
	}
}
