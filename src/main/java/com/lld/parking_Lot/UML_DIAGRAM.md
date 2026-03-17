# Parking Lot System - Complete UML Relationship Diagram

## Full System Architecture with All Dependencies

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    PARKING LOT MANAGEMENT SYSTEM                            ┃
┃                         (21 Classes, Multi-Layer)                           ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 1: ENUMS (No Dependencies)                                           │
└─────────────────────────────────────────────────────────────────────────────┘
                           ┌──────────────────┐
                           │ <<enumeration>>  │
                           │   VehicleType    │
                           ├──────────────────┤
                           │ + TWO_WHEELER    │
                           │ + FOUR_WHEELER   │
                           └──────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 2: BASIC ENTITIES (Depend on Enums)                                  │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌──────────────────────────────┐            ┌──────────────────────┐
    │        Vehicle               │            │    ParkingSpot       │
    ├──────────────────────────────┤            ├──────────────────────┤
    │ Dependencies:                │            │ Dependencies: NONE   │
    │ - VehicleType (HAS-A)        │            ├──────────────────────┤
    ├──────────────────────────────┤            │ - spotId: String     │
    │ - vehicleNumber: String      │            │ - isFree: boolean    │
    │ - vehicleType: VehicleType   │◄───┐       ├──────────────────────┤
    ├──────────────────────────────┤    │       │ + occupySpot()       │
    │ + getVehicleNumber()         │    │       │ + releaseSpot()      │
    │ + getVehicleType()           │    │       │ + isSpotFree()       │
    └──────────────────────────────┘    │       └──────────────────────┘
                │                       │                 │
                │                       │                 │
                │ used by               │ HAS-A           │ managed by
                │                       │                 │
                ▼                       │                 ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 3: STRATEGY INTERFACES (Define Contracts)                            │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────┐  ┌────────────────────────┐  ┌──────────────────┐
│ <<interface>>                │  │ <<interface>>          │  │ <<interface>>    │
│ParkingSpotLookupStrategy     │  │  pricingStrategy       │  │    Payment       │
├──────────────────────────────┤  ├────────────────────────┤  ├──────────────────┤
│ + selectSpot(spots): Spot    │  │ + computeCost(): double│  │ + pay(): boolean │
└──────────────────────────────┘  └────────────────────────┘  └──────────────────┘
          △                                  △                         △
          │                                  │                         │
          │ IMPLEMENTS                       │ IMPLEMENTS              │ IMPLEMENTS
          │                                  │                         │
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 4: STRATEGY IMPLEMENTATIONS                                          │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────┐  ┌────────────────────────┐  ┌─────────┐ ┌──────────┐
│  RandomLookupStrategy    │  │ FixedPricingStrategy   │  │  Cash   │ │   UPI    │
├──────────────────────────┤  ├────────────────────────┤  │ Payment │ │ Payment  │
│ + selectSpot(): Spot     │  │ + computeCost(): 100.0 │  ├─────────┤ ├──────────┤
└──────────────────────────┘  └────────────────────────┘  │+ pay()  │ │+ pay()   │
          │                                  │              └─────────┘ └──────────┘
          │ injected into                    │ injected into     │          │
          │                                  │                   │          │
          ▼                                  ▼                   │          │
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 5: MANAGERS & COMPUTATION (Use Strategies)                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐  ┌──────────────────────┐
│        <<abstract>>                                 │  │  CostComputation     │
│       ParkingSpotManager                            │  ├──────────────────────┤
├─────────────────────────────────────────────────────┤  │ Dependencies:        │
│ Dependencies:                                       │  │ - pricingStrategy    │
│ - List<ParkingSpot> (HAS-A)                         │  ├──────────────────────┤
│ - ParkingSpotLookupStrategy (HAS-A)                 │  │ - strategy: pricing  │
│ - ReentrantLock (HAS-A)                             │  ├──────────────────────┤
├─────────────────────────────────────────────────────┤  │ + computeCost(ticket)│
│ # spots: List<ParkingSpot>                          │  └──────────────────────┘
│ # strategy: ParkingSpotLookupStrategy               │            │
│ - lock: ReentrantLock                               │            │ used by
├─────────────────────────────────────────────────────┤            │
│ + park(): ParkingSpot                               │            │
│ + unPark(spot): void                                │            │
│ + hasFreeSpot(): boolean                            │            │
└─────────────────────────────────────────────────────┘            │
                    △                                               │
                    │ IS-A                                          │
        ┌───────────┴───────────┐                                   │
        │                       │                                   │
