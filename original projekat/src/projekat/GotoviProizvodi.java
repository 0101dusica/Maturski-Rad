package projekat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GotoviProizvodi {
	private String naziv;
	private int cena;
	private String tip;
	private ArrayList<Sirovina> namirnice = new ArrayList<Sirovina>();
	
	public GotoviProizvodi() {
		
	}
	
	public GotoviProizvodi(String naziv, int cena) {
		this.naziv = naziv;
		this.cena = cena;
		this.tip = "pice";
	}
	public GotoviProizvodi(String naziv, int cena, String tip) {
		this.naziv = naziv;
		this.cena = cena;
		this.tip = tip;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public ArrayList<Sirovina> getNamirnice() {
		return namirnice;
	}
	public void setNamirnice(ArrayList<Sirovina> namirnice) {
		this.namirnice = namirnice;
	}
	
	public void dodajSirovinuURecept(Sirovina s) {
		this.namirnice.add(s);
	}
	
	public void ispis(){
		System.out.println("-------PROIZVODI------");
		if(getTip() == "hrana") {
			System.out.println("Naziv obroka: " + naziv);
		}
		if(getTip() == "pice") {
			System.out.println("Naziv pica: " + naziv);
		}
		
		System.out.println("Cena: " + cena);
		if(namirnice.size() == 0) {
			System.out.println("Ovaj proizvod se ne pravi rucno!");
		}else {
			System.out.println("Sastojci: ");
			for(int i = 0 ; i< namirnice.size() ; i++) {
				System.out.println((i+1) + "." );
				namirnice.get(i).ispis();
		}
		System.out.println("---------------------");
		}
	}
	
	public String toSting() {
		String proizvod;
		
		proizvod = naziv + "@" + String.valueOf(cena) + "@";
		if(getTip() == "pice") {
			proizvod = proizvod + ("pice");
		}else {
			proizvod = proizvod + ("hrana");
		}
		
		return proizvod;
	}
	
	public void dodajUFajl() {
			
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("cenovnik.txt"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("cenovnik.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(this.toSting());
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
		}
			
		
}
