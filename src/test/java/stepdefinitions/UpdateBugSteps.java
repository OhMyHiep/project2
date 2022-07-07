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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pagefactory.LoginPageFactory;
import pagefactory.UpdatePageFactory;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class UpdateBugSteps {

    public WebDriver driver;
    public LoginPageFactory loginPageFactory;
    public UpdatePageFactory updatePageFactory;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_win32.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.get("http://localhost:8080/");
        loginPageFactory = new LoginPageFactory(driver);
        updatePageFactory = new UpdatePageFactory(driver);

    }

    @Given("I am logged in and I am on The detail Bug page")
    public void iMLoggedInAndIMOnTheDetailBugPage() {
        loginPageFactory.login("email@email.com", "pass");
        List<WebElement> bugButtons = driver.findElements(By.className("viewAllBtn"));

        bugButtons.get(1).click();
    }

    @When("I chose a severity {string}")
    public void iChoseA(String arg0) {
        Select severityElement = new Select(driver.findElement(By.id("update-severity")));

        if (!arg0.equals("")) severityElement.selectByValue(arg0);
    }

    @And("I input an urgency {string}")
    public void iInputAn(String arg0) {
        Select urgencyElement = new Select(driver.findElement(By.id("update-urgency")));

        if (!arg0.equals("")) urgencyElement.selectByValue(arg0);
    }

    @And("I set the Assignee {string}")
    public void ISetAssignee(String args0) {
        Select assignElement = new Select(driver.findElement(By.id("assign-to")));

        if (!args0.equals("")) assignElement.selectByValue(args0);
    }

    @And("I chose a status {string}")
    public void IChoseStatus(String args0){
        Select assignElement = new Select(driver.findElement(By.id("update-status")));

        if (!args0.equals("")) assignElement.selectByValue(args0);
    }

    @And("I click update")
    public void iClickUpdate() {
        updatePageFactory.clickUpdate();
    }

    @Then("I will see the feedback {string}")
    public void iWillSeeThe(String arg0) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(5000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("feedback")));
        WebElement ele = driver.findElement(By.id("feedback"));
        Assert.assertTrue(ele.getText().contains(arg0));
    }

    @After
    public void tearDown() {
        this.driver.quit();

    }
}

