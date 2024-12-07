import java.util.*;
public class Pile {
    private List<Card> cards;

    public Pile(){
        cards = new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public Card removeCard(){
        if (!cards.isEmpty()){
            Card removedCard = cards.remove(cards.size()-1);
            if(!isEmpty()){
                Card topCard = getTopCard();
                if(!topCard.isFaceUp()){
                    topCard.flip();
                }
            }
            return removedCard;
        }
        return null;
    }

    public void removeCard(Card card){
        if (!cards.isEmpty()){
            cards.remove(card);
            
            if(!isEmpty()){
                Card topCard = getTopCard();
                if(!topCard.isFaceUp()){
                    topCard.flip();
                }
            }
        }
    }

    public Card removeAndFlipTopCard(){
        if(!isEmpty()){
            Card removedCard = removeCard();
            if(removedCard!=null && !removedCard.isFaceUp()){
                removedCard.flip();
            }
            return removedCard;
        }
        return null;
    }

    public List<Card> getCards(){
        return cards;
    }

    public void displayPile(){
        for(Card card: cards){
            //show face up cards differently
            if(card.isFaceUp()){
                System.out.println(card + " ");
            }else{
                System.out.println("[Hidden]");
            }
        }
        System.out.println();
    }

    public String toString(){
        String allCards = "";
        for(Card card: cards){
            allCards += card.toString();
            allCards += ", ";
        }
        return allCards;
    }

    public int size(){
        return cards.size();
    }

    public Card get(int index){
        return cards.get(index);
    }

    public Card getTopCard(){
        if(!cards.isEmpty()){
            return cards.get(cards.size()-1);
        }
        return null;
    }
}
