# Car Rental System - Design Documentation

## All LLD Objects in this Repository

| #  | LLD Object          | Folder          |
|----|---------------------|-----------------|
| 1  | Parking Lot System  | `parking_Lot/`  |
| 2  | TicTacToe Game      | `TicTacToe/`    |
| 3  | Car Rental System   | `carRental/`    |
| 4  | Snake and Ladder    | `snakeNladder/` |

## Entities Used (24 Classes)

| #  | Entity                     | Type                | Package         |
|----|----------------------------|---------------------|-----------------|
| 1  | VehicleType                | Enum                | product         |
| 2  | VehicleStatus              | Enum                | product         |
| 3  | ReservationType            | Enum                | reservation     |
| 4  | ReservationStatus          | Enum                | reservation     |
| 5  | PaymentMode                | Enum                | payment         |
| 6  | Location                   | Value Object        | carRental       |
| 7  | User                       | Entity              | carRental       |
| 8  | Vehicle                    | Entity              | product         |
| 9  | DateInterval               | Value Object        | product         |
| 10 | Reservation                | Entity              | reservation     |
| 11 | ReservationRepository      | Repository          | reservation     |
| 12 | VehicleInventoryManager    | Service / Manager   | product         |
| 13 | ReservationManager         | Service / Manager   | reservation     |
| 14 | BillingStrategy            | Interface           | Bill            |
| 15 | DailyBillingStrategy       | Strategy Impl       | Bill            |
| 16 | Bill                       | Entity              | Bill            |
| 17 | BillManager                | Service / Manager   | Bill            |
| 18 | PaymentStrategy            | Interface           | payment         |
| 19 | UPIPaymentStrategy         | Strategy Impl       | payment         |
| 20 | Payment                    | Entity              | payment         |
| 21 | PaymentManager             | Service / Manager   | payment         |
| 22 | Store                      | Aggregate Root      | carRental       |
| 23 | VehicleRentalSystem        | Facade              | carRental       |
| 24 | Demo                       | Main / Client       | carRental       |

---

## Overview
A comprehensive vehicle rental management system demonstrating advanced object-oriented design principles including Strategy Pattern, Repository Pattern, thread-safe operations, and dependency injection. The system supports multiple vehicle types, flexible billing strategies, multiple payment methods, and reservation management with date overlap checking.

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

┌─────────────────────────┐
│   <<enumeration>>       │
│     VehicleStatus       │
├─────────────────────────┤
│ + AVAILABLE             │
│ + BOOKED                │
│ + MAINTENANCE           │
└─────────────────────────┘

┌─────────────────────────┐
│   <<enumeration>>       │
│   ReservationType       │
├─────────────────────────┤
│ + HOURLY                │
│ + DAILY                 │
└─────────────────────────┘

┌─────────────────────────┐
│   <<enumeration>>       │
│  ReservationStatus      │
├─────────────────────────┤
│ + SCHEDULED             │
│ + IN_USE                │
│ + COMPLETED             │
│ + CANCELLED             │
└─────────────────────────┘

┌─────────────────────────┐
│   <<enumeration>>       │
│     PaymentMode         │
├─────────────────────────┤
│ + CASH                  │
│ + ONLINE                │
│ + UPI                   │
└─────────────────────────┘

┌──────────────────────────────────┐
│           Location               │
├──────────────────────────────────┤
│ + buildingNo: int                │
│ + area: String                   │
│ + city: String                   │
│ + state: String                  │
│ + country: String                │
│ + pincode: int                   │
├──────────────────────────────────┤
│ + Location(...)                  │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│             User                 │
├──────────────────────────────────┤
│ + userId: int                    │
│ + userName: String               │
│ + drivingLicenseNo: String       │
├──────────────────────────────────┤
│ + User(id, name, license)        │
│ + getUserId(): int               │
│ + getUserName(): String          │
│ + getDrivingLicenseNo(): String  │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│           Vehicle                │
├──────────────────────────────────┤
│ - vehicleID: int                 │
│ - vehicleNumber: String          │
│ - vehicleType: VehicleType       │──────► HAS-A VehicleType
│ - dailyRentalCost: double        │
│ - vehicleStatus: VehicleStatus   │──────► HAS-A VehicleStatus
├──────────────────────────────────┤
│ + Vehicle(id, number, type)      │
│ + getVehicleID(): int            │
│ + getVehicleType(): VehicleType  │
│ + getVehicleStatus(): Status     │
│ + getDailyRentalCost(): double   │
│ + setStatus(status): void        │
│ + setDailyRentalCost(cost): void │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│         DateInterval             │
├──────────────────────────────────┤
│ - from: LocalDate                │
│ - to: LocalDate                  │
├──────────────────────────────────┤
│ + DateInterval(from, to)         │
│ + getFrom(): LocalDate           │
│ + getTo(): LocalDate             │
│ + overlaps(other): boolean       │
└──────────────────────────────────┘

