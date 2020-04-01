import Connection.DBConnection;
import DAO.DAOUsuario;
import tools.P;

public class Prueba {
	public static void main(String[] args) {
		DAOUsuario user1 = new DAOUsuario(
				"Edson Manuel",
				"Carballo Vera",
				"edsonn1999@hotmail.com",
				"relojito"
		);
		P.p(user1.signUp());
	}
}
