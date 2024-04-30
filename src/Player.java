import java.util.ArrayList;

public class Player {
    static ArrayList<Card> hand;
    static int wallet;

    public Player (Deck d, int w) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
        wallet = w;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getWallet() {
        return wallet;
    }

    public void updateWallet(int update) {
        wallet += update;
    }
}
