package mplayer.controllers;
//Необходимые библиотеки для работы с СУБД, FXML и Аудио Потоками
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import mplayer.MPlayer;
import mplayer.classes.Database;
import mplayer.classes.User;
import static mplayer.controllers.LoginController.infoBox;


public class RegistrationController {  //Контроллер, отвечающий за работу окна с регистрацией пользователей
    //Переменные кнопок окна Registation.fxml, созданы автоматически
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button Registration_button;

    @FXML
    private TextField login_field;

    @FXML  // Метод, отвечающий за регистрацию Пользователя
    public void registration(ActionEvent event) throws SQLException{
    	Window owner = login_field.getScene().getWindow();
    	
    	if(login_field.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter your email id");
            return;
        }
        if(password_field.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter a password");
            return;
        }
        
    	String login = login_field.getText();
    	String password = password_field.getText();
    	
    	Database db = new Database();
    	User user = db.getUser(login, password);

    	if(user != null) { //Если введено уже существующее имя пользователя
            infoBox("Пользователь существует", null, "Failed");  
    	} else {
            
            boolean IsUserCreated /*Переменная для корректного создания аккаунта пользователя*/ = db.createUser/*Метод создания поля в таблице пользователей*/(login, password);
            
            if (IsUserCreated) {
                Registration_button.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MPlayer.class.getResource("resources/Tracks.fxml"));//открытие библиотеки пользователя
                try {
                    loader.load();
                } catch (IOException e){
                    System.out.println("Ошибка с инициализацией библиотеки");;
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();  
            } else {
                infoBox("Ошибка при создании пользователя", null, "Failed");
            }
    	}
    }
    
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
