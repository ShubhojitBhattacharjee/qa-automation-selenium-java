package com.crossover.e2e;

import org.junit.Assert;
import org.junit.Test;
import util.CommonUtil;

import java.awt.*;

public class GMailTest extends BaseTest{

    /*
     * Please focus on completing the task
     *
     */
    @Test
    public void testSendEmail() throws AWTException {

        final String userName = properties.getProperty("username");
        final String recipient = userName + "@gmail.com";
        final String password = properties.getProperty("password");
        final String emailSubject = properties.getProperty("email.subject") + CommonUtil.getCurrentSystemDateTime();
        final String emailBody = properties.getProperty("email.body") + CommonUtil.getCurrentSystemDateTime();

        reportLogger( "Login to Gmail" );
        inboxPage = loginPage.loginToGmail(userName, password);

        reportLogger( "Compose an email from subject and body as mentioned" );
        composeMailPage = inboxPage.composeNewEmail();
        composeMailPage.writeEmail(recipient, emailSubject, emailBody);

        reportLogger( "Label email as 'Social' and Send the Email" );
        inboxPage = composeMailPage.setLabelAndSendEmail("Social");

        reportLogger( "Wait for the email to arrive in the Inbox" );
        inboxPage.checkNewEmail(emailSubject);

        reportLogger( "Mark email as starred" );
        inboxPage.starEmail(emailSubject);

        reportLogger( "Open the received email" );
        receivedEmailPage = inboxPage.openEmail(emailSubject);

        reportLogger( "Verify email came under proper Label i.e. 'Social'" );
        Assert.assertEquals( receivedEmailPage.getEmailLabel(), "true" );

        reportLogger( "Verify the subject and body of the received email" );
        Assert.assertEquals( receivedEmailPage.getEmailSubject(), emailSubject );
        Assert.assertTrue( receivedEmailPage.getEmailBody().contains(emailBody) );

    }

}
