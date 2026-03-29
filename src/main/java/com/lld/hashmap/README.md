# Custom HashMap Implementation - Design Documentation

## All LLD Objects in this Repository

| #  | LLD Object          | Folder          |
|----|---------------------|-----------------|
| 1  | Parking Lot System  | `parking_Lot/`  |
| 2  | TicTacToe Game      | `TicTacToe/`    |
| 3  | Car Rental System   | `carRental/`    |
| 4  | Snake and Ladder    | `snakeNladder/` |
| 5  | HashMap Implementation | `hashmap/`   |

## Entities Used (3 Classes)

| #  | Entity          | Type              | Package  |
|----|-----------------|-------------------|----------|
| 1  | Entry<K, V>     | Node / Entity     | hashmap  |
| 2  | MyHashMap<K, V> | Data Structure    | hashmap  |
| 3  | HashMapDemo     | Main / Client     | hashmap  |

---

## Overview
A custom implementation of HashMap data structure from scratch, demonstrating fundamental concepts of hash tables including hashing, collision handling via chaining, dynamic resizing, and generic programming. The implementation supports null keys, automatic capacity expansion, and provides O(1) average-case performance for put/get operations.

---

## UML Class Diagram

```
┌─────────────────────────────────────────┐
│        Entry<K, V>                      │
├─────────────────────────────────────────┤
│ + key: K                                │
│ + value: V                              │
│ + next: Entry<K, V>                     │──────► HAS-A Entry<K, V> (self-reference)
├─────────────────────────────────────────┤
│ + Entry(key, value)                     │
│ + getKey(): K                           │
│ + getValue(): V                         │
│ + setValue(value): void                 │
│ + getNext(): Entry<K, V>                │
│ + setNext(next): void                   │
│ + toString(): String                    │
└─────────────────────────────────────────┘
           △
           │ HAS-A (array of)
           │
┌─────────────────────────────────────────────────┐
│          MyHashMap<K, V>                        │
├─────────────────────────────────────────────────┤
│ - INITIAL_CAPACITY: int = 16                    │
│ - LOAD_FACTOR: float = 0.75                     │
│ - buckets: Entry<K, V>[]                        │──────► HAS-A Entry<K, V>[]
│ - size: int                                     │
│ - capacity: int                                 │
├─────────────────────────────────────────────────┤
│ + MyHashMap()                                   │
│ + MyHashMap(initialCapacity)                    │
│ + put(key, value): void                         │
│ + get(key): V                                   │
│ + remove(key): V                                │
│ + containsKey(key): boolean                     │
│ + size(): int                                   │
│ + isEmpty(): boolean                            │
│ + clear(): void                                 │
│ + printHashMap(): void                          │
│ - getBucketIndex(key): int                      │
│ - putNullKey(value): void                       │
│ - getNullKey(): V                               │
│ - removeNullKey(): V                            │
│ - resize(): void                                │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│         HashMapDemo (Main)                      │
├─────────────────────────────────────────────────┤
│ + main(args): void                              │──────► USES MyHashMap
└─────────────────────────────────────────────────┘
```

---

## Relationships

### HAS-A (Composition) Relationships

1. **Entry HAS-A Entry (next)** (Self-reference)
   - Forms linked list for collision handling
   - Points to next entry in same bucket

2. **MyHashMap HAS-A Entry[]** (Composition)
   - Array of buckets, each bucket is a linked list

---

## Class Details

### 1. Entry<K, V> (Node/Entity)
**Purpose:** Represents a key-value pair with chaining support

**Attributes:**
- `key: K` - The key (can be null)
- `value: V` - The value
- `next: Entry<K, V>` - Reference to next entry (for collision handling)

**Methods:**
- `Entry(K key, V value)` - Constructor
- `getKey(): K` - Returns key
- `getValue(): V` - Returns value
- `setValue(V value): void` - Updates value
- `getNext(): Entry<K, V>` - Returns next entry
- `setNext(Entry<K, V> next): void` - Sets next entry
- `toString(): String` - Returns "key=value" format

**Relationships:**
- HAS-A Entry<K, V> (self-reference for linked list)

**Design Pattern:** Node in singly linked list

---