┌─────────────────────────────────────────┐
│          Reservation                    │
├─────────────────────────────────────────┤
│ - reservationId: int                    │
│ - vehicleId: int                        │
│ - userId: int                           │
│ - dateBookedFrom: LocalDate             │
│ - dateBookedTo: LocalDate               │
│ - reservationType: ReservationType      │──────► HAS-A ReservationType
│ - reservationStatus: ReservationStatus  │──────► HAS-A ReservationStatus
├─────────────────────────────────────────┤
│ + Reservation(...)                      │
│ + getReservationId(): int               │
│ + getVehicleId(): int                   │
│ + getUserId(): int                      │
│ + getDateBookedFrom(): LocalDate        │
│ + getDateBookedTo(): LocalDate          │
│ + getReservationType(): Type            │
│ + getReservationStatus(): Status        │
│ + setReservationStatus(status): void    │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│    ReservationRepository                │
├─────────────────────────────────────────┤
│ - reservations: Map<Int,Reservation>    │──────► HAS-A Map<Reservation>
├─────────────────────────────────────────┤
│ + save(reservation): void               │
│ + findById(id): Optional<Reservation>   │
│ + remove(id): void                      │
│ + getAll(): Map<Int,Reservation>        │
└─────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│    VehicleInventoryManager                   │
├──────────────────────────────────────────────┤
│ - vehicles: ConcurrentMap<Int,Vehicle>       │──────► HAS-A Map<Vehicle>
│ - vehicleBookingIds: Map<Int,List<Int>>      │
│ - vehicleLocks: Map<Int,ReentrantLock>       │
│ - reservationRepository: ReservationRepo     │──────► HAS-A ReservationRepository
├──────────────────────────────────────────────┤
│ + addVehicle(vehicle): void                  │
│ + getVehicle(id): Optional<Vehicle>          │
│ + isAvailable(id, from, to): boolean         │
│ + reserve(id, resId, from, to): boolean      │
│ + release(id, resId): void                   │
│ + getAvailableVehicles(type, from, to): List │
│ + setReservationRepository(repo): void       │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│        ReservationManager                    │
├──────────────────────────────────────────────┤
│ - inventory: VehicleInventoryManager         │──────► HAS-A VehicleInventoryManager
│ - reservationRepository: ReservationRepo     │──────► HAS-A ReservationRepository
│ - reservationIdGenerator: AtomicInteger      │
├──────────────────────────────────────────────┤
│ + ReservationManager(inventory)              │
│ + createReservation(...): Reservation        │
│ + cancelReservation(id): void                │
│ + startTrip(id): void                        │
│ + submitVehicle(id): void                    │
│ + findByID(id): Optional<Reservation>        │
│ + remove(id): void                           │
└──────────────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │  <<interface>>                       │
        │    BillingStrategy                   │
        ├──────────────────────────────────────┤
        │ + generateBill(res): Bill            │
        └──────────────────────────────────────┘
                        △
                        │ IMPLEMENTS
                        │
        ┌───────────────────────────────────────┐
        │    DailyBillingStrategy               │
        ├───────────────────────────────────────┤
        │ - inventory: VehicleInventoryManager  │──────► HAS-A VehicleInventoryManager
        │ - billIdGenerator: AtomicInteger      │
        ├───────────────────────────────────────┤
        │ + DailyBillingStrategy(inventory)     │
        │ + generateBill(res): Bill             │
        └───────────────────────────────────────┘

┌─────────────────────────────────────────┐
│              Bill                       │
├─────────────────────────────────────────┤
│ - billId: int                           │
│ - reservationId: int                    │
│ - totalBillAmount: double               │
│ - billPaid: boolean                     │
├─────────────────────────────────────────┤
│ + Bill(id, resId, amount)               │
│ + getBillId(): int                      │
│ + getReservationId(): int               │
│ + getTotalBillAmount(): double          │
│ + isBillPaid(): boolean                 │
│ + setBillPaid(paid): void               │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│           BillManager                   │
├─────────────────────────────────────────┤
│ - billingStrategy: BillingStrategy      │──────► HAS-A BillingStrategy
│ - bills: Map<Integer, Bill>             │──────► HAS-A Map<Bill>
├─────────────────────────────────────────┤
│ + BillManager(strategy)                 │
│ + generateBill(reservation): Bill       │
│ + getBill(billId): Optional<Bill>       │
│ + setBillingStrategy(strategy): void    │
└─────────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │  <<interface>>                       │
        │    PaymentStrategy                   │
        ├──────────────────────────────────────┤
        │ + processPayment(bill, amt): Payment │
        └──────────────────────────────────────┘
                        △
                        │ IMPLEMENTS
                        │
        ┌───────────────────────────────────────┐
        │     UPIPaymentStrategy                │
        ├───────────────────────────────────────┤
        │ - paymentIdGenerator: AtomicInteger   │
        ├───────────────────────────────────────┤
        │ + processPayment(bill, amt): Payment  │
        └───────────────────────────────────────┘