┌───────────────────┐  ┌────────────────────┐                      │
│ TwoWheelerSpot    │  │ FourWheelerSpot    │                      │
│ Manager           │  │ Manager            │                      │
├───────────────────┤  ├────────────────────┤                      │
│ Inherits all from │  │ Inherits all from  │                      │
│ ParkingSpotManager│  │ ParkingSpotManager │                      │
└───────────────────┘  └────────────────────┘                      │
        │                       │                                   │
        │ used by               │ used by                           │
        │                       │                                   │
        └───────────┬───────────┘                                   │
                    ▼                                               │
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 6: LEVEL AGGREGATION (Aggregates Managers)                           │
└─────────────────────────────────────────────────────────────────────────────┘

                    ┌─────────────────────────────────────┐
                    │       ParkingLevel                  │
                    ├─────────────────────────────────────┤
                    │ Dependencies:                       │
                    │ - Map<VehicleType,                  │
                    │       ParkingSpotManager> (HAS-A)   │
                    │ - VehicleType (uses as key)         │
                    ├─────────────────────────────────────┤
                    │ - levelNumber: int                  │
                    │ - managers: Map<Type, Manager>      │
                    ├─────────────────────────────────────┤
                    │ + hasAvailability(type): boolean    │
                    │ + park(type): ParkingSpot           │
                    │ + unPark(type, spot): void          │
                    └─────────────────────────────────────┘
                                    │
                                    │ used by (collection)
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 7: BUILDING & TICKET (Core Business Logic)                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────┐          ┌───────────────────────────────┐
│     ParkingBuilding             │          │         Ticket                │
├─────────────────────────────────┤          ├───────────────────────────────┤
│ Dependencies:                   │          │ Dependencies:                 │
│ - List<ParkingLevel> (HAS-A)    │          │ - Vehicle (HAS-A)             │
│ - Vehicle (uses)                │          │ - ParkingLevel (HAS-A)        │
│ - ParkingSpot (returns)         │          │ - ParkingSpot (HAS-A)         │
│ - Ticket (creates)              │          │ - LocalDateTime (HAS-A)       │
├─────────────────────────────────┤          ├───────────────────────────────┤
│ - levels: List<ParkingLevel>    │          │ - vehicle: Vehicle            │◄──┐
├─────────────────────────────────┤          │ - level: ParkingLevel         │   │
│ + allocate(vehicle): Ticket     │──────────►│ - spot: ParkingSpot           │   │
│ + release(ticket): void         │          │ - entryTime: LocalDateTime    │   │
└─────────────────────────────────┘          ├───────────────────────────────┤   │
            │                                 │ + getVehicle()                │   │
            │ used by                         │ + getLevel()                  │   │
            │                                 │ + getSpot()                   │   │
            ▼                                 │ + getEntryTime()              │   │
┌─────────────────────────────────────────────────────────────────────────────┐ │
│  LAYER 8: GATES (Entry/Exit Points)                                         │ │
└─────────────────────────────────────────────────────────────────────────────┘ │
                                                                                  │
