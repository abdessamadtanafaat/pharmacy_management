package controllers;

import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class MainController implements Initializable {
	
    @FXML
    private Button btn_sinscrire;

    @FXML
    private Button btn_seconnecter;

    @FXML
    private VBox VBox;
    
    private Parent fxml;

    @FXML
    void openSignIn() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(0.7),VBox);
		t.setToX(VBox.getLayoutX() * 8.7);
		t.play();
		t.setOnFinished(e ->{
			try {
				setFxml(FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml")));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		});
    }

    @FXML
    void openSignUp() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(0.7),VBox);
		t.setToX(VBox.getLayoutX() * 0.5);
		t.play();
		t.setOnFinished(e ->{
			try {
				setFxml(FXMLLoader.load(getClass().getResource("/interfaces/SignUp.fxml")));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		});
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(0.7),VBox);
		t.setToX(VBox.getLayoutX() * 8.5);
		t.play();
		t.setOnFinished(e ->{
			try {
				setFxml(FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml")));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	public Parent getFxml() {
		return fxml;
	}

	public void setFxml(Parent fxml) {
		this.fxml = fxml;
	}



}
