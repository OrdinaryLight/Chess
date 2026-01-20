# Product Requirements Document (PRD)

**Project:** JavaFX Chess Game  
**Tech Stack:** Java 17+, JavaFX

---

## 1. Product Overview

This project is a **desktop chess game built in Java using JavaFX**, designed with clean object-oriented principles and a focus on maintainability, extensibility, and user experience.

The application supports:

- **Single-player** games against an AI with multiple difficulty levels
    
- **Two-player (local)** games on the same device
    
- Player choice of **Black or White**
    
- A polished, welcoming (“homely”) UI with theme support
    

The primary goal is to create a **well-architected chess engine + UI**, not just a playable game. Emphasis is placed on separation of concerns, testability, and clean design.

---

## 2. File Structure (OOP-Focused)

The project follows a **layered architecture** separating game logic, UI, and application state.

chess-game/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/chess/
│   │   │       ├── app/
│   │   │       │   ├── ChessApplication.java
│   │   │       │   └── SceneManager.java
│   │   │
│   │   │       ├── controller/
│   │   │       │   ├── MainMenuController.java
│   │   │       │   ├── GameController.java
│   │   │       │   └── SettingsController.java
│   │   │
│   │   │       ├── model/
│   │   │       │   ├── board/
│   │   │       │   │   ├── Board.java
│   │   │       │   │   ├── Square.java
│   │   │       │   │   └── Move.java
│   │   │       │   │
│   │   │       │   ├── piece/
│   │   │       │   │   ├── Piece.java
│   │   │       │   │   ├── Pawn.java
│   │   │       │   │   ├── Rook.java
│   │   │       │   │   ├── Knight.java
│   │   │       │   │   ├── Bishop.java
│   │   │       │   │   ├── Queen.java
│   │   │       │   │   └── King.java
│   │   │       │   │
│   │   │       │   ├── player/
│   │   │       │   │   ├── Player.java
│   │   │       │   │   ├── HumanPlayer.java
│   │   │       │   │   └── AIPlayer.java
│   │   │       │   │
│   │   │       │   └── game/
│   │   │       │       ├── GameState.java
│   │   │       │       ├── GameSettings.java
│   │   │       │       └── GameResult.java
│   │   │
│   │   │       ├── ai/
│   │   │       │   ├── ChessAI.java
│   │   │       │   ├── MinimaxEngine.java
│   │   │       │   ├── EvaluationFunction.java
│   │   │       │   └── Difficulty.java
│   │   │
│   │   │       ├── view/
│   │   │       │   ├── BoardView.java
│   │   │       │   ├── PieceView.java
│   │   │       │   └── Theme.java
│   │   │
│   │   │       └── util/
│   │   │           ├── Constants.java
│   │   │           └── SoundManager.java
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── main_menu.fxml
│   │       │   ├── game.fxml
│   │       │   └── settings.fxml
│   │       ├── styles/
│   │       │   ├── light-theme.css
│   │       │   ├── dark-theme.css
│   │       │   └── wood-theme.css
│   │       ├── images/
│   │       │   ├── pieces/
│   │       │   └── icons/
│   │       └── fonts/
│   │
├── README.md
└── build.gradle / pom.xml


**Key design choices**

- Game logic is **UI-agnostic**
    
- AI logic is isolated from controllers
    
- Controllers never mutate board state directly
    

---

## 3. Naming Patterns (Standard Java)

The project follows **Java naming conventions strictly**:

### Classes

- `PascalCase`
    
- Nouns for models (`Board`, `GameState`)
    
- Verbs avoided unless appropriate (`SceneManager`)
    

### Methods

- `camelCase`
    
- Verb-based (`calculateLegalMoves`, `makeMove`, `resetGame`)
    

### Packages

- All lowercase (`model.board`, `model.piece`)
    

### Constants

- `UPPER_SNAKE_CASE`
    
- Centralized in `Constants.java`
    

### Enums

- Singular nouns (`Difficulty`, `PieceColor`, `GameMode`)
    

---

## 4. UI Design (Homely & Polished)

The UI aims to feel **warm, modern, and calm**, not sterile or overly technical.

### Visual Style

- **Primary layout:** Centered board with soft margins and breathing room
    
- **Spacing:**
    
    - 16–24px padding around main containers
        
    - 8–12px spacing between UI controls
        
- **Corners:** Rounded buttons (6–10px radius)
    
- **Colors:** Muted backgrounds with high-contrast pieces
    

### Fonts

- **Primary:** Serif or humanist font (e.g., Playfair Display, Merriweather, or a JavaFX-friendly equivalent)
    
- **UI text:** Clean sans-serif fallback for buttons and menus
    
- **Font hierarchy:**
    
    - Title: Large, semi-bold
        
    - Buttons: Medium, regular
        
    - Secondary text: Smaller, muted
        

### Icons

- SVG or PNG icons for:
    
    - Play
        
    - Settings
        
    - Back / Exit
        
    - Player color (black / white)
        
- Icons paired with text (never icon-only for accessibility)
    

### Animations

- Subtle, fast animations only:
    
    - Piece movement: ~150ms slide
        
    - Hover states on buttons
        
    - Fade transitions between scenes
        
- No distracting effects or long animations
    

### Board Design

- Square board with:
    
    - Light & dark squares (theme-dependent)
        
    - Slight drop shadow
        
- Highlighting:
    
    - Selected piece
        
    - Legal moves
        
    - Last move made
        

### Theme Options

- **Light Theme** – clean, minimal
    
- **Dark Theme** – reduced eye strain
    
- **Wood Theme** – classic chessboard look
    

Themes affect:

- Board colors
    
- Background
    
- Highlight colors
    
- Font colors
    

---

## 5. Key Features & User Flow

### Core Features

- Single-player vs AI
    
- Two-player local mode
    
- AI difficulty levels:
    
    - Easy (random + basic capture logic)
        
    - Medium (depth-limited minimax)
        
    - Hard (deeper minimax + evaluation)
        
- Choose player color (Black or White)
    
- Pause / exit game
    
- Persistent settings
    

---

### User Flow

#### 1. Application Launch

- App opens to **Main Menu**
    
- Background music (optional, soft)
    

#### 2. Main Menu

Options:

- **1 Player**
    
- **2 Player**
    
- **Settings**
    
- **Exit**
    

#### 3. Game Setup

If **1 Player**:

- Choose:
    
    - Player color (White / Black)
        
    - AI difficulty
        

If **2 Player**:

- Confirm local multiplayer
    
- Default White starts
    

Selections are stored in `GameSettings`.

#### 4. Start Game

- Clicking **Start Game**:
    
    - Initializes `GameState`
        
    - Loads Game Scene
        
    - Applies saved theme and settings
        

#### 5. In-Game

- Board displayed
    
- Turn indicator
    
- Menu button available at all times:
    
    - Resume
        
    - Restart
        
    - Return to Main Menu
        

#### 6. Exit / Back to Menu

- On exit:
    
    - Current settings are preserved
        
    - Active game state is discarded (no resume mid-game)
        

---

## 6. Constraints (Guardrails)

To keep the project focused and manageable:

1. **No online multiplayer**
    
    - Local play only
        
2. **No advanced chess rules initially**
    
    - First version may exclude:
            
        - Threefold repetition
            
        - Fifty-move rule  
            (Can be added later)
            
3. **AI depth limits**
    
    - Maximum depth capped to avoid performance issues
        
4. **No database or accounts**
    
    - Settings stored in memory or simple config files
        
5. **UI must not control game logic**
    
    - Controllers communicate via services/models only
        
6. **Performance target**
    
    - All moves must resolve in <300ms on average hardware