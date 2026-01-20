package com.example.chess.app;

import com.example.chess.util.Constants;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChessApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(primaryStage);

        primaryStage.setWidth(Constants.WINDOW_WIDTH);
        primaryStage.setHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setResizable(false);

        sceneManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