### 2. MyHashMap<K, V> (Data Structure)
**Purpose:** Custom hash table implementation with separate chaining

**Constants:**
- `INITIAL_CAPACITY: int = 16` - Default bucket array size
- `LOAD_FACTOR: float = 0.75` - Threshold for resizing (75% full)

**Attributes:**
- `buckets: Entry<K, V>[]` - Array of linked lists
- `size: int` - Number of key-value pairs stored
- `capacity: int` - Current bucket array size

**Methods:**

#### Public API:
- `MyHashMap()` - Constructor with default capacity (16)
- `MyHashMap(int initialCapacity)` - Constructor with custom capacity
- `put(K key, V value): void`
  - Inserts or updates key-value pair
  - Handles null keys (stored in bucket 0)
  - Triggers resize if load factor exceeded
  - Time: O(1) average, O(n) worst case (all keys in one bucket)
  
- `get(K key): V`
  - Retrieves value for given key
  - Returns null if key not found
  - Time: O(1) average, O(n) worst case
  
- `remove(K key): V`
  - Removes key-value pair
  - Returns removed value or null
  - Time: O(1) average, O(n) worst case
  
- `containsKey(K key): boolean`
  - Checks if key exists in map
  - Time: O(1) average
  
- `size(): int` - Returns number of entries
- `isEmpty(): boolean` - Checks if map is empty
- `clear(): void` - Removes all entries
- `printHashMap(): void` - Prints bucket structure (for debugging)

#### Private Helper Methods:
- `getBucketIndex(K key): int`
  - Calculates bucket index from key's hash code
  - Formula: `|hashCode(key)| % capacity`
  
- `putNullKey(V value): void` - Special handling for null keys (bucket 0)
- `getNullKey(): V` - Retrieves value for null key
- `removeNullKey(): V` - Removes null key entry
- `resize(): void`
  - Doubles capacity when load factor exceeded
  - Rehashes all existing entries
  - Time: O(n) where n = number of entries

**Relationships:**
- HAS-A Entry<K, V>[]

**Design Pattern:** 
- Hash Table with Separate Chaining
- Generics for type safety

---

### 3. HashMapDemo (Main)
**Purpose:** Demonstrates HashMap operations

**Methods:**
- `main(String[] args): void`
  - Tests put, get, remove operations
  - Demonstrates collision handling
  - Shows resize behavior
  - Tests null key handling
  - Prints bucket structure

**Demonstrates:**
- Basic CRUD operations
- Update existing key
- Collision resolution
- Dynamic resizing
- Null key support
- Clear operation

---

## How HashMap Works

### 1. Hash Function
```
Input: Key
Output: Bucket Index

Steps:
1. Get key's hash code: key.hashCode()
2. Handle negative hash codes: Math.abs(hashCode)
3. Map to bucket index: hashCode % capacity

Example:
  key = "Apple"
  hashCode = "Apple".hashCode() = 63476538
  capacity = 16
  bucketIndex = 63476538 % 16 = 10
  
  → "Apple" goes to bucket[10]
```

### 2. Collision Handling (Separate Chaining)
```
When two keys hash to same bucket:

Example:
  key1 = "A", hash % 16 = 5 → bucket[5]
  key2 = "Q", hash % 16 = 5 → bucket[5] (collision!)

Resolution:
  bucket[5] → [Entry("A", val1)] → [Entry("Q", val2)] → null
              ↑                     ↑
              first entry           chained entry

This forms a linked list in each bucket.
```

### 3. Load Factor and Resizing
```
Load Factor = size / capacity

When load factor exceeds 0.75:
  - Current: size=12, capacity=16, load=0.75
  - Action: Resize triggered
  - New capacity: 16 × 2 = 32
  - Rehash all entries to new buckets

Why 0.75?
  - Balance between space and time
  - Too low (0.5): Wastes memory
  - Too high (0.9): More collisions, slower lookups
```

---

## Operation Details

