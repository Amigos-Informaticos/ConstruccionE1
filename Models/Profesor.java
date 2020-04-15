package Models;

public class Profesor extends User {
    private String noPersonal;
    private int turno;
    
    public Profesor() {
    
    }
    
    public Profesor(String nombres, String apellidos, String correoElectronico, String contrasena,
                    String noPersonal, int turno) {
        super(nombres, apellidos, correoElectronico, contrasena);
        this.noPersonal = noPersonal;
        this.turno=turno;
    }

    public String getNoPersonal() {
        return this.noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public int getTurno() {
        return this.turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
}
