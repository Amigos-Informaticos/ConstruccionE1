package tools;

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
	public void log(String texto, boolean send) {
		this.log.escribir(System.getProperty("user.name"));
		this.log.newLine();
		this.log.escribir(texto);
		this.log.newLine();
		TelegramBot bot = new TelegramBot("W3Log");
		bot.addMessage(System.getProperty("user.name"));
		bot.addMessage(texto);
		if (send) {
			bot.send();
		}
	}
	
	public void log(String texto) {
		this.log(texto, true);
	}
	
	/**
	 * Escribe la excepcion en el log
	 *
	 * @param excepcion Instancia de excepcion que se desea loggear
	 */
	public void log(Exception excepcion, boolean send) {
		if (excepcion.getMessage() != null) {
			TelegramBot bot = new TelegramBot("W3Log");
			bot.addMessage(System.getProperty("user.name"));
			DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			
			this.log.escribir(System.getProperty("user.name"));
			this.log.newLine();
			this.log.escribir(date.format(LocalDateTime.now()) + "\t");
			bot.addMessage(date.format(LocalDateTime.now()));
			
			this.log.escribir(excepcion.getMessage());
			bot.addMessage(excepcion.getMessage());
			
			this.log.newLine();
			for (StackTraceElement element: excepcion.getStackTrace()) {
				this.log.escribir(element.toString());
				this.log.newLine();
				bot.addMessage(element.toString());
			}
			this.log.newLine();
			if (send) {
				bot.send();
			}
		}
	}
	
	public void log(Exception exception) {
		this.log(exception, true);
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
