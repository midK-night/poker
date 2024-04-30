import java.util.ArrayList;

public class Player {
    static ArrayList<Card> hand;
    static double wallet;

    public Player (Deck d, double w) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
        wallet = w;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
