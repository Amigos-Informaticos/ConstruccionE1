import Tests.TestRunner;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tools.P;

public class Main {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestRunner.class);
		for (Failure fail : result.getFailures()) {
			P.pln(fail);
		}
	}
}
