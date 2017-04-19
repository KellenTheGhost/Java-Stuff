/*
Kellen Parker
Pock Paper Scissors
*/

package rps;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RPS extends Application {

    private final ImageView ROCK = new ImageView(new java.io.File("rock.jpg").toURI().toString());
    private final ImageView PAPER = new ImageView(new java.io.File("paper.jpg").toURI().toString());
    private final ImageView SCISSOR = new ImageView(new java.io.File("scissors.jpg").toURI().toString());
    private StackPane choicePane = new StackPane();
    private Label winner;
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Label pWeapon = new Label("Player Choice");
        StackPane pWpane = new StackPane();
        pWpane.getChildren().add(pWeapon);
        Label cWeapon = new Label("\nCPU Choice");
        StackPane cWpane = new StackPane();
        cWpane.getChildren().add(cWeapon);
        winner = new Label("\n???");
        StackPane winPane = new StackPane();
        winPane.getChildren().add(winner);
        
        Button bRock = new Button();
        bRock.setGraphic(new ImageView(new java.io.File("rockS.jpg").toURI().toString()));
        bRock.setOnAction(e -> bPressed(0));
        Button bPaper = new Button();
        bPaper.setOnAction(e -> bPressed(1));
        bPaper.setGraphic(new ImageView(new java.io.File("paperS.jpg").toURI().toString()));
        Button bScissor = new Button();
        bScissor.setOnAction(e -> bPressed(2));
        bScissor.setGraphic(new ImageView(new java.io.File("scissorsS.jpg").toURI().toString()));
        FlowPane bPane = new FlowPane();
        bPane.setAlignment(Pos.CENTER);
        bPane.getChildren().addAll(bRock, bPaper, bScissor);
        
        ImageView iChoice = new ImageView(new java.io.File("qm.jpg").toURI().toString());
        choicePane.getChildren().addAll(iChoice);
        
        VBox container = new VBox();
        container.getChildren().addAll(pWpane, bPane, cWpane, choicePane, winPane);
        
        Scene scene = new Scene(container, 300, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 0 rock
    // 1 paper
    // 2 scissor
    private void bPressed(int choice) {
        int cChoice = (int) (Math.random() * 3);
        switch (cChoice) {
            case 0:
                choicePane.getChildren().clear();
                choicePane.getChildren().add(ROCK);
                break;
            case 1:
                choicePane.getChildren().clear();
                choicePane.getChildren().add(PAPER);
                break;
            case 2:
                choicePane.getChildren().clear();
                choicePane.getChildren().add(SCISSOR);
                break;            
            default:
                break;
        }
        
        if (choice == 0 && cChoice == 1) {//cpu wins
            winner.setText("CPU Wins!");
        } else if (choice == 1 && cChoice == 2) {         
            winner.setText("\nCPU Wins!");
        } else if (choice == 2 && cChoice == 0) {         
            winner.setText("\nCPU Wins!");
        } else if (choice == 0 && cChoice == 2) {//player wins          
            winner.setText("\nPlayer Wins!");
        } else if (choice == 1 && cChoice == 0) {         
            winner.setText("\nPlayer Wins!");
        } else if (choice == 2 && cChoice == 1) {         
            winner.setText("\nPlayer Wins!");
        } else if (choice == cChoice) {//tie         
            winner.setText("\nTie!");
        }
    }
    
}
