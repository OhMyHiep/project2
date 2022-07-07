package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.Generated;

@Generated
public class UpdatePageFactory {
    WebDriver webDriver;

    @FindBy(id="update-severity")
    Select updateSeverity;

    @FindBy(id="update-urgency")
    Select updateUrgency;

    @FindBy(id="assign-to")
    Select assignTo;

    @FindBy(id="update-status")
    Select updateStatus;

    @FindBy(id ="update-input")
    WebElement submitBtn;

    public UpdatePageFactory(WebDriver webDriver){
        this.webDriver=webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void inputSeverity(String text){
        updateSeverity.selectByVisibleText(text);
    }

    public void inputUrgency(String text){
        updateUrgency.selectByVisibleText(text);
    }

    public void inputAssignee(String text){
        assignTo.selectByVisibleText(text);
    }

    public void inputStatus(String text){
        updateStatus.selectByVisibleText(text);
    }

    public void clickUpdate(){
        submitBtn.click();
    }


}
