package DAO;

import Connection.ConexionBD;
import Exceptions.CustomException;
import Models.Documento;
import Models.Practicante;
import tools.Dir;
import tools.File;
import tools.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAODocumento {
	/*
	private final Documento documento;
	private final ConexionBD connection = new ConexionBD();
	
	public DAODocumento(Documento documento) {
		this.documento = documento;
	}
	
	public boolean save(String authorEmail) throws SQLException {
		assert this.documento != null : "Document is null: DAODocument.save()";
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.save()";
		assert this.documento.getFile().exists() : "File doesnt exists: DAODocument.save()";
		
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM MiembroFEI " +
			"WHERE correoElectronico = ?";
		String[] values = {authorEmail};
		String[] names = {"TOTAL"};
		assert this.connection.seleccionar(query, values, names)[0][0].equals("1");
		boolean saved = false;
		
		query = "SELECT COUNT(titulo) AS TOTAL FROM Documento " +
			"WHERE titulo LIKE CONCAT(?, '%')";
		values = new String[] {this.documento.getTitle()};
		int occurrences = Integer.parseInt(this.connection.seleccionar(query, values, names)[0][0]);
		String fullTitle = occurrences > 0 ?
			this.documento.getTitle() + "(" + occurrences + ")" :
			this.documento.getTitle();
		
		query = "INSERT INTO Documento (titulo, fecha, tipo, archivo, autor, path) " +
			"VALUES (?, (SELECT CURRENT_DATE()), ?, ?, " +
			"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), ?)";
		java.io.File file = new java.io.File(this.documento.getFile().getStringPath());
		try {
			FileInputStream fis = new FileInputStream(file);
			this.connection.openConnection();
			PreparedStatement statement =
				this.connection.getConnection().prepareStatement(query);
			statement.setString(1, fullTitle);
			statement.setString(2, this.documento.getType());
			statement.setBinaryStream(3, fis, (int) file.length());
			statement.setString(4, authorEmail);
			statement.setString(5, this.documento.getFile().getStringPath());
			statement.executeUpdate();
			this.connection.closeConnection();
			this.documento.setTitle(fullTitle);
			saved = true;
		} catch (FileNotFoundException | SQLException e) {
			new Logger().log(e);
		}
		return saved;
	}
	
	public boolean saveReport(Practicante practicante, String type) throws CustomException, SQLException {
		boolean saved;
		if (practicante.estaRegistrado() && this.save(practicante.getEmail())) {
			String studentId = new DAOPracticante(practicante).getId();
			String query = "INSERT INTO Reporte " +
				"(idDocumento, temporalidad, asignacion) VALUES (?, ?, " +
				"(SELECT idPracticante FROM Asignacion WHERE idPracticante = ? AND estaActivo =1))";
			String[] values = {this.getId(), type, studentId};
			saved = this.connection.ejecutar(query, values);
		} else {
			saved = false;
		}
		return saved;
	}
	
	public boolean saveLocally() {
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.saveLocally()";
		assert this.documento.getFile().exists() : "File doesnt exists: DAODocument.saveLocally()";
		
		try {
			File file = new File("LocalFiles/" + this.documento.getFile().getName());
			Dir.mkdir("LocalFiles");
			file.create();
			Files.copy(this.documento.getFile().getPath(), file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean downloadFile() throws SQLException {
		assert this.documento != null : "Document is null: DAODocument.getFile()";
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.getFile()";
		boolean got;
		
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = {this.documento.getTitle()};
		String[] columns = {"TOTAL"};
		assert this.connection.seleccionar(query, values, columns)[0][0].equals("1");
		query = "SELECT archivo, path FROM Documento WHERE titulo = ?";
		columns = new String[] {"archivo", "path"};
		String[] responses = this.connection.seleccionar(query, values, columns)[0];
		File file = new File("LocalFiles/Downloads/" + responses[1]);
		Dir.mkdir(file.getStringParentPath());
		got = file.write(responses[0]);
		this.documento.setFile(file);
		return got;
	}
	
	public String getId() throws SQLException {
		String id = null;
		assert this.documento != null : "Document is null: DAODocument.getId()";
		assert this.documento.getTitle() != null : "Document's title is null: DAODocument.getId()";
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = {this.documento.getTitle()};
		String[] columns = {"TOTAL"};
		if (this.connection.seleccionar(query, values, columns)[0][0].equals("1")) {
			query = "SELECT idDocumento FROM Documento WHERE titulo = ?";
			columns = new String[] {"idDocumento"};
			id = this.connection.seleccionar(query, values, columns)[0][0];
		}
		return id;
	}

	 */
}
