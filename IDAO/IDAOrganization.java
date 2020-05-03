package IDAO;

import Exceptions.CustomException;

public interface IDAOrganization {
    boolean signUp() throws CustomException;
    
    boolean isRegistered();
    
    boolean delete();
    
    boolean isActive();
    
    boolean reactivate();
}
