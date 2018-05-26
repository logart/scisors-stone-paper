package org.logart;

import org.logart.bots.Bot;
import org.logart.bots.history.HistoryTreeBot;
import org.logart.bots.random.RandomBot;
import org.logart.infrastructure.Game;

public class Main {
    private static Game game;
    private static Bot bot1;
    private static Bot bot2;

    public static void main(String[] args) {
        game = new Game();
        bot1 = new HistoryTreeBot(1, 1, 1, 1);
        bot2 = new RandomBot();
        for (int i = 0; i < 10000; i++) {
            game.compete(bot1, bot2);
        }
        game.printWinrate();
    }
}
