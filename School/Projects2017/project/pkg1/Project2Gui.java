/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 2 using provided project 1 as clean slate
 */
package project.pkg1;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Project2Gui extends Application{
    
    private static MediaCollection collection = new MediaCollection();
    private ListView list;
    private boolean isTitle;
    private static Label lbLabel = new Label();

    public static void main(String[] args){
        try {
            collection.readCollection();
        } catch (Exception e) {
            lbLabel.setText("Continuing with a fresh collection");
        }
        
        Application.launch(args);
        
        //once gui closes
        try {
            collection.storeCollection();
        } catch (Exception e) {
            lbLabel.setText(e.getMessage());
        }
    }
    
    @Override
    public void start(Stage stage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(12, 12, 12, 12));
        pane.setHgap(12);
        pane.setVgap(12);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        pane.getColumnConstraints().addAll(col1,col2);
        
        list = new ListView();
        list.setItems(collection.getCollection());
        
        lbLabel.setMaxWidth(660);
        
        GridPane gpa = new GridPane();
        gpa.setVgap(12);
        gpa.setHgap(12);
        gpa.setPadding(new Insets(12, 12, 12, 12));
        gpa.setStyle("-fx-border-color: black;");
        TextField tfat = new TextField();
        TextField tfaf = new TextField();
        tfat.setMinWidth(185);
        Label lat = new Label("Title:");
        Label laf = new Label("Format:");
        Button ba = new Button("Add");
        ba.setOnAction(e -> {
            try {
                if(tfat.getText().equals("") || tfaf.getText().equals("")){
                    throw new NullPointerException();
                }
                collection.addItem(tfat.getText(),tfaf.getText());
            } catch (NullPointerException ex){
                lbLabel.setText("Fill out required fields");
            } catch (Exception ex) {
                lbLabel.setText(ex.getMessage());
            }
            refresh();
        });
        gpa.add(lat, 0, 0);
        gpa.add(laf, 0, 1);
        gpa.add(tfat, 1, 0);
        gpa.add(tfaf, 1, 1);
        gpa.add(ba, 0, 2);
        
        Button brm = new Button("Remove");
        brm.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    collection.removeItem(((MediaItem)list.getSelectionModel().getSelectedItem()));
                } catch (NullPointerException ex) {
                    lbLabel.setText("Select item from collection to remove");
                } catch (Exception ex) {
                    lbLabel.setText(ex.getMessage());
                }
            }else{
                // ... user chose CANCEL or closed the dialog
            }
            refresh();
        });
        Button brt = new Button("Return");
        brt.setOnAction(e -> {
            try {
                collection.returnItem(((MediaItem)list.getSelectionModel().getSelectedItem()));
            } catch (NullPointerException ex) {
                lbLabel.setText("Select loaned item from collection to return");
            } catch (Exception ex) {
                lbLabel.setText(ex.getMessage());
            }
            refresh();
        });
        
        GridPane gpl = new GridPane();
        gpl.setPadding(new Insets(12, 12, 12, 12));
        gpl.setVgap(12);
        gpl.setHgap(12);
        gpl.setStyle("-fx-border-color: black;");
        TextField tflt = new TextField();
        TextField tflf = new TextField();
        tflf.setMaxWidth(92);
        tflf.setPromptText("mm/dd/yy");
        Label llt = new Label("Loaned To:");
        Label llf = new Label("Loaned On:");
        Button bl = new Button("Loan");
        bl.setOnAction(e -> {
            MediaItem toLoan;
            try {
                toLoan = collection.retrieveItem(((MediaItem)list.getSelectionModel().getSelectedItem()).getTitle());
                if (collection.isLoanable(toLoan)) {
                            collection.loanItem(toLoan,tflt.getText(),DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(tflf.getText()));
                        } else {
                            lbLabel.setText(toLoan.getTitle() + " is already on loan.");
                        }
            } catch (NullPointerException ex){
                lbLabel.setText("Fill out required fields");
            } catch (Exception ex) {
                lbLabel.setText(ex.getMessage());
            }
            
            refresh();
        });
        Button bd = new Button(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        Tooltip bdtp = new Tooltip("Current Date");
        bdtp.setStyle("-fx-font-size: 12;");
        bd.setTooltip(bdtp);
        bd.setOnAction(e -> {
           tflf.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        });
        gpl.add(llt, 0, 0);
        gpl.add(llf, 0, 1);
        gpl.add(tflt, 1, 0, 2, 1);
        gpl.add(tflf, 1, 1);
        gpl.add(bl, 0, 2);
        gpl.add(bd, 2, 1);
        
        VBox vbrb = new VBox();
        vbrb.setSpacing(12);
        Label lrb = new Label("Sort");
        RadioButton rbt = new RadioButton("By title");
        rbt.setOnAction(e -> {
            isTitle = true;
            refresh();
        });
        RadioButton rbd = new RadioButton("By date loaned");
        rbd.setOnAction(eh -> {
            isTitle = false;
            refresh();
        });
        ToggleGroup tgrb = new ToggleGroup();
        rbt.setToggleGroup(tgrb);
        rbd.setToggleGroup(tgrb);
        rbt.setSelected(true);
        isTitle = true;
        vbrb.getChildren().addAll(lrb,rbt,rbd);
        
        pane.add(list, 0, 0, 1, 5);
        pane.add(lbLabel, 0, 5, 2, 1);
        pane.add(gpa, 1, 0);
        pane.add(brm, 1, 1);
        pane.add(brt, 1, 2);
        pane.add(gpl, 1, 3);
        
        pane.add(vbrb, 1, 4);
        
        Scene scene = new Scene(pane, 700, 456);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Media Collection");
        stage.show();
    }
    
    private void refresh(){
        if(isTitle){
            Collections.sort(collection.getCollection(), MediaItem.compareTitle);
        }else{
            Collections.sort(collection.getCollection(), MediaItem.compareDate);
        }
        list.refresh();
    }
    
}
