package IDAO;

import Models.Usuario;

public interface IDAOUsuario {
    public int logIn(Usuario usuario);

    public int signUp();

    public int isRegistered();

}
