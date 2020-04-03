package Models;

public class Profesor {
    private String noPersonal;
    private int turno;

    public Profesor(){

    }
    public Profesor(String noPersonal, int turno) {
        this.noPersonal=noPersonal;
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
