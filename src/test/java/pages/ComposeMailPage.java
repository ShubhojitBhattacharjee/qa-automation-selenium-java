package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ComposeMailPage extends BasePage {

    @FindBy(name = "to")
    private WebElement recepient;

    @FindBy(name = "subjectbox")
    private WebElement subject;

    @FindBy(css = "[aria-label='Message Body'][role='textbox']")
    private WebElement emailBody;

    @FindBy(css = "div[data-tooltip='More options'][role='button']")
    private WebElement moreOptions;

    @FindBy(xpath = "//div[@role='menuitem']/div[contains(text(),'Label')]")
    private WebElement applyLabel;

    @FindBy(xpath = "//*[@role='button' and text()='Send']")
    private WebElement sendButton;

    public ComposeMailPage(WebDriver driver) {
        super( driver );
    }

    public void writeEmail(String userName, String subjectValue, String body) {

        waitForElementToBeVisible( recepient );
        recepient.sendKeys( userName );

        subject.sendKeys( subjectValue );
        emailBody.sendKeys( body );
    }

    public InboxPage setLabelAndSendEmail(String labelValue) {

        moreOptions.click();
        moveToElement( applyLabel );

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[title='Social']" ))).click();

        sendButton.click();
        return new InboxPage( webDriver );
    }
}
