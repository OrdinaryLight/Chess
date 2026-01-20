package com.example.chess.ai;

import com.example.chess.model.board.Move;
import com.example.chess.model.game.GameState;
import com.example.chess.model.piece.PieceColor;

public class ChessAI {
    private final Difficulty difficulty;

    public ChessAI(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Move findBestMove(GameState gameState) {
        PieceColor aiColor = gameState.getCurrentTurn();
        int depth = difficulty.getDepth();

        return MinimaxEngine.findBestMove(gameState.getBoard(), aiColor, depth);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
