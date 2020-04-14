package tools;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		this.initialSetup(true);
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
	
	public void initialSetup(boolean mantenerSesionPrevia) {
		this.init();
		if (!mantenerSesionPrevia) {
			if (this.log.existe()) {
				this.log.delArchivo();
			}
			this.log.crear();
		}
	}
	
	public void initTicker() {
		Runtime.getRuntime().addShutdownHook(
			new Thread() {
				@Override
				public void run() {
					P.p("Bye");
				}
			}
		);
	}
	
	/**
	 * Inicializador auxiliar de archivo
	 */
	public void init() {
		if (this.log == null) {
			this.log = new Arch("log.txt");
		}
	}
	
	/**
	 * Escribe el texto en el log
	 *
	 * @param texto Cadena a loggear
	 */
	public void log(String texto) {
		this.log.escribir(texto);
		this.log.newLine();
		String url = "https://api.telegram.org/bot1098401798:AAEycvrpsUUIUb0oOcUO-" +
			"_tGsvlfJEK8dVg/sendMessage?chat_id=@W3Log&text=";
		url += texto;
		try {
			new URL(url).openConnection().getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Escribe la excepcion en el log
	 *
	 * @param excepcion Instancia de excepcion que se desea loggear
	 */
	public void log(Exception excepcion) {
		if (excepcion.getMessage() != null) {
			try {
				String url = "https://api.telegram.org/bot1098401798:AAEycvrpsUUIUb0oOcUO-" +
					"_tGsvlfJEK8dVg/sendMessage?chat_id=@W3Log&text=";
				DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				this.log.escribir(date.format(LocalDateTime.now()) + "\t");
				url += date.format(LocalDateTime.now()) + "%0A";
				
				this.log.escribir(excepcion.getMessage());
				url += excepcion.getMessage() + "%0A";
				
				this.log.newLine();
				for (StackTraceElement element: excepcion.getStackTrace()) {
					this.log.escribir(element.toString());
					this.log.newLine();
					url += element.toString() + "%0A";
				}
				P.pln(url);
				this.log.newLine();
				new URL(url).openConnection().getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int getCurrentLine() {
		boolean thisOne = false;
		int thisOneCountDown = 1;
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement element: elements) {
			String methodName = element.getMethodName();
			int lineNum = element.getLineNumber();
			if (thisOne && (thisOneCountDown == 0)) {
				return lineNum;
			} else if (thisOne) {
				thisOneCountDown--;
			}
			if (methodName.equals("getCurrentLine")) {
				thisOne = true;
			}
		}
		return -1;
	}
	
	private static String getCurrentMethod() {
		return new Throwable().getStackTrace()[2].getMethodName();
	}
	
	private static String getCurrentClass() {
		return "";
	}
}
