# Custom HashMap Implementation - Complete UML and Architecture

## Full System Architecture

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    CUSTOM HASHMAP DATA STRUCTURE                            ┃
┃                         (3 Classes, Fundamental DS)                         ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 1: NODE (Entry for Linked List)                                      │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────┐
                        │      Entry<K, V>           │
                        ├────────────────────────────┤
                        │ Dependencies:              │
                        │ - Entry<K,V> (self-ref)    │
                        ├────────────────────────────┤
                        │ + key: K                   │
                        │ + value: V                 │
                        │ + next: Entry<K, V>        │──────► HAS-A Entry<K, V>
                        ├────────────────────────────┤
                        │ + Entry(key, value)        │
                        │ + getKey(): K              │
                        │ + getValue(): V            │
                        │ + setValue(value): void    │
                        │ + getNext(): Entry         │
                        │ + setNext(next): void      │
                        │ + toString(): String       │
                        └────────────────────────────┘
                                    │
                                    │ used by (array of)
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 2: HASH TABLE (Uses Entry Array)                                     │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────┐
                │         MyHashMap<K, V>                      │
                ├──────────────────────────────────────────────┤
                │ Dependencies:                                │
                │ - Entry<K, V>[] (HAS-A array)                │
                ├──────────────────────────────────────────────┤
                │ - INITIAL_CAPACITY: int = 16                 │
                │ - LOAD_FACTOR: float = 0.75                  │
                │ - buckets: Entry<K, V>[]                     │──────► HAS-A Entry[]
                │ - size: int                                  │
                │ - capacity: int                              │
                ├──────────────────────────────────────────────┤
                │ + MyHashMap()                                │
                │ + MyHashMap(initialCapacity)                 │
                │ + put(key, value): void                      │
                │ + get(key): V                                │
                │ + remove(key): V                             │
                │ + containsKey(key): boolean                  │
                │ + size(): int                                │
                │ + isEmpty(): boolean                         │
                │ + clear(): void                              │
                │ + printHashMap(): void                       │
                │ - getBucketIndex(key): int                   │
                │ - putNullKey(value): void                    │
                │ - getNullKey(): V                            │
                │ - removeNullKey(): V                         │
                │ - resize(): void                             │
                └──────────────────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 3: CLIENT (Demo Application)                                         │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────┐
                        │    HashMapDemo (Main)      │
                        ├────────────────────────────┤
                        │ Dependencies:              │
                        │ - MyHashMap (creates)      │
                        ├────────────────────────────┤
                        │ + main(args): void         │
                        └────────────────────────────┘
```

---

## Complete Dependency Graph

```
LAYER 1: NODE STRUCTURE
═══════════════════════
┌─────────────────┐
│  Entry<K, V>    │  (self-referential for linked list)
│      ↓          │
│  Entry<K, V>    │  (next pointer)
└─────────────────┘


LAYER 2: HASH TABLE
═══════════════════
┌─────────────────────┐
│  MyHashMap<K, V>    │
│        ↓            │
│  Entry<K, V>[]      │  (array of buckets)
│        ↓            │
│  Entry<K, V>        │  (linked list nodes)
└─────────────────────┘


LAYER 3: CLIENT
═══════════════
┌─────────────────────┐
│  HashMapDemo        │
│        ↓            │
│  MyHashMap<K, V>    │
└─────────────────────┘
```

---

## Internal Structure Visualization

### Empty HashMap
```
MyHashMap:
  capacity = 16
  size = 0
  buckets = Entry[16]

┌──────┬──────┬──────┬──────┬──────┬──────┐
│ null │ null │ null │ null │ null │ null │ ... (16 total)
└──────┴──────┴──────┴──────┴──────┴──────┘
  [0]    [1]    [2]    [3]    [4]    [5]
```

### HashMap with Entries (No Collisions)
```
After: put("A", 1), put("B", 2), put("C", 3)
Assume: "A"→bucket[3], "B"→bucket[7], "C"→bucket[12]

┌──────┬──────┬──────┬─────────┬──────┬──────┬──────┬─────────┬─────────┐
│ null │ null │ null │ [A→1]   │ null │ null │ null │ [B→2]   │ [C→3]   │ ...
└──────┴──────┴──────┴─────────┴──────┴──────┴──────┴─────────┴─────────┘
  [0]    [1]    [2]     [3]      [4]    [5]    [6]     [7]       [12]

