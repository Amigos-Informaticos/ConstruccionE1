package DAO;

import Connection.DBConnection;
import Models.ProjectResponsible;

public class DAOProjectResponsible {
    private ProjectResponsible projectResponsible;
    private DBConnection connection = new DBConnection();

    public DAOProjectResponsible(ProjectResponsible projectResponsible){
        this.projectResponsible = projectResponsible;
    }

    public boolean signUp(){
        boolean signedUp = false;
        if(this.projectResponsible.isComplete()){
            String query = "INSERT INTO Responsable (correoElectronico, nombres, apellidos) " +
                            "VALUES (?,?,?)";
            String[] values = {this.projectResponsible.getEmail(),
                                this.projectResponsible.getNames(),
                                this.projectResponsible.getLastNames()};
            if(this.connection.sendQuery(query,values)){
                signedUp = true;
            }
        }
        return signedUp;
    }

    public boolean isRegistered(){
        String query = "SELECT COUNT (correoElectronico) AS TOTAL FROM Responsable WHERE correoElectron = ?";
        String[] values = {this.projectResponsible.getEmail()};
        String[] names = {"TOTAL"};
        return this.connection.select(query,values,names)[0][0].equals("1");
    }

    public boolean delete(){
        boolean deleted = false;
        if(this.projectResponsible != null && this.isRegistered()){
            String query = "UPDATE Responsable SET status = 0 WHERE correoElectronico";
            String[] values = {this.projectResponsible.getEmail()};
            if(this.connection.sendQuery(query,values)){
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean isActive(){
        boolean isActive = false;
        if(this.isRegistered() &&
                this.projectResponsible != null &&
                this.projectResponsible.getEmail() != null){
            String query = "SELECT status FROM Responsable WHERE correoElectronico = ?";
            String[] values = {this.projectResponsible.getEmail()};
            String[] names = {"status"};
            isActive = this.connection.select(query,values,names)[0][0].equals("");
        }
        return isActive;
    }

    public boolean reactive(){
        boolean reactivated = false;
        if(this.projectResponsible != null && this.isRegistered()){
            if(this.isActive()){
                String query = "UPDATE Responsable SET status = 1 WHERE correoElectronico = ?";
                String[] values =
            }
        }
        return reactivated;
    }


}
