# Parking Lot System - Design Documentation

## All LLD Objects in this Repository

| #  | LLD Object          | Folder          |
|----|---------------------|-----------------|
| 1  | Parking Lot System  | `parking_Lot/`  |
| 2  | TicTacToe Game      | `TicTacToe/`    |
| 3  | Car Rental System   | `carRental/`    |
| 4  | Snake and Ladder    | `snakeNladder/` |

## Entities Used (21 Classes)

| #  | Entity                     | Type                | Package         |
|----|----------------------------|---------------------|-----------------|
| 1  | VehicleType                | Enum                | parking_Lot     |
| 2  | Vehicle                    | Entity              | parking_Lot     |
| 3  | ParkingSpot                | Entity              | parking_Lot     |
| 4  | Ticket                     | Value Object        | parking_Lot     |
| 5  | ParkingSpotLookupStrategy  | Interface           | parking_Lot     |
| 6  | RandomLookupStrategy       | Strategy Impl       | parking_Lot     |
| 7  | pricingStrategy            | Interface           | parking_Lot     |
| 8  | FixedPricingStrategy       | Strategy Impl       | parking_Lot     |
| 9  | CostComputation            | Service             | parking_Lot     |
| 10 | Payment                    | Interface           | parking_Lot     |
| 11 | CashPayment                | Strategy Impl       | parking_Lot     |
| 12 | UpiPayment                 | Strategy Impl       | parking_Lot     |
| 13 | ParkingSpotManager         | Abstract Manager    | parking_Lot     |
| 14 | TwoWheelerSpotManager      | Concrete Manager    | parking_Lot     |
| 15 | FourWheelerSpotManager     | Concrete Manager    | parking_Lot     |
| 16 | ParkingLevel               | Aggregator          | parking_Lot     |
| 17 | ParkingBuilding            | Orchestrator        | parking_Lot     |
| 18 | EntranceGate               | Entry Point         | parking_Lot     |
| 19 | ExitGate                   | Exit Point          | parking_Lot     |
| 20 | ParkingLot                 | Facade              | parking_Lot     |
| 21 | ParkingClient              | Main / Client       | parking_Lot     |

---

## Overview
A multi-level parking lot management system demonstrating advanced object-oriented design principles including Strategy Pattern, Factory Pattern, dependency injection, and thread-safe operations. The system supports multiple vehicle types, flexible pricing strategies, and multiple payment methods.

---

## UML Class Diagram

