package project2fx_parker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.collections.FXCollections;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.SelectionMode;




public class Project2FX_Parker extends Application {
    static int num = 3;
    static final int BUTTON_SIZE = 50;
    static int select = 0;
    static Button[][] buttons;
    static int[][] grid;
    static ArrayList allNum = new ArrayList();
    static ArrayList inUse = new ArrayList();

    @Override
    public void start(Stage primaryStage){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        
        Label lbPrompt = new Label("                Size of grid:");
        Label lbBlank = new Label();
        Label lbSlPrint = new Label(" 3 ");
        lbSlPrint.setTextFill(Color.rgb(0, 0, 255));
        lbSlPrint.setUnderline(true);
        Label lbEasyDef = new Label("Easy Mode shows sums");

        Slider slNum = new Slider(3, 8, 3);
        slNum.setMajorTickUnit(1);
        slNum.setShowTickLabels(true);
        slNum.setShowTickMarks(true);
        slNum.setMinorTickCount(0);
        slNum.valueProperty().addListener(ov -> {
            num = (int) slNum.getValue();
            lbSlPrint.setText(" " + String.valueOf((int) slNum.getValue()) + " ");
        });        
        
        CheckBox cbEasy = new CheckBox("Easy Mode");
        
        Button btStart = new Button("Start");
        btStart.setOnAction((ActionEvent e) -> {
            GridPane pane1 = new GridPane();
            pane1.setAlignment(Pos.CENTER);
            pane1.setPadding(new Insets(5,5,5,5));
            
            buttons = new Button[num][num];

            for(int i = 0; i < num; i++){
                for(int j = 0; j < num; j++){
                    buttons[i][j] = new Button();
                    buttons[i][j].setText("0");
                    buttons[i][j].setMinSize(BUTTON_SIZE,BUTTON_SIZE);
                    buttons[i][j].setMaxSize(BUTTON_SIZE,BUTTON_SIZE);
                    pane1.add(buttons[i][j],j,i);
                    buttons[i][j].setOnAction((ActionEvent f) -> {
                        newText((Button)f.getSource());
                    });
                    
                }
            }

            Scene scene2 = new Scene(pane1, BUTTON_SIZE * num + 8, BUTTON_SIZE * num + 8);
            primaryStage.setScene(scene2);
        });
        
        
        pane.add(slNum, 1,1);
        pane.add(btStart, 2,1);        
        pane.add(lbPrompt,1,2);
        pane.add(lbSlPrint, 2,2);
        pane.add(lbBlank, 1,3);
        pane.add(cbEasy, 1,4);
        pane.add(lbEasyDef,1,5);
        
        
        Scene scene = new Scene(pane, 200, 200);
        primaryStage.setTitle("Magic Square");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void newText(Button b){
        
        for(int i = 1; i<=(num*num); i++){
            if(!inUse.contains(i))
                allNum.add(i);
        }
        if(allNum.size() == 0){
            
        }
        displayLists();
        Stage numStage = new Stage();
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        ListView nums = new ListView();
        nums.setItems(FXCollections.observableArrayList(allNum));
        nums.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pane.getChildren().add(nums);
        nums.getSelectionModel().selectedItemProperty().addListener(ov -> {
            int n = (int)nums.getSelectionModel().getSelectedItem();
            b.setText(n+"");
            if(!inUse.contains(n))
                inUse.add(n);
            allNum.clear();
            numStage.close();
            displayLists();
        });
        
        Scene numScene = new Scene(pane, 20,250);
        numStage.setTitle("Select a number");
        numStage.setScene(numScene);
        numStage.show();
    }
    
    static void displayLists(){
        System.out.print("\nallNum:");
        for(int i = 0; i < allNum.size(); i++){
            System.out.print(allNum.get(i)+"  ");
        }
        System.out.print("\ninUse:");
        for(int j = 0; j < inUse.size(); j++){
            System.out.print(inUse.get(j)+"  ");
        }
        System.out.println();
    }
    
    static void fillGrid(){
        grid = new int[num][num];
        for(int x = 0; x < num; x++){
            for(int y = 0; y < num; y++){
                grid[x][y] = Integer.parseInt(buttons[x][y].getText());
            }
        }
    }
    
    static boolean solution() {
        fillGrid();
        
        //base sum
        int sum1 = 0;
        for (int o = 0; o < num; o++) {
            sum1 += grid[0][o];
        }

        //horizontal
        for (int l[] : grid) {
            int sum2 = 0;
            for (int m : l) {
                sum2 += m;
            }
            if (sum1 != sum2) {
                return false;
            }
        }

        //vertical
        for (int p = 0; p < num; p++) {
            int sum2 = 0;
            for (int q = 0; q < num; q++) {
                sum2 += grid[q][p];
            }
            if (sum1 != sum2) {
                return false;
            }
        }

        //diagonal
        int sum2 = 0;
        for (int r = 0; r < num; r++) {
            sum2 += grid[r][r];
        }
        if (sum1 != sum2) {
            return false;
        }

        //other diagonal
        sum2 = 0;
        for (int t = 0; t < num; t++) {
            sum2 += grid[t][(num - t) - 1];
        }
        if (sum1 != sum2) {
            return false;
        }

        return true;

    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