size = 3
capacity = 16
load = 3/16 = 0.1875 (healthy)
```

### HashMap with Collisions
```
After: put(1, "A"), put(5, "B"), put(9, "C")  [capacity = 4]
All hash to bucket[1]: 1%4=1, 5%4=1, 9%4=1

┌──────┬─────────────────────────────────────┬──────┬──────┐
│ null │ [9→C] → [5→B] → [1→A] → null       │ null │ null │
└──────┴─────────────────────────────────────┴──────┴──────┘
  [0]                [1]                       [2]    [3]

Linked List in bucket[1]:
  Head → Entry(9, "C")
           └→ next: Entry(5, "B")
                      └→ next: Entry(1, "A")
                                 └→ next: null

size = 3
capacity = 4
load = 3/4 = 0.75 (at threshold, will resize on next insert)
```

---

## PUT Operation (Detailed Flow)

```
put(key="Apple", value=10):

┌──────────────────────────────────┐
│ 1. Null Key Check                │
│    key == null?                  │
└────────┬─────────────────────────┘
         │ NO
         ▼
┌──────────────────────────────────┐
│ 2. Calculate Bucket Index        │
│    hashCode = key.hashCode()     │
│    = "Apple".hashCode()          │
│    = 63476538                    │
│                                  │
│    index = |hashCode| % capacity │
│    = 63476538 % 16               │
│    = 10                          │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│ 3. Get Bucket                    │
│    existing = buckets[10]        │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│ 4. Search Chain for Key          │
│    while (existing != null):     │
│        if (existing.key == key): │
│            existing.value = val  │
│            return [UPDATE]       │
│        existing = existing.next  │
└────────┬─────────────────────────┘
         │ Key not found
         ▼
┌──────────────────────────────────┐
│ 5. Create New Entry              │
│    newEntry = new Entry(key, val)│
│                                  │
│    newEntry.next = buckets[10]   │
│    buckets[10] = newEntry        │
│    [Insert at head]              │
│                                  │
│    size++                        │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│ 6. Check Load Factor             │
│    if (size >= capacity × 0.75): │
│        resize()                  │
└──────────────────────────────────┘
```

---

## RESIZE Operation (Detailed Flow)

```
resize():

Initial State:
  capacity = 16
  size = 12
  load = 12/16 = 0.75

Step 1: Save Old Buckets
────────────────────────
oldBuckets = buckets
[Reference to old Entry[16] array]

Step 2: Create New Array
─────────────────────────
newCapacity = 16 × 2 = 32
buckets = new Entry[32]
capacity = 32
size = 0

State:
  Old Array: Entry[16] with 12 entries
  New Array: Entry[32] all null
  size reset to 0

Step 3: Rehash All Entries
───────────────────────────
for (i = 0; i < 16; i++):
    entry = oldBuckets[i]
    while (entry != null):
        
        Example: entry.key = "Apple", entry.value = 10
        
        Old Index: 63476538 % 16 = 10
        New Index: 63476538 % 32 = 10 (happens to be same)
        
        put(entry.key, entry.value)
        [Inserts into new buckets array]
        
        entry = entry.next

Result:
  capacity = 32
  size = 12 (restored)
  load = 12/32 = 0.375 (healthy again)
  
All entries redistributed in larger array.
```

---

## Hash Code Distribution

### Example Hash Codes
```
String hash codes (Java):

"A"      → 65
"B"      → 66
"Apple"  → 63476538
"Banana" → -1396355227 (negative!)
"Test"   → 2603186

Bucket calculation (capacity = 16):
  "A"      → |65| % 16 = 1
  "B"      → |66| % 16 = 2
  "Apple"  → |63476538| % 16 = 10
  "Banana" → |1396355227| % 16 = 3
  "Test"   → |2603186| % 16 = 2  [Collision with "B"!]
```

### Integer Hash Codes
```
Integer.hashCode(int value) = value

Examples:
  1.hashCode()  = 1  → 1 % 16 = 1
  5.hashCode()  = 5  → 5 % 16 = 5
  17.hashCode() = 17 → 17 % 16 = 1  [Collision with 1]
  33.hashCode() = 33 → 33 % 16 = 1  [Collision with 1 and 17]

