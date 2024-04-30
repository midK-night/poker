public class Person {
    private Bot bot;
    private Player player;
    private int type;

    public Person(Bot bot) {
        this.bot = bot;
        type = 1;
    }

    public Person(Player player) {
        this.player = player;
        type = 2;
    }

    public Bot getBot() {
        return bot;
    }

    public Player getPlayer() {
        return player;
    }

    public int getType() {
        return type;
    }
}
