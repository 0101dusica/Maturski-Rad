package projekat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menadzer extends Osoba{
	private String tip;
	
	public Menadzer () {
		
	}
	public Menadzer(String ime, String prezime) {
		super(ime, prezime);
		tip = "menadzer";
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public void dodajKonobara(Konobar k) {
	
	}
	
	public void istorijaRacuna() {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("racuni.txt"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
		System.out.println(sb);
	}
	
	public void prozor(Stage stage, HBox hBox, VBox vBox, Scene glavnaStrana) {
		VBox vBoxM = new VBox();
		Scene menadzerProgram = new Scene(vBoxM, 600, 600);
		stage.setScene(menadzerProgram);//na klik dugmeta otvara se nova scena
		
		//nove scene koje se otvaraju ako menadzer izabere tu opciju iz menija
		VBox vCenovnik = new VBox();
		Scene cenovnikScene = new Scene(vCenovnik, 400,500);
		
		VBox vKonobari = new VBox();
		Scene konobariScene = new Scene(vKonobari, 400,500);
		
		VBox vRacuni = new VBox();
		Scene racuniScene = new Scene(vRacuni, 300,500);
		
		//opcije koje se pojavljuju u padajucem meniju
		MenuItem prikazCenovnika = new MenuItem("prikazi cenovnik");
		MenuItem prikazPica = new MenuItem("prikazi pice");
		MenuItem dodajPice = new MenuItem("dodaj pice");
		MenuItem prikazHrane = new MenuItem("prikazi hranu");
		MenuItem dodajHranu = new MenuItem("dodaj hranu");
		MenuItem prikazKonobara = new MenuItem("prikazi listu konobara");
		MenuItem dodajNovogKonobara = new MenuItem("dodaj novog konobara");
		MenuItem obrisiKonobara = new MenuItem("obrisi konobara");
		MenuItem prikazRacuna = new MenuItem("prikazi istoriju izdatih racuna");
		
		Menu cenovnik = new Menu("Cenovnik");
		cenovnik.getItems().add(prikazCenovnika);
		cenovnik.getItems().add(prikazPica);
		cenovnik.getItems().add(dodajPice);
		cenovnik.getItems().add(prikazHrane);
		cenovnik.getItems().add(dodajHranu);
		prikazCenovnika.setOnAction(e -> {
			stage.setScene(cenovnikScene);
			Cenovnik c = new Cenovnik();
			c.prozorZaPrikazCenovnika(vCenovnik, menadzerProgram, stage);
			});
		prikazPica.setOnAction(e -> {
			stage.setScene(cenovnikScene);
			Cenovnik c = new Cenovnik();
			c.prozorZaPrikazPica(vCenovnik, menadzerProgram, stage);
			});
		dodajPice.setOnAction(e -> {
			stage.setScene(cenovnikScene);
			Cenovnik c = new Cenovnik();
			c.dodajPice(vCenovnik, menadzerProgram, stage);
			});
		prikazHrane.setOnAction(e -> {
			stage.setScene(cenovnikScene);
			Cenovnik c = new Cenovnik();
			c.prozorZaPrikazHrane(vCenovnik, menadzerProgram, stage);
			});
		dodajHranu.setOnAction(e -> {
			stage.setScene(cenovnikScene);
			Cenovnik c = new Cenovnik();
			c.dodajHranu(vCenovnik, menadzerProgram, stage);
			});
		Menu konobari = new Menu("Konobari");
		konobari.getItems().add(prikazKonobara);
		konobari.getItems().add(dodajNovogKonobara);
		konobari.getItems().add(obrisiKonobara);
		prikazKonobara.setOnAction(e -> {
			stage.setScene(konobariScene);
			Konobar k = new Konobar();
			k.prozorZaPrikazKonobara(vKonobari, menadzerProgram, stage);
			});
		dodajNovogKonobara.setOnAction(e -> {
			stage.setScene(konobariScene);
			Konobar k = new Konobar();
			k.dodajKonobara(vKonobari, menadzerProgram, stage);
			});
		obrisiKonobara.setOnAction(e -> {
			stage.setScene(konobariScene);
			Konobar k = new Konobar();
			k.obrisiKonobara(vKonobari, menadzerProgram, stage);
			});
		Menu istorijaRacuna = new Menu("Istorija Racuna");
		istorijaRacuna.getItems().add(prikazRacuna);
		prikazRacuna.setOnAction(e -> {
			stage.setScene(racuniScene);
			Racun r = new Racun();
			r.prozorZaPrikazRacuna(vRacuni, menadzerProgram, stage);
			});
		
		//dugme odjava, pri kojoj je se vraca na glavnu stranicu
		Button odjava = new Button("ODJAVI SE");
		MenuBar menuBar = new MenuBar();

		menuBar.getMenus().addAll(cenovnik, konobari, istorijaRacuna);	
		vBoxM.getChildren().add(menuBar);
		
		odjava.setOnMouseClicked(e->stage.setScene(glavnaStrana));
		try {

			Image image;
			image = new Image(new FileInputStream("slike/logo.jpg"));
			ImageView imageView = new ImageView(image); 
			 
			imageView.setFitHeight(455); 
			imageView.setFitWidth(600); 
			
			ImageView imageView1 = new ImageView(image); 
			 
			imageView1.setFitHeight(455); 
			imageView1.setFitWidth(500); 
			vBoxM.getChildren().addAll(imageView, odjava);
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	
	}
}
