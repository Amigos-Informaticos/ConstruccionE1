package tools;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase Bin
 * @author Edson Manuel Carballo Vera
 * @version 1.0.0
 */
public class Bin {
    private Path path = null;
    private ObjectInputStream reader = null;

    /**
     * Constructor vacio
     * @since 1.0.0
     */
    public Bin() {
    }

    /**
     * Constructor con inicializador de Path
     * @param path Path en cadena
     * @since 1.0.0
     */
    public Bin(String path) {
        this.path = Paths.get(path);
    }

    /**
     * Constructor con inicializador de Path
     * @param path Path para inicializar
     * @since 1.1.0
     */
    public Bin(Path path) {
        this.path = path;
    }

    /**
     * Inicializador de lector
     * <pre>
     * Ubica el puntero del lector al inicio del archivo.
     * </pre
     * @return
     */
    public boolean initReader() {
        try {
			if (this.path == null) {
				P.err("El Path no esta definido");
                return false;
            }
            this.reader = new ObjectInputStream(new FileInputStream(this.path.toFile()));
            return true;
        } catch (Exception e) {
            P.err(e);
            return false;
        }
    }

    /**
     * Setter del path
     * <pre>
     * Establece el path del archivo
     * Recibe un Path en cadena para establecer el Path de la instancia.
     * </pre>
     * @param path El path en cadena
     * @since 1.0.0
     */
    public void setPath(String path) {
        this.path = Paths.get(path);
    }

    /**
     * Setter del path
     * <pre>
     * Establece el path del archivo
     * Recibe un objeto tipo Path para establecer el Path de la instancia.
     * </pre>
     * @param path El path a establecer
     * @since 1.0.1
     */
    public void setPath(Path path) {
        this.path = path;
    }
    
    /**
     * Getter del Path
     * <pre>
     * Retorna el Path de la instancia casteado como String
     * Si la instancia no tiene un path definido, retorna null
     * </pre>
     * @return Path en cadena
     * @since 1.0.0
     */
    public String getStringPath() {
        return this.path.toString();
    }

    /**
     * Getter del Path
     * <pre>
     * Retorna el Path de la instancia
     * Si la instancia no tiene un path definido, retorna null
     * </pre>
     * @return Path de la instancia
     * @since 1.0.1
     */
    public Path getPath() {
        return this.path;
    }
    
    /**
     * Verifica si el archivo con el Path de la instancia existe.
     * <pre>
     * Si el archivo con el Path de la instancia existe, retorna true.
     * Caso contrario, devuelve false.
     * Si se desea verificar algun archivo con un Path distinto al de la instancia, utilizar {@link Arch#existe(Path)}
     * </pre>
     * @return Boolean. true => Existe. false => No existe
     * @see {@link Arch#existe(Path)}
     * @see {@link Arch#existe(String)}
     * @since 1.0.1
     */
    public boolean existe() {
        if (this.path != null) {
            return Files.exists(this.path);
        }
        P.err("El path no esta definido");
        return false;
    }

    /**
	 * Lee un Int del archivo
	 * <pre>
	 * Retorna un Int del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Int.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara un -1.
	 * </pre>
	 * @return Int leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
    public int leerInt() {
        if (this.reader == null) {
            this.initReader();
        }
        try {
            return this.reader.readInt();
        } catch (IOException e) {
            P.err(e);
            return -1;
        }
    }

    /**
	 * Escribe un Int en el archivo.
	 * <pre>
	 * Escribe un Int en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Int que desea escribir.
	 * </pre>
	 * @param v Int que desea escribir
	 * @see {@link #escribirInt(int, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirInt(int v) {
		return this.escribirInt(v, true);
    }

    /**
     * Escribe un Int en el archivo.
     * <pre>
     * Escribe un Int en el archivo.
     * Si se desea aniadir al final del archivo, establecer el ultimo parametro a true.
     * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
     * </pre>
     * @param v Int que se desea escribir
     * @param var Boolean. true => Aniadir al final. false => Sobreescribir
     * @return Boolean. true => Success. false => Failure
     * @since 1.0.0
     */
    public boolean escribirInt(int v, boolean var) {
        if (this.path != null) {
            try {
                DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
                w.writeInt(v);
                w.flush();
                w.close();
                return true;
            } catch (IOException e) {
                P.err(e);
            }
        }
        P.err("El Path no esta definido");
        return false;
    }