### PUT Operation
```
put(key, value):

1. Check for null key
   ├─→ If null: putNullKey(value) [special handling in bucket 0]
   └─→ Else: continue

2. Calculate bucket index
   bucketIndex = |key.hashCode()| % capacity

3. Search bucket for existing key
   entry = buckets[bucketIndex]
   while (entry != null):
       if (entry.key.equals(key)):
           entry.value = value  [UPDATE]
           return
       entry = entry.next

4. Key not found, insert new entry
   newEntry = new Entry(key, value)
   newEntry.next = buckets[bucketIndex]  [Insert at head]
   buckets[bucketIndex] = newEntry
   size++

5. Check load factor
   if (size >= capacity × 0.75):
       resize()  [Double capacity and rehash]
```

### GET Operation
```
get(key):

1. Check for null key
   ├─→ If null: return getNullKey()
   └─→ Else: continue

2. Calculate bucket index
   bucketIndex = |key.hashCode()| % capacity

3. Search bucket for key
   entry = buckets[bucketIndex]
   while (entry != null):
       if (entry.key.equals(key)):
           return entry.value  [FOUND]
       entry = entry.next

4. Key not found
   return null
```

### REMOVE Operation
```
remove(key):

1. Check for null key
   ├─→ If null: return removeNullKey()
   └─→ Else: continue

2. Calculate bucket index
   bucketIndex = |key.hashCode()| % capacity

3. Search bucket with previous tracking
   entry = buckets[bucketIndex]
   previous = null
   
   while (entry != null):
       if (entry.key.equals(key)):
           [FOUND - Remove from linked list]
           
           if (previous == null):
               buckets[bucketIndex] = entry.next  [Remove head]
           else:
               previous.next = entry.next  [Remove middle/end]
           
           size--
           return entry.value
       
       previous = entry
       entry = entry.next

4. Key not found
   return null
```

### RESIZE Operation
```
resize():

1. Create new bucket array
   newCapacity = capacity × 2
   newBuckets = new Entry[newCapacity]

2. Rehash all existing entries
   for each bucket in oldBuckets:
       entry = bucket
       while (entry != null):
           [Calculate new bucket index]
           newIndex = |entry.key.hashCode()| % newCapacity
           
           [Insert into new buckets using put()]
           put(entry.key, entry.value)
           
           entry = entry.next

3. Update references
   buckets = newBuckets
   capacity = newCapacity

Note: size is recalculated during put() calls
```

---

## Collision Resolution Example

```
Scenario: Capacity = 4, keys hash to same bucket

Step 1: put(1, "One")
┌──────┬──────┬──────┬──────┐
│ null │ [1→One] │ null │ null │
└──────┴──────┴──────┴──────┘
  [0]    [1]    [2]    [3]

Step 2: put(5, "Five")  [5 % 4 = 1, collision!]
┌──────┬─────────────────────┬──────┬──────┐
│ null │ [5→Five]→[1→One]    │ null │ null │
└──────┴─────────────────────┴──────┴──────┘
  [0]         [1]               [2]    [3]

Step 3: put(9, "Nine")  [9 % 4 = 1, collision!]
┌──────┬──────────────────────────────┬──────┬──────┐
│ null │ [9→Nine]→[5→Five]→[1→One]    │ null │ null │
└──────┴──────────────────────────────┴──────┴──────┘
  [0]              [1]                   [2]    [3]

Lookup: get(5)
  - Go to bucket[1]
  - Check [9→Nine]: key != 5, next
  - Check [5→Five]: key == 5, found!
  - Return "Five"
  
Time: O(chain_length) = O(3) in this case
```

---

## Memory Structure

```
MyHashMap Object:
│
├─→ buckets: Entry[] (array of references)
│   │
│   ├─→ buckets[0]: Entry<null, 999>
│   │               └─→ next: null
│   │
│   ├─→ buckets[1]: Entry<"A", 10>
│   │               └─→ next: Entry<"Q", 20>
│   │                           └─→ next: null
│   │
│   ├─→ buckets[2]: null
│   │
│   ├─→ buckets[3]: Entry<"B", 30>
│   │               └─→ next: null
│   │
│   └─→ ... more buckets
│
├─→ size: 4
└─→ capacity: 16

Memory per Entry: ~32 bytes (key ref + value ref + next ref + object overhead)
Memory for array: capacity × 8 bytes (reference size)
Total: size × 32 + capacity × 8 bytes
```

---

## Hash Function Design

