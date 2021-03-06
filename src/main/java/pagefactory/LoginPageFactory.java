package pagefactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Generated;

@Generated
public class LoginPageFactory {
    WebDriver webDriver;

    @FindBy(id = "username")
    WebElement usernameInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "submit")
    WebElement submitButton;

    @FindBy(xpath = "//*[@id=\'login-error-div\']/div")
    WebElement errorMessageDiv;

    public LoginPageFactory(WebDriver webDriver){
        this.webDriver=webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void login(String username,String password){
        inputUserName(username);
        inputPassword(password);
        clickSubmitBtn();
    }

    public void inputUserName(String username){usernameInput.sendKeys(username);}

    public void inputPassword(String password){passwordInput.sendKeys(password);}

    public void clickSubmitBtn(){submitButton.click();}

    public void getLoginPage(){this.webDriver.get("http://localhost:8080/");}

    public String getPageTitle(){return this.webDriver.getTitle();}

    public String getErrorMessage(){ return errorMessageDiv.getText();}
}
