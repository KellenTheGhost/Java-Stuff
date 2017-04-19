package credits;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Credits extends Application {
    
    private static int credLines = 0;

    @Override
    public void start(Stage primaryStage) {
        Pane paneC = new Pane();
        paneC.setStyle("-fx-background-color: #000000;");
        MediaPlayer player = new MediaPlayer(new Media(new java.io.File("johnny cash - hurt (lyrics).mp3").toURI().toString()));

        //credits text
        Text text = new Text();

        String credits = getCredits();
        System.out.println(credits);
        System.out.println("Credits loaded");

        text.setText(credits);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setScaleX(3);
        text.setScaleY(3);
        text.setFont(Font.font("Yu Gothic UI"));
        text.setFill(Color.rgb(200, 200, 200));
        text.setVisible(false);

        paneC.getChildren().add(text);
        Scene sceneC = new Scene(paneC, 1200, 800);

        //credits animations
        //scroll up animation
        PathTransition patht = new PathTransition();
        patht.setDelay(Duration.millis(1500));
        patht.setCycleCount(1);
        patht.setDuration(Duration.millis(credLines*400));
        patht.setPath(new Line(sceneC.getWidth() / 2, sceneC.getHeight() * (credLines/30), sceneC.getWidth() / 2, -(credLines/30) * (sceneC.getHeight())));

        //fadein animation
        FadeTransition fadet = new FadeTransition(Duration.millis(1000));
        fadet.setCycleCount(1);
        fadet.setFromValue(0.0f);
        fadet.setToValue(1.0f);

        //parallel animations
        ParallelTransition pt = new ParallelTransition(text, fadet, patht);

        //click screen to begin credits animation
        sceneC.setOnMouseClicked(eh -> {
            System.out.println("Screen clicked");
            text.setVisible(true);//unhide credits
            //player.play();//play music
            pt.play();//roll credits
        });

        primaryStage.setTitle("The End");
        primaryStage.setScene(sceneC);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    public static String getCredits() {
        String s = "Error: credits.txt not found";
        try (Scanner reader = new Scanner(new java.io.File("credits.txt"))) {
            s = "";
            while (reader.hasNext()) {
                s += "\n"+reader.nextLine();
                credLines++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Credits.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

}