┌──────────────────────────┐              ┌──────────────────────────────────┐   │
│    EntranceGate          │              │         ExitGate                 │   │
├──────────────────────────┤              ├──────────────────────────────────┤   │
│ Dependencies:            │              │ Dependencies:                    │   │
│ - ParkingBuilding (uses) │              │ - CostComputation (HAS-A)        │   │
│ - Vehicle (parameter)    │              │ - ParkingBuilding (uses)         │   │
│ - Ticket (returns)       │              │ - Ticket (parameter)             │───┘
├──────────────────────────┤              │ - Payment (uses)                 │
│ + enter(building,        │              ├──────────────────────────────────┤
│         vehicle): Ticket │              │ - costComputation: CostComp      │
└──────────────────────────┘              ├──────────────────────────────────┤
            │                              │ + completeExit(building,         │
            │ used by                      │       ticket, payment): void     │
            │                              │ - calculatePrice(ticket): double │
            │                              └──────────────────────────────────┘
            │                                          │
            │                                          │ used by
            ▼                                          ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 9: FACADE (Unified Interface)                                        │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────────┐
                        │       ParkingLot               │
                        ├────────────────────────────────┤
                        │ Dependencies:                  │
                        │ - ParkingBuilding (HAS-A)      │
                        │ - EntranceGate (HAS-A)         │
                        │ - ExitGate (HAS-A)             │
                        │ - Vehicle (parameter)          │
                        │ - Ticket (passes through)      │
                        │ - Payment (parameter)          │
                        ├────────────────────────────────┤
                        │ - building: ParkingBuilding    │
                        │ - entranceGate: EntranceGate   │
                        │ - exitGate: ExitGate           │
                        ├────────────────────────────────┤
                        │ + vehicleArrives(v): Ticket    │
                        │ + vehicleExits(t, p): void     │
                        └────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 10: CLIENT (Application Entry)                                       │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────────┐
                        │    ParkingClient (Main)        │
                        ├────────────────────────────────┤
                        │ Dependencies: ALL LAYERS       │
                        │ - Creates all objects          │
                        │ - Wires dependencies           │
                        │ - Simulates usage              │
                        ├────────────────────────────────┤
                        │ + main(args): void             │
                        └────────────────────────────────┘
```

---

## Complete Dependency Graph (Bottom-Up)

```
LAYER 1: NO DEPENDENCIES
═════════════════════════
┌──────────────┐
│ VehicleType  │ (enum)
└──────────────┘


LAYER 2: DEPENDS ON LAYER 1
════════════════════════════
┌─────────────┐         ┌──────────────┐
│  Vehicle    │         │ ParkingSpot  │
│    ↓        │         │ (independent)│
│ VehicleType │         └──────────────┘
└─────────────┘


LAYER 3: STRATEGY INTERFACES (NO CONCRETE DEPENDENCIES)
════════════════════════════════════════════════════════
┌──────────────────────────────┐  ┌─────────────────┐  ┌─────────┐
│ParkingSpotLookupStrategy     │  │pricingStrategy  │  │ Payment │
│ (defines contract)           │  │ (defines cost)  │  │ (pay)   │
└──────────────────────────────┘  └─────────────────┘  └─────────┘


LAYER 4: STRATEGY IMPLEMENTATIONS (DEPEND ON LAYER 3)
══════════════════════════════════════════════════════
┌─────────────────────────┐  ┌────────────────────┐  ┌──────┐  ┌──────┐
│ RandomLookupStrategy    │  │FixedPricingStrategy│  │ Cash │  │ UPI  │
│         ↓               │  │        ↓           │  │  ↓   │  │  ↓   │
│ParkingSpotLookupStrategy│  │ pricingStrategy    │  │Payment│  │Payment│
└─────────────────────────┘  └────────────────────┘  └──────┘  └──────┘


LAYER 5: MANAGERS & COMPUTATION (DEPEND ON LAYERS 2-4)
═══════════════════════════════════════════════════════
┌──────────────────────────────┐  ┌─────────────────────┐
│  ParkingSpotManager          │  │  CostComputation    │
│          ↓                   │  │        ↓            │
│  - List<ParkingSpot>         │  │ pricingStrategy     │
│  - ParkingSpotLookupStrategy │  │        ↓            │
│  - ReentrantLock             │  │      Ticket         │
└──────────────────────────────┘  └─────────────────────┘
            ↓
  ┌─────────┴─────────┐
  │                   │
┌─────────────────┐ ┌──────────────────┐
│TwoWheelerSpot   │ │FourWheelerSpot   │
│Manager          │ │Manager           │
│       ↓         │ │       ↓          │
│ParkingSpotMgr   │ │ParkingSpotMgr    │
└─────────────────┘ └──────────────────┘


