import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber.html"},
    features = {"src/test/resources/features/Comment_Creation_Feature.feature"},
    glue = {"stepdefinitions"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
