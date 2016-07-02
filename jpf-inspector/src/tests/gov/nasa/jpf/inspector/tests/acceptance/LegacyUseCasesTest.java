package gov.nasa.jpf.inspector.tests.acceptance;

import gov.nasa.jpf.inspector.tests.acceptance.architecture.CorrectOutputAbstractTest;
import gov.nasa.jpf.inspector.tests.acceptance.architecture.CorrectOutputTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

/**
 * This test runs use case scenarios automatically. In previous versions of JPF Inspector, these use cases
 * were run by hand.
 */
@RunWith(Parameterized.class)
public class LegacyUseCasesTest extends CorrectOutputAbstractTest {

  public LegacyUseCasesTest(CorrectOutputTestCase testCase) {
    super(testCase);
  }

  @Test
  public void test() {
    baseTest();
  }

  @Parameters(name = "{0}")
  public static Iterable<?> data() {
     return Arrays.asList(
             // Use Case 1 -- fully operational
             getCase("legacy/usecases/uc1", "part1"),
             getCase("legacy/usecases/uc1", "part2"),

             // Use Case 2
             getCase("legacy/usecases/uc2", "part1"),
             getCase("legacy/usecases/uc2", "part2"),

             // Use Case
             getCase("legacy/usecases/uc3", "part1"),
             getCase("legacy/usecases/uc3", "part2"),

             // Use case 4 -- requires back_step_transition
           //  getCase("legacy/usecases/uc4", "usecase"),

             // Use case JPF W -- fully operational
             getCase("legacy/usecases/ucjpfw", "usecase")
     );
  }




}
