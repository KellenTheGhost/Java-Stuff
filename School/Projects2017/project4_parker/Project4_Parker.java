/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 4
 */
package project4_parker;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Project4_Parker extends Application {

    private Stage stage;
    private RadioButton rSelection;
    private RadioButton rBubble;
    private RadioButton rInsertion;
    private RadioButton rQuick;
    private ToggleGroup tgSort;
    private RadioButton rSorted;
    private RadioButton rReversed;
    private RadioButton rRandom;
    private ToggleGroup tgInput;
    private TextField tInput;
    private TextField tBlock;
    private Button bGo;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage istage) {
        this.stage = istage;
        setup();
        stage.setTitle("Threaded Sorting");
        stage.show();
    }

    private void setup() {
        VBox sortPane = new VBox();
        sortPane.setSpacing(4);
        sortPane.setPadding(new Insets(4, 4, 4, 4));
        Label lSort = new Label("Sorting Algorithm");
        rSelection = new RadioButton("Selection");
        rBubble = new RadioButton("Bubble");
        rInsertion = new RadioButton("Insertion");
        rQuick = new RadioButton("Quick");
        tgSort = new ToggleGroup();
        rSelection.setToggleGroup(tgSort);
        rBubble.setToggleGroup(tgSort);
        rInsertion.setToggleGroup(tgSort);
        rQuick.setToggleGroup(tgSort);
        rSelection.setSelected(true);
        sortPane.getChildren().addAll(lSort, rSelection, rBubble, rInsertion, rQuick);
        sortPane.setStyle("-fx-border-color: black;");

        VBox inputPane = new VBox();
        inputPane.setSpacing(4);
        inputPane.setPadding(new Insets(4, 4, 4, 4));
        Label lInput = new Label("Input Type");
        rSorted = new RadioButton("Already Sorted");
        rReversed = new RadioButton("Reverse Order");
        rRandom = new RadioButton("Random");
        tgInput = new ToggleGroup();
        rSorted.setToggleGroup(tgInput);
        rReversed.setToggleGroup(tgInput);
        rRandom.setToggleGroup(tgInput);
        rSorted.setSelected(true);
        inputPane.getChildren().addAll(lInput, rSorted, rReversed, rRandom);
        inputPane.setStyle("-fx-border-color: black;");

        GridPane sizePane = new GridPane();
        sizePane.setHgap(4);
        sizePane.setVgap(4);
        sizePane.setPadding(new Insets(4, 4, 4, 4));
        Label lInputSize = new Label("Input Size");
        Label lBlockSize = new Label("Block Size");
        tInput = new TextField();
        tBlock = new TextField();
        sizePane.add(lInputSize, 0, 0);
        sizePane.add(lBlockSize, 0, 1);
        sizePane.add(tInput, 1, 0);
        sizePane.add(tBlock, 1, 1);
        sizePane.setStyle("-fx-border-color: black;");

        bGo = new Button("Go");
        bGo.setMaxWidth(Double.MAX_VALUE);
        bGo.setOnAction(e -> go());

        VBox mainPane = new VBox();
        mainPane.setSpacing(4);
        mainPane.setPadding(new Insets(4, 4, 4, 4));
        mainPane.getChildren().addAll(sortPane, inputPane, sizePane, bGo);
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
    }

    private void go() {
        int input, block;
        if (!tInput.getText().equals("")) {
            try {
                input = Integer.parseInt(tInput.getText());
            } catch (NumberFormatException e) {
                alert("Input field must contain numbers only");
                return;
            }
        } else {
            alert("Input field must be filled");
            return;
        }
        if (!tBlock.getText().equals("")) {
            try {
                block = Integer.parseInt(tBlock.getText());
                
                //sanity checks
                if(block>input){
                    block = input;
                    tBlock.setText(Integer.toString(block));
                }
                if(block<=0){
                    alert("Block must be a positive non-zero intiger.");
                    tBlock.setText("");
                    return;
                }
                if(input<0){
                    alert("Why would you want a negative-sized array?");
                    tInput.setText("");
                    return;
                }
                if(input/block >= 1000){
                    alert("Size/Block ratio too large;  Keep the ratio under 1000. (e.i. "+(input/999)+" or "+(block*999)+")");
                    return;
                }
                
                //end sanity checks
                
            } catch (NumberFormatException e) {
                alert("Block field must contain numbers only");
                return;
            }
        } else {
            alert("Block field must be filled");
            return;
        }

        String sort = ((RadioButton) tgSort.selectedToggleProperty().get()).getText();
        String type = ((RadioButton) tgInput.selectedToggleProperty().get()).getText();
        
        array(input, block, sort, type);
    }
    
    public void alert(String message){
        Stage nstage = new Stage();
        VBox pane = new VBox();
        Label label = new Label(message);
        Button button = new Button("OK");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(eh -> nstage.close());
        pane.getChildren().addAll(label,button);
        Scene scene = new Scene(pane);

        nstage.setScene(scene);
        nstage.setTitle("Error");
        nstage.show();
    }
    
    private static ArrayDeque<int[]> arrBlocks = new ArrayDeque<>();
    
    public void array(int input, int block, String sort, String type){
        int[] arr = new int[input];
        
        switch (type) {
            case "Already Sorted":
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                break;
            case "Reverse Order":
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = input - 1 - i;
                }
                break;
            case "Random":
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = (int) (Math.random() * input);
                }
                break;
        }
        long startTime = System.currentTimeMillis();
        ArrayList<Thread> threads = new ArrayList<>();
        while(arr.length>=block){
            int[] arr1 = split(arr, block);
            arr = remainder(arr,arr1);
            Thread thread = new Thread(new Sort(sort,arr1));
            threads.add(thread);
            thread.start();
        }
        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println(":(");
            }
        }
        
        //merge
        ArrayList<Thread> mThreads = new ArrayList<>();
        do{
            mThreads.clear();
            while(true){
                if(arrBlocks.size()==1){
                    break;
                }
                Thread thread = new Thread(new Sort("Merge",null)); // =================================================
                thread.start();
                mThreads.add(thread);
            }
            for(Thread t : mThreads){
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    System.out.println(":(");
                }
            }
        }while(arrBlocks.size()>1);
        
        long endTime = System.currentTimeMillis();
        alert("done -- "+(endTime - startTime)+" ms");
        System.out.println(Arrays.toString(poll()));
    }
    
    public static int[] split(int[] arr, int size){
        if(size>arr.length){
            return arr;
        }
        
        int[] arr1 = new int[size];
        for(int i = 0; i<size;i++){
            arr1[i]=arr[i];
        }
        return arr1;
    }
    
    public static int[] remainder(int[] arr, int[] arr1){
        if(arr.length==0){
            return arr;
        }
        int size = arr1.length;
        int[] temp = new int[arr.length-size];
        for(int i=0; i<temp.length;i++){
            temp[i]=arr[size+i];
        }
        return temp;
    }
    
    public static synchronized void queue(int[] arr){
        arrBlocks.offer(arr);
    }
    
    public static synchronized int[] poll(){
        return arrBlocks.poll();
    }
    
}