Pattern: All numbers ≡ 1 (mod 16) collide
```

---

## Collision Chain Management

### Insert at Head (Used)
```
Existing: bucket[3] → [B→2] → [A→1] → null

Insert C→3:
  newEntry = Entry(C, 3)
  newEntry.next = bucket[3]  [Point to current head]
  bucket[3] = newEntry       [Update bucket to new head]

Result: bucket[3] → [C→3] → [B→2] → [A→1] → null

Advantage: O(1) insertion (no traversal needed)
Order: Most recent first (useful for LRU cache)
```

### Insert at Tail (Alternative)
```
Existing: bucket[3] → [A→1] → [B→2] → null

Insert C→3:
  Traverse to end: O(n)
  last.next = Entry(C, 3)

Result: bucket[3] → [A→1] → [B→2] → [C→3] → null

Advantage: Maintains insertion order
Disadvantage: O(n) insertion (must traverse)
```

---

## Complete PUT Operation Call Chain

```
1. Client Code
   map.put("Apple", 10)
       │
       ▼
2. MyHashMap.put(K key, V value)
       │
       ├─→ if (key == null)
       │   │
       │   └─→ putNullKey(value)
       │       │
       │       └─→ Search bucket[0] for null key
       │           ├─→ If found: update value
       │           └─→ If not: insert at head
       │
       ├─→ bucketIndex = getBucketIndex(key)
       │   │
       │   └─→ hashCode = key.hashCode()
       │       absHash = Math.abs(hashCode)
       │       index = absHash % capacity
       │       return index
       │
       ├─→ existing = buckets[bucketIndex]
       │
       ├─→ Search chain:
       │   while (existing != null):
       │       if (existing.key.equals(key)):
       │           existing.value = value  [UPDATE CASE]
       │           return
       │       existing = existing.next
       │
       ├─→ Key not found, insert new:
       │   newEntry = new Entry<>(key, value)
       │       │
       │       └─→ Entry Constructor:
       │           this.key = key
       │           this.value = value
       │           this.next = null
       │
       │   newEntry.next = buckets[bucketIndex]
       │   buckets[bucketIndex] = newEntry
       │   size++
       │
       └─→ Check resize:
           if (size >= capacity × LOAD_FACTOR):
               resize()
                   │
                   └─→ newCapacity = capacity × 2
                       oldBuckets = buckets
                       buckets = new Entry[newCapacity]
                       capacity = newCapacity
                       size = 0
                       
                       for each oldBucket:
                           for each entry:
                               put(entry.key, entry.value)
                               [Recursive call with new capacity]
```

---

## Complete GET Operation Call Chain

```
1. Client Code
   value = map.get("Apple")
       │
       ▼
2. MyHashMap.get(K key)
       │
       ├─→ if (key == null)
       │   │
       │   └─→ return getNullKey()
       │       │
       │       └─→ Search bucket[0] for null key
       │           return value or null
       │
       ├─→ bucketIndex = getBucketIndex(key)
       │   │
       │   └─→ hashCode = key.hashCode()
       │       index = |hashCode| % capacity
       │       return index
       │
       ├─→ entry = buckets[bucketIndex]
       │
       └─→ Search chain:
           while (entry != null):
               │
               ├─→ if (entry.key.equals(key)):
               │   │
               │   └─→ return entry.value  [FOUND]
               │
               └─→ entry = entry.next
           
           return null  [NOT FOUND]
```

---

## Complete REMOVE Operation Call Chain

```
1. Client Code
   oldValue = map.remove("Apple")
       │
       ▼
2. MyHashMap.remove(K key)
       │
       ├─→ if (key == null)
       │   │
       │   └─→ return removeNullKey()
       │       [Similar to getNullKey but removes from chain]
       │
       ├─→ bucketIndex = getBucketIndex(key)
       │
       ├─→ entry = buckets[bucketIndex]
       │   previous = null
       │
       └─→ Search with previous tracking:
           while (entry != null):
               │
               if (entry.key.equals(key)):
                   [FOUND - Remove from chain]
                   │
                   ├─→ Case A: Entry is head (previous == null)
                   │   buckets[bucketIndex] = entry.next
                   │   
                   │   Before: bucket[i] → [A] → [B] → null
                   │   After:  bucket[i] → [B] → null
                   │
                   ├─→ Case B: Entry is not head
                   │   previous.next = entry.next
                   │   
                   │   Before: [X] → [A] → [B] → null
                   │   After:  [X] → [B] → null
                   │
                   ├─→ size--
                   └─→ return entry.value
               
               previous = entry
               entry = entry.next
           
           return null  [NOT FOUND]
