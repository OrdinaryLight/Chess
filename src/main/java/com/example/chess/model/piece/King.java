package com.example.chess.model.piece;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.board.Square;
import com.example.chess.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

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

        if (!hasMoved && !board.isInCheck(color)) {
            addCastlingMoves(board, moves);
        }

        return moves;
    }

    private void addCastlingMoves(Board board, List<Move> moves) {
        Piece kingsideRook = board.getPiece(row, 7);
        if (kingsideRook instanceof Rook && !kingsideRook.hasMoved()) {
            if (board.getSquare(row, 5).isEmpty() && board.getSquare(row, 6).isEmpty()) {
                if (!board.isSquareUnderAttack(row, 5, color.opposite()) &&
                        !board.isSquareUnderAttack(row, 6, color.opposite())) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(row, 6)
                            .piece(this)
                            .castling(true)
                            .build());
                }
            }
        }

        Piece queensideRook = board.getPiece(row, 0);
        if (queensideRook instanceof Rook && !queensideRook.hasMoved()) {
            if (board.getSquare(row, 1).isEmpty() &&
                    board.getSquare(row, 2).isEmpty() &&
                    board.getSquare(row, 3).isEmpty()) {
                if (!board.isSquareUnderAttack(row, 2, color.opposite()) &&
                        !board.isSquareUnderAttack(row, 3, color.opposite())) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(row, 2)
                            .piece(this)
                            .castling(true)
                            .build());
                }
            }
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public int getValue() {
        return Constants.KING_VALUE;
    }

    @Override
    public Piece copy() {
        King king = new King(color, row, col);
        king.setMoved(hasMoved);
        return king;
    }
}
