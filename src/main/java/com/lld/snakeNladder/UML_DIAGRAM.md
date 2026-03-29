# Snake and Ladder Game - Complete UML Relationship Diagram

## Full System Architecture with Dependencies

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                      SNAKE AND LADDER GAME SYSTEM                           ┃
┃                         (7 Classes, Simple Design)                          ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 1: VALUE OBJECTS (No Dependencies)                                   │
└─────────────────────────────────────────────────────────────────────────────┘

                            ┌─────────────────────┐
                            │       Jump          │
                            ├─────────────────────┤
                            │ Dependencies: NONE  │
                            ├─────────────────────┤
                            │ + start: int        │
                            │ + end: int          │
                            └─────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 2: CELL (Depends on Jump)                                            │
└─────────────────────────────────────────────────────────────────────────────┘

                            ┌─────────────────────┐
                            │       Cell          │
                            ├─────────────────────┤
                            │ Dependencies:       │
                            │ - Jump (HAS-A)      │
                            ├─────────────────────┤
                            │ + jump: Jump        │──────► HAS-A Jump (optional)
                            └─────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 3: BOARD, DICE, PLAYER (Board depends on Cell)                       │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌──────────────────────────────────┐  ┌────────────────────┐  ┌─────────────────┐
    │         Board                    │  │      Dice          │  │     Player      │
    ├──────────────────────────────────┤  ├────────────────────┤  ├─────────────────┤
    │ Dependencies:                    │  │ Dependencies: NONE │  │ Dependencies:   │
    │ - Cell (HAS-A 2D array)          │  ├────────────────────┤  │ NONE            │
    │ - Jump (creates via Cell)        │  │ + diceCount: int   │  ├─────────────────┤
    │ - ThreadLocalRandom (uses)       │  │ + min: int         │  │ + id: String    │
    ├──────────────────────────────────┤  │ + max: int         │  │ + currentPos:int│
    │ + cells: Cell[][]                │  ├────────────────────┤  └─────────────────┘
    ├──────────────────────────────────┤  │ + Dice(count)      │          │
    │ + Board(size, snakes, ladders)   │  │ + rollDice(): int  │          │
    │ - initializeCells(size): void    │  └────────────────────┘          │
    │ - addSnakesLadders(...): void    │            │                     │
    │ + getCell(position): Cell        │            │                     │
    └──────────────────────────────────┘            │                     │
                    │                               │                     │
                    │ used by                       │ used by             │ used by
                    └───────────────────────────────┴─────────────────────┘
                                                    │
                                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 4: GAME CONTROLLER (Depends on Board, Dice, Player)                  │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────┐
                │              Game                            │
                ├──────────────────────────────────────────────┤
                │ Dependencies:                                │
                │ - Board (HAS-A)                              │
                │ - Dice (HAS-A)                               │
                │ - Player (HAS-A collection)                  │
                │ - Deque (Java util)                          │
                ├──────────────────────────────────────────────┤
                │ + board: Board                               │──────► HAS-A Board
                │ + dice: Dice                                 │──────► HAS-A Dice
                │ + playersList: Deque<Player>                 │──────► HAS-A Deque<Player>
                │ + winner: Player                             │──────► HAS-A Player (reference)
                ├──────────────────────────────────────────────┤
                │ + Game()                                     │
                │ - initializeGame(): void                     │
                │ - addPlayers(): void                         │
                │ + startGame(): void                          │
                │ - findPlayerTurn(): Player                   │
                │ - jumpCheck(position): int                   │
                └──────────────────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 5: MAIN APPLICATION (Depends on Game)                                │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────────┐
                        │   SnakeNLadderDemo (Main)      │
                        ├────────────────────────────────┤
                        │ Dependencies:                  │
                        │ - Game (creates & uses)        │
                        ├────────────────────────────────┤
                        │ + main(args): void             │
                        └────────────────────────────────┘
```

---

## Complete Dependency Graph (Bottom-Up)

```
LAYER 1: NO DEPENDENCIES
═════════════════════════
┌──────────┐
│   Jump   │  (start, end)
└──────────┘


LAYER 2: DEPENDS ON LAYER 1
════════════════════════════
┌──────────┐
│   Cell   │
│    ↓     │
│   Jump   │  (optional)
└──────────┘


