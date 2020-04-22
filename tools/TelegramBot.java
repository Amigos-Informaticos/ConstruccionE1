package tools;

import java.io.IOException;
import java.net.URL;

public class TelegramBot {
	private String url = "https://api.telegram.org/bot1098401798:AAEycvrpsUUIUb0oOcUO-" +
		"_tGsvlfJEK8dVg/sendMessage?chat_id=@";
	private String group;
	private String message = "";
	
	public TelegramBot(String groupId) {
		this.group = groupId;
	}
	
	public void setMessage(String message) {
		this.message = message + "%0A";
	}
	
	public void addMessage(String message) {
		this.message += message + "%0A";
	}
	
	public void send() {
		try {
			String completeURL = this.url + this.group + "&text=" + this.message;
			new URL(completeURL).openConnection().getInputStream();
		} catch (IOException e) {
			new Logger().log(e, false);
		} finally {
			this.message = "";
		}
	}
}
