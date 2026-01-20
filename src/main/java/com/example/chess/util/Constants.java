package com.example.chess.util;

public class Constants {
    public static final int BOARD_SIZE = 8;
    public static final int SQUARE_SIZE = 80;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    public static final String APP_TITLE = "Chess Game";
    public static final String APP_VERSION = "1.0";

    public static final int AI_EASY_DEPTH = 1;
    public static final int AI_MEDIUM_DEPTH = 3;
    public static final int AI_HARD_DEPTH = 5;

    public static final int PAWN_VALUE = 100;
    public static final int KNIGHT_VALUE = 320;
    public static final int BISHOP_VALUE = 330;
    public static final int ROOK_VALUE = 500;
    public static final int QUEEN_VALUE = 900;
    public static final int KING_VALUE = 20000;

    private Constants() {
    }
}