```

---

## Relationship Matrix

| Class        | Depends On        | Relationship Type           |
|--------------|-------------------|-----------------------------|
| Entry<K, V>  | Entry<K, V>       | HAS-A (self-reference)      |
| MyHashMap    | Entry<K, V>[]     | HAS-A (array)               |
| HashMapDemo  | MyHashMap         | creates, uses               |

---

## Memory Layout Example

```
MyHashMap with 3 entries in capacity 4:

Heap Memory:
┌────────────────────────────────────────────┐
│ MyHashMap Object                           │
│ ├─ buckets: reference → Entry[4]          │
│ ├─ size: 3                                 │
│ └─ capacity: 4                             │
└────────────────────────────────────────────┘
         │
         │ points to
         ▼
┌────────────────────────────────────────────┐
│ Entry Array [4]                            │
│ ├─ [0]: null                               │
│ ├─ [1]: reference → Entry(9, "Nine")      │──┐
│ ├─ [2]: null                               │  │
│ └─ [3]: null                               │  │
└────────────────────────────────────────────┘  │
                                                │
         ┌──────────────────────────────────────┘
         │
         ▼
┌────────────────────────────────────────────┐
│ Entry(9, "Nine") Object                    │
│ ├─ key: 9                                  │
│ ├─ value: "Nine"                           │
│ └─ next: reference → Entry(5, "Five")     │──┐
└────────────────────────────────────────────┘  │
                                                │
         ┌──────────────────────────────────────┘
         │
         ▼
┌────────────────────────────────────────────┐
│ Entry(5, "Five") Object                    │
│ ├─ key: 5                                  │
│ ├─ value: "Five"                           │
│ └─ next: reference → Entry(1, "One")      │──┐
└────────────────────────────────────────────┘  │
                                                │
         ┌──────────────────────────────────────┘
         │
         ▼
┌────────────────────────────────────────────┐
│ Entry(1, "One") Object                     │
│ ├─ key: 1                                  │
│ ├─ value: "One"                            │
│ └─ next: null                              │
└────────────────────────────────────────────┘

Total Memory:
  MyHashMap: ~48 bytes
  Entry[4]:  4 × 8 = 32 bytes
  Entry(9):  ~32 bytes
  Entry(5):  ~32 bytes
  Entry(1):  ~32 bytes
  ────────────────────
  Total:     ~176 bytes
```

---

## Bucket Distribution Analysis

### Ideal Distribution (No Collisions)
```
100 entries, capacity 128:

Ideal: Each bucket has ≤1 entry
└─→ All operations O(1)

┌───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 1 │ 1 │ 1 │ 0 │ 1 │ 0 │ 1 │ ... (roughly uniform)
└───┴───┴───┴───┴───┴───┴───┴───┘
 [0] [1] [2] [3] [4] [5] [6] [7]

Average chain length: 100/128 = 0.78
```

### Poor Distribution (Many Collisions)
```
100 entries, capacity 128, but bad hash function:

┌────┬───┬───┬───┬───┬───┬───┬───┐
│ 50 │ 0 │ 0 │ 30│ 0 │ 20│ 0 │ 0 │ ... (clustered)
└────┴───┴───┴───┴───┴───┴───┴───┘
 [0]  [1] [2] [3] [4] [5] [6] [7]

Average chain length: Still 0.78
But: Worst-case lookup O(50) for bucket[0]!

Importance of good hash function!
```

---

## Resize Impact Analysis

### Before Resize
```
Capacity: 16
Size: 12
Load: 0.75

Bucket Distribution (example):
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 2 │ 1 │ 0 │ 2 │ 1 │ 0 │ 1 │ 0 │ 1 │ 0 │ 2 │ 1 │ 0 │ 1 │ 0 │ 0 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
 [0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15]

