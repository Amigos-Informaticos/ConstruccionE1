package DAO;

import Connection.ConexionBD;
import Models.Documento;
import tools.Dir;
import tools.File;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class DAODocumento {
	
	private final Documento documento;
	private final ConexionBD connection = new ConexionBD();
	
	public DAODocumento(Documento documento) {
		this.documento = documento;
	}
	
	public boolean save(String autorId, int idProyecto) throws SQLException {
		assert this.documento != null : "Document is null: DAODocument.save()";
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.save()";
		assert this.documento.getFile().exists() : "File doesnt exists: DAODocument.save()";
		
		String ruta = "";
		
		String query = "CALL SPI_registrarDocumento(?, ?, ?, 0)";
		String[] values = new String[] {
			autorId, ruta, this.documento.getNombre()};
		return this.connection.ejecutar(query, values);
	}
	
	public boolean saveLocally() throws IOException {
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.saveLocally()";
		assert this.documento.getFile().exists() : "File doesnt exists: DAODocument.saveLocally()";
		
		File file = new File("LocalFiles/" + this.documento.getFile().getName());
		Dir.mkdir("LocalFiles");
		file.create();
		Files.copy(this.documento.getFile().getPath(), file.getPath());
		return true;
	}
	
	public boolean downloadFile() throws SQLException {
		assert this.documento != null : "Document is null: DAODocument.getFile()";
		assert this.documento.estaCompleto() : "Document is incomplete: DAODocument.getFile()";
		boolean got;
		
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = {this.documento.getNombre()};
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
		assert this.documento.getNombre() != null : "Document's title is null: DAODocument.getId()";
		String query = "SELECT COUNT(titulo) AS TOTAL FROM Documento WHERE titulo = ?";
		String[] values = {this.documento.getNombre()};
		String[] columns = {"TOTAL"};
		if (this.connection.seleccionar(query, values, columns)[0][0].equals("1")) {
			query = "SELECT idDocumento FROM Documento WHERE titulo = ?";
			columns = new String[] {"idDocumento"};
			id = this.connection.seleccionar(query, values, columns)[0][0];
		}
		return id;
	}
	
}
