import java.util.*;

public class Deck {
    static boolean[][] isDrafted;
    static ArrayList<Bot> bots;

    public Deck (int bots) {
        isDrafted = new boolean[4][13];
        this.bots = new ArrayList<Bot>(bots);
        for (int i = 0; i < bots; i++) {
            this.bots.add(new Bot(this, i));
        }
    }

    public boolean getDrafted(int s, int v) {
        return isDrafted[s][v];
    }

    public void updateDeck(int s, int v) {
        isDrafted[s][v] = true;
    }

    public Card retrieveCard() {
        Random rng = new Random();
        boolean empty = false;
        int s = -1, v = -1;
        while (!empty) {
            s = rng.nextInt(4);
            v = rng.nextInt(13);
            empty = !getDrafted(s,v);
        }
        Card temp = new Card(s, v);
        updateDeck(s,v);
        return temp;
    }

    public ArrayList<Bot> getBots() {
        return bots;
    }
}
