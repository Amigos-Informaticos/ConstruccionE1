package tools;

import java.nio.file.Path;

/**
 * @author Edson Manuel Carballo Vera
 * @version 1.0
 */
public class Logger {
	private Arch log = null;

	/**
	 * Constructor vacio
	 */
	public Logger() {
	}

	/**
	 * Constructor con descriptor de destino
	 *
	 * @param destino Instancia de Arch con path de destino del log
	 * @since 1.0
	 */
	public Logger(Arch destino, boolean mantenerSesionPrevia) {
		this.log = destino;
		this.initialSetup(mantenerSesionPrevia);
	}

	/**
	 * Constructor con string de destino
	 *
	 * @param pathDestino Cadena con path de destino
	 * @since 1.0
	 */
	public Logger(String pathDestino, boolean mantenerSesionPrevia) {
		this.log = new Arch(pathDestino);
		this.initialSetup(mantenerSesionPrevia);
	}

	/**
	 * Constructor con Path de destino
	 *
	 * @param destino Path del destino
	 * @since 1.0
	 */
	public Logger(Path destino, boolean mantenerSesionPrevia) {
		this.log = new Arch(destino);
		this.initialSetup(mantenerSesionPrevia);
	}

	public void initialSetup(boolean mantenerSesionPrevia){
		if(!mantenerSesionPrevia){
			this.log.delArchivo();
			this.log.crear();
		}
		this.initTicker();
		this.init();
	}

	public void initTicker() {
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				P.p("Bye");
			}
		});
	}

	/**
	 * Inicializador auxiliar de archivo
	 */
	public void init() {
		if (this.log == null) {
			this.log = new Arch("../log.txt");
		}
	}

	/**
	 * Escribe el texto en el log
	 *
	 * @param texto       Cadena a loggear
	 * @param descripcion true => Escribir la linea en que se ejecuta. false => No escribir la linea
	 */
	public void log(String texto, boolean descripcion) {
		if (descripcion) {
			this.log.escribir(String.valueOf(___8drrd3148796d_Xaf()));
			this.log.escribir("\t\t");
		}
		this.log.escribir(texto);
		this.log.newLine();
	}

	/**
	 * Escribe el texto en el log
	 *
	 * @param texto Cadena a loggear
	 */
	public void log(String texto) {
		this.log(texto, false);
	}

	private static int ___8drrd3148796d_Xaf() {
		boolean thisOne = false;
		int thisOneCountDown = 1;
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : elements) {
			String methodName = element.getMethodName();
			int lineNum = element.getLineNumber();
			if (thisOne && (thisOneCountDown == 0)) {
				return lineNum;
			} else if (thisOne) {
				thisOneCountDown--;
			}
			if (methodName.equals("___8drrd3148796d_Xaf")) {
				thisOne = true;
			}
		}
		return -1;
	}

}
