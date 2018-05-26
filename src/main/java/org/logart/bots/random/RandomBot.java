package org.logart.bots.random;

import org.logart.bots.Bot;
import org.logart.dto.Result;

import java.util.Random;

public class RandomBot implements Bot {
    private Random random = new Random();

    @Override
    public Result getBid() {
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

    @Override
    public void opponentBit(Result result) {
        //do nothing
    }
}
