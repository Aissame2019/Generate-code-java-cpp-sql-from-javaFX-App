module userInterface {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.base;
	requires javafx.base;
	requires dom4j;
	requires org.jdom2;
	
	
	opens application to javafx.graphics, javafx.fxml;
}
