package com.example.chess.model.piece;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.board.Square;
import com.example.chess.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[][] offsets = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (isValidPosition(newRow, newCol)) {
                Square square = board.getSquare(newRow, newCol);

                if (square.isEmpty()) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(newRow, newCol)
                            .piece(this)
                            .build());
                } else if (square.getPiece().getColor() != color) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(newRow, newCol)
                            .piece(this)
                            .capturedPiece(square.getPiece())
                            .build());
                }
            }
        }

        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public int getValue() {
        return Constants.KNIGHT_VALUE;
    }

    @Override
    public Piece copy() {
        Knight knight = new Knight(color, row, col);
        knight.setMoved(hasMoved);
        return knight;
    }
}
