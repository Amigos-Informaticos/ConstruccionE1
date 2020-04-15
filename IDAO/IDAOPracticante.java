package IDAO;

import Exceptions.CustomException;
import Models.Student;

public interface IDAOPracticante extends IDAOUsuario {
	
	static Student[] getAll() {
		return new Student[0];
	}
	
	static Student get(Student student) {
		return null;
	}
	
	boolean update() throws CustomException;
	
	boolean delete() throws CustomException;
}
