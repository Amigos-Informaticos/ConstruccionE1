package Models;

public class ActividadCalendarizada {
	private String nombre;
	private String fecha;
	
	public ActividadCalendarizada() {
	}
	
	public ActividadCalendarizada(String nombre, String fecha) {
		this.nombre = nombre;
		this.fecha = fecha;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
