
import java.util.*;

public class UnoGame {
    private static final Scanner scanner = new Scanner(System.in);
    private final Queue<Card> deck = new LinkedList<>();
    private final List<Card> discard = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean clockwiseOrder = true;
    private Color currentColor;

    public static void main(String[] args) throws InterruptedException {
        new UnoGame().start();
    }

    public void start() throws InterruptedException {
        initializeDeck();
        Collections.shuffle((List<?>) deck);
        int numPlayers = 3;
        players.add(new Player("Vahe", false));
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Bot " + i, true));
        }

        // Deal 7 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck);
            }
        }

        // Initialize first card on table
        Card currentCard = dealFirstCard(deck);

        boolean gameOver = false;
        while (!gameOver) {
            displayCardCountPerPlayer();

            if (deck.isEmpty()) {
                reshuffleDiscard();
            }
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\n >> " + currentPlayer.getName() + "'s turn.");
            System.out.println(">>>>> Current card on table: " + currentCard);

            // TODO test if skip and draw two/four work correctly

            if (currentCard.isPlusTwo()) {
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                nextPlayer();
            }
            if (currentCard.isPlusFour()) {
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                nextPlayer();
            }
            if (currentCard.isSkip()) {
                nextPlayer();
            }
            if (currentCard.isReverse()) {
                clockwiseOrder = !clockwiseOrder;
            }

            if (currentPlayer.hasPlayableCard(currentCard, currentColor)) {
                Card playedCard;
                if (!currentPlayer.isBot()) {
                    playedCard = currentPlayer.playCard(currentCard, currentColor, scanner);
                    if (playedCard.isWild()) {
                        currentColor = currentPlayer.changeColor(scanner);
                    } else {
                        currentColor = playedCard.getColor();
                    }
                } else {
                    playedCard = currentPlayer.playRandomCard(currentCard, currentColor);
                    if (playedCard.isWild()) {
                        currentColor = currentPlayer.changeRandomColor();
                    } else {
                        currentColor = playedCard.getColor();
                    }
                }
                discard.add(currentCard);
                currentCard = playedCard;
            } else {
                System.out.println("No playable card, drawing a card...");
                currentPlayer.drawCard(deck);
            }

            if (currentPlayer.hasNoCards()) {
                System.out.println(currentPlayer.getName() + " wins!");
                gameOver = true;
            }

            nextPlayer();
            Thread.sleep(1000);
        }
    }

    public void nextPlayer() {
        if (clockwiseOrder) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
    }

    public Card dealFirstCard(Queue<Card> deck) {
        Card firstCard;
        do {
            firstCard = deck.poll();
            discard.add(firstCard);
            if (firstCard == null) {
                return null;
            }
        } while (firstCard.getType() != Card.Type.NUMBER);
        return firstCard;
    }

    private void initializeDeck() {
        List<Card> tempDeck = new ArrayList<>();

        // Initialize numbered cards (0-9, two of each except 0)
        for (Color color : Color.values()) {
            if (color != Color.WILD) {
                // Add one zero
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(0).createCard());
                // Add two copies of 1-9
                for (int i = 1; i <= 9; i++) {
                    tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(i).createCard());
                    tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(i).createCard());
                }
                // Add action cards: Skip, Reverse, Draw Two (two of each)
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.SKIP).createCard());
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.SKIP).createCard());
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.REVERSE).createCard());
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.REVERSE).createCard());
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.DRAW_TWO).createCard());
                tempDeck.add(new CardBuilder().setColor(color).setType(Card.Type.DRAW_TWO).createCard());
            }
        }

        // Add Wild and Wild Draw Four cards
        for (int i = 0; i < 4; i++) {
            tempDeck.add(new CardBuilder().setColor(Color.WILD).setType(Card.Type.COLOR_CHANGE).createCard());
            tempDeck.add(new CardBuilder().setColor(Color.WILD).setType(Card.Type.DRAW_FOUR).createCard());
        }

        Collections.shuffle(tempDeck);
        deck.addAll(tempDeck);
    }

    private void reshuffleDiscard() {
        Collections.shuffle(discard);
        deck.addAll(discard);
        discard.clear();
    }

    private void displayCardCountPerPlayer() {
        System.out.println("Number of cards: ");
        players.forEach(player -> {
            System.out.print(player.getName() + ": " + player.cardCount() + " cards. ");
            if (player.cardCount() == 1) {
                System.out.print(player.getName() + " is about to win!");
            }
            System.out.println();
        });
    }
}