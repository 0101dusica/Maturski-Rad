package projekat;

public class Sto {
	private String id;
	private Racun racun;

	public Sto() {
		
	}
	public Sto(String id) {
		this.id = id;
		racun = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}
	
	public void dodajRacun(Racun r) {
		racun = r;
		
	}
	
	public boolean stanje() {
		if(racun == null) {
			return true; //sto je prazan
		}else {
			return false;
		}
	}
	public void isprazniSto() {
		racun = null;
	}
	public void ispis() {
		System.out.println("---------STO " + id + "------------");
		if(stanje() == true) {
			System.out.println("Sto je slobodan. Ne postoji racun!");
		}else {
			System.out.println("Sto je zauzet!");
			racun.ispis();
		}
		System.out.println("--------------");
		
		
	}
	
}
