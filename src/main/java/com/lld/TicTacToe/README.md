# TicTacToe Game - Design Documentation

## Overview
A simple implementation of the classic TicTacToe game demonstrating object-oriented design principles including inheritance, encapsulation, and strategy pattern.

---

## UML Class Diagram

```
┌─────────────────────────┐
│   <<enumeration>>       │
│     GameStatus          │
├─────────────────────────┤
│ + DRAW                  │
│ + WIN                   │
└─────────────────────────┘

┌─────────────────────────┐
│   <<enumeration>>       │
│      PieceType          │
├─────────────────────────┤
│ + X                     │
│ + O                     │
└─────────────────────────┘

                    ┌─────────────────────────┐
                    │    PlayingPiece         │
                    ├─────────────────────────┤
                    │ + pieceType: PieceType  │
                    ├─────────────────────────┤
                    │ + PlayingPiece(type)    │
                    └─────────────────────────┘
                              △
                              │
                    ┌─────────┴─────────┐
                    │                   │
        ┌───────────────────┐  ┌───────────────────┐
        │  PlayingPieceX    │  │  PlayingPieceO    │
        ├───────────────────┤  ├───────────────────┤
        ├───────────────────┤  ├───────────────────┤
        │ + PlayingPieceX() │  │ + PlayingPieceO() │
        └───────────────────┘  └───────────────────┘
              IS-A                    IS-A

┌────────────────────────────────────┐
│           Player                   │
├────────────────────────────────────┤
│ + name: String                     │
│ + playingPiece: PlayingPiece       │──────► HAS-A PlayingPiece
├────────────────────────────────────┤
│ + Player(name, piece)              │
│ + getName(): String                │
│ + setName(name): void              │
│ + getPlayingPiece(): PlayingPiece  │
│ + setPlayingPiece(piece): void     │
└────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│              Board                          │
├─────────────────────────────────────────────┤
│ + size: int                                 │
│ + board: PlayingPiece[][]                   │──────► HAS-A PlayingPiece[][]
├─────────────────────────────────────────────┤
│ + Board(size)                               │
│ + addPiece(row, col, piece): boolean        │
│ + getFreeCells(): List<Pair<Integer,Int>>   │
│ + printBoard(): void                        │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│         TicTacToeGame                       │
├─────────────────────────────────────────────┤
│ - players: Deque<Player>                    │──────► HAS-A Deque<Player>
│ - gameBoard: Board                          │──────► HAS-A Board
│ - winner: Player                            │──────► HAS-A Player (reference)
├─────────────────────────────────────────────┤
│ + initializeGame(): void                    │
│ + startGame(): GameStatus                   │
│ + checkForWinner(row, col, type): boolean   │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│           PlayGame (Main)                   │
├─────────────────────────────────────────────┤
│ + main(args): void                          │──────► USES TicTacToeGame
└─────────────────────────────────────────────┘
```

---

## Relationships

### IS-A (Inheritance) Relationships
1. **PlayingPieceX IS-A PlayingPiece**
   - Extends the base `PlayingPiece` class
   - Represents X piece type
   
2. **PlayingPieceO IS-A PlayingPiece**
   - Extends the base `PlayingPiece` class
   - Represents O piece type

### HAS-A (Composition/Aggregation) Relationships
1. **Player HAS-A PlayingPiece**
   - Each player owns a playing piece (X or O)
   
2. **Board HAS-A PlayingPiece[][]**
   - Board contains a 2D array of playing pieces
   
3. **TicTacToeGame HAS-A Deque<Player>**
   - Game manages a queue of players for turn management
   
4. **TicTacToeGame HAS-A Board**
   - Game owns and manages the game board
   
5. **TicTacToeGame HAS-A Player (winner)**
   - Game maintains reference to winning player

---

## Class Details

### 1. GameStatus (Enum)
**Purpose:** Represents the final state of the game

**Values:**
- `DRAW` - Game ended in a draw
- `WIN` - Game ended with a winner

---

### 2. PieceType (Enum)
**Purpose:** Represents the type of playing piece

**Values:**
- `X` - Cross piece
- `O` - Nought piece

---

### 3. PlayingPiece (Abstract Base Class)
**Purpose:** Base class for all playing pieces

**Attributes:**
- `pieceType: PieceType` - Type of the piece (X or O)

**Methods:**
- `PlayingPiece(PieceType pieceType)` - Constructor to initialize piece type

**Design Pattern:** Template for concrete piece implementations

---

### 4. PlayingPieceX (Concrete Class)
**Purpose:** Represents the X piece

**Relationship:** IS-A PlayingPiece

**Methods:**
- `PlayingPieceX()` - Constructor that sets piece type to X

---

### 5. PlayingPieceO (Concrete Class)
**Purpose:** Represents the O piece

**Relationship:** IS-A PlayingPiece

**Methods:**
- `PlayingPieceO()` - Constructor that sets piece type to O

---

### 6. Player (Entity Class)
**Purpose:** Represents a player in the game

