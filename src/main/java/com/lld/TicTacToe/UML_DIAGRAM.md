# TicTacToe - Complete UML Relationship Diagram

## Full System Architecture with Dependencies

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         TICTACTOE GAME SYSTEM                              ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────┐
│  <<enumeration>>    │
│    GameStatus       │
├─────────────────────┤
│ + DRAW              │
│ + WIN               │
└─────────────────────┘
          △
          │
          │ uses
          │
          │                    ┌─────────────────────┐
          │                    │  <<enumeration>>    │
          │                    │     PieceType       │
          │                    ├─────────────────────┤
          │                    │ + X                 │
          │                    │ + O                 │
          │                    └─────────────────────┘
          │                              △
          │                              │
          │                              │ uses
          │                              │
          │                    ┌─────────┴──────────────────┐
          │                    │                            │
          │          ┌─────────────────────────┐            │
          │          │   PlayingPiece          │            │
          │          ├─────────────────────────┤            │
          │          │ + pieceType: PieceType  │◄───────────┤
          │          ├─────────────────────────┤            │
          │          │ + PlayingPiece(type)    │            │
          │          └─────────────────────────┘            │
          │                      △                          │
          │                      │                          │
          │                      │ IS-A                     │
          │                      │                          │
          │          ┌───────────┴───────────┐              │
          │          │                       │              │
          │  ┌──────────────────┐   ┌──────────────────┐   │
          │  │ PlayingPieceX    │   │ PlayingPieceO    │   │
          │  ├──────────────────┤   ├──────────────────┤   │
          │  │ + PlayingPieceX()│   │ + PlayingPieceO()│   │
          │  └──────────────────┘   └──────────────────┘   │
          │          △                       △              │
          │          │                       │              │
          │          │ creates               │ creates      │
          │          │                       │              │
          │          └───────────┬───────────┘              │
          │                      │                          │
          │                      │                          │
          │          ┌───────────────────────────────┐      │
          │          │        Player                 │      │
          │          ├───────────────────────────────┤      │
          │          │ + name: String                │      │
          │          │ + playingPiece: PlayingPiece  │──────┘ HAS-A
          │          ├───────────────────────────────┤
          │          │ + Player(name, piece)         │
          │          │ + getName(): String           │
          │          │ + setName(name): void         │
          │          │ + getPlayingPiece(): Piece    │
          │          │ + setPlayingPiece(p): void    │
          │          └───────────────────────────────┘
          │                      △
          │                      │
          │                      │ HAS-A (collection)
          │                      │
          │                      │
          │          ┌───────────────────────────────────────────┐
          │          │      TicTacToeGame                        │
          │          ├───────────────────────────────────────────┤
          │          │ - players: Deque<Player>                  │◄────┐
          │          │ - gameBoard: Board                        │     │
          │          │ - winner: Player                          │     │
          │          ├───────────────────────────────────────────┤     │
          │          │ + initializeGame(): void                  │     │
          │          │ + startGame(): GameStatus                 │─────┤ returns
          └──────────┤ + checkForWinner(r,c,type): boolean       │     │
                     └───────────────────────────────────────────┘     │
                                 │         │                            │
                                 │         │ HAS-A                      │
                                 │         │                            │
                                 │         ▼                            │
                                 │  ┌─────────────────────────────────┐│
                                 │  │         Board                    ││
                                 │  ├─────────────────────────────────┤│
                                 │  │ + size: int                      ││
                                 │  │ + board: PlayingPiece[][]        ││
                                 │  ├─────────────────────────────────┤│
                                 │  │ + Board(size)                    ││
                                 │  │ + addPiece(r,c,p): boolean       ││
                                 │  │ + getFreeCells(): List<Pair>     ││
                                 │  │ + printBoard(): void             ││
                                 │  └─────────────────────────────────┘│
                                 │                │                     │
                                 │                │ HAS-A (array)       │
                                 │                │                     │
                                 │                └─────────────────────┘
                                 │ creates
                                 │ uses
                                 ▼
                     ┌───────────────────────────────────────────┐
                     │        PlayGame (Main)                    │
                     ├───────────────────────────────────────────┤
                     │ + main(args): void                        │
                     └───────────────────────────────────────────┘
