package Exceptions;

import tools.Array;

public class CustomException extends Exception {
	private String causeMessage;
	private String exceptionCode;
	private Array codes = new Array();
	
	public CustomException() {
		super("Custom Exception");
	}
	
	public CustomException(String exceptionMessage) {
		super(exceptionMessage);
		this.causeMessage = exceptionMessage;
	}
	
	public CustomException(String exceptionMessage, String exceptionCode) {
		super(exceptionMessage);
		this.causeMessage = exceptionMessage;
		this.exceptionCode = exceptionCode;
	}
	
	public CustomException(String exceptionMessage, String... exceptionCodes) {
		super(exceptionMessage);
		this.causeMessage = exceptionMessage;
		this.addCodes(exceptionCodes);
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
	
	public void addCode(String code) {
		this.codes.add(code);
	}
	
	public void addCodes(String... exceptionCodes) {
		for (String code: exceptionCodes) {
			this.addCode(code);
		}
	}
	
	public String[] getCodes() {
		String[] returnCodes = new String[this.codes.len()];
		for (int i = 0; i < this.codes.len(); i++) {
			returnCodes[i] = this.codes.getString(i);
		}
		return returnCodes;
	}
}
