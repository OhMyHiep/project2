package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pagefactory.LoginPageFactory;
import pagefactory.UpdatePageFactory;

import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;

public class UpdateBugSteps {

    public WebDriver driver;
    public LoginPageFactory loginPageFactory;
    public UpdatePageFactory updatePageFactory;

    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        driver.get("http://localhost:8080/");
        loginPageFactory = new LoginPageFactory(driver);
        updatePageFactory=new UpdatePageFactory(driver);
    }

    @Given("I am logged in and I am on The detail Bug page")
    public void iMLoggedInAndIMOnTheDetailBugPage() {
        loginPageFactory.login("email@email.com", "pass");
        driver.get("http://localhost:8080/detailBug");
    }

    @When("I chose a severity {string}")
    public void iChoseA(String arg0) {
        updatePageFactory.inputSeverity(arg0);
    }

    @And("I input an urgency {string}")
    public void iInputAn(String arg0) {
        updatePageFactory.inputUrgency(arg0);
    }

    @And("I set the Assignee {string}")
    public void ISetAssignee(String args0){
        updatePageFactory.inputAssignee(args0);
    }

    @And("I chose a status {string}")
    public void IChoseStatus(String args0){
        updatePageFactory.inputStatus(args0);
    }

    @And("I click update")
    public void iClickUpdate() {
        updatePageFactory.clickUpdate();
    }

    @Then("I will see the feedback {string}")
    public void iWillSeeThe(String arg0) {
        WebElement ele=driver.findElement(By.id("feedback-msg"));
        assertTrue(ele.getText().contains(arg0));
    }

    @After
    public void tearDown(){
        this.driver.quit();
    }
}