```

---

## Detailed Dependency Graph

```
                                DEPENDENCY FLOW
                                ===============

┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 1: ENUMS (No Dependencies)                                        │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌───────────────┐              ┌──────────────┐                        │
│  │  PieceType    │              │ GameStatus   │                        │
│  │  (X, O)       │              │ (WIN, DRAW)  │                        │
│  └───────────────┘              └──────────────┘                        │
│         │                               │                                │
│         │ used by                       │ used by                        │
└─────────┼───────────────────────────────┼────────────────────────────────┘
          │                               │
          ▼                               │
┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 2: BASE ENTITIES (Depends on Enums)                               │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────┐                             │
│  │         PlayingPiece                   │                             │
│  │  ┌──────────────────────────┐          │                             │
│  │  │ Dependencies:             │          │                             │
│  │  │ - PieceType (HAS-A)       │          │                             │
│  │  └──────────────────────────┘          │                             │
│  │                                         │                             │
│  │  + pieceType: PieceType                │                             │
│  │  + PlayingPiece(PieceType)             │                             │
│  └────────────────────────────────────────┘                             │
│         │                                                                 │
│         │ inherited by                                                    │
│         │                                                                 │
└─────────┼─────────────────────────────────────────────────────────────────┘
          │
          ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 3: CONCRETE PIECES (Depends on PlayingPiece)                      │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────┐         ┌────────────────────────┐          │
│  │   PlayingPieceX        │         │   PlayingPieceO        │          │
│  │  ┌──────────────────┐  │         │  ┌──────────────────┐  │          │
│  │  │ Dependencies:    │  │         │  │ Dependencies:    │  │          │
│  │  │ - PlayingPiece   │  │         │  │ - PlayingPiece   │  │          │
│  │  │   (IS-A)         │  │         │  │   (IS-A)         │  │          │
│  │  │ - PieceType.X    │  │         │  │ - PieceType.O    │  │          │
│  │  └──────────────────┘  │         │  └──────────────────┘  │          │
│  │                        │         │                        │          │
│  │  + PlayingPieceX()     │         │  + PlayingPieceO()     │          │
│  └────────────────────────┘         └────────────────────────┘          │
│         │                                     │                           │
│         │ used by                             │ used by                   │
└─────────┼─────────────────────────────────────┼───────────────────────────┘
          │                                     │
          ▼                                     ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 4: PLAYER & BOARD (Depends on Pieces)                             │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌─────────────────────────────┐      ┌───────────────────────────────┐ │
│  │         Player               │      │          Board                │ │
│  │  ┌────────────────────────┐ │      │  ┌─────────────────────────┐ │ │
│  │  │ Dependencies:          │ │      │  │ Dependencies:           │ │ │
│  │  │ - PlayingPiece (HAS-A) │ │      │  │ - PlayingPiece[][] (2D) │ │ │
│  │  └────────────────────────┘ │      │  │ - List (Java util)      │ │ │
│  │                             │      │  │ - Pair (Apache Commons) │ │ │
│  │  + name: String             │      │  └─────────────────────────┘ │ │
│  │  + playingPiece: Piece      │      │                               │ │
│  │  + Player(name, piece)      │      │  + size: int                  │ │
│  │  + getName(): String        │      │  + board: PlayingPiece[][]    │ │
│  │  + getPlayingPiece(): Piece │      │  + addPiece(r,c,p): boolean   │ │
│  │  ...                        │      │  + getFreeCells(): List       │ │
│  └─────────────────────────────┘      │  + printBoard(): void         │ │
│         │                              └───────────────────────────────┘ │
│         │ used by                                  │ used by              │
└─────────┼──────────────────────────────────────────┼──────────────────────┘
          │                                          │
          ▼                                          ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 5: GAME CONTROLLER (Depends on All Above)                         │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────┐             │
