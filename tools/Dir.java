package tools;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Dir {
	private Path path = null;
	
	public Dir() {
	}
	
	public Dir(String path) throws IllegalArgumentException {
		if (File.exists(path) && !Dir.isDir(path)) {
			throw new IllegalArgumentException("Provided path is a file, not a directory");
		}
		this.path = Paths.get(path);
	}
	
	public void setPath(String path) {
		this.setPath(Paths.get(path));
	}
	
	public void setPath(File file) {
		this.setPath(file.getStringPath());
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public String getStringPath() {
		return this.path.toString();
	}
	
	public Path getParentPath() {
		return this.path.getParent();
	}
	
	public String getStringParentPath() {
		return this.path != null && this.path.getParent() != null ?
			this.path.getParent().toString() : "";
	}
	
	public boolean mkdir() {
		if (this.path != null && !Dir.exists(this.path)) {
			Dir.mkdir(this.path);
			return true;
		}
		return false;
	}
	
	public static boolean mkdir(Path path) {
		return Dir.mkdir(path.toString());
	}
	
	public static boolean mkdir(File file) {
		return Dir.mkdir(file.getStringPath());
	}
	
	public static boolean mkdir(String path) {
		if (new Dir(path).getStringParentPath().equals("")) {
			return true;
		} else {
			return new Dir(new Dir(path).getStringParentPath()).mkdir() &&
				new java.io.File(path).mkdirs();
		}
	}
	
	public boolean delete() {
		if (this.path != null) {
			return Dir.delete(this.path);
		}
		return false;
	}
	
	public static boolean delete(Path path) {
		return Dir.delete(path.toString());
	}
	
	public static boolean delete(File file) {
		return Dir.delete(file.getStringPath());
	}
	
	public static boolean delete(String path) {
		boolean deleted = false;
		if (Dir.exists(path)) {
			try {
				java.io.File index = new java.io.File(path);
				String[] entries = index.list();
				if (entries != null) {
					for (String entry: entries) {
						java.io.File currentFile = new java.io.File(index.getPath(), entry);
						currentFile.delete();
					}
				}
				index.delete();
				deleted = true;
			} catch (Exception e) {
				P.err(e);
			}
		}
		return deleted;
	}
	
	public static boolean exists(Path path) {
		return Dir.exists(path.toString());
	}
	
	public static boolean exists(File file) {
		return Dir.exists(file.getStringPath());
	}
	
	public static boolean exists(String path) {
		return File.exists(path) && Dir.isDir(path);
	}
	
	public boolean exists() {
		return Dir.exists(this.path);
	}
	
	public static boolean isDir(Path path) {
		return Dir.isDir(path.toString());
	}
	
	public static boolean isDir(File file) {
		boolean isDir = false;
		if (file.getPath() != null) {
			isDir = Dir.isDir(file.getStringPath());
		}
		return isDir;
	}
	
	public static boolean isDir(String path) {
		return new java.io.File(path).isDirectory();
	}
	
	public static File[] ls(Path path) {
		return Dir.ls(path.toString());
	}
	
	public static File[] ls(Dir dir) {
		return Dir.ls(dir.getStringPath());
	}
	
	public static File[] ls(String path) {
		File[] files = null;
		if (Dir.exists(path)) {
			try {
				java.io.File index = new java.io.File(path);
				files = new File[Objects.requireNonNull(index.list()).length];
				String[] entry = index.list();
				for (int i = 0; i < Objects.requireNonNull(entry).length; i++) {
					files[i] = new File(new java.io.File(index.getPath(), entry[i]).getPath());
				}
				return files;
			} catch (Exception e) {
				P.err(e);
			}
		}
		return files;
	}
	
	public File[] ls() {
		return Dir.ls(this.path);
	}
}