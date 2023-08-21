package projekat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Konobar extends Osoba{
	private String tip;
	
	public Konobar() {
		super();
	}
	
	public Konobar(String ime, String prezime) {
		super(ime, prezime);
		tip = "konobar";
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public boolean otvoriRestoran() {
		return true;
	}
	
	public void izdavanjeRacuna(Sto s) {
		
		if(s.getRacun() != null) {
			s.getRacun().ispis();
			
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
			
			try (FileWriter writer = new FileWriter("racuni.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(s.getRacun().toSting());
				bw.newLine();

			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
			s.isprazniSto();
		}
		
		
	}
	
	public String toSting() {
		String konobar;
		
		konobar = this.getIme() + "@" + this.getPrezime() + "@konobar";
		
		return konobar;
	
	}
	
	public void dodajUFajl() {
			
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("osobe.txt"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("osobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(this.toSting());
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
		}
	
	public void prozor(Stage stage, HBox hBox, VBox vBox, Scene glavnaStrana) {
		VBox vBoxK = new VBox();
		
		Scene konobarProgram = new Scene(vBoxK, 610, 650);
		stage.setScene(konobarProgram);
		
		Button odjava1 = new Button("ODJAVI SE");
		odjava1.setOnMouseClicked(e->stage.setScene(glavnaStrana));
		prozorZaPrikazStolova(vBoxK, stage, konobarProgram);
		vBoxK.getChildren().add(odjava1);
	}
	
	public void prozorZaPrikazKonobara(VBox vKonobari, Scene menadzerProgram, Stage stage) {
		
		TableView<Konobar> tableView1 = new TableView<>();
		TableColumn<Konobar, String> col11 = new TableColumn<>();
		TableColumn<Konobar, String> col22 = new TableColumn<>();
		
		col11.setText("Ime");
		col22.setText("Prezime");
		
		col11.setCellValueFactory(new PropertyValueFactory<Konobar, String>("ime"));
		col22.setCellValueFactory(new PropertyValueFactory<Konobar, String>("prezime"));
		
		ObservableList<Konobar> gp1 = FXCollections.observableArrayList();
		
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("osobe.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine());
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String[] prim = line.get(j).split("@");
				if(prim.length == 3) {
					if(prim[2].equals("konobar") == true) {
						gp1.add(new Konobar(prim[0],prim[1]));
						prim = null;
					}
		    	}	
			}
			
			PrintWriter writer = new PrintWriter("osobe.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		tableView1.getColumns().addAll(col11, col22);
		tableView1.setItems(gp1);
		
		Button vratiMe1 = new Button("nazad");
		vratiMe1.setOnMouseClicked(e -> {
			vKonobari.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
		vKonobari.getChildren().addAll(tableView1, vratiMe1);
		
}

	public void dodajKonobara(VBox vbox, Scene menadzerProgram, Stage stage) {
		TextField ime = new TextField();
		TextField prezime = new TextField();
		ime.setPromptText("Ime konobara");
		prezime.setPromptText("Prezime konobara");
		
		HBox hKonobar1 = new HBox();
		Button vratiMe1 = new Button("nazad");
		vratiMe1.setOnMouseClicked(e -> {
			hKonobar1.getChildren().clear();
			vbox.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
		Button dodaj = new Button("dodaj");
		dodaj.setOnMouseClicked(e -> {
			Konobar product = new Konobar();
	        product.setIme(ime.getText());
	        product.setPrezime(prezime.getText());
	        ime.clear();
	        prezime.clear();
	        
	        product.dodajUFajl();
	        
	        Text text = new Text("Uspesno ste dodali novog konobara!");
	        text.setX(50); 
	        text.setY(50);
	        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
	        text.setUnderline(true);
	        vbox.getChildren().add(text);
		});
		
		hKonobar1.getChildren().addAll(ime, prezime, dodaj);
		
		vbox.getChildren().addAll(hKonobar1, vratiMe1);
		
	}
	
	public void obrisiKonobara(VBox vKonobari, Scene menadzerProgram, Stage stage) {
		TableView<Konobar> tableView1 = new TableView<>();
		TableColumn<Konobar, String> col11 = new TableColumn<>();
		TableColumn<Konobar, String> col22 = new TableColumn<>();
		
		col11.setText("Ime");
		col22.setText("Prezime");
		
		col11.setCellValueFactory(new PropertyValueFactory<Konobar, String>("ime"));
		col22.setCellValueFactory(new PropertyValueFactory<Konobar, String>("prezime"));
		
		ObservableList<Konobar> gp1 = FXCollections.observableArrayList();
		
		
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("osobe.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine());
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String[] prim = line.get(j).split("@");
				if(prim.length == 3) {
					if(prim[2].equals("konobar") == true) {
						gp1.add(new Konobar(prim[0],prim[1]));
						prim = null;
					}
		    	}	
			}
			
			PrintWriter writer = new PrintWriter("osobe.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			

		tableView1.getColumns().addAll(col11, col22);
		tableView1.setItems(gp1);
		
		HBox hKonobar1 = new HBox();
		
		Button vratiMe1 = new Button("nazad");
		vratiMe1.setOnMouseClicked(e -> {
			hKonobar1.getChildren().clear();
			vKonobari.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
		Button obrisi = new Button("obrisi");
		hKonobar1.getChildren().addAll(obrisi, vratiMe1);
		
		obrisi.setOnMouseClicked(e -> {
			ObservableList<Konobar> izabrano = FXCollections.observableArrayList();
			izabrano = tableView1.getSelectionModel().getSelectedItems();
			System.out.println(izabrano.get(0).getIme());
			String ime = izabrano.get(0).getIme();
			
			izabrano.forEach(gp1::remove);
			
			BufferedReader reader1;
			try {
				reader1 = new BufferedReader(new FileReader("osobe.txt"));
				ArrayList<String> line = new ArrayList<String>();
				line.add(reader1.readLine());
				while (line.get(line.size()-1) != null) {
					line.add(reader1.readLine());
				}
				line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
				
				PrintWriter writer = new PrintWriter("osobe.txt");
				for (int l = 0; l< line.size(); l++) {
					String[] prim = line.get(l).split("@");
					if(prim[0].compareTo(ime)!= 0) {
						writer.print(line.get(l));
						writer.println();
					}
				}
				
				writer.close();
				
				reader1.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			vKonobari.getChildren().removeAll(tableView1, hKonobar1);
			vKonobari.getChildren().addAll(tableView1, hKonobar1);
		});
		
		vKonobari.getChildren().addAll(tableView1, hKonobar1);
		
	}
	
	public void prozorZaPrikazStolova(VBox vBoxK, Stage stage, Scene konobarScene) {
		try {
			
			HBox hBoxK1 = new HBox();
			HBox hBoxK2 = new HBox();
			HBox hBoxK3 = new HBox();
			
			Image image = new Image(new FileInputStream("slike/table.png"));
			Image image1 = new Image(new FileInputStream("slike/table1.png"));
			Image image2 = new Image(new FileInputStream("slike/table2.png"));
			Image image3 = new Image(new FileInputStream("slike/table3.png"));
			Image image4 = new Image(new FileInputStream("slike/table4.png"));
			ImageView imageView1 = new ImageView(image2);
			ImageView imageView2 = new ImageView(image1);
			ImageView imageView3 = new ImageView(image3);
			ImageView imageView4 = new ImageView(image);
			ImageView imageView5 = new ImageView(image4);
			ImageView imageView6 = new ImageView(image);
			ImageView imageView7 = new ImageView(image2);
			ImageView imageView8 = new ImageView(image);
			ImageView imageView9 = new ImageView(image1);
			
			VBox vb1 = new VBox();
			
			Sto sto1 = new Sto("1");
			Scene s1 = new Scene(vb1, 500, 500);
			imageView1.setOnMouseClicked(e -> stage.setScene(s1));
			prozorZaKucanjeRacuna(s1, vb1,stage,sto1,konobarScene);
			
			VBox vb2 = new VBox();
			Scene s2 = new Scene(vb2, 500, 500);
			imageView2.setOnMouseClicked(e -> stage.setScene(s2));
			Sto sto2 = new Sto("2");
			prozorZaKucanjeRacuna(s2, vb2,stage,sto2,konobarScene);
			
			VBox vb3 = new VBox();
			Scene s3 = new Scene(vb3, 500, 500);
			imageView3.setOnMouseClicked(e -> stage.setScene(s3));
			Sto sto3 = new Sto("3");
			prozorZaKucanjeRacuna(s3, vb3,stage,sto3,konobarScene);
			
			VBox vb4 = new VBox();
			Scene s4 = new Scene(vb4, 500, 500);
			imageView4.setOnMouseClicked(e -> stage.setScene(s4));
			Sto sto4 = new Sto("4");
			prozorZaKucanjeRacuna(s4, vb4,stage,sto4,konobarScene);
			
			VBox vb5 = new VBox();
			Scene s5 = new Scene(vb5, 500, 500);
			imageView5.setOnMouseClicked(e -> stage.setScene(s5));
			Sto sto5 = new Sto("5");
			prozorZaKucanjeRacuna(s5, vb5,stage,sto5,konobarScene);
			
			VBox vb6 = new VBox();
			Scene s6 = new Scene(vb6, 500, 500);
			imageView6.setOnMouseClicked(e -> stage.setScene(s6));
			Sto sto6 = new Sto("6");
			prozorZaKucanjeRacuna(s6, vb6,stage,sto6,konobarScene);
			
			VBox vb7 = new VBox();
			Scene s7 = new Scene(vb7, 500, 500);
			imageView7.setOnMouseClicked(e -> stage.setScene(s7));
			Sto sto7 = new Sto("7");
			prozorZaKucanjeRacuna(s7, vb7,stage,sto7,konobarScene);
			
			VBox vb8 = new VBox();
			Scene s8 = new Scene(vb8, 500, 500);
			imageView8.setOnMouseClicked(e -> stage.setScene(s8));
			Sto sto8 = new Sto("8");
			prozorZaKucanjeRacuna(s8, vb8,stage,sto8,konobarScene);
			
			VBox vb9 = new VBox();
			Scene s9 = new Scene(vb9, 500, 500);
			imageView9.setOnMouseClicked(e -> stage.setScene(s9));
			Sto sto9 = new Sto("9");
			prozorZaKucanjeRacuna(s9, vb9,stage,sto9,konobarScene);
			
			
			imageView1.setFitHeight(200); 
			imageView1.setFitWidth(200); 
			imageView2.setFitHeight(200); 
			imageView2.setFitWidth(200);
			imageView3.setFitHeight(200); 
			imageView3.setFitWidth(200); 
			imageView4.setFitHeight(200); 
			imageView4.setFitWidth(200); 
			imageView5.setFitHeight(200); 
			imageView5.setFitWidth(200); 
			imageView6.setFitHeight(200); 
			imageView6.setFitWidth(200); 
			imageView7.setFitHeight(200); 
			imageView7.setFitWidth(200); 
			imageView8.setFitHeight(200); 
			imageView8.setFitWidth(200); 
			imageView9.setFitHeight(200); 
			imageView9.setFitWidth(200); 
			 
			hBoxK1.getChildren().addAll(imageView1, imageView2,imageView3);
			hBoxK2.getChildren().addAll(imageView4, imageView5,imageView6);
			hBoxK3.getChildren().addAll(imageView7, imageView8,imageView9);
			vBoxK.getChildren().addAll(hBoxK1, hBoxK2,hBoxK3);
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}  
	}
	
	public void prozorZaKucanjeRacuna(Scene scene, VBox vbox, Stage stage, Sto s, Scene konobarScene) throws FileNotFoundException {
		
		Konobar konobar = new Konobar(); 
		Racun racun = new Racun();
		
		//ako je sto zauzet konobar je vec izabran i racun vec postoji
		if(s.stanje() == false) {
			konobar.setIme(s.getRacun().getKonobar().getIme());
			konobar.setPrezime(s.getRacun().getKonobar().getPrezime());
			racun.setRacun(s.getRacun());
		}
			
		Image image1 = new Image(new FileInputStream("slike/waiter.png"));
		ImageView imageView1 = new ImageView(image1);
		imageView1.setX(0);
		imageView1.setY(50);
		imageView1.setFitHeight(80); 
		imageView1.setFitWidth(80); 
		
		
		Button dodajArtikal = new Button("Dodaj Artikal Na Racun");
		Button izdajRacun = new Button("Izdaj Racun");
		Button nazad = new Button("Vrati Se!");
		Button obrisiArtikal = new Button("Obrisi artikal");
		Text cena = new Text("Ukupna Cena Racuna: " + racun.getUkupnaCena());
		Text konobarIzabran = new Text();
		Button zapocniNovi = new Button("Kucaj Novi Racun");
		ArrayList<GotoviProizvodi> gotovi = new ArrayList<GotoviProizvodi>();
		
		if(konobar.getIme() != null) {
			konobarIzabran.setText("Konobar : " + konobar.getIme() + " " + konobar.getPrezime());
		}
		
		TableView<GotoviProizvodi> tv = new TableView<GotoviProizvodi>();
		TableColumn<GotoviProizvodi, String> kol1 = new TableColumn<GotoviProizvodi, String>("naziv artikla");
		TableColumn<GotoviProizvodi, String> kol2 = new TableColumn<GotoviProizvodi, String>("cena artikla");
		kol1.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("naziv"));
		kol2.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("cena"));
		
		tv.getItems().clear();
		tv.getColumns().addAll(kol1, kol2);
			
		ObservableList<GotoviProizvodi> ol = FXCollections.observableArrayList();
		
		
		if(konobar.getIme() == null) {
			vbox.getChildren().clear();
			vbox.getChildren().addAll(imageView1, dodajArtikal, izdajRacun, nazad); 
		}else {
			cena.setText("Ukupna Cena Racuna: " + racun.getUkupnaCena());
			vbox.getChildren().addAll(konobarIzabran, tv, dodajArtikal, obrisiArtikal, cena, izdajRacun, nazad); 
		}
			
		//dugme za vracanje na predhodnu stranicu
		nazad.setOnMouseClicked(ee->{
			vbox.getChildren().clear();
			if(konobar.getIme() == null) {
				vbox.getChildren().addAll(imageView1, dodajArtikal, izdajRacun, nazad); 
			}else {
				cena.setText("Ukupna Cena Racuna: " + racun.getUkupnaCena());
				vbox.getChildren().addAll(konobarIzabran, tv, dodajArtikal, obrisiArtikal, cena, izdajRacun, nazad); 
			}
			stage.setScene(konobarScene);
		});
		
		//dugme za brisanje artikla sa racuna
		obrisiArtikal.setOnMouseClicked(a->{
			ObservableList<GotoviProizvodi> izabrano = FXCollections.observableArrayList();
			izabrano = tv.getSelectionModel().getSelectedItems();
			
			for(int i = 0; i< gotovi.size(); i++) {
				if(izabrano.get(0).getNaziv() == gotovi.get(i).getNaziv()) {
					int ukupnaCena= racun.getUkupnaCena() - gotovi.get(i).getCena();
					racun.setUkupnaCena(ukupnaCena);
					cena.setText("Ukupna Cena Racuna: " + racun.getUkupnaCena());
					gotovi.remove(i);
				}
			}
			
			izabrano.forEach(ol::remove);
			
		});
		
	//prikaz slike kolnobara gde moze da se izabere ko kuca racun
	imageView1.setOnMouseClicked(el->{
	
	vbox.getChildren().clear();
	
	TableView<Konobar> tableView = new TableView<>();
	TableColumn<Konobar, String> col1 = new TableColumn<>();
	TableColumn<Konobar, String> col2 = new TableColumn<>();
	
	col1.setText("Ime");
	col2.setText("Prezime");
	
	col1.setCellValueFactory(new PropertyValueFactory<Konobar, String>("ime"));
	col2.setCellValueFactory(new PropertyValueFactory<Konobar, String>("prezime"));
	
	ObservableList<Konobar> gp = FXCollections.observableArrayList();
	
	try {
		BufferedReader reader1;
		reader1 = new BufferedReader(new FileReader("osobe.txt"));
		ArrayList<String> line = new ArrayList<String>();
		line.add(reader1.readLine());
		while (line.get(line.size()-1) != null) {
			line.add(reader1.readLine());
		}
		line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
		
		for (int j = 0; j < line.size(); j++) {
			String[] prim = line.get(j).split("@");
			if(prim.length == 3) {
				if(prim[2].equals("konobar") == true) {
					gp.add(new Konobar(prim[0],prim[1]));
					prim = null;
				}
	    	}	
		}
		
		PrintWriter writer = new PrintWriter("osobe.txt");
		for (int l = 0; l< line.size(); l++) {
			writer.print(line.get(l));
			writer.println();
		}
		
		writer.close();
		
		reader1.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
		

	tableView.getColumns().addAll(col1, col2);
	tableView.setItems(gp);
	
	Button izaberi = new Button("izaberi");
		
	//dugme za biranje konobara
	izaberi.setOnMouseClicked(e-> {
		tv.getItems().clear();
		ObservableList<Konobar> izabrano = FXCollections.observableArrayList();
		izabrano = tableView.getSelectionModel().getSelectedItems();
		Konobar k = izabrano.get(0);
		String[] split = k.toSting().split("@");
		
		konobar.setIme(split[0]);
		konobar.setPrezime(split[1]);
		racun.setKonobar(konobar);
	
		konobarIzabran.setText("Racun izdaje konobar: " + split[0] + " " + split[1]);
		vbox.getChildren().clear();
		cena.setText("Ukupna Cena Racuna: " + racun.getUkupnaCena());
		vbox.getChildren().addAll(konobarIzabran, tv, dodajArtikal, obrisiArtikal, cena, izdajRacun, nazad);
			
		});
	
	vbox.getChildren().addAll(tableView, izaberi);
		
	});
			
			
	//dugme dodaj artikal
	dodajArtikal.setOnMouseClicked(em->{
		
		vbox.getChildren().clear();
		
		TableView<GotoviProizvodi> tableView = new TableView<>();
		TableColumn<GotoviProizvodi, String> col1 = new TableColumn<>();
		TableColumn<GotoviProizvodi, String> col2 = new TableColumn<>();
		TableColumn<GotoviProizvodi, String> col3 = new TableColumn<>();
		
		col1.setText("Naziv");
		col2.setText("Cena");
		col3.setText("Tip");
		
		col1.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("naziv"));
		col2.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("cena"));
		col3.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("tip"));
		
		tableView.getColumns().addAll(col1, col2, col3);
		
		ObservableList<GotoviProizvodi> gp = FXCollections.observableArrayList();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("cenovnik.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader.readLine());
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String[] prim = line.get(j).split("@");
				if(prim.length == 3) {
		        	Integer k = Integer.parseInt(prim[1]);
		        	String p;
		        	if(prim[2].compareTo("pice") == 0) {
		        		p = "pice";
		        	}else {
		        		p = "hrana";
		        	}
		        	
					gp.add(new GotoviProizvodi(prim[0],k, p));
					prim = null;
		    	}	
			}
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tableView.setItems(gp);
		Button izaberi1 = new Button("izaberi");
		
		vbox.getChildren().addAll(tableView, izaberi1);
				
			//dugme za biranje proizvoda koji se nalaze na racunu
			izaberi1.setOnMouseClicked(ell->{
				tv.getItems().clear();
				
				ObservableList<GotoviProizvodi> izabrano = FXCollections.observableArrayList();
				izabrano = tableView.getSelectionModel().getSelectedItems();
				GotoviProizvodi got = izabrano.get(0);
				gotovi.add(got);
				racun.setProizvodi(gotovi);
				int ukupnaCena = 0;
				for( int i = 0 ; i< gotovi.size(); i++) {
					ukupnaCena = ukupnaCena + gotovi.get(i).getCena();
					ol.add(gotovi.get(i));
				}
				
				racun.setUkupnaCena(ukupnaCena);
				cena.setText("Ukupna Cena Racuna: " + racun.getUkupnaCena());
				
				vbox.getChildren().clear();
				
				tv.setItems(ol);
				vbox.getChildren().clear();
				vbox.getChildren().addAll(konobarIzabran, tv, dodajArtikal, obrisiArtikal, cena, izdajRacun, nazad);
				
			});
			
		});
			
		//dugme za izdavanje racuna 
		izdajRacun.setOnMouseClicked(ep->{
		if(racun.getUkupnaCena() == 0) {
			Text text1 = new Text("Na Ovom Stolu Jos Uvek Ne Postoji Racun!");
			vbox.getChildren().addAll(text1, imageView1,dodajArtikal, izdajRacun);
			
		}else {
		
		racun.setDatum("2020-05-18");
		s.dodajRacun(racun);	
		konobar.izdavanjeRacuna(s);
		
		Text text = new Text("+++++++RACUN+++++++++++"+ System.lineSeparator());
		
		String primer = "Datum:" + racun.getDatum() + System.lineSeparator()+ "Naziv: 			Cena: " + System.lineSeparator();
		for( int i = 0; i< racun.getProizvodi().size(); i ++) {
			primer = primer + racun.getProizvodi().get(i).getNaziv()+ "			"+ racun.getProizvodi().get(i).getCena() + System.lineSeparator();
		}
		Text text1 = new Text(primer);
		Text text2 = new Text(System.lineSeparator() + "++++++++++++++++++++++++++++"+ System.lineSeparator());
			
		
		vbox.getChildren().clear();
		HBox hbox = new HBox();
		hbox.getChildren().addAll(zapocniNovi);
		vbox.getChildren().addAll(text,konobarIzabran,text1, cena, text2, hbox);
		s.isprazniSto();
		racun.setUkupnaCena(0);
		
		
		}});
			
		//dugme za kucanje novog racuna
		zapocniNovi.setOnMouseClicked(ek->{
			
			vbox.getChildren().clear();
			vbox.getChildren().addAll(imageView1, dodajArtikal, izdajRacun, nazad); 
			
			gotovi.clear();
			konobar.setIme(null);
			konobar.setPrezime(null);
			racun.setRacun(null);
			racun.setUkupnaCena(0);
			
		});
			
	}
}