┌─────────────────────────────────────────┐
│            Payment                      │
├─────────────────────────────────────────┤
│ - paymentId: int                        │
│ - billId: int                           │
│ - amountPaid: double                    │
│ - paymentMode: PaymentMode              │──────► HAS-A PaymentMode
│ - paymentDate: Date                     │
├─────────────────────────────────────────┤
│ + Payment(id, billId, amt, mode, date)  │
│ + getPaymentId(): int                   │
│ + getBillId(): int                      │
│ + getAmountPaid(): double               │
│ + getPaymentMode(): PaymentMode         │
│ + getPaymentDate(): Date                │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│         PaymentManager                  │
├─────────────────────────────────────────┤
│ - paymentStrategy: PaymentStrategy      │──────► HAS-A PaymentStrategy
│ - payments: Map<Integer, Payment>       │──────► HAS-A Map<Payment>
├─────────────────────────────────────────┤
│ + PaymentManager(strategy)              │
│ + makePayment(bill, amt): Payment       │
│ + getPaymentsForBill(billId): List      │
│ + getPayment(paymentId): Optional       │
│ + setPaymentStrategy(strategy): void    │
└─────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│              Store                           │
├──────────────────────────────────────────────┤
│ - storeId: int                               │
│ - storeLocation: Location                    │──────► HAS-A Location
│ - inventory: VehicleInventoryManager         │──────► HAS-A VehicleInventoryManager
│ - reservationManager: ReservationManager     │──────► HAS-A ReservationManager
│ - billManager: BillManager                   │──────► HAS-A BillManager
│ - paymentManager: PaymentManager             │──────► HAS-A PaymentManager
├──────────────────────────────────────────────┤
│ + Store(storeId, location)                   │
│ + getVehicles(type, from, to): List          │
│ + createReservation(...): Reservation        │
│ + cancelReservation(id): void                │
│ + startTrip(id): void                        │
│ + submitVehicle(id): void                    │
│ + generateBill(id, strategy): Bill           │
│ + makePayment(bill, strategy, amt): Payment  │
│ + getInventory(): VehicleInventoryManager    │
│ + getStoreId(): int                          │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│      VehicleRentalSystem                     │
├──────────────────────────────────────────────┤
│ - storeList: List<Store>                     │──────► HAS-A List<Store>
│ - userList: List<User>                       │──────► HAS-A List<User>
├──────────────────────────────────────────────┤
│ + VehicleRentalSystem()                      │
│ + getStore(storeId): Store                   │
│ + getUser(userId): User                      │
│ + addStore(store): void                      │
│ + addUser(user): void                        │
│ + removeStore(storeId): void                 │
│ + removeUser(userId): void                   │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│            Demo (Main)                       │
├──────────────────────────────────────────────┤
│ + main(args): void                           │──────► USES All Components
└──────────────────────────────────────────────┘
```

---

## Relationships Summary

### IS-A (Inheritance/Implementation) Relationships

1. **DailyBillingStrategy IMPLEMENTS BillingStrategy**
   - Concrete implementation for daily rental billing
   
2. **UPIPaymentStrategy IMPLEMENTS PaymentStrategy**
   - Concrete implementation for UPI payment processing

### HAS-A (Composition/Aggregation) Relationships

1. **Vehicle HAS-A VehicleType** (Composition)
   - Each vehicle has a type (TWO_WHEELER or FOUR_WHEELER)

2. **Vehicle HAS-A VehicleStatus** (Composition)
   - Each vehicle has a status (AVAILABLE, BOOKED, MAINTENANCE)

3. **Reservation HAS-A ReservationType** (Composition)
   - Each reservation has a type (HOURLY or DAILY)

4. **Reservation HAS-A ReservationStatus** (Composition)
   - Each reservation has a status (SCHEDULED, IN_USE, COMPLETED, CANCELLED)

5. **Payment HAS-A PaymentMode** (Composition)
   - Each payment has a mode (CASH, ONLINE, UPI)

6. **Store HAS-A Location** (Composition)
   - Each store has a physical location

7. **Store HAS-A VehicleInventoryManager** (Composition)
   - Store manages vehicle inventory

8. **Store HAS-A ReservationManager** (Composition)
   - Store manages reservations

9. **Store HAS-A BillManager** (Composition)
   - Store manages billing

10. **Store HAS-A PaymentManager** (Composition)
    - Store manages payments

11. **VehicleInventoryManager HAS-A Map<Integer, Vehicle>** (Composition)
    - Manages collection of vehicles

12. **VehicleInventoryManager HAS-A ReservationRepository** (Composition)
    - References reservation repository for date checking

13. **ReservationManager HAS-A VehicleInventoryManager** (Composition)
    - References inventory for availability checks

14. **ReservationManager HAS-A ReservationRepository** (Composition)
    - Manages reservation storage

15. **ReservationRepository HAS-A Map<Integer, Reservation>** (Composition)
    - Stores all reservations

16. **BillManager HAS-A BillingStrategy** (Composition)
    - Uses strategy for bill generation

17. **BillManager HAS-A Map<Integer, Bill>** (Composition)
    - Stores all generated bills

18. **DailyBillingStrategy HAS-A VehicleInventoryManager** (Composition)
    - Needs inventory to get vehicle rental costs

19. **PaymentManager HAS-A PaymentStrategy** (Composition)
    - Uses strategy for payment processing

20. **PaymentManager HAS-A Map<Integer, Payment>** (Composition)
    - Stores all payment records

21. **VehicleRentalSystem HAS-A List<Store>** (Composition)
    - System manages multiple stores

22. **VehicleRentalSystem HAS-A List<User>** (Composition)
    - System manages users

---

## Class Details

### 1. VehicleType (Enum)
**Purpose:** Defines supported vehicle categories

**Values:**
- `TWO_WHEELER` - Bikes, scooters, motorcycles
- `FOUR_WHEELER` - Cars, SUVs

---

### 2. VehicleStatus (Enum)
**Purpose:** Represents the current status of a vehicle

**Values:**
- `AVAILABLE` - Vehicle is free for booking
- `BOOKED` - Vehicle is reserved/rented
- `MAINTENANCE` - Vehicle is under maintenance

---

### 3. ReservationType (Enum)
**Purpose:** Defines rental duration types

**Values:**
- `HOURLY` - Hourly rental
- `DAILY` - Daily rental

---

### 4. ReservationStatus (Enum)
**Purpose:** Tracks the lifecycle of a reservation

**Values:**
- `SCHEDULED` - Reservation created but trip not started
- `IN_USE` - Trip is currently active
- `COMPLETED` - Vehicle returned, reservation complete
- `CANCELLED` - Reservation cancelled by user

---

### 5. PaymentMode (Enum)
**Purpose:** Defines supported payment methods

**Values:**
- `CASH` - Cash payment
- `ONLINE` - Online payment
- `UPI` - UPI payment

---

### 6. Location (Value Object)
**Purpose:** Represents a physical address

**Attributes:**
- `buildingNo: int` - Building/house number
- `area: String` - Area/locality
- `city: String` - City name
- `state: String` - State name
- `country: String` - Country name
- `pincode: int` - Postal code

**Methods:**
- `Location(int buildingNo, String area, String city, String state, String country, int pincode)` - Constructor

**Design:** Immutable value object (all fields package-private)

---

### 7. User (Entity)
**Purpose:** Represents a customer using the rental system

**Attributes:**
- `userId: int` - Unique user identifier
- `userName: String` - User's name
- `drivingLicenseNo: String` - Driving license number

**Methods:**
- `User(int userId, String userName, String drivingLicenseNo)` - Constructor
- `getUserId(): int` - Returns user ID
- `getUserName(): String` - Returns user name
- `getDrivingLicenseNo(): String` - Returns license number
- Setter methods for all attributes

---

### 8. Vehicle (Entity)
**Purpose:** Represents a rentable vehicle

**Attributes:**
- `vehicleID: int` - Unique vehicle identifier (final)
- `vehicleNumber: String` - Vehicle registration number (final)
- `vehicleType: VehicleType` - Category of vehicle (final)
- `dailyRentalCost: double` - Daily rental rate
- `vehicleStatus: VehicleStatus` - Current status (volatile for thread safety)

**Methods:**
- `Vehicle(int vehicleID, String vehicleNumber, VehicleType vehicleType)` - Constructor
- `getVehicleID(): int` - Returns vehicle ID
- `getVehicleType(): VehicleType` - Returns vehicle type
- `getVehicleStatus(): VehicleStatus` - Returns current status
- `getDailyRentalCost(): double` - Returns rental rate
- `setStatus(VehicleStatus vehicleStatus): void` - Updates status
- `setDailyRentalCost(double dailyRentalCost): void` - Sets rental rate

**Relationships:**
- HAS-A VehicleType
- HAS-A VehicleStatus

**Thread Safety:** vehicleStatus is volatile

---

### 9. DateInterval (Value Object)
**Purpose:** Represents a date range with overlap detection

**Attributes:**
- `from: LocalDate` - Start date (final)
- `to: LocalDate` - End date (final)

**Methods:**
- `DateInterval(LocalDate from, LocalDate to)` - Constructor with validation
- `getFrom(): LocalDate` - Returns start date
- `getTo(): LocalDate` - Returns end date
- `overlaps(DateInterval other): boolean` - Checks if two intervals overlap

**Validation:** Throws IllegalArgumentException if end date is before start date

**Algorithm:** Overlap check: `!(this.to < other.from || this.from > other.to)`

---

### 10. Reservation (Entity)
**Purpose:** Represents a vehicle rental reservation

**Attributes:**
- `reservationId: int` - Unique reservation ID (final)
- `vehicleId: int` - ID of reserved vehicle (final)
- `userId: int` - ID of user who made reservation (final)
- `dateBookedFrom: LocalDate` - Rental start date (final)
- `dateBookedTo: LocalDate` - Rental end date (final)
- `reservationType: ReservationType` - Type of rental (final)
- `reservationStatus: ReservationStatus` - Current status (mutable)

**Methods:**
- `Reservation(int reservationId, int vehicleId, int userId, LocalDate dateBookedFrom, LocalDate dateBookedTo, ReservationType reservationType)` - Constructor
- Getter methods for all attributes
- `setReservationStatus(ReservationStatus reservationStatus): void` - Updates status

**Relationships:**
- HAS-A ReservationType
- HAS-A ReservationStatus

**Design:** Mostly immutable except for status updates

---

### 11. ReservationRepository (Repository)
**Purpose:** In-memory storage for reservations

**Attributes:**
- `reservations: Map<Integer, Reservation>` - ConcurrentHashMap for thread safety

**Methods:**
- `save(Reservation reservation): void` - Stores or updates reservation
- `findById(int reservationId): Optional<Reservation>` - Retrieves reservation
- `remove(int reservationId): void` - Deletes reservation
- `getAll(): Map<Integer, Reservation>` - Returns all reservations

**Relationships:**
- HAS-A Map<Integer, Reservation>

**Thread Safety:** Uses ConcurrentHashMap

**Design Pattern:** Repository Pattern

---

### 12. VehicleInventoryManager (Service)
**Purpose:** Thread-safe vehicle inventory and availability management

**Attributes:**
- `vehicles: ConcurrentMap<Integer, Vehicle>` - All vehicles
- `vehicleBookingIds: ConcurrentMap<Integer, List<Integer>>` - Maps vehicle to its reservation IDs
- `vehicleLocks: ConcurrentMap<Integer, ReentrantLock>` - Per-vehicle locks
- `reservationRepository: ReservationRepository` - Reference to reservation data

**Methods:**
- `addVehicle(Vehicle vehicle): void` - Adds vehicle to inventory
- `getVehicle(int vehicleId): Optional<Vehicle>` - Retrieves vehicle by ID
- `setReservationRepository(ReservationRepository reservationRepository): void` - Injects repository
- `isAvailable(int vehicleId, LocalDate from, LocalDate to): boolean`
  - Checks if vehicle available for date range
  - Checks vehicle status
  - Checks for date overlaps with existing reservations
  
- `reserve(int vehicleId, int reservationId, LocalDate from, LocalDate to): boolean`
  - Thread-safe atomic reservation
  - Acquires per-vehicle lock
  - Checks availability
  - Adds reservation ID to vehicle's booking list
  - Updates vehicle status to BOOKED
  
- `release(int vehicleId, int reservationId): void`
  - Thread-safe atomic release
  - Removes reservation ID from vehicle's booking list
  - Sets status to AVAILABLE if no more bookings
  
- `getAvailableVehicles(VehicleType type, LocalDate from, LocalDate to): List<Vehicle>`
  - Searches for available vehicles by type and date range

**Relationships:**
- HAS-A Map<Integer, Vehicle>
- HAS-A ReservationRepository

**Thread Safety:**
- ConcurrentHashMap for vehicle storage
- Per-vehicle ReentrantLock for atomic reserve/release operations
- Prevents double-booking race conditions

**Design Pattern:** Service Layer with Repository Pattern

---

### 13. ReservationManager (Service)
**Purpose:** Manages reservation lifecycle

**Attributes:**
- `inventory: VehicleInventoryManager` - Reference to inventory
- `reservationRepository: ReservationRepository` - Reservation storage
- `reservationIdGenerator: AtomicInteger` - Thread-safe ID generation (starts at 20000)

**Methods:**
- `ReservationManager(VehicleInventoryManager inventory)` - Constructor
  - Creates repository
  - Injects repository into inventory
  
- `createReservation(int vehicleId, User user, LocalDate from, LocalDate to, ReservationType type): Reservation`
  - Generates unique reservation ID
  - Attempts to reserve vehicle via inventory
  - Creates reservation object
  - Saves to repository
  - Throws exception if vehicle unavailable
  
- `cancelReservation(int reservationId): void`
  - Updates status to CANCELLED
  - Releases vehicle in inventory
  - Removes from repository
  
- `startTrip(int reservationId): void`
  - Updates status to IN_USE
  
- `submitVehicle(int reservationId): void`
  - Updates status to COMPLETED
  - Releases vehicle in inventory
  
- `findByID(int reservationId): Optional<Reservation>` - Retrieves reservation
- `remove(int reservationId): void` - Removes from repository

**Relationships:**
- HAS-A VehicleInventoryManager
- HAS-A ReservationRepository

**Coordination:** Works closely with VehicleInventoryManager for availability

---

### 14. BillingStrategy (Interface)
**Purpose:** Strategy pattern for billing calculations

**Methods:**
- `generateBill(Reservation reservation): Bill` - Calculates and creates bill

**Implementations:**
- `DailyBillingStrategy` - Daily rate billing

**Design Pattern:** Strategy Pattern for flexible billing

---

### 15. DailyBillingStrategy (Concrete Strategy)
**Purpose:** Calculates bills based on daily rates

**Attributes:**
- `vehicleInventoryManager: VehicleInventoryManager` - Reference to get vehicle costs
- `billIdGenerator: AtomicInteger` - Thread-safe ID generation (starts at 5000)

**Methods:**
- `DailyBillingStrategy(VehicleInventoryManager vehicleInventoryManager)` - Constructor
- `generateBill(Reservation reservation): Bill`
  - Calculates number of days (inclusive)
  - Gets vehicle's daily rental cost
  - Computes total: days × daily rate
  - Creates and returns Bill

**Relationships:**
- IMPLEMENTS BillingStrategy
- HAS-A VehicleInventoryManager

**Algorithm:** `total = (days_between + 1) × daily_rate`

**Future Extensions:** HourlyBillingStrategy, WeeklyBillingStrategy

---

### 16. Bill (Entity)
**Purpose:** Represents a billing invoice

**Attributes:**
- `billId: int` - Unique bill identifier
- `reservationId: int` - Associated reservation ID
- `totalBillAmount: double` - Total amount to pay
- `billPaid: boolean` - Payment status

**Methods:**
- `Bill(int billId, int reservationId, double totalBillAmount)` - Constructor
- `getBillId(): int` - Returns bill ID
- `getReservationId(): int` - Returns reservation ID
- `getTotalBillAmount(): double` - Returns bill amount
- `isBillPaid(): boolean` - Returns payment status
- `setBillPaid(boolean billPaid): void` - Updates payment status

**Design:** Mutable to allow payment status updates

---

### 17. BillManager (Service)
**Purpose:** Manages bill generation and storage

**Attributes:**
- `billingStrategy: BillingStrategy` - Current billing strategy
- `bills: Map<Integer, Bill>` - All generated bills (ConcurrentHashMap)

**Methods:**
- `BillManager(BillingStrategy billingStrategy)` - Constructor with strategy injection
- `generateBill(Reservation reservation): Bill`
  - Delegates to billing strategy
  - Stores bill in map
  - Returns generated bill
  
- `getBill(int billId): Optional<Bill>` - Retrieves bill by ID
- `setBillingStrategy(BillingStrategy billingStrategy): void` - Updates strategy

**Relationships:**
- HAS-A BillingStrategy
- HAS-A Map<Integer, Bill>

**Thread Safety:** Uses ConcurrentHashMap

**Design Pattern:** Strategy Pattern + Service Layer

---

### 18. PaymentStrategy (Interface)
**Purpose:** Strategy pattern for payment processing

**Methods:**
- `processPayment(Bill bill, double paymentAmount): Payment` - Processes payment and returns receipt

**Implementations:**
- `UPIPaymentStrategy` - UPI payment processing

**Design Pattern:** Strategy Pattern

---

### 19. UPIPaymentStrategy (Concrete Strategy)
**Purpose:** Handles UPI payments

**Attributes:**
- `paymentIdGenerator: AtomicInteger` - Thread-safe ID generation (starts at 9000)

**Methods:**
- `processPayment(Bill bill, double paymentAmount): Payment`
  - Creates Payment object with UPI mode
  - Marks bill as paid
  - Returns payment receipt

**Relationships:**
- IMPLEMENTS PaymentStrategy

**Assumptions:** Payment always succeeds, no partial payments

**Future Extensions:** CashPaymentStrategy, CardPaymentStrategy

---

### 20. Payment (Entity)
**Purpose:** Represents a payment transaction receipt

**Attributes:**
- `paymentId: int` - Unique payment identifier (final)
- `billId: int` - Associated bill ID (final)
- `amountPaid: double` - Amount paid (final)
- `paymentMode: PaymentMode` - Payment method used (final)
- `paymentDate: Date` - Payment timestamp (final)

**Methods:**
- `Payment(int paymentId, int billId, double amountPaid, PaymentMode paymentMode, Date paymentDate)` - Constructor
- Getter methods for all attributes

**Relationships:**
- HAS-A PaymentMode

**Design:** Immutable receipt

---

### 21. PaymentManager (Service)
**Purpose:** Manages payment processing and storage

**Attributes:**
- `paymentStrategy: PaymentStrategy` - Current payment strategy
- `payments: Map<Integer, Payment>` - All payment records (ConcurrentHashMap)

**Methods:**
- `PaymentManager(PaymentStrategy paymentStrategy)` - Constructor with strategy injection
- `makePayment(Bill bill, double paymentAmount): Payment`
  - Delegates to payment strategy
  - Stores payment record
  - Returns payment
  
- `getPaymentsForBill(int billId): List<Payment>` - Retrieves all payments for a bill
- `getPayment(int paymentId): Optional<Payment>` - Retrieves payment by ID
- `setPaymentStrategy(PaymentStrategy paymentStrategy): void` - Updates strategy

**Relationships:**
- HAS-A PaymentStrategy
- HAS-A Map<Integer, Payment>

**Thread Safety:** Uses ConcurrentHashMap

**Design Pattern:** Strategy Pattern + Service Layer

---

### 22. Store (Aggregate Root)
**Purpose:** Represents a rental store location with full rental operations

**Attributes:**
- `storeId: int` - Unique store identifier (final)
- `storeLocation: Location` - Store address (final)
- `inventory: VehicleInventoryManager` - Vehicle inventory (final)
- `reservationManager: ReservationManager` - Reservation management (final)
- `billManager: BillManager` - Billing management (final)
- `paymentManager: PaymentManager` - Payment management (final)

**Methods:**
- `Store(int storeId, Location location)` - Constructor
  - Initializes all managers with default strategies
  - Sets up bidirectional inventory-reservation link
  
- `getVehicles(VehicleType type, LocalDate from, LocalDate to): List<Vehicle>`
  - Searches available vehicles
  
- `createReservation(int vehicleId, User user, LocalDate from, LocalDate to, ReservationType type): Reservation`
  - Delegates to reservation manager
  
- `cancelReservation(int reservationId): void` - Cancels reservation
- `startTrip(int reservationId): void` - Starts rental trip
- `submitVehicle(int reservationId): void` - Returns vehicle
- `generateBill(int reservationId, BillingStrategy billingStrategy): Bill`
  - Finds reservation
  - Sets billing strategy
  - Generates bill via bill manager
  
- `makePayment(Bill bill, PaymentStrategy paymentStrategy, double paymentAmount): Payment`
  - Sets payment strategy
  - Processes payment
  - Validates payment success
  - Removes reservation after successful payment
  
- `getInventory(): VehicleInventoryManager` - Returns inventory reference
- `getStoreId(): int` - Returns store ID

**Relationships:**
- HAS-A Location
- HAS-A VehicleInventoryManager
- HAS-A ReservationManager
- HAS-A BillManager
- HAS-A PaymentManager

**Design Pattern:** Facade Pattern + Aggregate Root (DDD)

**Business Logic:** Payment must succeed before reservation removal

---

### 23. VehicleRentalSystem (System Facade)
**Purpose:** Top-level system managing multiple stores and users

**Attributes:**
- `storeList: List<Store>` - All rental stores
- `userList: List<User>` - All registered users

**Methods:**
- `VehicleRentalSystem()` - Constructor
- `getStore(int storeId): Store` - Finds store by ID
- `getUser(int userId): User` - Retrieves user by ID
- `addStore(Store store): void` - Registers new store
- `addUser(User user): void` - Registers new user
- `removeStore(int storeId): void` - Removes store
- `removeUser(int userId): void` - Removes user

**Relationships:**
- HAS-A List<Store>
- HAS-A List<User>

**Design Pattern:** Facade Pattern

**Future Enhancement:** Could be split into StoreManager and UserManager

---

### 24. Demo (Main)
**Purpose:** Application entry point demonstrating system usage

**Methods:**
- `main(String[] args): void`
  - Creates VehicleRentalSystem
  - Creates stores with locations
  - Registers users
  - Adds vehicles to store inventory
  - Searches available vehicles
  - Creates reservation
  - Starts trip
  - Submits vehicle
  - Generates bill
  - Processes payment

**Demonstrates:**
- Full system initialization
- End-to-end rental workflow
- Strategy pattern usage
- Multi-store capability

---

## Design Patterns Used

### 1. **Strategy Pattern** (Multiple instances)
   - **BillingStrategy**: Different billing models (Daily, Hourly)
   - **PaymentStrategy**: Different payment methods (UPI, Cash, Online)

### 2. **Repository Pattern**
   - **ReservationRepository**: Separates data access from business logic
   - In-memory storage with consistent interface

### 3. **Facade Pattern**
   - **Store**: Unified interface to reservation, billing, and payment subsystems
   - **VehicleRentalSystem**: Top-level system facade

### 4. **Aggregate Root Pattern** (DDD)
   - **Store**: Coordinates all rental operations
   - Ensures consistency across reservation, billing, payment

### 5. **Dependency Injection**
   - Strategies injected into managers
   - Repository injected into inventory
   - Promotes loose coupling and testability

### 6. **Thread-Safe Singleton Locks**
   - Per-vehicle locks prevent race conditions
   - ConcurrentHashMap for shared data structures

### 7. **Single Responsibility Principle**
   - Each class has one clear responsibility
   - Inventory manages vehicles, ReservationManager handles bookings, etc.

### 8. **Open/Closed Principle**
   - New billing types: Add new BillingStrategy implementation
   - New payment methods: Add new PaymentStrategy implementation
   - New vehicle types: Add to enum, system handles automatically

---

## Object Interaction Flow

### Vehicle Reservation Flow
```
User Searches for Vehicle
    │
    └──> VehicleRentalSystem.getStore(storeId)
         │
         └──> Store.getVehicles(type, from, to)
              │
              └──> VehicleInventoryManager.getAvailableVehicles(type, from, to)
                   │
                   ├──> Filter by VehicleType
                   ├──> For each vehicle:
                   │    └──> isAvailable(vehicleId, from, to)
                   │         │
                   │         ├──> Check VehicleStatus != MAINTENANCE
                   │         ├──> Create DateInterval(from, to)
                   │         ├──> Get vehicleBookingIds
                   │         └──> For each reservationId:
                   │              └──> ReservationRepository.findById(resId)
                   │                   └──> Check DateInterval.overlaps()
                   │
                   └──> Return List<Vehicle>

