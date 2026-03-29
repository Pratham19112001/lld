# Snake and Ladder Game - Design Documentation

## All LLD Objects in this Repository

| #  | LLD Object          | Folder          |
|----|---------------------|-----------------|
| 1  | Parking Lot System  | `parking_Lot/`  |
| 2  | TicTacToe Game      | `TicTacToe/`    |
| 3  | Car Rental System   | `carRental/`    |
| 4  | Snake and Ladder    | `snakeNladder/` |
| 5  | HashMap Implementation | `hashmap/`   |

## Entities Used (7 Classes)

| #  | Entity              | Type              | Package      |
|----|---------------------|-------------------|--------------|
| 1  | Cell                | Entity            | snakeNladder |
| 2  | Jump                | Value Object      | snakeNladder |
| 3  | Dice                | Service           | snakeNladder |
| 4  | Player              | Entity            | snakeNladder |
| 5  | Board               | Entity            | snakeNladder |
| 6  | Game                | Game Controller   | snakeNladder |
| 7  | SnakeNLadderDemo    | Main / Client     | snakeNladder |

---

## Overview
A classic Snake and Ladder board game implementation demonstrating object-oriented design principles including composition, encapsulation, and turn-based game logic. The system supports multiple players, configurable board size, random snake and ladder placement, and dice rolling mechanics.

---

## UML Class Diagram

```
┌─────────────────────────┐
│         Jump            │
├─────────────────────────┤
│ + start: int            │
│ + end: int              │
├─────────────────────────┤
│ + Jump()                │
└─────────────────────────┘
           △
           │ HAS-A (optional)
           │
┌──────────────────────────────┐
│          Cell                │
├──────────────────────────────┤
│ + jump: Jump                 │──────► HAS-A Jump (optional)
├──────────────────────────────┤
│ + Cell()                     │
└──────────────────────────────┘
           △
           │ HAS-A (2D array)
           │
┌──────────────────────────────┐
│         Board                │
├──────────────────────────────┤
│ + cells: Cell[][]            │──────► HAS-A Cell[][]
├──────────────────────────────┤
│ + Board(size, snakes, lddrs) │
│ - initializeCells(size): void│
│ - addSnakesLadders(...): void│
│ + getCell(position): Cell    │
└──────────────────────────────┘

┌──────────────────────────────┐
│          Dice                │
├──────────────────────────────┤
│ + diceCount: int             │
│ + min: int                   │
│ + max: int                   │
├──────────────────────────────┤
│ + Dice(diceCount)            │
│ + rollDice(): int            │
└──────────────────────────────┘

┌──────────────────────────────┐
│         Player               │
├──────────────────────────────┤
│ + id: String                 │
│ + currentPosition: int       │
├──────────────────────────────┤
│ + Player(id, position)       │
└──────────────────────────────┘
           △
           │ HAS-A (collection)
           │
┌─────────────────────────────────────────┐
│             Game                        │
├─────────────────────────────────────────┤
│ + board: Board                          │──────► HAS-A Board
│ + dice: Dice                            │──────► HAS-A Dice
│ + playersList: Deque<Player>            │──────► HAS-A Deque<Player>
│ + winner: Player                        │──────► HAS-A Player (reference)
├─────────────────────────────────────────┤
│ + Game()                                │
│ - initializeGame(): void                │
│ - addPlayers(): void                    │
│ + startGame(): void                     │
│ - findPlayerTurn(): Player              │
│ - jumpCheck(position): int              │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│      SnakeNLadderDemo (Main)            │
├─────────────────────────────────────────┤
│ + main(args): void                      │──────► USES Game
└─────────────────────────────────────────┘
```

---

## Relationships

### HAS-A (Composition/Aggregation) Relationships

1. **Cell HAS-A Jump** (Optional Composition)
   - Each cell may have a jump (snake or ladder)
   - Null if the cell is a normal cell

2. **Board HAS-A Cell[][]** (Composition)
   - Board contains a 2D array of cells

3. **Game HAS-A Board** (Composition)
   - Game owns the game board

4. **Game HAS-A Dice** (Composition)
   - Game owns the dice

