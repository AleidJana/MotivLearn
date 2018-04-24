package com.example.jana.motivlearn.email;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.jana.motivlearn.Register;
import com.example.jana.motivlearn.RegisterCode;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends AsyncTask<Void,Void,Void> {
    //Declaring Variables
    private Register context;
    private Session session;
    private Multipart _multipart;
    ProgressDialog progressDialog;
    private String email;
    private String subject;
    private String message;
    private String[] userinfo;
    //Class Constructor
    public GMailSender(Register context, String email, String subject, String message, ProgressDialog p,String[] userinfo){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        _multipart = new MimeMultipart();
        progressDialog=p;
        this.userinfo=userinfo;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        //After Sending An Email Here Will Open A Register Code Page To Verify Registration Code Was Sending
        Intent intent=new Intent(context,RegisterCode.class);
        //Add User Information As Extra To Intent Which Will Use It After Verifying Registration Code
        intent.putExtra("userinfo",userinfo);
        context.startActivity(intent);
    }
    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();
        //Configuring properties for gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        //Creating a new session
        session = Session.getInstance(props,new javax.mail.Authenticator() {
            //Authenticating the password
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
            }
        });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            mm.setContent(message,"text/html");
            Transport.send(mm);
            return null;
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

