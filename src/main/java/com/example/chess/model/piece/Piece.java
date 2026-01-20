package com.example.chess.model.piece;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import java.util.List;

public abstract class Piece {
    protected final PieceColor color;
    protected int row;
    protected int col;
    protected boolean hasMoved;

    public Piece(PieceColor color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.hasMoved = false;
    }

    public abstract List<Move> calculateLegalMoves(Board board);

    public abstract PieceType getType();

    public abstract int getValue();

    public abstract Piece copy();

    public PieceColor getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    @Override
    public String toString() {
        return color.toString().charAt(0) + getType().toString();
    }
}
