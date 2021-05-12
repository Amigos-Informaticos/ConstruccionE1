package IDAO;

import java.sql.SQLException;

public interface IDAOCoordinador extends IDAOUsuario {
    boolean registrar() throws SQLException;
    boolean actualizar() throws SQLException;
    boolean eliminar() throws SQLException;
    boolean hayOtro() throws SQLException;
    
}