import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Création de la fenêtre principale
        primaryStage.setTitle("Quoridor Game");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(true);
        primaryStage.setAlwaysOnTop(true);

        // Chargement de l'image de fond
        Image imageFond = new Image(getClass().getResourceAsStream("fondEcr.PNG"));

        // Création d'un ImageView pour afficher l'image de fond avec redimensionnement automatique
        ImageView imageViewFond = new ImageView(imageFond);
        imageViewFond.setPreserveRatio(true);
        imageViewFond.fitWidthProperty().bind(primaryStage.widthProperty());
        imageViewFond.fitHeightProperty().bind(primaryStage.heightProperty());
      
        // Appliquer l'effet de flou à l'image de fond
        GaussianBlur blur = new GaussianBlur(10); // Choisir le rayon du flou
        imageViewFond.setEffect(blur);

        // Création des boutons
        Button btnPlay2 = new Button("PLAY with 2");
        btnPlay2.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #964B00;");
        Button btnPlay4 = new Button("PLAY with 4");
        btnPlay4.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #964B00;");
        Button btnQuit = new Button("QUIT");
        btnQuit.setPrefSize(210, 40);
        btnQuit.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #964B00;");

        // Utilisation de Region pour les boutons afin qu'ils s'adaptent à la taille de la fenêtre
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        spacer1.setMaxHeight(Double.MAX_VALUE);
        spacer2.setMaxHeight(Double.MAX_VALUE);

        // Positionnement des boutons dans une VBox flexible
        VBox buttonsBox = new VBox(10, spacer1, btnPlay2, btnPlay4, spacer2, btnQuit);
        buttonsBox.setAlignment(Pos.CENTER);

        // Gestionnaire d'événements pour le bouton QUIT
        btnQuit.setOnAction(event -> {
            primaryStage.close(); // Fermer la fenêtre
        });

        // Gestionnaire d'événements pour le bouton PLAY with 2
        btnPlay2.setOnAction(event -> {
            // Logique pour jouer avec 2 joueurs
        	Grille grille = new Grille(); // Création d'une instance de la classe Grille
          try {
                grille.start(new Stage()); // Exécution de la classe Grille
                 } catch (Exception e) {
                 e.printStackTrace();
             }
            primaryStage.close(); // Fermer la fenêtre principal
        });

        // Gestionnaire d'événements pour le bouton PLAY with 4
        btnPlay4.setOnAction(event -> {
            // Logique pour jouer avec 4 joueurs
        	Grille grille = new Grille(); // Création d'une instance de la classe Grille
        	try {
                grille.start(new Stage()); // Exécution de la classe Grille
                 } catch (Exception e) {
                 e.printStackTrace();
             }
            primaryStage.close();
        });

        // Création d'un StackPane pour centrer l'image de fond et les boutons
        StackPane root = new StackPane(imageViewFond, buttonsBox);

        // Création de la scène
        Scene scene = new Scene(root, 1000, 600);

        // Configuration de la scène principale
        primaryStage.setScene(scene);

        // Affichage de la fenêtre principale
        primaryStage.show();
    }
}
