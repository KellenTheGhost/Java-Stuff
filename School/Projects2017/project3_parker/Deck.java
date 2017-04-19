/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Lab 
 */
package project3_parker;

import java.util.*;

public class Deck {
    private final Card[] FULLDECK = {
        new Card("2","Spades"),
        new Card("3","Spades"),
        new Card("4","Spades"),
        new Card("5","Spades"),
        new Card("6","Spades"),
        new Card("7","Spades"),
        new Card("8","Spades"),
        new Card("9","Spades"),
        new Card("10","Spades"),
        new Card("Jack","Spades"),
        new Card("Queen","Spades"),
        new Card("King","Spades"),
        new Card("Ace","Spades"),
        
        new Card("2","Clubs"),
        new Card("3","Clubs"),
        new Card("4","Clubs"),
        new Card("5","Clubs"),
        new Card("6","Clubs"),
        new Card("7","Clubs"),
        new Card("8","Clubs"),
        new Card("9","Clubs"),
        new Card("10","Clubs"),
        new Card("Jack","Clubs"),
        new Card("Queen","Clubs"),
        new Card("King","Clubs"),
        new Card("Ace","Clubs"),
        
        new Card("2","Hearts"),
        new Card("3","Hearts"),
        new Card("4","Hearts"),
        new Card("5","Hearts"),
        new Card("6","Hearts"),
        new Card("7","Hearts"),
        new Card("8","Hearts"),
        new Card("9","Hearts"),
        new Card("10","Hearts"),
        new Card("Jack","Hearts"),
        new Card("Queen","Hearts"),
        new Card("King","Hearts"),
        new Card("Ace","Hearts"),
        
        new Card("2","Diamonds"),
        new Card("3","Diamonds"),
        new Card("4","Diamonds"),
        new Card("5","Diamonds"),
        new Card("6","Diamonds"),
        new Card("7","Diamonds"),
        new Card("8","Diamonds"),
        new Card("9","Diamonds"),
        new Card("10","Diamonds"),
        new Card("Jack","Diamonds"),
        new Card("Queen","Diamonds"),
        new Card("King","Diamonds"),
        new Card("Ace","Diamonds"),
    };
    
    private Card[] deck;
    private Queue<Card> drawpile;
    private Deque<Card> discardpile;
    private LinkedList<Card> playerHand;
    private LinkedList<Card> cpuHand;
    
    public Deck(){
        deck = FULLDECK.clone();
        shuffle();
        drawpile = new LinkedList<>();
        discardpile = new ArrayDeque<>();
        playerHand = new LinkedList<>();
        cpuHand = new LinkedList<>();
        for(Card c : deck){
            drawpile.offer(c);
        }
        for(int i=0; i<4; i++){
            draw(true,true);
            draw(false,true);
        }
        
    }
    
    public void shuffle(){
        for(int i = 0; i < 52; i++){
            int index = (int)(Math.random()*51)+1;
            Card temp = deck[index];
            deck[index] = deck[i];
            deck[i] = temp;
        }
    }
    
    public Card getTopDiscard(){
        return discardpile.peekLast();
    }
    
    public Card getTopDrawpile(){
        return drawpile.peek();
    }
    
    public Deque<Card> getDiscardpile(){
        return discardpile;
    }
    
    public boolean discardNotEmpty(){
        return !discardpile.isEmpty();
    }
    
    public boolean isDrawPileEmpty(){
        return drawpile.isEmpty();
    }
    
    public void reshuffle(){
        while(discardNotEmpty()){
            drawpile.offer(discardpile.pollFirst());
        }
        shuffle();
    }

    public String getPlayerHandString() {
       String s = "Your cards are:";
       for(int i=0; i<playerHand.size(); i++){
           s += "\n\t" + (i+1) + ".  " + playerHand.get(i).toString();
       }
       return s;
    }
    
    public String getCpuHandString() {
       String s = "Your cards are:";
       for(int i=0; i<cpuHand.size(); i++){
           s += "\n\t" + (i+1) + ".  " + cpuHand.get(i).toString();
       }
       return s;
    }
    
    public LinkedList<Card> getCpuHand() {
        Collections.sort(cpuHand);
        return cpuHand;
    }
    
    public LinkedList<Card> getPlayerHand() {
        Collections.sort(playerHand);
        return playerHand;
    }

    public void draw(boolean isPlayer, boolean isDrawpile){
        if(isDrawpile){
            if(isPlayer){
                playerHand.offer(drawpile.poll());
            }else{
                cpuHand.offer(drawpile.poll());
            }
        }else{
            if(isPlayer){
                playerHand.offer(discardpile.pollLast());
            }else{
                cpuHand.offer(discardpile.pollLast());
            }
        }
    }
    
    public void discard(boolean isPlayer, int index){
        if(isPlayer){
            discardpile.offer(playerHand.get(index));
            playerHand.remove(index);
        }else{
            discardpile.offer(cpuHand.get(index));
            cpuHand.remove(index);
        }
    }
    
    public boolean isWin(){
        if(playerHand.get(0).getValue()==playerHand.get(1).getValue()&&playerHand.get(0).getValue()==playerHand.get(2).getValue()&&playerHand.get(0).getValue()==playerHand.get(3).getValue()){
            return true;
        }else if(cpuHand.get(0).getValue()==cpuHand.get(1).getValue()&&cpuHand.get(0).getValue()==cpuHand.get(2).getValue()&&cpuHand.get(0).getValue()==cpuHand.get(3).getValue()){
            return true;
        }else{
            return false;
        }
    }
}
