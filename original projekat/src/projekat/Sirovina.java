package projekat;

public class Sirovina {
	private String naziv;
	private int kolicina;
	
	public Sirovina(String naziv, int kolicina){
		this.naziv = naziv;
		this.kolicina = kolicina;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	public void dodajKolicinu(int k) {
		kolicina = kolicina + k;
	}
	
	public void ispis() {
		System.out.println("Naziv: " + getNaziv());
	}
}
