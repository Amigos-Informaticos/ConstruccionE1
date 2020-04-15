package DAO;

import Connection.DBConnection;
import IDAO.IDAOCoordinator;
import Models.Coordinator;

public class DAOCoordinator implements IDAOCoordinator {
	private Coordinator coordinator;
	private DBConnection connection = new DBConnection();

    public DAOCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }




    /**
     * Method to sign up a new Admin
     * <p>
     * STATUS DESCRIPTION
     * 0	->	Initial status: no action has been taken
     * 1	->	Success
     * 2	->	Error in sending query
     * 3	->	User already registered
     * 4	->	Malformed object
     * 5	->	Registered into User but not into Admin. Attend immediately
     * </p>
     *
     * @return The status description
     */
    @Override
    public boolean signUp() {
        boolean signedUp = false;
        if(this.coordinator.isComplete() && !this.isRegistered() && !this.isAnother()){
            String query = "INSERT INTO User (names, lastNames, email, password, status)" +
                            "VALUES (?, ?, ?, ?, ?)";
            String[] values = {this.coordinator.getNames(),
                                this.coordinator.getLastNames(),
                                this.coordinator.getEmail(),
                                this.coordinator.getPassword(), "1"};
            if (this.connection.sendQuery(query, values)) {
                query = "INSERT INTO Coordinator (idUser, noPersonal, dateUp) VALUES " +
                        "((SELECT idUser FROM User WHERE email = ?), ?, (SELECT CURRENT_DATE))";
                values = new String[]{this.coordinator.getEmail(), this.coordinator.getPersonalNo()};
                if (this.connection.sendQuery(query, values)) {
                    signedUp = true;
                }
            }
        }
        return signedUp;
    }

    @Override
    public boolean isRegistered() {
        boolean isRegistered = false;
        String query = "SELECT COUNT(idUser) AS TOTAL FROM User WHERE email = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] names = {"TOTAL"};
        if (this.connection.select(query, values, names)[0][0].equals("1")) {
            isRegistered = true;
        }
        return isRegistered;
    }

    @Override
    public boolean logIn() {
        boolean loggedIn = false;
        String query = "SELECT COUNT(idUser) AS TOTAL FROM Coordinator WHERE email = ? " +
                "AND password = ?";
        String[] values = {this.coordinator.getEmail(), this.coordinator.getPassword()};
        String[] names = {"TOTAL"};
        if (this.isRegistered()) {
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    //@Override
    public boolean delete() {
        boolean deleted = false;
        if (this.coordinator != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE User SET status = 0 WHERE email = ?";
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
            String query = "SELECT status FROM User WHERE email = ?";
            String[] values = {this.coordinator.getEmail()};
            String[] names = {"status"};
            isActive = this.connection.select(query, values, names)[0][0].equals("1");
        }
        return isActive;
    }

    //@Override
    public boolean reactive() {
        boolean reactivated = false;
        if (this.coordinator != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE User SET status = 1 WHERE email = ?";
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
        String query = "SELECT COUNT (Coordinator.idUser) AS TOTAL FROM Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser WHERE User.status = 1";
        String[] names = {"TOTAL"};
        String[][] resultQuery = this.connection.select(query, null, names);
        int coordinators = Integer.parseInt(resultQuery[0][0]);
        if(coordinators > 0){
            anotherCoordinator = true;
        }
        return anotherCoordinator;
    }



}