```
┌─────────────────────────┐
│   <<enumeration>>       │
│     VehicleType         │
├─────────────────────────┤
│ + TWO_WHEELER           │
│ + FOUR_WHEELER          │
└─────────────────────────┘

┌─────────────────────────────────┐
│         Vehicle                 │
├─────────────────────────────────┤
│ - vehicleNumber: String         │
│ - vehicleType: VehicleType      │──────► HAS-A VehicleType
├─────────────────────────────────┤
│ + Vehicle(number, type)         │
│ + getVehicleNumber(): String    │
│ + getVehicleType(): VehicleType │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│       ParkingSpot               │
├─────────────────────────────────┤
│ - spotId: String                │
│ - isFree: boolean               │
├─────────────────────────────────┤
│ + ParkingSpot(spotId)           │
│ + getSpotId(): String           │
│ + isSpotFree(): boolean         │
│ + occupySpot(): void            │
│ + releaseSpot(): void           │
└─────────────────────────────────┘

┌─────────────────────────────────────────┐
│            Ticket                       │
├─────────────────────────────────────────┤
│ - vehicle: Vehicle                      │──────► HAS-A Vehicle
│ - level: ParkingLevel                   │──────► HAS-A ParkingLevel
│ - spot: ParkingSpot                     │──────► HAS-A ParkingSpot
│ - entryTime: LocalDateTime              │
├─────────────────────────────────────────┤
│ + Ticket(vehicle, level, spot)          │
│ + getVehicle(): Vehicle                 │
│ + getLevel(): ParkingLevel              │
│ + getSpot(): ParkingSpot                │
│ + getEntryTime(): LocalDateTime         │
└─────────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │  <<interface>>                       │
        │  ParkingSpotLookupStrategy           │
        ├──────────────────────────────────────┤
        │ + selectSpot(spots): ParkingSpot     │
        └──────────────────────────────────────┘
                        △
                        │ IMPLEMENTS
                        │
        ┌───────────────────────────────────────┐
        │    RandomLookupStrategy               │
        ├───────────────────────────────────────┤
        │ + selectSpot(spots): ParkingSpot      │
        └───────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │  <<interface>>                       │
        │  pricingStrategy                     │
        ├──────────────────────────────────────┤
        │ + computeCost(ticket): double        │
        └──────────────────────────────────────┘
                        △
                        │ IMPLEMENTS
                        │
        ┌───────────────────────────────────────┐
        │    FixedPricingStrategy               │
        ├───────────────────────────────────────┤
        │ + computeCost(ticket): double         │
        └───────────────────────────────────────┘

┌─────────────────────────────────────────┐
│       CostComputation                   │
├─────────────────────────────────────────┤
│ - pricingStrategy: pricingStrategy      │──────► HAS-A pricingStrategy
├─────────────────────────────────────────┤
│ + CostComputation(strategy)             │
│ + computeCost(ticket): double           │
└─────────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │  <<interface>>                       │
        │  Payment                             │
        ├──────────────────────────────────────┤
        │ + pay(amount): boolean               │
        └──────────────────────────────────────┘
                        △
                        │ IMPLEMENTS
            ┌───────────┴───────────┐
            │                       │
┌───────────────────┐     ┌───────────────────┐
│   CashPayment     │     │   UpiPayment      │
├───────────────────┤     ├───────────────────┤
│ + pay(amt): bool  │     │ + pay(amt): bool  │
└───────────────────┘     └───────────────────┘

┌─────────────────────────────────────────────────┐
│    <<abstract>>                                 │
│    ParkingSpotManager                           │
├─────────────────────────────────────────────────┤
│ # spots: List<ParkingSpot>                      │──────► HAS-A List<ParkingSpot>
│ # strategy: ParkingSpotLookupStrategy           │──────► HAS-A ParkingSpotLookupStrategy
│ - lock: ReentrantLock                           │
├─────────────────────────────────────────────────┤
│ # ParkingSpotManager(spots, strategy)           │
│ + park(): ParkingSpot                           │
│ + unPark(spot): void                            │
│ + hasFreeSpot(): boolean                        │
└─────────────────────────────────────────────────┘
                        △
                        │ IS-A
            ┌───────────┴───────────┐
            │                       │
┌────────────────────────┐  ┌────────────────────────┐
│ TwoWheelerSpotManager  │  │ FourWheelerSpotManager │
├────────────────────────┤  ├────────────────────────┤
│ + TwoWheelerSpot...()  │  │ + FourWheelerSpot...() │
└────────────────────────┘  └────────────────────────┘
         IS-A                        IS-A

┌──────────────────────────────────────────────────┐
│           ParkingLevel                           │
├──────────────────────────────────────────────────┤
│ - levelNumber: int                               │
│ - managers: Map<VehicleType,ParkingSpotManager>  │──────► HAS-A Map of ParkingSpotManager
├──────────────────────────────────────────────────┤
│ + ParkingLevel(levelNum, managers)               │
│ + hasAvailability(type): boolean                 │
│ + park(type): ParkingSpot                        │
│ + unPark(type, spot): void                       │
│ + getLevelNumber(): int                          │
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│         ParkingBuilding                          │
├──────────────────────────────────────────────────┤
│ - levels: List<ParkingLevel>                     │──────► HAS-A List<ParkingLevel>
├──────────────────────────────────────────────────┤
│ + ParkingBuilding(levels, costComputation)       │
│ + allocate(vehicle): Ticket                      │
│ + release(ticket): void                          │
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│         EntranceGate                             │
├──────────────────────────────────────────────────┤
│ + enter(building, vehicle): Ticket               │──────► USES ParkingBuilding
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│           ExitGate                               │
├──────────────────────────────────────────────────┤
│ - costComputation: CostComputation               │──────► HAS-A CostComputation
├──────────────────────────────────────────────────┤
│ + ExitGate(costComputation)                      │
│ + completeExit(building, ticket, payment): void  │──────► USES ParkingBuilding, Payment
│ - calculatePrice(ticket): double                 │
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│            ParkingLot                            │
├──────────────────────────────────────────────────┤
│ - building: ParkingBuilding                      │──────► HAS-A ParkingBuilding
│ - entranceGate: EntranceGate                     │──────► HAS-A EntranceGate
│ - exitGate: ExitGate                             │──────► HAS-A ExitGate
├──────────────────────────────────────────────────┤
│ + ParkingLot(building, entrance, exit)           │
│ + vehicleArrives(vehicle): Ticket                │
│ + vehicleExits(ticket, payment): void            │
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│         ParkingClient (Main)                     │
├──────────────────────────────────────────────────┤
│ + main(args): void                               │──────► USES All Components
└──────────────────────────────────────────────────┘
```

