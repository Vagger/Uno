import java.util.*;

class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
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

    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    public String getName() {
        return name;
    }
}