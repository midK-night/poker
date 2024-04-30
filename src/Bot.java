import java.util.ArrayList;

public class Bot {
    private ArrayList<Card> hand;

    public Bot (Deck d) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