LAYER 3: BOARD (DEPENDS ON LAYER 2), DICE & PLAYER (NO DEPS)
══════════════════════════════════════════════════════════════
┌──────────┐    ┌──────────┐    ┌──────────┐
│  Board   │    │   Dice   │    │  Player  │
│    ↓     │    │   (no    │    │   (no    │
│  Cell    │    │   deps)  │    │   deps)  │
│    ↓     │    └──────────┘    └──────────┘
│  Jump    │
└──────────┘


LAYER 4: GAME CONTROLLER (DEPENDS ON LAYER 3)
══════════════════════════════════════════════
┌───────────────┐
│     Game      │
│       ↓       │
│  - Board      │
│  - Dice       │
│  - Player     │
│  - Deque      │
└───────────────┘


LAYER 5: MAIN (DEPENDS ON LAYER 4)
═══════════════════════════════════
┌──────────────────────┐
│ SnakeNLadderDemo     │
│        ↓             │
│      Game            │
└──────────────────────┘
```

---

## Relationship Matrix

| Class              | Depends On           | Relationship Type      |
|--------------------|----------------------|------------------------|
| Jump               | None                 | -                      |
| Cell               | Jump                 | HAS-A (optional)       |
| Dice               | None                 | -                      |
| Player             | None                 | -                      |
| Board              | Cell, Jump           | HAS-A (array), creates |
| Game               | Board, Dice, Player  | HAS-A (all three)      |
| SnakeNLadderDemo   | Game                 | creates, uses          |

---

## Complete Object Creation Flow

```
main() starts
    │
    └──> new Game()
         │
         └──> Game Constructor:
              │
              └──> initializeGame()
                   │
                   ├─→ new Board(10, 5, 4)
                   │       [LAYER 3: Board]
                   │       │
                   │       └─→ Board Constructor:
                   │           │
                   │           ├─→ initializeCells(10)
                   │           │   │
                   │           │   └─→ Create 10×10 grid:
                   │           │       for i in 0..9:
                   │           │         for j in 0..9:
                   │           │           └─→ cells[i][j] = new Cell()
                   │           │               [LAYER 2: Cell created]
                   │           │               [Cell.jump = null initially]
                   │           │
                   │           └─→ addSnakesLadders(cells, 5, 4)
                   │               │
                   │               ├─→ Place 5 Snakes:
                   │               │   while (numberOfSnakes > 0):
                   │               │     │
                   │               │     ├─→ snakeHead = random(1, 98)
                   │               │     ├─→ snakeTail = random(1, 98)
                   │               │     │
                   │               │     ├─→ if (tail >= head) continue
                   │               │     │   [Validation: snake goes down]
                   │               │     │
                   │               │     ├─→ snakeObj = new Jump()
                   │               │     │   [LAYER 1: Jump created]
                   │               │     │   snakeObj.start = snakeHead
                   │               │     │   snakeObj.end = snakeTail
                   │               │     │
                   │               │     ├─→ cell = getCell(snakeHead)
                   │               │     │   [Get cell at snake head position]
                   │               │     │
                   │               │     └─→ cell.jump = snakeObj
                   │               │         [Attach jump to cell]
                   │               │
                   │               └─→ Place 4 Ladders:
                   │                   while (numberOfLadders > 0):
                   │                     │
                   │                     ├─→ ladderStart = random(1, 98)
                   │                     ├─→ ladderEnd = random(1, 98)
                   │                     │
                   │                     ├─→ if (start >= end) continue
                   │                     │   [Validation: ladder goes up]
                   │                     │
                   │                     ├─→ ladderObj = new Jump()
                   │                     │   [LAYER 1: Jump created]
                   │                     │   ladderObj.start = ladderStart
                   │                     │   ladderObj.end = ladderEnd
                   │                     │
                   │                     ├─→ cell = getCell(ladderStart)
                   │                     │
                   │                     └─→ cell.jump = ladderObj
                   │
                   ├─→ new Dice(1)
                   │       [LAYER 3: Dice]
                   │       dice.diceCount = 1
                   │       dice.min = 1
                   │       dice.max = 6
                   │
                   ├─→ winner = null
                   │
                   └─→ addPlayers()
                       │
                       ├─→ new Player("Player-1", 0)
                       │   [LAYER 3: Player]
                       │   player.id = "Player-1"
                       │   player.currentPosition = 0
                       │
                       ├─→ new Player("Player-2", 0)
                       │   [LAYER 3: Player]
                       │
                       └─→ playersList.add(player1)
                           playersList.add(player2)
                           [Deque initialized]
