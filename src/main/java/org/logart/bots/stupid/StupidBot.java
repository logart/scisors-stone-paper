package org.logart.bots.stupid;

import org.logart.bots.Bot;
import org.logart.dto.Result;

public class StupidBot implements Bot {
    @Override
    public Result getBid() {
        return Result.SCISORS;
    }

    @Override
    public void opponentBit(Result result) {

    }
}
