
import java.util.*;

public class UnoGame {
    private static final Scanner scanner = new Scanner(System.in);
    private final Queue<Card> deck = new LinkedList<>();
    private final List<Card> discard = new LinkedList<>();
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public static void main(String[] args) {
        new UnoGame().start();
    }

    public void start() {
        initializeDeck();
        Collections.shuffle((List<?>) deck);
        int numPlayers = 2; // For simplicity, 2 players
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
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
            if (deck.isEmpty()) {
                reshuffleDiscard();
            }
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\n" + currentPlayer.getName() + "'s turn.");
            System.out.println("Current card on table: " + currentCard);

            if (currentCard.isPlusTwo()) {
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                continue;
            }
            if (currentCard.isPlusFour()) {
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayer.drawCard(deck);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                continue;
            }
            if (currentCard.isSkip()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                continue;
            }

            if (currentPlayer.hasPlayableCard(currentCard)) {
                Card playedCard = currentPlayer.playCard(currentCard, scanner);
                if (playedCard != null) {
                    discard.add(currentCard);
                    currentCard = playedCard;
                }
            } else {
                System.out.println("No playable card, drawing a card...");
                currentPlayer.drawCard(deck);
            }

            if (currentPlayer.hasNoCards()) {
                System.out.println(currentPlayer.getName() + " wins!");
                gameOver = true;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
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
        // Initialize numbered cards (0-9, two of each except 0)
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.WILD) {
                // Add one zero
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(0).createCard());
                // Add two copies of 1-9
                for (int i = 1; i <= 9; i++) {
                    deck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(i).createCard());
                    deck.add(new CardBuilder().setColor(color).setType(Card.Type.NUMBER).setNumber(i).createCard());
                }
                // Add action cards: Skip, Reverse, Draw Two (two of each)
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.SKIP).createCard());
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.SKIP).createCard());
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.REVERSE).createCard());
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.REVERSE).createCard());
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.DRAW_TWO).createCard());
                deck.add(new CardBuilder().setColor(color).setType(Card.Type.DRAW_TWO).createCard());
            }
        }

        // Add Wild and Wild Draw Four cards
        for (int i = 0; i < 4; i++) {
            deck.add(new CardBuilder().setColor(Card.Color.WILD).setType(Card.Type.WILD).createCard());
            deck.add(new CardBuilder().setColor(Card.Color.WILD).setType(Card.Type.WILD_DRAW_FOUR).createCard());
        }
    }

    private void reshuffleDiscard() {
        Collections.shuffle(discard);
        deck.addAll(discard);
        discard.clear();
    }
}