package Tests;

import Exceptions.CustomException;
import Models.Coordinator;
import Models.Student;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsCoordinator {
	Coordinator coordinator = new Coordinator();

	//JUST FOR TEST: I create an student object here because we don´t have GUI yet, so we can create it "manually"
	@Test
	public void studentManagementTest(){
		Student student = new Student("Efrain","Arenas","efrain@correo.com","contrasenia123","s18012138");
		try {
			assertTrue(coordinator.signUpStudent(student));
		} catch (CustomException exception) {
			exception.printStackTrace();
		}
	}
}