### Why Math.abs()?
```
Problem: hashCode() can return negative values
  "ABC".hashCode() = 64578 (positive)
  "polygenelubricants".hashCode() = -1767607358 (negative!)

Issue: -1767607358 % 16 = -14 (negative index → ArrayIndexOutOfBounds)

Solution: Math.abs(hashCode)
  Math.abs(-1767607358) = 1767607358
  1767607358 % 16 = 14 (valid index)

Edge Case: Math.abs(Integer.MIN_VALUE) = Integer.MIN_VALUE
  - Still negative due to integer overflow
  - Real-world solution: (hashCode & 0x7FFFFFFF) % capacity
```

### Hash Distribution
```
Good hash function properties:
  1. Deterministic: Same key → same hash
  2. Uniform distribution: Keys spread evenly across buckets
  3. Fast computation: O(1) time

Java's String.hashCode():
  "ABC" → 64578
  "ABD" → 64579  (small change in input → small change in hash)
  
  Not cryptographically secure, but good for hash tables.
```

---

## Collision Strategies Comparison

### 1. Separate Chaining (Used in this implementation)
```
Bucket is a linked list:
  bucket[i] → Entry1 → Entry2 → Entry3 → null

Pros:
  + Simple implementation
  + Never "full" (can always add more)
  + Good for high load factors

Cons:
  - Extra memory for next pointers
  - Cache unfriendly (pointer chasing)
  - Degrades to O(n) if all keys in one bucket
```

### 2. Open Addressing (Alternative, not used)
```
Probing sequence to find next empty slot:

Linear Probing:
  index, index+1, index+2, ...

Quadratic Probing:
  index, index+1², index+2², ...

Pros:
  + Better cache locality
  + No extra memory for pointers

Cons:
  - Can become full (needs resize)
  - Clustering problems
  - Complex deletion
```

---

## Resize Operation Details

### When Resize Happens
```
Trigger: size >= capacity × LOAD_FACTOR

Examples:
  Capacity 16: Resize at size 12 (16 × 0.75)
  Capacity 32: Resize at size 24 (32 × 0.75)
  Capacity 64: Resize at size 48 (64 × 0.75)
```

### Resize Process
```
Before Resize:
  Capacity: 16
  Size: 12
  Buckets: Entry[16]

After Resize:
  Capacity: 32 (doubled)
  Size: 12 (same entries)
  Buckets: Entry[32] (all entries rehashed)

Why Rehash?
  Old: "Apple" → 63476538 % 16 = 10 → bucket[10]
  New: "Apple" → 63476538 % 32 = 10 → bucket[10] (may be different!)
  
  Hash % 16 ≠ Hash % 32
  
  All entries must be repositioned based on new capacity.
```

### Resize Algorithm
```
1. Save old bucket array
   oldBuckets = buckets

2. Create new larger array
   capacity = capacity × 2
   buckets = new Entry[capacity]
   size = 0

3. Rehash all entries
   for each oldBucket:
       for each entry in bucket:
           put(entry.key, entry.value)
           [Recalculates bucket index with new capacity]

Time Complexity: O(n) where n = total entries
Amortized Cost: O(1) per put (rare operation)
```

---

## Null Key Handling

### Why Special Handling?
```
Problem: null.hashCode() → NullPointerException

Solution: Store null keys in bucket[0]

putNullKey(value):
  - Search bucket[0] for entry with key == null
  - If found: update value
  - If not found: insert at head of bucket[0]

getNullKey():
  - Search bucket[0] for entry with key == null
  - Return value if found, null otherwise
```

---

## Performance Analysis

### Time Complexity

| Operation     | Average Case | Worst Case | Explanation                    |
|---------------|--------------|------------|--------------------------------|
| `put(k, v)`   | O(1)         | O(n)       | O(n) if all keys in one bucket |
| `get(k)`      | O(1)         | O(n)       | O(n) if all keys in one bucket |
| `remove(k)`   | O(1)         | O(n)       | O(n) if all keys in one bucket |
| `containsKey` | O(1)         | O(n)       | Same as get                    |
| `size()`      | O(1)         | O(1)       | Direct field access            |
| `clear()`     | O(capacity)  | O(capacity)| Must null all bucket slots     |
| `resize()`    | O(n)         | O(n)       | Rehash all n entries           |

