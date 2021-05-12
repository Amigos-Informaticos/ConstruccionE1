package tools;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramBot {
	private String url = "https://api.telegram.org/bot1098401798:AAEycvrpsUUIUb0oOcUO-" +
		"_tGsvlfJEK8dVg/sendMessage?chat_id=@";
	private String group;
	private String message = "";
	
	public TelegramBot(String groupId) {
		this.group = groupId;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addMessage(String message) {
		this.message += message;
		this.message += "\n";
	}
	
	public void send() {
		try {
			String completeURL = this.url + this.group + "&text=" +
				URLEncoder.encode(this.message, StandardCharsets.UTF_8.toString());
			new URL(completeURL).openConnection().getInputStream();
		} catch (IOException e) {
			new Logger().log(e, false);
		} finally {
			this.message = "";
		}
	}
}