Max chain: 2
Average chain: 0.75
```

### After Resize
```
Capacity: 32
Size: 12
Load: 0.375

Entries redistributed:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 1 │ 0 │ 1 │ 1 │ 0 │ 1 │ 0 │ 1 │ 0 │ 1 │ 1 │ 0 │ 1 │ 0 │ 0 │ 1 │ 0 │ 1 │ 0 │ 0 │ 0 │ 0 │ 0 │ 0 │ 0 │ 1 │ 0 │ 0 │ 0 │ 0 │ 0 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
 [0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15][16][17][18][19][20][21][22][23][24][25][26][27][28][29][30][31]

Max chain: 1
Average chain: 0.375

Benefits:
  - Fewer collisions
  - Faster lookups
  - More empty buckets (ready for new entries)
```

---

## Step-by-Step Example (Demo Execution)

```
=== Execution Trace ===

1. map = new MyHashMap<>();
   ─────────────────────────────
   capacity: 16
   size: 0
   buckets: Entry[16] (all null)

2. map.put("Apple", 10);
   ─────────────────────
   hash("Apple") % 16 = 10
   buckets[10] = Entry("Apple", 10)
   size: 1

3. map.put("Banana", 20);
   ──────────────────────
   hash("Banana") % 16 = 3
   buckets[3] = Entry("Banana", 20)
   size: 2

4. map.put("Cherry", 30);
   ──────────────────────
   Assume hash("Cherry") % 16 = 3 [COLLISION!]
   bucket[3]: Entry("Banana", 20)
   Insert at head: buckets[3] = Entry("Cherry", 30) → Entry("Banana", 20)
   size: 3

5. value = map.get("Banana");
   ──────────────────────────
   index = 3
   Search bucket[3]:
     - Check Entry("Cherry"): key != "Banana"
     - Check Entry("Banana"): key == "Banana" ✓
   return 20

6. map.put("Apple", 100);  [UPDATE]
   ──────────────────────────────
   index = 10
   Search bucket[10]:
     - Entry("Apple"): key == "Apple" ✓
     - Update value: 10 → 100
   size: 3 (unchanged)

7. map.remove("Cherry");
   ─────────────────────
   index = 3
   bucket[3]: Entry("Cherry") → Entry("Banana")
   previous = null
   Match at head → buckets[3] = Entry("Banana")
   size: 2

8. map.put(null, 999);
   ───────────────────
   Special handling: bucket[0]
   Insert: buckets[0] = Entry(null, 999)
   size: 3

9. Trigger Resize (add 10 more entries):
   ─────────────────────────────────────
   size reaches 12 → 12 >= 16×0.75
   Resize triggered:
     - New capacity: 32
     - Rehash all 12 entries
     - Load: 12/32 = 0.375
```

---

## Algorithm Complexity Proof

### Average Case O(1)

**Assumptions:**
- Good hash function (uniform distribution)
- Load factor ≤ 0.75

**Proof:**
```
n = number of entries
m = capacity

Average chain length = n/m = load factor ≤ 0.75

Operations:
  - Calculate hash: O(1)
  - Get bucket: O(1)
  - Traverse chain: O(n/m) = O(0.75) = O(1)

Therefore: Average case is O(1)
```

### Worst Case O(n)

**Scenario:** All keys hash to same bucket

**Proof:**
```
n entries all in bucket[0]:
  bucket[0] → E₁ → E₂ → E₃ → ... → Eₙ → null

To find last entry:
  - Must traverse entire chain: O(n)

To insert (check for duplicate):
  - Must traverse entire chain: O(n)

Therefore: Worst case is O(n)
```

### Amortized Resize Cost

**Analysis:**
```
Resize happens at: 12, 24, 48, 96, ...
Resize cost: n (rehash all entries)

Total cost for n operations:
  = n × O(1) + (log n) × O(n)
  = O(n) + O(n log n)
  = O(n log n)

Amortized per operation:
  = O(n log n) / n
  = O(log n)

But with table doubling:
  Actual amortized: O(1)
```

---

## Comparison: Array vs HashMap

### Array
```
int[] array = new int[100];

Access by index: O(1)
  array[5] = 10;
  value = array[5];