5. **Game HAS-A Deque<Player>** (Composition)
   - Game manages a queue of players for turn management

6. **Game HAS-A Player (winner)** (Reference)
   - Game maintains reference to winning player

---

## Class Details

### 1. Jump (Value Object)
**Purpose:** Represents a snake or ladder on the board

**Attributes:**
- `start: int` - Starting position of jump (snake head or ladder bottom)
- `end: int` - Ending position of jump (snake tail or ladder top)

**Jump Type:**
- **Snake**: `start > end` (moves player down)
- **Ladder**: `start < end` (moves player up)

**Design:** Simple data holder for jump transitions

---

### 2. Cell (Entity)
**Purpose:** Represents a single cell on the board

**Attributes:**
- `jump: Jump` - Optional jump object (null for normal cells)

**Relationships:**
- HAS-A Jump (optional)

**States:**
- **Normal Cell**: `jump == null`
- **Snake Head**: `jump != null && jump.start > jump.end`
- **Ladder Bottom**: `jump != null && jump.start < jump.end`

---

### 3. Dice (Service)
**Purpose:** Simulates dice rolling

**Attributes:**
- `diceCount: int` - Number of dice to roll
- `min: int` - Minimum value per die (1)
- `max: int` - Maximum value per die (6)

**Methods:**
- `Dice(int diceCount)` - Constructor
- `rollDice(): int`
  - Rolls `diceCount` dice
  - Sums all dice values
  - Uses ThreadLocalRandom for randomness
  - Returns total sum

**Algorithm:** `sum = Σ(random(1, 6))` for each die

---

### 4. Player (Entity)
**Purpose:** Represents a player in the game

**Attributes:**
- `id: String` - Player identifier
- `currentPosition: int` - Current position on board (0-indexed)

**Methods:**
- `Player(String id, int currentPosition)` - Constructor

**Design:** Simple entity with mutable position

---

### 5. Board (Entity)
**Purpose:** Manages the game board with cells, snakes, and ladders

**Attributes:**
- `cells: Cell[][]` - 2D array representing the board

**Methods:**
- `Board(int boardSize, int numberOfSnakes, int numberOfLadders)` - Constructor
  - Creates board of given size
  - Randomly places snakes and ladders
  
- `initializeCells(int boardSize): void` (private)
  - Creates `boardSize × boardSize` grid
  - Initializes all cells
  
- `addSnakesLadders(Cell[][] cells, int numberOfSnakes, int numberOfLadders): void` (private)
  - Randomly places snakes (start > end)
  - Randomly places ladders (start < end)
  - Ensures valid placements (no overlap)
  
- `getCell(int playerPosition): Cell`
  - Converts 1D position to 2D coordinates
  - Returns cell at that position

**Relationships:**
- HAS-A Cell[][]

**Algorithm for Position Mapping:**
```
position = 0-99 (for 10x10 board)
row = position / boardSize
column = position % boardSize
```

**Random Placement:**
- Uses ThreadLocalRandom for random positions
- Validates snakes have `head > tail`
- Validates ladders have `bottom < top`

---

### 6. Game (Game Controller)
**Purpose:** Main game logic and flow controller

**Attributes:**
- `board: Board` - The game board
- `dice: Dice` - Dice for rolling
- `playersList: Deque<Player>` - Queue of players for turn management
- `winner: Player` - Reference to winning player (null until someone wins)

**Methods:**
- `Game()` - Constructor that calls `initializeGame()`
  
- `initializeGame(): void` (private)
  - Creates 10×10 board with 5 snakes and 4 ladders
  - Creates 1 dice
  - Initializes winner to null
  - Adds players
  
- `addPlayers(): void` (private)
  - Creates 2 players starting at position 0
  - Adds to player queue
  
- `startGame(): void`
  - Main game loop
  - Continues until winner found
  - Gets current player's turn
  - Rolls dice
  - Calculates new position
  - Checks for jumps (snakes/ladders)
  - Updates player position
  - Checks for win condition (position >= 99 for 10×10 board)
  
- `findPlayerTurn(): Player` (private)
  - Removes first player from queue
  - Adds player back to end of queue
  - Returns current player
  - Implements round-robin turn system
  
