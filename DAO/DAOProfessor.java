package DAO;

import Connection.DBConnection;
import IDAO.IDAOProfessor;
import IDAO.Shift;
import Models.Professor;

public class DAOProfessor implements IDAOProfessor, Shift {
    private Professor professor;
    private final DBConnection connection = new DBConnection();

    public DAOProfessor(Professor professor) {
        this.professor = professor;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public boolean update() {
        assert professor != null : "Professor is null : update()";
        assert professor.isComplete() : "Professor is not complete : update()";
        assert this.isActive() : "Professor has not a status active on the system";
        boolean updated = false;
        String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
        String[] values = {this.professor.getNames(), this.professor.getLastnames(),
                this.professor.getEmail()};
        if (this.connection.sendQuery(query, values)) {
            query = "UPDATE Profesor SET noPersonal = ?, turno = ? WHERE idMiembro = ?";
            values = new String[]{this.professor.getPersonalNo(), this.getIdShift(), this.getIdProfessor()};
            if (this.connection.sendQuery(query, values)) {
                updated = true;
            }
        }
        return updated;
    }

    @Override
    public boolean delete() {
        assert this.professor != null : "Professor null is null : delete()";
        assert this.isActive() : "Professor is not active : delete()";
        String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        return this.connection.sendQuery(query, values);
    }

    @Override
    public boolean reactive() {
        assert this.professor != null : "Professor is null : reactive()";
        assert !this.isActive() : "Professor is not active : reactive()";
        String query = "UPDATE MiembroFEI SET status = 1 WHERE correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        return this.connection.sendQuery(query, values);
    }

    @Override
    public boolean logIn() {
        assert this.isRegistered() : "Professor is not registered: DAOProfessor.logIn()";
        assert this.isActive() : "Professor is not active: DAOProfessor.logIn()";
        String query = "SELECT COUNT(idMiembro AS TOTAL FROM MiembroFEI WHERE correoElectronico = ? " +
                "AND contrasena = ?";
        String[] values = {this.professor.getEmail(), this.professor.getPassword()};
        String[] names = {"TOTAL"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    @Override
    public boolean signUp() {
        assert this.professor.isComplete() : "Professor not complete: DAOProfessor.signUp()";
        boolean signedUp = false;
        String query = "INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, contrasena, estaActivo," +
                " registrador) VALUES (?, ?, ?, ?, ?, ?)";
        String[] values = {this.professor.getNames(), this.professor.getLastnames(),
                this.professor.getEmail(), this.professor.getPassword(), "1", "1"};
        if (this.connection.sendQuery(query, values)) {
            query = "INSERT INTO Profesor (idMiembro, fechaRegistro, noPersonal, turno) VALUES " +
                    "((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), (SELECT CURRENT_DATE), ?, ?)";
            values = new String[]{this.professor.getEmail(), this.professor.getPersonalNo(), this.getIdShift()};
            signedUp = this.connection.sendQuery(query, values);
        }
        return signedUp;
    }

    public boolean isActive() {
        assert this.professor != null : "Professor is null: DAOProfessor.isActive()";
        assert this.professor.getEmail() != null :
                "Professor's email is null: DAOProfessor.isActive()";
        assert this.isRegistered() : "Professor is not registered: DAOProfessor.isActive()";
        String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        String[] names = {"estaActivo"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    @Override
    public boolean isRegistered() {
        assert this.professor != null;
        assert this.professor.getEmail() != null;
        String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI INNER JOIN Profesor " +
                "ON MiembroFEI.idMiembro = Profesor.idMiembro WHERE correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        String[] names = {"TOTAL"};
        return this.connection.select(query, values, names)[0][0].equals("1");
    }

    public String getIdShift() {
        String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
        String[] values = {this.professor.getShift()};
        String[] names = {"Turno"};
        return this.connection.select(query, values, names)[0][0];
    }

    @Override
    public String getShift() {
        assert this.professor != null : "Professor is null: DAOProfessor.getShift()";
        assert this.professor.getEmail() != null :
                "Professor's email is null: DAOProfessor.getShoft()";
        String query = "SELECT turno FROM Turno " +
                "INNER JOIN Profesor ON Turno.idTurno = Profesor.turno " +
                "INNER JOIN Usuario ON Profesor.idUsuario = Usuario.idUsuario " +
                "WHERE Usuario.correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        String[] responses = {"turno"};
        return this.connection.select(query, values, responses)[0][0];
    }

    private String getIdProfessor() {
        String query = "SELECT idMiembro AS idProfessor FROM MiembroFEI WHERE correoElectronico = ?";
        String[] values = {this.professor.getEmail()};
        String[] names = {"idMiembro"};
        return this.connection.select(query, values, names)[0][0];
    }

    public static Professor[] get(Professor professor) {
        return null;
    }
}
