import View.MainController;

public class Main {
	public static void main(String[] args) {
		
		MainController mainController = new MainController();
		mainController.hit();
		mainController.activate("Iniciar Sesion","Login/Login.fxml");
	}
}