- `jumpCheck(int playerNewPosition): int` (private)
  - Checks if new position has a jump
  - Returns jump end position if snake/ladder present
  - Returns original position if no jump
  - Prints "Snake" or "Ladder" message

**Relationships:**
- HAS-A Board
- HAS-A Dice
- HAS-A Deque<Player>
- HAS-A Player (winner reference)

**Game Flow:**
1. Initialize board, dice, players
2. Loop until winner found:
   - Get current player
   - Roll dice
   - Move player
   - Check for snakes/ladders
   - Check for win condition
3. Announce winner

**Win Condition:** Player position >= (boardSize² - 1)

---

### 7. SnakeNLadderDemo (Main Entry Point)
**Purpose:** Application entry point

**Methods:**
- `main(String[] args): void`
  - Creates Game instance
  - Starts game loop

**Relationships:**
- USES Game

---

## Design Patterns Used

### 1. **Queue Pattern**
- Uses `Deque<Player>` for turn management
- Players are rotated in a FIFO manner
- Same pattern as TicTacToe

### 2. **Composition Pattern**
- **Game** composes Board, Dice, and Players
- **Board** composes Cell grid
- **Cell** optionally composes Jump

### 3. **Single Responsibility Principle**
- Each class has one clear responsibility:
  - `Cell` - Represents board position
  - `Jump` - Represents snake/ladder transition
  - `Dice` - Handles dice rolling
  - `Player` - Represents player entity
  - `Board` - Manages board structure
  - `Game` - Controls game logic and flow

### 4. **Encapsulation**
- Board initialization logic encapsulated
- Position-to-cell mapping hidden in Board
- Dice rolling logic encapsulated

---

## Object Interaction Flow

```
SnakeNLadderDemo.main()
    │
    └──> new Game()
         │
         └──> Game Constructor:
              │
              └──> initializeGame()
                   │
                   ├──> new Board(10, 5, 4)
                   │    │
                   │    ├──> initializeCells(10)
                   │    │    └──> Create 10×10 Cell[][] grid
                   │    │
                   │    └──> addSnakesLadders(cells, 5, 4)
                   │         │
                   │         ├──> Place 5 snakes randomly:
                   │         │    └──> new Jump(snakeHead, snakeTail)
                   │         │         └──> cell.jump = snakeObj
                   │         │
                   │         └──> Place 4 ladders randomly:
                   │              └──> new Jump(ladderStart, ladderEnd)
                   │                   └──> cell.jump = ladderObj
                   │
                   ├──> new Dice(1)
                   │
                   ├──> winner = null
                   │
                   └──> addPlayers()
                        │
                        ├──> new Player("Player-1", 0)
                        ├──> new Player("Player-2", 0)
                        └──> Add to playersList Deque

         └──> Game.startGame()
              │
              └──> Game Loop (while winner == null):
                   │
                   ├──> findPlayerTurn()
                   │    │
                   │    ├──> playersList.removeFirst()
                   │    ├──> playersList.addLast(player)
                   │    └──> return current player
                   │
                   ├──> dice.rollDice()
                   │    │
                   │    └──> Generate random(1, 6) × diceCount
                   │         └──> return sum
                   │
                   ├──> playerNewPosition = currentPosition + diceNumbers
                   │
                   ├──> jumpCheck(playerNewPosition)
                   │    │
                   │    ├──> if (position > board size) return position
                   │    │
                   │    ├──> cell = board.getCell(playerNewPosition)
                   │    │    │
                   │    │    └──> Convert 1D position to 2D:
                   │    │         row = position / boardSize
                   │    │         col = position % boardSize
                   │    │         return cells[row][col]
                   │    │
                   │    └──> if (cell.jump != null && cell.jump.start == position)
                   │         │
                   │         ├──> Determine type:
                   │         │    Snake if (start > end)
                   │         │    Ladder if (start < end)
                   │         │
                   │         └──> return cell.jump.end
                   │
                   ├──> player.currentPosition = playerNewPosition
                   │
                   └──> if (playerNewPosition >= boardSize² - 1)
                        │
                        └──> winner = currentPlayer
                             └──> Exit loop

              └──> Print winner
```

