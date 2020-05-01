package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOActivity;
import Models.Activity;
import tools.Logger;

public class DAOActivity implements IDAOActivity {
    private Activity activity;
    private DBConnection connection = new DBConnection();

    public DAOActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean create() throws CustomException {
        boolean created = false;
        if (this.activity.isComplete()) {
            String query = "INSERT INTO Actividad (idPracticante, titulo, descripcion, fechaInicio, fechaCierre) VALUES" +
                    " ((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?," +
                    " ?, (SELECT SYSDATE()), ?)";
            String[] values = {"edsonn1999@hotmail.com", this.activity.getTitle(), this.activity.getDescription(),
                    this.activity.getDeliveryDate()};
            if (this.connection.sendQuery(query, values)) {
                query = "SELECT fechaInicio FROM Actividad WHERE titulo = ?";
                String [] valuesSelect = {this.activity.getTitle()};
                String[] names = {"fechaInicio"};
                String startDate = this.connection.select(query, valuesSelect, names)[0][0];
                this.activity.setStartDate(startDate);
                created = true;
            } else {
                throw new CustomException("Error in query INSERT: SQLException");
            }
        } else {
            throw new CustomException("Some atribute is empty/null: ActivityNull");
        }
        return created;
    }

    @Override
    public boolean update() throws CustomException {
        boolean updated = false;
        if(this.activity.getStartDate() != null) {
            String query = "UPDATE Actividad SET titulo = ?, descripcion = ? WHERE  fechaInicio = ?";
            String[] values = {this.activity.getTitle(), this.activity.getDescription(), this.activity.getStartDate()};
            if (this.connection.sendQuery(query, values)) {
                updated = true;
            }
        } else {
            System.out.println("startDateNull");
        }
        return updated;
    }


    @Override
    public boolean delete() throws CustomException {
        boolean deleted = false;
        if (this.activity != null && this.isRegistered()) {
            String query = "DELETE FROM Actividad WHERE fechaInicio = ?";
            String[] values = {this.activity.getStartDate()};
            if (this.connection.sendQuery(query, values)) {
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean isRegistered() throws CustomException {
        boolean isRegistered = false;
        if (this.activity != null) {
            String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad WHERE titulo = ? AND descripcion = ?";
            String[] values = {this.activity.getTitle(), this.activity.getDescription()};
            String[] names = {"TOTAL"};
            isRegistered = this.connection.select(query, values, names)[0][0].equals("1");
        } else {
            throw new CustomException("Null Pointer Exception: isRegistered()");
        }
        return isRegistered;
    }

    public String getIdActivity() {
        String idActivity = null;
        try {
            if (this.isRegistered()) {
                String query = "SELECT idActividad FROM Actividad WHERE titulo = ? AND descripcion = ?";
                String[] values = {this.activity.getTitle(), this.activity.getDescription()};
                String[] names = {"idActividad"};
                idActivity = this.connection.select(query, values, names)[0][0];
            }
        } catch (CustomException e) {
            new Logger().log(e);
        }
        return idActivity;
    }
}
