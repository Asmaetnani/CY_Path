package quoridor;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.BorderStroke;
//import javafx.scene.layout.BorderStrokeStyle;
//import javafx.scene.layout.BorderWidths;
//import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Grille extends Application{
     
	private static final int GRID_SIZE = 9;
    //private static final int SQUARE_SIZE = 50;
	private static final double BUTTON_WIDTH = 70.0;
	private static final double BUTTON_HIGH = 120.0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button topButton = new Button("Top");
		topButton.setPrefSize(BUTTON_HIGH, 50);
		topButton.setStyle("-fx-border-color: #562B05");
		topButton.setStyle("-fx-background-color: #DEB887");
	

		Button rightButton = new Button("Right");
		rightButton.setPrefSize(BUTTON_HIGH, BUTTON_WIDTH);
		rightButton.setStyle("-fx-border-color: #562B05");
		rightButton.setStyle("-fx-background-color: #DEB887");
		rightButton.setRotate(90);
		
		
		Button leftButton = new Button("Left");
		leftButton.setPrefSize(BUTTON_HIGH, BUTTON_WIDTH);
		leftButton.setStyle("-fx-border-color: #562B05");
		leftButton.setStyle("-fx-background-color: #DEB887");
		leftButton.setRotate(270);
	
		Button bottomButton = new Button("bottom");
		bottomButton.setPrefSize(BUTTON_HIGH, 50);
		bottomButton.setStyle("-fx-border-color: #562B05");
		bottomButton.setStyle("-fx-background-color: #DEB887");
		
		
		
		BorderPane bordpane = new BorderPane();
		bordpane.setStyle("-fx-background-color:#E0CDA9");
		
		//poser le topbuuton en haut de BoderPane
		bordpane.setTop(topButton);
		//Mettre le bouton au centre de la zone 
		BorderPane.setAlignment(topButton, Pos.CENTER);
		BorderPane.setMargin(topButton, new Insets(15, 0, 0, 0));
		
		
		bordpane.setBottom(bottomButton);
		BorderPane.setAlignment(bottomButton, Pos.CENTER);
		BorderPane.setMargin(bottomButton, new Insets(0, 0, 15, 0));
		
		
		bordpane.setRight(rightButton);
		BorderPane.setAlignment(rightButton, Pos.CENTER);
		BorderPane.setMargin(rightButton, new Insets(0, 150, 0, 15));
		
		bordpane.setLeft(leftButton);
		BorderPane.setAlignment(leftButton, Pos.CENTER);
		BorderPane.setMargin(leftButton, new Insets(0, 15, 0, 150));
		
		 GridPane gridPane = createGridPane();
		 bordpane.setCenter(gridPane);
        Scene scene = new Scene(bordpane, 400, 400);
       
		primaryStage.setScene(scene);
        primaryStage.setTitle("Quaridor Game");
        primaryStage.show();
	}
	
	
private GridPane createGridPane() {
	
	GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(5);
    gridPane.setVgap(5);

    for (int row = 0; row < GRID_SIZE; row++) {
        for (int col = 0; col < GRID_SIZE; col++) {
            Rectangle square = createSquare();
            gridPane.add(square, col, row);
        }
    }
    return gridPane;

}
  private Rectangle createSquare(){
	  Rectangle square = new Rectangle(60,60);
	  square.setFill(Paint.valueOf("#562B05"));
      square.setStroke(Color.CHOCOLATE);
	  square.setStrokeWidth(3);
	  return square;
  }
	public static void main(String[] args) {
        launch(args);
    }

}
