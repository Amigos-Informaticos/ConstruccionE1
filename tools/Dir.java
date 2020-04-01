package tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase Dir
 * 
 * @author Edson Manuel Carballo Vera
 * @version 1.2.0
 */
public class Dir {
	private Path path = null;

	/**
	 * Constructor vacio
	 * 
	 * @since 1.0.0
	 */
	public Dir() {
	}

	/**
	 * Constructor con inicializador de Path
	 * 
	 * @param path Cadena con Path del archivo
	 * @since 1.0.0
	 */
	public Dir(String path) throws IllegalArgumentException {
		if (Arch.existe(path) && !Dir.isDir(path)) {
			throw new IllegalArgumentException("El path otorgado es de un archivo ya existente, no directorio");
		}
		this.path = Paths.get(path);
	}

	/**
	 * Setter del path del directorio
	 * 
	 * @param path Cadena con Path del directorio
	 * @since 1.0.0
	 */
	public void setPath(String path) {
		this.setPath(Paths.get(path));
	}

	/**
	 * Setter del path del directorio
	 * 
	 * @param arch Instancia de Arch con path del directorio
	 * @since 1.0.0
	 */
	public void setPath(Arch arch) {
		this.setPath(arch.getStringPath());
	}

	/**
	 * Setter del path del directorio
	 * 
	 * @param path Path del directorio
	 * @since 1.0.0
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Getter del Path del directorio
	 * 
	 * @return Path del directorio
	 * @since 1.0.0
	 */
	public Path getPath() {
		return this.path;
	}

	/**
	 * Getter del Path del directorio
	 * 
	 * @return Cadena con Path del directorio
	 * @since 1.0.0
	 */
	public String getStringPath() {
		return this.path.toString();
	}

	/**
	 * Metodo para crear un directorio a partir del Path de la instancia
	 * 
	 * <pre>
	 * Si la instancia no tiene un Path definido, retornara false
	 * y no se creara el directorio
	 * </pre>
	 * 
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean crear() {
		if (this.path != null && !Dir.existe(this.path)) {
			Dir.crear(this.path);
			return true;
		}
		return false;
	}

	/**
	 * Metodo para crear un directorio
	 * 
	 * @param path Path del directorio a crear
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean crear(Path path) {
		return Dir.crear(path.toString());
	}

	/**
	 * Metodo para crear un directorio
	 * 
	 * @param arch Instancia de Arch con path del directorio a crear
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean crear(Arch arch) {
		return Dir.crear(arch.getStringPath());
	}

	/**
	 * Metodo para crear un directorio
	 * 
	 * @param path Cadena con Path del directorio a crear
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean crear(String path) {
		if (Dir.existe(path)) {
			return false;
		}
		try {
			Files.createDirectory(Paths.get(path));
			return true;
		} catch (Exception e) {
			P.err("ERROR " + e);
			return false;
		}
	}

	/**
	 * Elimina el directorio
	 * 
	 * <pre>
	 * En caso de que el directorio tenga contenidos
	 * estos SERAN ELIMINADOS
	 * Solo funciona si la instancia tiene asignado un path
	 * Caso contrario retornara false
	 * </pre>
	 * 
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean delDir() {
		if (this.path != null) {
			return Dir.delDir(this.path);
		}
		return false;
	}

	/**
	 * Elimina un directorio
	 * 
	 * <pre>
	 * En caso de que el directorio tenga contenidos
	 * estos SERAN ELIMINADOS
	 * </pre>
	 * 
	 * @param path Path del directorio a eliminar
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean delDir(Path path) {
		return Dir.delDir(path.toString());
	}

	/**
	 * Elimina un directorio
	 * 
	 * <pre>
	 * En caso de que el directorio tenga contenidos
	 * estos SERAN ELIMINADOS
	 * </pre>
	 * 
	 * @param arch Instancia de Arch con el path del directorio a eliminar
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean delDir(Arch arch) {
		return Dir.delDir(arch.getStringPath());
	}

	/**
	 * Elimina un directorio
	 * 
	 * <pre>
	 * En caso de que el directorio tenga contenidos
	 * estos SERAN ELIMINADOS
	 * </pre>
	 * 
	 * @param path Cadena con Path del directorio a eliminar
	 * @return true => Success. false => Failure
	 * @since 1.0.0
	 */
	public static boolean delDir(String path) {
		if (Dir.existe(path)) {
			try {
				File index = new File(path);
				String[] entries = index.list();
				for (String s : entries) {
					File currentFile = new File(index.getPath(), s);
					currentFile.delete();
				}
				index.delete();
				return true;
			} catch (Exception e) {
				P.err("ERROR " + e);
				return false;
			}
		}
		return false;
	}

