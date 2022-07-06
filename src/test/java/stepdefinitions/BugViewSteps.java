package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pagefactory.BugPageFactory;
import pagefactory.LoginPageFactory;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BugViewSteps {
    public WebDriver driver;
    public BugPageFactory bugPageFactory;
    public LoginPageFactory login;

    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_win32.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.get("http://localhost:8080/");

        bugPageFactory = new BugPageFactory(driver);
        login =new LoginPageFactory(driver);
    }

    @Given("I am at loginPage")
    public void getLoginPage(){
        login.getLoginPage();

    }
    @When("I tried to enter page url")
    public void openUrl(){
        bugPageFactory.getWebpage();

    }
    @Then("I will get back to login page")
    public void checkLandingPage(){
        Assert.assertEquals(bugPageFactory.getTitle(),"Login");
    }

    @When("I login with valid inputs")
    public void login(){
        login.login("email@email.com","pass");
    }
    @When("I click on view full details of first bug")
    public void clickFullDetails() {
            bugPageFactory.getFirstBugDetails();
    }
    @Then("I will go to bug page")
    public void checkLandingPageTitle(){
        System.out.println(bugPageFactory.getTitle());
        Assert.assertEquals(bugPageFactory.getTitle(),"Bug");
    }

    @After
    public void teardown(){
        this.driver.quit();
    }

}