LAYER 6: LEVEL (DEPENDS ON LAYERS 1, 5)
════════════════════════════════════════
┌──────────────────────────────────┐
│      ParkingLevel                │
│             ↓                    │
│  - Map<VehicleType,              │
│        ParkingSpotManager>       │
│  - VehicleType (key)             │
└──────────────────────────────────┘


LAYER 7: BUILDING & TICKET (DEPEND ON LAYERS 2, 6)
═══════════════════════════════════════════════════
┌─────────────────────────┐      ┌──────────────────┐
│  ParkingBuilding        │      │     Ticket       │
│         ↓               │      │       ↓          │
│  - List<ParkingLevel>   │      │  - Vehicle       │
│  - Vehicle              │      │  - ParkingLevel  │
│  - Ticket               │      │  - ParkingSpot   │
└─────────────────────────┘      │  - LocalDateTime │
                                  └──────────────────┘


LAYER 8: GATES (DEPEND ON LAYERS 5, 7)
═══════════════════════════════════════
┌──────────────────────┐         ┌─────────────────────────┐
│   EntranceGate       │         │      ExitGate           │
│        ↓             │         │         ↓               │
│  - ParkingBuilding   │         │  - CostComputation      │
│  - Vehicle           │         │  - ParkingBuilding      │
│  - Ticket            │         │  - Ticket               │
└──────────────────────┘         │  - Payment              │
                                  └─────────────────────────┘


LAYER 9: FACADE (DEPENDS ON LAYERS 7, 8)
═════════════════════════════════════════
┌─────────────────────────────┐
│      ParkingLot             │
│           ↓                 │
│  - ParkingBuilding          │
│  - EntranceGate             │
│  - ExitGate                 │
│  - Vehicle (parameter)      │
│  - Ticket (pass-through)    │
│  - Payment (parameter)      │
└─────────────────────────────┘


LAYER 10: CLIENT (DEPENDS ON ALL)
══════════════════════════════════
┌────────────────────────────┐
│    ParkingClient           │
│           ↓                │
│  Creates & Wires:          │
│  - All Strategies          │
│  - All Managers            │
│  - All Levels              │
│  - Building                │
│  - Gates                   │
│  - ParkingLot              │
└────────────────────────────┘
```

---

## Complete Object Creation Flow (From ParkingClient.main)

```
main() starts
    │
    ├─→ new RandomLookupStrategy()
    │       [LAYER 4: Strategy Implementation]
    │
    ├─→ Create Level 1 Managers:
    │   │
    │   ├─→ new TwoWheelerSpotManager(
    │   │       List.of(new ParkingSpot("L1-S1"),
    │   │               new ParkingSpot("L1-S2")),
    │   │       strategy)
    │   │       [LAYER 5: Manager + LAYER 2: Spots]
    │   │       │
    │   │       └─→ super(spots, strategy)  [ParkingSpotManager constructor]
    │   │           │
    │   │           ├─→ stores List<ParkingSpot>
    │   │           ├─→ stores ParkingSpotLookupStrategy
    │   │           └─→ new ReentrantLock(true)
    │   │
    │   ├─→ new FourWheelerSpotManager(
    │   │       List.of(new ParkingSpot("L1-S3")),
    │   │       strategy)
    │   │       [LAYER 5: Manager + LAYER 2: Spots]
    │   │
    │   └─→ Create Map<VehicleType, ParkingSpotManager>
    │           levelOneManagers.put(TWO_WHEELER, twoWheelerMgr)
    │           levelOneManagers.put(FOUR_WHEELER, fourWheelerMgr)
    │
    ├─→ new ParkingLevel(1, levelOneManagers)
    │       [LAYER 6: Level aggregates Managers]
    │
    ├─→ Create Level 2 Managers: (same pattern)
    │   └─→ new ParkingLevel(2, levelTwoManagers)
    │
    ├─→ new CostComputation(new FixedPricingStrategy())
    │       [LAYER 5: Computation + LAYER 4: Strategy]
    │
    ├─→ new ParkingBuilding(
    │       List.of(level1, level2),
    │       costComputation)
    │       [LAYER 7: Building aggregates Levels]
    │
    ├─→ new EntranceGate()
    │       [LAYER 8: Entry point]
    │
    ├─→ new ExitGate(costComputation)
    │       [LAYER 8: Exit point]
    │
    ├─→ new ParkingLot(
    │       parkingBuilding,
    │       entranceGate,
    │       exitGate)
    │       [LAYER 9: Facade aggregates all]
    │
    ├─→ new Vehicle("BIKE-101", VehicleType.TWO_WHEELER)
    │       [LAYER 2: Entity]
    │
    ├─→ parkingLot.vehicleArrives(bike)
    │       [ENTRY FLOW - see detailed flow below]
    │       │
    │       └─→ Returns Ticket (created in ParkingBuilding.allocate)
    │
    ├─→ new CashPayment()
    │       [LAYER 4: Payment strategy]
    │
    └─→ parkingLot.vehicleExits(ticket, cashPayment)
            [EXIT FLOW - see detailed flow below]
