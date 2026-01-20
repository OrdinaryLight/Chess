package com.example.chess.view;

public enum Theme {
    LIGHT("light-theme"),
    DARK("dark-theme"),
    WOOD("wood-theme");

    private final String cssFile;

    Theme(String cssFile) {
        this.cssFile = cssFile;
    }

    public String getCssFile() {
        return cssFile + ".css";
    }
}
