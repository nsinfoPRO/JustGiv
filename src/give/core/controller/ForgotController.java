/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.controller;

import give.base.controller.BaseController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotController extends BaseController implements Initializable {

    @FXML
    private TextField codeInput, emailInput, passwordInput;
    String email, check, password;
    @FXML
    private Button checkBtn, sendBtn, changeBtn;
    Integer number;
    @FXML
    Label infoInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void sendBtnPressed(ActionEvent e) throws SQLException {

        email = emailInput.getText();
        String queryString = "SELECT EXISTS(SELECT * FROM user WHERE email=\"" + email + "\")";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(queryString);

        try {
            while (rset.next()) {
                check = rset.getString(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (check.equals("1")) {

            Random rand = new Random();
            number = rand.nextInt(999999999) + 1;
            sendMessage(email, number);
            emailInput.setVisible(false);
            codeInput.setVisible(true);
            infoInput.setText("Go to your email and send code");
            infoInput.setLayoutY(160);
            checkBtn.setVisible(true);
            sendBtn.setVisible(false);

        }else{
        
            infoInput.setText("Email don't exists in our database");
        
        }

        System.out.println(number);

    }

    public void checkBtnPressed() {

        if (codeInput.getText().equals(number.toString())) {

            codeInput.setVisible(false);
            checkBtn.setVisible(false);
            passwordInput.setVisible(true);
            changeBtn.setVisible(true);
            changeBtn.setDisable(true);
            infoInput.setText("Enter your new password");

        } else {
            infoInput.setText("Wrong code");
        }

    }

    public void changeBtnPressed() throws SQLException {

        if (!passwordInput.getText().equals("")) {
            String command = "UPDATE `prebid`.`user` SET `password`=MD5(\"" + passwordInput.getText() + "\") WHERE `email`=\"" + email + "\"";

            try {
                stmt.executeUpdate(command);
                infoInput.setText("Password changed");
                changeBtn.setDisable(true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void sendMessage(String email, Integer number) {

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("langlangfan465@gmail.com", "Activefitness101");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("langlangfan465@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Recovery");
            message.setText("Code for password recovery access is " + number);

            Transport.send(message);

            System.out.println("Message sent!");

        } catch (MessagingException exc) {
            System.out.println(exc.getMessage());
        }

    }

    public void closeBtnPressed(ActionEvent event) throws IOException{
    
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.getResourceURL("view/fxml_login.fxml"));        
        Scene homeScene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(homeScene);
        stage.initStyle(StageStyle.UNDECORATED);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // Get stage and transfer to home scene
        stage.show();
        
    }
    
    public void keyTyped(KeyEvent e){
    
        int passwordLength = passwordInput.getText().trim().length();
        if(passwordLength >= 7){
            changeBtn.setDisable(false);

        }else{
            changeBtn.setDisable(true);
        }
    }
    
    
    
}
