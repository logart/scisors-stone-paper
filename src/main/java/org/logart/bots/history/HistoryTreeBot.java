package org.logart.bots.history;

import org.logart.bots.Bot;
import org.logart.dto.Result;

public class HistoryTreeBot implements Bot {

    private HistoryTree history;
    private Result lastOwnBid;

    public HistoryTreeBot(int winReward, int loseNegativeReward, int losePositiveReward, int tieReward) {
        history = new HistoryTree(winReward, loseNegativeReward, losePositiveReward, tieReward);
    }

    @Override
    public Result getBid() {
        Result optimalBid = history.getOptimalBid();
        lastOwnBid = optimalBid;
        return optimalBid;
    }

    @Override
    public void opponentBit(Result result) {
        history.registerHistory(lastOwnBid, result);
    }
}