---

## Relationships Summary

### IS-A (Inheritance) Relationships

1. **TwoWheelerSpotManager IS-A ParkingSpotManager**
   - Specialized manager for two-wheeler parking spots
   
2. **FourWheelerSpotManager IS-A ParkingSpotManager**
   - Specialized manager for four-wheeler parking spots

3. **RandomLookupStrategy IS-A ParkingSpotLookupStrategy**
   - Implements strategy interface for spot selection

4. **FixedPricingStrategy IS-A pricingStrategy**
   - Implements pricing interface with fixed cost

5. **CashPayment IS-A Payment**
   - Implements payment interface for cash transactions

6. **UpiPayment IS-A Payment**
   - Implements payment interface for UPI transactions

### HAS-A (Composition/Aggregation) Relationships

1. **Vehicle HAS-A VehicleType** (Composition)
   - Each vehicle has a type (TWO_WHEELER or FOUR_WHEELER)

2. **Ticket HAS-A Vehicle, ParkingLevel, ParkingSpot** (Composition)
   - Ticket contains all parking information

3. **ParkingSpotManager HAS-A List<ParkingSpot>** (Composition)
   - Manager owns and manages multiple parking spots

4. **ParkingSpotManager HAS-A ParkingSpotLookupStrategy** (Composition)
   - Manager uses a strategy to select spots

5. **ParkingLevel HAS-A Map<VehicleType, ParkingSpotManager>** (Composition)
   - Each level has different managers for different vehicle types

6. **ParkingBuilding HAS-A List<ParkingLevel>** (Composition)
   - Building contains multiple parking levels

7. **ParkingLot HAS-A ParkingBuilding, EntranceGate, ExitGate** (Composition)
   - Lot aggregates all major components

8. **ExitGate HAS-A CostComputation** (Composition)
   - Exit gate uses cost computation for billing

9. **CostComputation HAS-A pricingStrategy** (Composition)
   - Cost computation delegates to pricing strategy

---

## Class Details

### 1. VehicleType (Enum)
**Purpose:** Defines supported vehicle categories

**Values:**
- `TWO_WHEELER` - Bikes, scooters, motorcycles
- `FOUR_WHEELER` - Cars, SUVs

---

### 2. Vehicle (Entity)
**Purpose:** Represents a vehicle entering the parking lot

**Attributes:**
- `vehicleNumber: String` - Unique vehicle registration number
- `vehicleType: VehicleType` - Category of vehicle

**Methods:**
- `Vehicle(String vehicleNumber, VehicleType vehicleType)` - Constructor
- `getVehicleNumber(): String` - Returns vehicle number
- `getVehicleType(): VehicleType` - Returns vehicle type