│  │              TicTacToeGame                             │             │
│  │  ┌───────────────────────────────────────────────────┐ │             │
│  │  │ Dependencies:                                     │ │             │
│  │  │ - Player (HAS-A collection)                       │ │             │
│  │  │ - Board (HAS-A)                                   │ │             │
│  │  │ - PlayingPieceX (creates)                         │ │             │
│  │  │ - PlayingPieceO (creates)                         │ │             │
│  │  │ - GameStatus (returns)                            │ │             │
│  │  │ - PieceType (uses for comparison)                 │ │             │
│  │  │ - Deque (Java util)                               │ │             │
│  │  │ - Scanner (Java util)                             │ │             │
│  │  │ - List, Pair (from Board)                         │ │             │
│  │  └───────────────────────────────────────────────────┘ │             │
│  │                                                         │             │
│  │  - players: Deque<Player>                              │             │
│  │  - gameBoard: Board                                    │             │
│  │  - winner: Player                                      │             │
│  │                                                         │             │
│  │  + initializeGame(): void                              │             │
│  │  + startGame(): GameStatus                             │             │
│  │  + checkForWinner(row, col, type): boolean             │             │
│  └────────────────────────────────────────────────────────┘             │
│         │                                                                 │
│         │ used by                                                         │
└─────────┼─────────────────────────────────────────────────────────────────┘
          │
          ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  LAYER 6: MAIN APPLICATION (Depends on Game Controller)                  │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────┐                     │
│  │           PlayGame (Main)                      │                     │
│  │  ┌──────────────────────────────────────────┐  │                     │
│  │  │ Dependencies:                            │  │                     │
│  │  │ - TicTacToeGame (creates & uses)         │  │                     │
│  │  │ - GameStatus (for switch statement)      │  │                     │
│  │  └──────────────────────────────────────────┘  │                     │
│  │                                                 │                     │
│  │  + main(args): void                            │                     │
│  └────────────────────────────────────────────────┘                     │
│                                                                           │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## Relationship Matrix

| Class           | Depends On                                    | Dependency Type        |
|-----------------|-----------------------------------------------|------------------------|
| PieceType       | None                                          | -                      |
| GameStatus      | None                                          | -                      |
| PlayingPiece    | PieceType                                     | HAS-A (attribute)      |
| PlayingPieceX   | PlayingPiece, PieceType                       | IS-A, uses             |
| PlayingPieceO   | PlayingPiece, PieceType                       | IS-A, uses             |
| Player          | PlayingPiece                                  | HAS-A (attribute)      |
| Board           | PlayingPiece, List, Pair                      | HAS-A (array), uses    |
| TicTacToeGame   | Player, Board, PlayingPieceX, PlayingPieceO,  | HAS-A, creates, uses   |
|                 | GameStatus, PieceType, Deque, Scanner         |                        |
| PlayGame        | TicTacToeGame, GameStatus                     | creates, uses          |

---

## Object Creation and Lifecycle Flow

```
main() starts
    │
    ├─→ new TicTacToeGame()
    │       │
    │       └─→ initializeGame()
    │               │
    │               ├─→ new LinkedList<Player>()  [creates Deque]
    │               │
    │               ├─→ new PlayingPieceX()  [creates X piece]
    │               │       │
    │               │       └─→ super(PieceType.X)  [calls PlayingPiece constructor]
    │               │
    │               ├─→ new Player("Player1", crossPiece)  [creates Player with X]
    │               │       │
    │               │       └─→ stores PlayingPieceX reference
    │               │
    │               ├─→ new PlayingPieceO()  [creates O piece]
    │               │       │
    │               │       └─→ super(PieceType.O)  [calls PlayingPiece constructor]
    │               │
    │               ├─→ new Player("Player2", noughtsPiece)  [creates Player with O]
    │               │       │
    │               │       └─→ stores PlayingPieceO reference
    │               │
    │               ├─→ players.add(player1)  [adds to Deque]
    │               ├─→ players.add(player2)  [adds to Deque]
    │               │
    │               └─→ new Board(3)  [creates 3x3 board]
    │                       │
    │                       └─→ board = new PlayingPiece[3][3]  [2D array]
    │
    ├─→ startGame()
    │       │
    │       └─→ Game Loop:
    │           │
    │           ├─→ players.removeFirst()  [get current player]
    │           │       │
    │           │       └─→ returns Player with their PlayingPiece
    │           │
    │           ├─→ gameBoard.printBoard()
    │           │       │
    │           │       └─→ reads board[i][j].pieceType  [accesses PlayingPiece]
    │           │
    │           ├─→ gameBoard.getFreeCells()
    │           │       │
    │           │       └─→ returns List<Pair<Integer, Integer>>
    │           │
    │           ├─→ new Scanner(System.in)  [reads input]
    │           │
    │           ├─→ gameBoard.addPiece(row, col, currentPlayer.playingPiece)
    │           │       │
    │           │       └─→ board[row][col] = playingPiece  [stores reference]
    │           │
    │           ├─→ checkForWinner(row, col, pieceType)
    │           │       │
    │           │       └─→ compares board[i][j].pieceType
    │           │
    │           ├─→ winner = currentPlayer  [stores Player reference]
    │           │
    │           └─→ return GameStatus.WIN or GameStatus.DRAW
    │
    └─→ Display result based on GameStatus
```

