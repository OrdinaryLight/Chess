# Chess Game

A desktop chess game built with JavaFX, featuring single-player AI with multiple difficulty levels and local two-player mode.

## Features

- **Single-player mode** against AI with Easy, Medium, and Hard difficulty levels
- **Two-player local mode** on the same device
- **Choose your color** (Black or White) when playing against AI
- **Three beautiful themes**: Light, Dark, and Wood
- **Clean, polished UI** with smooth animations
- **Full chess rules** including castling, en passant, and pawn promotion

## Requirements

- **Java 17 or higher**
- No additional setup required (Gradle wrapper included)

## How to Run

### Option 1: Using Gradle (Recommended)

**On Windows:**
```bash
gradlew.bat run
```

**On Mac/Linux:**
```bash
./gradlew run
```

### Option 2: Build and Run JAR

**Build the application:**
```bash
./gradlew build
```

**Run the JAR:**
```bash
java -jar build/libs/chess-game-1.0-SNAPSHOT.jar
```

## How to Play

1. **Start the game** - The application will open to the main menu
2. **Choose game mode:**
   - **1 Player**: Play against AI
   - **2 Player**: Play locally with another person
3. **For single-player:**
   - Select your color (White or Black)
   - Choose AI difficulty (Easy, Medium, or Hard)
   - Click "Start Game"
4. **Gameplay:**
   - Click on a piece to select it (highlighted squares show legal moves)
   - Click on a highlighted square to move
   - The status bar shows whose turn it is and check status

## Settings

- Access settings from the main menu
- Change theme (Light, Dark, or Wood)
- Settings are preserved between sessions

## Project Structure

```
chess-game/
├── src/main/java/com/example/chess/
│   ├── app/              # Application entry point and scene management
│   ├── controller/       # JavaFX controllers for UI screens
│   ├── model/            # Game logic (board, pieces, game state)
│   ├── ai/               # AI engine with minimax algorithm
│   ├── view/             # Custom UI components
│   └── util/             # Constants and utilities
├── src/main/resources/
│   ├── fxml/             # FXML layout files
│   ├── styles/           # CSS theme files
│   └── images/           # (Reserved for piece images)
└── build.gradle          # Build configuration
```

## Architecture

The game follows clean OOP principles with clear separation of concerns:

- **Model**: Game logic is UI-agnostic and fully testable
- **View**: JavaFX UI components for rendering
- **Controller**: Mediates between model and view
- **AI**: Isolated minimax engine with alpha-beta pruning

## Tech Stack

- **Java 17+**
- **JavaFX 21** for UI
- **Gradle 8.5** for build management

## Development

To build the project:
```bash
./gradlew build
```

To run tests:
```bash
./gradlew test
```

## License

This project is open source and available for all purposes.