**Relationships:**
- HAS-A VehicleType

---

### 3. ParkingSpot (Entity)
**Purpose:** Represents a single parking spot

**Attributes:**
- `spotId: String` - Unique identifier for the spot
- `isFree: boolean` - Availability status (default: true)

**Methods:**
- `ParkingSpot(String spotId)` - Constructor
- `getSpotId(): String` - Returns spot identifier
- `isSpotFree(): boolean` - Checks if spot is available
- `occupySpot(): void` - Marks spot as occupied
- `releaseSpot(): void` - Marks spot as free

**Thread Safety:** State changes should be synchronized by manager

---

### 4. Ticket (Value Object)
**Purpose:** Immutable record of parking transaction

**Attributes:**
- `vehicle: Vehicle` - The parked vehicle (final)
- `level: ParkingLevel` - Parking level (final)
- `spot: ParkingSpot` - Assigned spot (final)
- `entryTime: LocalDateTime` - Time of entry (final)

**Methods:**
- `Ticket(Vehicle vehicle, ParkingLevel level, ParkingSpot spot)` - Constructor
- `getVehicle(): Vehicle` - Returns vehicle
- `getLevel(): ParkingLevel` - Returns parking level
- `getSpot(): ParkingSpot` - Returns parking spot
- `getEntryTime(): LocalDateTime` - Returns entry time

**Relationships:**
- HAS-A Vehicle
- HAS-A ParkingLevel
- HAS-A ParkingSpot

**Design:** Immutable to prevent tampering

---

### 5. ParkingSpotLookupStrategy (Interface)
**Purpose:** Strategy pattern for spot selection algorithms

**Methods:**
- `selectSpot(List<ParkingSpot> spots): ParkingSpot` - Selects a spot from available list

**Implementations:**
- `RandomLookupStrategy` - Simple first-available selection

**Design Pattern:** Strategy Pattern for extensibility

---

### 6. RandomLookupStrategy (Concrete Strategy)
**Purpose:** Default spot selection strategy

**Methods:**
- `selectSpot(List<ParkingSpot> spots): ParkingSpot`
  - Iterates through spots
  - Returns first free spot
  - Returns null if no spots available

**Relationships:**
- IMPLEMENTS ParkingSpotLookupStrategy

---

### 7. pricingStrategy (Interface)
**Purpose:** Strategy pattern for pricing calculations

**Methods:**
- `computeCost(Ticket ticket): double` - Calculates parking cost

**Implementations:**
- `FixedPricingStrategy` - Fixed cost pricing

**Design Pattern:** Strategy Pattern for flexible pricing

---

### 8. FixedPricingStrategy (Concrete Strategy)
**Purpose:** Simple fixed-cost pricing

**Methods:**
- `computeCost(Ticket ticket): double`
  - Returns fixed cost of 100.0
  - Ignores duration and vehicle type

**Relationships:**
- IMPLEMENTS pricingStrategy

**Future Extensions:** HourlyPricingStrategy, DynamicPricingStrategy

---

### 9. CostComputation (Service)
**Purpose:** Delegates cost calculation to pricing strategy

**Attributes:**
- `pricingStrategy: pricingStrategy` - The pricing strategy to use

**Methods:**
- `CostComputation(pricingStrategy pricingStrategy)` - Constructor with strategy injection
- `computeCost(Ticket ticket): double` - Delegates to strategy

**Relationships:**
- HAS-A pricingStrategy

**Design Pattern:** Strategy Pattern + Dependency Injection

---

### 10. Payment (Interface)
**Purpose:** Strategy pattern for payment methods

**Methods:**
- `pay(double amount): boolean` - Processes payment, returns success status

**Implementations:**
- `CashPayment` - Cash payment processing
- `UpiPayment` - UPI payment processing

**Design Pattern:** Strategy Pattern

---

### 11. CashPayment (Concrete Payment)
**Purpose:** Handles cash payments

