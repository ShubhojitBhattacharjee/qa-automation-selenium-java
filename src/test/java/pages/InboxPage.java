package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InboxPage extends BasePage {

    @FindBy(xpath = "//*[@role='button' and (.)='Compose']")
    private WebElement compose;

    @FindBy(css = "data-tooltip='Inbox']")
    private WebElement inbox;

    @FindBy(css = "div[role='tab'][aria-label='Social']")
    private WebElement socialTab;

    @FindBy(xpath = "//div[@data-tooltip='Labels']")
    private WebElement labelHead;

    @FindBy(css = "h2.hP")
    private WebElement emailSubject;

    @FindBy(css = "div[role='listitem']")
    private WebElement emailBody;

    WebElement receivedEmail;

    public InboxPage(WebDriver driver) {
        super( driver );
    }

    public ComposeMailPage composeNewEmail() {

        waitForElementToBeClickable( compose );
        compose.click();
        return new ComposeMailPage( webDriver );
    }

    public void checkNewEmail(String emailSubject) {

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "[data-tooltip='Inbox']" ) )).click();
        socialTab.click();

        receivedEmail = webDriverWait.until( ExpectedConditions.visibilityOf( webDriver.findElement(
                By.xpath( "//table//tr[contains(.,'" + emailSubject + "')]" ) ) ) );

    }

    public void starEmail(String emailSubject) {

        retryingGetElementClick( By.xpath( "//table//tr[contains(.,'" + emailSubject + "')]/td[3]" ) );
    }

    public void readEmail(String emailSubjectValue) {

        retryingGetElementClick(By.xpath( "//table//tr[contains(.,'" + emailSubjectValue + "')]" ) );

        waitForElementToBeClickable( emailSubject );

//        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
//        executor.executeScript("arguments[0].click();", labelHead);

//        moveToElement( labelHead );
//        labelHead.click();
    }

    public String getEmailLabel() {

        return webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[role='menuitemcheckbox'][title='Social']" )))
                .getAttribute("aria-checked");
    }

    public String getEmailSubject() {
        return emailSubject.getText();
    }

    public String getEmailBody() {
        return emailBody.getText();
    }
}
