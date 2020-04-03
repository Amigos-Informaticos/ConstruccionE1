package DAO;

import Models.Practicante;

public class DAOPracticante extends DAOUsuario {
	private Practicante practicante;

	public DAOPracticante(Practicante practicante) {
		super(practicante);
		this.practicante = practicante;
	}


	public int logIn() {
		int status = super.logIn();
		if (status == 1) {
			String query = "SELECT COUNT(Practicante.idUsuario) AS TOTAL FROM Practicante " +
					"INNER JOIN Usuario ON Practicante.idUsuario = Usuario.idUsuario " +
					"WHERE Usuario.correoElectronico = ?";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"TOTAL"};
			if (!this.connection.select(query, values, names)[0][0].equals("1")) {
				status = 5;
			}
		}
		return status;
	}

	public int signUp() {
		int status = super.signUp();
		if (status == 1) {
			String query = "INSERT INTO Practicante (idUsuario, matricula) VALUES " +
					"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
			String[] values = {this.practicante.getCorreoElectronico(), this.practicante.getMatricula()};
			if (!this.connection.preparedQuery(query, values)) {
				status = 5;
			}
		}
		return status;
	}
}
