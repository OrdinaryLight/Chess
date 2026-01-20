package com.example.chess.app;

import com.example.chess.model.game.GameSettings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private final GameSettings gameSettings;

    private SceneManager() {
        this.gameSettings = new GameSettings();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void showMainMenu() {
        loadScene("main_menu.fxml", "Chess Game - Main Menu");
    }

    public void showGame() {
        loadScene("game.fxml", "Chess Game");
    }

    public void showSettings() {
        loadScene("settings.fxml", "Settings");
    }

    private void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();

            String theme = gameSettings.getTheme();
            String cssFile = "/styles/" + theme + "-theme.css";
            String css = getClass().getResource(cssFile).toExternalForm();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