```

---

## Vehicle Entry Flow (Complete Call Chain)

```
1. Client Code
   parkingLot.vehicleArrives(bike)
       │
       ▼
2. ParkingLot (Facade)
   vehicleArrives(vehicle)
       │
       ├─→ entranceGate.enter(building, vehicle)
       │       │
       │       ▼
3. EntranceGate
   enter(building, vehicle)
       │
       ├─→ building.allocate(vehicle)
       │       │
       │       ▼
4. ParkingBuilding
   allocate(vehicle)
       │
       ├─→ for each level in levels:
       │   │
       │   ├─→ level.hasAvailability(vehicle.getVehicleType())
       │   │       │
       │   │       ▼
5. ParkingLevel
   hasAvailability(type)
       │
       ├─→ manager = managers.get(type)  [Map lookup]
       │
       ├─→ manager.hasFreeSpot()
       │       │
       │       ▼
6. ParkingSpotManager (TwoWheeler or FourWheeler)
   hasFreeSpot()
       │
       ├─→ lock.lock()  [Thread-safe]
       │
       ├─→ spots.stream().anyMatch(ParkingSpot::isSpotFree)
       │       │
       │       ▼
7. ParkingSpot (multiple instances checked)
   isSpotFree()
       │
       └─→ return isFree

   [Back to ParkingLevel]
   level.park(type)
       │
       ▼
8. ParkingLevel
   park(type)
       │
       ├─→ manager = managers.get(type)
       │
       ├─→ manager.park()
       │       │
       │       ▼
9. ParkingSpotManager
   park()
       │
       ├─→ lock.lock()
       │
       ├─→ spot = strategy.selectSpot(spots)
       │       │
       │       ▼
10. RandomLookupStrategy
    selectSpot(spots)
        │
        ├─→ for each spot:
        │       if spot.isSpotFree():
        │           return spot
        │
        └─→ [Back to ParkingSpotManager]

11. ParkingSpotManager (continued)
    park() continued
        │
        ├─→ spot.occupySpot()
        │       │
        │       ▼
12. ParkingSpot
    occupySpot()
        │
        └─→ isFree = false

    [Back to ParkingBuilding]
    
13. ParkingBuilding
    allocate(vehicle) continued
        │
        ├─→ new Ticket(vehicle, level, spot)
        │       │
        │       ▼
14. Ticket Constructor
    Ticket(vehicle, level, spot)
        │
        ├─→ this.vehicle = vehicle
        ├─→ this.level = level
        ├─→ this.spot = spot
        └─→ this.entryTime = LocalDateTime.now()

    [Returns Ticket all the way back to Client]
```

---

## Vehicle Exit Flow (Complete Call Chain)

```
1. Client Code
   parkingLot.vehicleExits(ticket, cashPayment)
       │
       ▼
2. ParkingLot (Facade)
   vehicleExits(ticket, payment)
       │
       ├─→ exitGate.completeExit(building, ticket, payment)
       │       │
       │       ▼
3. ExitGate
   completeExit(building, ticket, payment)
       │
       ├─→ amount = calculatePrice(ticket)
       │       │
       │       ▼
4. ExitGate (private method)
   calculatePrice(ticket)
       │
       ├─→ costComputation.computeCost(ticket)
       │       │
       │       ▼
