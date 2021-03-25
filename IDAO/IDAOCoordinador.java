package IDAO;

import java.sql.SQLException;

public interface IDAOCoordinador extends IDAOUsuario {
    
    boolean hayOtro() throws SQLException;
    
}