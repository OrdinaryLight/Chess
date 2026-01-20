package com.example.chess.model.player;

import com.example.chess.model.board.Move;
import com.example.chess.model.game.GameState;
import com.example.chess.model.piece.PieceColor;

public abstract class Player {
    protected final PieceColor color;

    public Player(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract boolean isHuman();

    public abstract Move getMove(GameState gameState);
}
