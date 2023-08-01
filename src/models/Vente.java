package models;

import java.sql.Date;

public class Vente {
    private int idVente;
    private int idProduit;
    private int idClient;
    private int quantite;
    private double prixUnitaire;
    private Date dateCommande;
    private double montant;
    
    public Vente() {
    	super();
    }

    public Vente(int idVente, int idProduit, int idClient, int quantite, double prixUnitaire, Date dateCommande, double montant) {
        this.idVente = idVente;
        this.idProduit = idProduit;
        this.idClient = idClient;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.dateCommande = dateCommande;
        this.montant = montant;
    }

    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
