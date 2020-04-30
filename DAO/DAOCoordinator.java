package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOCoordinator;
import IDAO.IDAOUser;
import Models.Coordinator;

public class DAOCoordinator implements IDAOUser, IDAOCoordinator {
	private Coordinator coordinator;
	private DBConnection connection = new DBConnection();

    public DAOCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public boolean signUp() throws CustomException{
        boolean signedUp = false;
        if(this.coordinator.isComplete() && !this.isRegistered() && !this.isAnother()){
            String query = "INSERT INTO User (nombres, apellidos, correoElectronico, contrasena, status)" +
                            "VALUES (?, ?, ?, ?, ?)";
            String[] values = {this.coordinator.getNames(),
                                this.coordinator.getLastnames(),
                                this.coordinator.getEmail(),
                                this.coordinator.getPassword(), "1"};
            if (this.connection.sendQuery(query, values)) {
                query = "INSERT INTO Coordinator (idUser, noPersonal, dateUp) VALUES " +
                        "((SELECT idUser FROM User WHERE correoElectronico = ?), ?, (SELECT CURRENT_DATE))";
                values = new String[]{this.coordinator.getEmail(), this.coordinator.getPersonalNo()};
                if (this.connection.sendQuery(query, values)) {
                    signedUp = true;
                }else{
                    throw new CustomException("Could not insert into Coordinator: signUp()","NotSignUpCoordinator");
                }
            } else {
                throw new CustomException("Could not insert into Usuario: signUp()","NotSignUpUser");
            }
        }else{
            throw new CustomException("Null Pointer Exception: signUp()");
        }
        return signedUp;
    }

    @Override
    public boolean isRegistered() {
        boolean isRegistered = false;
        String query = "SELECT COUNT(idUser) AS TOTAL FROM User WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] names = {"TOTAL"};
        if (this.connection.select(query, values, names)[0][0].equals("1")) {
            query = "SELECT COUNT(Coordinador.idUsuario) AS TOTAL FROM Coordinador INNER JOIN Usuario ON " +
                "Coordinador.idUsuario = Usuario.idUsuario WHERE Usuario.correoElectronico = ?";
            isRegistered = this.connection.select(query, values, names)[0][0].equals("1");
        }
        return isRegistered;
    }

    @Override
    public boolean logIn() {
        boolean loggedIn = false;
        String query = "SELECT COUNT(idUser) AS TOTAL FROM Coordinator WHERE correoElectronico = ? " +
                        "AND contrasena = ?";
        String[] values = {this.coordinator.getEmail(), this.coordinator.getPassword()};
        String[] names = {"TOTAL"};
        if (this.isRegistered()) {
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    @Override
    public boolean delete() {
        boolean deleted = false;
        if (this.coordinator != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE User SET status = 0 WHERE correoElectronico = ?";
                String[] values = {this.coordinator.getEmail()};
                if (this.connection.sendQuery(query, values)) {
                    deleted = true;
                }
            } else {
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean isActive() {
        boolean isActive = false;
        if (this.coordinator != null && this.coordinator.getEmail() != null &&
                this.isRegistered()) {
            String query = "SELECT status FROM User WHERE correoElectronico = ?";
            String[] values = {this.coordinator.getEmail()};
            String[] names = {"status"};
            isActive = this.connection.select(query, values, names)[0][0].equals("1");
        }
        return isActive;
    }

    @Override
    public boolean reactive() {
        boolean reactivated = false;
        if (this.coordinator != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE User SET status = 1 WHERE correoElectronico = ?";
                String[] values = {this.coordinator.getEmail()};
                if (this.connection.sendQuery(query, values)) {
                    reactivated = true;
                }
            } else {
                reactivated = true;
            }
        }
        return reactivated;
    }

    public boolean isAnother(){
        boolean anotherCoordinator = false;
        String query = "SELECT COUNT (Coordinador.idUser) AS TOTAL FROM Coordinador INNER JOIN User ON Coordinador.idUser = User.idUser WHERE User.status = 1";
        String[] names = {"TOTAL"};
        String[][] resultQuery = this.connection.select(query, null, names);
        int coordinators = Integer.parseInt(resultQuery[0][0]);
        if(coordinators > 0){
            anotherCoordinator = true;
        }
        return anotherCoordinator;
    }

}