5. CostComputation
   computeCost(ticket)
       │
       ├─→ pricingStrategy.computeCost(ticket)
       │       │
       │       ▼
6. FixedPricingStrategy
   computeCost(ticket)
       │
       └─→ return 100.0  [Fixed price]

   [Back to ExitGate]

7. ExitGate (continued)
   completeExit() continued
       │
       ├─→ success = payment.pay(amount)
       │       │
       │       ▼
8. CashPayment (or UpiPayment)
   pay(amount)
       │
       ├─→ System.out.println("Payment of " + amount + " done via Cash")
       └─→ return true

   [Back to ExitGate]

9. ExitGate (continued)
   completeExit() continued
       │
       ├─→ if (!success) throw RuntimeException
       │
       ├─→ building.release(ticket)
       │       │
       │       ▼
10. ParkingBuilding
    release(ticket)
        │
        ├─→ ticket.getLevel().unPark(
        │       ticket.getVehicle().getVehicleType(),
        │       ticket.getSpot())
        │       │
        │       ▼
11. ParkingLevel
    unPark(type, spot)
        │
        ├─→ manager = managers.get(type)
        │
        ├─→ manager.unPark(spot)
        │       │
        │       ▼
12. ParkingSpotManager
    unPark(spot)
        │
        ├─→ lock.lock()
        │
        ├─→ spot.releaseSpot()
        │       │
        │       ▼
13. ParkingSpot
    releaseSpot()
        │
        └─→ isFree = true

    [Spot is now available again]
```

---

## Relationship Matrix (All 21 Classes)

| Class                        | Depends On                                    | Relationship Type           |
|------------------------------|-----------------------------------------------|-----------------------------|
| VehicleType (enum)           | None                                          | -                           |
| Vehicle                      | VehicleType                                   | HAS-A                       |
| ParkingSpot                  | None                                          | -                           |
| ParkingSpotLookupStrategy    | ParkingSpot                                   | Uses (interface)            |
| RandomLookupStrategy         | ParkingSpotLookupStrategy, ParkingSpot        | IMPLEMENTS, Uses            |
| pricingStrategy              | Ticket                                        | Uses (interface)            |
| FixedPricingStrategy         | pricingStrategy, Ticket                       | IMPLEMENTS, Uses            |
| CostComputation              | pricingStrategy, Ticket                       | HAS-A, Uses                 |
| Payment                      | None                                          | Interface                   |
| CashPayment                  | Payment                                       | IMPLEMENTS                  |
| UpiPayment                   | Payment                                       | IMPLEMENTS                  |
| ParkingSpotManager           | ParkingSpot, ParkingSpotLookupStrategy,       | HAS-A (list), HAS-A,        |
|                              | ReentrantLock                                 | HAS-A                       |
| TwoWheelerSpotManager        | ParkingSpotManager                            | IS-A (extends)              |
| FourWheelerSpotManager       | ParkingSpotManager                            | IS-A (extends)              |
| ParkingLevel                 | VehicleType, ParkingSpotManager, ParkingSpot  | Uses, HAS-A (map), Uses     |
| Ticket                       | Vehicle, ParkingLevel, ParkingSpot,           | HAS-A (all four)            |
|                              | LocalDateTime                                 |                             |
| ParkingBuilding              | ParkingLevel, Vehicle, Ticket, ParkingSpot,   | HAS-A (list), Uses,         |
|                              | CostComputation                               | Creates, Uses               |
| EntranceGate                 | ParkingBuilding, Vehicle, Ticket              | Uses, Uses, Returns         |
| ExitGate                     | CostComputation, ParkingBuilding, Ticket,     | HAS-A, Uses, Uses, Uses     |
|                              | Payment                                       |                             |
| ParkingLot                   | ParkingBuilding, EntranceGate, ExitGate,      | HAS-A (all three), Uses,    |
|                              | Vehicle, Ticket, Payment                      | Uses, Uses                  |
| ParkingClient                | ALL ABOVE CLASSES                             | Creates, Wires, Uses        |

---

## Dependency Count by Layer

```
Layer 1 (Enums):           0 dependencies
Layer 2 (Entities):        1 dependency  (Vehicle → VehicleType)
Layer 3 (Interfaces):      1-2 dependencies each
Layer 4 (Implementations): 1 dependency  (Interface)
Layer 5 (Managers):        3-4 dependencies
Layer 6 (Level):           5+ dependencies
Layer 7 (Building):        6+ dependencies
Layer 8 (Gates):           4-6 dependencies
Layer 9 (Facade):          8+ dependencies
Layer 10 (Client):         ALL dependencies (20+ classes)
```

---

## Thread-Safety Chain

```
Multiple Threads
    │
    ├─→ Thread 1: parkingLot.vehicleArrives(bike1)
    │       │
    │       └─→ TwoWheelerSpotManager.park()
    │           │
    │           └─→ lock.lock() [Thread 1 acquires lock]
    │               └─→ Critical Section
    │
    └─→ Thread 2: parkingLot.vehicleArrives(bike2)
            │
            └─→ TwoWheelerSpotManager.park()
                │
                └─→ lock.lock() [Thread 2 BLOCKS waiting]
                    └─→ Waits until Thread 1 releases

