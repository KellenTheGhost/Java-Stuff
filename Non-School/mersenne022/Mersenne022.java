package mersenne022;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Kellen Parker
 */
public class Mersenne022 extends Application {

    @Override
    public void start(Stage primaryStage) { // go straight to start2 for recursion purposes, while still using @override
        start2();
    }

    static ArrayList mersenne = new ArrayList();
    static int num = 1;
    private static int count = 0;
    static MediaPlayer player = new MediaPlayer(new Media(new java.io.File("Song.mp3").toURI().toString()));

    public void start2() {  //=================================================  begin primary stage
        Stage primaryStage = new Stage();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(8);

        Label lbMain = new Label("Number of Mersenne Primes?  ");
        Label lbSlPrint = new Label(" 1 ");
        lbSlPrint.setTextFill(Color.rgb(0, 0, 255));
        lbSlPrint.setUnderline(true);

        Slider slNum = new Slider(1, 9, 1);
        slNum.setMajorTickUnit(1);
        slNum.setShowTickLabels(true);
        slNum.setShowTickMarks(true);
        slNum.setMinorTickCount(0);
        slNum.valueProperty().addListener(ov -> {
            num = (int) slNum.getValue();
            lbSlPrint.setText(" " + String.valueOf((int) slNum.getValue()) + " ");
        });

        CheckBox cbSong = new CheckBox("Play Song");
        CheckBox chConsole = new CheckBox("Enable Console Output");

        //=====================================================================  begin ok button lambda class
        Button btOk = new Button("OK");
        btOk.setOnAction((ActionEvent e) -> {
            if (cbSong.isSelected()) {
                player.play();
            }
            mersenne.clear();
            count = 0;
            for (int i = 2; count < num; i++) {
                int i2 = (int) Math.sqrt(i) + 1;
                boolean isPrime = true;
                if (chConsole.isSelected()) {
                    System.out.print(i + "  ");
                }
                for (int j = 2; j <= i2 && j < i; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                    }
                }
                if (isPrime) {
                    if (chConsole.isSelected()) {
                        System.out.println("\n" + i + " is prime");
                    }
                    //mersenne method
                    boolean isMersenne = true;
                    long mers = (long) Math.pow(2, i) - 1;
                    long mers2 = (long) Math.sqrt(mers) + 1;
                    if (chConsole.isSelected()) {
                        System.out.print("potential mersenne is: " + mers);
                    }
                    for (long k = 2; k <= mers2 && k < mers; k++) {
                        if (mers % k == 0) {
                            isMersenne = false;
                        }
                    }
                    if (isMersenne) {
                        //place in new arraylist
                        count += 1;
                        if (chConsole.isSelected()) {
                            System.out.println("  ...  It is!!");
                        }
                        mersenne.add(mers);
                    } else if (chConsole.isSelected()) {
                        System.out.println("  ...  It is not.");
                    }
                    //end mersenne method
                }
            }
            primaryStage.close();
            if (chConsole.isSelected()) {
                System.out.println("\n\n" + mersenne);
            }

            //=================================================================  begin stage two
            Stage stageSecond = new Stage();
            GridPane pane2 = new GridPane();
            pane2.setAlignment(Pos.CENTER);
            pane2.setVgap(8);

            Button btPause = new Button("Pause");
            btPause.setOnAction((ActionEvent f) -> {
                if (btPause.getText().equals("Pause")) {
                    btPause.setText("Play");
                    player.pause();
                } else {
                    btPause.setText("Pause");
                    player.play();
                }
            });

            Button btRes = new Button("Restart");
            btRes.setOnAction((ActionEvent f) -> {
                stageSecond.close();
                start2();
            });

            Label title = new Label("The Mersenne Primes");
            title.setUnderline(true);
            title.setTextFill(Color.rgb(0, 0, 255));
            Label mersString = new Label(mersenne.toString());
            mersString.setWrapText(true);
            mersString.setTextFill(Color.rgb(255, 0, 0));

            pane2.add(title, 1, 1);
            pane2.add(mersString, 1, 2);
            if (cbSong.isSelected()) {
                pane2.add(btPause, 1, 3);
            }
            pane2.add(btRes, 1, 4);

            Scene scene = new Scene(pane2, 320, 150);
            stageSecond.setTitle("Mersenne");
            stageSecond.setScene(scene);
            stageSecond.show();
        });
//=============================================================================  end ok button lambda class

        pane.add(lbMain, 1, 1);
        pane.add(lbSlPrint, 2, 1);
        pane.add(slNum, 1, 3);
        pane.add(btOk, 2, 3);
        pane.add(cbSong, 1, 4);
        pane.add(chConsole, 1, 5);

        Scene scene = new Scene(pane, 320, 150);
        primaryStage.setTitle("Mersenne");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
