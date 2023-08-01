package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SignUpcontroller implements Initializable{
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private TextField txt_nomuser;

    @FXML
    private TextField txt_adrruser;

    @FXML
    private TextField txt_passuser;

    @FXML
    private Button btn_sinscrire;

    @FXML
    void addInscription() {
    	 String username = txt_nomuser.getText();
	        String mail = txt_adrruser.getText();
	        String password = txt_passuser.getText();

	        // Vérification des champs obligatoires
	        if (username.isEmpty() || mail.isEmpty() || password.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        
	     // Vérification si le compte existe déjà
			try {
				if (checkIfAccountExists(username)) {
					Alert alert = new Alert(AlertType.ERROR, "Le compte existe déjà !", javafx.scene.control.ButtonType.OK);
					alert.showAndWait();
					txt_nomuser.setText("");
	                txt_adrruser.setText("");
	                txt_passuser.setText("");
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

	        String sql = "INSERT INTO admin (userName,password) VALUES (?, ?)";
	        try {
	            st = cnx.prepareStatement(sql);
	            st.setString(1, username);
	            st.setString(2, password);
	            int nbr_ligne_affect = st.executeUpdate();
	            if (nbr_ligne_affect > 0) {
	                // Ajout réussi
	                Alert alert = new Alert(AlertType.INFORMATION, "Compte ajouté avec succès !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	                // Réinitialiser les champs
	                txt_nomuser.setText("");
	                txt_adrruser.setText("");
	                txt_passuser.setText("");
	            } else {
	                // Échec de l'ajout
	                Alert alert = new Alert(AlertType.ERROR, "Échec de l'ajout du compte !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    }
    
	private boolean checkIfAccountExists(String username) throws SQLException {
		String sql = "SELECT * FROM admin WHERE userName = ?";
		st = cnx.prepareStatement(sql);
		st.setString(1, username);
		result = st.executeQuery();
		return result.next();
	}
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();	
	}


}
