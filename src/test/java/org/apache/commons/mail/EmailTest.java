// CI/CD test commit

package org.apache.commons.mail;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.*;

public class EmailTest {
    
    private Email email;
    
    // SetUp to create a new email object before testing each test case
    @Before
    public void setUp() {
        email = new SimpleEmail();
    }
    
    // Teardown to set the email back to null after each test case
    @After
    public void tearDown() {
        email = null;
    }
    
    // Test addBcc with valid emails
    @Test
    public void testAddBccWithValidEmails() throws Exception {
        String[] valid_emails = {"test1@gmail.com", "test2@gmail.com"};

        email.addBcc(valid_emails);   
        
        String email1 =  email.getBccAddresses().get(0).getAddress();
        String email2 =  email.getBccAddresses().get(1).getAddress();


        Assert.assertEquals(valid_emails[0], email1);
        Assert.assertEquals(valid_emails[1], email2);
    }
    
    // Test addBcc with a null email
    @Test(expected = EmailException.class)
    public void testAddBccWithNullEmail() throws Exception {
        String[] null_emails = null;
        email.addBcc(null_emails);
    }
    
    // Test addBcc with an empty email list
    @Test(expected = EmailException.class)
    public void testAddBccWithEmptyEmail() throws Exception {
        String[] empty_emails = {};
       email.addBcc(empty_emails);

    }


    // Test addCc with a valid email 
    @Test
    public void testAddCcWithValidEmail() throws Exception {
        String valid_email = "test@gmail.com";

        email.addCc(valid_email);
        
        String test_email = email.getCcAddresses().get(0).getAddress();
        Assert.assertEquals(valid_email, test_email);

    }
    

   // Test addCc with an invalid email
    @Test(expected = EmailException.class)
    public void testAddCcWithInvalidEmail() throws Exception {
        String invalid_email = "invalid email";

        email.addCc(invalid_email);
    }
    
    // Test addHeader with valid name and value
    @Test
    public void testAddHeaderWithValidNameValue() throws Exception {
       String name = "test name";
       String value =  "test value";
        email.addHeader(name, value);

    }

    
    // Test addHeader with null name
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeaderWithNullName() throws Exception {
        String name = null;
        String value =  "test value";
        email.addHeader(name, value);
    }
    
    // Test addHeader with empty value
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeaderWithEmptyValue() throws Exception {
        String name = "test name";
        String value = "";
        email.addHeader(name, value);
    }
    
    // Test addReplyTo with valid email and name
    @Test
    public void testAddReplyToWithValidEmailAndName() throws Exception {
        String valid_email = "test@gmail.com";
        String name = "Zain";
        email.addReplyTo(valid_email, name);

        String test_email = email.getReplyToAddresses().get(0).getAddress();
        String test_name = email.getReplyToAddresses().get(0).getPersonal();
        Assert.assertEquals(valid_email, test_email);
        Assert.assertEquals(name, test_name);

    }
    
    // Test addReplyTo with invalid email
    @Test(expected = EmailException.class)
    public void testAddReplyToWithInvalidEmail() throws Exception {
        String invalid_email = "invalid email";
        String name = "Zain";
        email.addReplyTo(invalid_email, name);
    }
    
    // Test buildMimeMessage with valid inputs
    @Test
    public void testBuildMimeMessageWithValidInputs() throws Exception {
        email.setHostName("google.com");
        email.setFrom("from@gmail.com");
        email.addTo("to@gmail.com");
        email.addCc("cc@gmail.com");
        email.addBcc("bcc@gmail.com");
        email.addReplyTo("reply@gmail.com");
        email.setContent("Test Message", EmailConstants.TEXT_PLAIN);
        email.addHeader("Zain", "Test");

        email.buildMimeMessage();
        
        MimeMessage message = email.getMimeMessage();
        assertEquals(EmailConstants.TEXT_PLAIN, message.getContentType());
    }

    // Test buildMimeMessage when it is already built
    @Test(expected = IllegalStateException.class)
    public void testBuildMimeMessageAlreadyBuilt() throws Exception {
        email.setHostName("google.com");
        email.setFrom("from@gmail.com");
        email.addTo("to@gmail.com");
        email.addCc("cc@gmail.com");
        email.addBcc("bcc@gmail.com");
        email.addReplyTo("reply@gmail.com");
        email.setContent("Test Message", EmailConstants.TEXT_PLAIN);
        email.addHeader("Zain", "Test");

        email.buildMimeMessage();
        
        email.buildMimeMessage();

    }
    
    // Test buildMimeMessage with no from address
    @Test(expected = EmailException.class)
    public void testBuildMimeMessageNoFrom() throws Exception {
        email.setHostName("google.com");
        email.addTo("to@gmail.com");
        
        email.buildMimeMessage();
    }

    // Test buildMimeMessage with no to address
    @Test(expected = EmailException.class)
    public void testBuildMimeMessageNoTo() throws Exception {
        email.setHostName("google.com");
        email.setFrom("from@gmail.com");
        
        email.buildMimeMessage();
    }


     // Test getHostName with session
     @Test
     public void testGetHostNameWithSession() throws Exception {
         String hostname = "google.com";
         email.setHostName(hostname);
         
         Session session = email.getMailSession();
         
         Assert.assertEquals(hostname, session.getProperty(EmailConstants.MAIL_HOST));
         Assert.assertEquals(hostname, email.getHostName());
     }
 
     // Test getHostName without session
     @Test
     public void testGetHostNameWithoutSession() throws Exception {
         String hostname = "google.com";
         email.setHostName(hostname);
                 
         Assert.assertEquals(hostname, email.getHostName());
     }
      
     // Test getHostName with empty hostname
     @Test
     public void testGetHostNameWithEmptyHostname() throws Exception {
         String hostname = "";
         email.setHostName(hostname);
                 
         Assert.assertEquals(email.getHostName(), null);
     }

   
  
    // Test getMailSession with valid hostname
     @Test
     public void testGetMailSessionWithValidHostName() throws Exception {
         String hostname = "google.com";
         email.setHostName(hostname);
         
         Session session = email.getMailSession();
         String email_host_name = session.getProperty(EmailConstants.MAIL_HOST);
         Assert.assertNotNull(session);
         Assert.assertEquals(hostname, email_host_name);
     }

     // Test getMailSession with empty hostname
     @Test(expected = EmailException.class)
     public void testGetMailSessionWithEmptyHostname() throws Exception {
         email.setHostName("");

         email.getMailSession();
     }
   
  
    // Test setFrom with a valid email
    @Test
    public void testSetFromWithValidEmail() throws Exception {
        String valid_email = "test@gmail.com";

        email.setFrom(valid_email);
        String test_email = email.getFromAddress().getAddress();
        Assert.assertEquals(valid_email, test_email);
    }
    
    // Test setFrom with an invalid email
    @Test(expected = EmailException.class)
    public void testSetFromWithInvalidEmail() throws Exception {
        String invalid_email = "invalid email";

        email.setFrom(invalid_email);
    }


    // Test getSentDate with set date
    @Test
    public void testGetSentDateWithSetDate() throws Exception {

        Date setDate = new Date(0);

        email.setSentDate(setDate);
        
        Date receivedDate = email.getSentDate();
        
        Assert.assertEquals(setDate.getTime(), receivedDate.getTime());
    }

    // Test getSocketConnectionTimeout with set timeout
    @Test
    public void testGetSocketConnectionTimeout() throws Exception {
        
        int timeout = 5000;
        email.setSocketConnectionTimeout(timeout);
        
        Assert.assertEquals(timeout, email.getSocketConnectionTimeout());
    }


    
}