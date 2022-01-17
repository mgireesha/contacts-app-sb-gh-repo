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
import java.time.Duration;
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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
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
        try{
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
        String esToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY0MzMzMjczNSwiaWF0IjoxNjQyNDQzNzAyfQ.VxXnTU5fLbCN1dI5jw42TN_v43_J8JVJI0kaOVCc78E";
        System.out.println("esToken: "+esToken);
        JSONObject responseObj = null;
        RestTemplate restTemplate = new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofMillis(60000))
				.setReadTimeout(Duration.ofMillis(60000))
				.build();
        JSONObject reqJson = new JSONObject();
		reqJson.put("toAddress", registeredEmail);
		reqJson.put("messageBody", BODY);
		reqJson.put("messageSubject", "Contacts App Password Reset");
		reqJson.put("requestingApplication", "TODO");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(esToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://send-email-sb.herokuapp.com/sendEmail");
		UriComponents uriComponents = builder.build();
		HttpEntity<String> request = new HttpEntity<String>(reqJson.toString(),headers);
		ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.POST,request,String.class);
		responseObj = new JSONObject(response.getBody().toString());
		System.out.println(responseObj.toString());
		if(responseObj.getString("sendStatus").equals("MESSAGE_SENT"))
			isRsNext = true;
        
        }catch (Exception e){ 
            System.err.println("error sending email, cause: " + e);
        }
  
    }
  return isRsNext;
  }
}