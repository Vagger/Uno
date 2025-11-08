import java.util.*;

public class Card {

    enum Color { RED, YELLOW, GREEN, BLUE, WILD }
    enum Type { NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR }

    private final Color color;
    private final Type type;
    private final int number; // Valid only for NUMBER type

    public Card(Color color, Type type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    public Card(Color color, Type type) {
        this(color, type, -1);
    }

    public boolean isPlayableOn(Card topCard) {
        if (this.color == Color.WILD || this.type == Type.WILD || this.type == Type.WILD_DRAW_FOUR) {
            return true;
        }
        if (this.type == Type.DRAW_TWO || this.type == Type.SKIP || this.type == Type.REVERSE) {
            return this.color == topCard.color;
        }
        return this.color == topCard.color || this.number == topCard.number;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isPlusTwo() {
        return this.type == Type.DRAW_TWO;
    }

    public boolean isPlusFour() {
        return this.type == Type.WILD_DRAW_FOUR;
    }

    public boolean isSkip() {
        return this.type == Type.SKIP;
    }

    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return color + " " + number;
        } else {
            return color + " " + type;
        }
    }
}
