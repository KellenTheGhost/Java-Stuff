package iamtoast;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IAmToast extends Application {

    public static int width = 1200;
    public static int height = 800;
    private static int credLines = 0;
    private static Stage Stage;
    private static final MediaPlayer PLAYER = new MediaPlayer(new Media(new java.io.File("johnny cash - hurt (lyrics).mp3").toURI().toString()));

    @Override
    public void start(Stage primaryStage) {
        Stage = primaryStage;
        Stage.setTitle("I am Toast");
        Stage.setScene(titleScene());
        Stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene titleScene() {
        Pane paneT = new Pane();

        ImageView breadLoaf = new ImageView(new java.io.File("breadLoaf1.jpeg").toURI().toString());
        Text title = new Text("I AM TOAST");
        title.setVisible(false);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setScaleX(6);
        title.setScaleY(6);
        title.setFont(Font.font("Comic Sans MS"));
        title.setFill(Color.rgb(160, 82, 45));
        title.setX(width / 2);
        title.setY(height * 3 / 4);

        paneT.getChildren().addAll(breadLoaf, title);

        PathTransition pt1 = new PathTransition(Duration.millis(1000), new Line(width * 2, height / 2, width / 2, height / 2), breadLoaf);
        pt1.setCycleCount(1);

        FadeTransition ft1 = new FadeTransition(Duration.millis(100), title);
        ft1.setFromValue(0.0f);
        ft1.setToValue(1.0f);
        ft1.setCycleCount(5);

        FadeTransition ft21 = new FadeTransition(Duration.millis(2000), title);
        ft21.setDelay(Duration.millis(1000));
        ft21.setFromValue(1.0f);
        ft21.setToValue(0.0f);
        FadeTransition ft22 = new FadeTransition(Duration.millis(2000), breadLoaf);
        ft22.setDelay(Duration.millis(1000));
        ft22.setFromValue(1.0f);
        ft22.setToValue(0.0f);

        pt1.setOnFinished(eh -> {
            title.setVisible(true);
            ft1.play();
        });

        ft1.setOnFinished(eh -> {
            ft21.play();
            ft22.play();
        });

        ft22.setOnFinished(eh -> {
            Stage.setScene(mainScene());
        });

        pt1.play();

        return new Scene(paneT, width, height);
    }

    public static Scene mainScene() {
        Pane pane = new Pane();
        ImageView breadLoaf = new ImageView(new java.io.File("breadLoaf1.jpeg").toURI().toString());
        breadLoaf.setX(width / 2);
        breadLoaf.setY(0);
        breadLoaf.setCursor(Cursor.HAND);
        ImageView toaster = new ImageView(new java.io.File("Toaster.jpeg").toURI().toString());
        toaster.setScaleX(0.4);
        toaster.setScaleY(0.4);
        toaster.setX(-(width) / 6);
        toaster.setY(height / 8);
        ImageView slice = new ImageView(new java.io.File("bread1.jpeg").toURI().toString());
        slice.setX(width / 4);
        slice.setY(height / 3);
        slice.setOpacity(0);
        slice.setVisible(false);
        Rectangle rect = new Rectangle(0, -height, width, height);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.BLACK);

        pane.getChildren().addAll(breadLoaf, slice, toaster, rect);

        PathTransition pt1 = new PathTransition(Duration.millis(5000), new Line(width / 2, -(height) / 2, width / 2, height / 2), rect);

        breadLoaf.setOnMouseClicked(eh -> {
            slice.setOpacity(1);
            slice.setVisible(true);
            breadLoaf.setCursor(Cursor.DEFAULT);
            pane.getChildren().remove(breadLoaf);
            pane.getChildren().add(0, breadLoaf);
            toaster.setCursor(Cursor.HAND);
            pane.getChildren().remove(toaster);
            pane.getChildren().add(2, toaster);
        });

        pt1.setOnFinished(eh -> {
            if (slice.getOpacity() == 1) {
                Stage.setScene(creditScene());
            }
        });

        toaster.setOnMouseClicked(e -> {
            pt1.play();
        });

        return new Scene(pane, width, height);
    }

    public static Scene creditScene() {
        Pane paneC = new Pane();
        paneC.setStyle("-fx-background-color: #000000;");

        PLAYER.setCycleCount(1);

        Text text = new Text();

        String credits = getCredits();

        text.setText(credits);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setScaleX(3);
        text.setScaleY(3);
        text.setFont(Font.font("Yu Gothic UI"));
        text.setFill(Color.rgb(200, 200, 200));

        paneC.getChildren().add(text);

        PathTransition patht = new PathTransition();
        patht.setDelay(Duration.millis(1500));
        patht.setCycleCount(1);
        patht.setDuration(Duration.millis(credLines * 400));
        patht.setPath(new Line(width / 2, height * (credLines / 30), width / 2, -(credLines / 30) * (height)));

        FadeTransition fadet = new FadeTransition(Duration.millis(1000));
        fadet.setCycleCount(1);
        fadet.setFromValue(0.0f);
        fadet.setToValue(1.0f);

        ParallelTransition pt = new ParallelTransition(text, fadet, patht);

        pt.setOnFinished(eh -> {
            PLAYER.setVolume(PLAYER.getVolume() / 3);
            Stage.setScene(stingerScene());
        });

        PLAYER.play();
        pt.play();

        return new Scene(paneC, width, height);
    }

    public static String getCredits() {
        String s = "Error: credits.txt not found";
        try (Scanner reader = new Scanner(new java.io.File("credits.txt"))) {
            s = "";
            while (reader.hasNext()) {
                s += "\n" + reader.nextLine();
                credLines++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IAmToast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public static Scene stingerScene() {
        MediaPlayer ding = new MediaPlayer(new Media(new java.io.File("40725^DING1.mp3").toURI().toString()));
        Pane pane = new Pane();

        ImageView toaster = new ImageView(new java.io.File("Toaster.jpeg").toURI().toString());
        toaster.setScaleX(0.4);
        toaster.setScaleY(0.4);
        toaster.setX(-(width) / 6);
        toaster.setY(height / 8);
        pane.getChildren().add(toaster);

        ImageView toast = new ImageView(new java.io.File("toast.png").toURI().toString());
        toast.setY(height * 10);
        pane.getChildren().add(0, toast);

        PathTransition pt = new PathTransition(Duration.millis(300), new Line(width * 5 / 12, height / 2, width * 5 / 12, height / 3), toast);

        Rectangle rect = new Rectangle(0, 0, width, height);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.BLACK);
        pane.getChildren().add(rect);
        PathTransition pt1 = new PathTransition(Duration.millis(3000), new Line(width / 2, height / 2, width / 2, -(height) / 2), rect);
        pt1.play();

        PathTransition pt21 = new PathTransition();
        pt21.setDuration(Duration.millis(1));
        pt21.setPath(new Line(width / 2, 0, width / 2, height / 2));
        FadeTransition ft22 = new FadeTransition();
        ft22.setDuration(Duration.millis(1000));
        ft22.setFromValue(0);
        ft22.setToValue(1);
        ParallelTransition ptt = new ParallelTransition(rect, ft22, pt21);

        ft22.setOnFinished(eh -> {
            System.exit(0);
        });

        Runnable r = () -> {
            ding.play();
            pt.play();
            ptt.play();
        };

        PLAYER.setOnEndOfMedia(r);

        return new Scene(pane, width, height);
    }

}
