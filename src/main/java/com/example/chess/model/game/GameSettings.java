package com.example.chess.model.game;

import com.example.chess.ai.Difficulty;
import com.example.chess.model.piece.PieceColor;

public class GameSettings {
    private GameMode gameMode;
    private PieceColor humanPlayerColor;
    private Difficulty aiDifficulty;
    private String theme;

    public GameSettings() {
        this.gameMode = GameMode.SINGLE_PLAYER;
        this.humanPlayerColor = PieceColor.WHITE;
        this.aiDifficulty = Difficulty.MEDIUM;
        this.theme = "light";
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public PieceColor getHumanPlayerColor() {
        return humanPlayerColor;
    }

    public void setHumanPlayerColor(PieceColor humanPlayerColor) {
        this.humanPlayerColor = humanPlayerColor;
    }

    public Difficulty getAiDifficulty() {
        return aiDifficulty;
    }

    public void setAiDifficulty(Difficulty aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