### Space Complexity
- **Storage**: O(n) - n entries
- **Overhead**: O(capacity) - bucket array
- **Per Entry**: 32 bytes (approx)
- **Total**: n × 32 + capacity × 8 bytes

### Load Factor Impact
```
Load Factor 0.5:
  - More empty buckets
  - Fewer collisions
  - More memory used
  - Faster lookups

Load Factor 0.75: (Used)
  - Balanced trade-off
  - Standard Java HashMap default
  
Load Factor 0.9:
  - Less memory used
  - More collisions
  - Slower lookups
```

---

## Collision Probability

### Birthday Paradox Applied
```
With uniform hash distribution:

For capacity C and n entries:
  Collision probability ≈ 1 - e^(-n²/2C)

Example (capacity = 16):
  n = 4:  ~20% chance of collision
  n = 8:  ~80% chance of collision
  n = 12: ~99% chance of collision

This is why we resize at 0.75 load factor!
```

---

## Object Interaction Flow

### PUT Operation Flow
```
Client: map.put("Apple", 10)
    │
    ▼
MyHashMap.put("Apple", 10)
    │
    ├─→ key == null? → No
    │
    ├─→ getBucketIndex("Apple")
    │   │
    │   ├─→ hashCode = "Apple".hashCode() = 63476538
    │   ├─→ abs = Math.abs(63476538) = 63476538
    │   └─→ index = 63476538 % 16 = 10
    │       return 10
    │
    ├─→ existing = buckets[10]
    │
    ├─→ Search linked list at bucket[10]:
    │   while (existing != null):
    │       if (existing.key.equals("Apple")):
    │           existing.value = 10  [UPDATE]
    │           return
    │       existing = existing.next
    │
    ├─→ Key not found, create new entry:
    │   newEntry = new Entry("Apple", 10)
    │   newEntry.next = buckets[10]
    │   buckets[10] = newEntry  [Insert at head]
    │   size++
    │
    └─→ Check load factor:
        if (12 >= 16 × 0.75):  [12 >= 12]
            resize()
            │
            └─→ newCapacity = 16 × 2 = 32
                Rehash all entries
```

### GET Operation Flow
```
Client: value = map.get("Apple")
    │
    ▼
MyHashMap.get("Apple")
    │
    ├─→ key == null? → No
    │
    ├─→ getBucketIndex("Apple")
    │   └─→ return 10
    │
    ├─→ entry = buckets[10]
    │
    ├─→ Search linked list:
    │   while (entry != null):
    │       if (entry.key.equals("Apple")):
    │           return entry.value  [FOUND]
    │       entry = entry.next
    │
    └─→ return null  [NOT FOUND]
```

### REMOVE Operation Flow
```
Client: oldValue = map.remove("Apple")
    │
    ▼
MyHashMap.remove("Apple")
    │
    ├─→ key == null? → No
    │
    ├─→ getBucketIndex("Apple")
    │   └─→ return 10
    │
    ├─→ entry = buckets[10]
    │   previous = null
    │
    └─→ Search with previous tracking:
        while (entry != null):
            if (entry.key.equals("Apple")):
                [FOUND - Remove from list]
                
                Case A: Entry is head (previous == null)
                ───────────────────────────────────────
                buckets[10] = entry.next
                
                Before: bucket[10] → [Apple] → [Banana] → null
                After:  bucket[10] → [Banana] → null
                
                Case B: Entry in middle/end
                ───────────────────────────
                previous.next = entry.next
                
                Before: [Cherry] → [Apple] → [Banana] → null
                After:  [Cherry] → [Banana] → null
                
                size--
                return entry.value
            
            previous = entry
            entry = entry.next
```

---

## Example Execution Trace

