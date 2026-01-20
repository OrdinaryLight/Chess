package com.example.chess.model.piece;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.board.Square;
import com.example.chess.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int direction = color == PieceColor.WHITE ? -1 : 1;

        int newRow = row + direction;
        if (isValidPosition(newRow, col)) {
            Square square = board.getSquare(newRow, col);
            if (square.isEmpty()) {
                moves.add(new Move.Builder()
                        .from(row, col)
                        .to(newRow, col)
                        .piece(this)
                        .promotion(newRow == 0 || newRow == 7)
                        .build());

                if (!hasMoved) {
                    int doubleRow = row + 2 * direction;
                    Square doubleSquare = board.getSquare(doubleRow, col);
                    if (doubleSquare.isEmpty()) {
                        moves.add(new Move.Builder()
                                .from(row, col)
                                .to(doubleRow, col)
                                .piece(this)
                                .build());
                    }
                }
            }
        }

        for (int colOffset : new int[]{-1, 1}) {
            int newCol = col + colOffset;
            if (isValidPosition(newRow, newCol)) {
                Square square = board.getSquare(newRow, newCol);
                if (!square.isEmpty() && square.getPiece().getColor() != color) {
                    moves.add(new Move.Builder()
                            .from(row, col)
                            .to(newRow, newCol)
                            .piece(this)
                            .capturedPiece(square.getPiece())
                            .promotion(newRow == 0 || newRow == 7)
                            .build());
                }

                Move lastMove = board.getLastMove();
                if (lastMove != null && lastMove.getPiece() instanceof Pawn) {
                    if (Math.abs(lastMove.getFromRow() - lastMove.getToRow()) == 2 &&
                            lastMove.getToRow() == row && lastMove.getToCol() == newCol) {
                        moves.add(new Move.Builder()
                                .from(row, col)
                                .to(newRow, newCol)
                                .piece(this)
                                .capturedPiece(lastMove.getPiece())
                                .enPassant(true)
                                .build());
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public int getValue() {
        return Constants.PAWN_VALUE;
    }

    @Override
    public Piece copy() {
        Pawn pawn = new Pawn(color, row, col);
        pawn.setMoved(hasMoved);
        return pawn;
    }
}
