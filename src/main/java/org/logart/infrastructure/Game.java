package org.logart.infrastructure;

import org.logart.dto.Result;
import org.logart.bots.Bot;
import org.logart.util.BidComparator;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Map<Integer, Integer> winrate;

    public Game() {
        this.winrate = new HashMap<>();
    }

    public void compete(Bot bot1, Bot bot2) {
        Result result1 = bot1.getBid();
        Result result2 = bot2.getBid();
        bot1.opponentBit(result2);
        bot2.opponentBit(result1);
        int result = BidComparator.compare(result1, result2);
        logResult(result, result1, result2);
        winrate.merge(result, 1, (v, a) -> v + 1);
    }

    private void logResult(int result, Result result1, Result result2) {
        System.out.println("Player 1 big for " + result1 + " player 2 bid for " + result2 + " winner is " + getWinner(result));
    }

    private String getWinner(int result) {
        return result == 0 ? "draw" : result == -1 ? "player1" : "player2";
    }

    public void printWinrate() {
        System.out.println(getWinner(0) + " " + winrate.get(0));
        System.out.println(getWinner(-1) + " " + winrate.get(-1));
        System.out.println(getWinner(1) + " " + winrate.get(1));
    }
}
