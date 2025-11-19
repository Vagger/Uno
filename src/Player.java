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

    public boolean hasPlayableCard(Card topCard, Color currentColor) {
        return hand.stream().anyMatch(card -> card.isPlayableOn(topCard, currentColor));
    }

    public int cardCount() {
        return hand.size();
    }

    public Card playCard(Card topCard, Color currentColor, Scanner scanner) {
        System.out.println("Your hand: " + hand);
        System.out.println("Top card: " + topCard);
        List<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayableOn(topCard, currentColor)) {
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

    public Card playRandomCard(Card topCard, Color currentColor) {
        List<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayableOn(topCard, currentColor)) {
                playableCards.add(card);
            }
        }
        if (playableCards.isEmpty()) {
            System.out.println("No playable cards. " + name + " draws a card.");
            return null;
        } else {
            System.out.println("Playable cards: " + playableCards);
            Random rand = new Random();
            Card selected = playableCards.get(rand.nextInt(playableCards.size()));
            hand.remove(selected);
            return selected;
        }
    }

    public Color changeColor(Scanner scanner) {
        System.out.println("Choose a color: ");
        System.out.println("1: Red");
        System.out.println("2: Yellow");
        System.out.println("3: Blue");
        System.out.println("4: Green");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.BLUE;
            case 4 -> Color.GREEN;
            default -> changeColor(scanner);
        };
    }

    public Color changeRandomColor() {
        Random rand = new Random();
        int choice = rand.nextInt(5);
        return switch (choice) {
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.BLUE;
            case 4 -> Color.GREEN;
            default -> changeRandomColor();
        };
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