Note: FourWheelerSpotManager has SEPARATE lock
      → bike and car can park simultaneously
```

---

## Strategy Pattern Flow

```
THREE Strategy Patterns in System:

1. Spot Selection Strategy
   ═══════════════════════
   ParkingSpotManager ──HAS-A──► ParkingSpotLookupStrategy
                                          │
                                          ├─→ RandomLookupStrategy
                                          ├─→ (Future: NearestStrategy)
                                          └─→ (Future: PreferredStrategy)

2. Pricing Strategy
   ════════════════
   CostComputation ──HAS-A──► pricingStrategy
                                   │
                                   ├─→ FixedPricingStrategy
                                   ├─→ (Future: HourlyPricingStrategy)
                                   └─→ (Future: DynamicPricingStrategy)

3. Payment Strategy
   ════════════════
   ExitGate ──USES──► Payment
                         │
                         ├─→ CashPayment
                         ├─→ UpiPayment
                         └─→ (Future: CardPayment, WalletPayment)
```

---

## Key Design Insights

### Dependency Inversion Principle
```
High-Level Module                    Low-Level Module
────────────────                     ────────────────
ParkingSpotManager  ──depends on──►  ParkingSpotLookupStrategy (interface)
                                              △
                                              │ implements
                                              │
                                     RandomLookupStrategy (concrete)

Both depend on abstraction, not concrete implementation.
```

### Open/Closed Principle
```
ParkingSpotManager (abstract)
        │
        ├─→ Add new vehicle type? Create new XxxSpotManager
        │
pricingStrategy (interface)
        │
        ├─→ Add new pricing model? Implement new XxxPricingStrategy
        │
Payment (interface)
        │
        └─→ Add new payment method? Implement new XxxPayment

System is OPEN for extension, CLOSED for modification.
```

### Single Responsibility Principle
```
ParkingSpot         → Manages spot state
ParkingSpotManager  → Manages collection of spots
ParkingLevel        → Aggregates managers by vehicle type
ParkingBuilding     → Coordinates levels
EntranceGate        → Handles entry
ExitGate            → Handles exit + payment
ParkingLot          → Facade for external API
```

---

## Complete System at a Glance

```
21 Classes Total:
  - 1 Enum (VehicleType)
  - 3 Entities (Vehicle, ParkingSpot, Ticket)
  - 3 Strategy Interfaces
  - 5 Strategy Implementations
  - 3 Managers (1 abstract + 2 concrete)
  - 1 Level Aggregator
  - 1 Building Coordinator
  - 2 Gates
  - 1 Facade
  - 1 Client

Design Patterns:
  - Strategy Pattern (×3)
  - Template Method Pattern
  - Facade Pattern
  - Dependency Injection

Relationships:
  - 4 IS-A (inheritance)
  - 20+ HAS-A (composition)
  - 30+ USES (dependency)

Thread Safety:
  - ReentrantLock per manager
  - Fair locking (FIFO)
  - Separate locks per vehicle type
```
