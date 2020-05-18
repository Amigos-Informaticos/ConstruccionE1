package Models;

import DAO.DAOTelephoneNumber;

public class TelephoneNumber {
    private String number;
    private String number2;

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public TelephoneNumber(){}

    public TelephoneNumber(String number) {
        this.number = number;
    }

    public TelephoneNumber(String number, String number2) {
        this.number = number;
        this.number2 = number2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean signUp(String idOrganization){
        DAOTelephoneNumber daoTelephoneNumber = new DAOTelephoneNumber();
        return daoTelephoneNumber.signUp(idOrganization);
    }

}
