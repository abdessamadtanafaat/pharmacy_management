package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable{
	
	private Parent fxml;
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private AnchorPane root;
    
    @FXML
    private Label txt_nomuser;

    @FXML
    void acceuil(MouseEvent event) {
    	
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acceuil.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}

    }

    @FXML
    void clients(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Clients.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void factures(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Factures.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void fournisseurs(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Fournisseurs.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void historique(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Historique.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void produits(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/produits.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void ventes(MouseEvent event) {
    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Ventes.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();
		try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acceuil.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
		
		String sql = "select userName from admin";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			String nom=null;
			if(result.next()) {
				nom=result.getString("userName");
			}
			txt_nomuser.setText(nom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	

}
