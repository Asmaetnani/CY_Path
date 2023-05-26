package quoridor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Grille2 extends Application{
     
	private static final int GRID_SIZE = 9;
    //private static final int SQUARE_SIZE = 60;
	//private static final double BUTTON_WIDTH = 70.0;
	private static final double BUTTON_HIGH = 120.0;
	private  int NBR_BARRIERE_TOT = 20;
	private int nbrBrr1 = NBR_BARRIERE_TOT/2;
	private int nbrBrr2 = NBR_BARRIERE_TOT/2;
	private int col,row;
	

	BorderPane bordpane = new BorderPane();
	GridPane gridPane = new GridPane();
	
	@Override
	public void start(Stage primaryStage)throws Exception {
		Button topButton = new Button("NB : 10");
		topButton.setPrefSize(BUTTON_HIGH, 50);
		topButton.setStyle("-fx-border-color: #562B05");
		topButton.setStyle("-fx-background-color: #DEB887");
		
        topButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				if(nbrBrr1 > 0) {
				nbrBrr1 -= 1;
				topButton.setText("NB :" + nbrBrr1);
				
			}
			}
		});
  
        Button bottomButton = new Button("NB : 10");
		bottomButton.setPrefSize(BUTTON_HIGH, 50);
		bottomButton.setStyle("-fx-border-color: #562B05");
		bottomButton.setStyle("-fx-background-color: #DEB887");
		
	      //nbrBrr = NBR_BARRIERE_TOT/2;
		bottomButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				
				if(nbrBrr2 > 0) {
				nbrBrr2 -= 1;
				bottomButton.setText("NB : " + nbrBrr2);
				
				for(int i = 0; i < nbrBrr2; i++) {
				Coordinate coordinates = new Coordinate(row, col);
	            int direction = 0; // Remplacez par la direction appropriée pour la barrière

	            Barrier barrier = new Barrier(coordinates, direction);
	            displayBarrier(barrier);
				
				}
				
			}
			}
		});
		
		
       

				bordpane.setStyle("-fx-background-color:#E0CDA9");
				
				//poser le topbuuton en haut de BoderPane
				bordpane.setTop(topButton);
				//Mettre le bouton au centre de la zone 
				BorderPane.setAlignment(topButton, Pos.CENTER);
				BorderPane.setMargin(topButton, new Insets(15, 0, 0, 0));
				
				
				bordpane.setBottom(bottomButton);
				BorderPane.setAlignment(bottomButton, Pos.CENTER);
				BorderPane.setMargin(bottomButton, new Insets(0, 0, 15, 0));
			
				
				 GridPane gridPane = createGridPane();
				 bordpane.setCenter(gridPane);
		        Scene scene = new Scene(bordpane, 400, 400);
		       
				primaryStage.setScene(scene);
		        primaryStage.setTitle("Quaridor Game");
		        primaryStage.show();
			}
			
        

			
	private GridPane createGridPane() {
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setHgap(5);
	    gridPane.setVgap(5);

	    for (row = 0; row < GRID_SIZE; row++) {
	        for (col = 0; col < GRID_SIZE; col++) {
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
		  
	
		  private Rectangle createBarrier(Barrier barrier) {
				Rectangle rectangle;
				
				if(barrier.getDirection() == Direction.VERTICAL) {
					rectangle = new Rectangle(3, 60);  // Two squares high for vertical
				} else {
					rectangle = new Rectangle(60, 3);  // Two squares wide for horizontal
				}
				
				rectangle.setFill(Paint.valueOf("#000000"));  // Black color
				rectangle.setStroke(Color.BLUE);
				rectangle.setStrokeWidth(3);
				return rectangle;
			}

			public void displayBarrier(Barrier barrier, GridPane gridPane) {
				Rectangle rectangle = createBarrier(barrier);
				rectangle.setTranslateX(-5);
				//rectangle.setTranslateY(5);
				GridPane.setConstraints(rectangle, barrier.coordinate.getPositionX() * 2 + 1, 
						barrier.coordinate.getPositionY() * 2 + 1);
						gridPane.getChildren().add(rectangle);
			}
		  
			public static void main(String[] args) {
		        launch(args);
		    }


			

		}
