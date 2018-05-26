package org.logart.bots.history;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.logart.dto.Result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.logart.util.BidUtil.oppositeBid;

public class HistoryTreeBotTest {

    private static final int RANDOM_LUCKINESS = 2;
    private static final int SIZE = 3;
    private HistoryTreeBot bot;

    @Before
    public void setUp() {
        bot = new HistoryTreeBot(1, 1, 1, 1);
    }

    @Test
    public void historyBotShouldBidRandomValueWithoutHistory() {
        assertRandomBid();
    }

    @Test
    public void historyBotShouldShouldBidValueOppositeToCompetitorsInPreviousTurn() {
        Result bid = bot.getBid();
        Result oppositeBid = oppositeBid(bid);
        bot.opponentBit(oppositeBid);

        Result optimalBid = bot.getBid();
        assertThat(optimalBid)
                .isEqualTo(oppositeBid(oppositeBid));
    }

    @Test
    public void historyBotShouldShouldBidRandomOnDrawIfHistoryIsNotAvailable() {
        Set<Result> optimalBids = new HashSet<>();
        Result optimalBid = bot.getBid();
        Result oppositeBid = oppositeBid(optimalBid);
        bot.opponentBit(oppositeBid);

        for (int i = 0; i < 100; i++) {
            optimalBid = bot.getBid();
            optimalBids.add(optimalBid);
            oppositeBid = oppositeBid(optimalBid);
            bot.opponentBit(oppositeBid);
        }

        assertThat(optimalBids).contains(Result.SCISORS, Result.PAPER, Result.STONE);
    }

    @Test
    public void historyBotWinShouldInfluenceAllLevelsOfHistory() {
        List<Result> freshStartOptimalBids = collectBidHistory(SIZE);
        Result optimalBid = bot.getBid();
        bot.opponentBit(oppositeLoosingBid(optimalBid));
        List<Result> secondStartOptimalBids = collectBidHistory(SIZE);

        assertThat(secondStartOptimalBids).containsSequence(freshStartOptimalBids);
    }

    @Test
    public void historyBotLoseShouldInfluenceAllLevelsOfHistory() {
        List<Result> freshStartOptimalBids = collectBidHistory(SIZE);
        Result optimalBid = bot.getBid();
        bot.opponentBit(oppositeBid(optimalBid));
        List<Result> secondStartOptimalBids = collectBidHistory(SIZE);

        assertThat(secondStartOptimalBids)
                //use negative comparator to ensure that second attempt didn't choose elements from the first one
                .usingElementComparator((a, b) -> a.equals(b) ? 1 : 0)
                .isEqualTo(freshStartOptimalBids);
    }


    @Test
    public void historyBotLoseShouldBidRandomIfThereAreSeveralEqualChances() {
        collectBidHistory(SIZE);
        Result optimalBid = bot.getBid();
        bot.opponentBit(oppositeBid(optimalBid));

        assertRandomBid();
    }

    @Test
    public void historyBotShouldUpdateHistoryWhenLose() {
        Result bid = bot.getBid();
        bot.opponentBit(bid);

        assertRandomBid();
    }

    private void assertRandomBid() {
        for (int i = 0; i < RANDOM_LUCKINESS; ++i) {
            Result bid1 = bot.getBid();
            Result bid2 = bot.getBid();
            if (bid1 != bid2)
                return;
        }
        Assert.fail();
    }

    private static Result oppositeLoosingBid(Result bid) {
        if (bid == Result.SCISORS) {
            return Result.PAPER;
        }
        if (bid == Result.STONE) {
            return Result.SCISORS;
        }
        if (bid == Result.PAPER) {
            return Result.STONE;
        }
        throw new IllegalStateException();
    }

    private List<Result> collectBidHistory(int size) {
        List<Result> result = new ArrayList<>();
        Result optimalBid;
        for (int i = 0; i < size; i++) {
            optimalBid = bot.getBid();
            result.add(optimalBid);
            bot.opponentBit(optimalBid);
        }
        return result;
    }
}
