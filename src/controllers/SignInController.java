package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignInController implements Initializable{
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	
    @FXML
    private VBox VBox;
    @FXML
    private TextField txt_userName;

    @FXML
    private TextField txt_password;

    @FXML
    private Button btn_passwordForgoten;

    @FXML
    private Button btn_seconnecter;
    
    private Parent fxml;

    @FXML
    void openHome() {
        String nom = txt_userName.getText();
        String pass = txt_password.getText();
        String sql = "SELECT * FROM admin WHERE userName = ? AND password = ?";
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, nom);
            st.setString(2, pass);
            result = st.executeQuery();

            if (result.next()) {
                System.out.println("Bienvenue !");
                VBox.getScene().getWindow().hide();
                Stage home = new Stage();
                try {
                    fxml = FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
                    Scene scene = new Scene(fxml);
                    home.setScene(scene);
                    home.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Nom d'utilisateur et mot de passe incorrects !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    @FXML
    void sendPassword() {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cnx=ConnectionMysql.connectionDB();
		
	}

	

}
