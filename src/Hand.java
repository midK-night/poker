import java.util.*;

public class Hand {
    private final ArrayList<Card> cards;
    public Hand(pokerTable p, Person c) {
        cards = new ArrayList<>();
        cards.addAll(p.getTable());

        boolean personIsBot = c.getType() == 1;
        if (personIsBot) {
            cards.addAll(c.getBot().getHand());
        } else {
            cards.addAll(c.getPlayer().getHand());
        }
    }

    public int evaluate(Card[] cards) {
        // Only 5-card hands are supported
        if (cards == null || cards.length != 5) {
            throw new IllegalArgumentException("Exactly 5 cards are required.");
        }

        //Binary representations of each card
        final int c1 = cards[0].getValue();
        final int c2 = cards[1].getValue();
        final int c3 = cards[2].getValue();
        final int c4 = cards[3].getValue();
        final int c5 = cards[4].getValue();

        // No duplicate cards allowed
        if (hasDuplicates(new int[]{c1, c2, c3, c4, c5})) {
            throw new IllegalArgumentException("Illegal hand.");
        }

        // Calculate index in the flushes/unique table
        final int index = (c1 | c2 | c3 | c4 | c5) >> 16;

        // Flushes, including straight flushes
        if ((c1 & c2 & c3 & c4 & c5 & 0xF000) != 0) {
            return Tables.Flushes.TABLE[index];
        }

        // Straight and high card hands
        final int value = Tables.Unique.TABLE[index];
        if (value != 0) {
            return value;
        }

        // Remaining cards
        final int product = (c1 & 0xFF) * (c2 & 0xFF) * (c3 & 0xFF) * (c4 & 0xFF) * (c5 & 0xFF);
        return Tables.Hash.Values.TABLE[hash(product)];
    }

    public int getRelativeHandRank() {
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<ArrayList<Card>> combinations = new ArrayList<>();
        generateCombinations(cards, combinations, new ArrayList<Card>(), 0);

        for (int i = 0; i < combinations.size(); i++) {
            Card[] temp = new Card[combinations.get(i).size()];
            values.add(evaluate(combinations.get(i).toArray(temp)));
        }

        int temp = 7500;

        for (int value : values) {
            temp = Math.min(temp, value);
        }

        return temp;
    }

    private void generateCombinations (ArrayList<Card> input, ArrayList<ArrayList<Card>> result, ArrayList<Card> inProgress, int x) {
        if (inProgress.size() == 5) {
            result.add((ArrayList<Card>) inProgress.clone());
        } else {
            for (int i = x; i < input.size(); i++) {
                inProgress.add(input.get(i));
                generateCombinations(input, result, inProgress, i + 1);
                inProgress.removeLast();
            }
        }
    }

    private static boolean hasDuplicates(int[] values) {
        Arrays.sort(values);
        for (int i = 1; i < values.length; i++) {
            if (values[i] == values[i - 1])
                return true;
        }
        return false;
    }

    private static int hash(int key) {
        key += 0xE91AAA35;
        key ^= key >>> 16;
        key += key << 8;
        key ^= key >>> 4;
        return ((key + (key << 2)) >>> 19) ^ Tables.Hash.Adjust.TABLE[(key >>> 8) & 0x1FF];
    }
}
