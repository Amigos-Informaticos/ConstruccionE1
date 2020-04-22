package IDAO;

import Exceptions.CustomException;

public interface IDAOActivity {
    boolean create() throws CustomException;

    boolean update() throws CustomException;

    boolean delete() throws CustomException;
}
