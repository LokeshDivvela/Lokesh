import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;      

/* Multi Threading*/
class Mailer extends Thread{ 
	
	private String from = "your mail id";
	private String password = "Your mail password";
	private String to = "";
	private String sub = "";
	private String msg = "";
	
	public void run() {
		Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        //get Session   
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);    
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         message.setSubject(sub);    
         message.setText(msg);    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}   
	}
	
	
    public static void send(String to,String sub,String msg){  
    	Mailer mail = new Mailer();
    	mail.to = to;
    	mail.sub = sub;
    	mail.msg = msg;;
    	
    	mail.start();
    	   
             
    }  
}  