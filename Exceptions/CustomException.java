package Exceptions;

public class CustomException extends Exception {
	private String causeMessage;
	private String exceptionCode;
	
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
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
