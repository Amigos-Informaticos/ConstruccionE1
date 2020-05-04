package idao;

import exceptions.CustomException;
import models.Student;

public interface IDAOStudent extends IDAOUser {
	
	static Student[] getAll() {
		return new Student[0];
	}
	
	static Student get(Student student) {
		return null;
	}
	
	boolean update() throws CustomException;
	
	boolean delete() throws CustomException;
}
