package mplayer.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import mplayer.classes.Database;
import mplayer.MPlayer;
import mplayer.classes.User;

public class LoginController {
    
    @FXML
    private TextField login_field;
    
    @FXML
    private PasswordField password_field;
    
    @FXML
    private Button login_button;
    
    @FXML
    private Button Registration_button;
    
    @FXML
    public void login(ActionEvent event) throws SQLException{
    
    	Window owner = login_button.getScene().getWindow();
    	
    	System.out.println(login_field.getText());
    	System.out.println(password_field.getText());
    	
    	if(login_field.getText().isEmpty()) { //Проверка на пустой логин
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter your email id");
            return;
        }
        if(password_field.getText().isEmpty()) { //Проверка на пустой пароль
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter a password");
            return;
        }
        
        //Принимаем данны из полей с логином и паролем
    	String emailId = login_field.getText();
    	String password = password_field.getText();
    	//Создаем объект с базой данных
    	Database db = new Database();
    	User user = db.getUser(emailId, password);  //Проверка на правильность ввода данных

    	if(user == null) {
            infoBox("Please enter correct Login and Password or tap on Registration", null, "Failed");  //Если введено неправильно
    	} else {
    	 //Если введено всё правильно
            login_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MPlayer.class.getResource("resources/Tracks.fxml"));//открытие библиотеки пользователя
            try {
                loader.load();
            } catch (IOException e){
                System.out.println("Не удалось найти путь до fxml файла с окном библиотеки пользователя");;
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();   
    	}
    }
//    Открытия окна регистрации
    @FXML
        public void registration(ActionEvent event) {
            
            Registration_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MPlayer.class.getResource("resources/Registration.fxml"));
            try {
                loader.load();
            } catch (IOException e){
                System.out.println("Не удалось найти путь до fxml файла с окном регистрации");
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
                }
    //Работа окна информации
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    //Вывод ошибки о неправильно введеных или в принципе неведенных данных
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}