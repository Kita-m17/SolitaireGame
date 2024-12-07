//package com.solitaire.model;
/*
 * Class that represemts an individual card
 */
public class Card {
    private String suit; //eg "Hearts", "Spades"
    private String rank; //eg, for Ace, 2, 3, ..., Jack, Queen, King
    private boolean isFaceUp;

    public Card(String suit, String rank){
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;
    }

    public String getSuit(){
        return suit;
    }

    public String getRank(){
        return rank;
    }

    public boolean isFaceUp(){
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp){
        isFaceUp = faceUp;
    }

    public void flip(){
        this.isFaceUp = !this.isFaceUp;
    }

    public String toString(){
        if(isFaceUp){
            return getRank() + " of " + getSuit();
        }
        return "[hidden]";
    }
}
