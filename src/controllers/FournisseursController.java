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
import models.Fournisseur;

public class FournisseursController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	  @FXML
	    private TableView<Fournisseur> tableFournisseur;

	    @FXML
	    private TableColumn<Fournisseur, Integer> id_id;

	    @FXML
	    private TableColumn<Fournisseur, String> id_nom;

	    @FXML
	    private TableColumn<Fournisseur, String> id_adresse;

	    @FXML
	    private TableColumn<Fournisseur, String> id_tele;

	    @FXML
	    private TextField txt_searchid;

	    @FXML
	    private TextField txt_adresse;

	    @FXML
	    private TextField txt_nom;

	    @FXML
	    private TextField txt_tele;

	    @FXML
	    private Button btn_add;

	    @FXML
	    private Button btn_edit;

	    @FXML
	    private Button btn_delete;

	    @FXML
	    private Button btn_searchid;
	    
	    public ObservableList<Fournisseur> data = FXCollections.observableArrayList();

	    @FXML
	    void addFournisseur() {
	    	String nom = txt_nom.getText();
	        String adresse = txt_adresse.getText();
	        String tele = txt_tele.getText();

	        // Vérification des champs obligatoires
	        if (nom.isEmpty() || adresse.isEmpty() || tele.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        String sql = "INSERT INTO fournisseurs (nom, adresse, telephone) VALUES (?, ?, ?)";
	        try {
	            st = cnx.prepareStatement(sql);
	            st.setString(1, nom);
	            st.setString(2, adresse);
	            st.setString(3, tele);
	            int nbr_ligne_affect = st.executeUpdate();
	            if (nbr_ligne_affect > 0) {
	                // Ajout réussi
	                Alert alert = new Alert(AlertType.INFORMATION, "Fournisseur ajouté avec succès !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	                // Réinitialiser les champs
	                txt_nom.setText("");
	                txt_adresse.setText("");
	                txt_tele.setText("");
	                // Rafraîchir la table
	                data.clear();
	                showFournisseur();
	            } else {
	                // Échec de l'ajout
	                Alert alert = new Alert(AlertType.ERROR, "Échec de l'ajout du fournisseur !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @FXML
	    void deleteFournisseur() {
	    	String id = txt_searchid.getText();

	        // Vérification du champ ID
	        if (id.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer l'ID du fournisseur à supprimer !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        // Confirmation de la suppression
	        Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce fournisseur ?", javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
	        confirmAlert.showAndWait();

	        if (confirmAlert.getResult() == javafx.scene.control.ButtonType.YES) {
	            String sql = "DELETE FROM fournisseurs WHERE id_fournisseur = ?";
	            try {
	                st = cnx.prepareStatement(sql);
	                st.setString(1, id);
	                int nbr_ligne_affect = st.executeUpdate();
	                if (nbr_ligne_affect > 0) {
	                    // Suppression réussie
	                    Alert alert = new Alert(AlertType.INFORMATION, "Fournisseur supprimé avec succès !", javafx.scene.control.ButtonType.OK);
	                    alert.showAndWait();
	                    // Réinitialiser les champs
	                    txt_searchid.setText("");
	                    txt_nom.setText("");
	                    txt_adresse.setText("");
	                    txt_tele.setText("");
	                    // Rafraîchir la table
	                    data.clear();
	                    showFournisseur();
	                } else {
	                    // Échec de la suppression
	                    Alert alert = new Alert(AlertType.ERROR, "Échec de la suppression du fournisseur !", javafx.scene.control.ButtonType.OK);
	                    alert.showAndWait();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @FXML
	    void editFournisseur() {
	    	Integer id = Integer.parseInt(txt_searchid.getText());
	        String nom = txt_nom.getText();
	        String adresse = txt_adresse.getText();
	        String tele = txt_tele.getText();

	        // Vérification des champs obligatoires
	        if (id==null || nom.isEmpty() || adresse.isEmpty() || tele.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        String sql = "UPDATE fournisseurs SET nom = ?, adresse = ?, telephone = ? WHERE id_fournisseur = ?";
	        try {
	            st = cnx.prepareStatement(sql);
	            st.setString(1, nom);
	            st.setString(2, adresse);
	            st.setString(3, tele);
	            st.setInt(4, id);
	            int nbr_ligne_affect = st.executeUpdate();
	            if (nbr_ligne_affect > 0) {
	                // Mise à jour réussie
	                Alert alert = new Alert(AlertType.INFORMATION, "Fournisseur mis à jour avec succès !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	                // Réinitialiser les champs
	                txt_searchid.setText("");
	                txt_nom.setText("");
	                txt_adresse.setText("");
	                txt_tele.setText("");
	                // Rafraîchir la table
	                data.clear();
	                showFournisseur();
	            } else {
	                // Échec de la mise à jour
	                Alert alert = new Alert(AlertType.ERROR, "Échec de la mise à jour du fournisseur !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        
	    }
	   }

	    @FXML
	    void searchFournisseur() {
	    	String sql = "select nom,adresse,telephone from fournisseurs where id_fournisseur='"+txt_searchid.getText()+"'";
	    	int m=0;
	    	try {
				st = cnx.prepareStatement(sql);
				result = st.executeQuery();
				if(result.next()) {
					txt_nom.setText(result.getString("nom"));
					txt_adresse.setText(result.getString("adresse"));
					txt_tele.setText(result.getString("telephone"));
					m=1;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	if(m==0) {
	    		Alert alert = new Alert(AlertType.WARNING, "id n'existe pas !", javafx.scene.control.ButtonType.OK);
	    		alert.showAndWait();
	    	}
	    }

	    @FXML
	    void tableFournisseurEvent() {
	    	Fournisseur fournisseur = tableFournisseur.getSelectionModel().getSelectedItem();
	    	String sql = "SELECT * FROM fournisseurs WHERE id_fournisseur = ?";
	    	try {
				st = cnx.prepareStatement(sql);
				st.setInt(1, fournisseur.getIdFournisseur());
				result = st.executeQuery();
				if(result.next()) {
					id_id.setText(result.getString("id_fournisseur"));
					txt_nom.setText(result.getString("nom"));
					txt_adresse.setText(result.getString("adresse"));
					txt_tele.setText(result.getString("telephone"));
					txt_searchid.setText(result.getString("id_fournisseur"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    public void showFournisseur() {
	    	
	    	String sql = "select * from fournisseurs";
	    	try {
				st = cnx.prepareStatement(sql);
				result = st.executeQuery();
				while(result.next()) {
					data.add(new Fournisseur(result.getInt("id_fournisseur"),result.getString("nom"),result.getString("adresse"),result.getString("telephone")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	id_id.setCellValueFactory(new PropertyValueFactory<Fournisseur,Integer>("idFournisseur"));
	    	id_nom.setCellValueFactory(new PropertyValueFactory<Fournisseur,String>("nomFournisseur"));
	    	id_adresse.setCellValueFactory(new PropertyValueFactory<Fournisseur,String>("adresse"));
	    	id_tele.setCellValueFactory(new PropertyValueFactory<Fournisseur,String>("tele"));
	    	tableFournisseur.setItems(data);
	    	
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();
		showFournisseur();
	}

	

}