---

## Relationships Summary

### HAS-A (Composition/Aggregation) Relationships

1. **Cell HAS-A Jump** (Optional)
   - Cell may contain a snake or ladder

2. **Board HAS-A Cell[][]** (Composition)
   - Board contains 2D grid of cells

3. **Game HAS-A Board** (Composition)
   - Game owns the board

4. **Game HAS-A Dice** (Composition)
   - Game owns the dice

5. **Game HAS-A Deque<Player>** (Composition)
   - Game manages player queue

6. **Game HAS-A Player (winner)** (Reference)
   - Game maintains winner reference

---

## Game Mechanics

### Board Structure
```
10×10 Board (100 cells):

Position 0-9:    Row 0
Position 10-19:  Row 1
Position 20-29:  Row 2
...
Position 90-99:  Row 9

Example:
Position 47 = Row 4, Column 7
Position 99 = Row 9, Column 9 (winning position)
```

### Snake Placement
```
Random snake placement:
- Snake Head: Random position (1 to 98)
- Snake Tail: Random position < head
- Effect: Player moves DOWN the board

Example:
  Snake at cell 67 → 23
  Player lands on 67 → Moves to 23 (down 44 positions)
```

### Ladder Placement
```
Random ladder placement:
- Ladder Bottom: Random position (1 to 98)
- Ladder Top: Random position > bottom
- Effect: Player moves UP the board

Example:
  Ladder at cell 8 → 34
  Player lands on 8 → Moves to 34 (up 26 positions)
```

### Turn Mechanics
```
1. Player rolls dice
2. Move forward by dice value
3. Check for snake/ladder at new position
4. If jump present, move to jump end
5. Check if reached position ≥ 99
6. Next player's turn
```

### Win Condition
```
Player wins when: position >= (boardSize × boardSize - 1)

For 10×10 board: position >= 99
```

---

## Object Creation Flow

```
main() starts
    │
    └──> new Game()
         │
         ├──> new Board(10, 5, 4)
         │    │
         │    ├──> cells = new Cell[10][10]
         │    │    │
         │    │    └──> Initialize 100 cells
         │    │
         │    ├──> Add 5 Snakes:
         │    │    │
         │    │    └──> For each snake:
         │    │         ├──> head = random(1, 99)
         │    │         ├──> tail = random(1, 99)
         │    │         ├──> Validate: tail < head
         │    │         ├──> new Jump(head, tail)
         │    │         └──> cell[head].jump = Jump
         │    │
         │    └──> Add 4 Ladders:
         │         │
         │         └──> For each ladder:
         │              ├──> bottom = random(1, 99)
         │              ├──> top = random(1, 99)
         │              ├──> Validate: top > bottom
         │              ├──> new Jump(bottom, top)
         │              └──> cell[bottom].jump = Jump
         │
         ├──> new Dice(1)
         │    └──> diceCount = 1
         │
         ├──> new LinkedList<Player>()
         │
         ├──> new Player("Player-1", 0)
         ├──> new Player("Player-2", 0)
         └──> Add to playersList
```

---

## Game Flow Sequence

```
Game Start
    │
    ├──> Board Created (10×10 = 100 cells)
    ├──> 5 Snakes Placed Randomly
    ├──> 4 Ladders Placed Randomly
    ├──> 2 Players Created at Position 0
    │
    └──> Game Loop:
         │
         ┌─────────────────────────────────┐
         │  Turn Cycle:                    │
         │                                 │
         │  1. Get Current Player          │
         │     (rotate queue)              │
         │                                 │
         │  2. Roll Dice                   │
         │     (random 1-6)                │
         │                                 │
         │  3. Calculate New Position      │
         │     (current + dice)            │
         │                                 │
         │  4. Check for Jump              │
         │     ├─ If Snake → Move Down     │
         │     ├─ If Ladder → Move Up      │
         │     └─ If Normal → Stay         │
         │                                 │
         │  5. Update Player Position      │
         │                                 │
         │  6. Check Win Condition         │
         │     (position >= 99)            │
         │                                 │
         │  7. If not won → Next Player    │
         │                                 │
         └─────────────────────────────────┘
         │
         └──> Winner Found → End Game
```