```
=== Initialize HashMap ===
capacity = 16
buckets = new Entry[16]  [all null]
size = 0

=== put("Apple", 10) ===
hashCode("Apple") = 63476538
index = 63476538 % 16 = 10
buckets[10] = null (empty)
Insert: buckets[10] → Entry("Apple", 10)
size = 1

State:
┌─────┬─────┬─────┬──────────────┬─────┬─────┐
│ ... │ ... │ ... │ [Apple→10]   │ ... │ ... │
└─────┴─────┴─────┴──────────────┴─────┴─────┘
  [0]   [1]   [9]      [10]        [11]  [15]

=== put("Banana", 20) ===
hashCode("Banana") = -1396355227
index = 1396355227 % 16 = 3
buckets[3] = null (empty)
Insert: buckets[3] → Entry("Banana", 20)
size = 2

State:
┌─────┬─────┬─────┬──────────────┬─────┬──────────────┬─────┐
│ ... │ ... │ ... │ [Banana→20]  │ ... │ [Apple→10]   │ ... │
└─────┴─────┴─────┴──────────────┴─────┴──────────────┴─────┘
  [0]   [1]   [2]      [3]         [9]      [10]        [15]

=== put("Cherry", 30) ===
Assume hashCode("Cherry") % 16 = 3 (COLLISION with Banana!)
buckets[3] = Entry("Banana", 20)
Insert at head: buckets[3] → Entry("Cherry", 30) → Entry("Banana", 20)
size = 3

State:
┌─────┬──────────────────────────────┬──────────────┬─────┐
│ ... │ [Cherry→30]→[Banana→20]      │ [Apple→10]   │ ... │
└─────┴──────────────────────────────┴──────────────┴─────┘
  [0]            [3]                       [10]        [15]

=== get("Banana") ===
index = 3
Search bucket[3]:
  - Entry("Cherry", 30): key != "Banana", continue
  - Entry("Banana", 20): key == "Banana", return 20

=== put("Apple", 100) [UPDATE] ===
index = 10
Search bucket[10]:
  - Entry("Apple", 10): key == "Apple", update value to 100
size unchanged (still 3)

=== remove("Cherry") ===
index = 3
Search bucket[3]:
  - Entry("Cherry", 30): key == "Cherry", remove
  - previous == null → buckets[3] = entry.next
  - buckets[3] now points to Entry("Banana", 20)
size = 2

Final State:
┌─────┬──────────────┬──────────────┬─────┐
│ ... │ [Banana→20]  │ [Apple→100]  │ ... │
└─────┴──────────────┴──────────────┴─────┘
  [0]      [3]            [10]        [15]
```

---

## Design Patterns Used

### 1. **Generics Pattern**
- `MyHashMap<K, V>` - Type parameters for key and value
- Type safety at compile time
- No casting required

### 2. **Linked List Pattern**
- Each bucket is a singly linked list
- Entry has `next` pointer
- Handles collisions gracefully

### 3. **Array + Linked List Hybrid**
- Array for O(1) bucket access
- Linked list for collision handling
- Best of both worlds

### 4. **Lazy Initialization**
- Buckets created on demand
- Empty buckets remain null
- Saves memory

### 5. **Single Responsibility**
- `Entry` - Represents single key-value pair
- `MyHashMap` - Manages collection of entries

---

## Comparison with Java's HashMap

| Feature                  | MyHashMap (Custom)    | Java HashMap          |
|--------------------------|-----------------------|-----------------------|
| **Collision Handling**   | Separate Chaining     | Chaining + Red-Black Tree (Java 8+) |
| **Initial Capacity**     | 16                    | 16                    |
| **Load Factor**          | 0.75                  | 0.75                  |
| **Null Keys**            | Supported             | Supported             |
| **Null Values**          | Supported             | Supported             |
| **Resize Strategy**      | Double capacity       | Double capacity       |
| **Tree Conversion**      | No                    | Yes (when chain > 8)  |
| **Thread Safety**        | No                    | No                    |
| **Iterator Support**     | No                    | Yes                   |
| **EntrySet/KeySet**      | No                    | Yes                   |
| **Compute Methods**      | No                    | Yes (compute, merge)  |

---

## Key Features

1. **Generic Type Support**: Works with any key-value types
2. **Null Key Support**: Can store null as a key
3. **Dynamic Resizing**: Automatically grows when needed
4. **Collision Handling**: Uses separate chaining
5. **O(1) Average Performance**: Fast put/get/remove operations
6. **Type Safe**: Compile-time type checking
7. **Debug Support**: printHashMap() shows internal structure

