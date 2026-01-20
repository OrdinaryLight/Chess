package com.example.chess.model.board;

import com.example.chess.model.piece.Piece;

public class Square {
    private final int row;
    private final int col;
    private Piece piece;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.piece = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            piece.setPosition(row, col);
        }
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isOccupiedByColor(com.example.chess.model.piece.PieceColor color) {
        return piece != null && piece.getColor() == color;
    }

    @Override
    public String toString() {
        return piece == null ? "[]" : "[" + piece + "]";
    }
}