---

## Example Game Scenario

```
Initial State:
  Player-1 at position 0
  Player-2 at position 0
  Board: 10×10 with snakes and ladders

Turn 1: Player-1
  Roll: 4
  New Position: 0 + 4 = 4
  Jump Check: No jump at cell 4
  Final Position: 4

Turn 2: Player-2
  Roll: 3
  New Position: 0 + 3 = 3
  Jump Check: No jump at cell 3
  Final Position: 3

Turn 3: Player-1
  Roll: 5
  New Position: 4 + 5 = 9
  Jump Check: Ladder at 9 → 34
  Print: "[+] Jump done by: Ladder"
  Final Position: 34

Turn 4: Player-2
  Roll: 6
  New Position: 3 + 6 = 9
  Jump Check: Ladder at 9 → 34
  Print: "[+] Jump done by: Ladder"
  Final Position: 34

...continue until someone reaches position ≥ 99...

Final Turn: Player-1
  Current Position: 95
  Roll: 6
  New Position: 95 + 6 = 101
  Jump Check: Position > 99, return 101
  Final Position: 101
  Win Check: 101 >= 99 → WINNER!
  
Output: "===> The Winner is: Player-1"
```

---

## Key Features

1. **Configurable Board Size**: Can create any N×N board (default 10×10)
2. **Random Placement**: Snakes and ladders placed randomly each game
3. **Multiple Dice**: Supports rolling multiple dice (default 1)
4. **Turn Management**: Deque-based player rotation
5. **Jump Detection**: Automatic snake/ladder handling
6. **Win Detection**: Checks if player reached/exceeded final cell
7. **Extensible**: Easy to add more players, change board size

---

## Design Characteristics

### Simplicity
- Only 7 classes (most lightweight LLD in this repo)
- No complex patterns (no Strategy, Factory, etc.)
- Straightforward game logic

### Randomness
- Board layout changes every game
- Dice rolls are random
- High replay value

### Turn-Based
- Similar queue pattern to TicTacToe
- Fair turn rotation using Deque

---

## Example Usage

```java
// Simple usage
Game game = new Game();
game.startGame();

// Customizable board (if constructor exposed)
Board customBoard = new Board(15, 8, 6);  // 15×15 board, 8 snakes, 6 ladders
Dice twoDice = new Dice(2);               // Roll 2 dice (range 2-12)
```

---

## Comparison with Other LLD Projects

| Aspect              | Snake & Ladder      | TicTacToe           | Parking Lot         | Car Rental          |
|---------------------|---------------------|---------------------|---------------------|---------------------|
| **Complexity**      | Simple              | Simple              | Complex             | Complex             |
| **Classes**         | 7                   | 9                   | 21                  | 24                  |
| **Design Patterns** | None                | Strategy (implicit) | Strategy (×3)       | Strategy (×2)       |
| **Turn Management** | Deque rotation      | Deque rotation      | N/A                 | N/A                 |
| **Randomness**      | High (dice, board)  | None                | None                | None                |
| **Thread Safety**   | No                  | No                  | Yes                 | Yes                 |
| **Data Structures** | 2D Array, Deque     | 2D Array, Deque     | Maps, Lists, Locks  | Maps, Lists, Locks  |
| **User Input**      | None (automatic)    | Yes (coordinates)   | None (simulated)    | None (simulated)    |

---

## Future Enhancements

1. **Variable Players**: Support for 2-6 players
2. **Custom Rules**: 
   - Exact landing on 100 required
   - Roll 6 to start
   - Extra turn on rolling 6
3. **Player Input**: Let players decide when to roll
4. **Graphical Board**: Display board visually with ASCII art
5. **Statistics**: Track rolls, average moves to win
6. **Save/Load Game**: Persist game state
7. **Special Cells**: Bonus cells, skip turn cells
8. **AI Players**: Computer-controlled opponents
9. **Multiplayer**: Network play support
10. **Custom Board Designer**: Let users place snakes/ladders
11. **Animation**: Show player movement step by step
12. **Sound Effects**: Add audio feedback

---

## Code Quality

