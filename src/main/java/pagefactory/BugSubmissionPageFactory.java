package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BugSubmissionPageFactory {
    WebDriver webDriver;

    @FindBy(id ="title")
    WebElement titleInput;

    @FindBy(id="description")
    WebElement descriptionInput;

    @FindBy(id="severity")
    Select severityInput;

    @FindBy(id="urgency")
    Select urgencyInput;

    @FindBy(id="submit")
    WebElement submitInput;

    public BugSubmissionPageFactory(WebDriver webDriver){
        this.webDriver=webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void inputTitle(String text){
        titleInput.sendKeys(text);
    }

    public void inputDescription(String text){
        descriptionInput.sendKeys(text);
    }

    public void inputSeverity(String text){
        severityInput.selectByVisibleText(text);
    }

    public void inputUrgency(String text){
        urgencyInput.selectByVisibleText(text);
    }

    public void clickSubmit(){
        submitInput.click();
    }

}
