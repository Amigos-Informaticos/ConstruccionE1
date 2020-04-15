package Exceptions;

public class CustomException extends Exception {
	private String causeMessage;
	
	
	public CustomException() {
		super("Custom Exception");
	}
	
	public CustomException(String exceptionMessage) {
		super(exceptionMessage);
		this.causeMessage = exceptionMessage;
	}
	
	public String getCauseMessage() {
		return causeMessage;
	}
	
	public void setCauseMessage(String causeMessage) {
		this.causeMessage = causeMessage;
	}
}