```

---

## Game Execution Flow (Complete Call Chain)

```
1. Main Entry
   SnakeNLadderDemo.main(args)
       │
       └─→ new Game()
           └─→ initializeGame() [see creation flow above]

2. Start Game
   game.startGame()
       │
       └─→ Game Loop (while winner == null):
           │
           ├─→ STEP 1: Get Current Player
           │    findPlayerTurn()
           │        │
           │        ├─→ playerTurn = playersList.removeFirst()
           │        │   [Deque: Remove from front]
           │        │
           │        ├─→ playersList.addLast(playerTurn)
           │        │   [Deque: Add to back]
           │        │
           │        └─→ return playerTurn
           │
           │    Print: "Player turn: [id] current position is: [pos]"
           │
           ├─→ STEP 2: Roll Dice
           │    dice.rollDice()
           │        │
           │        ├─→ totalSum = 0
           │        ├─→ diceUsed = 0
           │        │
           │        └─→ while (diceUsed < diceCount):
           │            │
           │            ├─→ ThreadLocalRandom.current().nextInt(1, 7)
           │            │   [Generate random 1-6]
           │            │
           │            ├─→ totalSum += randomValue
           │            └─→ diceUsed++
           │            │
           │            └─→ return totalSum
           │
           ├─→ STEP 3: Calculate New Position
           │    playerNewPosition = playerTurn.currentPosition + diceNumbers
           │    [Example: 4 + 5 = 9]
           │
           ├─→ STEP 4: Check for Jumps
           │    jumpCheck(playerNewPosition)
           │        │
           │        ├─→ if (playerNewPosition > board.cells.length² - 1)
           │        │       return playerNewPosition
           │        │       [Overflow case: position beyond board]
           │        │
           │        ├─→ cell = board.getCell(playerNewPosition)
           │        │       │
           │        │       └─→ Board.getCell(position)
           │        │           │
           │        │           ├─→ boardRow = position / cells.length
           │        │           │   [Example: 47 / 10 = 4]
           │        │           │
           │        │           ├─→ boardColumn = position % cells.length
           │        │           │   [Example: 47 % 10 = 7]
           │        │           │
           │        │           └─→ return cells[boardRow][boardColumn]
           │        │               [Return cells[4][7]]
           │        │
           │        ├─→ if (cell.jump != null && cell.jump.start == playerNewPosition)
           │        │   │
           │        │   ├─→ Determine Jump Type:
           │        │   │   if (cell.jump.start < cell.jump.end)
           │        │   │       jumpBy = "Ladder"  [Going UP]
           │        │   │   else
           │        │   │       jumpBy = "Snake"   [Going DOWN]
           │        │   │
           │        │   ├─→ Print: "[+] Jump done by: [Snake/Ladder]"
           │        │   │
           │        │   └─→ return cell.jump.end
           │        │       [Move to jump destination]
           │        │
           │        └─→ return playerNewPosition
           │            [No jump, stay at rolled position]
           │
           ├─→ STEP 5: Update Player Position
           │    playerTurn.currentPosition = playerNewPosition
           │    [Mutate player position]
           │
           │    Print: "Player turn: [id] new Position is: [pos]"
           │
           └─→ STEP 6: Check Win Condition
               if (playerNewPosition >= board.cells.length² - 1)
               │   [For 10×10 board: position >= 99]
               │
               └─→ winner = playerTurn
                   [Exit loop, game over]

3. Game Ends
   Print: "===> The Winner is: [winner.id]"
```

---

## Detailed Class Interaction Diagram

```
                    ╔═══════════════════════════════╗
                    ║  SnakeNLadderDemo (main)      ║
                    ╚═══════════════════════════════╝
                              │
                              │ creates & controls
                              ▼
                    ┌───────────────────────┐
                    │        Game           │
                    └───────────────────────┘
                       │     │     │
        ┌──────────────┤     │     └────────────┐
        │ HAS-A        │HAS-A│ HAS-A             │ HAS-A
        │              │     │                   │
        ▼              ▼     ▼                   ▼
