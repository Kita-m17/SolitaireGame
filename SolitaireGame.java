import java.util.*;

public class SolitaireGame {
    private Deck deck;
    private List<Pile> tableau;//columns within the solitaire game layout
    private List<Pile> foundation;//piles stacked in a specific order
    private Pile wastePile;
    
    public SolitaireGame(){
        deck = new Deck();
        deck.shuffle();
        tableau = new ArrayList<>(7);
        //this represents the four foundation piles required for Solitaire, each representing one pile for each suit.
        foundation = new ArrayList<>(4);
        wastePile = new Pile();

        setUpGame();
        for(int i = 0; i<4; i++){
            foundation.add(new Pile());//add a new empty pile to the foundations list
        }
    }

    public List<Pile> getTableau(){
        return tableau;
    }

    public Pile getWastePile(){
        return wastePile;
    }

    public void setUpGame(){
        //sets up the initial layout of the solitaire game's tableau piles (7 columns of cards)
        for(int i = 0; i<7; i++){//loop through each of the 7 piles
            tableau.add(new Pile());//creates a new pile and add it to the tableau list
            for(int j = 0; j<=i; j++){ //for each pile add (i+1) cards
                Card card = deck.dealCard();
                card.setFaceUp(false);
                tableau.get(i).addCard(card);//deal one card from the deck and add it to the pile
            }
        }

        for(Pile pile: tableau){
            if(!pile.isEmpty()){
                Card topCard = pile.getTopCard();
                if(!topCard.isFaceUp()){
                    topCard.flip();
                    //System.out.println("Flipping top card: " + topCard); // Debug statement
                }
            }
        }

    }

    public void displayTableau(){
        System.out.println("Waste pile: ");
        wastePile.displayPile();//display the waste pile
        System.out.println();

        //display the tableau
        for(int i = 0; i<tableau.size(); i++){
            Pile pile = tableau.get(i);
            System.out.println("Pile " + (i+1) + ": "); /*+ tableau.get(i).toString() + "\n");*/
            pile.displayPile();
            System.out.println();
        }
    }

    //method which draws cards from the deck to the waste pile
    public void drawFromDeck(){
        if(deck.isEmpty()){
            while(!wastePile.isEmpty()){
                Card card = wastePile.removeCard();
                card.flip();
                deck.addCard(card);
            }

            deck.shuffle();

            if(deck.isEmpty()){
                System.out.println("No more cards left in the game!");
                return;
            }
        }

        Card card = deck.dealCard();//draw card from the deck
        card.flip();//ensure its face up
        wastePile.addCard(card);//add it to the watse pile
    }

    public boolean moveCard(Pile source, Pile destination){
        //implement solitaire game rules for legal moves
        if(source.isEmpty()){
            System.out.println("Source pile is empty");
            return false;
        }

        Card card = source.removeCard();
        if(card!=null){
            destination.addCard(card);
            return true;
        }
        return false;
    }

    public boolean moveCard(int sourceIndex, int targetIndex){
        //implement solitaire game rules for legal moves
        if(sourceIndex<0 || sourceIndex>=tableau.size() || targetIndex<0 || targetIndex>=tableau.size()){
            System.out.println("Invalid pile index.");
            return false;
        }

        Pile source = tableau.get(sourceIndex);
        Pile destination = tableau.get(targetIndex);

        if(source.isEmpty()){
            System.out.println("Source pile is empty.");
            return false;
        }

        //Card cardToMove = source.get(source.size()-1);
        Card cardToMove = source.getTopCard();
        Card targetCard = destination.isEmpty() ? null : destination.getTopCard();

        //Card card = source.removeCard();
        if(cardToMove != null && isValidMove(cardToMove, targetCard)){
            source.removeCard();
            destination.addCard(cardToMove);

            //flip the new top card of the source pile if the pile is not empty
            if(!source.isEmpty()){
                Card newTopCard = source.getTopCard();
                if(!newTopCard.isFaceUp()){
                    newTopCard.flip();
                }
            }

            return true;
        }else{
            System.out.println( "Invalid move!");
            return false;
        }
    }

    public boolean moveToFoundation(int sourceIndex, int foundationIndex){
        //check if the foundation index is within bounds
        if(foundationIndex < 0 || foundationIndex >= foundation.size()){
            System.out.println("Invalid foundation index");
            return false;
        }

        // Check if source index is within bounds
        if (sourceIndex < 0 || sourceIndex >= tableau.size()) {
            System.out.println("Invalid source index.");
            return false;
        }

        Pile source = tableau.get(sourceIndex);
        if(source.isEmpty()){
            System.out.println("Source pile is empty.");
            return false;
        }

        Card card = source.getTopCard();
        if (card == null){
            System.out.println("No valid card to move.");
        }

        Pile foundationPile = foundation.get(foundationIndex);
        //if foundation pile is empty, allow only the Ace to be placed
        if(foundation.isEmpty()){
            if (!card.getRank().equals("Ace")){
                System.out.println("Only an Ace can start the foundation pile.");
                return false;
            }
        }


        if(moveToFoundation(card, foundationPile)){
            source.removeCard();

            // Flip the new top card of the source pile if it's face down
            if (!source.isEmpty() && !source.getTopCard().isFaceUp()) {
                source.getTopCard().flip();
            }
            return true;
        }
        return false;
    }

