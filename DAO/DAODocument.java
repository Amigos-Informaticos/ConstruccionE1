package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import Models.Document;
import Models.Student;
import tools.Dir;
import tools.File;
import tools.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAODocument {
	private final Document document;
	private final DBConnection connection = new DBConnection();
	
	public DAODocument(Document document) {
		this.document = document;
	}
	
	public boolean save(String authorEmail) {
		assert this.document != null : "Document is null: DAODocument.save()";
		assert this.document.isComplete() : "Document is incomplete: DAODocument.save()";
		assert this.document.getFile().exists() : "File doesnt exists: DAODocument.save()";
		
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM MiembroFEI " +
			"WHERE correoElectronico = ?";
		String[] values = { authorEmail };
		String[] names = { "TOTAL" };
		assert this.connection.select(query, values, names)[0][0].equals("1");
		boolean saved = false;
		
		query = "SELECT COUNT(titulo) AS TOTAL FROM Documento " +
			"WHERE titulo LIKE CONCAT(?, '%')";
		values = new String[]{ this.document.getTitle() };
		int occurrences = Integer.parseInt(this.connection.select(query, values, names)[0][0]);
		String fullTitle = occurrences > 0 ?
			this.document.getTitle() + "(" + occurrences + ")" :
			this.document.getTitle();
		
		query = "INSERT INTO Documento (titulo, fecha, tipo, archivo, autor, path) " +
			"VALUES (?, (SELECT CURRENT_DATE()), ?, ?, " +
			"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), ?)";
		java.io.File file = new java.io.File(this.document.getFile().getStringPath());
		try {
			FileInputStream fis = new FileInputStream(file);
			this.connection.openConnection();
			PreparedStatement statement =
				this.connection.getConnection().prepareStatement(query);
			statement.setString(1, fullTitle);
			statement.setString(2, this.document.getType());
			statement.setBinaryStream(3, fis, (int) file.length());
			statement.setString(4, authorEmail);
			statement.setString(5, this.document.getFile().getStringPath());
			statement.executeUpdate();
			this.connection.closeConnection();
			this.document.setTitle(fullTitle);
			saved = true;
		} catch (FileNotFoundException | SQLException e) {
			new Logger().log(e);
		}
		return saved;
	}
	
	public boolean saveReport(Student student, String type) throws CustomException {
		boolean saved;
		if (student.isRegistered() && this.save(student.getEmail())) {
			String studentId = new DAOStudent(student).getId();
			String query = "INSERT INTO Reporte " +
				"(idDocumento, temporalidad, asignacion) VALUES (?, ?, " +
				"(SELECT idPracticante FROM Asignacion WHERE idPracticante = ? AND estaActivo =1))";
			String[] values = { this.getId(), type, studentId };
			saved = this.connection.sendQuery(query, values);
		} else {
			saved = false;
		}
		return saved;
	}
	
	public boolean downloadFile() {
		assert this.document != null : "Document is null: DAODocument.getFile()";
		assert this.document.isComplete() : "Document is incomplete: DAODocument.getFile()";
		boolean got;
		
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = { this.document.getTitle() };
		String[] columns = { "TOTAL" };
		assert this.connection.select(query, values, columns)[0][0].equals("1");
		query = "SELECT archivo, path FROM Documento WHERE titulo = ?";
		columns = new String[]{ "archivo", "path" };
		String[] responses = this.connection.select(query, values, columns)[0];
		File file = new File("Downloads/" + responses[1]);
		Dir.mkdir(file.getStringParentPath());
		got = file.write(responses[0]);
		this.document.setFile(file);
		return got;
	}
	
	public String getId() {
		String id = null;
		assert this.document != null : "Document is null: DAODocument.getId()";
		assert this.document.getTitle() != null : "Document's title is null: DAODocument.getId()";
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = { this.document.getTitle() };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("1")) {
			query = "SELECT idDocumento FROM Documento WHERE titulo = ?";
			columns = new String[]{ "idDocumento" };
			id = this.connection.select(query, values, columns)[0][0];
		}
		return id;
	}
}