┌──────────┐   ┌──────────┐ ┌─────────────┐   ┌──────────┐
│  Board   │   │   Dice   │ │  Player 1   │   │  Player 2│
│          │   │          │ │  Player 2   │   │   ...    │
│ HAS-A    │   │          │ │  (in Deque) │   │          │
│   ▼      │   └──────────┘ └─────────────┘   └──────────┘
│Cell[][]  │
│   │      │
│   ▼      │
│  Cell    │
│   │      │
│   ▼      │
│  Jump    │
│(optional)│
└──────────┘
```

---

## Snake and Ladder Placement Logic

### Snake Placement Algorithm
```
Input: numberOfSnakes = 5

while (numberOfSnakes > 0):
    │
    ├─→ snakeHead = random(1, 98)
    ├─→ snakeTail = random(1, 98)
    │
    ├─→ Validation:
    │   if (snakeTail >= snakeHead):
    │       continue  [Retry: snake must go DOWN]
    │
    ├─→ Create Jump:
    │   Jump snake = new Jump()
    │   snake.start = snakeHead  (e.g., 67)
    │   snake.end = snakeTail    (e.g., 23)
    │
    ├─→ Attach to Cell:
    │   cell = getCell(snakeHead)
    │   cell.jump = snake
    │
    └─→ numberOfSnakes--

Result: 5 cells now have snakes (start > end)
```

### Ladder Placement Algorithm
```
Input: numberOfLadders = 4

while (numberOfLadders > 0):
    │
    ├─→ ladderStart = random(1, 98)
    ├─→ ladderEnd = random(1, 98)
    │
    ├─→ Validation:
    │   if (ladderStart >= ladderEnd):
    │       continue  [Retry: ladder must go UP]
    │
    ├─→ Create Jump:
    │   Jump ladder = new Jump()
    │   ladder.start = ladderStart  (e.g., 8)
    │   ladder.end = ladderEnd      (e.g., 34)
    │
    ├─→ Attach to Cell:
    │   cell = getCell(ladderStart)
    │   cell.jump = ladder
    │
    └─→ numberOfLadders--

Result: 4 cells now have ladders (start < end)
```

---

## Position to Cell Mapping

```
1D Position (0-99) → 2D Coordinates

Formula:
  row = position / boardSize
  column = position % boardSize

Examples (10×10 board):

Position 0:
  row = 0 / 10 = 0
  col = 0 % 10 = 0
  → cells[0][0]

Position 47:
  row = 47 / 10 = 4
  col = 47 % 10 = 7
  → cells[4][7]

Position 99:
  row = 99 / 10 = 9
  col = 99 % 10 = 9
  → cells[9][9]

Visual Mapping:
┌─────┬─────┬─────┬─────┐
│  0  │  1  │  2  │  3  │  Row 0
├─────┼─────┼─────┼─────┤
│ 10  │ 11  │ 12  │ 13  │  Row 1
├─────┼─────┼─────┼─────┤
│ 20  │ 21  │ 22  │ 23  │  Row 2
└─────┴─────┴─────┴─────┘
```

---

## Game Turn Sequence (Detailed)

```
Turn N for Player-X:

1. Remove Player from Queue
   ═══════════════════════════
   playersList: [Player-X, Player-Y, ...]
                    ↓
   Player-X = removeFirst()
   playersList: [Player-Y, ...]

2. Roll Dice
   ══════════
   diceValue = dice.rollDice()
   [Random 1-6 for single die]
   [Sum if multiple dice]

3. Calculate New Position
   ════════════════════════
   currentPos = player.currentPosition
   newPos = currentPos + diceValue
   
   Example:
     currentPos = 45
     diceValue = 5
     newPos = 50

4. Jump Check
   ═══════════
   cell = board.getCell(50)
   
   Case A: Normal Cell
   ───────────────────
   cell.jump == null
   → finalPos = 50 (stay at rolled position)
   
   Case B: Snake Head
   ──────────────────
   cell.jump != null
   cell.jump.start = 50
   cell.jump.end = 12
   start > end → Snake!
   Print: "[+] Jump done by: Snake"
   → finalPos = 12 (slide down)
   
   Case C: Ladder Bottom
   ─────────────────────
   cell.jump != null
   cell.jump.start = 50
   cell.jump.end = 78
   start < end → Ladder!
   Print: "[+] Jump done by: Ladder"
   → finalPos = 78 (climb up)
   
   Case D: Position Overflow
   ─────────────────────────
   newPos = 105 (beyond board)
   → return 105 (overflow handled by win check)

5. Update Position
   ════════════════
   player.currentPosition = finalPos
   Print: "Player turn: [id] new Position is: [pos]"

