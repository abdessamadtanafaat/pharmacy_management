package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.ConnectionMysql;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FacturesController implements Initializable{
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
    @FXML
    private ComboBox<Integer> cb_produit;

    @FXML
    private ComboBox<String> cb_client;

    @FXML
    private TextField txt_nomprod;

    @FXML
    private TextField txt_mnt;

    @FXML
    private TextField txt_pu;

    @FXML
    private TextField txt_date;

    @FXML
    private TextField txt_nomclient;

    @FXML
    private TextField txt_qte;

    @FXML
    void imprimer() {
    	 Document doc = new Document();
    	    try {
    	        PdfWriter.getInstance(doc, new FileOutputStream("facture.pdf"));
    	        doc.open();
    	        String format = "dd/MM/yy HH:mm";
    	        SimpleDateFormat formater = new SimpleDateFormat(format);
    	        java.util.Date date = new java.util.Date();
    	        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("C:\\Users\\pc\\eclipse-workspace\\Pharmacie\\src\\images\\FactureHead.jpeg");
    	        img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
    	        doc.add(img);

    	        // Informations sur la facture
    	        doc.add(new Paragraph("Facture", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
    	        doc.add(new Paragraph("Date: " + formater.format(date)));
    	        doc.add(new Paragraph("Client: " + txt_nomclient.getText()));
    	        doc.add(new Paragraph("Produit: " + txt_nomprod.getText()));
    	        doc.add(new Paragraph("Prix unitaire: " + txt_pu.getText()));
    	        doc.add(new Paragraph("Quantité: " + txt_qte.getText()));
    	        doc.add(new Paragraph("Montant: " + txt_mnt.getText()));
    	        doc.add(new Paragraph("                                                    Merci ! "));
    	        doc.close();

    	        Alert alert = new Alert(AlertType.INFORMATION, "Facture générée avec succès !", javafx.scene.control.ButtonType.OK);
    	        alert.showAndWait();

    	    } catch (FileNotFoundException | DocumentException e) {
    	        e.printStackTrace();
    	        Alert alert = new Alert(AlertType.ERROR, "Erreur lors de la génération de la facture !", javafx.scene.control.ButtonType.OK);
    	        alert.showAndWait();
    	    } catch (MalformedURLException e) {
    	        e.printStackTrace();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    }

    @FXML
    void remplirClients() {
    	String sql = "SELECT cin from clients";
    	List<String> cins = new ArrayList<String>();
    	try {
			st=cnx.prepareStatement(sql);
			result=st.executeQuery();
			while(result.next()) {
				cins.add(result.getString("cin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	cb_client.setItems(FXCollections.observableArrayList(cins));
    }

    @FXML
    void remplirProduits() {
    	String sql = "SELECT id_produit from produits";
    	List<Integer> ids = new ArrayList<Integer>();
    	try {
			st=cnx.prepareStatement(sql);
			result=st.executeQuery();
			while(result.next()) {
				ids.add(result.getInt("id_produit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	cb_produit.setItems(FXCollections.observableArrayList(ids));
    	
    }

    @FXML
    void search() {
    	Integer idprod = cb_produit.getValue();
    	String cin = cb_client.getValue();
    	String sql1 = "SELECT nom FROM clients WHERE cin = '"+cin+"' ";
    	String sql2 = "SELECT nom_produit,prix_unitaire FROM produits WHERE id_produit = '"+idprod+"' ";
    	try {
			st=cnx.prepareStatement(sql1);
			result=st.executeQuery();
			if(result.next()) {
				txt_nomclient.setText(result.getString("nom"));
				st=cnx.prepareStatement(sql2);
				result=st.executeQuery();
				if(result.next()) {
					txt_nomprod.setText(result.getString("nom_produit"));
					txt_pu.setText(result.getString("prix_unitaire"));
					
					Integer idclient = null;
					String sql3 = "SELECT id_client FROM clients WHERE cin = '"+cin+"'";
					st=cnx.prepareStatement(sql3);
					result=st.executeQuery();
					if(result.next()) {
						idclient = result.getInt("id_client");
						String sql4 = "SELECT quantite, date_commande , montant FROM ventes WHERE ventes.id_client ='"+idclient+"' AND ventes.id_produit ='"+idprod+"'";
						st=cnx.prepareStatement(sql4);
						result=st.executeQuery();
						if(result.next()) {
							txt_date.setText(String.valueOf(result.getDate("date_commande")));
							txt_qte.setText(String.valueOf(result.getInt("quantite")));
							txt_mnt.setText(String.valueOf(result.getInt("montant")));
						}else {
							Alert alert = new Alert(AlertType.ERROR,"Erreur Vente introuvable",javafx.scene.control.ButtonType.OK);
							alert.showAndWait();
							txt_nomprod.setText("");
							txt_nomclient.setText("");
							txt_pu.setText("");
						}
					}else {
						Alert alert = new Alert(AlertType.ERROR,"Erreur id_client introuvable",javafx.scene.control.ButtonType.OK);
						alert.showAndWait();
					}
					
					
					
				}else {
					Alert alert = new Alert(AlertType.ERROR,"Erreur Produit introuvable",javafx.scene.control.ButtonType.OK);
					alert.showAndWait();
					txt_nomprod.setText("");
					txt_pu.setText("");
				}
			}else {
				Alert alert = new Alert(AlertType.ERROR,"Erreur Client introuvable",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				txt_nomclient.setText("");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnectionMysql.connectionDB();		
	}

	

}
