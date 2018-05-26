package org.logart.bots;

import org.logart.dto.Result;

public interface Bot {
    Result getBid();

    void opponentBit(Result result);
}
