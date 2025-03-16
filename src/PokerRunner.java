import java.util.*;

public class PokerRunner {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        pokerTable table = new pokerTable();
        double w;
        int b;
        boolean gameOver = false;

        System.out.println("**************************************************");
        System.out.println("     THE    POKER   TABLE   -   $10 chip in ");
        System.out.println("**************************************************");

        System.out.print("How much money are you entering into the game?: $");
        w = console.nextDouble();

        System.out.print("How large is the table you're playing at? (# of other people): ");
        b = console.nextInt();
        console.nextLine();

        while (!gameOver) {
            w = table.gameLoop(b, w);
            table.resetGame();
            System.out.println("\nCurrent wallet: " + w);
            System.out.print("Would you like to keep playing? (Y/N): ");
            gameOver = console.nextLine().equalsIgnoreCase("n");
        }
    }
}
