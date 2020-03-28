package com.crossover.e2e;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.CommonUtil;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class TestGmail {

    private WebDriver driver;
    private Properties properties = new Properties();

    @Before
    public void setUp() throws Exception {

        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
//        driver.quit();
    }

    /*
     * Please focus on completing the task
     *
     */
    @Test
    public void testSendEmail() throws Exception {

        driver.get("https://mail.google.com/");

        WebElement userElement = driver.findElement( By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        WebDriverWait webDriverWait = new WebDriverWait( driver, 15 );

        Thread.sleep( 10000 );

        webDriverWait.until( ExpectedConditions.elementToBeClickable(driver.findElement( By.name("password"))))
                .sendKeys( properties.getProperty("password") );

        driver.findElement(By.id("passwordNext")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable
                ( By.xpath("//*[@role='button' and (.)='Compose']")) ).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable
                ( By.name("to"))).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));

        // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject") + CommonUtil.getCurrentSystemDateTime();
        String emailBody = properties.getProperty("email.body") + CommonUtil.getCurrentSystemDateTime();

        driver.findElement( By.name( "subjectbox" ) ).sendKeys( emailSubject );
        driver.findElement( By.cssSelector( "[aria-label='Message Body'][role='textbox']" ) ).sendKeys( emailBody );

        driver.findElement( By.cssSelector( "div[data-tooltip='More options'][role='button']" ) ).click();

        WebElement label = webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.xpath( "//div[@role='menuitem']/div[contains(text(),'Label')]")));

        Actions actions = new Actions( driver );
        actions.moveToElement( label ).perform();

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[title='Social']" ))).click();

        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "[data-tooltip='Inbox']" ) )).click();
        driver.findElement( By.cssSelector( "div[role='tab'][aria-label='Social']")).click();

        WebElement receivedEmail = webDriverWait.until( ExpectedConditions.visibilityOf( driver.findElement(
                By.xpath( "//table//tr[contains(.,'" + emailSubject + "')]" ) ) ) );

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                receivedEmail.findElement( By.xpath( "td[3]" ) ) )).click();

        webDriverWait.until( ExpectedConditions.presenceOfElementLocated(
                By.xpath( "//table//tr[contains(.,'" + emailSubject + "')]" ) ) ).click();

//        receivedEmail.findElement( By.xpath( "td[6]" ) ).click();

        WebElement labelHead = webDriverWait.until( ExpectedConditions.presenceOfElementLocated(
                By.xpath( "//div[@data-tooltip='Labels']" ) ) );
        actions.moveToElement( labelHead ).click().perform();

//        webDriverWait.until( ExpectedConditions.elementToBeClickable(
//                By.xpath( "//div[@data-tooltip='Labels']" ) )).click();
//        driver.findElement( By.cssSelector( "div[data-tooltip='Labels']" ) ).click();

        WebElement labelInMail = webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[role='menuitemcheckbox'][title='Social']" )));

        Assert.assertEquals( labelInMail.getAttribute( "aria-checked" ), "true" );
        Assert.assertEquals( driver.findElement( By.cssSelector( "h2.hP" ) ).getText(), emailSubject );
        Assert.assertTrue( driver.findElement( By.cssSelector( "div[role='listitem']" ) ).getText().contains( emailBody) );

    }
}
