package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import pagefactory.CommentPageFactory;

import java.util.List;
import java.time.Duration;

public class CommentCreationSteps {

    public WebDriver driver;
    public CommentPageFactory commentPageFactory;

    @Before
    public void setup() {
        //Setup should only be done once or will run test twice
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_win32.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("http://localhost:8080/");

        commentPageFactory = new CommentPageFactory(driver);
    }

    @Given("I am logged in as John Smith")
    public void i_am_logged_in_as_john_smith() {
        // Write code here that turns the phrase above into concrete actions

    }

    @When("I click on the first bug")
    public void i_click_on_the_first_bug() {
        // Write code here that turns the phrase above into concrete actions

    }
    @When("I input a comment")
    public void i_input_a_comment() {
        // Write code here that turns the phrase above into concrete actions
        commentPageFactory.inputCommentText("This is a comment. Hello from Selenium!");
    }
    @Then("I should see my comment")
    public void i_should_see_my_comment() {
        // Write code here that turns the phrase above into concrete actions
        List<WebElement> comments = commentPageFactory.getComments();
        WebElement commentText = comments.get(0).findElement(By.className("card-text"));
        String createdText = commentText.getText();
        Assert.assertEquals(createdText, "This is a comment. Hello from Selenium!");
    }

    @When("I input {string} as a comment")
    public void i_input_text_as_a_comment(String text) {
        // Write code here that turns the phrase above into concrete actions
        if (text.equals("empty")) {
            commentPageFactory.inputCommentText("");
        }
        commentPageFactory.inputCommentText(text);
    }


    @When("I press the submit button")
    public void i_press_the_submit_button() {
        // Write code here that turns the phrase above into concrete actions
        commentPageFactory.pressSubmit();
    }
    @Then("I should see an alert")
    public void i_should_an_alert() {
        // Write code here that turns the phrase above into concrete actions
        WebElement errorElement = commentPageFactory.getAlertBox();
        String text = errorElement.getText();
        Assert.assertEquals(text, "Make sure your comment is between 10 and 1000 characters!");
    }

    @After
    public void teardown() {
        this.driver.quit();
    }

}
