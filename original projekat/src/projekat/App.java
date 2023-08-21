package projekat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) throws FileNotFoundException{
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		Scene glavnaStrana = new Scene(vBox, 600, 600);
		
		Image image3 = new Image(new FileInputStream("slike/blue.png"));
		ImageView pravougaonik = new ImageView(image3);
		pravougaonik.setScaleX(300);
		pravougaonik.setFitHeight(80); 
		pravougaonik.setFitWidth(80); 
		
		Image image1 = new Image(new FileInputStream("slike/menager.png"));
		ImageView menadzerDugme = new ImageView(image1);
		
		Image image2 = new Image(new FileInputStream("slike/waiter.png"));
		ImageView konobarDugme = new ImageView(image2);
		
		Konobar konobar = new Konobar();
		Menadzer menadzer = new Menadzer();
		
		menadzerDugme.setOnMouseClicked(e -> menadzer.prozor(stage, hBox, vBox, glavnaStrana));
		konobarDugme.setOnMouseClicked(a->konobar.prozor(stage, hBox, vBox, glavnaStrana));
		
		menadzerDugme.setFitHeight(80); 
		menadzerDugme.setFitWidth(80); 
		konobarDugme.setFitHeight(80); 
		konobarDugme.setFitWidth(80);
		
		try {

			Image image;
			image = new Image(new FileInputStream("slike/logo.jpg"));
			ImageView imageView = new ImageView(image); 
			 
			imageView.setFitHeight(455); 
			imageView.setFitWidth(600); 
			
			ImageView imageView1 = new ImageView(image); 
			 
			imageView1.setFitHeight(455); 
			imageView1.setFitWidth(500); 
			vBox.getChildren().add(imageView);
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		vBox.setSpacing(10);
		hBox.setSpacing(95);
		hBox.getChildren().addAll(pravougaonik,menadzerDugme, konobarDugme);
		vBox.getChildren().addAll(hBox);
		stage.setTitle("Projekat - Restoran");
		stage.setScene(glavnaStrana);
		stage.show();
	}
}
