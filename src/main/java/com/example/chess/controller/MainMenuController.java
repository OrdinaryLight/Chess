package com.example.chess.controller;

import com.example.chess.ai.Difficulty;
import com.example.chess.app.SceneManager;
import com.example.chess.model.game.GameMode;
import com.example.chess.model.game.GameSettings;
import com.example.chess.model.piece.PieceColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MainMenuController {

    @FXML private Button singlePlayerButton;
    @FXML private Button twoPlayerButton;
    @FXML private Button settingsButton;
    @FXML private Button exitButton;
    @FXML private VBox gameSetupPanel;
    @FXML private RadioButton whiteRadio;
    @FXML private RadioButton blackRadio;
    @FXML private ComboBox<String> difficultyCombo;
    @FXML private Button startGameButton;
    @FXML private Button backButton;

    private final SceneManager sceneManager = SceneManager.getInstance();
    private final GameSettings gameSettings = sceneManager.getGameSettings();

    @FXML
    public void initialize() {
        gameSetupPanel.setVisible(false);
        gameSetupPanel.setManaged(false);

        ToggleGroup colorGroup = new ToggleGroup();
        whiteRadio.setToggleGroup(colorGroup);
        blackRadio.setToggleGroup(colorGroup);
        whiteRadio.setSelected(true);

        if (difficultyCombo != null) {
            difficultyCombo.getItems().addAll("Easy", "Medium", "Hard");
            difficultyCombo.setValue("Medium");
        }
    }

    @FXML
    private void onSinglePlayer() {
        gameSettings.setGameMode(GameMode.SINGLE_PLAYER);
        showGameSetup(true);
    }

    @FXML
    private void onTwoPlayer() {
        gameSettings.setGameMode(GameMode.TWO_PLAYER);
        sceneManager.showGame();
    }

    @FXML
    private void onSettings() {
        sceneManager.showSettings();
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }

    @FXML
    private void onStartGame() {
        if (gameSettings.getGameMode() == GameMode.SINGLE_PLAYER) {
            PieceColor playerColor = whiteRadio.isSelected() ? PieceColor.WHITE : PieceColor.BLACK;
            gameSettings.setHumanPlayerColor(playerColor);

            String difficultyStr = difficultyCombo.getValue();
            Difficulty difficulty = switch (difficultyStr) {
                case "Easy" -> Difficulty.EASY;
                case "Hard" -> Difficulty.HARD;
                default -> Difficulty.MEDIUM;
            };
            gameSettings.setAiDifficulty(difficulty);
        }

        sceneManager.showGame();
    }

    @FXML
    private void onBack() {
        showGameSetup(false);
    }

    private void showGameSetup(boolean show) {
        singlePlayerButton.setVisible(!show);
        singlePlayerButton.setManaged(!show);
        twoPlayerButton.setVisible(!show);
        twoPlayerButton.setManaged(!show);
        settingsButton.setVisible(!show);
        settingsButton.setManaged(!show);
        exitButton.setVisible(!show);
        exitButton.setManaged(!show);

        gameSetupPanel.setVisible(show);
        gameSetupPanel.setManaged(show);
    }
}