6. Check Win Condition
   ════════════════════
   if (finalPos >= 99):
       winner = Player-X
       → Exit game loop
   else:
       → Continue to next turn

7. Add Player Back to Queue
   ══════════════════════════
   playersList.addLast(Player-X)
   playersList: [Player-Y, ..., Player-X]
   
   → Next player's turn
```

---

## Turn Management (Round-Robin)

```
Initial State:
┌────────────────────────────┐
│ Deque<Player>              │
│                            │
│ [Player-1, Player-2]       │
│    ↑front      ↑back       │
└────────────────────────────┘

Turn 1:
═══════
removeFirst() → Player-1
Deque: [Player-2]
... Player-1 plays ...
addLast(Player-1)
Deque: [Player-2, Player-1]

Turn 2:
═══════
removeFirst() → Player-2
Deque: [Player-1]
... Player-2 plays ...
addLast(Player-2)
Deque: [Player-1, Player-2]

Pattern repeats until winner found.
```

---

## Jump Detection Logic

```
When player lands on position P:

┌──────────────────────────────────┐
│ Is P within board bounds?        │
│ (P <= boardSize² - 1)            │
└──────────────────────────────────┘
         │
         │ YES
         ▼
┌──────────────────────────────────┐
│ cell = getCell(P)                │
│ Convert P to (row, col)          │
└──────────────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│ Does cell have a jump?           │
│ (cell.jump != null)              │
└──────────────────────────────────┘
         │
    ┌────┴────┐
   YES        NO
    │          │
    ▼          └─→ Stay at P
┌──────────────────────────────────┐
│ Is jump.start == P?              │
│ (Confirms this is jump origin)   │
└──────────────────────────────────┘
         │
    ┌────┴────┐
   YES        NO
    │          │
    ▼          └─→ Stay at P
┌──────────────────────────────────┐
│ Determine Jump Type:             │
│                                  │
│ if (start < end) → Ladder ↑      │
│ if (start > end) → Snake ↓       │
└──────────────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│ Move to jump.end                 │
│ Print jump type                  │
└──────────────────────────────────┘
```

---

## Example Game Trace

```
=== Game Initialization ===
Board: 10×10 (positions 0-99)
Snakes Placed:
  - Snake at 67 → 23 (down 44)
  - Snake at 54 → 19 (down 35)
  - Snake at 91 → 52 (down 39)
  - Snake at 78 → 38 (down 40)
  - Snake at 85 → 24 (down 61)

Ladders Placed:
  - Ladder at 9 → 34 (up 25)
  - Ladder at 14 → 56 (up 42)
  - Ladder at 27 → 83 (up 56)
  - Ladder at 41 → 72 (up 31)

Players:
  - Player-1: position 0
  - Player-2: position 0

=== Game Starts ===

Turn 1: Player-1
  Current: 0
  Roll: 4
  New: 0 + 4 = 4
  Jump: None
  Final: 4

Turn 2: Player-2
  Current: 0
  Roll: 6
  New: 0 + 6 = 6
  Jump: None
  Final: 6

Turn 3: Player-1
  Current: 4
  Roll: 5
  New: 4 + 5 = 9
  Jump: Ladder at 9 → 34
  Print: "[+] Jump done by: Ladder"
  Final: 34

Turn 4: Player-2
  Current: 6
  Roll: 2
  New: 6 + 2 = 8
  Jump: None
  Final: 8

Turn 5: Player-1
  Current: 34
  Roll: 6
  New: 34 + 6 = 40
  Jump: None
  Final: 40

Turn 6: Player-2
  Current: 8
  Roll: 6
  New: 8 + 6 = 14
  Jump: Ladder at 14 → 56
  Print: "[+] Jump done by: Ladder"
  Final: 56

...many turns later...

Turn 47: Player-1
  Current: 95
  Roll: 5
  New: 95 + 5 = 100
  Jump: Position overflow (100 > 99)
  Final: 100
  Win Check: 100 >= 99 → WINNER!

Output: "===> The Winner is: Player-1"
```

---

## Key Design Insights

### Why Jump is Dual-Purpose?
```
Same class represents both snakes and ladders:

Snake:
  start = 67  (high position)
  end = 23    (low position)
  start > end → downward movement

Ladder:
  start = 9   (low position)
  end = 34    (high position)
  start < end → upward movement

