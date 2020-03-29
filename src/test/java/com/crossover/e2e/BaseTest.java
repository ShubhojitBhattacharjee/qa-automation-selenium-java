package com.crossover.e2e;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ComposeMailPage;
import pages.InboxPage;
import pages.LoginPage;
import pages.ReceivedEmailPage;
import reports.Logs;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected Properties properties = new Properties();
    protected WebDriverWait webDriverWait;

    protected LoginPage loginPage;
    protected InboxPage inboxPage;
    protected ComposeMailPage composeMailPage;
    protected ReceivedEmailPage receivedEmailPage;


    @Before
    public void setUp() throws Exception {

        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        webDriverWait = new WebDriverWait( driver, 15 );

        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void reportLogger(String message) {
        Logs.info( message );
    }

}
