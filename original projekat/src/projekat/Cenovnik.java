package projekat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Cenovnik {
	private ArrayList<GotoviProizvodi> namirnice = new ArrayList<GotoviProizvodi>();
	
	Cenovnik(){
		
	}

	public ArrayList<GotoviProizvodi> getNamirnice() {
		return namirnice;
	}

	public void setNamirnice(ArrayList<GotoviProizvodi> namirnice) {
		this.namirnice = namirnice;
	}
	
	public void prozorZaPrikazCenovnika(VBox vCenovnik, Scene menadzerProgram, Stage stage) {
		
		Button vratiMe = new Button("nazad");
		vratiMe.setOnMouseClicked(e -> {
			vCenovnik.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
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
		vCenovnik.getChildren().addAll(tableView, vratiMe);
		
	}

	
	public void prozorZaPrikazPica(VBox vCenovnik, Scene menadzerProgram, Stage stage) {
		
		Button vratiMe = new Button("nazad");
		HBox hbox = new HBox();
		vratiMe.setOnMouseClicked(e -> {
			hbox.getChildren().clear();
			vCenovnik.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		Button obrisi = new Button("obrisi pice");
		
		TableView<GotoviProizvodi> tableView = new TableView<>();
		TableColumn<GotoviProizvodi, String> col1 = new TableColumn<>();
		TableColumn<GotoviProizvodi, String> col2 = new TableColumn<>();
		
		col1.setText("Naziv");
		col2.setText("Cena");
		
		col1.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("naziv"));
		col2.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("cena"));
		
		tableView.getColumns().addAll(col1, col2);
		
		ObservableList<GotoviProizvodi> tipPice = FXCollections.observableArrayList();
		
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
		        	if(prim[2].compareTo("pice") == 0) {
		        		tipPice.add(new GotoviProizvodi(prim[0], k, "pice"));
		        	
					prim = null;
		    	}	
			}}
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		tableView.setItems(tipPice);
		
		
		obrisi.setOnMouseClicked(e->{
			ObservableList<GotoviProizvodi> izabrano = FXCollections.observableArrayList();
			izabrano = tableView.getSelectionModel().getSelectedItems();
			
			BufferedReader reader1;
			try {
				reader1 = new BufferedReader(new FileReader("cenovnik.txt"));
				ArrayList<String> line = new ArrayList<String>();
				line.add(reader1.readLine());
				while (line.get(line.size()-1) != null) {
					line.add(reader1.readLine());
				}
				line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
				
				PrintWriter writer = new PrintWriter("cenovnik.txt");
				for (int l = 0; l< line.size(); l++) {
					String[] prim = line.get(l).split("@");
					if(prim[0].compareTo(izabrano.get(0).getNaziv())!= 0) {
						writer.print(line.get(l));
						writer.println();
					}
				}
				
				writer.close();
				reader1.close();
				
				izabrano.forEach(tipPice::remove);
			} catch (IOException ex) {
				ex.printStackTrace();
			}});
		
		hbox.getChildren().addAll(obrisi, vratiMe);
		vCenovnik.getChildren().addAll(tableView, hbox);
	}
	
	public void dodajPice(VBox vCenovnik, Scene menadzerProgram, Stage stage) {

		Button vratiMe = new Button("nazad");
		HBox hbox = new HBox();
		vratiMe.setOnMouseClicked(e -> {
			hbox.getChildren().clear();
			vCenovnik.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
		TextField nazivArtikla = new TextField();
		TextField cenaArtikla = new TextField();
		nazivArtikla.setPromptText("Naziv Artikla");
		cenaArtikla.setPromptText("Cena Artikla");
		
		Button dodaj = new Button("dodaj");
		dodaj.setOnMouseClicked(e -> {
		
		GotoviProizvodi product = new GotoviProizvodi();
        product.setNaziv(nazivArtikla.getText());
        product.setCena(Integer.parseInt(cenaArtikla.getText()));
        product.setTip("pice");
        
        nazivArtikla.clear();
        cenaArtikla.clear();
        
        product.dodajUFajl();
        
        Text text = new Text("Uspesno ste dodali novi artikal!");
        text.setX(50); 
        text.setY(50);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        text.setUnderline(true);
        
        vCenovnik.getChildren().add(text);
		});
        
		
		hbox.getChildren().addAll(nazivArtikla, cenaArtikla, dodaj);
        vCenovnik.getChildren().addAll(hbox, vratiMe);
	}
	
	public void prozorZaPrikazHrane(VBox vCenovnik, Scene menadzerProgram, Stage stage) {

		Button vratiMe = new Button("nazad");
		HBox hbox = new HBox();
		vratiMe.setOnMouseClicked(e -> {
			hbox.getChildren().clear();
			vCenovnik.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		Button obrisi = new Button("obrisi hranu");
		
		TableView<GotoviProizvodi> tableView = new TableView<>();
		TableColumn<GotoviProizvodi, String> col1 = new TableColumn<>();
		TableColumn<GotoviProizvodi, String> col2 = new TableColumn<>();
		
		col1.setText("Naziv");
		col2.setText("Cena");
		
		col1.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("naziv"));
		col2.setCellValueFactory(new PropertyValueFactory<GotoviProizvodi, String>("cena"));
		
		tableView.getColumns().addAll(col1, col2);
		
		ObservableList<GotoviProizvodi> tipPice = FXCollections.observableArrayList();
		
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
		        	if(prim[2].compareTo("hrana") == 0) {
		        		tipPice.add(new GotoviProizvodi(prim[0], k, "hrana"));
		        	
					prim = null;
		    	}	
			}}
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		tableView.setItems(tipPice);
		
		
		obrisi.setOnMouseClicked(e->{
			ObservableList<GotoviProizvodi> izabrano = FXCollections.observableArrayList();
			izabrano = tableView.getSelectionModel().getSelectedItems();
			
			BufferedReader reader1;
			try {
				reader1 = new BufferedReader(new FileReader("cenovnik.txt"));
				ArrayList<String> line = new ArrayList<String>();
				line.add(reader1.readLine());
				while (line.get(line.size()-1) != null) {
					line.add(reader1.readLine());
				}
				line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
				
				PrintWriter writer = new PrintWriter("cenovnik.txt");
				for (int l = 0; l< line.size(); l++) {
					String[] prim = line.get(l).split("@");
					if(prim[0].compareTo(izabrano.get(0).getNaziv())!= 0) {
						writer.print(line.get(l));
						writer.println();
					}
				}
				
				writer.close();
				reader1.close();
				
				izabrano.forEach(tipPice::remove);
			} catch (IOException ex) {
				ex.printStackTrace();
			}});
		
		hbox.getChildren().addAll(obrisi, vratiMe);
		vCenovnik.getChildren().addAll(tableView, hbox);
	}
	
	public void dodajHranu(VBox vCenovnik, Scene menadzerProgram, Stage stage) {
		Button vratiMe = new Button("nazad");
		HBox hbox = new HBox();
		vratiMe.setOnMouseClicked(e -> {
			hbox.getChildren().clear();
			vCenovnik.getChildren().clear();
			stage.setScene(menadzerProgram);
		});
		
		TextField nazivArtikla = new TextField();
		TextField cenaArtikla = new TextField();
		nazivArtikla.setPromptText("Naziv Artikla");
		cenaArtikla.setPromptText("Cena Artikla");
		
		Button dodaj = new Button("dodaj");
		dodaj.setOnMouseClicked(e -> {
		
		GotoviProizvodi product = new GotoviProizvodi();
        product.setNaziv(nazivArtikla.getText());
        product.setCena(Integer.parseInt(cenaArtikla.getText()));
        product.setTip("hrana");
        
        nazivArtikla.clear();
        cenaArtikla.clear();
        
        product.dodajUFajl();
        
        Text text = new Text("Uspesno ste dodali novi artikal!");
        text.setX(50); 
        text.setY(50);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        text.setUnderline(true);
        
        vCenovnik.getChildren().add(text);
		});
        
		
		hbox.getChildren().addAll(nazivArtikla, cenaArtikla, dodaj);
        vCenovnik.getChildren().addAll(hbox, vratiMe);
	}
		
}
