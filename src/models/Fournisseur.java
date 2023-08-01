package models;

public class Fournisseur {
	private int idFournisseur;
	private String nomFournisseur;
	private String adresse;
	private String tele;
	
	public Fournisseur() {
		super();
	}
	
	public Fournisseur(int idFournisseur,String nomFournisseur,String adresse,String tele) {
		this.idFournisseur=idFournisseur;
		this.nomFournisseur=nomFournisseur;
		this.adresse=adresse;
		this.tele=tele;
	}

	public int getIdFournisseur() {
		return idFournisseur;
	}

	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}

	public String getNomFournisseur() {
		return nomFournisseur;
	}

	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}
}