**Methods:**
- `pay(double amount): boolean`
  - Prints payment confirmation
  - Returns true (simulated success)

**Relationships:**
- IMPLEMENTS Payment

---

### 12. UpiPayment (Concrete Payment)
**Purpose:** Handles UPI digital payments

**Methods:**
- `pay(double amount): boolean`
  - Prints UPI payment confirmation
  - Returns true (simulated success)

**Relationships:**
- IMPLEMENTS Payment

---

### 13. ParkingSpotManager (Abstract Base)
**Purpose:** Thread-safe manager for parking spots

**Attributes:**
- `spots: List<ParkingSpot>` - List of managed spots (protected)
- `strategy: ParkingSpotLookupStrategy` - Spot selection strategy (protected)
- `lock: ReentrantLock` - Thread-safety lock (private)

**Methods:**
- `ParkingSpotManager(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy)` - Constructor
- `park(): ParkingSpot`
  - Thread-safe method to find and occupy a spot
  - Uses strategy to select spot
  - Marks spot as occupied
  - Returns null if no spots available
  
- `unPark(ParkingSpot spot): void`
  - Thread-safe method to release a spot
  - Marks spot as free
  
- `hasFreeSpot(): boolean`
  - Thread-safe check for availability
  - Streams through spots to check

**Relationships:**
- HAS-A List<ParkingSpot>
- HAS-A ParkingSpotLookupStrategy

**Thread Safety:** Uses ReentrantLock for fair locking

---

### 14. TwoWheelerSpotManager (Concrete Manager)
**Purpose:** Manages two-wheeler parking spots

**Inheritance:** Extends ParkingSpotManager

**Methods:**
- `TwoWheelerSpotManager(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy)` - Constructor

**Relationships:**
- IS-A ParkingSpotManager

**Design:** Separate lock per manager type prevents cross-type blocking

---

### 15. FourWheelerSpotManager (Concrete Manager)
**Purpose:** Manages four-wheeler parking spots

**Inheritance:** Extends ParkingSpotManager

**Methods:**
- `FourWheelerSpotManager(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy)` - Constructor

**Relationships:**
- IS-A ParkingSpotManager

**Design:** Separate lock per manager type prevents cross-type blocking

---

### 16. ParkingLevel (Aggregator)
**Purpose:** Represents one level of parking building

**Attributes:**
- `levelNumber: int` - Level identifier (1, 2, 3...)
- `managers: Map<VehicleType, ParkingSpotManager>` - Managers for each vehicle type

**Methods:**
- `ParkingLevel(int levelNumber, Map<VehicleType, ParkingSpotManager> managers)` - Constructor
- `hasAvailability(VehicleType type): boolean`
  - Checks if spots available for vehicle type
  - Delegates to appropriate manager
  
- `park(VehicleType type): ParkingSpot`
  - Parks vehicle of given type
  - Delegates to appropriate manager
  - Throws exception if no manager for vehicle type
  
- `unPark(VehicleType type, ParkingSpot spot): void`
  - Releases a parking spot
  - Delegates to appropriate manager
  
- `getLevelNumber(): int` - Returns level number

**Relationships:**
- HAS-A Map<VehicleType, ParkingSpotManager>

**Design:** Delegates operations to appropriate vehicle-type manager

---

### 17. ParkingBuilding (Orchestrator)
**Purpose:** Manages multiple parking levels

**Attributes:**
- `levels: List<ParkingLevel>` - All parking levels in building

**Methods:**
- `ParkingBuilding(List<ParkingLevel> levels, CostComputation costComputation)` - Constructor
- `allocate(Vehicle vehicle): Ticket`
  - Iterates through levels
  - Finds first available level for vehicle type
  - Allocates spot
  - Creates and returns ticket
  - Throws exception if parking full
  
- `release(Ticket ticket): void`
  - Releases parking spot
  - Delegates to ticket's level

**Relationships:**
- HAS-A List<ParkingLevel>

**Algorithm:** First-available level allocation

