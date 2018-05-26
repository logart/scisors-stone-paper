package org.logart.bots.distribution;

import org.logart.bots.Bot;
import org.logart.util.BidComparator;
import org.logart.util.BidUtil;
import org.logart.dto.Result;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class DistributionBot implements Bot {
    private Map<Result, Integer> history = new HashMap<>();
    private Result ownBid;
    private Random random = new Random();

    public DistributionBot() {
        initHistory();
    }

    @Override
    public Result getBid() {
        Optional<Map.Entry<Result, Integer>> max = history.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
        System.out.println(history);
        System.out.println(max);
        Result result = max
                .map(Map.Entry::getKey)
                .map(BidUtil::oppositeBid)
                .orElse(BidUtil.randomBid());
        ownBid = result;
        System.out.println(result);
        return result;
    }

    @Override
    public void opponentBit(Result result) {
        if (isWiningCombination(ownBid, result)) {
            //we had won and will start over
            initHistory();
        }
        history.merge(result, 1, (v, a) -> v + 1);
    }

    private boolean isWiningCombination(Result ownBid, Result result) {
        return BidComparator.compare(ownBid, result) == -1;
    }

    private void initHistory() {
        history.put(Result.SCISORS, 0);
        history.put(Result.PAPER, 0);
        history.put(Result.STONE, 0);
    }
}