Search by value: O(n)
  for (int i = 0; i < array.length; i++) {
      if (array[i] == target) return i;
  }
```

### HashMap
```
MyHashMap<Integer, String> map = new MyHashMap<>();

Access by key: O(1) average
  map.put(5, "value");
  value = map.get(5);

Search by key: O(1) average
  boolean exists = map.containsKey(5);
```

**When to use HashMap:**
- Need fast lookup by arbitrary keys
- Keys are not sequential integers
- Frequent insertions/deletions
- Don't need order preservation

---

## Linked List in Bucket (Detailed)

### Structure
```
Entry objects form singly linked list:

Entry A:
  ┌─────────────┐
  │ key: "A"    │
  │ value: 1    │
  │ next: ───┐  │
  └──────────│──┘
             │
             └───────────────┐
                             │
Entry B:                     │
  ┌─────────────┐           │
  │ key: "B"    │  ◄────────┘
  │ value: 2    │
  │ next: ───┐  │
  └──────────│──┘
             │
             └───────────────┐
                             │
Entry C:                     │
  ┌─────────────┐           │
  │ key: "C"    │  ◄────────┘
  │ value: 3    │
  │ next: null  │
  └─────────────┘

Traversal:
  start = Entry A
  → next → Entry B
  → next → Entry C
  → next → null (end)
```

---

## Hash Function Quality Metrics

### Uniform Distribution Test
```
Test: Insert 1000 random strings into capacity 128

Good Hash Function:
  Expected entries per bucket: 1000/128 ≈ 7.8
  Std deviation: Low (5-10)
  Max chain length: ~15

Bad Hash Function:
  Expected: 7.8
  Std deviation: High (20+)
  Max chain length: 100+ (many collisions)

Java's String.hashCode() is a good hash function.
```

### Collision Rate
```
For n entries and m buckets:

Expected number of collisions (Birthday Paradox):
  ≈ n - m + m(1 - 1/m)ⁿ

Example (n=12, m=16):
  ≈ 12 - 16 + 16(1 - 1/16)¹²
  ≈ 3-4 collisions

Observed in practice: Similar (validates theory)
```

---

## Load Factor Scenarios

### Scenario 1: Load = 0.25
```
capacity = 16, size = 4

Distribution:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 0 │ 1 │ 0 │ 0 │ 1 │ 0 │ 0 │ 1 │ 0 │ 0 │ 0 │ 0 │ 0 │ 0 │ 0 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

Pros: Very fast lookups (almost no collisions)
Cons: Wasting memory (12 empty buckets)
```

### Scenario 2: Load = 0.75 (Target)
```
capacity = 16, size = 12

Distribution:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 2 │ 1 │ 1 │ 2 │ 1 │ 0 │ 1 │ 0 │ 1 │ 1 │ 2 │ 0 │ 0 │ 0 │ 0 │ 0 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

Pros: Good balance, some collisions but manageable
Cons: Approaching resize threshold
```

### Scenario 3: Load = 1.0
```
capacity = 16, size = 16

Distribution:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 3 │ 2 │ 1 │ 2 │ 1 │ 2 │ 1 │ 0 │ 1 │ 0 │ 1 │ 1 │ 0 │ 0 │ 0 │ 0 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

Pros: Memory efficient
Cons: Many collisions, slower lookups (some chains length 3)
```

---

## Complete Initialization Flow

```
new MyHashMap<String, Integer>()
    │
    └─→ Constructor:
        │
        ├─→ this.capacity = INITIAL_CAPACITY (16)
        │
        ├─→ this.buckets = new Entry[16]
        │   │
        │   └─→ Creates array:
        │       [null, null, null, ..., null] (16 nulls)
        │
        └─→ this.size = 0

Result: Empty hash map ready for use
```

---

## Null Key Storage Strategy

```
Why bucket[0] for null keys?

Rationale:
  - Need special handling (can't call null.hashCode())
  - Could use separate field, but wastes space
  - Using bucket[0] is elegant and consistent

Implementation:
  bucket[0] can contain:
    - Regular entries (keys that hash to 0)
    - Null key entry (key == null)
    - Both (mixed in same chain!)

Example bucket[0]:
  Entry(null, 999) → Entry("X", 10) → null
  
  where "X".hashCode() % 16 = 0
```

---

## Key Design Decisions

### 1. Why Separate Chaining?
```
Alternatives:
  a) Open Addressing (Linear/Quadratic Probing)
  b) Separate Chaining (Linked Lists) ← CHOSEN
  c) Cuckoo Hashing
  d) Robin Hood Hashing

