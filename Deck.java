//package com.solitaire.model;
import java.util.*;

/*
 * Class which represemts a Deck of cards, which also manages a collection of Card objects
 */
public class Deck {

    private List<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        String [] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        String [] ranks = {"Ace", "2", "3","4","5","6", "7","8","9","10","Jack","Queen","King"};
        for(String suit: suits){
            for(String rank: ranks){
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        shuffle();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card dealCard(){
        if (cards.isEmpty()){
            return null;
        }else{
            Card card = cards.remove(cards.size()-1);
            card.setFaceUp(false);
            return card;
        }
    }

    public int size(){
        return cards.size();
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}
