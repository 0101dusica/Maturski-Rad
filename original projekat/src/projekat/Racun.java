package projekat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Racun {
	private int ukupnaCena;
	private String datum;
	private Konobar konobar;
	private ArrayList<GotoviProizvodi> proizvodi;
	
	public Racun (){
		
	}
	public Racun(Konobar konobar) {
		ukupnaCena = 0;
		this.datum = new Date().toString();
		this.konobar = konobar;
		proizvodi = new  ArrayList<GotoviProizvodi>();
	}
	
	public void setRacun(Racun racun) {
		this.ukupnaCena = racun.getUkupnaCena();
		this.datum = racun.getDatum();
		this.konobar = racun.getKonobar();
		this.proizvodi = racun.getProizvodi();
	}
	
	public int getUkupnaCena() {
		return ukupnaCena;
	}
	public void setUkupnaCena(int ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public ArrayList<GotoviProizvodi> getProizvodi() {
		return proizvodi;
	}

	public void setProizvodi(ArrayList<GotoviProizvodi> proizvodi) {
		this.proizvodi = proizvodi;
	}

	public Konobar getKonobar() {
		return konobar;
	}
	public void setKonobar(Konobar konobar) {
		this.konobar = konobar;
	}
	
	
	public void kucanjeRacuna(GotoviProizvodi g) {
		proizvodi.add(g);
		int i = proizvodi.size();
		ukupnaCena = ukupnaCena + proizvodi.get(i-1).getCena();
	}
	
	public void ispis() {
		if(proizvodi.size() == 0) {
			System.out.println("Racun jos uvek nije iskucan!");
		}else {
			System.out.println("Konobar: " );
			konobar.predstaviSe();
			System.out.println("Datum i vreme: " + datum);
			System.out.println("Porudzbina: ");
			for (int i = 0; i < proizvodi.size(); i++) {
				System.out.println((i+1) + ". proizvod: " + proizvodi.get(i).getNaziv() + "	cena: " +proizvodi.get(i).getCena() );
			}
			System.out.println("Ukupna cena: " + ukupnaCena);
		}
	}
	
	public String toSting() {
		String racun;
		
		racun = konobar.getIme() + "@" +konobar.getPrezime() + "@" + datum.toString() + "@"+String.valueOf(ukupnaCena);
		for (int i = 0; i < proizvodi.size(); i++) {
			racun = racun + ("@" + proizvodi.get(i).getNaziv() + "@" + String.valueOf(proizvodi.get(i).getCena()));
		}
		
		return racun;
	}
	
	public void prozorZaPrikazRacuna(VBox vRacuni, Scene menadzerProgram, Stage stage) {
		
		HBox hBox = new HBox();
		
		Label layout2 = new Label("Istorija Izdatih Racuna					");
		Button vratiMe2 = new Button("nazad");
		vratiMe2.setOnMouseClicked(e -> stage.setScene(menadzerProgram));
		hBox.getChildren().addAll(layout2, vratiMe2);
		vRacuni.getChildren().add(hBox);
		
		Text text = new Text();
		
		try {
			BufferedReader reader2;
			reader2 = new BufferedReader(new FileReader("racuni.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader2.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader2.readLine());
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			String primer = null;
			for (int j = 0; j < line.size(); j++) {
				String[] prim = line.get(j).split("@");
				
				if(primer == null) {
					primer = "+++++++RACUN+++++++++++ " + System.lineSeparator() + "Konobar: "+ prim[0] + " " + prim[1] + System.lineSeparator() + "++++++++++++++++++++++++++++"+ System.lineSeparator() + "Datum: "+ prim[2] + System.lineSeparator();
					primer = primer + "++++++++++++++++++++++++++++"+System.lineSeparator() + "Naziv: 			Cena: " + System.lineSeparator() ;
					
				}else {
					primer = primer+ "+++++++RACUN+++++++++++ " + System.lineSeparator() + "Konobar: "+ prim[0] + " " + prim[1] + System.lineSeparator() + "++++++++++++++++++++++++++++"+ System.lineSeparator() + "Datum: "+ prim[2] + System.lineSeparator();
					primer = primer + "++++++++++++++++++++++++++++"+System.lineSeparator() + "Naziv: 			Cena: " + System.lineSeparator() ;
					
				}
				
				for( int i = 4; i< prim.length; i = i+2) {
					primer = primer + prim[i]+ "			"+ prim[i+1] + System.lineSeparator();
				}
				primer = primer + "Ukupna Cena: " + prim[3] + System.lineSeparator() + "++++++++++++++++++++++++++++"+ System.lineSeparator();
				
				prim = null;
			}
			
			text.setText(primer);
			vRacuni.getChildren().add(text);
			
			PrintWriter writer = new PrintWriter("racuni.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
				
			}
			
			writer.close();
			
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
