package pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CommentPageFactory {
    WebDriver webDriver;

    @FindBy(id = "comment-textarea")
    WebElement commentInput;

    @FindBy(id = "comment-error-div")
    WebElement commentError;

    @FindBy(id = "submit-comment-button")
    WebElement submitCommentButton;

    @FindBy(id = "comment-section-list")
    WebElement section;

    public CommentPageFactory(WebDriver webDriver) {
        this.webDriver = webDriver;

        PageFactory.initElements(webDriver, this);
    }

    public void submitComment(String text) {
        inputCommentText(text);
        pressSubmit();
    }

    public void inputCommentText(String text) {
        commentInput.sendKeys(text);
    }

    public void pressSubmit() {
        submitCommentButton.click();
    }

    public WebElement getAlertBox() {
        return commentError.findElement(By.className("alert"));
    }

    public List<WebElement> getComments() {
        return section.findElements(By.className("comments"));
    }
}
