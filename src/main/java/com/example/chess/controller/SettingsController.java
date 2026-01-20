package com.example.chess.controller;

import com.example.chess.app.SceneManager;
import com.example.chess.model.game.GameSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SettingsController {

    @FXML private ComboBox<String> themeCombo;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    private final SceneManager sceneManager = SceneManager.getInstance();
    private final GameSettings gameSettings = sceneManager.getGameSettings();

    @FXML
    public void initialize() {
        themeCombo.getItems().addAll("light", "dark", "wood");
        themeCombo.setValue(gameSettings.getTheme());
    }

    @FXML
    private void onSave() {
        gameSettings.setTheme(themeCombo.getValue());
        sceneManager.showMainMenu();
    }

    @FXML
    private void onBack() {
        sceneManager.showMainMenu();
    }
}
