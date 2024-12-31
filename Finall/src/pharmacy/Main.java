package pharmacy;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private double x = 0;
    private double y = 0;
    
	public void start(Stage stage) {
		try {
			StackPane root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
			Scene scene = new Scene(root);
			
			 root.setOnMousePressed((MouseEvent event) ->{
		            x = event.getSceneX();
		            y = event.getSceneY();
		        });
		        
		        root.setOnMouseDragged((MouseEvent event) ->{
		            stage.setX(event.getScreenX() - x);
		            stage.setY(event.getScreenY() - y);
		            
		            stage.setOpacity(.8);
		        });
		        
		        root.setOnMouseReleased((MouseEvent event) ->{
		            stage.setOpacity(1);
		        });
		        
		        stage.initStyle(StageStyle.TRANSPARENT);
			
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
