package org.logart.util;

import org.logart.dto.Result;

import java.util.Random;

public class BidUtil {
    private static Random random = new Random();

    public static Result oppositeBid(Result bid) {
        if (bid == Result.SCISORS) {
            return Result.STONE;
        }
        if (bid == Result.STONE) {
            return Result.PAPER;
        }
        if (bid == Result.PAPER) {
            return Result.SCISORS;
        }
        throw new IllegalStateException();
    }

    public static Result randomBid() {
        int i = random.nextInt(3);
        if (i == 0) {
            return Result.SCISORS;
        }
        if (i == 1) {
            return Result.PAPER;
        }
        if (i == 2) {
            return Result.STONE;
        }
        throw new IllegalStateException();
    }

}
