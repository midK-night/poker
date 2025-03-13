import java.util.ArrayList;

public class Bot {
    private ArrayList<Card> hand;
    private boolean isActive;

    public Bot (Deck d) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
        isActive = true;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int turnBet (pokerTable p, int curBet) {
        if (!isActive) {
            return Integer.MIN_VALUE;
        }
        Hand current = new Hand(p, new Person(this));
        int val = current.getTemporaryHandValue();
        double percentage = 1 - (val / 7462.0);

        if (percentage < 0.1) { //bottom 10% of hands -> fold
            isActive = false;
            return -1;
        }

        if (percentage < 0.5) { // bottom 50% of hands -> call
            return curBet;
        }

        //raise
        int percen10 = (int) (5 - (percentage * 10));
        return (percen10 * 5) + curBet;
    }
}
