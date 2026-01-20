package com.example.chess.model.player;

import com.example.chess.model.board.Move;
import com.example.chess.model.game.GameState;
import com.example.chess.model.piece.PieceColor;

public class HumanPlayer extends Player {

    public HumanPlayer(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public Move getMove(GameState gameState) {
        return null;
    }
}
