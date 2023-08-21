module projekat {
	requires javafx.fxml;
    requires javafx.controls;
    opens projekat to javafx.graphics;
    exports	projekat;
}