	/**
	 * Metodo para saber si un directorio existe
	 * 
	 * @param path Cadena con Path del directorio
	 * @return true => Existe el directorio. false => No existe el directorio
	 * @since 1.0.0
	 */
	public static boolean existe(Path path) {
		return Dir.existe(path.toString());
	}

	/**
	 * Metodo para saber si un directorio existe
	 * 
	 * @param arch Instancia de Arch con path del directorio
	 * @return true => Existe el directorio. false => No existe el directorio
	 * @since 1.0.0
	 */
	public static boolean existe(Arch arch) {
		return Dir.existe(arch.getStringPath());
	}

	/**
	 * Metodo para saber si un directorio existe
	 * 
	 * @param path Cadena con Path del directorio
	 * @return true => Existe el directorio. false => No existe el directorio
	 * @since 1.0.0
	 */
	public static boolean existe(String path) {
		return Arch.existe(path) && Dir.isDir(path);
	}

	/**
	 * Metodo para saber si un directorio existe
	 * 
	 * @return true => Existe el directorio. false => No existe el directorio
	 * @since 1.0.0
	 */
	public boolean existe() {
		return Dir.existe(this.path);
	}

	/**
	 * Metodo para saber si un Path representa un archivo o un directorio
	 * 
	 * @param path Path del archivo
	 * @return true => Es un directorio. false => Es un archivo
	 * @since 1.0.0
	 */
	public static boolean isDir(Path path) {
		return Dir.isDir(path.toString());
	}

	/**
	 * Metodo para saber si un Path representa un archivo o un directorio
	 * 
	 * @param arch Instancia de Arch con Path del archivo
	 * @return true => Es un directorio. false => Es un archivo
	 * @since 1.0.0
	 */
	public static boolean isDir(Arch arch) {
		if (arch.getPath() == null) {
			return false;
		}
		return Dir.isDir(arch.getStringPath());
	}

	/**
	 * Metodo para saber si un Path representa un archivo o un directorio
	 * 
	 * @param path Cadena con Path del archivo
	 * @return true => Es un directorio. false => Es un archivo
	 * @since 1.0.0
	 */
	public static boolean isDir(String path) {
		return new File(path).isDirectory();
	}

	/**
	 * Retorna un arreglo de Arch con los archivos dentro del directorio
	 * 
	 * @param path Path del directorio del cual se desea conocer los archivos
	 * @return Arraglo de Arch
	 * @since 1.2.0
	 */
	public static Arch[] ls(Path path) {
		return Dir.ls(path.toString());
	}

	/**
	 * Retorna un arreglo de Arch con los archivos dentro del directorio
	 * 
	 * @param dir Instancia de tipo Dir de la que se desea obtener los archivos
	 * @return Arreglo de Arch
	 * @since 1.2.0
	 */
	public static Arch[] ls(Dir dir) {
		return Dir.ls(dir.getStringPath());
	}

	/**
	 * Retorna un arreglo de Arch con los archivos dentro del directorio
	 * 
	 * @param path String con path del directorio del que se desea obtener los
	 *             archivos
	 * @return Arreglo de Arch
	 * @since 1.2.0
	 */
	public static Arch[] ls(String path) {
		if (Dir.existe(path)) {
			try {
				Arch[] files;
				File index = new File(path);
				files = new Arch[index.list().length];
				String[] entry = index.list();
				for (int i = 0; i < entry.length; i++) {
					files[i] = new Arch(new File(index.getPath(), entry[i]).getPath());
				}
				return files;
			} catch (Exception e) {
				P.err(e);
				return null;
			}
		}
		return null;
	}

	/**
	 * Retorna un arreglo de Arch con los archivos dentro del directorio
	 * 
	 * @return Arreglo de Arch
	 */
	public Arch[] ls() {
		return Dir.ls(this.path);
	}
}