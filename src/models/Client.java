package models;

public class Client {
	private int id;
	private String cin;
	private String nomprenom;
	private String adresse;
	private String tele;
	
	public Client() {
		super();
	}
	
	public Client(int id,String cin,String nomprenom,String adresse,String tele) {
		this.id=id;
		this.cin=cin;
		this.nomprenom=nomprenom;
		this.adresse=adresse;
		this.tele=tele;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomprenom() {
		return nomprenom;
	}

	public void setNomprenom(String nomprenom) {
		this.nomprenom = nomprenom;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}
	
}