User Creates Reservation
    │
    └──> Store.createReservation(vehicleId, user, from, to, type)
         │
         └──> ReservationManager.createReservation(...)
              │
              ├──> Generate reservationId (AtomicInteger)
              ├──> VehicleInventoryManager.reserve(vehicleId, resId, from, to)
              │    │
              │    └──> lockForVehicle(vehicleId).lock()
              │         ├──> isAvailable() check
              │         ├──> Add reservationId to vehicleBookingIds
              │         ├──> Vehicle.setStatus(BOOKED)
              │         └──> lock.unlock()
              │
              ├──> new Reservation(...) with SCHEDULED status
              ├──> ReservationRepository.save(reservation)
              └──> Return Reservation
```

### Trip Start Flow
```
User Starts Trip
    │
    └──> Store.startTrip(reservationId)
         │
         └──> ReservationManager.startTrip(reservationId)
              │
              ├──> ReservationRepository.findById(reservationId)
              └──> Reservation.setReservationStatus(IN_USE)
```

### Vehicle Return and Payment Flow
```
User Submits Vehicle
    │
    └──> Store.submitVehicle(reservationId)
         │
         └──> ReservationManager.submitVehicle(reservationId)
              │
              ├──> ReservationRepository.findById(reservationId)
              ├──> Reservation.setReservationStatus(COMPLETED)
              └──> VehicleInventoryManager.release(vehicleId, reservationId)
                   │
                   └──> lockForVehicle(vehicleId).lock()
                        ├──> Remove reservationId from vehicleBookingIds
                        ├──> If no more bookings:
                        │    └──> Vehicle.setStatus(AVAILABLE)
                        └──> lock.unlock()