### Strengths
- Simple and easy to understand
- Follows OOP principles
- Proper separation of concerns
- Random board generation adds variety

### Potential Improvements
1. **Validation**: Add input validation for board size, snake/ladder count
2. **Collision Detection**: Prevent snakes/ladders from overlapping
3. **Board Display**: Add method to print visual board representation
4. **Getters/Setters**: Add proper getters and setters to Cell and Jump
5. **Constants**: Extract magic numbers (min=1, max=6, boardSize=10)
6. **Exception Handling**: Handle edge cases (position out of bounds)
7. **Immutability**: Make Jump immutable (final fields)
8. **Access Modifiers**: Use proper access levels (private/public)

---

## Example Board Visualization (Conceptual)

```
100|99 |98 |97 |96 |95 |94 |93 |92 |91 |
90 |89 |88 |87🪜|86 |85 |84 |83 |82 |81 |
80 |79 |78 |77 |76 |75 |74 |73 |72 |71 |
70 |69 |68 |67🐍|66 |65 |64 |63 |62 |61 |
60 |59 |58 |57 |56 |55 |54 |53 |52 |51 |
50 |49 |48 |47 |46 |45 |44 |43 |42 |41 |
40 |39 |38 |37 |36 |35 |34 |33 |32 |31 |
30 |29 |28 |27 |26 |25 |24 |23 |22 |21 |
20 |19 |18 |17 |16 |15 |14 |13 |12 |11 |
10 |9🪜|8  |7  |6  |5  |4  |3  |2  |1  |

🪜 = Ladder (moves up)
🐍 = Snake (moves down)

Example Jumps:
  Ladder: 9 → 34 (up 25)
  Ladder: 87 → 94 (up 7)
  Snake: 67 → 23 (down 44)
```

---

## Algorithm Details

### Position to Cell Mapping
```java
// Convert 1D position (0-99) to 2D coordinates
int position = 47;
int row = position / boardSize;     // 47 / 10 = 4
int column = position % boardSize;  // 47 % 10 = 7
Cell cell = cells[4][7];
```

### Jump Detection
```java
// Check if position has a jump
Cell cell = board.getCell(position);
if (cell.jump != null && cell.jump.start == position) {
    // Jump exists at this position
    if (cell.jump.start < cell.jump.end) {
        // Ladder (going up)
    } else {
        // Snake (going down)
    }
    return cell.jump.end;  // Move to jump end
}
return position;  // No jump, stay at position
```

### Random Placement Algorithm
```java
// Place snake
while (numberOfSnakes > 0) {
    int head = random(1, 98);
    int tail = random(1, 98);
    
    // Validate: tail must be less than head
    if (tail >= head) {
        continue;  // Try again
    }
    
    // Place snake
    Jump snake = new Jump(head, tail);
    getCell(head).jump = snake;
    numberOfSnakes--;
}
```

---

## Class Dependency Graph

```
LAYER 1: SIMPLE VALUE OBJECTS
══════════════════════════════
┌──────────┐
│   Jump   │  (no dependencies)
└──────────┘
     │
     │ used by
     ▼
LAYER 2: CELL
═════════════
┌──────────┐
│   Cell   │  (depends on Jump)
└──────────┘
     │
     │ used by
     ▼
LAYER 3: BOARD & DICE & PLAYER
═══════════════════════════════
┌──────────┐  ┌──────────┐  ┌──────────┐
│  Board   │  │   Dice   │  │  Player  │
│    ↓     │  │   (no    │  │   (no    │
│  Cell    │  │   deps)  │  │   deps)  │
└──────────┘  └──────────┘  └──────────┘
     │             │              │
     └─────────────┴──────────────┘
                   │
                   │ all used by
                   ▼
LAYER 4: GAME CONTROLLER
════════════════════════
┌──────────────────┐
│      Game        │
│       ↓          │
│  - Board         │
│  - Dice          │
│  - Player (list) │
└──────────────────┘
         │
         │ used by
         ▼
LAYER 5: MAIN
═════════════
┌──────────────────────┐
│ SnakeNLadderDemo     │
│        ↓             │
│      Game            │
└──────────────────────┘
```

---