**Attributes:**
- `name: String` - Player's name
- `playingPiece: PlayingPiece` - The piece this player uses (X or O)

**Methods:**
- `Player(String name, PlayingPiece playingPiece)` - Constructor
- `getName(): String` - Returns player name
- `setName(String name): void` - Sets player name
- `getPlayingPiece(): PlayingPiece` - Returns player's piece
- `setPlayingPiece(PlayingPiece playingPiece): void` - Sets player's piece

**Relationships:**
- HAS-A PlayingPiece

---

### 7. Board (Game Board Manager)
**Purpose:** Manages the game board state and operations

**Attributes:**
- `size: int` - Size of the board (typically 3x3)
- `board: PlayingPiece[][]` - 2D array representing the game board

**Methods:**
- `Board(int size)` - Constructor to initialize board with given size
- `addPiece(int row, int column, PlayingPiece playingPiece): boolean` 
  - Adds a piece to the board at specified position
  - Returns `true` if placement successful, `false` if cell already occupied
- `getFreeCells(): List<Pair<Integer, Integer>>` 
  - Returns list of available cells as (row, column) pairs
- `printBoard(): void` 
  - Prints current board state to console

**Relationships:**
- HAS-A PlayingPiece[][] (composition)

---

### 8. TicTacToeGame (Game Controller)
**Purpose:** Main game logic and flow controller

**Attributes:**
- `players: Deque<Player>` - Queue of players for turn management
- `gameBoard: Board` - The game board
- `winner: Player` - Reference to the winning player (null if no winner yet)

**Methods:**
- `initializeGame(): void`
  - Initializes the game with 2 players
  - Creates a 3x3 board
  - Sets up player pieces (X and O)
  
- `startGame(): GameStatus`
  - Main game loop
  - Manages player turns
  - Validates moves
  - Checks for winner after each move
  - Returns final game status (WIN or DRAW)
  
- `checkForWinner(int row, int column, PieceType pieceType): boolean`
  - Checks if the last move resulted in a win
  - Validates row, column, diagonal, and anti-diagonal
  - Returns `true` if winning condition met

**Relationships:**
- HAS-A Deque<Player> (manages players)
- HAS-A Board (owns game board)
- HAS-A Player (winner reference)

**Game Flow:**
1. Players added to queue
2. Remove first player for their turn
3. Display board and get player input
4. Validate and place piece
5. Check for winner
6. Add player back to queue
7. Repeat until winner or draw

---

### 9. PlayGame (Main Entry Point)
**Purpose:** Application entry point and game orchestrator

**Methods:**
- `main(String[] args): void`
  - Creates TicTacToeGame instance
  - Initializes game
  - Starts game loop
  - Displays final result (WIN with winner name or DRAW)

**Relationships:**
- USES TicTacToeGame

---

## Design Patterns Used

### 1. **Strategy Pattern**
- Different piece types (X, O) can be extended easily
- Inheritance hierarchy allows for easy addition of new piece types

### 2. **Queue Pattern**
- Uses `Deque<Player>` for turn management
- Players are rotated in a FIFO manner

### 3. **Single Responsibility Principle**
- Each class has one clear responsibility:
  - `Board` - Manages board state
  - `Player` - Represents player entity
  - `TicTacToeGame` - Game logic and flow
  - `PlayGame` - Application entry point

---

## Object Interaction Flow

```
PlayGame.main()
    │
    ├──> Creates TicTacToeGame
    │
    ├──> TicTacToeGame.initializeGame()
    │    │
    │    ├──> Creates PlayingPieceX (Player1)
    │    ├──> Creates PlayingPieceO (Player2)
    │    ├──> Creates Player objects with pieces
    │    └──> Creates Board(3)
    │
    ├──> TicTacToeGame.startGame()
    │    │
    │    └──> Game Loop:
    │         ├──> players.removeFirst() → Current Player
    │         ├──> gameBoard.printBoard()
    │         ├──> gameBoard.getFreeCells()
    │         ├──> Read user input (row, column)
    │         ├──> gameBoard.addPiece(row, col, piece)
    │         ├──> checkForWinner(row, col, pieceType)
    │         └──> players.addLast() → Add player back
    │
    └──> Display GameStatus result
```

---

## Key Features

1. **Extensibility**: Easy to add new piece types by extending `PlayingPiece`
2. **Turn Management**: Deque-based player rotation ensures fair turns
3. **Input Validation**: Checks for valid moves and occupied cells
4. **Win Detection**: Efficient row, column, and diagonal checking
5. **Draw Detection**: Automatically detects when board is full

---

## Example Usage

```java
TicTacToeGame game = new TicTacToeGame();
game.initializeGame();  // Creates players and board
GameStatus status = game.startGame();  // Starts game loop

// Players take turns entering coordinates
// Game validates moves and checks for winner
// Returns GameStatus.WIN or GameStatus.DRAW
```

---

## Future Enhancements

1. Add variable board size support
2. Implement AI player strategy
3. Add score tracking across multiple games
4. Support for more than 2 players
5. Graphical user interface
6. Network multiplayer support