Generate Bill
    │
    └──> Store.generateBill(reservationId, billingStrategy)
         │
         ├──> ReservationManager.findByID(reservationId)
         ├──> BillManager.setBillingStrategy(strategy)
         └──> BillManager.generateBill(reservation)
              │
              └──> BillingStrategy.generateBill(reservation)
                   │
                   └──> DailyBillingStrategy.generateBill()
                        │
                        ├──> Calculate days: ChronoUnit.DAYS.between(...) + 1
                        ├──> VehicleInventoryManager.getVehicle(vehicleId)
                        ├──> Vehicle.getDailyRentalCost()
                        ├──> total = days × rate
                        └──> new Bill(billId, reservationId, total)

Process Payment
    │
    └──> Store.makePayment(bill, paymentStrategy, amount)
         │
         ├──> PaymentManager.setPaymentStrategy(strategy)
         ├──> PaymentManager.makePayment(bill, amount)
         │    │
         │    └──> PaymentStrategy.processPayment(bill, amount)
         │         │
         │         └──> UPIPaymentStrategy.processPayment()
         │              │
         │              ├──> Generate paymentId (AtomicInteger)
         │              ├──> new Payment(id, billId, amount, UPI, new Date())
         │              ├──> Bill.setBillPaid(true)
         │              └──> Return Payment
         │
         ├──> Validate bill.isBillPaid()
         └──> ReservationManager.remove(reservationId)
              └──> ReservationRepository.remove(reservationId)
