package pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BugPageFactory {
    WebDriver webDriver;
    WebDriverWait wait;
    @FindBy(id ="goToSubmission")
    WebElement createBugBtn;

    public BugPageFactory(WebDriver webDriver)
    {
        this.webDriver= webDriver;
        PageFactory.initElements(webDriver,this);
        this.wait = new WebDriverWait(this.webDriver, Duration.ofMillis(1000));
    }

    public void getWebpage(){this.webDriver.get("http://localhost:8080/view");}
    public void getFirstBugDetails(){
//        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
       try {
           Thread.sleep(3000);
       }catch (Exception e){
           e.printStackTrace();
       }
       WebElement button= this.webDriver.findElement(By.xpath("//*[@id=\'bugViewer\']/div/div[2]/a"));
       button.click();
    }

    public boolean createBugBtnDisplayed(){
        return createBugBtn.isDisplayed();
    }

    public String  getTitle(){return this.webDriver.getTitle();}
}
