package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOActivity;
import Models.Activity;

public class DAOActivity implements IDAOActivity {
    private Activity activity;
    private final DBConnection connection = new DBConnection();

    @Override
    public boolean create() throws CustomException {
        boolean created = false;
        if (this.activity.isComplete()) {
            String query = "INSERT INTO Activity (idPracticante, titulo, descripcion, fechaInicio, fechaCierre)" +
                    "VALUES ((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?, ?, ?, ?)";
            String[] values = {"edsonn1999@hotmail.com", this.activity.getTitle(),
                    this.activity.getDescription(), this.activity.getStartDate(),
                    this.activity.getDeliveryDate()};
            if (this.connection.sendQuery(query, values)) {
                created = true;
            }
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
