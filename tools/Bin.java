package tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Deprecated
public class Bin {
    private Path path = null;
    private ObjectInputStream reader = null;
	
	
	public Bin() {
    }
	
	
	public Bin(String path) {
        this.path = Paths.get(path);
    }
	
	
	public Bin(Path path) {
        this.path = path;
    }
	
	
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
	
	
	public void setPath(String path) {
        this.path = Paths.get(path);
    }
	
	
	public void setPath(Path path) {
        this.path = path;
    }
	
	
	public String getStringPath() {
        return this.path.toString();
    }
	
	
	public Path getPath() {
        return this.path;
    }
	
	
	public boolean existe() {
        if (this.path != null) {
            return Files.exists(this.path);
        }
        P.err("El path no esta definido");
        return false;
    }
	
	
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
	
	
	public boolean escribirInt(int v) {
		return this.escribirInt(v, true);
    }
	
	
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
	
	
	public boolean escribirFloat(float v) {
		return this.escribirFloat(v, true);
	}
	
	
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
	
	
	public boolean escribirDouble(double v) {
		return this.escribirDouble(v, true);
	}
	
	
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
	
	
	public boolean escribirBool(boolean v) {
		return this.escribirBool(v, true);
	}
	
	
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
	
	
	public boolean escribirChar(char v) {
		return this.escribirChar(v, true);
	}
	
	
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
	
	
	public boolean escribirChars(String v) {
		return this.escribirChars(v, true);
	}
	
	
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
	
	
	public boolean escribirLong(long v) {
		return this.escribirLong(v, true);
	}
	
	
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
	
	
	public boolean escribirShort(short v) {
		return this.escribirShort(v, true);
	}
	
	
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
	
	
	public boolean escribirUTF(String v) {
		return this.escribirUTF(v, true);
	}
	
	
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
	
	
	public boolean escribirObject(Object o) {
		return this.escribirObject(o, true);
	}
	
	
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