```

---

## Thread Safety

### Concurrent Operations Supported
1. **Multiple users booking same vehicle simultaneously**
   - Per-vehicle ReentrantLock ensures atomic reserve operations
   - Double-booking prevented

2. **Separate vehicles can be booked in parallel**
   - Different locks for different vehicles
   - No contention across vehicles

3. **Thread-safe ID generation**
   - AtomicInteger for reservation, bill, and payment IDs
   - Prevents ID conflicts

4. **Thread-safe data structures**
   - ConcurrentHashMap for vehicles, reservations, bills, payments
   - Thread-safe read/write operations

5. **Atomic check-and-reserve**
   - Lock acquisition before availability check
   - Prevents race condition between check and reserve

---

## Extensibility Points

### Easy to Add:
1. **New Vehicle Types**
   - Add enum value in VehicleType
   - System handles automatically

2. **New Billing Strategies**
   - Implement BillingStrategy interface
   - Examples: HourlyBillingStrategy, WeeklyBillingStrategy, MembershipDiscountStrategy

3. **New Payment Methods**
   - Implement PaymentStrategy interface
   - Examples: CashPaymentStrategy, CardPaymentStrategy, WalletPaymentStrategy

4. **New Reservation Types**
   - Add to ReservationType enum
   - Create corresponding billing strategy

5. **Dynamic Pricing**
   - Create strategy that considers:
     - Vehicle type
     - Season/peak hours
     - Rental duration
     - Customer loyalty

6. **Vehicle Features**
   - Extend Vehicle class with properties:
     - Company, model, km driven, mileage, CC, seats
     - GPS tracking, insurance details

---

## Example Usage

```java
// Setup
VehicleRentalSystem rentalSystem = new VehicleRentalSystem();
Store store = new Store(1001, location);
rentalSystem.addStore(store);

