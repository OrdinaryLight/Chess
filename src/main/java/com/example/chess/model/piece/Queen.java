package com.example.chess.model.piece;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.board.Square;
import com.example.chess.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            while (isValidPosition(newRow, newCol)) {
                Square square = board.getSquare(newRow, newCol);

                if (square.isEmpty()) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(newRow, newCol)
                            .piece(this)
                            .build());
                } else {
                    if (square.getPiece().getColor() != color) {
                        moves.add(new Move.Builder()
                                .from(row, col)
                                .to(newRow, newCol)
                                .piece(this)
                                .capturedPiece(square.getPiece())
                                .build());
                    }
                    break;
                }

                newRow += dir[0];
                newCol += dir[1];
            }
        }

        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public int getValue() {
        return Constants.QUEEN_VALUE;
    }

    @Override
    public Piece copy() {
        Queen queen = new Queen(color, row, col);
        queen.setMoved(hasMoved);
        return queen;
    }
}
