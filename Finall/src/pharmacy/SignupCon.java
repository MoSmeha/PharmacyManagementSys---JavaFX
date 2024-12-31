package pharmacy;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignupCon implements Initializable {

    @FXML
    private Button SP_createbtn;

    @FXML
    private TextField SP_password;

    @FXML
    private TextField SP_phone;

    @FXML
    private TextField SP_repeatpass;

    @FXML
    private TextField SP_username;

    @FXML
    private Button SU_close;
    
    
    private PreparedStatement prepare;
    private Connection connect;
    
    
    private double x= 0;
    private double y= 0;
    
    
    	
    public void registerUser() {
        String usernameText = SP_username.getText();
        String passwordText = SP_password.getText();
        String repeatPasswordText = SP_repeatpass.getText();
        String phoneText = SP_phone.getText();

        Alert alert;

        if (usernameText.isEmpty() || passwordText.isEmpty() || repeatPasswordText.isEmpty() || phoneText.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        if (!passwordText.equals(repeatPasswordText)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password and Repeat Password do not match");
            alert.showAndWait();
            return;
        }

        
        try {
            int phoneNumber = Integer.parseInt(phoneText);
            if (phoneNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be a valid non-negative integer");
            alert.showAndWait();
            return;
        }

        String sql = "INSERT INTO user (username, password, phone) VALUES (?, ?, ?)";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameText);
            prepare.setString(2, passwordText);
            prepare.setString(3, phoneText);

            int result = prepare.executeUpdate();

            if (result > 0) {
            	alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Success");
	            alert.setHeaderText(null);
	            alert.setContentText("User registered successfully");
	            alert.showAndWait();

	            
				
	            SP_createbtn.getScene().getWindow().hide(); 
	            Parent root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
	            Stage stage = new Stage();
	            Scene scene = new Scene(root);

	            root.setOnMousePressed((MouseEvent event) -> {
	                x = event.getSceneX();
	                y = event.getSceneY();
	            });

	            root.setOnMouseDragged((MouseEvent event) -> {
	                stage.setX(event.getScreenX() - x);
	                stage.setY(event.getScreenY() - y);
	            });

	            stage.initStyle(StageStyle.TRANSPARENT);

	            stage.setScene(scene);
	            stage.show();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Registration failed");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    	
    	public void close() {
    		System.exit(0);
    	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
