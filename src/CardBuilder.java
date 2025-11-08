public class CardBuilder {
    private Card.Color color;
    private Card.Type type;
    private int number = -1;

    public CardBuilder setColor(Card.Color color) {
        this.color = color;
        return this;
    }

    public CardBuilder setType(Card.Type type) {
        this.type = type;
        return this;
    }

    public CardBuilder setNumber(int number) {
        this.number = number;
        return this;
    }

    public Card createCard() {
        return new Card(color, type, number);
    }
}