import java.util.*;

public class Card {

    enum Type { NUMBER, SKIP, REVERSE, DRAW_TWO, COLOR_CHANGE, DRAW_FOUR }

    private final Color color;
    private final Type type;
    private final int number; // Valid only for NUMBER type

    public Card(Color color, Type type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    public boolean isPlayableOn(Card topCard, Color currentColor) {
        if (this.color == Color.WILD || this.type == Type.COLOR_CHANGE || this.type == Type.DRAW_FOUR) {
            return true;
        }
        if (this.type == Type.DRAW_TWO || this.type == Type.SKIP || this.type == Type.REVERSE) {
            return this.color == currentColor;
        }
        return this.color == currentColor || this.number == topCard.number;
    }

    public Color getColor() {
        return this.color;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isPlusTwo() {
        return this.type == Type.DRAW_TWO;
    }

    public boolean isPlusFour() {
        return this.type == Type.DRAW_FOUR;
    }

    public boolean isSkip() {
        return this.type == Type.SKIP;
    }

    public boolean isReverse() {
        return this.type == Type.REVERSE;
    }

    public boolean isWild() {
        return this.color == Color.WILD;
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
