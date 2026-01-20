package com.example.chess.ai;

import com.example.chess.model.board.Board;
import com.example.chess.model.piece.Piece;
import com.example.chess.model.piece.PieceColor;

import java.util.List;

public class EvaluationFunction {

    public static int evaluate(Board board, PieceColor color) {
        int score = 0;

        score += evaluateMaterial(board, color);
        score += evaluateMobility(board, color);

        return score;
    }

    private static int evaluateMaterial(Board board, PieceColor color) {
        int score = 0;

        List<Piece> myPieces = board.getPieces(color);
        List<Piece> opponentPieces = board.getPieces(color.opposite());

        for (Piece piece : myPieces) {
            score += piece.getValue();
        }

        for (Piece piece : opponentPieces) {
            score -= piece.getValue();
        }

        return score;
    }

    private static int evaluateMobility(Board board, PieceColor color) {
        int myMoves = 0;
        int opponentMoves = 0;

        List<Piece> myPieces = board.getPieces(color);
        for (Piece piece : myPieces) {
            myMoves += piece.calculateLegalMoves(board).size();
        }

        List<Piece> opponentPieces = board.getPieces(color.opposite());
        for (Piece piece : opponentPieces) {
            opponentMoves += piece.calculateLegalMoves(board).size();
        }

        return (myMoves - opponentMoves) * 10;
    }
}
