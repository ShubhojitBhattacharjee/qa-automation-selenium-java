package com.crossover.e2e;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CommonUtil;

public class GMailTest extends BaseTest{

    /*
     * Please focus on completing the task
     *
     */
    @Test
    public void testSendEmail() throws Exception {

        reportLogger( "Login to Gmail" );
        driver.get("https://mail.google.com/");

        WebElement userElement = driver.findElement( By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep( 10000 );

        webDriverWait.until( ExpectedConditions.elementToBeClickable(driver.findElement( By.name("password"))))
                .sendKeys( properties.getProperty("password") );

        driver.findElement(By.id("passwordNext")).click();

        reportLogger( "Compose an email from subject and body as mentioned" );
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

        reportLogger( "Label email as 'Social'" );
        WebElement label = webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.xpath( "//div[@role='menuitem']/div[contains(text(),'Label')]")));

        Actions actions = new Actions( driver );
        actions.moveToElement( label ).perform();

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "div[title='Social']" ))).click();

        reportLogger( "Send the email to the same account which was used to login" );
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                By.cssSelector( "[data-tooltip='Inbox']" ) )).click();
        driver.findElement( By.cssSelector( "div[role='tab'][aria-label='Social']")).click();

        reportLogger( "Wait for the email to arrive in the Inbox" );
        WebElement receivedEmail = webDriverWait.until( ExpectedConditions.visibilityOf( driver.findElement(
                By.xpath( "//table//tr[contains(.,'" + emailSubject + "')]" ) ) ) );

        reportLogger( "Mark email as starred" );
        webDriverWait.until( ExpectedConditions.elementToBeClickable(
                receivedEmail.findElement( By.xpath( "td[3]" ) ) )).click();

        reportLogger( "Open the received email" );
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
        reportLogger( "Verified that email came under proper Label i.e. 'Social'" );

        Assert.assertEquals( driver.findElement( By.cssSelector( "h2.hP" ) ).getText(), emailSubject );
        Assert.assertTrue( driver.findElement( By.cssSelector( "div[role='listitem']" ) ).getText().contains( emailBody) );
        reportLogger( "Verified that subject and body of the received email" );
    }

    @Test
    public void testSendEmailPOM() throws InterruptedException {

        final String userName = properties.getProperty("username");
        final String recipient = userName + "@gmail.com";
        final String password = properties.getProperty("password");
        final String emailSubject = properties.getProperty("email.subject") + CommonUtil.getCurrentSystemDateTime();
        final String emailBody = properties.getProperty("email.body") + CommonUtil.getCurrentSystemDateTime();

        inboxPage = loginPage.loginToGmail(userName, password);
        composeMailPage = inboxPage.composeNewEmail();

        composeMailPage.writeEmail(recipient, emailSubject, emailBody);
        inboxPage = composeMailPage.setLabelAndSendEmail("Social");

        inboxPage.checkNewEmail(emailSubject);
        inboxPage.starEmail(emailSubject);
        inboxPage.readEmail(emailSubject);

//        Assert.assertEquals( inboxPage.getEmailLabel(), "true" );
        Assert.assertEquals( inboxPage.getEmailSubject(), emailSubject );
        Assert.assertTrue( inboxPage.getEmailBody().contains(emailBody) );

    }

}