Benefit: Reduces code duplication, single jump logic
```

### Why Cell Has Optional Jump?
```
100 cells on board
Only 9 cells have jumps (5 snakes + 4 ladders)
91 cells are normal (no jump)

Using Optional Jump (null when not present):
  - Memory efficient: 91 cells save space
  - Simple check: if (jump != null)
  - Clear semantics: null = normal cell
```

### Why Deque for Players?
```
Alternative: Iterator + Index
  - Requires tracking current player index
  - Manual wraparound logic

Current: Deque
  - removeFirst() → O(1)
  - addLast() → O(1)
  - Automatic rotation
  - No index management needed
```

---

## Random Number Generation

### ThreadLocalRandom vs Random

```
Random (Not Used):
  - Needs instantiation: new Random()
  - Slower in concurrent scenarios
  - Requires object management

ThreadLocalRandom (Used):
  - Static access: ThreadLocalRandom.current()
  - Better performance
  - No object creation needed
  - Modern Java best practice (Java 7+)
```

### Dice Roll Distribution
```
Single Die (diceCount = 1):
  Range: [1, 6]
  Probabilities: Each value 1/6 (~16.67%)
  Average: 3.5

Two Dice (diceCount = 2):
  Range: [2, 12]
  Probabilities: Bell curve (7 most likely)
  Average: 7.0
```

---

## Edge Cases and Validation

### 1. Position Overflow
```
Player at 95, rolls 6 → position 101

