package Models;

import DAO.DAOReporte;

import java.sql.SQLException;
import java.time.LocalDate;

public class Reporte extends Documento {
	private String idReporte;
	private String actividadesPlaneadas;
	private String actividadesRealizadas;
	private String resumen;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String tipoReporte;
	private Practicante practicante;

	public Reporte(String actividadesPlaneadas, String actividadesRealizadas, String resumen, LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte, String idReporte) {
		this.actividadesPlaneadas = actividadesPlaneadas;
		this.actividadesRealizadas = actividadesRealizadas;
		this.resumen = resumen;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipoReporte = tipoReporte;
		this.idReporte = idReporte;
	}

	public Reporte() {

	}

	public String getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(String idReporte) {
		this.idReporte = idReporte;
	}

	public String getActividadesPlaneadas() {
		return actividadesPlaneadas;
	}
	
	public void setActividadesPlaneadas(String actividadesPlaneadas) {
		this.actividadesPlaneadas = actividadesPlaneadas;
	}
	
	public String getActividadesRealizadas() {
		return actividadesRealizadas;
	}
	
	public void setActividadesRealizadas(String actividadesRealizadas) {
		this.actividadesRealizadas = actividadesRealizadas;
	}
	
	public String getResumen() {
		return resumen;
	}
	
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public String getTipoReporte() {
		return tipoReporte;
	}
	
	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	public Practicante getPracticante() {
		return practicante;
	}
	
	public void setPracticante(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public boolean guardarReporte(String ruta) throws SQLException {
		return new DAOReporte(this).guardarReporte(ruta);
	}

	@Override
	public String toString() {
		return "Reporte{" +
				"idReporte='" + idReporte + '\'' +
				", actividadesPlaneadas='" + actividadesPlaneadas + '\'' +
				", actividadesRealizadas='" + actividadesRealizadas + '\'' +
				", resumen='" + resumen + '\'' +
				", fechaInicio=" + fechaInicio +
				", fechaFin=" + fechaFin +
				", tipoReporte='" + tipoReporte + '\'' +
				", practicante=" + practicante +
				'}';
	}
}
