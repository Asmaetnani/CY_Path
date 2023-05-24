package Control;

import Abstraction.*;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class GameTurn extends Application implements Observer {
	public static void main(String[] args) {
		launch(args);
	}

	public void update(Game game, Board board) {

	}

	@Override
	public void start(Stage primaryStage) {
		Button placeBarrierButton = new Button("Placer barrière");
		placeBarrierButton.setOnAction(event -> {
			showBarrierWindow();
			primaryStage.close();
		});

		Button movePawnButton = new Button("Déplacer pion");
		movePawnButton.setOnAction(event -> {
			showPawnWindow();
			primaryStage.close();
		});

		VBox vbox = new VBox(placeBarrierButton, movePawnButton);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 300, 300);

		primaryStage.setTitle("Your turn");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void showBarrierWindow() {
		Stage barrierStage = new Stage();

		Slider ligneSlider = new Slider(0, 9, 0);
		Slider colonneSlider = new Slider(0, 9, 0);

		ligneSlider.setShowTickMarks(true);
		ligneSlider.setShowTickLabels(true);
		colonneSlider.setShowTickMarks(true);
		colonneSlider.setShowTickLabels(true);

		Label ligneLabel = new Label("Ligne: 0");
		Label colonneLabel = new Label("Colonne: 0");

		ligneSlider.valueProperty().addListener(
				(obs, oldVal, newVal) -> ligneLabel.setText("Ligne: " + (int) Math.round(newVal.doubleValue())));

		colonneSlider.valueProperty().addListener(
				(obs, oldVal, newVal) -> colonneLabel.setText("Colonne: " + (int) Math.round(newVal.doubleValue())));

		ToggleButton orientationToggle = new ToggleButton("Vertical");
		orientationToggle.setOnAction(event -> {
			if (orientationToggle.isSelected()) {
				orientationToggle.setText("Horizontal");
			} else {
				orientationToggle.setText("Vertical");
			}
		});

		Button validateButton = new Button("Valider");
		validateButton.setOnAction(event -> {
			int ligne = (int) Math.round(ligneSlider.getValue());
			int colonne = (int) Math.round(colonneSlider.getValue());
			int orientation = 0;
			if (orientationToggle.getText() == "horizontal")
				orientation = 1;
			
			Coordinate c = new Coordinate(ligne, colonne);
			Barrier b = new Barrier(c, orientation);
			GameController.placeBarrier(b);

			/*
			 * System.out.println("Ligne: " + ligne); System.out.println("Colonne: " +
			 * colonne); System.out.println("Orientation: " + orientation);
			 */

			barrierStage.close();
		});

		VBox vbox = new VBox(ligneLabel, ligneSlider, colonneLabel, colonneSlider, orientationToggle, validateButton);

		Scene scene = new Scene(vbox, 300, 300);

		barrierStage.setTitle("Placer barrière");
		barrierStage.setScene(scene);
		barrierStage.show();
	}

	private void showPawnWindow() {
		Stage pawnStage = new Stage();

		Button upButton = new Button("Haut");
		upButton.setOnAction(event -> pawnStage.close());

		Button downButton = new Button("Bas");
		downButton.setOnAction(event -> pawnStage.close());

		Button leftButton = new Button("Gauche");
		leftButton.setOnAction(event -> pawnStage.close());

		Button rightButton = new Button("Droite");
		rightButton.setOnAction(event -> pawnStage.close());

		VBox vbox = new VBox(upButton, downButton, leftButton, rightButton);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 300, 300);

		pawnStage.setTitle("Déplacer pion");
		pawnStage.setScene(scene);
		pawnStage.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
