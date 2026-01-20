package com.example.chess.model.game;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.piece.King;
import com.example.chess.model.piece.Piece;
import com.example.chess.model.piece.PieceColor;
import com.example.chess.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final Board board;
    private PieceColor currentTurn;
    private final List<Move> moveHistory;
    private GameResult.GameStatus status;
    private Player whitePlayer;
    private Player blackPlayer;

    public GameState() {
        this.board = new Board();
        this.currentTurn = PieceColor.WHITE;
        this.moveHistory = new ArrayList<>();
        this.status = GameResult.GameStatus.IN_PROGRESS;
    }

    public void setPlayers(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public boolean makeMove(Move move) {
        if (status != GameResult.GameStatus.IN_PROGRESS) {
            return false;
        }

        if (!isMoveLegal(move)) {
            return false;
        }

        board.makeMove(move);
        moveHistory.add(move);
        currentTurn = currentTurn.opposite();

        updateGameStatus();
        return true;
    }

    private boolean isMoveLegal(Move move) {
        Board testBoard = board.copy();
        testBoard.makeMove(move);

        PieceColor movingColor = move.getPiece().getColor();
        return !testBoard.isInCheck(movingColor);
    }

    public List<Move> getLegalMovesForPiece(Piece piece) {
        if (piece.getColor() != currentTurn) {
            return new ArrayList<>();
        }

        List<Move> potentialMoves = piece.calculateLegalMoves(board);
        List<Move> legalMoves = new ArrayList<>();

        for (Move move : potentialMoves) {
            if (isMoveLegal(move)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public List<Move> getAllLegalMoves(PieceColor color) {
        List<Move> allMoves = new ArrayList<>();
        List<Piece> pieces = board.getPieces(color);

        for (Piece piece : pieces) {
            allMoves.addAll(getLegalMovesForPiece(piece));
        }

        return allMoves;
    }

    private void updateGameStatus() {
        List<Move> legalMoves = getAllLegalMoves(currentTurn);

        if (legalMoves.isEmpty()) {
            if (board.isInCheck(currentTurn)) {
                status = GameResult.GameStatus.CHECKMATE;
            } else {
                status = GameResult.GameStatus.STALEMATE;
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public List<Move> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }

    public GameResult.GameStatus getStatus() {
        return status;
    }

    public boolean isGameOver() {
        return status != GameResult.GameStatus.IN_PROGRESS;
    }

    public Player getCurrentPlayer() {
        return currentTurn == PieceColor.WHITE ? whitePlayer : blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }
}
