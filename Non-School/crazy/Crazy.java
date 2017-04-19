package crazy;

import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.util.Random;

public class Crazy extends Application {
    private static String words;
    private static String[] fonts;
    private static final Random RAND = new Random();

    @Override
    public void start(Stage primaryStage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane,1200,800);
        scene.setOnMousePressed(eh -> pane.getChildren().add(craziness()));
        primaryStage.setTitle("Crazy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        words = (String)JOptionPane.showInputDialog(null, "Enter any words: ");
        getFonts();
        launch(args);
    }

    private Text craziness() {
        Text crazy = new Text(words);
        crazy.setFill(Color.rgb((int)(Math.random()*255)+1, (int)(Math.random()*255)+1, (int)(Math.random()*255)+1));
        crazy.setStroke(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
        crazy.setFont(new Font(fonts[RAND.nextInt(fonts.length)],8));
        crazy.setX((int)(Math.random()*1200)+1);
        crazy.setY((int)(Math.random()*800)+1);
        crazy.setRotate(Math.random()*360);
        crazy.setScaleX(Math.random()*20);
        crazy.setScaleY(Math.random()*20);
        crazy.setScaleZ(Math.random()*20);
        return crazy;
    }
    
    public static void getFonts(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts = ge.getAvailableFontFamilyNames();
    }
    
}