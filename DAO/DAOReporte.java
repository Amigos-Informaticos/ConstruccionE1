package DAO;

import Connection.ConexionBD;
import Connection.ConexionFTP;
import Models.Practicante;
import Models.Reporte;

import java.sql.SQLException;

public class DAOReporte {
	private Reporte reporte;
	private ConexionBD conexion = new ConexionBD();
	
	public DAOReporte(Reporte reporte) {
		this.reporte = reporte;
	}
	
	public Reporte getReporte() {
		return reporte;
	}
	
	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}
	
	public boolean guardarReporte(String ruta) throws SQLException {
		boolean guardado = false;
		String query = "CALL SPI_registrarReporteDocumento(?,?,?,?,?,?,?,?,?)";
		String idPracticante = new DAOPracticante((Practicante) this.reporte.getAutor()).getId();
		String rutaRemota = ((Practicante) this.reporte.getAutor()).getMatricula();
		String[] values = new String[] {
			idPracticante,
			this.reporte.getTipoReporte(),
			this.reporte.getNombre(),
			rutaRemota,
			this.reporte.getActividadesPlaneadas(),
			this.reporte.getActividadesRealizadas(),
			this.reporte.getResumen(),
			String.valueOf(this.reporte.getFechaInicio()),
			String.valueOf(this.reporte.getFechaFin())
		};
		if (this.conexion.ejecutar(query, values)) {
			ConexionFTP ftp = new ConexionFTP();
			String rutaLocal = ruta + "/" + this.reporte.getNombre();
			rutaRemota = ((Practicante) this.reporte.getAutor()).getMatricula();
			rutaRemota += "." + this.reporte.getNombre();
			guardado = ftp.enviarArchivo(rutaLocal, rutaRemota);
		}
		return guardado;
	}
}
