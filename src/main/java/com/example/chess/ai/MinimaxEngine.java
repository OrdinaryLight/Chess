package com.example.chess.ai;

import com.example.chess.model.board.Board;
import com.example.chess.model.board.Move;
import com.example.chess.model.piece.Piece;
import com.example.chess.model.piece.PieceColor;

import java.util.List;

public class MinimaxEngine {

    public static Move findBestMove(Board board, PieceColor color, int depth) {
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        List<Piece> pieces = board.getPieces(color);
        for (Piece piece : pieces) {
            List<Move> moves = piece.calculateLegalMoves(board);

            for (Move move : moves) {
                Board testBoard = board.copy();
                testBoard.makeMove(move);

                if (testBoard.isInCheck(color)) {
                    continue;
                }

                int boardValue = minimax(testBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, color);

                if (boardValue > bestValue) {
                    bestValue = boardValue;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    private static int minimax(Board board, int depth, int alpha, int beta, boolean maximizing, PieceColor aiColor) {
        if (depth == 0) {
            return EvaluationFunction.evaluate(board, aiColor);
        }

        PieceColor currentColor = maximizing ? aiColor : aiColor.opposite();
        List<Piece> pieces = board.getPieces(currentColor);

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Piece piece : pieces) {
                List<Move> moves = piece.calculateLegalMoves(board);

                for (Move move : moves) {
                    Board testBoard = board.copy();
                    testBoard.makeMove(move);

                    if (testBoard.isInCheck(currentColor)) {
                        continue;
                    }

                    int eval = minimax(testBoard, depth - 1, alpha, beta, false, aiColor);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);

                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Piece piece : pieces) {
                List<Move> moves = piece.calculateLegalMoves(board);

                for (Move move : moves) {
                    Board testBoard = board.copy();
                    testBoard.makeMove(move);

                    if (testBoard.isInCheck(currentColor)) {
                        continue;
                    }

                    int eval = minimax(testBoard, depth - 1, alpha, beta, true, aiColor);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);

                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return minEval;
        }
    }
}
