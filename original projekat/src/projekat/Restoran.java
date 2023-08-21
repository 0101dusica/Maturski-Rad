package projekat;
import java.util.ArrayList;

public class Restoran {
	private String naziv;
	private ArrayList<Sto> stolovi;
	private Menadzer menadzer;
	
	public Restoran(String naziv, Menadzer menadzer) {
		this.naziv = naziv;
		stolovi = new ArrayList<Sto> ();
		this.menadzer = menadzer;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public ArrayList<Sto> getStolovi() {
		return stolovi;
	}
	public void setStolovi(ArrayList<Sto> stolovi) {
		this.stolovi = stolovi;
	}

	public Menadzer getMenadzer() {
		return menadzer;
	}

	public void setMenadzer(Menadzer menadzer) {
		this.menadzer = menadzer;
	}
	
	public void dodajSto(Sto s) {
		stolovi.add(s);
	}
	
	public void ispis() {
		System.out.println("-----------**RESTORAN**----------");
		System.out.println("Restoran: " + naziv);
		System.out.println("Menadzer: ");
		menadzer.predstaviSe();
		for( int i = 0; i< stolovi.size(); i++) {
			stolovi.get(i).ispis();
		}
		
		System.out.println("-----------************----------");
	}
}
