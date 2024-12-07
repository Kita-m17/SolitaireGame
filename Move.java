import java.util.*;

public class Move{
    public enum MoveType{
        TABLEAU_TO_TABLEAU,
        TABLEAU_TO_FOUNDATION,
        WASTE_TO_TABLEAU,
        WASTE_TO_FOUNDATION,
        DRAW_FROM_DECK
    }

    private MoveType move;
    private int sourceIndex;
    private int targetIndex;
    private List<Card> movedCards;
    private boolean topCardFlipped;

    public Move(MoveType moveType, int sourceIndex, int targetIndex, List<Card> movedCards, boolean topFlippedCard){
        move = moveType;
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
        this.movedCards = new ArrayList<>(movedCards);
        this.topCardFlipped = topFlippedCard;
    }

    public MoveType getType(){
        return move;
    }

    public int getSourceIndex(){
        return sourceIndex;
    }

    public int getDestIndex(){
        return targetIndex;
    }

    public List<Card> getMovedCard(){
        return movedCards;
    }

    public boolean wasTopCardFlipped(){
        return topCardFlipped;
    }
}