---

### 18. EntranceGate (Entry Point)
**Purpose:** Handles vehicle entry

**Methods:**
- `enter(ParkingBuilding building, Vehicle vehicle): Ticket`
  - Delegates allocation to building
  - Returns generated ticket

**Relationships:**
- USES ParkingBuilding

**Design:** Thin facade over building allocation

---

### 19. ExitGate (Exit Point)
**Purpose:** Handles vehicle exit with payment

**Attributes:**
- `costComputation: CostComputation` - Cost calculation service

**Methods:**
- `ExitGate(CostComputation costComputation)` - Constructor
- `completeExit(ParkingBuilding building, Ticket ticket, Payment payment): void`
  - Calculates parking cost
  - Processes payment
  - Releases parking spot via building
  - Throws exception if payment fails
  
- `calculatePrice(Ticket ticket): double` - Private helper to compute cost

**Relationships:**
- HAS-A CostComputation
- USES ParkingBuilding
- USES Payment

**Business Logic:** Payment must succeed before spot release

---

### 20. ParkingLot (Facade)
**Purpose:** Main facade for parking lot operations

**Attributes:**
- `building: ParkingBuilding` - The parking building
- `entranceGate: EntranceGate` - Entry point
- `exitGate: ExitGate` - Exit point

**Methods:**
- `ParkingLot(ParkingBuilding building, EntranceGate entranceGate, ExitGate exitGate)` - Constructor
- `vehicleArrives(Vehicle vehicle): Ticket`
  - Entry point for arriving vehicles
  - Delegates to entrance gate
  
- `vehicleExits(Ticket ticket, Payment payment): void`
  - Exit point for departing vehicles
  - Delegates to exit gate

**Relationships:**
- HAS-A ParkingBuilding
- HAS-A EntranceGate
- HAS-A ExitGate

**Design Pattern:** Facade Pattern - Simplified interface to complex subsystem

---

### 21. ParkingClient (Main)
**Purpose:** Application entry point and system setup

**Methods:**
- `main(String[] args): void`
  - Creates lookup strategy
  - Sets up two-wheeler and four-wheeler managers per level
  - Creates two parking levels with managers
  - Creates parking building with levels
  - Creates entrance and exit gates
  - Creates parking lot facade
  - Simulates vehicle entry and exit

**Demonstrates:**
- Full system initialization
- Dependency injection
- Strategy pattern usage
- Multi-level parking
- Different vehicle types
- Different payment methods

---

## Design Patterns Used

### 1. **Strategy Pattern** (Multiple instances)
   - **ParkingSpotLookupStrategy**: Different spot selection algorithms
   - **pricingStrategy**: Different pricing models
   - **Payment**: Different payment methods

### 2. **Template Method Pattern**
   - **ParkingSpotManager**: Abstract base with common logic, extensible for specific types

### 3. **Facade Pattern**
   - **ParkingLot**: Simplified interface to complex parking subsystem

### 4. **Dependency Injection**
   - Strategies injected into managers
   - Promotes loose coupling and testability

### 5. **Factory Pattern** (Implicit)
   - Client code creates appropriate managers for vehicle types

### 6. **Single Responsibility Principle**
   - Each class has one clear responsibility
   - Managers handle spots, gates handle entry/exit, building coordinates levels

### 7. **Open/Closed Principle**
   - New vehicle types: Add new manager subclass
   - New pricing: Add new pricing strategy
   - New payment method: Add new payment implementation

---

## Object Interaction Flow

### Vehicle Entry Flow
```
Vehicle Arrives
    │
    └──> ParkingLot.vehicleArrives(vehicle)
         │
         └──> EntranceGate.enter(building, vehicle)
              │
              └──> ParkingBuilding.allocate(vehicle)
                   │
                   ├──> Iterate through ParkingLevels
                   ├──> ParkingLevel.hasAvailability(vehicleType)
                   │    │
                   │    └──> ParkingSpotManager.hasFreeSpot()
                   │
                   ├──> ParkingLevel.park(vehicleType)
                   │    │
                   │    └──> ParkingSpotManager.park()
                   │         │
                   │         ├──> Strategy.selectSpot(spots)
                   │         └──> ParkingSpot.occupySpot()
                   │
                   └──> Create Ticket(vehicle, level, spot)
                        └──> Return Ticket
```

