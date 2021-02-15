package tools;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {
	private File log = null;
	
	public Logger() {
		this.initialSetup(true);
	}
	
	public Logger(File destine, boolean keepLastSession) {
		this.log = destine;
		this.initialSetup(keepLastSession);
	}
	
	public Logger(String path, boolean keepLastSession) {
		this.log = new File(path);
		this.initialSetup(keepLastSession);
	}
	
	public Logger(Path path, boolean keepLastSession) {
		this.log = new File(path);
		this.initialSetup(keepLastSession);
	}
	
	public void initialSetup(boolean keepLastSession) {
		this.init();
		if (!keepLastSession) {
			if (this.log.exists()) {
				this.log.delete();
			}
			this.log.create();
		}
	}
	
	public void init() {
		if (this.log == null) {
			this.log = new File("log");
		}
	}
	
	public void log(String text, boolean send) {
		this.log.write(System.getProperty("user.name"));
		this.log.newLine();
		this.log.write(text);
		this.log.newLine();
		TelegramBot bot = new TelegramBot("W3Log");
		bot.addMessage(System.getProperty("user.name"));
		bot.addMessage(text);
		if (send) {
			bot.send();
		}
	}
	
	public void log(String text) {
		this.log(text, true);
	}
	
	public void log(Exception exception, boolean send) {
		if (exception.getMessage() != null) {
			TelegramBot bot = new TelegramBot("W3Log");
			bot.addMessage(System.getProperty("user.name"));
			DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			
			this.log.write(System.getProperty("user.name"));
			this.log.newLine();
			this.log.write(date.format(LocalDateTime.now()) + "\t");
			bot.addMessage(date.format(LocalDateTime.now()));
			
			this.log.write(exception.getMessage());
			bot.addMessage(exception.getMessage());
			
			this.log.newLine();
			for (StackTraceElement element: exception.getStackTrace()) {
				this.log.write(element.toString());
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
	
	public static void staticLog(String text, boolean send) {
		new Logger().log(text, send);
	}
	
	public static void staticLog(String text) {
		new Logger().log(text);
	}
	
	public static void staticLog(Exception exception, boolean send) {
		new Logger().log(exception, send);
	}
	
	public static void staticLog(Exception exception) {
		new Logger().log(exception);
	}
}
