package com.example.chess.model.player;

import com.example.chess.ai.ChessAI;
import com.example.chess.ai.Difficulty;
import com.example.chess.model.board.Move;
import com.example.chess.model.game.GameState;
import com.example.chess.model.piece.PieceColor;

public class AIPlayer extends Player {
    private final ChessAI ai;

    public AIPlayer(PieceColor color, Difficulty difficulty) {
        super(color);
        this.ai = new ChessAI(difficulty);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Move getMove(GameState gameState) {
        return ai.findBestMove(gameState);
    }
}