    public boolean moveFromTableauToTableau(int sourceIndex, int targetIndex){
        if (sourceIndex<0 || sourceIndex >= tableau.size()){
            System.out.println("Invalid source index.");
            return false;
        }
        if (targetIndex<0 || targetIndex >= tableau.size()){
            System.out.println("Invalid target index.");
            return false;
        }

        Pile sourcePile = tableau.get(sourceIndex);
        Pile targetPile = tableau.get(targetIndex);

        if(sourcePile.isEmpty()){
            System.out.println("Source pile is empty");
            return false;
        }

        List<Card> cardsToMove = new ArrayList<>();

        //gather all the face up cards
        for(int i = sourcePile.size() - 1; i >=0; i--){
            Card currentCard = sourcePile.get(i);
            if(currentCard.isFaceUp()){
                //loop top of pile down
                cardsToMove.add(0, currentCard);
            }else{
                break;
            }
        }

        if(cardsToMove.isEmpty()){
            System.out.println("No face-up cards to move.");
            return false;
        }

        //check if the sequence of cards is valid (alternating colours, in descending order)
        for(int i = 0; i< cardsToMove.size() - 1; i++){
            Card current = cardsToMove.get(i);
            Card next = cardsToMove.get(i+1);

            //Check if the next card is one rank lower and alternates colour
            if(!isNextRank(next, current) || !isDifferentColour(current, next)){
                System.out.println("Invalid move: Cards must alternate colors and be in descending order.");
                return false;
            }
        }

        if(targetPile.isEmpty()){
            if (!cardsToMove.get(0).getRank().equals("King")){
                System.out.println("Invalid move: You can only place a King on an empty pile.");
                return false;
            }
        }else{
            Card targetTopCard = targetPile.getTopCard();
            Card bottomMovingCard = cardsToMove.get(0);

            if (!isDifferentColour(bottomMovingCard, targetTopCard) || !isNextRank(bottomMovingCard, targetTopCard)) {
                System.out.println("Invalid move to target pile.");
                return false;
            }
        }

        for(Card card : cardsToMove){
            sourcePile.removeCard(card);
            targetPile.addCard(card);
        }

        // Flip the new top card of the source pile if it exists
        if (!sourcePile.isEmpty() && !sourcePile.getTopCard().isFaceUp()) {
            sourcePile.getTopCard().flip();
        }
        return true; 
    }

    public boolean isValidMove(Card cardToMove, Card targetCard){
        if( cardToMove.getRank().equals("King") && targetCard==null) return true;
        return (isDifferentColour(cardToMove, targetCard) && isNextRank(cardToMove, targetCard));
    }

    public boolean isDifferentColour(Card card1, Card card2){
        if(card1 == null || card2 == null){
            return false;
        }
        boolean card1Red = card1.getSuit().equals("Hearts") || card1.getSuit().equals("Diamonds");
        boolean card2Red = card2.getSuit().equals("Hearts") || card2.getSuit().equals("Diamonds");
        return card1Red != card2Red;// True if one is red and the other is black
    }

    public boolean isNextRank(Card lower, Card higher){
        if(lower == null || higher == null){
            return false;
        }

        int lowerRank = getRankToInt(lower.getRank());
        int higherRank = getRankToInt(higher.getRank());
        return lowerRank+1 == higherRank;
    }

    public int getRankToInt(String rank){
        switch(rank){
            case "Ace": return 1;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "Jack": return 11;
            case "Queen": return 12;
            case "King": return 13;
            default: return 0;
        }
    }
    
    public boolean moveToFoundation(Card card, Pile foundation){
        if (foundation.isEmpty()){
            if(card.getRank().equals("Ace")){
                foundation.addCard(card);
                return true;
            }else{
                System.out.println("Only an Ace can start a foundation pile.");
                return false;
            }
        }

        Card topCard = foundation.getTopCard();
        if(/*topCard!=null &&*/ card.getSuit().equals(topCard.getSuit()) && (getRankToInt(card.getRank()) == (getRankToInt(topCard.getRank())+1 ))){
            foundation.addCard(card);
            return true;
        } else{
            System.out.println("Invalid move to foundation.");
            return false;
        }
    }

    public boolean moveFromWasteToTableau(int targetIndex){
        if(wastePile.isEmpty()){
            System.out.println("Waste pile is empty!");
            return false;
        }

        Card cardToMove = wastePile.getTopCard();
        Pile targetPile = tableau.get(targetIndex);//target foundation
        
        Card targetCard;
        if(targetPile.isEmpty()){
            targetCard = null;
        }else{
            targetCard = targetPile.getTopCard();
        }

        if(isValidMove(cardToMove, targetCard)){
            wastePile.removeCard();
            targetPile.addCard(cardToMove);
            return true;
        }else{
            System.out.println("Invalid move!");
            return false;
        }
    }

    public boolean moveFromWasteToFoundation(int foundationIndex){
        if (foundationIndex < 0 || foundationIndex >= foundation.size()) {
            System.out.println("Invalid foundation index");
            return false;
        }
        
        if(wastePile.isEmpty()){
            System.out.println("Waste pile is empty!");
            return false;
        }

        Card cardToMove = wastePile.getTopCard();
        Pile foundationPile = foundation.get(foundationIndex);

        if(foundationPile.isEmpty()){
            if (cardToMove.getRank().equals("Ace")) {
                foundationPile.addCard(cardToMove);
                wastePile.removeCard();
                return true;
            } else {
                System.out.println("Only an Ace can start the foundation pile.");
                return false;
            }
        }

        Card topCard = foundationPile.getTopCard();
        if (isNextRank(topCard, cardToMove) && cardToMove.getSuit().equals(topCard.getSuit())) {
            foundationPile.addCard(cardToMove);
            wastePile.removeCard();
            return true;
        } else {
            System.out.println("Invalid move to foundation");
            return false;
        }
    }

    public boolean isGameWon(){
        for(Pile foundationPile : foundation){
            //for each foundation, there must be 13 cards
            if(foundationPile.size() < 13){
                return false;
            }
        }

        if(foundation.size() != 52){
            return false;
        }
        return true;
    }
}
