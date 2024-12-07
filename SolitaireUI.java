import java.util.Scanner;
public class SolitaireUI {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        SolitaireGame game = new SolitaireGame();
        
        game.drawFromDeck();
        game.displayTableau();

        while(true){
            System.out.println("Enter your move (e.g., draw, move source target, movef source, waste,  quit): ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");

            try{
                if(parts[0].equalsIgnoreCase("draw")){
                    game.drawFromDeck();
                } else if(parts[0].equalsIgnoreCase("move") && parts.length == 3){
                    int source = Integer.parseInt(parts[1])-1;
                    int target = Integer.parseInt(parts[2])-1;
                    boolean success = game.moveFromTableauToTableau(source, target);
                    if(!success){
                        System.out.println("Move failed. Please try again.");
                    }
                } else if(parts[0].equalsIgnoreCase("movef") && parts.length == 2){
                    int source = Integer.parseInt(parts[1])-1;
                    if(source < 0 || source >= game.getTableau().size()){
                        System.out.println("Invalid tableau pile index.");
                    }else{
                        Pile sourcePile = game.getTableau().get(source);
                        if (sourcePile.isEmpty()) {
                            System.out.println("Source pile is empty.");
                        }else{
                            Card cardToMove = sourcePile.getTopCard();
                            int foundationIndex = getFoundationIndex(cardToMove.getSuit());
                            boolean success = game.moveToFoundation(source, foundationIndex);
                            if(!success){
                                System.out.println("Move to foundation failed.");
                            }
                        }
                    }
                } else if(parts[0].equalsIgnoreCase("waste") && parts.length ==2){
                    int target = Integer.parseInt(parts[1])-1;
                    boolean success = game.moveFromWasteToTableau(target);
                    if (!success){
                        System.out.println("Move from waste to tableau failed.");
                    }
                } else if(parts[0].equalsIgnoreCase("wastef")){
                    Pile wastePile = game.getWastePile();
                    if(wastePile.isEmpty()){
                        System.out.println("Waste pile is empty.");
                    }else{
                        Card  cardToMove = wastePile.getTopCard();
                        int foundationIndex = getFoundationIndex(cardToMove.getSuit());
                        boolean success = game.moveFromWasteToFoundation(foundationIndex);
                        if(!success){
                            System.out.println("Move from waste to foundation failed.");
                        }
                    }
                } else if (input.equalsIgnoreCase("quit")) {
                    System.out.println("Game over. Thanks for playing!");
                    break;
                } else if(game.isGameWon()){
                    System.out.println("Congratulations, you won!");
                    break;
                } else{
                    System.out.println("Invalid command. Please try again.");
                }

                game.displayTableau();
            }catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter numbers for pile indices.");
            } finally{
                scanner.close();
            }

            // Check for win condition
            if (game.isGameWon()) {
                System.out.println("Congratulations, you won!");
                break;
            }
        }
    }

    public static int getFoundationIndex(String suit){
        switch(suit){
            case "Spades":
                return 0;
            case "Diamonds":
                return 1;
            case "Clubs":
                return 2;
            case "Hearts":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown suit: " + suit);
        }
    }
}