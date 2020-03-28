package com.crossover.e2e;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class TestGmail {

    private WebDriver driver;
    private Properties properties = new Properties();

    @Before
    public void setUp() throws Exception {

//        GmailQuickstart.authorizeGmail();

        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );

//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
//        capabilities.setCapability("chrome.binary",
//                properties.getProperty("webdriver.chrome.driver"));
//        capabilities.setCapability( ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(options);

//        driver = new ChromeDriver();
    }

    public void tearDown() {
        driver.quit();
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

        WebDriverWait webDriverWait = new WebDriverWait( driver, 60 );

        Thread.sleep( 10000 );

        webDriverWait.until( ExpectedConditions.visibilityOf(driver.findElement( By.name("password"))))
                .sendKeys( properties.getProperty("password") );

        driver.findElement(By.id("passwordNext")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable
                ( By.xpath("//*[@role='button' and (.)='Compose']")) ).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable
                ( By.name("to"))).clear();

//        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

        // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body");

    }
}
