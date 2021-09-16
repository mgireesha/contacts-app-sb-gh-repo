/*
*
*-------Modification History-------
* Sr No         Date             Change No       Author             Description
*------         ----             ---------       ------             ------------
* 01            01 Oct 2017          CH01         Gireesh M T         Initial Version
* 
*
*
*@Author Gireesh M T*
*/
package com.capp.springboot.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    Connection con=null;
    Statement stmt=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    boolean isRsNext=false;
    String NameOfUser="";
    
    public static void main(String[] args) {
		SendEmail send = new SendEmail();
		send.mailPwd("mgireesha73@gmail.com");
	}
    
    public boolean mailPwd(String registeredEmail) {
      try {
            String sql="SELECT * FROM public.CONTACTS_APP_CREDS WHERE USERNAME='"+registeredEmail+"'";                               
            
            con = DbUtil.getConnection();
            System.out.println("connection successfull");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next()){
              isRsNext=true;
              NameOfUser=rs.getString("name");
            }else{isRsNext=false;} 
              
      }catch (SQLException e) {
            e.printStackTrace();
      }catch (DaoException e) {
            e.printStackTrace();
      }finally{
            try {
                DbUtil.closeResources(con, stmt, rs);
            } catch (DaoException e) {
                e.printStackTrace();
            }
    }

        if(isRsNext){
          
          String uuid = UUID.randomUUID().toString();
          String NewPwd=uuid.substring(1,8);
          System.out.println(NewPwd);
            try {
                con = DbUtil.getConnection();
                System.out.println("connection successfull"); 
                String sql="UPDATE public.CONTACTS_APP_CREDS SET OTP=? WHERE USERNAME=?";                               
                ps=con.prepareStatement(sql);
                ps.setString(1, NewPwd);
                ps.setString(2, registeredEmail);
                ps.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }catch (DaoException e) {
                e.printStackTrace();
            }finally{
                try {
                    DbUtil.closeResources(con, ps);
                }catch (DaoException e) {
                    e.printStackTrace();
                }
            }
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "contactsapppwdreset@gmail.com";//
        final String password = "contactsapp@2021";
        //final String username = "hactress6@gmail.com";//
        //final String password = "hotpornstar";
        try{
                Session session = Session.getInstance(props,new Authenticator(){
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(username, password);
                                                                            }
                                            });
        // -- Create a new message --
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("contactsapppwdreset@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(registeredEmail,false));
        msg.setSubject("Contacts App Password Reset");
        //msg.setText("Hi "+NameOfUser+" \n\nYour Password has been reset, Please use below password to login\nNew Password : "+NewPwd);
            
        String encodedregisteredEmail = Base64.getEncoder().encodeToString(registeredEmail.getBytes());
        String doubleEncodedregisteredEmail = Base64.getEncoder().encodeToString(encodedregisteredEmail.getBytes());
        String tripleEncodedregisteredEmail = Base64.getEncoder().encodeToString(doubleEncodedregisteredEmail.getBytes());
        String quadrupleEncodedregisteredEmail = Base64.getEncoder().encodeToString(tripleEncodedregisteredEmail.getBytes());
            
        final String BODY = String.join(
                        System.getProperty("line.separator"),
                        "<h3>Hi <i style='color:#7f10a2'>"+NameOfUser+"</i></h3>",
                        "<h4>Your password reset request has been initiated</h4>",
                        "<h4>Please <a href='https://contacts-app-sb.herokuapp.com/process-request-pwr?uq="+quadrupleEncodedregisteredEmail+"'><button class='btn btn-primary' " +
                  "   style='background-color: #1149ab;\n" + 
                        "    border: none;\n" + 
                        "    border-radius: 5px;\n" +
                        "    color: white;\n" + 
                        "    padding: 5px 20px;\n" + 
                        "    text-align: center;\n" + 
                        "    text-decoration: none;\n" + 
                        "    display: inline-block;\n" + 
                        "    font-size: 12px;\n" + 
                        "    margin: 4px 2px;'><i>click here</i></button></a> to reset your password.</h4>",
                        "<h4>Use OTP :"+NewPwd+"</h4>"
                    );
            msg.setContent(BODY,"text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
        System.out.println("Message sent.");
        }catch (MessagingException e){ 
            System.err.println("error sending email, cause: " + e);
        }
  
    }
  return isRsNext;
  }
}