package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOActivity;
import Models.Activity;

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
                created = true;
            } else {
                throw new CustomException("Error in query INSERT: SQLException");
            }
        }else {
            throw new CustomException("Some atribute is empty/null: ActivityNull");
        }
        return created;
    }

    @Override
    public boolean update() throws CustomException {
        return false;
    }

    @Override
    public boolean delete() throws CustomException {
        return false;
    }
}