	/**
	 * Lee un Float del archivo
	 * <pre>
	 * Retorna un Float del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Float.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara -1.
	 * </pre>
	 * @return Float leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public float leerFloat() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readFloat();
		} catch (IOException e) {
			P.err(e);
			return -1;
		}
	}

	/**
	 * Escribe un Float en el archivo.
	 * <pre>
	 * Escribe un Float en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Float que desea escribir.
	 * </pre>
	 * @param v Float que desea escribir
	 * @see {@link #escribirFloat(float, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirFloat(float v) {
		return this.escribirFloat(v, true);
	}

	/**
	 * Escribe un Float en el archivo.
	 * <pre>
	 * Escribe un Float en el archivo.
	 * Si se desea aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Float que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirFloat(float v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeFloat(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Double del archivo
	 * <pre>
	 * Retorna un Double del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Double.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara -1.
	 * </pre>
	 * @return Double leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public double leerDouble() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readDouble();
		} catch (IOException e) {
			P.err(e);
			return -1;
		}
	}

	/**
	 * Escribe un Double en el archivo.
	 * <pre>
	 * Escribe un Double en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Double que desea escribir.
	 * </pre>
	 * @param v Double que desea escribir
	 * @see {@link #escribirDouble(double, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirDouble(double v) {
		return this.escribirDouble(v, true);
	}

	/**
	 * Escribe un Double en el archivo.
	 * <pre>
	 * Escribe un Double en el archivo.
	 * Si se desea aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Double que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirDouble(double v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeDouble(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Boolean del archivo
	 * <pre>
	 * Retorna un Boolean del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Boolean.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara false.
	 * </pre>
	 * @return Boolean leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public boolean leerBool() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readBoolean();
		} catch (IOException e) {
			P.err(e);
			return false;
		}
	}

	/**
	 * Escribe un Boolean en el archivo.
	 * <pre>
	 * Escribe un Boolean en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Boolean que desea escribir.
	 * </pre>
	 * 
	 * @param v Boolean que desea escribir
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirBool(boolean v) {
		return this.escribirBool(v, true);
	}

	/**
	 * Escribe un Boolean en el archivo.
	 * <pre>
	 * Escribe un Boolean en el archivo.
	 * Si se desea aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Boolean que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirBool(boolean v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeBoolean(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Char del archivo
	 * <pre>
	 * Retorna un Char del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Char.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara \n.
	 * </pre>
	 * @return Char leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public char leerChar() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readChar();
		} catch (IOException e) {
			P.err(e);
			return '\n';
		}
	}

	/**
	 * Escribe un Char en el archivo.
	 * <pre>
	 * Escribe un Char en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Char que desea escribir.
	 * </pre>
	 * @param v Char que desea escribir
	 * @see {@link #escribirChar(char, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirChar(char v) {
		return this.escribirChar(v, true);
	}

	/**
	 * Escribe un Char en el archivo.
	 * <pre>
	 * Escribe un Char en el archivo.
	 * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Char que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirChar(char v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeChar(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}
	
	/**
	 * Escribe una serie de Chars en el archivo.
	 * <pre>
	 * Escribe una serie de Char en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Int que desea escribir.
	 * </pre>
	 * @param v Int que desea escribir
	 * @see {@link #escribirChars(String, boolean)}
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirChars(String v) {
		return this.escribirChars(v, true);
	}

	/**
	 * Escribe una serie de Chars en el archivo.
	 * <pre>
	 * Escribe una serie de Chars en el archivo.
	 * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Int que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirChars(String v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeChars(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Long del archivo
	 * <pre>
	 * Retorna un Long del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Long.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara -1.
	 * </pre>
	 * @return Long leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public long leerLong() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readLong();
		} catch (IOException e) {
			P.err(e);
			return -1;
		}
	}

	/**
	 * Escribe un Long en el archivo.
	 * <pre>
	 * Escribe un Long en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Long que desea escribir.
	 * </pre>
	 * @param v Long que desea escribir
	 * @see {@link #escribirLong(long, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirLong(long v) {
		return this.escribirLong(v, true);
	}

	/**
	 * Escribe un Long en el archivo.
	 * <pre>
	 * Escribe un Long en el archivo.
	 * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Long que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirLong(long v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeLong(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Short del archivo
	 * <pre>
	 * Retorna un Short del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Short.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara -1.
	 * </pre>
	 * @return Short leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public short leerShort() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readShort();
		} catch (IOException e) {
			P.err(e);
			return -1;
		}
	}

	/**
	 * Escribe un Short en el archivo.
	 * <pre>
	 * Escribe un Short en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Short que desea escribir.
	 * </pre>
	 * @param v Short que desea escribir
	 * @see {@link #escribirShort(short, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirShort(short v) {
		return this.escribirShort(v, true);
	}

	/**
	 * Escribe un Short en el archivo.
	 * 
	 * <pre>
	 * Escribe un Short en el archivo.
	 * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * 
	 * @param v Short que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirShort(short v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeShort(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un String del archivo
	 * <pre>
	 * Retorna un String del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como String.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara null.
	 * </pre>
	 * @return String leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public String leerUTF() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readUTF();
		} catch (IOException e) {
			P.err(e);
			return null;
		}
	}
	
	/**
	 * Escribe un String en el archivo.
	 * <pre>
	 * Escribe un String en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del String que desea escribir.
	 * </pre>
	 * @param v String que desea escribir
	 * @see {@link #escribirUTF(String, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirUTF(String v) {
		return this.escribirUTF(v, true);
	}

	/**
	 * Escribe un Sring en el archivo.
	 * <pre>
	 * Escribe un Sring en el archivo.
	 * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
	 * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
	 * </pre>
	 * @param v   Sring que se desea escribir
	 * @param var Boolean. true => Aniadir al final. false => Sobreescribir
	 * @return Boolean. true => Success. false => Failure
	 * @since 1.0.0
	 */
	public boolean escribirUTF(String v, boolean var) {
		if (this.path != null) {
			try {
				DataOutputStream w = new DataOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeUTF(v);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
	}

	/**
	 * Lee un Object del archivo
	 * <pre>
	 * Retorna un Object del archivo.
	 * Lee los siguientes bytes del archivo desde la ultima posicion del puntero.
	 * Retorna lo leido casteado como Object.
	 * En caso no haber inicializado antes el lector, se inicializara y se ubicara el puntero al inicio del archivo.
	 * En caso de que ocurra algun error, se retornara null.
	 * </pre>
	 * @return Object leido desde la ultima ubicacion del puntero en el archivo.
	 * @see {@link #initReader()}
	 * @since 1.0.0
	 */
	public Object leerObject() {
		if (this.reader == null) {
			this.initReader();
		}
		try {
			return this.reader.readObject();
		} catch (Exception e) {
			P.err(e);
			return null;
		}
	}

	/**
	 * Escribe un Object en el archivo.
	 * <pre>
	 * Escribe un Object en el archivo.
	 * Escribe al final del archivo.
	 * Si se desea sobreescribir la informacion del archivo, aniadir un false despues del Object que desea escribir.
	 * </pre>
	 * @param o Object que desea escribir
	 * @see {@link #escribirObject(Object, boolean)}
	 * @return Boolean. true => Success. false => Failure.
	 * @since 1.0.0
	 */
	public boolean escribirObject(Object o) {
		return this.escribirObject(o, true);
	}
	
	/**
     * Escribe un Object en el archivo.
     * <pre>
     * Escribe un Object en el archivo.
     * Si se desea escribir aniadir al final del archivo, establecer el ultimo parametro a true.
     * Si se desea sobreescribir la informacion del archivo, establecer el ultimo parametro a false.
     * </pre>
     * @param o Object que se desea escribir
     * @param var Boolean. true => Aniadir al final. false => Sobreescribir
     * @return Boolean. true => Success. false => Failure
     * @since 1.0.0
     */
	public boolean escribirObject(Object o, boolean var) {
		if (this.path != null) {
			try {
				ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(this.path.toFile(), var));
				w.writeObject(o);
				w.flush();
				w.close();
				return true;
			} catch (IOException e) {
				P.err(e);
			}
		}
		P.err("El Path no esta definido");
		return false;
    }
}