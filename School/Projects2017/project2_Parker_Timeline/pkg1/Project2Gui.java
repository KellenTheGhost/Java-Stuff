/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 2 using provided project 1 as clean slate
 */
package project.pkg1;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class Project2Gui extends Application{
    
    private static MediaCollection collection = new MediaCollection();
    private ListView list;
    private TextField tfat;
    private TextField tfaf;
    private TextField tflt;
    private TextField tflf;
    private ChoiceBox<String> cbaf;
    private Label lat;
    private Label laf;
    private Label llt;
    private Label llf;
    private Button ba;
    private Button bl;
    private Button brt;
    private Button brm;
    private Button bd;
    private RadioButton rbt;
    private RadioButton rbd;
    private boolean bAdd;
    private boolean bLoan;
    private boolean bRemove;
    private boolean bReturn;
    private boolean bTT;
    private Label tooltip;
    private DatePicker dp;
    private ArrayList<Node> nodes = new ArrayList<>();
    private final String[] FORMAT = {"DVD","Game","Other"};

    public static void main(String[] args){
        try {
            collection.readCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        Application.launch(args);
        
        //once gui closes
        try {
            collection.storeCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void start(Stage stage) {
        Timeline tl = new Timeline();
        tl.setCycleCount(Integer.MAX_VALUE);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> updateTime()));
        
        Pane pane = new Pane();
        GridPane pane1 = new GridPane();
        pane1.setPadding(new Insets(12, 12, 12, 12));
        pane1.setHgap(12);
        pane1.setVgap(12);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        pane1.getColumnConstraints().addAll(col1,col2);
        
        tooltip = new Label();
        tooltip.setVisible(false);
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(140);
        tooltip.setStyle("-fx-font-color: #ffa0a0");
        
        list = new ListView();
        list.setItems(collection.getCollection());
        
        GridPane gpa = new GridPane();
        gpa.setVgap(12);
        gpa.setHgap(12);
        gpa.setPadding(new Insets(12, 12, 12, 12));
        gpa.setStyle("-fx-border-color: black;");
        
        cbaf = new ChoiceBox<>();
        cbaf.getItems().addAll(FORMAT);
        cbaf.setMaxWidth(120);
        cbaf.setOnAction(eh -> {
            if(cbaf.getValue().equals("Other")){
                cbaf.setValue((String)JOptionPane.showInputDialog(null, "Enter custom Format: "));
            }
            tfaf.setText(cbaf.getValue());
        });
        
        tfat = new TextField();
        tfaf = new TextField();
        lat = new Label("Title:");
        laf = new Label("Format:");
        ba = new Button("Add");
        ba.setOnAction(e -> mAdd());
        ba.setOnMouseEntered(e -> {
            nodes.clear();
            if(tfat.getText().equals("")){
                nodes.add(tfat);
            }
            if(tfaf.getText().equals("")){
                nodes.add(cbaf);
            }
            if(!bAdd){
                tooltip("Requires Title and Format",442,100);
            }
        });
        ba.setOnMouseExited(eh -> {bTT = false;});
        gpa.add(lat, 0, 0);
        gpa.add(laf, 0, 1);
        gpa.add(tfat, 1, 0);
        gpa.add(cbaf, 1, 1);
        gpa.add(ba, 0, 2);
        
        brm = new Button("Remove");
        brm.setOnAction(e -> mRemove());
        brm.setOnMouseEntered(e -> {
            nodes.clear();
            if(list.getSelectionModel().getSelectedItem() == null){
                nodes.add(list);
            }
            if(!bRemove){
                tooltip("Requires item to be selected",450,146);
            }
        });
        brm.setOnMouseExited(eh -> {bTT = false;});
        brt = new Button("Return");
        brt.setOnAction(e -> mReturn());
        brt.setOnMouseEntered(e -> {
            nodes.clear();
            if(list.getSelectionModel().getSelectedItem() == null){
                nodes.add(list);   
            }else if(((MediaItem)list.getSelectionModel().getSelectedItem()).getLoanedTo() == null){
                nodes.add(list);   
            }
            if(!bReturn)tooltip("Requires loaned item to be selected",444,184);
        });
        brt.setOnMouseExited(eh -> {bTT = false;});
        
        GridPane gpl = new GridPane();
        gpl.setPadding(new Insets(12, 12, 12, 12));
        gpl.setVgap(12);
        gpl.setHgap(12);
        gpl.setStyle("-fx-border-color: black;");
        tflt = new TextField();

        llt = new Label("Loaned To:");
        llf = new Label("Loaned On:");
        bl = new Button("Loan");
        bl.setOnAction(e -> mLoan());
        bl.setOnMouseEntered(e -> {
            nodes.clear();
            if(tflt.getText().equals("")){
                nodes.add(tflt);
            }
            if(tflf.getText().equals("")){
                nodes.add(tflf);
            }
            if(list.getSelectionModel().getSelectedItem() == null){
                nodes.add(list);   
            }else if(((MediaItem)list.getSelectionModel().getSelectedItem()).getLoanedTo() != null){
                nodes.add(list);   
            }
            if(!bLoan)tooltip("Requires unloaned item, Loaned To and Loan Date",446,308);
        });
        bl.setOnMouseExited(eh -> {bTT = false;});
        
        dp = new DatePicker();
        dp.setMaxWidth(120);
        tflf = dp.getEditor();
        tflf.setOnKeyPressed(eh -> {
            if(eh.getCode()==KeyCode.BACK_SPACE){
                tflf.setText("");
            }
        });
        tflf.textProperty().addListener((ObservableValue<? extends String> observable,String oldvalue,String newvalue) -> {
            char c = '0';
            try{
                c = newvalue.charAt(newvalue.length()-1);
            }catch(StringIndexOutOfBoundsException e){
                //System.out.println(e.getMessage());
            }
            if(Character.isDigit(c) || c=='/'){
                if(tflf.getLength()==2 || tflf.getLength()==5){
                    tflf.appendText("/");
                }
                if(newvalue.length() > 8){
                    tflf.setText(oldvalue);
                    newvalue = oldvalue;
                }
            }else{
                tflf.setText(oldvalue);
                newvalue = oldvalue;
            }
            
            try{
                dp.setValue(LocalDate.parse(newvalue, DateTimeFormatter.ofPattern("MM/dd/yy")));
                bTT = false;
            }catch(Exception e){
                if(newvalue.length() > 0){
                    nodes.clear();
                    tooltip("Date must be in format mm/dd/yy",480,300);
                }
            }
        });
        
        gpl.add(llt, 0, 0);
        gpl.add(llf, 0, 1);
        gpl.add(tflt, 1, 0, 2, 1);
        gpl.add(dp, 1, 1);
        gpl.add(bl, 0, 2);
        
        VBox vbrb = new VBox();
        vbrb.setSpacing(12);
        Label lrb = new Label("Sort");
        rbt = new RadioButton("By title");
        rbt.setOnAction(e -> {
            refresh();
        });
        rbd = new RadioButton("By date loaned");
        rbd.setOnAction(eh -> {
            refresh();
        });
        ToggleGroup tgrb = new ToggleGroup();
        rbt.setToggleGroup(tgrb);
        rbd.setToggleGroup(tgrb);
        rbt.setSelected(true);
        vbrb.getChildren().addAll(lrb,rbt,rbd);
        
        pane1.add(list, 0, 0, 1, 5);
        pane1.add(gpa, 1, 0);
        pane1.add(brm, 1, 1);
        pane1.add(brt, 1, 2);
        pane1.add(gpl, 1, 3);
        
        pane1.add(vbrb, 1, 4);
        
        pane.getChildren().add(pane1);
        pane.getChildren().add(tooltip);
        
        tl.play();
        Scene scene = new Scene(pane, 650, 470);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Media Collection");
        stage.show();
    }
    
    private void refresh(){
        if(rbt.isSelected()){
            Collections.sort(collection.getCollection(), MediaItem.compareTitle);
        }else{
            Collections.sort(collection.getCollection(), MediaItem.compareDate);
        }
        list.refresh();
    }
    
    private void mAdd(){
        if(bAdd){
            try {
                collection.addItem(tfat.getText(),tfaf.getText());
                tfat.setText("");
                tfaf.setText("");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            refresh();
        }else{
            ba.disarm();
        }
    }
    
    private void mRemove(){
        if(bRemove){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    collection.removeItem(((MediaItem)list.getSelectionModel().getSelectedItem()));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }else{
                // ... user chose CANCEL or closed the dialog
            }
            refresh();
        }
    }
    
    private void mReturn(){
        if(bReturn){
            try {
                collection.returnItem(((MediaItem)list.getSelectionModel().getSelectedItem()));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            refresh();
        }
    }
    
    private void mLoan(){
        if(bLoan){
            MediaItem toLoan;
            try {
                toLoan = collection.retrieveItem(((MediaItem)list.getSelectionModel().getSelectedItem()).getTitle());
                if (collection.isLoanable(toLoan)) {
                            collection.loanItem(toLoan,tflt.getText(),DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(tflf.getText()));
                            tflf.setText("");
                            tflt.setText("");
                        }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            refresh();
        }
    }
    
    private void tooltip(String message, double x, double y){
        bTT = true;
        tooltip.setText(message);
        tooltip.setLayoutX(x);
        tooltip.setLayoutY(y);
        tooltip.setVisible(true);
        for (Node n: nodes){
            n.setStyle("-fx-background-color: #ffa0a0; -fx-control-inner-background: #ffa0a0;");
        }
    }
    
    private void updateTime(){
        //add button
        if(tfat.getText().equals("") || tfaf.getText().equals("")){ //textfields empty
            bAdd = false;
            ba.setStyle("-fx-background-color: #b0b0b0;");
        }else{
            bAdd = true;
            ba.setStyle("");
        }
        
        //loan button
        if(tflt.getText().equals("") || tflf.getText().equals("")){ //textfields empty
            bLoan = false;
            bl.setStyle("-fx-background-color: #b0b0b0;");
        }else{
            if(list.getSelectionModel().getSelectedItem() != null){ //list item selected
                if(((MediaItem)list.getSelectionModel().getSelectedItem()).getLoanedTo()==null){ //item not on loan
                    try { //date parsable
                        DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(tflf.getText());
                        bLoan = true;
                        bl.setStyle("");
                    } catch (ParseException ex) {
                        bLoan = false;
                        bl.setStyle("-fx-background-color: #b0b0b0;");
                    }
                }else{
                    bLoan = false;
                    bl.setStyle("-fx-background-color: #b0b0b0;");
                }
            }else{
                bLoan = false;
                bl.setStyle("-fx-background-color: #b0b0b0;");
            }
        }    
        
        //return button
        if(list.getSelectionModel().getSelectedItem() != null){ //list item selected
            if(((MediaItem)list.getSelectionModel().getSelectedItem()).getLoanedTo()!=null){ //item on loan
                bReturn = true;
                brt.setStyle("");
            }else{
                bReturn = false;
                brt.setStyle("-fx-background-color: #b0b0b0;");
            }
        }else{
            bReturn = false;
            brt.setStyle("-fx-background-color: #b0b0b0;");
        }    
        
        //remove button
        if(list.getSelectionModel().getSelectedItem() != null){ //list item selected
            bRemove = true;
            brm.setStyle("");
        }else{
            bRemove = false;
            brm.setStyle("-fx-background-color: #b0b0b0;");
        }    
        
        //tooltip visibility
        if(!bTT){
            tooltip.setVisible(false);
            list.setStyle("");
            tfat.setStyle("");
            cbaf.setStyle("");
            tflt.setStyle("");
            tflf.setStyle("");
        }
    }

}