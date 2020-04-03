public class Prueba {
	public static void main(String[] args) {
		DAOAdministrador admin = new DAOAdministrador(
				"Edson Manuel",
				"Carballo Vera",
				"edsonn1999@hotmail.com",
				"relojito"
		);
		P.p(admin.signUp());
	}//Efrain was here
}