---

## Implementation Highlights

### 1. Generic Type Safety
```java
// Compile-time safety
MyHashMap<String, Integer> map = new MyHashMap<>();
map.put("key", 123);      // OK
// map.put(123, "key");   // Compile error!

// No casting needed
Integer value = map.get("key");  // No cast required
```

### 2. Separate Chaining Visualization
```
buckets[3]: [Entry1] → [Entry2] → [Entry3] → null
              ↓          ↓          ↓
            key1=val1  key2=val2  key3=val3

All three keys hash to bucket 3, forming a chain.
```

### 3. Load Factor Math
```
size=12, capacity=16
load = 12/16 = 0.75

size=13, capacity=16  
load = 13/16 = 0.8125 > 0.75 → RESIZE!

After resize:
size=13, capacity=32
load = 13/32 = 0.40625 (healthy again)
```

---

## Common Operations Examples

### Example 1: Basic CRUD
```java
MyHashMap<String, Integer> map = new MyHashMap<>();

// Create
map.put("Apple", 10);
map.put("Banana", 20);

// Read
int apples = map.get("Apple");  // 10

// Update
map.put("Apple", 15);  // Overwrites 10

// Delete
map.remove("Banana");
```

### Example 2: Collision Scenario
```java
MyHashMap<Integer, String> map = new MyHashMap<>(4);

map.put(1, "One");    // 1 % 4 = 1 → bucket[1]
map.put(5, "Five");   // 5 % 4 = 1 → bucket[1] (collision!)
map.put(9, "Nine");   // 9 % 4 = 1 → bucket[1] (collision!)

// All in bucket[1]: [9→Nine] → [5→Five] → [1→One] → null
// Get still works: O(3) to find
```

### Example 3: Null Key
```java
map.put(null, 999);
int value = map.get(null);  // 999
map.remove(null);
```

---

## Advantages of This Implementation

1. **Educational**: Shows internal working of hash tables
2. **Simple**: No complex optimizations, easy to understand
3. **Extensible**: Can add features like iteration, entrySet
4. **Standard Algorithms**: Uses industry-standard techniques

---

## Limitations (vs Production HashMap)

1. **No Tree Conversion**: Long chains stay as lists (Java 8+ converts to trees)
2. **No Iterator**: Cannot use for-each loop
3. **No Thread Safety**: Not concurrent-safe (unlike ConcurrentHashMap)
4. **No Compute Methods**: No computeIfAbsent, merge, etc.
5. **Simple Hash**: Uses default hashCode (no additional mixing)
6. **No Serialization**: Not serializable
7. **No EntrySet/KeySet**: Cannot iterate over keys/entries

---

## Future Enhancements

1. **Tree Conversion**: Convert long chains (>8) to red-black trees
2. **Iterator Support**: Implement Iterable interface
3. **EntrySet/KeySet**: Return Set<Entry<K,V>> and Set<K>
4. **Thread Safety**: Add synchronized version or lock-free implementation
5. **Compute Methods**: Add computeIfAbsent, computeIfPresent, merge
6. **Better Hash Function**: Additional hash mixing for better distribution
7. **Capacity Validation**: Ensure capacity is power of 2 (use bitwise AND)
8. **Remove During Iteration**: Support fail-fast iterators
9. **Clone Support**: Implement Cloneable
10. **Serialization**: Implement Serializable

---

## Usage Examples

### Example 1: Student Records
```java
MyHashMap<Integer, String> students = new MyHashMap<>();
students.put(101, "Alice");
students.put(102, "Bob");
students.put(103, "Charlie");

String name = students.get(102);  // "Bob"
```

### Example 2: Word Frequency Counter
```java
MyHashMap<String, Integer> frequency = new MyHashMap<>();
String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};

for (String word : words) {
    Integer count = frequency.get(word);
    if (count == null) {
        frequency.put(word, 1);
    } else {
        frequency.put(word, count + 1);
    }
}

// Result: {apple=3, banana=2, cherry=1}
```

