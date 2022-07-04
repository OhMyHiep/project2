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
// UNCOMMENTING THIS WILL CAUSE THE TESTS TO RUN TWICE
//    private TestNGCucumberRunner testNGCucumberRunner;
//
//    @BeforeClass( alwaysRun = true)
//    public void setupClass(){
//        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//    }
//
//    @Test(dataProvider = "features")
//    public void feature( PickleWrapper pickleWrapper, FeatureWrapper feature){
//        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
//    }
//
//
//    @DataProvider
//    public Object[][] features(){
//        return testNGCucumberRunner.provideScenarios();
//    }
//
//    @AfterClass( alwaysRun = true)
//    public void tearDownClass(){
//        testNGCucumberRunner.finish();
//    }
}
