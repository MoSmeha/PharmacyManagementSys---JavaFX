module Finally {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens pharmacy to javafx.graphics, javafx.fxml, javafx.base;
	 
}
