Solitaire Game in Java
Overview
This project is a command-line implementation of the classic Solitaire game written in Java. 
The game models the key elements and rules of Solitaire, including a shuffled deck, tableau piles (columns), waste pile, and foundation piles (for each suit).
Players can draw cards, move cards between tableau piles, and move cards to foundations following standard Solitaire rules.

Features
Shuffled Deck: A standard 52-card deck is shuffled at the start of the game.
Tableau Setup: The game starts with 7 tableau piles of increasing size, where only the top card in each pile is face-up.
Foundation Piles: There are 4 foundation piles (one for each suit), where cards are stacked in ascending order starting with Aces.
Waste Pile: Drawn cards from the deck are moved to the waste pile.
Move Validation: Moves are validated to ensure they follow Solitaire rules (e.g., alternating colors in descending order for tableau moves, ascending order for foundation moves).
Game Win Condition: The game is won when all 52 cards are moved to the foundation piles in the correct order.
Gameplay
Initial Setup
The deck is shuffled and dealt into 7 tableau piles:
Pile 1: 1 card
Pile 2: 2 cards
Pile 3: 3 cards
Pile 4: 4 cards
Pile 5: 5 cards
Pile 6: 6 cards
Pile 7: 7 cards
Only the top card of each tableau pile is face-up.
Key Components
Deck: The stack of remaining cards that haven't been dealt.
Tableau: 7 columns where cards are arranged in descending order with alternating colors.
Foundation: 4 piles where cards are stacked by suit in ascending order starting with Aces.
Waste Pile: Cards drawn from the deck are placed here before being moved to the tableau or foundation.
Actions
Draw from Deck: Draw a card from the deck to the waste pile.
Move to Tableau: Move cards between tableau piles following the rules of alternating colors and descending ranks.
Move to Foundation: Move cards to the foundation piles if they follow ascending rank order and match the suit.
Move from Waste: Move the top card from the waste pile to either a tableau pile or a foundation pile.
Class Descriptions
SolitaireGame
The main class that manages the game state and gameplay logic.

Methods:
setUpGame(): Initializes the tableau with 7 piles of cards.
displayTableau(): Prints the current state of the tableau and waste pile.
drawFromDeck(): Draws a card from the deck to the waste pile.
moveCard(Pile source, Pile destination): Moves a card between piles.
moveCard(int sourceIndex, int targetIndex): Moves a card between tableau piles by index.
moveToFoundation(int sourceIndex, int foundationIndex): Moves a card from a tableau pile to a foundation pile.
moveFromWasteToTableau(int targetIndex): Moves a card from the waste pile to a tableau pile.
moveFromWasteToFoundation(int foundationIndex): Moves a card from the waste pile to a foundation pile.
isGameWon(): Checks if the game is won.
Pile
Represents a stack of cards. Supports operations like adding, removing, and displaying cards.

Card
Represents an individual card with a suit, rank, and face-up/face-down status.

Deck
Represents a shuffled deck of 52 cards with methods to deal cards and check if the deck is empty.
