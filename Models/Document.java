package Models;

import DAO.DAODocument;
import tools.File;

import java.util.Date;

public class Document {
	private String title;
	private String type;
	private Date date;
	private File file;
	private User author;
	
	public Document(String title, String type, Date date, File file, User author) {
		this.title = title;
		this.type = type;
		this.date = date;
		this.file = file;
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public File getFile() {
		return file;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public boolean isComplete() {
		return this.title != null &&
			this.type != null &&
			this.date != null;
	}
	
	public boolean save() {
		return new DAODocument(this).save(this.author.getEmail());
	}
}