---

## Complete Class Interaction Diagram

```
                    ╔═══════════════════════╗
                    ║   PlayGame (main)     ║
                    ╚═══════════════════════╝
                              │
                              │ creates & controls
                              ▼
                    ┌───────────────────────┐
                    │   TicTacToeGame       │
                    └───────────────────────┘
                       │         │        │
        ┌──────────────┤         │        └────────────┐
        │ creates      │ creates │ HAS-A               │ returns
        │              │         │                     │
        ▼              ▼         ▼                     ▼
┌───────────┐   ┌───────────┐   ┌─────────────────┐   ┌──────────┐
│  Player1  │   │  Player2  │   │  Board          │   │GameStatus│
│           │   │           │   │                 │   └──────────┘
│ HAS-A     │   │ HAS-A     │   │  HAS-A          │
│    ▼      │   │    ▼      │   │    ▼            │
│ Piece X   │   │ Piece O   │   │ Piece[][]       │
└───────────┘   └───────────┘   └─────────────────┘
     │               │                   │
     │               │                   │
     └───────┬───────┘                   │
             │                           │
             │ both use                  │ contains
             ▼                           ▼
      ┌─────────────────┐         ┌─────────────┐
      │ PlayingPiece    │◄────────│PlayingPiece │
      │   (abstract)    │ IS-A    │   X or O    │
      └─────────────────┘         └─────────────┘
             │                           │
             │ HAS-A                     │
             ▼                           │
      ┌─────────────────┐                │
      │   PieceType     │◄───────────────┘ uses
      │   (X or O)      │
      └─────────────────┘
```

---

## Dependency Injection Flow

```
No Dependency Injection: TicTacToeGame creates all dependencies internally

  initializeGame() {
      ├─→ Creates PlayingPieceX()         [Hard dependency]
      ├─→ Creates PlayingPieceO()         [Hard dependency]
      ├─→ Creates Player with pieces      [Hard dependency]
      └─→ Creates Board(3)                [Hard coded size]
  }

This means:
  - Cannot easily test with mock objects
  - Board size is fixed at 3
  - Piece types are fixed at X and O
  - Player names are hard-coded
```

---

## Key Observations

### Tight Coupling
- **TicTacToeGame** is tightly coupled to concrete implementations (PlayingPieceX, PlayingPieceO)
- Board size is hard-coded to 3
- Player creation is internal to game initialization

### Loose Coupling
- **PlayingPiece** abstraction allows different piece types
- **Player** doesn't care about specific piece implementations
- **Board** works with any PlayingPiece subclass

### Data Flow
1. **Input**: User → Scanner → TicTacToeGame
2. **Processing**: TicTacToeGame → Board → PlayingPiece array
3. **Output**: TicTacToeGame → GameStatus → PlayGame → Console

### Object Relationships Count
- **6 Classes** (excluding enums)
- **2 Enums**
- **2 IS-A** relationships (inheritance)
- **5 HAS-A** relationships (composition)
- **Multiple USES** relationships (dependencies)