// Add vehicles
Vehicle car = new Vehicle(1, "DL1234", VehicleType.FOUR_WHEELER);
car.setDailyRentalCost(1100);
store.getInventory().addVehicle(car);

// User searches
List<Vehicle> available = store.getVehicles(
    VehicleType.FOUR_WHEELER, 
    fromDate, 
    toDate
);

// Create reservation
Reservation reservation = store.createReservation(
    vehicleId, 
    user, 
    fromDate, 
    toDate, 
    ReservationType.DAILY
);

// Start trip
store.startTrip(reservation.getReservationId());

// Return vehicle
store.submitVehicle(reservation.getReservationId());

// Generate bill and pay
Bill bill = store.generateBill(
    reservation.getReservationId(), 
    new DailyBillingStrategy(store.getInventory())
);

Payment payment = store.makePayment(
    bill, 
    new UPIPaymentStrategy(), 
    bill.getTotalBillAmount()
);
```

---

## Key Features

1. **Multi-Store Support**: Manage multiple rental locations
2. **Multiple Vehicle Types**: Support for different vehicle categories
3. **Thread-Safe Reservations**: Prevents double-booking with per-vehicle locks
4. **Date Overlap Detection**: Intelligent availability checking
5. **Flexible Billing**: Strategy-based billing calculations
6. **Multiple Payment Methods**: UPI, Cash, Online payments
7. **Reservation Lifecycle**: SCHEDULED → IN_USE → COMPLETED
8. **Atomic Operations**: Reserve and release are atomic
9. **Type-Safe**: Strong typing prevents errors
10. **Testable**: Dependency injection enables easy testing

---

## Future Enhancements

1. **User Authentication**: Login system, user profiles
2. **Vehicle Reviews**: Ratings and reviews after rental
3. **Surge Pricing**: Dynamic pricing based on demand
4. **Insurance Options**: Optional insurance packages
5. **Loyalty Programs**: Points, memberships, discounts
6. **Multi-City Search**: Search across all stores
7. **Vehicle Maintenance Scheduling**: Automatic maintenance tracking
8. **Damage Assessment**: Post-rental inspection and damage charges
9. **Late Return Penalties**: Additional charges for late returns
10. **Mobile App Integration**: Push notifications, digital keys
11. **Analytics Dashboard**: Revenue, occupancy, popular vehicles
12. **Partial Payments**: Support for deposits and installments
13. **Cancellation Policies**: Refund rules based on cancellation time
14. **Vehicle Recommendations**: AI-based suggestions
15. **Multi-language Support**: Internationalization
