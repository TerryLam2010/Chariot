
package cn.terrylam.framework.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author TerryLam
 */
public class SendMailUtils extends Thread {

    private Session pSession;
    private MimeMessage pMessage;
    private Multipart pMP = new MimeMultipart();
    private Properties pProps = new Properties();

    private String from = null;
    private String to = null;
    private String[] toArray = null;
    private String[] ccArray = null;
    private String[] bccArray = null;

    public SendMailUtils(String smtpServer) {
        this(smtpServer, null);
    }

    public class SimpleAuthenticator extends Authenticator {
        private PasswordAuthentication pa = null;

        public SimpleAuthenticator(String userName, String password) {
            pa = new PasswordAuthentication(userName, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return pa;
        }
    }


    public SendMailUtils(String smtpServer, String userName, String password) {
        pProps.put("mail.user", userName);
        pProps.put("mail.from", userName);
        pProps.put("mail.smtp.user", userName);
        pProps.put("mail.smtp.auth", "true");
        pProps.put("mail.smtp.allow8bitmime", "true");

        pProps.put("mail.host", smtpServer);
        pProps.put("mail.smtp.host", smtpServer);
        //pSession = Session.getDefaultInstance(pProps, authenticator);
        pSession = Session.getInstance(pProps,
                new SimpleAuthenticator(userName, password));

        pSession.setDebug(false);
        pMessage = new MimeMessage(pSession);
    }

    public SendMailUtils(String smtpserver, Authenticator authenticator) {
        pProps.put("mail.host", smtpserver);
        pProps.put("mail.smtp.host", smtpserver);
        //pSession = Session.getDefaultInstance(pProps, authenticator);
        pSession = Session.getInstance(pProps, authenticator);

        pSession.setDebug(false);
        pMessage = new MimeMessage(pSession);
    }

    public void setMainParam(String from, String to, String subject,
                             String content, String contentType) throws
            MessagingException {
        this.from = from;
        this.to = to;
        setSubject(subject);
        setMainText(content, contentType);
    }

    public void setMainParam(String from, String[] to, String subject,
                             String content, String contentType) throws
            MessagingException {
        this.from = from;
        this.toArray = to;
        setSubject(subject);
        setMainText(content, contentType);
    }

    public void setCCParam(String[] cc, String[] bcc) {
        ccArray = cc;
        bccArray = bcc;
    }

    public void setFrom(String _from) throws MessagingException {
        if (_from == null) {
            pMessage.setFrom();
            from = null;
            return;
        }
        InternetAddress fromAddress = new InternetAddress(_from);
        pMessage.setFrom(fromAddress);
        from = null;
    }

    public void setTo(String _to) throws MessagingException {
        if (_to == null)
            return;
        InternetAddress toAddress = new InternetAddress(_to);
        InternetAddress[] address = {toAddress};
        pMessage.setRecipients(Message.RecipientType.TO, address);
        to = null;
    }

    public void setToArray(String[] to) throws AddressException,
            MessagingException {
        if (to == null || to.length == 0)
            return;
        int length = to.length;
        InternetAddress toAddresses[] = new InternetAddress[length];
        for (int i = 0; i <= length - 1; i++)
            toAddresses[i] = new InternetAddress(to[i]);
        pMessage.setRecipients(Message.RecipientType.TO, toAddresses);
        toArray = null;

    }

    public void setCCArray(String[] cc) throws AddressException,
            MessagingException {
        if (cc == null || cc.length == 0)
            return;
        int ccLength = cc.length;
        InternetAddress ccAddresses[] = new InternetAddress[ccLength];
        for (int i = 0; i <= ccLength - 1; i++)
            ccAddresses[i] = new InternetAddress(cc[i]);
        pMessage.setRecipients(Message.RecipientType.CC, ccAddresses);
        ccArray = null;
    }

    public void setBCCArray(String[] bcc) throws AddressException,
            MessagingException {
        if (bcc == null || bcc.length == 0)
            return;
        int ccLength = bcc.length;
        InternetAddress ccAddresses[] = new InternetAddress[ccLength];
        for (int i = 0; i <= ccLength - 1; i++)
            ccAddresses[i] = new InternetAddress(bcc[i]);
        pMessage.setRecipients(Message.RecipientType.BCC, ccAddresses);
        bccArray = null;
    }

    public void setSubject(String subject) throws MessagingException {
        pMessage.setSubject(subject, "GBK");
    }

    public void setMainText(String texts, String contentType) throws
            MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();
        if (contentType == null || contentType.trim().length() == 0)
            contentType = "text/plain";

        mbp.setContent(texts, contentType);
        pMP.addBodyPart(mbp, 0);

    }

    public void setMainText(String texts) throws MessagingException {
        setMainText(texts, "text/plain");
    }


    public void addAttachment(String filename) throws MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filename);
        mbp.setDataHandler(new DataHandler(fds));
        mbp.setDescription("attachment");
        mbp.setFileName(filename);
        pMP.addBodyPart(mbp);
    }


    public void send() throws MessagingException {
        setFrom(from);
        setTo(to);
        setToArray(toArray);
        setCCArray(ccArray);
        setBCCArray(bccArray);

        pMessage.setHeader("X-Mailer", "terry Java Mailer");
        pMessage.setSentDate(new Date());
        pMessage.setContent(pMP);
        Transport.send(pMessage);
    }

    public void newMessage() {
        pMessage = new MimeMessage(pSession);
    }

    public void close() throws NoSuchProviderException, MessagingException {
        pSession.getTransport().close();
    }

    public void run() {
        try {
            send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void setDebug(boolean b) {
        pSession.setDebug(b);
    }

    public static void main(String args[]) {
        try {
            SendMailUtils mail = new SendMailUtils(args[0], args[1], args[2]);
            mail.setDebug(true);
            mail.setMainParam(args[1], args[3], args[4], args[5], "text/plain");
            mail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
