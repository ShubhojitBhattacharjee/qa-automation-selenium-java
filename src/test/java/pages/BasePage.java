package pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;

public class BasePage {

    protected WebDriver webDriver;
    private Actions actions;
    protected WebDriverWait webDriverWait;

    public BasePage(WebDriver driver) {

        webDriver = driver;
        webDriverWait = new WebDriverWait( webDriver, 15 );
        PageFactory.initElements( driver, this );
        actions = new Actions( webDriver );
    }

    private WebDriverWait getWebDriverWait() {
        WebDriverWait wait = new WebDriverWait( webDriver, 30);
        wait.pollingEvery( Duration.ofMillis(1000));
        wait.ignoring( NoSuchElementException.class);
        wait.ignoring( ElementNotVisibleException.class);
        wait.ignoring( StaleElementReferenceException.class);
        wait.ignoring( NoSuchFrameException.class);
        return wait;
    }

    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = getWebDriverWait();
        wait.until( ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = getWebDriverWait();
        wait.until( ExpectedConditions.visibilityOf(element));
    }

    public void moveToElement(WebElement element) {

        actions.moveToElement( element ).perform();
    }

    public void retryingGetElementClick(By by) {
        int attempts = 0;
        while(attempts < 30) {
            try {
                webDriver.findElement(by).click();
                break;
            } catch(StaleElementReferenceException e) {
                System.out.println("Found in Attempt = " + attempts);
            } catch (WebDriverException e) {
                System.out.println("Attempt in WebDriverException catch = " + attempts);
            }
            attempts++;
        }
    }

    public void moveMouse() throws AWTException {

        Robot robot = new Robot();
        Dimension i = webDriver.manage().window().getSize();

        int x = (i.getWidth()/4)+50;
        int y = (i.getHeight()/10)+120;

        robot.mouseMove( x, y );
    }


}
