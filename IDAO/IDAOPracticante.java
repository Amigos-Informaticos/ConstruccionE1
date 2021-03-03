package IDAO;

import Exceptions.CustomException;
import Models.Practicante;

public interface IDAOPracticante extends IDAOUsuario {
	
	static Practicante[] getAll() {
		return new Practicante[0];
	}
	
	static Practicante get(Practicante practicante) {
		return null;
	}
	
	boolean actualizar() throws CustomException;
	
	boolean eliminar() throws CustomException;
}
