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

public class LoginSteps {
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

    @When("I enter username as {string}")
    public void enterUsername(String username){
        if(!(username.equals("empty"))){
            login.inputUserName(username);
        }
    }
    @When("I enter password as {string}")
    public void enterPassword(String password){
        if(!(password.equals("empty"))){
            login.inputPassword(password);
        }
    }
    @When("I click Login button")
    public void pressLoginBtn(){
        login.clickSubmitBtn();
    }
    @Then("I can see all bug page")
    public void landingPage(){
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertEquals(bugPageFactory.createBugBtnDisplayed(),true);
    }

    @Then("I can the error message as {string}")
    public void invalidMessage(String expected){
        Assert.assertEquals(login.getErrorMessage(),expected);
    }

    @After
    public void teardown(){
        this.driver.quit();
    }
}
