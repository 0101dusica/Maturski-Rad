package projekat;

public abstract class Osoba {
	 private String ime;
	 private String prezime;
	 
	 public Osoba() {
		 
	 }
	public Osoba(String ime, String prezime) {
		this.ime = ime;
		this.prezime = prezime;
	}
	 
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public void predstaviSe() {
		System.out.println("Ime: " + ime);
		System.out.println("Prezime: " + prezime);
	}
}