Handled:
  jumpCheck(101):
    if (101 > 99) return 101  [Don't process jump]
  
  Win Check:
    if (101 >= 99) → Winner!  [Still wins]
```

### 2. Jump at Start Position Only
```
Why check: cell.jump.start == playerNewPosition?

Prevents: Player at position X triggering jump at position Y
Ensures: Jump only activates when landing exactly on start

Example:
  Jump at 50 → 78
  Player lands on 50: jump.start == 50 → Activate
  Player lands on 51: jump.start != 51 → No jump
```

### 3. Snake/Ladder Collision
```
Current: No collision detection
Possible: Two jumps assigned to same cell
         (second overwrites first)

Improvement:
  if (cell.jump != null) continue;  // Skip occupied cells
```

### 4. Invalid Placements
```
Snake with tail >= head → Rejected, retry
Ladder with top <= bottom → Rejected, retry

This ensures:
  - All snakes move down
  - All ladders move up
```

---

## Comparison with Similar Game (TicTacToe)

| Aspect                | Snake & Ladder        | TicTacToe             |
|-----------------------|-----------------------|-----------------------|
| **Board Type**        | Linear (1D → 2D map)  | True 2D grid          |
| **Player Pieces**     | Position tracking     | Pieces on board       |
| **Turn Management**   | Deque rotation        | Deque rotation        |
| **Win Condition**     | Reach end position    | 3 in a row            |
| **Randomness**        | High (dice + board)   | None                  |
| **User Input**        | None                  | Yes (row, col)        |
| **Game Length**       | Variable (luck-based) | 5-9 turns max         |
| **Board State**       | Player positions      | Piece placement       |
| **Complexity**        | Simple                | Simple                |

---

## System Characteristics

### 7 Classes Total:
- 1 Value Object (Jump)
- 3 Entities (Cell, Player, Board)
- 1 Service (Dice)
- 1 Controller (Game)
- 1 Client (SnakeNLadderDemo)

### Design Patterns:
- Composition (Game contains Board, Dice, Players)
- Queue Pattern (Turn management)
- Optional Pattern (Cell may have Jump)

### Relationships:
- 0 IS-A (no inheritance)
- 6 HAS-A (composition)
- 3 USES (dependency)

### Randomness:
- Board layout changes every game
- Dice rolls are unpredictable
- High replay value

---

## Data Flow Diagram

```
┌──────────────┐
│    Main      │
└──────┬───────┘
       │ creates
       ▼
┌──────────────┐
│    Game      │
└──────┬───────┘
       │ initializes
       ├────────────────────┐
       │                    │
       ▼                    ▼
┌──────────────┐    ┌─────────────┐
│    Board     │    │   Players   │
│              │    │   (Deque)   │
└──────┬───────┘    └─────┬───────┘
       │                  │
       │ contains         │ rotates
       ▼                  ▼
┌──────────────┐    ┌─────────────┐
│   Cells      │    │   Current   │
│   (grid)     │    │   Player    │
└──────┬───────┘    └─────┬───────┘
       │                  │
       │ may contain      │ rolls
       ▼                  ▼
┌──────────────┐    ┌─────────────┐
│    Jump      │    │    Dice     │
│ (snake/laddr)│    │             │
└──────────────┘    └─────────────┘
       │                  │
       │                  │
       └────────┬─────────┘
                │
                │ both affect
                ▼
         ┌─────────────┐
         │   Player    │
         │  Position   │
         │  (updated)  │
         └─────────────┘
```

---

## State Transitions

### Player Position States
```
START (Position 0)
    │
    ├─→ Roll Dice (1-6)
    │
    ▼
MOVED (Position + Dice)
    │
    ├─→ Check Cell at New Position
    │
    ├─→ If Snake: SLIDE DOWN
    │   └─→ Position = tail
    │
    ├─→ If Ladder: CLIMB UP
    │   └─→ Position = top
    │
    └─→ If Normal: STAY
        └─→ Position unchanged
    │
    ▼
CHECK WIN
    │
    ├─→ If Position >= 99: WIN STATE
    │   └─→ Game Over
    │
    └─→ Else: Continue
        └─→ Next Player Turn
```

---

## Memory Layout

```
Game Object
│
├─→ board: Board
│   │
│   └─→ cells: Cell[10][10]
│       │
│       ├─→ cells[0][0]: Cell { jump: null }
│       ├─→ cells[0][9]: Cell { jump: Jump(9→34) }  [Ladder]
│       ├─→ cells[6][7]: Cell { jump: Jump(67→23) } [Snake]
│       └─→ ... 97 more cells
│
├─→ dice: Dice
│   └─→ { diceCount: 1, min: 1, max: 6 }
│
├─→ playersList: Deque<Player>
│   ├─→ Player-1 { id: "Player-1", currentPosition: 34 }
│   └─→ Player-2 { id: "Player-2", currentPosition: 12 }
│
└─→ winner: null (or Player reference when game ends)
```

---

## Complete System Flow Chart

```
        START
          │
          ▼
    ┌─────────────┐
    │  Initialize │
    │   Game      │
    └─────┬───────┘
          │
          ├─→ Create Board (10×10)
          ├─→ Place 5 Snakes
          ├─→ Place 4 Ladders
          ├─→ Create 1 Dice
          └─→ Create 2 Players
          │
          ▼
    ┌─────────────┐
    │  Game Loop  │
    └─────┬───────┘
          │
          ▼
    ┌─────────────────────┐
    │ Get Current Player  │◄───────┐
    └─────┬───────────────┘        │
          │                        │
          ▼                        │
    ┌─────────────────────┐        │
    │    Roll Dice        │        │
    └─────┬───────────────┘        │
          │                        │
          ▼                        │
    ┌─────────────────────┐        │
    │  Move Forward       │        │
    └─────┬───────────────┘        │
          │                        │
          ▼                        │
    ┌─────────────────────┐        │
    │  Check for Jump     │        │
    └─────┬───────────────┘        │
          │                        │
     ┌────┴────┐                   │
   Snake    Ladder   None          │
     │        │        │            │
     ▼        ▼        ▼            │
  Move      Move     Stay           │
  Down       Up                     │
     │        │        │            │
     └────┬───┴────────┘            │
          │                        │
          ▼                        │
    ┌─────────────────────┐        │
    │ Update Position     │        │
    └─────┬───────────────┘        │
          │                        │
          ▼                        │
    ┌─────────────────────┐        │
    │  Check Win?         │        │
    │ (Position >= 99)    │        │
    └─────┬───────────────┘        │
          │                        │
     ┌────┴────┐                   │
    YES        NO                  │
     │          │                  │
     ▼          └──────────────────┘
    WIN                      Next Turn
     │
     ▼
┌─────────────┐
│ Announce    │
│  Winner     │
└─────────────┘
     │
     ▼
    END
```

---

## Complete Dependency List

| Class            | Direct Dependencies              | Transitive Dependencies |
|------------------|----------------------------------|-------------------------|
| Jump             | None                             | None                    |
| Cell             | Jump                             | None                    |
| Dice             | ThreadLocalRandom                | None                    |
| Player           | None                             | None                    |
| Board            | Cell, Jump, ThreadLocalRandom    | None                    |
| Game             | Board, Dice, Player, Deque       | Cell, Jump              |
| SnakeNLadderDemo | Game                             | All above               |

---

## Extensibility Examples

### Adding More Players
```java
private void addPlayers() {
    for (int i = 1; i <= 4; i++) {  // 4 players instead of 2
        Player player = new Player("Player-" + i, 0);
        playersList.add(player);
    }
}
```

### Adding Multiple Dice
```java
// In initializeGame():
dice = new Dice(2);  // Roll 2 dice

// Result: Range 2-12 instead of 1-6
```

### Customizable Board Size
```java
// In initializeGame():
board = new Board(15, 10, 8);  // 15×15 board, 10 snakes, 8 ladders
```

### Adding Special Rules
```java
// Roll 6 to start rule
private int getValidMove(Player player, int diceValue) {
    if (player.currentPosition == 0 && diceValue != 6) {
        return 0;  // Must roll 6 to start
    }
    return player.currentPosition + diceValue;
}

// Exact landing on 100
private boolean checkWin(int position) {
    if (position == 99) return true;     // Exact landing
    if (position > 99) return false;     // Overshoot = no win
    return false;
}
```

---

## Performance Analysis

### Best Case
```
Scenario: All rolls are 6, hit only ladders
Minimum turns: ~17 turns
Path: 0 → 6 → 12 → 18 → [ladder] → 60 → [ladder] → 99
```

### Worst Case
```
Scenario: Low rolls, hit many snakes
Maximum turns: Theoretically infinite (bad luck)
Realistic worst: ~200-300 turns
```

### Average Case
```
Based on probability:
- Average roll: 3.5
- Expected turns: ~30-50
- With snakes/ladders: Adds variance
```

### Time Complexity per Turn
```
O(1) for all operations:
  - Get player: O(1) [Deque]
  - Roll dice: O(1) [Random]
  - Get cell: O(1) [Array access]
  - Jump check: O(1) [Direct access]
  - Win check: O(1) [Comparison]
```

---

## Code Quality Metrics

### Lines of Code
- Jump: ~10 LOC
- Cell: ~9 LOC
- Dice: ~29 LOC
- Player: ~15 LOC
- Board: ~71 LOC
- Game: ~75 LOC
- Demo: ~11 LOC

**Total: ~220 LOC** (most compact LLD)

### Cyclomatic Complexity
- Jump: 1 (simple data)
- Cell: 1 (simple data)
- Dice: 2 (one loop)
- Player: 1 (simple data)
- Board: 5 (initialization loops, validation)
- Game: 6 (game loop, conditions)
- Demo: 1 (single call)

**Average: 2.4** (low complexity = easy to understand)

---

## Testing Strategy

### Unit Tests
```java
@Test
void testDiceRoll() {
    Dice dice = new Dice(1);
    int roll = dice.rollDice();
    assertTrue(roll >= 1 && roll <= 6);
}

@Test
void testBoardSize() {
    Board board = new Board(10, 0, 0);
    assertEquals(100, board.cells.length * board.cells.length);
}

@Test
void testPlayerMovement() {
    Player player = new Player("Test", 0);
    player.currentPosition = 10;
    assertEquals(10, player.currentPosition);
}

@Test
void testJumpDetection() {
    // Create board with known snake at position 50 → 10
    // Move player to 50
    // Verify position becomes 10
}

@Test
void testWinCondition() {
    // Move player to position 99
    // Verify winner is set
}
```

---

## Why This Design?

### Intentional Simplicity
This LLD demonstrates that not every system needs:
- Strategy patterns
- Factory patterns
- Dependency injection
- Thread safety
- Complex architectures

**When to use simple design:**
- Small domain (single-player board game)
- No extensibility requirements
- No concurrent access
- Clear, fixed rules
- Educational purposes

**When to use complex design (like Parking Lot/Car Rental):**
- Enterprise systems
- Multiple strategies needed
- Concurrent users
- Extensibility required
- Production-grade systems

---

## Learning Objectives

This Snake and Ladder implementation teaches:

1. **Basic OOP**: Classes, objects, composition
2. **Data Structures**: 2D arrays, Deque
3. **Game Logic**: Turn management, win conditions
4. **Randomness**: Using Java's random APIs
5. **Control Flow**: Loops, conditionals
6. **Encapsulation**: Private methods, public interfaces

Perfect starting point before tackling complex systems like Parking Lot or Car Rental.
