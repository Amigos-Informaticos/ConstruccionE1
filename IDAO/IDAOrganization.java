package IDAO;

import Exceptions.CustomException;

public interface IDAOrganization {
    public boolean signUp() throws CustomException;

    public boolean isRegistered();

    public boolean delete();

    public boolean isActive();

    public boolean reactivate();
}
