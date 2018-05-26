package org.logart.bots.history;

import org.logart.dto.Result;
import org.logart.util.BidComparator;
import org.logart.util.BidUtil;

import java.util.Random;

public class HistoryTree {
    private final int WIN_REWARD;
    private final int LOSE_NEGATIVE_REWARD;
    private final int LOSE_POSITIVE_REWARD;
    private final int TIE_REWARD;

    private int currentLevel;
    private int[] path = new int[100];
    private int[][] history = new int[100][3];
    private Random random = new Random();

    public HistoryTree(int winReward, int loseNegativeReward, int losePositiveReward, int tieReward) {
        this.WIN_REWARD = winReward;
        this.LOSE_NEGATIVE_REWARD = loseNegativeReward;
        this.LOSE_POSITIVE_REWARD = losePositiveReward;
        this.TIE_REWARD = tieReward;
    }

    public void nextLevel() {
        this.currentLevel++;
    }

    public void registerHistory(Result own, Result competitor) {
        int[] weights = history[currentLevel];
        Integer result = BidComparator.compare(own, competitor);
        int i = mapBidOnIndex(own);
        if (result == 0) {
            weights[i] += TIE_REWARD;
            path[currentLevel] = i;
            nextLevel();
        }
        if (result < 0) {
            weights[mapBidOnIndex(own)] += WIN_REWARD;
            updatePathWeight(true);
            currentLevel = 0;
        }
        if (result > 0) {
            weights[mapBidOnIndex(own)] -= LOSE_NEGATIVE_REWARD;
            weights[mapBidOnIndex(BidUtil.oppositeBid(competitor))] += LOSE_POSITIVE_REWARD;
            updatePathWeight(false);
            currentLevel = 0;
        }
    }

    private void updatePathWeight(boolean win) {
        int i = currentLevel;
        while (i > 0) {
            i--;
            int[] weights = history[i];
            int reward;
            if (win) {
                reward = WIN_REWARD;
            } else {
                reward = -(LOSE_NEGATIVE_REWARD + LOSE_POSITIVE_REWARD);
            }
            weights[path[i]] += reward;
        }

    }

    public Result getOptimalBid() {
        int[] weights = history[currentLevel];
        System.out.println(currentLevel + " " + weights[0] + " " + weights[1] + " " + weights[2]);
        int preferredIndex = findMaxIndex(weights);
        System.out.println(preferredIndex);
        if (preferredIndex == -1) {
            return BidUtil.randomBid();
        } else {
            return mapIndexOnBid(preferredIndex);
        }
    }

    private Result mapIndexOnBid(int prefferedIndex) {
        if (prefferedIndex == 0) {
            return Result.STONE;
        }
        if (prefferedIndex == 1) {
            return Result.SCISORS;
        }
        if (prefferedIndex == 2) {
            return Result.PAPER;
        }
        throw new IllegalStateException();
    }

    private int mapBidOnIndex(Result bid) {
        if (bid == Result.STONE) {
            return 0;
        }
        if (bid == Result.SCISORS) {
            return 1;
        }
        if (bid == Result.PAPER) {
            return 2;
        }
        throw new IllegalStateException();
    }

    private int findMaxIndex(int[] weights) {
        int result = 0;
        int secondMax = -1;
        int current = 0;


        if (weights[0] == weights[1] && weights[1] == weights[2]) {
            //if all chances are equal return -1 to bid random.
            return -1;
        }

        while (current < 2) {
            current++;
            if (weights[current] > weights[result]) {
                result = current;
                secondMax = result;
            } else if (weights[current] == weights[result]) {
                secondMax = result;
                result = current;
            }
        }

        //if second max does not equal to result it means there are several correct answers
        if (secondMax != -1 && result != secondMax) {
            if (random.nextInt(2) == 0) {
                return result;
            } else {
                return secondMax;
            }
        }
        return result;
    }
}
