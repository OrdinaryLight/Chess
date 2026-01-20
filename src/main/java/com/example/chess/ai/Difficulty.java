package com.example.chess.ai;

import com.example.chess.util.Constants;

public enum Difficulty {
    EASY(Constants.AI_EASY_DEPTH),
    MEDIUM(Constants.AI_MEDIUM_DEPTH),
    HARD(Constants.AI_HARD_DEPTH);

    private final int depth;

    Difficulty(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }
}
