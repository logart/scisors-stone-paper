package org.logart.util;

import org.logart.dto.Result;

public class BidComparator {
    public static Integer compare(Result result1, Result result2) {
        if (result1 == result2) {
            return 0;
        }
        if (result1 == Result.SCISORS && result2 == Result.STONE) {
            return 1;
        }
        if (result1 == Result.SCISORS && result2 == Result.PAPER) {
            return -1;
        }
        if (result1 == Result.STONE && result2 == Result.PAPER) {
            return 1;
        }
        if (result1 == Result.STONE && result2 == Result.SCISORS) {
            return -1;
        }
        if (result1 == Result.PAPER && result2 == Result.SCISORS) {
            return 1;
        }
        if (result1 == Result.PAPER && result2 == Result.STONE) {
            return -1;
        }
        throw new IllegalStateException();
    }

}
