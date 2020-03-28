package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "identifierId")
    private WebElement userName;

    @FindBy(id = "identifierNext")
    private WebElement userNext;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(id = "passwordNext")
    private WebElement passwordNext;

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get("https://mail.google.com/");
    }

    public InboxPage loginToGmail(String userrNameValue, String passwordValue) throws InterruptedException {

        userName.sendKeys( userrNameValue );
        userNext.click();

        Thread.sleep( 1000 );

        waitForElementToBeClickable( password );

        password.sendKeys( passwordValue );
        passwordNext.click();

        return new InboxPage(webDriver);
    }
}
