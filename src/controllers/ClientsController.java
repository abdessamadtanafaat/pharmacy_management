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
import models.Client;

public class ClientsController implements Initializable{
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	 @FXML
	    private TableView<Client> table_clients;

	    @FXML
	    private TableColumn<Client, Integer> cin_id;

	    @FXML
	    private TableColumn<Client, String> cin_cin;

	    @FXML
	    private TableColumn<Client, String> cin_nom;

	    @FXML
	    private TableColumn<Client, String> cin_adresse;

	    @FXML
	    private TableColumn<Client, String> cin_tele;

	    @FXML
	    private TextField txt_searchCIN;

	    @FXML
	    private TextField txt_CIN;

	    @FXML
	    private TextField txt_nom;

	    @FXML
	    private TextField txt_adresse;

	    @FXML
	    private TextField txt_tele;

	    @FXML
	    private Button btn_add;

	    @FXML
	    private Button btn_edit;

	    @FXML
	    private Button btn_delete;
	    
	    public ObservableList<Client> data = FXCollections.observableArrayList();

	    @FXML
	    void addClient() {
	        String cin = txt_CIN.getText();
	        String nom = txt_nom.getText();
	        String adresse = txt_adresse.getText();
	        String tele = txt_tele.getText();

	        // Vérification des champs obligatoires
	        if (cin.isEmpty() || nom.isEmpty() || adresse.isEmpty() || tele.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        String sql = "INSERT INTO clients (cin, nom, adresse, telephone) VALUES (?, ?, ?, ?)";
	        try {
	            st = cnx.prepareStatement(sql);
	            st.setString(1, cin);
	            st.setString(2, nom);
	            st.setString(3, adresse);
	            st.setString(4, tele);
	            int nbr_ligne_affect = st.executeUpdate();
	            if (nbr_ligne_affect > 0) {
	                // Ajout réussi
	                Alert alert = new Alert(AlertType.INFORMATION, "Client ajouté avec succès !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	                // Réinitialiser les champs
	                txt_CIN.setText("");
	                txt_nom.setText("");
	                txt_adresse.setText("");
	                txt_tele.setText("");
	                // Rafraîchir la table
	                data.clear();
	                showClient();
	            } else {
	                // Échec de l'ajout
	                Alert alert = new Alert(AlertType.ERROR, "Échec de l'ajout du client !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    

	    @FXML
	    void deleteClient() {
	        String cin = txt_CIN.getText();

	        // Vérification du champ CIN
	        if (cin.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez entrer le CIN du client à supprimer !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        // Confirmation de la suppression
	        Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce client ?", javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
	        confirmAlert.showAndWait();

	        if (confirmAlert.getResult() == javafx.scene.control.ButtonType.YES) {
	            String sql = "DELETE FROM clients WHERE cin = ?";
	            try {
	                st = cnx.prepareStatement(sql);
	                st.setString(1, cin);
	                int nbr_ligne_affect = st.executeUpdate();
	                if (nbr_ligne_affect > 0) {
	                    // Suppression réussie
	                    Alert alert = new Alert(AlertType.INFORMATION, "Client supprimé avec succès !", javafx.scene.control.ButtonType.OK);
	                    alert.showAndWait();
	                    // Réinitialiser les champs
	                    txt_CIN.setText("");
	                    txt_nom.setText("");
	                    txt_adresse.setText("");
	                    txt_tele.setText("");
	                    // Rafraîchir la table
	                    data.clear();
	                    showClient();
	                } else {
	                    // Échec de la suppression
	                    Alert alert = new Alert(AlertType.ERROR, "Échec de la suppression du client !", javafx.scene.control.ButtonType.OK);
	                    alert.showAndWait();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @FXML
	    void editClient() {
	        String cin = txt_CIN.getText();
	        String nom = txt_nom.getText();
	        String adresse = txt_adresse.getText();
	        String tele = txt_tele.getText();

	        // Vérification des champs obligatoires
	        if (cin.isEmpty() || nom.isEmpty() || adresse.isEmpty() || tele.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !", javafx.scene.control.ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }

	        String sql = "UPDATE clients SET nom = ?, adresse = ?, telephone = ? WHERE cin = ?";
	        try {
	            st = cnx.prepareStatement(sql);
	            st.setString(1, nom);
	            st.setString(2, adresse);
	            st.setString(3, tele);
	            st.setString(4, cin);
	            int nbr_ligne_affect = st.executeUpdate();
	            if (nbr_ligne_affect > 0) {
	                // Mise à jour réussie
	                Alert alert = new Alert(AlertType.INFORMATION, "Client mis à jour avec succès !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	                // Réinitialiser les champs
	                txt_CIN.setText("");
	                txt_nom.setText("");
	                txt_adresse.setText("");
	                txt_tele.setText("");
	                // Rafraîchir la table
	                data.clear();
	                showClient();
	            } else {
	                // Échec de la mise à jour
	                Alert alert = new Alert(AlertType.ERROR, "Échec de la mise à jour du client !", javafx.scene.control.ButtonType.OK);
	                alert.showAndWait();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @FXML
	    void searchClient() {
	    	String sql = "select nom,cin,adresse,telephone from clients where cin='"+txt_searchCIN.getText()+"'";
	    	int m=0;
	    	try {
				st = cnx.prepareStatement(sql);
				result = st.executeQuery();
				if(result.next()) {
					txt_CIN.setText(result.getString("cin"));
					txt_nom.setText(result.getString("nom"));
					txt_adresse.setText(result.getString("adresse"));
					txt_tele.setText(result.getString("telephone"));
					m=1;
				}else {
					txt_CIN.setText("");
					txt_nom.setText("");
					txt_adresse.setText("");
					txt_tele.setText("");
					txt_searchCIN.setText("");
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
	    void tableClientsEvent() {
	    	Client client = table_clients.getSelectionModel().getSelectedItem();
	    	String sql = "SELECT * FROM clients WHERE id_client = ?";
	    	try {
				st = cnx.prepareStatement(sql);
				st.setInt(1, client.getId());
				result = st.executeQuery();
				if(result.next()) {
					txt_CIN.setText(result.getString("cin"));
					txt_nom.setText(result.getString("nom"));
					txt_adresse.setText(result.getString("adresse"));
					txt_tele.setText(result.getString("telephone"));
					txt_searchCIN.setText(result.getString("cin"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    public void showClient() {
	    	String sql = "select * from clients";
	    	try {
				st = cnx.prepareStatement(sql);
				result = st.executeQuery();
				while(result.next()) {
					data.add(new Client(result.getInt("id_client"),result.getString("cin"),result.getString("nom"),result.getString("adresse"),result.getString("telephone")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	cin_cin.setCellValueFactory(new PropertyValueFactory<Client,String>("cin"));
	    	cin_id.setCellValueFactory(new PropertyValueFactory<Client,Integer>("id"));
	    	cin_nom.setCellValueFactory(new PropertyValueFactory<Client,String>("nomprenom"));
	    	cin_adresse.setCellValueFactory(new PropertyValueFactory<Client,String>("adresse"));
	    	cin_tele.setCellValueFactory(new PropertyValueFactory<Client,String>("tele"));
	    	table_clients.setItems(data);
	    }
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();
		showClient();
	}

	

}
