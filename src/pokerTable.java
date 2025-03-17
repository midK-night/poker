import java.util.ArrayList;
import java.util.Scanner;

public class pokerTable {
    static ArrayList<Card> table;
    public pokerTable() { table = new ArrayList<>(); }
    public double gameLoop(int bots, double w) {
        Deck d = new Deck(bots);
        Player p = new Player(d, w);
        int pool = 0;
        int bet;

        System.out.println("\nYou chip in 10");
        w -= 10;
        pool += 10 * (bots + 1);

        System.out.println("\nYour hand is \n" + p.getHand().get(0).toString() + "\n" + p.getHand().get(1).toString());
        table.add(d.retrieveCard());
        table.add(d.retrieveCard());
        table.add(d.retrieveCard());

        System.out.println(tableToString(table));

        double[] turnResult = gameLoopTurn(d, p, w, pool);
        w = turnResult[1];
        pool = (int) turnResult[2];
        if (turnResult[0] < 0) {
            return w;
        }

        table.add(d.retrieveCard());
        System.out.println("\nThe card added to the table is: \n" + table.get(3).toString());
        System.out.println(tableToString(table));

        turnResult = gameLoopTurn(d, p, w, pool);
        w = turnResult[1];
        pool = (int) turnResult[2];
        if (turnResult[0] < 0) {
            return w;
        }

        table.add(d.retrieveCard());
        System.out.println("\nThe final card added to the table is: \n" + table.get(4).toString());
        System.out.println(tableToString(table));

        turnResult = gameLoopTurn(d, p, w, pool);
        w = turnResult[1];
        pool = (int) turnResult[2];
        if (turnResult[0] < 0) {
            return w;
        }

        int max = 0;
        for (int i = 0; i < d.getBots().size(); i++) {
            if (d.getBots().get(i).getIsActive()) {
                if (new Hand(this, new Person(d.getBots().get(i))).getRelativeHandRank() < new Hand(this, new Person(p)).getRelativeHandRank()) {
                    max = i;
                }
            }
        }

        boolean win = max == 0;
        if (!win) {
            System.out.println("Bot " + d.getBots().get(max).getNum() + " won");
            return w;
        }

        w += pool;
        System.out.println("You Won! \nYou gained the pot of " + pool + " credits");
        return w;
    }

    public void resetGame() {
        table.clear();
    }

    public ArrayList<Card> getTable() {
        return table;
    }

    public double[] gameLoopTurn (Deck d, Player p, double w, int pool) {
        ArrayList<Bot> b = d.getBots();
        double[] returnVar = {0, w, pool}; // -1/0 for player status, wallet status, pool status

        int curBet = 0;
        int firstBet = p.betTurn(0);
        if (firstBet == -1) {
            returnVar[0] = -1;
            return returnVar;
        }
        curBet = firstBet;


        for (Bot bot : b) {
            bot.betTurn(this, curBet);
            if (bot.getBet() > 0) {
                curBet = bot.getBet();
            }
        }

        if (!p.betCurrent(curBet) && p.betTurn(curBet) == -1) {
            returnVar[1] -= firstBet;
            return returnVar;
        }
        returnVar[1] -= curBet;
        returnVar[2] += curBet;
        p.resetTurn();
        p.setWallet(returnVar[1]);

        for (Bot bot : b) {
            if (!bot.betCurrent(curBet)) {
                bot.betTurn(this, curBet);
            }
            returnVar[2] += bot.getBet();
            bot.resetTurn();
        }
        return returnVar;
    }


    public String tableToString(ArrayList<Card> table) {
        StringBuilder str = new StringBuilder("\nThe cards on the table are: \n");
        for (Card card : table) {
            str.append(card.toString()).append("\n");
        }
        return str.toString();
    }
}