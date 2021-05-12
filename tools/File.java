package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class File implements Serializable {
	private Path path = null;
	public BufferedReader bufferedReader = null;
	
	public File() {
	}
	
	public File(String path) {
		if (path != null && !path.equals("")) {
			this.path = Paths.get(path);
		}
	}
	
	@Deprecated
	public File(String path, boolean var) {
		this.path = Paths.get(path);
		if (var) {
			this.initLineReader();
		}
	}
	
	public File(Path path) {
		if (path != null && !path.toString().equals("")) {
			this.path = path;
		}
	}
	
	public File(File file) {
		if (file != null) {
			this.path = file.getPath();
		}
	}
	
	@Deprecated
	public File(Path path, boolean initLineReader) {
		this.path = path;
		if (initLineReader) {
			this.initLineReader();
		}
	}
	
	public void initLineReader() {
		try {
			this.bufferedReader = new BufferedReader(new FileReader(this.path.toFile()));
		} catch (Exception e) {
			P.err(e.getMessage());
		}
	}
	
	public void setPath(String path) {
		this.path = Paths.get(path);
	}
	
	public void setPath(Path path) {
		this.setPath(path.toString());
	}
	
	public String getStringPath() {
		return this.path.toString();
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public Path getParentPath() {
		assert this.path != null;
		return this.path.getParent();
	}
	
	public String getStringParentPath() {
		assert this.path != null;
		return this.path.getParent().toString();
	}
	
	public boolean create() {
		if (this.path != null) {
			return File.create(this.path.toString());
		}
		return false;
	}
	
	public static boolean create(String path) {
		return File.create(Paths.get(path));
	}
	
	public static boolean create(Path path) {
		boolean created = false;
		try {
			Files.createFile(path);
			created = true;
		} catch (IOException e) {
			P.err(e.getMessage());
		}
		return created;
	}
	
	@Deprecated
	public static boolean mkdir(String path) {
		return File.mkdir(Paths.get(path));
	}
	
	@Deprecated
	public static boolean mkdir(Path path) {
		boolean created = false;
		if (!File.exists(path)) {
			try {
				Dir.mkdir(path);
				created = true;
			} catch (Exception e) {
				P.err(e.getMessage());
			}
		}
		return created;
	}
	
	public static boolean delete(String sPath) {
		boolean deleted = false;
		Path path = Paths.get(sPath);
		try {
			Files.delete(path);
			deleted = true;
		} catch (IOException e) {
			P.err(e.getMessage());
		}
		return deleted;
	}
	
	public boolean delete() {
		return File.delete(this.getStringPath());
	}
	
	public boolean rename(String path) {
		return this.rename(Paths.get(path));
	}
	
	public boolean rename(Path path) {
		boolean renamed = false;
		if (this.path != null) {
			try {
				renamed = this.path.toFile().renameTo(path.toFile());
			} catch (Exception e) {
				P.err(e.getMessage());
			}
		}
		return renamed;
	}
	
	public static boolean exists(Path path) {
		return Files.exists(path);
	}
	
	public static boolean exists(String path) {
		return File.exists(Paths.get(path));
	}
	
	public boolean exists() {
		return File.exists(this.path);
	}
	
	public boolean write(String text) {
		return this.write(text, true);
	}
	
	public boolean write(String text, boolean append) {
		boolean wrote = false;
		if (this.path != null) {
			if (!this.exists()) {
				this.create();
			}
			try {
				BufferedWriter bufferedWriter =
					new BufferedWriter(new FileWriter(this.path.toFile(), append));
				bufferedWriter.write(text);
				bufferedWriter.flush();
				bufferedWriter.close();
				wrote = true;
			} catch (IOException e) {
				P.err(e.getMessage());
			}
		}
		return wrote;
	}
	
	public boolean newLine() {
		boolean inserted = false;
		if (this.path != null) {
			if (!this.exists()) {
				this.create();
			}
			try {
				BufferedWriter bufferedWriter =
					new BufferedWriter(new FileWriter(this.path.toFile(), true));
				bufferedWriter.newLine();
				bufferedWriter.flush();
				bufferedWriter.close();
				inserted = true;
			} catch (Exception e) {
				P.err(e.getMessage());
			}
		}
		return inserted;
	}
	
	@Deprecated
	public static String readAll(String path) {
		StringBuilder text = new StringBuilder();
		try {
			Reader inputReader = new InputStreamReader(new FileInputStream(path));
			int data = inputReader.read();
			while (data != -1) {
				text.append((char) data);
				data = inputReader.read();
			}
			inputReader.close();
		} catch (Exception e) {
			P.err(e.getMessage());
			text = null;
		}
		assert text != null;
		return text.toString();
	}
	
	@Deprecated
	public String readAll() {
		return File.readAll(this.path.toString());
	}
	
	@Deprecated
	public String[] getLines() {
		return File.getLines(this.path.toString());
	}
	
	@Deprecated
	public static String[] getLines(String path) {
		return File.readAll(path).split("\\r?\\n");
	}
	
	public String readLine() {
		String line = null;
		if (this.bufferedReader == null)
			this.initLineReader();
		try {
			line = this.bufferedReader.readLine();
		} catch (Exception e) {
			P.err(e.getMessage());
		}
		if (line == null) {
			this.bufferedReader = null;
		}
		return line;
	}
	
	public void modify(String original, String replace) {
		this.modify(original, replace, false);
	}
	
	public void modify(String original, String replace, boolean allOccurrences) {
		try {
			Files.createFile(Paths.get("a"));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path.toFile()));
			BufferedWriter bufferedWriter =
				new BufferedWriter(new FileWriter(Paths.get("a").toFile()));
			String line;
			boolean flag = true;
			boolean cont = true;
			while ((line = bufferedReader.readLine()) != null) {
				if (flag && line.equals(original)) {
					if (!cont) {
						bufferedWriter.newLine();
					}
					bufferedWriter.write(replace);
					if (!allOccurrences) {
						flag = false;
					}
				} else {
					if (!cont) {
						bufferedWriter.newLine();
					}
					bufferedWriter.write(line);
				}
				cont = false;
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			bufferedReader.close();
			Files.delete(this.path);
			java.io.File a = Paths.get("a").toFile();
			a.renameTo(this.path.toFile());
		} catch (Exception e) {
			P.err(e.getMessage());
		}
	}
	
	public static String getName(Path path) {
		return File.getName(path.toString());
	}
	
	public static String getName(File file) {
		if (file.exists()) {
			return file.getName();
		}
		return null;
	}
	
	public static String getName(String path) {
		if (File.exists(path)) {
			java.io.File aux = new java.io.File(path);
			return aux.getName();
		}
		return null;
	}
	
	public String getName() {
		if (this.exists()) {
			return File.getName(this.path);
		}
		return null;
	}
	
	public static String getNameNoExt(File file) {
		return file.getNameNoExt();
	}
	
	public static String getNameNoExt(String path) {
		return new File(path).getNameNoExt();
	}
	
	public String getNameNoExt() {
		if (this.exists()) {
			return this.getName().replaceFirst("[.][^.]+$", "");
		}
		return null;
	}
	
	public static String getExt(Path path) {
		return File.getExt(path.toString());
	}
	
	public static String getExt(File file) {
		return File.getExt(file.getName());
	}
	
	public static String getExt(String path) {
		if (File.exists(path)) {
			String reversed = Util.reverse(Objects.requireNonNull(File.getName(path)));
			StringBuilder returnValue = new StringBuilder();
			int i = 0;
			while (reversed.charAt(i) != '.') {
				returnValue.append(reversed.charAt(i));
				i++;
			}
			return Util.reverse(returnValue.toString());
		}
		return null;
	}
	
	public String getExt() {
		return File.getExt(this.path);
	}
	
	@Deprecated
	public static void print(Path path) {
		P.p(new File(path));
	}
	
	@Deprecated
	public static void print(String path) {
		File.print(Paths.get(path));
	}
	
	@Deprecated
	public void print() {
		File.print(this.path);
	}
	
	@Deprecated
	public boolean log(String log) {
		return this.write(log) && this.newLine();
	}
	
	public int getSizeInLines() {
		int size = 0;
		if (this.exists()) {
			this.initLineReader();
			while (this.readLine() != null) {
				size++;
			}
		}
		return size;
	}
	
	@Deprecated
	public String toString() {
		StringBuilder cad = new StringBuilder();
		for (int i = 0; i < this.getLines().length; i++) {
			cad.append(this.getLines()[i]).append("\n");
		}
		return cad.toString();
	}
}