## Randomness Details

### Dice Roll
```java
ThreadLocalRandom.current().nextInt(1, 7)
// Generates random number from 1 to 6 (inclusive)
// If 2 dice: sum of two random(1, 6)
```

### Snake/Ladder Placement
```java
ThreadLocalRandom.current().nextInt(1, 99)
// Generates random position from 1 to 98
// Position 0 (start) and 99 (end) excluded
```

### Why ThreadLocalRandom?
- Better performance than `Random` in multi-threaded contexts
- Even though game is single-threaded, it's good practice
- No need for `new Random()` object

---

## Edge Cases Handled

1. **Position Overflow**: If dice roll takes position > 99, still checked for win
2. **Jump Validation**: Cell checks if `jump.start == position` before applying
3. **Snake/Ladder Validation**: Placement loop continues until valid positions found
4. **Empty Cells**: Cells without jumps have `jump = null`

---

## Memory Footprint

```
For 10×10 board with 2 players:

Board:   100 cells × Cell object = ~800 bytes
Jumps:   9 Jump objects (5 snakes + 4 ladders) = ~288 bytes
Players: 2 Player objects = ~96 bytes
Dice:    1 Dice object = ~48 bytes
Game:    1 Game object + Deque overhead = ~200 bytes

Total: ~1.5 KB (very lightweight)
```

---

## Performance Characteristics

### Time Complexity
- **Initialize Board**: O(n²) - n = board size
- **Place Snakes/Ladders**: O(s + l) - s = snakes, l = ladders (with retries)
- **Roll Dice**: O(d) - d = dice count
- **Get Cell**: O(1) - direct array access
- **Jump Check**: O(1) - direct cell access
- **Find Player Turn**: O(1) - Deque operations
- **Game Loop**: O(turns) - varies based on luck

### Space Complexity
- **Board**: O(n²) - n × n grid
- **Players**: O(p) - p = number of players
- **Jumps**: O(s + l) - snakes + ladders

### Average Game Length
```
Statistical expectation:
- Average dice roll: 3.5
- Board size: 100
- With snakes/ladders: ~30-50 turns per game
- Can be as quick as 17 turns (rolling 6s with ladders)
- Can exceed 200 turns (unlucky with many snakes)
```

---

## Design Insights

### Why No Interfaces?
Unlike Parking Lot and Car Rental, this game doesn't need:
- Multiple strategies (simple dice roll, no variants)
- Extensibility for different implementations
- Dependency injection
Simple composition is sufficient for this use case.

### Why Deque?
```
Deque<Player> provides:
  - addLast() → O(1)
  - removeFirst() → O(1)
  
Perfect for round-robin turn management:
  Player turn = queue.removeFirst();  // Get current player
  queue.addLast(turn);                // Put back at end
```

### Why 2D Array?
```
Alternative: 1D Array
  - cells[100]
  - Simpler indexing

Current: 2D Array
  - cells[10][10]
  - More intuitive for board games
  - Easier to visualize spatially
  - Natural mapping (though not used here)

Trade-off: Requires position → (row, col) conversion
```

---

## Testing Scenarios

### Test Cases
1. **Normal Move**: Roll dice, move forward, no jump
2. **Snake Encounter**: Land on snake head, move to tail
3. **Ladder Encounter**: Land on ladder bottom, move to top
4. **Win Condition**: Reach position ≥ 99
5. **Position Overflow**: Roll takes beyond 100
6. **Multiple Players**: Turn rotation works correctly
7. **Random Board**: Different games have different layouts

### Manual Testing
```java
// Run multiple times to test randomness
for (int i = 0; i < 5; i++) {
    Game game = new Game();
    game.startGame();
}
// Each game should have different snake/ladder positions
// Winners will vary
```

---

## Summary

**Snake and Ladder** is the simplest LLD in this repository, demonstrating:
- Basic OOP with composition
- Queue-based turn management
- Random number generation
- 2D array manipulation
- Simple game loop logic

Unlike the other systems (Parking Lot, Car Rental), it intentionally avoids over-engineering and keeps the design straightforward - perfect for understanding fundamental OOP concepts without the complexity of enterprise patterns.
