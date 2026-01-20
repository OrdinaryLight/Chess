package com.example.chess.model.board;

import com.example.chess.model.piece.*;
import com.example.chess.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Square[][] squares;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private Move lastMove;

    public Board() {
        this.squares = new Square[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        initializeBoard();
    }

    private Board(Square[][] squares, List<Piece> whitePieces, List<Piece> blackPieces, Move lastMove) {
        this.squares = squares;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.lastMove = lastMove;
    }

    private void initializeBoard() {
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                squares[row][col] = new Square(row, col);
            }
        }
        setupPieces();
    }

    private void setupPieces() {
        setupBackRank(0, PieceColor.BLACK);
        setupPawns(1, PieceColor.BLACK);
        setupPawns(6, PieceColor.WHITE);
        setupBackRank(7, PieceColor.WHITE);
    }

    private void setupBackRank(int row, PieceColor color) {
        placePiece(new Rook(color, row, 0), row, 0);
        placePiece(new Knight(color, row, 1), row, 1);
        placePiece(new Bishop(color, row, 2), row, 2);
        placePiece(new Queen(color, row, 3), row, 3);
        placePiece(new King(color, row, 4), row, 4);
        placePiece(new Bishop(color, row, 5), row, 5);
        placePiece(new Knight(color, row, 6), row, 6);
        placePiece(new Rook(color, row, 7), row, 7);
    }

    private void setupPawns(int row, PieceColor color) {
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            placePiece(new Pawn(color, row, col), row, col);
        }
    }

    private void placePiece(Piece piece, int row, int col) {
        squares[row][col].setPiece(piece);
        if (piece.getColor() == PieceColor.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    public Square getSquare(int row, int col) {
        if (row < 0 || row >= Constants.BOARD_SIZE || col < 0 || col >= Constants.BOARD_SIZE) {
            return null;
        }
        return squares[row][col];
    }

    public Piece getPiece(int row, int col) {
        Square square = getSquare(row, col);
        return square != null ? square.getPiece() : null;
    }

    public void makeMove(Move move) {
        Square fromSquare = getSquare(move.getFromRow(), move.getFromCol());
        Square toSquare = getSquare(move.getToRow(), move.getToCol());

        Piece piece = fromSquare.getPiece();
        Piece captured = toSquare.getPiece();

        if (captured != null) {
            removePiece(captured);
        }

        fromSquare.setPiece(null);
        toSquare.setPiece(piece);
        piece.setMoved(true);

        if (move.isCastling()) {
            performCastling(move);
        }

        if (move.isEnPassant()) {
            performEnPassant(move);
        }

        lastMove = move;
    }

    public void promotePawn(int row, int col, PieceType promotionType) {
        Square square = getSquare(row, col);
        Piece pawn = square.getPiece();

        if (pawn == null || !(pawn instanceof Pawn)) {
            return;
        }

        removePiece(pawn);
        square.setPiece(null);

        Piece promotedPiece = switch (promotionType) {
            case QUEEN -> new Queen(pawn.getColor(), row, col);
            case ROOK -> new Rook(pawn.getColor(), row, col);
            case BISHOP -> new Bishop(pawn.getColor(), row, col);
            case KNIGHT -> new Knight(pawn.getColor(), row, col);
            default -> new Queen(pawn.getColor(), row, col);
        };

        square.setPiece(promotedPiece);
        promotedPiece.setMoved(true);

        if (pawn.getColor() == PieceColor.WHITE) {
            whitePieces.add(promotedPiece);
        } else {
            blackPieces.add(promotedPiece);
        }
    }

    private void performCastling(Move move) {
        int rookFromCol = move.getToCol() > move.getFromCol() ? 7 : 0;
        int rookToCol = move.getToCol() > move.getFromCol() ? move.getToCol() - 1 : move.getToCol() + 1;

        Square rookFromSquare = getSquare(move.getFromRow(), rookFromCol);
        Square rookToSquare = getSquare(move.getFromRow(), rookToCol);

        Piece rook = rookFromSquare.getPiece();
        rookFromSquare.setPiece(null);
        rookToSquare.setPiece(rook);
        rook.setMoved(true);
    }

    private void performEnPassant(Move move) {
        int capturedRow = move.getFromRow();
        int capturedCol = move.getToCol();
        Square capturedSquare = getSquare(capturedRow, capturedCol);
        Piece capturedPawn = capturedSquare.getPiece();

        if (capturedPawn != null) {
            removePiece(capturedPawn);
            capturedSquare.setPiece(null);
        }
    }

    private void removePiece(Piece piece) {
        if (piece.getColor() == PieceColor.WHITE) {
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
    }

    public List<Piece> getPieces(PieceColor color) {
        return color == PieceColor.WHITE ? new ArrayList<>(whitePieces) : new ArrayList<>(blackPieces);
    }

    public King getKing(PieceColor color) {
        List<Piece> pieces = getPieces(color);
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }

    public boolean isSquareUnderAttack(int row, int col, PieceColor attackingColor) {
        List<Piece> attackers = getPieces(attackingColor);
        for (Piece attacker : attackers) {
            if (attacker instanceof King) {
                int rowDiff = Math.abs(attacker.getRow() - row);
                int colDiff = Math.abs(attacker.getCol() - col);
                if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)) {
                    return true;
                }
            } else {
                List<Move> moves = attacker.calculateLegalMoves(this);
                for (Move move : moves) {
                    if (move.getToRow() == row && move.getToCol() == col) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheck(PieceColor color) {
        King king = getKing(color);
        if (king == null) {
            return false;
        }
        return isSquareUnderAttack(king.getRow(), king.getCol(), color.opposite());
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Board copy() {
        Square[][] newSquares = new Square[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        List<Piece> newWhitePieces = new ArrayList<>();
        List<Piece> newBlackPieces = new ArrayList<>();

        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                newSquares[row][col] = new Square(row, col);
            }
        }

        for (Piece piece : whitePieces) {
            Piece copiedPiece = piece.copy();
            newSquares[copiedPiece.getRow()][copiedPiece.getCol()].setPiece(copiedPiece);
            newWhitePieces.add(copiedPiece);
        }

        for (Piece piece : blackPieces) {
            Piece copiedPiece = piece.copy();
            newSquares[copiedPiece.getRow()][copiedPiece.getCol()].setPiece(copiedPiece);
            newBlackPieces.add(copiedPiece);
        }

        return new Board(newSquares, newWhitePieces, newBlackPieces, lastMove);
    }
}
