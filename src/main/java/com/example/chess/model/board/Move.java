package com.example.chess.model.board;

import com.example.chess.model.piece.Piece;

public class Move {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;
    private final Piece piece;
    private final Piece capturedPiece;
    private final boolean isCastling;
    private final boolean isEnPassant;
    private final boolean isPromotion;

    private Move(Builder builder) {
        this.fromRow = builder.fromRow;
        this.fromCol = builder.fromCol;
        this.toRow = builder.toRow;
        this.toCol = builder.toCol;
        this.piece = builder.piece;
        this.capturedPiece = builder.capturedPiece;
        this.isCastling = builder.isCastling;
        this.isEnPassant = builder.isEnPassant;
        this.isPromotion = builder.isPromotion;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public boolean isCastling() {
        return isCastling;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public boolean isCapture() {
        return capturedPiece != null;
    }

    @Override
    public String toString() {
        String from = "" + (char)('a' + fromCol) + (8 - fromRow);
        String to = "" + (char)('a' + toCol) + (8 - toRow);
        return from + to + (isCapture() ? "x" : "");
    }

    public static class Builder {
        private int fromRow;
        private int fromCol;
        private int toRow;
        private int toCol;
        private Piece piece;
        private Piece capturedPiece;
        private boolean isCastling;
        private boolean isEnPassant;
        private boolean isPromotion;

        public Builder from(int row, int col) {
            this.fromRow = row;
            this.fromCol = col;
            return this;
        }

        public Builder to(int row, int col) {
            this.toRow = row;
            this.toCol = col;
            return this;
        }

        public Builder piece(Piece piece) {
            this.piece = piece;
            return this;
        }

        public Builder capturedPiece(Piece capturedPiece) {
            this.capturedPiece = capturedPiece;
            return this;
        }

        public Builder castling(boolean isCastling) {
            this.isCastling = isCastling;
            return this;
        }

        public Builder enPassant(boolean isEnPassant) {
            this.isEnPassant = isEnPassant;
            return this;
        }

        public Builder promotion(boolean isPromotion) {
            this.isPromotion = isPromotion;
            return this;
        }

        public Move build() {
            return new Move(this);
        }
    }
}
