package com.example.chess.view;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.piece.Piece;
import com.example.chess.model.piece.PieceType;
import com.example.chess.util.Constants;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.function.Consumer;

public class BoardView extends GridPane {
    private final Rectangle[][] squares = new Rectangle[8][8];
    private final Text[][] pieceTexts = new Text[8][8];
    private Consumer<SquareClick> onSquareClick;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private List<Move> highlightedMoves = null;

    public BoardView() {
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle square = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
                square.getStyleClass().add((row + col) % 2 == 0 ? "light-square" : "dark-square");

                int finalRow = row;
                int finalCol = col;
                square.setOnMouseClicked(e -> handleSquareClick(finalRow, finalCol));

                squares[row][col] = square;
                add(square, col, row);

                Text pieceText = new Text("");
                pieceText.setFont(Font.font(48));
                pieceText.setMouseTransparent(true);
                pieceTexts[row][col] = pieceText;
                add(pieceText, col, row);
                GridPane.setHalignment(pieceText, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(pieceText, javafx.geometry.VPos.CENTER);
            }
        }
    }

    public void updateBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                Text text = pieceTexts[row][col];

                if (piece == null) {
                    text.setText("");
                } else {
                    text.setText(getPieceSymbol(piece));
                    if (piece.getColor() == com.example.chess.model.piece.PieceColor.WHITE) {
                        text.setFill(Color.rgb(255, 255, 255));
                        text.setStroke(Color.rgb(50, 50, 50));
                        text.setStrokeWidth(1.5);
                    } else {
                        text.setFill(Color.rgb(30, 30, 30));
                        text.setStroke(Color.rgb(200, 200, 200));
                        text.setStrokeWidth(0.5);
                    }
                }

                squares[row][col].getStyleClass().removeAll("selected-square", "legal-move-square");
                squares[row][col].getStyleClass().add((row + col) % 2 == 0 ? "light-square" : "dark-square");
            }
        }

        if (selectedRow >= 0 && selectedCol >= 0) {
            squares[selectedRow][selectedCol].getStyleClass().add("selected-square");
        }

        if (highlightedMoves != null) {
            for (Move move : highlightedMoves) {
                squares[move.getToRow()][move.getToCol()].getStyleClass().add("legal-move-square");
            }
        }
    }

    private String getPieceSymbol(Piece piece) {
        return switch (piece.getType()) {
            case KING -> "♔";
            case QUEEN -> "♕";
            case ROOK -> "♖";
            case BISHOP -> "♗";
            case KNIGHT -> "♘";
            case PAWN -> "♙";
        };
    }

    private void handleSquareClick(int row, int col) {
        if (onSquareClick != null) {
            onSquareClick.accept(new SquareClick(row, col));
        }
    }

    public void setOnSquareClick(Consumer<SquareClick> handler) {
        this.onSquareClick = handler;
    }

    public void setSelectedSquare(int row, int col) {
        this.selectedRow = row;
        this.selectedCol = col;
    }

    public void clearSelection() {
        this.selectedRow = -1;
        this.selectedCol = -1;
        this.highlightedMoves = null;
    }

    public void highlightLegalMoves(List<Move> moves) {
        this.highlightedMoves = moves;
    }

    public static class SquareClick {
        private final int row;
        private final int col;

        public SquareClick(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}
