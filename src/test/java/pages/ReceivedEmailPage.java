package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReceivedEmailPage extends BasePage {

    @FindBy(xpath = "//div[@data-tooltip='Labels']")
    private WebElement labelHead;

    @FindBy(css = "h2.hP")
    private WebElement emailSubject;

    @FindBy(css = "div[role='listitem']")
    private WebElement emailBody;

    public ReceivedEmailPage(WebDriver driver) {
        super( driver );
    }

    public String getEmailLabel() {

        return webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[role='menuitemcheckbox'][title='Social']" )))
                .getAttribute("aria-checked");
    }

    public String getEmailSubject() {
        waitForElementToBeClickable( emailSubject );
        return emailSubject.getText();
    }

    public String getEmailBody() {
        return emailBody.getText();
    }
}
