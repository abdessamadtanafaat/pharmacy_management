package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Produit;

public class produitsController implements Initializable{
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private TableView<Produit> table_produits;

    @FXML
    private TableColumn<Produit, Integer> id_id;

    @FXML
    private TableColumn<Produit, String> id_nom;

    @FXML
    private TableColumn<Produit, Integer> id_qte;

    @FXML
    private TableColumn<Produit, Integer> id_pu;

    @FXML
    private TableColumn<Produit, Integer> id_idfournisseur;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_idfournisseur;

    @FXML
    private TextField txt_searchid;

    @FXML
    private TextField txt_prixunitaire;

    @FXML
    private TextField txt_quantite;

    @FXML
    private Button btn_editproduit;

    @FXML
    private Button btn_addproduit;

    @FXML
    private Button btn_deleteproduit;

    @FXML
    private Button btn_searchid;
    
    public ObservableList<Produit> data = FXCollections.observableArrayList();

    @FXML
    void addProduit() {
    	String nom = txt_nom.getText();
        String quantite = txt_quantite.getText();
        String prixunitaire = txt_prixunitaire.getText();
        String idfournisseur = txt_idfournisseur.getText();

        // Vérification des champs obligatoires
        if (nom.isEmpty() || quantite.isEmpty() || quantite.isEmpty()|| prixunitaire.isEmpty()|| idfournisseur.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        // Vérification si l'ID du fournisseur existe dans la table fournisseurs
        String fournisseurSql = "SELECT * FROM fournisseurs WHERE id_fournisseur = ?";
        try {
            st = cnx.prepareStatement(fournisseurSql);
            st.setString(1, idfournisseur);
            result = st.executeQuery();
            if (!result.next()) {
                // Si l'ID du fournisseur n'existe pas, afficher une erreur
                Alert alert = new Alert(AlertType.ERROR, "ID du fournisseur invalide !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO produits (nom_produit, quantite, prix_unitaire,id_fournisseur) VALUES (?, ?, ?, ?)";
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, nom);
            st.setString(2, quantite);
            st.setString(3, prixunitaire);
            st.setString(4, idfournisseur);
            int nbr_ligne_affect = st.executeUpdate();
            if (nbr_ligne_affect > 0) {
                // Ajout réussi
                Alert alert = new Alert(AlertType.INFORMATION, "Produit ajouté avec succès !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                // Réinitialiser les champs
                txt_searchid.setText("");
                txt_nom.setText("");
                txt_quantite.setText("");
                txt_prixunitaire.setText("");
                txt_idfournisseur.setText("");
                // Rafraîchir la table
                data.clear();
                showProduit();
            } else {
                // Échec de l'ajout
                Alert alert = new Alert(AlertType.ERROR, "Échec de l'ajout du produit !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteProduit() {
    	String id = txt_searchid.getText();

        // Vérification du champ ID
        if (id.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer l'ID du produit à supprimer !", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Confirmation de la suppression
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce produit ?", javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == javafx.scene.control.ButtonType.YES) {
            String sql = "DELETE FROM produits WHERE id_produit = ?";
            try {
                st = cnx.prepareStatement(sql);
                st.setString(1, id);
                int nbr_ligne_affect = st.executeUpdate();
                if (nbr_ligne_affect > 0) {
                    // Suppression réussie
                    Alert alert = new Alert(AlertType.INFORMATION, "Produit supprimé avec succès !", javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                    // Réinitialiser les champs
                    txt_searchid.setText("");
                    txt_nom.setText("");
                    txt_quantite.setText("");
                    txt_prixunitaire.setText("");
                    txt_idfournisseur.setText("");
                    // Rafraîchir la table
                    data.clear();
                    showProduit();
                } else {
                    // Échec de la suppression
                    Alert alert = new Alert(AlertType.ERROR, "Échec de la suppression du produit !", javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void editProduit() {
    	String id = txt_searchid.getText();
        String nom = txt_nom.getText();
        String quantite = txt_quantite.getText();
        String prixunitaire = txt_prixunitaire.getText();
        String idfournisseur = txt_idfournisseur.getText();

        // Vérification des champs obligatoires
        if (id.isEmpty() || nom.isEmpty() || quantite.isEmpty() || prixunitaire.isEmpty() || idfournisseur.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE produits INNER JOIN fournisseurs ON produits.id_fournisseur = fournisseurs.id_fournisseur SET nom = ?, quantite = ?, prix_unitaire = ?, produits.id_fournisseur = ? WHERE id_produit = ? ";
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, nom);
            st.setString(2, quantite);
            st.setString(3, prixunitaire);
            st.setString(4, idfournisseur);
            st.setString(5, id);
            int nbr_ligne_affect = st.executeUpdate();
            if (nbr_ligne_affect > 0) {
                // Mise à jour réussie
                Alert alert = new Alert(AlertType.INFORMATION, "produit mis à jour avec succès !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                // Réinitialiser les champs
                txt_searchid.setText("");
                txt_nom.setText("");
                txt_quantite.setText("");
                txt_prixunitaire.setText("");
                txt_idfournisseur.setText("");
                // Rafraîchir la table
                data.clear();
                showProduit();
            } else {
                // Échec de la mise à jour
                Alert alert = new Alert(AlertType.ERROR, "Échec de la mise à jour du produit !", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
    }
    }

    @FXML
    void searchProduit() {
    	String id = txt_searchid.getText();
    	String sql = "SELECT * FROM produits INNER JOIN fournisseurs ON produits.id_fournisseur = fournisseurs.id_fournisseur WHERE produits.id_produit = ? ";
    	int m=0;
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, id);
            result = st.executeQuery();
            if (result.next()) {
                txt_nom.setText(result.getString("nom_produit"));
                txt_quantite.setText(result.getString("quantite"));
                txt_prixunitaire.setText(result.getString("prix_unitaire"));
                txt_idfournisseur.setText(result.getString("id_fournisseur"));
                txt_searchid.setText(result.getString("id_produit"));
                m=1;
            } else {
                // If no product is found, clear the text fields
                txt_nom.setText("");
                txt_quantite.setText("");
                txt_prixunitaire.setText("");
                txt_idfournisseur.setText("");
                txt_searchid.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(m==0) {
    		Alert alert = new Alert(AlertType.WARNING, "ID de produit n'existe pas !", javafx.scene.control.ButtonType.OK);
    		alert.showAndWait();
    	}
    }

    @FXML
    void tableProduitsEvent() {
        Produit produit = table_produits.getSelectionModel().getSelectedItem();
        String sql = "SELECT * FROM produits INNER JOIN fournisseurs ON produits.id_fournisseur = fournisseurs.id_fournisseur WHERE produits.id_produit = ?";
        try {
            st = cnx.prepareStatement(sql);
            st.setInt(1, produit.getIdProduit());
            result = st.executeQuery();
            if(result.next()) {
                id_id.setText(String.valueOf(result.getInt("id_produit")));
                txt_nom.setText(result.getString("nom_produit"));
                txt_quantite.setText(String.valueOf(result.getInt("quantite")));
                txt_prixunitaire.setText(String.valueOf(result.getInt("prix_unitaire")));
                txt_idfournisseur.setText(String.valueOf(result.getInt("id_fournisseur")));
                txt_searchid.setText(String.valueOf(result.getInt("id_produit")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void showProduit() {
    	 String sql = "SELECT produits.id_produit, produits.nom_produit, produits.quantite, produits.prix_unitaire, produits.id_fournisseur " +
                 "FROM produits INNER JOIN fournisseurs ON produits.id_fournisseur = fournisseurs.id_fournisseur";
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				data.add(new Produit(result.getInt("id_produit"),result.getString("nom_produit"),result.getInt("quantite"),result.getInt("prix_unitaire"),result.getInt("id_fournisseur")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	id_id.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("idProduit"));
    	id_nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nomProduit"));
    	id_qte.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
    	id_pu.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("prixUnitaire"));
    	id_idfournisseur.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("idFournisseur"));
    	table_produits.setItems(data);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();
		showProduit();
	}



}
