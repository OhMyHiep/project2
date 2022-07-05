import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber.html"},
        features = {"src/test/resources/features/Bug_Creation_Feature.feature"},
        glue = {"stepdefinitions"}
)
public class TestBugSubmissionRunner extends AbstractTestNGCucumberTests {

}
