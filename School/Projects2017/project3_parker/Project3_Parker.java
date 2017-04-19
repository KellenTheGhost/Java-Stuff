/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 3
 */
package project3_parker;

import java.util.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Project3_Parker extends Application{

    private static Deck deck;
    private static boolean isGui;
    
    public static void main(String[] args) {
        isGui = true;
        Application.launch(args);
        if(!isGui){
            driver();
        }
    }
    
    private static void driver(){
        deck = new Deck();
        boolean isPlayer = true;
        while(true){
            if(deck.isDrawPileEmpty()){// when drawpile (deck) is empty, the discard pile is flipped over and replaces the drawpile
                deck.reshuffle();
            }
            if(isPlayer){
                System.out.println(deck.getPlayerHandString());
            }
            boolean pile = getPile(isPlayer);
            deck.draw(isPlayer,pile);
            if(isPlayer){
                System.out.println("You draw the "+deck.getPlayerHand().peekLast());
                System.out.println(deck.getPlayerHandString());
            }
            int index = getIndex(isPlayer);
            deck.discard(isPlayer, index);
            if(deck.isWin()){
                break;
            }
            isPlayer = !isPlayer;
            System.out.println("");
        }
        if(isPlayer){
            System.out.println(deck.getPlayerHandString());
            System.out.println("You win!");
        }else{
            System.out.println(deck.getCpuHandString());
            System.out.println("I--the computer--win!");
        }
    }
    
    private static boolean getPile(boolean isPlayer){
        if(isPlayer){
            if(deck.discardNotEmpty()){
                Scanner kb = new Scanner(System.in);
                System.out.println("The top card in the discard pile is the " + deck.getTopDiscard().toString());
                System.out.print("Do you want to draw a card [1] or pick up the " + deck.getTopDiscard().toString() + " [2]?  ");
                while(true){
                    try{
                        int num = kb.nextInt();
                        kb.nextLine();
                        if(num==1){
                            return true;
                        }else if(num==2){
                            return false;
                        }else{
                            System.out.println("Enter either 1 or 2");
                        }
                    }catch(java.util.InputMismatchException e){
                        System.out.println("Enter either 1 or 2");
                        kb.nextLine();
                    }
                }
            }else{
                System.out.println("The discard pile is currently empty -- you must draw a card");
                return true;
            }
        }else{
            //cpu strategy for draw
            int num = 1;
            if(num==2 && deck.discardNotEmpty()){
                System.out.println("I will pick up the "+ deck.getTopDiscard().toString());
                return false;
            }else{
                System.out.println("I will draw a card.");
                return true;
            }
        }
    }

    private static int getIndex(boolean isPlayer){
        if(isPlayer){
            Scanner kb = new Scanner(System.in);
            int index = -1;
            while(true){
                try{
                    System.out.print("Which card would you like to discard?  ");
                    index = kb.nextInt();
                    kb.nextLine();
                }catch(java.util.InputMismatchException e){
                    System.out.println("Enter a number between 1 and 5");
                    kb.nextLine();
                    continue;
                }
                if(index<1 || index >5){
                    System.out.println("Enter a number between 1 and 5");
                }else{
                    System.out.println("You discard the " + deck.getPlayerHand().get(index-1).toString());
                    return index - 1;
                }
            }
        }else{
            //cpu strategy for discard       
            int index = 0;
            System.out.println("I will discard the " + deck.getCpuHand().get(index).toString());
            return index;
        }
    }
    
    private Pane pane;
    private boolean isDraw;
    private boolean isDiscard;
    private boolean isPlayer;
    private boolean isSmart;
    private ArrayList<Pane> cpuHand;
    private Stage stage;
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        pane = new Pane();
        menu();
        Scene scene = new Scene(pane, 400,300);
        this.stage.setScene(scene);
        this.stage.setTitle("Weird Card Game");
        this.stage.show();
    }
    
    public void reset(){
        
        pane.getChildren().clear();
        //drawpile
        Label ldraw = new Label("Draw");
        pane.getChildren().add(ldraw);
        ldraw.setLayoutX(100);
        ldraw.setLayoutY(80);
        pane.getChildren().add(deck.getTopDrawpile().cardbackFX());
        pane.getChildren().get(1).setLayoutX(100);
        pane.getChildren().get(1).setLayoutY(100);
        pane.getChildren().get(1).setOnMouseClicked(eh -> {
            if(isDraw){
                draw(true,true);
            }
            
        });
        
        //discard pile
        Label ldisc = new Label("Discard");
        pane.getChildren().add(ldisc);
        ldisc.setLayoutX(300);
        ldisc.setLayoutY(80);
        if(deck.discardNotEmpty()){
            Pane c = deck.getTopDiscard().cardFX();
            pane.getChildren().add(c);
            c.setLayoutX(300);
            c.setLayoutY(100);
            c.setOnMouseClicked(eh -> {
                if(isDraw){
                    draw(true,false);
                }
            });
        }
        
        //player hand
        for(int i = 0; i < deck.getPlayerHand().size(); i++){
            Pane c = deck.getPlayerHand().get(i).cardFX();
            pane.getChildren().add(c);
            final int x = 20+(i*70);
            final int index = i;
            c.setLayoutX(x);
            c.setOnMouseClicked(eh -> {
                if(isDiscard){
                    discard(true,index,(Pane)eh.getSource());
                }
            });
        }
        
        //cpu hand
        cpuHand = new ArrayList<>();
        for(int i = 0; i < deck.getCpuHand().size(); i++){
            Pane c = deck.getCpuHand().get(i).cardbackFX();
            cpuHand.add(c);
            pane.getChildren().add(c);
            c.setLayoutX(20+i*70);//300
            c.setLayoutY(200);
        }  
        
        if(deck.isDrawPileEmpty()){// when drawpile (deck) is empty, the discard pile is flipped over and replaces the drawpile
            deck.reshuffle();
        }
        
        if(deck.isWin()){
            win();
        }
        
        
    }
    
    public void draw(boolean isPlayer, boolean isDrawPile){
        PathTransition pt;
        Pane c;
        if(isPlayer && isDrawPile){
            c = deck.getTopDrawpile().cardbackFX();
            pt = new PathTransition(Duration.millis(1000),new Line(100,120,320,40),c);
        }else if(!isPlayer && isDrawPile){
            c = deck.getTopDrawpile().cardbackFX();
            pt = new PathTransition(Duration.millis(1000),new Line(100,120,320,240),c);
        }else if(isPlayer && !isDrawPile){
            c = deck.getTopDiscard().cardbackFX();
            pt = new PathTransition(Duration.millis(1000),new Line(300,120,320,40),c);
        }else{
            c = deck.getTopDiscard().cardbackFX();
            pt = new PathTransition(Duration.millis(1000),new Line(300,120,320,240),c);
        }
        pane.getChildren().add(c);
        pt.setOnFinished(eh -> {
            deck.draw(isPlayer, isDrawPile);
            reset();
            isDraw = false;
            isDiscard = true;
            if(!this.isPlayer){
                if(isSmart){
                    smart();
                }else{
                    cpu();
                }
            }
        });
        pt.play();
    }
    
    public void discard(boolean isPlayer, int index, Pane pane){
        PathTransition pt1;
        if(isPlayer){
            pt1 = new PathTransition(Duration.millis(1000),new Line(pane.getLayoutX()-index*70,pane.getLayoutY(),320-pane.getLayoutX(),120),pane);
        }else{
            pt1 = new PathTransition(Duration.millis(1000),new Line(pane.getLayoutX()-index*70,pane.getLayoutY()-120,320-pane.getLayoutX(),-60),pane);
        }
        pt1.setOnFinished(eh -> {
            deck.discard(isPlayer,index);
            reset();
            isDiscard = false;
            isDraw = true;
            if(this.isPlayer){
                this.isPlayer=false;
                if(isSmart){
                    smart();
                }else{
                    cpu();
                }
            }else{
                this.isPlayer = true;
            }
        });
        pt1.play();
    }
    
    public void cpu(){
        if(!isPlayer){
            //draw
            if(isDraw){
                draw(false,true);
            }
            if(isDiscard){
                int index = 1;
                //discard
                discard(false,index,cpuHand.get(index));
            }
        }
    }
    
    public void smart(){
        if(!isPlayer){
            //draw
            if(isDraw){
                Card discard = deck.getTopDiscard();
                LinkedList<Card> hand = deck.getCpuHand();
                for(Card c : hand){
                    if(c.getValue().equals(discard.getValue())){
                        draw(false,false);
                        return;
                    }
                }
                draw(false,true);
            }
            if(isDiscard){
                int index = 0;
                //discard
                
                
                LinkedList<Card> hand = deck.getCpuHand();
                Deque<Card> discard = deck.getDiscardpile();
                for(Card c : discard){
                    for(int i = 0; i<hand.size(); i++){
                        if(c.getValue().equals(hand.get(i).getValue())){//card is in discard pile, thus you cant get 4 of it
                            index = i;
                            break;
                        }
                    }
                }
                
                for(int i = 0, j = 1, k = 2, l = 3; i<hand.size(); i++,j++,k++,l++){
                    if(j==hand.size()){
                        j=0;
                    }else if(k==hand.size()){
                        k=0;
                    }else if(l==hand.size()){
                        l=0;
                    }
                    if(hand.get(i).getValue().equals(hand.get(j).getValue())){//two match
                        if(index==i || index==j){
                            index=k;
                        }
                    }
                    if(hand.get(i).getValue().equals(hand.get(j).getValue()) && hand.get(i).getValue().equals(hand.get(k).getValue())){//three match
                        discard(false,l,cpuHand.get(l));
                        return;
                    }
                }

                discard(false,index,cpuHand.get(index));
            }
        }
    }
    
    public void win(){
        pane.setDisable(true);
        Label label = new Label("");
        pane.getChildren().add(label);
        if(isPlayer){
            label.setText("Player wins!");
        }else{
            label.setText("CPU wins!");
        }
        label.setLayoutX(160);
        label.setLayoutY(100);
        label.setStyle("-fx-font-size: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 5;");
        
    }
    
    public void menu(){
        Button bgui = new Button("GUI");
        Button bCmd = new Button("CMD");
        RadioButton rDumb = new RadioButton("Dumb CPU");
        RadioButton rSmart = new RadioButton("Smart CPU");
        ToggleGroup group = new ToggleGroup();
        rDumb.setToggleGroup(group);
        rSmart.setToggleGroup(group);
        rDumb.setSelected(true);
        isSmart = false;
        
        Label lCmd = new Label("Play the game in cmd-style.");
        Label lGui = new Label("Play the game in a GUI!");
        Label lDif = new Label("Choose the intelligence of the GUI's CPU player.");
        
        pane.getChildren().addAll(bgui,bCmd,rDumb,rSmart,lCmd,lGui,lDif);
        bCmd.setLayoutX(10);
        bCmd.setLayoutY(80);
        bCmd.setOnAction(e -> {
            isGui = false;
            stage.close();
        });
        
        bgui.setLayoutX(200);
        bgui.setLayoutY(80);
        bgui.setOnAction(e -> {
            deck = new Deck();
            isGui = true;
            isDraw = true;
            isPlayer = true;
            reset();
        });
        
        rDumb.setLayoutX(200);
        rDumb.setLayoutY(200);
        rDumb.setOnAction(e -> {
            isSmart = false;
        });
        
        rSmart.setLayoutX(200);
        rSmart.setLayoutY(220);
        rSmart.setOnAction(e -> {
            isSmart = true;
        });
        
        lCmd.setLayoutX(10);
        lCmd.setLayoutY(40);
        lCmd.setWrapText(true);
        lCmd.setMaxWidth(100);
        
        lGui.setLayoutX(200);
        lGui.setLayoutY(40);
        lGui.setWrapText(true);
        lGui.setMaxWidth(100);
        
        lDif.setLayoutX(200);
        lDif.setLayoutY(140);
        lDif.setWrapText(true);
        lDif.setMaxWidth(100);
        
    }
    
}
