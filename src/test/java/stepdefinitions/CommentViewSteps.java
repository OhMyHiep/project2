package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import pagefactory.CommentPageFactory;

import java.time.Duration;
import java.util.List;

public class CommentViewSteps {

    public WebDriver driver;
    public CommentPageFactory commentPageFactory;

    @Before
    public void setup() {
        commentPageFactory = new CommentPageFactory(driver);
    }

    @When("I click on a bug with comments")
    public void i_click_on_a_bug_with_comments() {
        // Write code here that turns the phrase above into concrete actions

    }
    @Then("I can see comments")
    public void i_can_see_comments() {
        // Write code here that turns the phrase above into concrete actions
//        List<WebElement> comments = commentPageFactory.getComments();
//        Assert.assertTrue(comments.size() > 0);
    }
    @When("I click on a bug with no comments")
    public void i_click_on_a_bug_with_no_comments() {
        // Write code here that turns the phrase above into concrete actions

    }
    @Then("I should see no comments")
    public void i_should_see_no_comments() {
        // Write code here that turns the phrase above into concrete actions
//        List<WebElement> comments = commentPageFactory.getComments();
//        Assert.assertTrue(comments.size() == 0);
    }
}
