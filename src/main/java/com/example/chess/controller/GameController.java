package com.example.chess.controller;

import com.example.chess.ai.Difficulty;
import com.example.chess.app.SceneManager;
import com.example.chess.model.board.Move;
import com.example.chess.model.game.GameMode;
import com.example.chess.model.game.GameResult;
import com.example.chess.model.game.GameSettings;
import com.example.chess.model.game.GameState;
import com.example.chess.model.piece.Piece;
import com.example.chess.model.piece.PieceColor;
import com.example.chess.model.piece.PieceType;
import com.example.chess.model.player.AIPlayer;
import com.example.chess.model.player.HumanPlayer;
import com.example.chess.model.player.Player;
import com.example.chess.view.BoardView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Optional;

import java.util.List;

public class GameController {

    @FXML private BorderPane root;
    @FXML private BoardView boardView;
    @FXML private Text statusText;
    @FXML private Button menuButton;

    private final SceneManager sceneManager = SceneManager.getInstance();
    private final GameSettings gameSettings = sceneManager.getGameSettings();
    private GameState gameState;
    private Piece selectedPiece;

    @FXML
    public void initialize() {
        initializeGame();
        boardView.setOnSquareClick(this::handleSquareClick);
        updateDisplay();

        if (!gameState.getCurrentPlayer().isHuman()) {
            makeAIMove();
        }
    }

    private void initializeGame() {
        gameState = new GameState();

        Player whitePlayer;
        Player blackPlayer;

        if (gameSettings.getGameMode() == GameMode.SINGLE_PLAYER) {
            PieceColor humanColor = gameSettings.getHumanPlayerColor();
            Difficulty aiDifficulty = gameSettings.getAiDifficulty();

            if (humanColor == PieceColor.WHITE) {
                whitePlayer = new HumanPlayer(PieceColor.WHITE);
                blackPlayer = new AIPlayer(PieceColor.BLACK, aiDifficulty);
            } else {
                whitePlayer = new AIPlayer(PieceColor.WHITE, aiDifficulty);
                blackPlayer = new HumanPlayer(PieceColor.BLACK);
            }
        } else {
            whitePlayer = new HumanPlayer(PieceColor.WHITE);
            blackPlayer = new HumanPlayer(PieceColor.BLACK);
        }

        gameState.setPlayers(whitePlayer, blackPlayer);
    }

    private void handleSquareClick(BoardView.SquareClick click) {
        if (gameState.isGameOver()) {
            return;
        }

        if (!gameState.getCurrentPlayer().isHuman()) {
            return;
        }

        int row = click.getRow();
        int col = click.getCol();

        Piece clickedPiece = gameState.getBoard().getPiece(row, col);

        if (selectedPiece == null) {
            if (clickedPiece != null && clickedPiece.getColor() == gameState.getCurrentTurn()) {
                selectedPiece = clickedPiece;
                boardView.setSelectedSquare(row, col);
                List<Move> legalMoves = gameState.getLegalMovesForPiece(selectedPiece);
                boardView.highlightLegalMoves(legalMoves);
                updateDisplay();
            }
        } else {
            List<Move> legalMoves = gameState.getLegalMovesForPiece(selectedPiece);
            Move selectedMove = null;

            for (Move move : legalMoves) {
                if (move.getToRow() == row && move.getToCol() == col) {
                    selectedMove = move;
                    break;
                }
            }

            if (selectedMove != null) {
                gameState.makeMove(selectedMove);

                if (selectedMove.isPromotion()) {
                    handlePromotion(selectedMove);
                }

                selectedPiece = null;
                boardView.clearSelection();
                updateDisplay();

                if (!gameState.isGameOver() && !gameState.getCurrentPlayer().isHuman()) {
                    Platform.runLater(this::makeAIMove);
                }
            } else {
                selectedPiece = null;
                boardView.clearSelection();
                updateDisplay();

                if (clickedPiece != null && clickedPiece.getColor() == gameState.getCurrentTurn()) {
                    handleSquareClick(click);
                }
            }
        }

        checkGameOver();
    }

    private void makeAIMove() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Move aiMove = gameState.getCurrentPlayer().getMove(gameState);

            if (aiMove != null) {
                Platform.runLater(() -> {
                    gameState.makeMove(aiMove);

                    if (aiMove.isPromotion()) {
                        gameState.getBoard().promotePawn(aiMove.getToRow(), aiMove.getToCol(), PieceType.QUEEN);
                    }

                    updateDisplay();
                    checkGameOver();
                });
            }
        }).start();
    }

    private void updateDisplay() {
        boardView.updateBoard(gameState.getBoard());

        String turn = gameState.getCurrentTurn() == PieceColor.WHITE ? "White" : "Black";
        String status = "Current Turn: " + turn;

        if (gameState.getBoard().isInCheck(gameState.getCurrentTurn())) {
            status += " (Check!)";
        }

        statusText.setText(status);
    }

    private void checkGameOver() {
        if (gameState.isGameOver()) {
            String message = switch (gameState.getStatus()) {
                case CHECKMATE -> {
                    PieceColor winner = gameState.getCurrentTurn().opposite();
                    yield (winner == PieceColor.WHITE ? "White" : "Black") + " wins by checkmate!";
                }
                case STALEMATE -> "Stalemate! The game is a draw.";
                case DRAW -> "Draw!";
                default -> "Game over!";
            };

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    private void handlePromotion(Move move) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", "Queen", "Rook", "Bishop", "Knight");
        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Your pawn reached the end!");
        dialog.setContentText("Choose piece to promote to:");

        Optional<String> result = dialog.showAndWait();

        PieceType promotionType = PieceType.QUEEN;
        if (result.isPresent()) {
            promotionType = switch (result.get()) {
                case "Rook" -> PieceType.ROOK;
                case "Bishop" -> PieceType.BISHOP;
                case "Knight" -> PieceType.KNIGHT;
                default -> PieceType.QUEEN;
            };
        }

        gameState.getBoard().promotePawn(move.getToRow(), move.getToCol(), promotionType);
    }

    @FXML
    private void onMenuButton() {
        sceneManager.showMainMenu();
    }
}
