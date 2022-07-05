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
import org.openqa.selenium.support.ui.Select;
import pagefactory.BugSubmissionPageFactory;
import pagefactory.LoginPageFactory;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class BugSubmissionSteps {
    public WebDriver driver;
    public BugSubmissionPageFactory bugPageFactory;
    public LoginPageFactory loginPageFactory;

    @Before
    public void setup(){

        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver_win32.exe");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("http://localhost:8080/");
        bugPageFactory = new BugSubmissionPageFactory(driver);
        loginPageFactory = new LoginPageFactory(driver);
    }


    @Given("I am logged in and on The bug submission page")
    public void iAmLoggedInAndOnTheBugSubmissionPage() {
        loginPageFactory.login("email@email.com", "pass");
        WebElement goButton = driver.findElement(By.id("goToSubmission"));
        goButton.click();

    }

    @When("I fill out the title {string}")
    public void iFillOutTheTitle(String title) {
        bugPageFactory.inputTitle(title);
    }

    @And("I fill out the description {string}")
    public void iFillOutTheDescription(String description) {
        bugPageFactory.inputDescription(description);
    }

    @And("I input out the {string}")
    public void iInputOutThe(String severity) {
        Select severityElement = new Select(driver.findElement(By.id("severity")));
        severityElement.selectByValue(severity);
    }

    @And("I set the {string}")
    public void iSetThe(String urgency) {
        Select urgencyElement = new Select(driver.findElement(By.id("urgency")));
        urgencyElement.selectByValue(urgency);
    }

    @And("I click submit")
    public void iClickSubmit() {
        bugPageFactory.clickSubmit();
    }

    @Then("a {string} confirmation will appear")
    public void aConfirmationWillAppear(String message) {
        WebElement msg=driver.findElement(By.id("feedback-msg"));
        assertTrue(msg.getText().contains(message));
    }

    @After
    public void teardown() {
        this.driver.quit();
    }

}
