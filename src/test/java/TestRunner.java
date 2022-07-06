import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber.html"},
<<<<<<< Updated upstream
    features = {"src/test/resources/features"},
=======
    features = {"src/test/resources/features/Bug_View_Feature.feature"},
>>>>>>> Stashed changes
    glue = {"stepdefinitions"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
