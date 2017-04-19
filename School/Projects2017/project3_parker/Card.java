/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Lab 
 */
package project3_parker;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Card implements Comparable<Card>{
    String value;
    String suit;
    
    private final ImageView HEARTS = new ImageView(new java.io.File("hearts.jpg").toURI().toString());
    private final ImageView DIAMONDS = new ImageView(new java.io.File("diamond.png").toURI().toString());
    private final ImageView SPADES = new ImageView(new java.io.File("spade.png").toURI().toString());
    private final ImageView CLUBS = new ImageView(new java.io.File("club.png").toURI().toString());        
    
    public Card(String value, String suit){
        this.value = value;
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
    
    public Pane cardFX(){
        Label val = new Label(value);
        ImageView st = new ImageView();
        switch(suit){
            case "Diamonds":
                st = DIAMONDS;
                break;
            case "Hearts":
                st = HEARTS;
                break;
            case "Spades":
                st = SPADES;
                break;
            case "Clubs":
                st = CLUBS;
                break;
        }
        st.setFitHeight(20);
        st.setFitWidth(20);
        Pane pane = new Pane();
        pane.getChildren().add(val);
        pane.getChildren().add(st);
        pane.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;");
        val.setLayoutX(2);
        val.setLayoutY(2);
        st.setLayoutX(20);
        st.setLayoutY(40);
        pane.setMinSize(50, 70);        
        pane.setMaxSize(50, 70);
        
        return pane;
    }    
    
    public Pane cardbackFX(){
        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black; -fx-background-color: #008040;");
        pane.setMinSize(50, 70);
        pane.setMaxSize(50, 70);
        
        return pane;
    }

    @Override
    public int compareTo(Card o) {
        int o1;
        int o2;
        if(value.equals("Jack")){
            o1 = 11;
        }else if(value.equals("Queen")){
            o1 = 12;
        }else if(value.equals("King")){
            o1 = 13;
        }else if(value.equals("Ace")){
            o1 = 14;
        }else {
            o1 = Integer.parseInt(value);
        }
        
        if(o.getValue().equals("Jack")){
            o2 = 11;
        }else if(o.getValue().equals("Queen")){
            o2 = 12;
        }else if(o.getValue().equals("King")){
            o2 = 13;
        }else if(o.getValue().equals("Ace")){
            o2 = 14;
        }else {
            o2 = Integer.parseInt(o.getValue());
        }
        
        return o1 - o2;
    }
    
}