Chosen because:
  ✓ Simplest to implement
  ✓ No clustering issues
  ✓ Easy deletion
  ✓ No need for tombstones
  ✓ Never "full"
  
Drawback:
  ✗ Extra memory for pointers
  ✗ Poor cache locality
```

### 2. Why Load Factor 0.75?
```
Trade-off Analysis:

Load Factor 0.5:
  - Resize more frequently
  - More memory used
  - Fewer collisions

Load Factor 0.75: ← CHOSEN
  - Industry standard
  - Good balance
  - Used by Java HashMap

Load Factor 0.9:
  - Resize less frequently
  - Less memory used
  - More collisions
```

### 3. Why Power-of-2 Capacity?
```
This implementation: Uses any capacity (16, 32, 64...)

Alternative: Force power of 2

Benefit of power-of-2:
  index = hash & (capacity - 1)  [Bitwise AND, faster]
  
Instead of:
  index = hash % capacity        [Modulo, slower]

Example:
  hash = 47, capacity = 16
  
  Method 1: 47 % 16 = 15
  Method 2: 47 & 15 = 0b101111 & 0b001111 = 0b001111 = 15
  
  Bitwise AND is ~2-3x faster than modulo.
  
Java HashMap uses this optimization.
```

---

## Real Implementation Optimizations (Not in This Code)

### 1. Tree Conversion (Java 8+)
```
When chain length > 8:
  Convert linked list → Red-Black Tree
  
Before: [E1] → [E2] → [E3] → ... → [E10] → null
         O(10) lookup

After:       [E5]
           /      \
        [E2]      [E8]
        /  \      /  \
      [E1][E3] [E7] [E9]
                     \
                     [E10]
         O(log 10) lookup
```

### 2. Hash Mixing
```
static int hash(Object key) {
    int h = key.hashCode();
    // Mix bits to improve distribution
    h ^= (h >>> 16);
    return h;
}

Reason: Spread high bits to low bits for better distribution
```

### 3. Capacity as Power of 2
```
// Ensure capacity is power of 2
int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return n + 1;
}

Result: Always rounds up to nearest power of 2
  10 → 16
  20 → 32
  50 → 64
```

---

## Performance Benchmarks (Theoretical)

```
Operation: 1 million put/get operations

MyHashMap (our implementation):
  put:    ~2-3 ms (average)
  get:    ~1-2 ms (average)
  Total:  ~3-5 seconds for 1M operations

Java HashMap (optimized):
  put:    ~1-2 ms (with tree conversion)
  get:    ~0.5-1 ms
  Total:  ~1.5-3 seconds

Our implementation: ~2x slower (acceptable for learning!)
```

---

## System at a Glance

```
3 Classes Total:
  - 1 Node (Entry)
  - 1 Data Structure (MyHashMap)
  - 1 Client (HashMapDemo)

Core Concepts:
  - Hashing
  - Collision Resolution
  - Dynamic Resizing
  - Linked Lists
  - Generics

Relationships:
  - 1 HAS-A (self-reference in Entry)
  - 1 HAS-A (MyHashMap contains Entry array)
  - 1 USES (Demo uses MyHashMap)

Complexity:
  - Average: O(1) for put/get/remove
  - Worst: O(n) when all collide
  - Space: O(n + m) where n=entries, m=capacity
```

---

## Interview Topics Covered

1. **Hash Table Fundamentals**: Array + Linked List
2. **Hash Function**: Converting key to index
3. **Collision Resolution**: Separate chaining technique
4. **Load Factor**: When and why to resize
5. **Dynamic Resizing**: Rehashing process
6. **Null Handling**: Special case management
7. **Generic Programming**: Type parameters
8. **Linked List Operations**: Insert, search, delete
9. **Time Complexity**: Average vs worst case
10. **Space Complexity**: Memory usage analysis

This implementation is commonly asked in coding interviews for system design and data structure questions!
