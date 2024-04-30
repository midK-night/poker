public class Card {
    private int suit;
    private int rank;
    private int value;
    static int[] suits = {0X1000, 0X2000, 0X4000, 0X8000};

    public Card(int s, int v) {
        suit = s;
        rank = v;

        value = (1 << (rank + 16)) | suits[suit] | (rank << 8) | Tables.PRIMES[rank];
    }

    int getValue() {
        return value;
    }

    public String toString() {
        String str = "";
        switch (rank) {
            case 0:
                str += "Ace";
                break;
            case 1:
                str += "Two";
                break;
            case 2:
                str += "Three";
                break;
            case 3:
                str += "Four";
                break;
            case 4:
                str += "Five";
                break;
            case 5:
                str += "Six";
                break;
            case 6:
                str += "Seven";
                break;
            case 7:
                str += "Eight";
                break;
            case 8:
                str += "Nine";
                break;
            case 9:
                str += "Ten";
                break;
            case 10:
                str += "Jack";
                break;
            case 11:
                str += "Queen";
                break;
            case 12:
                str += "King";
                break;
        }
        str += " of ";
        switch (suit) {
            case 0:
                str += "spades";
                break;
            case 1:
                str += "hearts";
                break;
            case 2:
                str += "diamonds";
                break;
            case 3:
                str += "clubs";
                break;
        }
        return str;
    }
}
