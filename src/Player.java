import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    static ArrayList<Card> hand;
    static double wallet;
    private boolean notFirstTurn;
    private int turnBet;

    public Player (Deck d, double w) {
        hand = new ArrayList<>(2);
        hand.add(d.retrieveCard());
        hand.add(d.retrieveCard());
        wallet = w;
        notFirstTurn = false;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int betTurn(int curBet) {
        Scanner console = new Scanner(System.in);
        int bet = 0;
        boolean valid = false;
        String choice = "";
        if (notFirstTurn) {
            while (!valid) {
                System.out.print("Would you like to hold or fold? >>> ");
                choice = console.nextLine();
                valid = choice.equalsIgnoreCase("hold") || choice.equalsIgnoreCase("fold");
            }
        } else { // FIRST TURN
            while (!valid) {
                System.out.print("Would you like to raise, hold, or fold? >>> ");
                choice = console.nextLine();
                valid = choice.equalsIgnoreCase("raise") || choice.equalsIgnoreCase("hold") || choice.equalsIgnoreCase("fold");
            }
            notFirstTurn = true;
        }

        if (choice.equalsIgnoreCase("raise")) {
            valid = false;
            while (!valid) {
                System.out.print("How much would you like to raise by? >>> ");
                bet = console.nextInt();
                valid = bet < wallet;
            }
            return bet + curBet;
        } else if (choice.equalsIgnoreCase("fold")) {
            return -1;
        }
        return curBet;
    }

    public void resetTurn () {
        notFirstTurn = false;
    }

    public boolean betCurrent (int curBet) {
        return turnBet == curBet;
    }
}
