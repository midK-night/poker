import java.util.ArrayList;

public class Bot {
    private ArrayList<Card> hand;
    private boolean isActive;
    private boolean notFirstTurn;
    private int turnBet;
    private int num;

    public Bot (Deck d, int num) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
        isActive = true;
        notFirstTurn = false;
        this.num = num;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void betTurn(pokerTable p, int curBet) {
        if (!isActive) {
            turnBet = 0;
            return;
        }

        notFirstTurn = true;

        Hand current = new Hand(p, new Person(this));
        int val = current.getRelativeHandRank();
        double percentage = 1 - (val / 7462.0);

        if (percentage < 0.1) { //bottom 10% of hands -> fold
            isActive = false;
            turnBet = -1;
            System.out.println("Bot " + getNum() + " folds.");
            return;
        }

        if (percentage < 0.7 || notFirstTurn) { // bottom 70% of hands -> call
            turnBet = curBet;
            System.out.println("Bot " + getNum() + " calls.");
            return;
        }

        //raise
        int percen10 = (int) (5 - (percentage * 10));
        turnBet = (percen10 * 5) + curBet;
        System.out.println("Bot " + getNum() + " raises by " + (percen10 * 5) +
                ".\nThe bet for this round is now " + turnBet + ".");
    }

    public void resetTurn () {
        notFirstTurn = false;
        turnBet = 0;
    }

    public int getBet () {
        return turnBet;
    }

    public boolean betCurrent (int curBet) {
        return turnBet == curBet;
    }

    public int getNum () {
        return num;
    }

    public boolean getIsActive () {
        return isActive;
    }
}