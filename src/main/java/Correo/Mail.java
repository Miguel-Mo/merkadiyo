package Correo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
    public Mail() {}

    /**
     * Método para enviar Gmail (extraido de https://www.campusmvp.es/recursos/post/como-enviar-correo-electronico-con-java-a-traves-de-gmail.aspx)
     * @param destinatario correo al que va dirigido
     * @param asunto asunto del correo
     * @param cuerpo cuerpo del correo
     */
    public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "testdatalean@gmail.com";  //Para la dirección testdatalean@gmail.com
        String password="uno234$$";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        //utilizamos message y le insertamos lo que tenemos y necesitamos
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(destinatario));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport.send(message);
            System.out.println("Correo enviado a "+destinatario);
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }


}