### Vehicle Exit Flow
```
Vehicle Exits with Ticket and Payment Method
    │
    └──> ParkingLot.vehicleExits(ticket, payment)
         │
         └──> ExitGate.completeExit(building, ticket, payment)
              │
              ├──> CostComputation.computeCost(ticket)
              │    │
              │    └──> pricingStrategy.computeCost(ticket)
              │
              ├──> Payment.pay(amount)
              │    └──> [CashPayment or UpiPayment].pay()
              │
              └──> ParkingBuilding.release(ticket)
                   │
                   └──> ParkingLevel.unPark(vehicleType, spot)
                        │
                        └──> ParkingSpotManager.unPark(spot)
                             │
                             └──> ParkingSpot.releaseSpot()
```

---

## Thread Safety

### Concurrent Operations Supported
1. **Multiple vehicles parking simultaneously**
   - Different vehicle types use different managers with separate locks
   - Same vehicle type operations are serialized via ReentrantLock

2. **Fair locking**
   - ReentrantLock(true) ensures FIFO order
   - Prevents starvation

3. **Thread-safe spot selection**
   - Lock acquisition before spot lookup
   - Atomic selection and occupation

---

## Extensibility Points

### Easy to Add:
1. **New Vehicle Types**
   - Add enum value in VehicleType
   - Create new XxxSpotManager extends ParkingSpotManager
   - Configure in parking levels

2. **New Pricing Strategies**
   - Implement pricingStrategy interface
   - Examples: HourlyPricing, PeakHourPricing, VehicleTypePricing

3. **New Payment Methods**
   - Implement Payment interface
   - Examples: CardPayment, WalletPayment, QrCodePayment

4. **New Lookup Strategies**
   - Implement ParkingSpotLookupStrategy
   - Examples: NearestToEntrance, NearestToElevator, PreferredSpot

5. **Dynamic Pricing**
   - Create strategy that considers:
     - Duration (from ticket.entryTime)
     - Vehicle type
     - Peak hours
     - Occupancy rate

---

## Example Usage

```java
// Setup (from ParkingClient)
ParkingLot parkingLot = new ParkingLot(building, entranceGate, exitGate);

// Vehicle arrives
Vehicle bike = new Vehicle("BIKE-101", VehicleType.TWO_WHEELER);
Ticket ticket = parkingLot.vehicleArrives(bike);

// Vehicle exits with payment
parkingLot.vehicleExits(ticket, new CashPayment());
```

---

## Key Features

1. **Multi-level Support**: Unlimited parking levels
2. **Multiple Vehicle Types**: Segregated parking for different types
3. **Thread-Safe**: Concurrent operations supported
4. **Flexible Pricing**: Strategy-based pricing
5. **Multiple Payment Methods**: Cash, UPI, and extensible
6. **Automatic Allocation**: First-available level assignment
7. **Type-Safe**: Strong typing prevents errors
8. **Testable**: Dependency injection enables easy testing

---

## Future Enhancements

1. **Reservation System**: Pre-book parking spots
2. **Dynamic Pricing**: Time-based, demand-based pricing
3. **Parking Analytics**: Occupancy reports, revenue tracking
4. **User Accounts**: Registered users, membership discounts
5. **Spot Preferences**: Handicapped spots, EV charging spots
6. **Multi-building Support**: Campus-wide parking management
7. **Real-time Availability Display**: Digital signage integration
8. **Mobile App Integration**: QR code scanning, digital payments
9. **Automated Gates**: Integration with barrier systems
10. **ANPR Integration**: Automatic number plate recognition
