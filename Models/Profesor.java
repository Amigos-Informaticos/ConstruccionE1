package Models;

public class Profesor {
    private String noPersonal;
    private int turno;

    public Profesor(){

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
