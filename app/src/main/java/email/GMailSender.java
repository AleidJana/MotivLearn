package email;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

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

//Class is extending AsyncTask because this class is going to perform a networking operation
public class GMailSender extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Register context;
    private Session session;
    private Multipart _multipart;
    ProgressDialog progressDialog;
    //Information to send email
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
        Intent intent=new Intent(context,RegisterCode.class);
        intent.putExtra("userinfo",userinfo);
        context.startActivity(intent);
    }
    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();
        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
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

