package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ReceivedEmailPage extends BasePage {

    @FindBy(css = "h2.hP")
    private WebElement emailSubject;

    @FindBy(css = "div[role='listitem']")
    private WebElement emailBody;

    @FindBy(xpath = "//div[@data-tooltip='Labels']")
    private WebElement labelPlaceholder;

    @FindBy(css = "div[role='menuitemcheckbox'][title='Social']")
    private WebElement labelInEmail;

    public ReceivedEmailPage(WebDriver driver) {
        super( driver );
    }

    public String getEmailSubject() {
        waitForElementToBeVisible( emailSubject );
        return emailSubject.getText();
    }

    public String getEmailBody() {
        return emailBody.getText();
    }

    public String getEmailLabel() {

        waitForElementToBeVisible( labelPlaceholder );
        Actions actions = new Actions( webDriver );
        actions.moveToElement( labelPlaceholder ).click( labelPlaceholder ).perform();

        waitForElementToBeVisible( labelInEmail);
        return labelInEmail.getAttribute( "aria-checked" );
    }
}