### Example 3: Cache Implementation
```java
MyHashMap<String, Object> cache = new MyHashMap<>();

// Store cached data
cache.put("user:123", userObject);
cache.put("config:app", configObject);

// Retrieve from cache
User user = (User) cache.get("user:123");

// Invalidate cache entry
cache.remove("user:123");
```

---

## Testing Scenarios

### Test Cases
```java
// 1. Empty map
MyHashMap<String, Integer> map = new MyHashMap<>();
assert map.isEmpty() == true;
assert map.size() == 0;

// 2. Put and get
map.put("key", 100);
assert map.get("key") == 100;
assert map.size() == 1;

// 3. Update existing key
map.put("key", 200);
assert map.get("key") == 200;
assert map.size() == 1;  // Size unchanged

// 4. Remove
int removed = map.remove("key");
assert removed == 200;
assert map.get("key") == null;
assert map.size() == 0;

// 5. Null key
map.put(null, 999);
assert map.get(null) == 999;

// 6. Collision handling
MyHashMap<Integer, String> collisionMap = new MyHashMap<>(4);
collisionMap.put(1, "A");
collisionMap.put(5, "B");  // Both hash to bucket 1
assert collisionMap.get(1).equals("A");
assert collisionMap.get(5).equals("B");

// 7. Resize
MyHashMap<Integer, Integer> resizeMap = new MyHashMap<>(4);
for (int i = 0; i < 10; i++) {
    resizeMap.put(i, i * 10);
}
// Should trigger resize at least once
assert resizeMap.size() == 10;

// 8. Contains key
assert map.containsKey("key") == false;
map.put("key", 100);
assert map.containsKey("key") == true;

// 9. Clear
map.clear();
assert map.isEmpty() == true;
```

---

## Complexity Analysis

### Best Case
```
No Collisions:
  - Each key in different bucket
  - put: O(1)
  - get: O(1)
  - remove: O(1)
```

### Average Case
```
Uniform Hash Distribution with Load Factor 0.75:
  - Average chain length: 0.75
  - put: O(1)
  - get: O(1)
  - remove: O(1)
```

### Worst Case
```
All Keys Hash to Same Bucket:
  - All entries in single linked list
  - put: O(n) - must traverse chain
  - get: O(n) - must search entire chain
  - remove: O(n) - must find in chain

Why unlikely?
  - Good hash functions distribute uniformly
  - Load factor triggers resize before chains too long
```

---

## Memory Efficiency

```
For 100 entries with capacity 128:

Array overhead:    128 × 8 bytes = 1 KB
Entry objects:     100 × 32 bytes = 3.2 KB
MyHashMap object:  ~64 bytes
─────────────────────────────────────────
Total:             ~4.3 KB

Load factor: 100/128 = 0.78 (just triggered resize from 64→128)
```

---

## Real-World Applications

### Where HashMap is Used
1. **Caching**: Store computed results
2. **Indexing**: Fast lookup by ID
3. **Counting**: Frequency maps
4. **Graph Adjacency**: Store neighbors
5. **Memoization**: Store function results
6. **Symbol Tables**: Compilers, interpreters
7. **Database Indexing**: Hash indexes
8. **Set Implementation**: HashSet uses HashMap internally

---

## Interview Questions This Covers

1. How does HashMap work internally?
   - Array of buckets with linked lists

2. What is hash collision?
   - Two keys with same hash code % capacity

3. How are collisions resolved?
   - Separate chaining (linked lists)

4. What is load factor?
   - Ratio of size to capacity (0.75 default)

5. Why resize at 0.75?
   - Balance between space and time efficiency

6. How does resize work?
   - Double capacity, rehash all entries

7. Time complexity of operations?
   - Average O(1), worst O(n)

8. How to handle null keys?
   - Special handling in bucket 0

9. What makes a good hash function?
   - Uniform distribution, deterministic, fast

10. Why use linked lists?
    - Simple, no size limit, easy insertion

---

## Summary

This HashMap implementation demonstrates:
- **Core Data Structure**: Hash table fundamentals
- **Collision Resolution**: Separate chaining technique
- **Dynamic Resizing**: Automatic capacity management
- **Generic Programming**: Type-safe with generics
- **Algorithm Design**: Hashing and linked list manipulation

Perfect for understanding how Java's built-in HashMap works under the hood!
