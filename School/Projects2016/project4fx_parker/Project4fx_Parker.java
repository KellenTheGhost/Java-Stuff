package project4fx_parker;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;

/**
 * project 4 in javafx gui form
 * 
 * differences include:
 *      buttons to pitch
 *      change batter
 *      change pitcher
 *      end of pitch replaces batter(old version had beginning of pitch replace batter and pitcher)
 *      display of batter's and pitcher's names and averages
 * 
 * @author kjpar
 */
public class Project4fx_Parker extends Application {

    private static final File INPUT = new File("name_list.txt");
    private static int balls;
    private static int strikes;
    private static TextArea ta;
    private static CheckBox chDebug;
    private static Pitcher pitcher;
    private static Batter batter;
    private static Label lbStats;

    /**
     * creates gui for program
     * @param primaryStage - inherited from Application
     */
    @Override
    public void start(Stage primaryStage) {
        batter = createBatter();
        pitcher = createPitcher();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Button btOK = new Button("Pitch");
        btOK.setOnAction(e -> {
            driver();
            replaceBatter();
            updateStats();
        });
        btOK.setStyle("-fx-font: 22 arial; -fx-base: #f0f0ff;");

        Button btPitch = new Button("New Pitcher");
        btPitch.setOnAction(e -> {
            replacePitcher();
            updateStats();
        });

        Button btBat = new Button("New Batter");
        btBat.setOnAction(e -> {
            replaceBatter();
            updateStats();
        });

        ta = new TextArea();
        ta.setText("Press Pitch to begin game!");
        ta.setPrefSize(300, 270);
        ta.setEditable(false);

        lbStats = new Label("");
        updateStats();

        pane.add(ta, 1, 1, 4, 1);
        pane.add(lbStats, 1, 2, 1, 2);
        pane.add(btPitch, 3, 2, 1, 1);
        pane.add(btBat, 4, 2, 1, 1);
        pane.add(new Label("\t\t\t\t"), 2, 2, 1, 1);
        pane.add(btOK, 4, 4, 1, 2);
        pane.autosize();

        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Project 4");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * launches gui program
     * @param args - n/a
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * takes random name from file
     * only returns one name
     * @return - random name
     */
    public static String createName() {
        String name = null;
        try (Scanner reader = new Scanner(INPUT)) {
            int rand = (int) (Math.random() * 1245);
            for (int i = 0; i < rand && reader.hasNextLine(); i++) {
                reader.nextLine();
            }
            name = reader.nextLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project4fx_Parker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    /**
     * creates batter object and/or overwrites object pointer on next driver program iteration
     * @return - batter object with random name
     */
    public static Batter createBatter() {
        String name = createName() + " " + createName();
        double average = Math.random();
        Batter batter1 = new Batter(name, average);
        return batter1;
    }

    /**
     * creates pitcher object and/or overwrites object pointer on next driver program iteration
     * @return - pitcher object with random name
     */
    public static Pitcher createPitcher() {
        String name = createName() + " " + createName();
        double average = Math.random();
        Pitcher pitcher1 = new Pitcher(name, average);
        return pitcher1;
    }

    /**
     * creates string with count of balls and strikes of this iteration with proper pluralization
     * @return - string created
     */
    public static String count() {
        String count;
        if (balls == 1 && strikes == 1) {
            count = "\tThe count is " + balls + " ball and " + strikes + " strike";
        } else if (balls == 1 && strikes != 1) {
            count = "\tThe count is " + balls + " ball and " + strikes + " strikes";
        } else if (balls != 1 && strikes == 1) {
            count = "\tThe count is " + balls + " balls and " + strikes + " strike";
        } else {
            count = "\tThe count is " + balls + " balls and " + strikes + " strikes";
        }
        return count;
    }

    /**
     * overwrites object pointer for batter to a new one
     */
    public static void replaceBatter() {
        batter = createBatter();
    }

    /**
     * overwrites object pointer for pitcher to a new one
     */
    public static void replacePitcher() {
        pitcher = createPitcher();
    }

    /**
     * changes the text on gui to current statistics for:
     *    pitcher name and average
     *    batter name and average
     */
    public static void updateStats() {
        String s = "Pitcher: " + pitcher.getName();
        s += "\nAverages: " + String.format("%.2f", pitcher.getStrikeAverage());
        s += "\n\nBatter: " + batter.getName();
        s += "\nAverages: " + String.format("%.2f", batter.getAverage());
        lbStats.setText(s);
    }

    /**
     * driver program
     * outputs to console play-by-play for this pitch
     * ends when pitch is over with a walk, a hit, or a strike.
     */
    public static void driver() {
        balls = 0;
        strikes = 0;

        String s = "";
        s += pitcher.getName() + " is pitching to " + batter.getName();
        while (true) {
            if (pitcher.pitch()) {
                if (batter.hit()) {
                    s += "\n" + count();
                    s += "\n" + batter.getName() + " got a hit!";
                    break;
                } else {
                    s += "\n" + "\t" + batter.getName() + " swung and missed";
                    strikes++;
                }
            } else {
                s += "\n" + "\t" + pitcher.getName() + " threw a ball";
                balls++;
            }
            s += "\n" + count();
            if (balls >= 4) {
                s += "\n" + batter.getName() + " walked.";
                break;
            }
            if (strikes >= 3) {
                s += "\n" + batter.getName() + " struck out.";
                break;
            }
        }
        s += "\n\n\nPress Pitch for next pitch!";
        ta.setText(s);
    }

}
