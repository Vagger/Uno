import java.util.*;

class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private final boolean isBot;

    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
    }

    public void drawCard(Queue<Card> deck) {
        if (!deck.isEmpty()) {
            hand.add(deck.poll());
        }
    }

    public boolean hasPlayableCard(Card topCard) {
        return hand.stream().anyMatch(card -> card.isPlayableOn(topCard));
    }

    public Card playCard(Card topCard, Scanner scanner) {
        System.out.println(name + "'s turn. Your hand: " + hand);
        System.out.println("Top card: " + topCard);
        List<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayableOn(topCard)) {
                playableCards.add(card);
            }
        }
        if (playableCards.isEmpty()) {
            System.out.println("No playable cards. Drawing a card...");
            return null;
        } else {
            System.out.println("Playable cards: " + playableCards);
            System.out.print("Choose a card to play (index): ");
            int choice = scanner.nextInt();
            Card selected = playableCards.get(choice);
            hand.remove(selected);
            return selected;
        }
    }

    public Card playRandomCard(Card topCard) {
        System.out.println(name + "'s turn.");
        List<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayableOn(topCard)) {
                playableCards.add(card);
            }
        }
        if (playableCards.isEmpty()) {
            System.out.println("No playable cards. " + name + " draws a card.");
            return null;
        } else {
            System.out.println("Playable cards: " + playableCards);
            System.out.print("Choose a card to play (index): ");
            Random rand = new Random();
            Card selected = playableCards.get(rand.nextInt(playableCards.size()));
            hand.remove(selected);
            return selected;
        }
    }

    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }
}