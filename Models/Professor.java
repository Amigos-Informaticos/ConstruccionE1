package Models;

public class Professor extends User {
    private String personalNo;
    private int shift;
    
    public Professor() {
    }

    public Professor(String names, String lastnames, String email, String password,
                     String personalNo, int shift) {
        super(names, lastnames, email, password);
        this.personalNo = personalNo;
        this.shift=shift;
    }

    public String getPersonalNo() {
        return personalNo;
    }

    public int getShift() {
        return shift;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

}
