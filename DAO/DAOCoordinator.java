package DAO;

import Connection.DBConnection;
import IDAO.IDAOCoordinator;
import IDAO.Shift;
import Models.Coordinator;

public class DAOCoordinator implements IDAOCoordinator, Shift {
    private Coordinator coordinator;
    private final DBConnection connection = new DBConnection();

    public DAOCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public boolean signUp() {
        assert this.coordinator.isComplete() : "Coordinator isnt complete: DAOCoordinator.signUp()";
        assert !this.isRegistered() : "Coordinator is already registered: DAOCoordinator.signUp()";
        assert !this.isAnother() : "There is another coordinator: DAOCoordinator.signUp()";
        boolean signedUp = false;
        String query =
                "INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, contrasena, estaActivo)" +
                        "VALUES (?, ?, ?, ?, ?)";
        String[] values =
                {this.coordinator.getNames(),
                        this.coordinator.getLastnames(),
                        this.coordinator.getEmail(),
                        this.coordinator.getPassword(), "1"};
        if (this.connection.sendQuery(query, values)) {
            query = "INSERT INTO Coordinador (idMiembro, noPersonal, fechaRegistro, turno, registrador) VALUES " +
                    "((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?),?,(SELECT CURRENT_DATE), ?,?)";
            values = new String[]{this.coordinator.getEmail(), this.coordinator.getPersonalNo(), "1", "16"}; //TODO Agregar getIdCoordinator() y getIdAdmin
            signedUp = this.connection.sendQuery(query, values);
        }
        return signedUp;
    }

    public boolean update() {
        assert coordinator != null : "Coordinator is null : update()";
        assert coordinator.isComplete() : "Coordinator is not complete : update()";
        assert this.isActive() : "Coordinator has not a status active on the system";
        boolean updated = false;
        String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getNames(), this.coordinator.getLastnames(),
                this.coordinator.getEmail()};
        if (this.connection.sendQuery(query, values)) {
            query = "UPDATE Coordinador SET noPersonal = ? WHERE idMiembro = ?";
            values = new String[]{this.coordinator.getPersonalNo(), this.getIdCoordinator()};
            if (this.connection.sendQuery(query, values)) {
                updated = true;
            }
        }
        return updated;
    }

    @Override
    public boolean isRegistered() {
        assert this.coordinator != null;
        assert this.coordinator.getEmail() != null;
        String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
                "INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro " +
                "WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] names = {"TOTAL"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    @Override
    public boolean logIn() {
        assert this.isRegistered() : "Coordinator is not registered: DAOCoordinator.logIn()";
        String query = "SELECT COUNT(idMiembro) AS TOTAL FROM Coordinador " +
                "WHERE correoElectronico = ? AND contrasena = ?";
        String[] values = {this.coordinator.getEmail(), this.coordinator.getPassword()};
        String[] names = {"TOTAL"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    @Override
    public boolean delete() {
        assert this.coordinator != null : "Coordinator is null: DAOCoordinator.delete()";
        assert this.isRegistered() : "Coordinator is not registered: DAOCoordinator.delete()";
        assert this.isActive() : "Coordinator is not active: DAOCoordinator.delete()";
        String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        return this.connection.sendQuery(query, values);
    }

    public boolean isActive() {
        assert this.coordinator != null : "Coordinator is null: DAOCoordinator.isActive()";
        assert this.coordinator.getEmail() != null :
                "Coordinator email is null: DAOCoordinator.isActive()";
        assert this.isRegistered() : "Coordinator is not registered: DAOCoordinator.isActive()";
        String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] names = {"estaActivo"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    @Override
    public boolean reactive() {
        assert this.coordinator != null : "Coordinator is null: DAOCoordinator.reactive()";
        assert this.isRegistered() : "Coordinator is not registered: DAOCoordinator.reactive()";
        assert this.isActive() : "Coordinator is not active: DAOCoordinator.reactive()";
        String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        return this.connection.sendQuery(query, values);
    }

    public boolean isAnother() {
        String query = "SELECT COUNT (Coordinador.idMiembro) AS TOTAL FROM Coordinador " +
                "INNER JOIN MiembroFEI ON Coordinador.idMiembro = MiembroFEI.idMiembro WHERE MiembroFEI.estaActivo = 1";
        String[] names = {"TOTAL"};
        String[][] resultQuery = this.connection.select(query, null, names);
        return Integer.parseInt(resultQuery[0][0]) > 0;
    }

    @Override
    public String getShift() {
        assert this.coordinator != null : "Professor is null: DAOCoordinator.getShift()";
        assert this.coordinator.getEmail() != null :
                "Professor's email is null: DAOProfessor.getShift()";
        String query = "SELECT turno FROM Turno " +
                "INNER JOIN Coordinador ON Turno.idTurno = Coordinador.turno " +
                "INNER JOIN MiembroFEI ON Coordinador.idMiembro = MiembroFEI.idMiembro " +
                "WHERE MiembroFEI.correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] responses = {"turno"};
        return this.connection.select(query, values, responses)[0][0];
    }

    public static Coordinator getActive() {
        Coordinator coordinator = new Coordinator();
        String query = "SELECT nombres, apellidos, correoElectronico, contrasena noPersonal, fechaRegistro, Turno.turno FROM " +
                "MiembroFEI INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro INNER JOIN Turno ON " +
                "Turno.idTurno = Coordinador.turno WHERE estaActivo = 1";
        String[] names = {"nombres", "apellidos", "correoElectronico","contrasena","noPersonal","fechaRegistro","turno"};
        String[][] responses = new DBConnection().select(query, null, names);
        if(responses.length>0){
            coordinator.setNames(responses[0][0]);
            coordinator.setLastnames(responses[0][1]);
            coordinator.setEmail(responses[0][2]);
            coordinator.setPassword(responses[0][3]);
            coordinator.setPersonalNo(responses[0][4]);
            coordinator.setRegistrationDate(responses[0][5]);
            coordinator.setShift(responses[0][6]);
        }
        return coordinator;
    }

    public static Coordinator[] getAll() {
        String query =
                "SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, estaActivo, fechaRegistro, fechaBaja FROM " +
                        "MiembroFEI INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro WHERE estaActivo=1";
        String[] names =
                {"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "estaActivo", "fechaRegistro",
                        "fechaBaja"};
        String[][] responses = new DBConnection().select(query, null, names);
        Coordinator[] coordinators = new Coordinator[responses.length];
        for (int i = 0; i < responses.length; i++) {
            coordinators[i] = new Coordinator(
                    responses[i][0],
                    responses[i][1],
                    responses[i][2],
                    responses[i][3],
                    responses[i][4],
                    responses[i][6],
                    responses[i][7]
            );
        }
        return coordinators;
    }

    private String getIdCoordinator() {
        String query = "SELECT idMiembro AS idCoordinator FROM MiembroFEI WHERE correoElectronico = ?";
        String[] values = {this.coordinator.getEmail()};
        String[] names = {"idCoordinator"};
        return this.connection.select(query, values, names)[0][0];
    }

    public String getIdShift() {
        String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
        String[] values = {this.coordinator.getShift()};
        String[] names = {"Turno"};
        return this.connection.select(query, values, names)[0][0];
    }
}