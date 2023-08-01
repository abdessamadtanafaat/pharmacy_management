package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Vente;

public class HistoriqueController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private TableView<Vente> tableVente;

    @FXML
    private TableColumn<Vente,Integer > idclient;

    @FXML
    private TableColumn<Vente,Integer> idproduit;

    @FXML
    private TableColumn<Vente, Integer> qte;

    @FXML
    private TableColumn<Vente, Integer> mnt;

    @FXML
    private TableColumn<Vente, Date> date;
    
    public ObservableList<Vente> data = FXCollections.observableArrayList();
    
    public void showVente() {
    	String sql = "select * from ventes";
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				data.add(new Vente(result.getInt("id_vente"), result.getInt("id_produit"), result.getInt("id_client"), result.getInt("quantite"), result.getInt("prix_unitaire"), result.getDate("date_commande"), result.getInt("montant")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	idclient.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("idClient"));
    	idproduit.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("idProduit"));
    	qte.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("quantite"));
    	mnt.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("montant"));
    	date.setCellValueFactory(new PropertyValueFactory<Vente,Date>("dateCommande"));
    	tableVente.setItems(data);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();	
		showVente();
	}

	

}
