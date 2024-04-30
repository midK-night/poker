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

        bet = betTurn(w);
        w -= bet;
        pool += (bet * (bots + 1));
        if (bet == -1) {
            return w;
        }

        table.add(d.retrieveCard());
        System.out.println("\nThe card added to the table is: \n" + table.get(3).toString());
        System.out.println(tableToString(table));

        bet = betTurn(w);
        w -= bet;
        pool += (bet * (bots + 1));
        if (bet == -1) {
            return w;
        }

        table.add(d.retrieveCard());
        System.out.println("\nThe final card added to the table is: \n" + table.get(4).toString());
        System.out.println(tableToString(table));

        int max = 0;
        for (int i = 0; i < d.getBots().size(); i++) {
            if (new Hand(this, new Person(d.getBots().get(i))).getRelativeHandRank() < new Hand(this, new Person(p)).getRelativeHandRank() ) {
                max = i;
            }
        }

        boolean win = max == 0;
        if (!win) {
            System.out.println("Bot " + max + " won");
            return w;
        }

        w += pool;
        System.out.println("You Won! \nYou gained " + pool + " credits");
        return w;
    }

    public ArrayList<Card> getTable() {
        return table;
    }

    public int betTurn(double w) {
        Scanner console = new Scanner(System.in);
        int bet = 0;
        boolean valid = false;
        String choice = "";
        while (!valid) {
            System.out.print("Would you like to raise, hold, or fold? >>> ");
            choice = console.nextLine();
            valid = choice.equalsIgnoreCase("raise") || choice.equalsIgnoreCase("hold") || choice.equalsIgnoreCase("fold");
        }

        if (choice.equalsIgnoreCase("raise")) {
            valid = false;
            while (!valid) {
                System.out.print("How much would you like to raise by? >>> ");
                bet = console.nextInt();
                valid = bet < w;
            }
        } else if (choice.equalsIgnoreCase("fold")) {
            bet = -1;
        }
        return bet;
    }

    public String tableToString(ArrayList<Card> table) {
        String str = "\nThe cards on the table are: \n";
        for (int i = 0; i < table.size(); i++) {
            str += table.get(i).toString() + "\n";
        }
        return str;
    }
}