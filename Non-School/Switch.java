package pkgswitch;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Switch extends Application{
    private static int width=1200;
    private static int height=800;
    private static boolean gravityDown;
    private static fred f = new fred();
    private static Line uBound = new Line(0,0,width,0);
    private static Line lBound = new Line(0,height,width,height);
    private Rectangle freddy = new Rectangle(width/40,width/40);
    private int yC;
    Timeline timeline = new Timeline();
    Label lbScore = new Label("0");
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        uBound.setStrokeWidth(height/8);
        lBound.setStrokeWidth(height/8);
        pane.getChildren().addAll(uBound,lBound,freddy,lbScore);
        yC = height/2;
        freddy.setLayoutX(width/2);
        lbScore.setLayoutX(width/16);
        lbScore.setLayoutY(height/10);
        lbScore.setScaleX(width/120);
        lbScore.setScaleY(width/120);
    
        timeline.setCycleCount(2147483647);
        KeyFrame keyframe = new KeyFrame(Duration.millis(50),e -> update());
        timeline.getKeyFrames().add(keyframe);
        timeline.play();
        
        Scene scene = new Scene(pane,width,height);
        scene.setOnMouseClicked(eh -> toggle());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Switch");
        primaryStage.show();
    }
  
    private void update(){
        f.setVelocity(f.getVelocity()+f.getAcceleration());
        System.out.println(f.getVelocity());
        yC += f.getVelocity()/2;
        freddy.setLayoutY(yC);
        f.increaseScore();
        lbScore.setText(""+f.getScore());
        if(freddy.getLayoutY()<height/16||freddy.getLayoutY()>height*7/8){//======================================
            timeline.stop();
            lbScore.setVisible(false);
        }
    }
    
    private void toggle(){
        gravityDown = !gravityDown;
        f.SWITCH();
    }
}

class fred{
    private double velocity;
    private double acceleration;
    private int score;

    public fred(){
        velocity = 0;
        acceleration = -1;
        score = 0;
    }
    
    
    
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void SWITCH(){
        acceleration *= -1;
    }
    
    public void increaseScore(){
        score += 1;
    }

    
}

class obstacle{
    private int uY;
    private int lY;
    private int uX;
    private int lX;
    private int velocity;
    
    public obstacle(){
        
    }
}