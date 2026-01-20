package com.example.chess.model.game;

import com.example.chess.model.piece.PieceColor;

public class GameResult {
    private final GameStatus status;
    private final PieceColor winner;

    public GameResult(GameStatus status, PieceColor winner) {
        this.status = status;
        this.winner = winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public PieceColor getWinner() {
        return winner;
    }

    public enum GameStatus {
        IN_PROGRESS,
        CHECKMATE,
        STALEMATE,
        DRAW
    }
}
