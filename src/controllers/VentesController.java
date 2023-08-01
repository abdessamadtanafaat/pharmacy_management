package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class VentesController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private DatePicker datecmd;

    @FXML
    private TextField txt_nomproduit;

    @FXML
    private TextField txt_prixunitaire;

    @FXML
    private TextField txt_quantite;

    @FXML
    private TextField txt_mntotal;

    @FXML
    private TextField txt_adresse;

    @FXML
    private TextField txt_nomclient;

    @FXML
    private TextField txt_idclient;

    @FXML
    private TextField txt_searchidproduit;

    @FXML
    private TextField txt_searchcinclient;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_searchidproduit;

    @FXML
    private Button btn_searchcinclient;
    
    @FXML
    private Button btn_affichmnt;
    
    
    @FXML
    void addVente() {
    	String id = txt_searchidproduit.getText();
        String idClient = txt_idclient.getText();
        String quantite = txt_quantite.getText();
        String prixunitaire = txt_prixunitaire.getText();
        LocalDate dateCommande = datecmd.getValue();
        String montant = txt_mntotal.getText();

        // Vérification des champs obligatoires
        if (id.isEmpty() || idClient.isEmpty() || quantite.isEmpty() || prixunitaire.isEmpty() || dateCommande == null || montant.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Insertion de la vente dans la table des ventes
        String sql1 = "INSERT INTO ventes (id_produit, id_client, quantite, prix_unitaire, date_commande, montant) VALUES (?, ?, ?, ?, ?, ?)";
        String sql2= "UPDATE produits SET quantite = quantite - ? WHERE id_produit = ?";
        try {
            st = cnx.prepareStatement(sql1);
            st.setString(1, id);
            st.setString(2, idClient);
            st.setString(3, quantite);
            st.setString(4, prixunitaire);
            st.setDate(5, Date.valueOf(dateCommande));
            st.setString(6, montant);
            int nbr_ligne_affect = st.executeUpdate();
            if (nbr_ligne_affect > 0) {
                // Ajout réussi
                Alert alert = new Alert(AlertType.INFORMATION, "Vente ajoutée avec succès !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                // Réinitialiser les champs
                txt_searchidproduit.setText("");
                txt_nomproduit.setText("");
                txt_quantite.setText("");
                txt_prixunitaire.setText("");
                txt_mntotal.setText("");
                txt_idclient.setText("");
                txt_nomclient.setText("");
                txt_adresse.setText("");
                txt_searchcinclient.setText("");
                datecmd.setValue(null);
                
                //Soustarction de la quantité achetée du stock
                st = cnx.prepareStatement(sql2);
                st.setString(1, quantite);
                st.setString(2, id);
                nbr_ligne_affect = st.executeUpdate();
                if(nbr_ligne_affect>0) {
                	Alert alert1 = new Alert(AlertType.INFORMATION, "Qte achetée restreindre du stock !", javafx.scene.control.ButtonType.OK);
                	alert1.showAndWait();
                }else {
                	Alert alert1 = new Alert(AlertType.ERROR, "Qte achetée n'a pas était modifier du stock !", javafx.scene.control.ButtonType.OK);
                	alert1.showAndWait();
                }
                
            } else {
                // Échec de l'ajout
                Alert alert = new Alert(AlertType.ERROR, "Échec de l'ajout de la vente !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void afficherTotal() {
    	String id = txt_searchidproduit.getText();
    	String quantite = txt_quantite.getText();
    	String prixunitaire = txt_prixunitaire.getText();
    	int qteProduit = 0 ;
    	if(quantite.isEmpty() || prixunitaire.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING, "les champs PU et Qte sont vides !", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();
    	}else {
    		String sql = "SELECT quantite FROM produits where id_produit = "+id+"";
    		try {
				st=cnx.prepareStatement(sql);
				result = st.executeQuery();
				if(result.next()) {
					qteProduit=Integer.parseInt(result.getString("quantite"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		
    		if(Integer.parseInt(quantite) > qteProduit) {
    			Alert alert = new Alert(AlertType.WARNING, "Qte saisie est superieur à la Qte du stock !", javafx.scene.control.ButtonType.OK);
        		alert.showAndWait();
        		txt_quantite.setText("");
    		}else {
    			txt_mntotal.setText(String.valueOf(Integer.parseInt(prixunitaire)*Integer.parseInt(quantite)));
    		}
    		
    		
    	}
    }


    @FXML
    void searchClient() {
    	String sql = "select id_client,nom,adresse from clients where cin='"+txt_searchcinclient.getText()+"'";
    	int m=0;
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				txt_idclient.setText(result.getString("id_client"));
				txt_nomclient.setText(result.getString("nom"));
				txt_adresse.setText(result.getString("adresse"));
				m=1;
			}else {
				txt_idclient.setText("");
				txt_nomclient.setText("");
				txt_adresse.setText("");
				txt_searchcinclient.setText("");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	if(m==0) {
    		Alert alert = new Alert(AlertType.WARNING, "CIN n'existe pas !", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();
    	
    	}
    	
    }

    @FXML
    void searchProduit() {
    	
    	String id = txt_searchidproduit.getText();
    	String sql = "SELECT * FROM produits INNER JOIN fournisseurs ON produits.id_fournisseur = fournisseurs.id_fournisseur WHERE produits.id_produit = ? ";
    	int m=0;
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, id);
            result = st.executeQuery();
            if (result.next()) {
                txt_nomproduit.setText(result.getString("nom_produit"));
                txt_prixunitaire.setText(result.getString("prix_unitaire"));
                txt_searchidproduit.setText(result.getString("id_produit"));
                m=1;
            } else {
                // If no product is found, clear the text fields
                txt_nomproduit.setText("");
                txt_quantite.setText("");
                txt_prixunitaire.setText("");
                txt_searchidproduit.setText("");
                txt_mntotal.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(m==0) {
    		Alert alert = new Alert(AlertType.WARNING, "ID de produit n'existe pas !", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();
    	}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();
		
	}
	
}

	

