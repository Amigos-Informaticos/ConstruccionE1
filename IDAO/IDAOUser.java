package IDAO;

import Exceptions.CustomException;

public interface IDAOUser {
	boolean logIn() throws CustomException;
	
	boolean signUp() throws CustomException;
	
	boolean isRegistered() throws CustomException;
	
	boolean delete() throws CustomException;
	
	boolean reactive() throws CustomException;
}