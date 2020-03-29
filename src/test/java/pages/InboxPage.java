package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InboxPage extends BasePage {

    @FindBy(xpath = "//div[@role='button' and normalize-space()='Compose']")
    private WebElement compose;

    @FindBy(css = "data-tooltip='Inbox']")
    private WebElement inbox;

    @FindBy(css = "div[role='tab'][aria-label='Social']")
    private WebElement socialTab;

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

    public ReceivedEmailPage openEmail(String emailSubjectValue) {

        retryingGetElementClick(By.xpath( "//table//tr[contains(.,'" + emailSubjectValue + "')]" ) );
        return new ReceivedEmailPage( webDriver );


    }

}
