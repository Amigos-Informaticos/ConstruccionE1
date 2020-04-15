import Exceptions.CustomException;
import tools.Logger;

public class Main {
	public static void main(String[] args) {
		new Logger().log(
			new CustomException("ola kases, probando las nuevas excepciones")
		);
	}
}
