# Car Rental System - Complete UML Relationship Diagram

## Full System Architecture with All Dependencies

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    CAR RENTAL MANAGEMENT SYSTEM                             ┃
┃                         (24 Classes, Multi-Layer)                           ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 1: ENUMS (No Dependencies)                                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│  VehicleType    │  │  VehicleStatus   │  │ ReservationType  │
├─────────────────┤  ├──────────────────┤  ├──────────────────┤
│ + TWO_WHEELER   │  │ + AVAILABLE      │  │ + HOURLY         │
│ + FOUR_WHEELER  │  │ + BOOKED         │  │ + DAILY          │
└─────────────────┘  │ + MAINTENANCE    │  └──────────────────┘
                     └──────────────────┘

┌──────────────────┐  ┌──────────────────┐
│ReservationStatus │  │  PaymentMode     │
├──────────────────┤  ├──────────────────┤
│ + SCHEDULED      │  │ + CASH           │
│ + IN_USE         │  │ + ONLINE         │
│ + COMPLETED      │  │ + UPI            │
│ + CANCELLED      │  └──────────────────┘
└──────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 2: BASIC ENTITIES & VALUE OBJECTS (Depend on Enums)                  │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌────────────────────────────┐        ┌────────────────────────────┐
    │       Location             │        │         User               │
    ├────────────────────────────┤        ├────────────────────────────┤
    │ Dependencies: NONE         │        │ Dependencies: NONE         │
    ├────────────────────────────┤        ├────────────────────────────┤
    │ + buildingNo: int          │        │ + userId: int              │
    │ + area: String             │        │ + userName: String         │
    │ + city: String             │        │ + drivingLicenseNo: String │
    │ + state: String            │        ├────────────────────────────┤
    │ + country: String          │        │ + getUserId()              │
    │ + pincode: int             │        │ + getUserName()            │
    └────────────────────────────┘        │ + getDrivingLicenseNo()    │
                │                            └────────────────────────────┘
                │ used by                                 │
                │                                         │ used by
                ▼                                         ▼

    ┌────────────────────────────┐        ┌────────────────────────────┐
    │      DateInterval          │        │        Vehicle             │
    ├────────────────────────────┤        ├────────────────────────────┤
    │ Dependencies: NONE         │        │ Dependencies:              │
    ├────────────────────────────┤        │ - VehicleType (HAS-A)      │
    │ - from: LocalDate          │        │ - VehicleStatus (HAS-A)    │
    │ - to: LocalDate            │        ├────────────────────────────┤
    ├────────────────────────────┤        │ - vehicleID: int           │
    │ + overlaps(other): boolean │        │ - vehicleNumber: String    │
    └────────────────────────────┘        │ - vehicleType: VehicleType │◄──┐
                │                          │ - dailyRentalCost: double  │   │
                │ used by                  │ - vehicleStatus: Status    │   │
                │                          ├────────────────────────────┤   │
                ▼                          │ + getVehicleID()           │   │
                                           │ + getVehicleType()         │   │
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 3: RESERVATION ENTITY (Depends on Enums & Dates)                     │
└─────────────────────────────────────────────────────────────────────────────┘

                    ┌────────────────────────────────────────┐
                    │         Reservation                    │
                    ├────────────────────────────────────────┤
                    │ Dependencies:                          │
                    │ - ReservationType (HAS-A)              │
                    │ - ReservationStatus (HAS-A)            │
                    ├────────────────────────────────────────┤
                    │ - reservationId: int                   │
                    │ - vehicleId: int                       │
                    │ - userId: int                          │
                    │ - dateBookedFrom: LocalDate            │
                    │ - dateBookedTo: LocalDate              │
                    │ - reservationType: ReservationType     │
                    │ - reservationStatus: ReservationStatus │
                    ├────────────────────────────────────────┤
                    │ + getReservationId(): int              │
                    │ + getVehicleId(): int                  │
                    │ + getUserId(): int                     │
                    │ + getDateBookedFrom(): LocalDate       │
                    │ + getDateBookedTo(): LocalDate         │
                    │ + getReservationType(): Type           │
                    │ + getReservationStatus(): Status       │
                    │ + setReservationStatus(s): void        │
                    └────────────────────────────────────────┘
                                    │
                                    │ stored in
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 4: REPOSITORIES (Depend on Entities)                                 │
└─────────────────────────────────────────────────────────────────────────────┘

                    ┌────────────────────────────────────────┐
                    │    ReservationRepository               │
                    ├────────────────────────────────────────┤
                    │ Dependencies:                          │
                    │ - Reservation (stores)                 │
                    ├────────────────────────────────────────┤
                    │ - reservations: Map<Int,Reservation>   │──────► HAS-A Map<Reservation>
                    ├────────────────────────────────────────┤
                    │ + save(reservation): void              │
                    │ + findById(id): Optional<Reservation>  │
                    │ + remove(id): void                     │
                    │ + getAll(): Map<Int,Reservation>       │
                    └────────────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 5: INVENTORY MANAGER (Depends on Vehicle, Repository, DateInterval)  │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────────┐
                │    VehicleInventoryManager                       │
                ├──────────────────────────────────────────────────┤
                │ Dependencies:                                    │
                │ - Vehicle (stores)                               │
                │ - ReservationRepository (references)             │
                │ - DateInterval (uses for overlap)                │
                │ - VehicleType (filters)                          │
                │ - VehicleStatus (checks)                         │
                │ - ReentrantLock (thread safety)                  │
                ├──────────────────────────────────────────────────┤
                │ - vehicles: ConcurrentMap<Int,Vehicle>           │──────► HAS-A Map<Vehicle>
                │ - vehicleBookingIds: Map<Int,List<Int>>          │
                │ - vehicleLocks: Map<Int,ReentrantLock>           │
                │ - reservationRepository: ReservationRepo         │──────► HAS-A ReservationRepository
                ├──────────────────────────────────────────────────┤
                │ + addVehicle(vehicle): void                      │
                │ + getVehicle(id): Optional<Vehicle>              │
                │ + isAvailable(id, from, to): boolean             │
                │ + reserve(id, resId, from, to): boolean          │
                │ + release(id, resId): void                       │
                │ + getAvailableVehicles(type, from, to): List     │
                │ + setReservationRepository(repo): void           │
                └──────────────────────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 6: RESERVATION MANAGER (Depends on Inventory & Repository)           │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────────┐
                │        ReservationManager                        │
                ├──────────────────────────────────────────────────┤
                │ Dependencies:                                    │
                │ - VehicleInventoryManager (HAS-A)                │
                │ - ReservationRepository (HAS-A)                  │
                │ - Reservation (creates)                          │
                │ - User (parameter)                               │
                │ - ReservationType (parameter)                    │
                │ - ReservationStatus (updates)                    │
                ├──────────────────────────────────────────────────┤
                │ - inventory: VehicleInventoryManager             │──────► HAS-A VehicleInventoryManager
                │ - reservationRepository: ReservationRepo         │──────► HAS-A ReservationRepository
                │ - reservationIdGenerator: AtomicInteger          │
                ├──────────────────────────────────────────────────┤
                │ + createReservation(...): Reservation            │
                │ + cancelReservation(id): void                    │
                │ + startTrip(id): void                            │
                │ + submitVehicle(id): void                        │
                │ + findByID(id): Optional<Reservation>            │
                │ + remove(id): void                               │
                └──────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 7: STRATEGY INTERFACES (Define Contracts)                            │
└─────────────────────────────────────────────────────────────────────────────┘

        ┌────────────────────────────┐         ┌─────────────────────────────┐
        │ <<interface>>              │         │ <<interface>>               │
        │   BillingStrategy          │         │   PaymentStrategy           │
        ├────────────────────────────┤         ├─────────────────────────────┤
        │ + generateBill(res): Bill  │         │ + processPayment(b,a): Pay  │
        └────────────────────────────┘         └─────────────────────────────┘
                  △                                       △
                  │ IMPLEMENTS                            │ IMPLEMENTS
                  │                                       │
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 8: STRATEGY IMPLEMENTATIONS & ENTITIES                               │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌──────────────────────────────────┐      ┌───────────────────────────────┐
    │  DailyBillingStrategy            │      │   UPIPaymentStrategy          │
    ├──────────────────────────────────┤      ├───────────────────────────────┤
    │ Dependencies:                    │      │ Dependencies:                 │
    │ - BillingStrategy (IMPLEMENTS)   │      │ - PaymentStrategy (IMPLEMENTS)│
    │ - VehicleInventoryManager        │      │ - Bill (parameter)            │
    │ - Reservation (parameter)        │      │ - PaymentMode (uses)          │
    │ - Bill (creates)                 │      │ - Payment (creates)           │
    ├──────────────────────────────────┤      ├───────────────────────────────┤
    │ - inventory: VehicleInventoryMgr │      │ - paymentIdGenerator: AtomInt │
    │ - billIdGenerator: AtomicInteger │      ├───────────────────────────────┤
    ├──────────────────────────────────┤      │ + processPayment(b,a): Payment│
    │ + generateBill(res): Bill        │      └───────────────────────────────┘
    └──────────────────────────────────┘                  │
                │                                         │ creates
                │ creates                                 ▼
                ▼
    ┌──────────────────────────────────┐      ┌───────────────────────────────┐
    │           Bill                   │      │         Payment               │
    ├──────────────────────────────────┤      ├───────────────────────────────┤
    │ Dependencies: NONE               │      │ Dependencies:                 │
    ├──────────────────────────────────┤      │ - PaymentMode (HAS-A)         │
    │ - billId: int                    │      ├───────────────────────────────┤
    │ - reservationId: int             │◄─────┤ - paymentId: int              │
    │ - totalBillAmount: double        │      │ - billId: int                 │
    │ - billPaid: boolean              │      │ - amountPaid: double          │
    ├──────────────────────────────────┤      │ - paymentMode: PaymentMode    │
    │ + getBillId(): int               │      │ - paymentDate: Date           │
    │ + getReservationId(): int        │      ├───────────────────────────────┤
    │ + getTotalBillAmount(): double   │      │ + getPaymentId(): int         │
    │ + isBillPaid(): boolean          │      │ + getBillId(): int            │
    │ + setBillPaid(paid): void        │      │ + getAmountPaid(): double     │
    └──────────────────────────────────┘      │ + getPaymentMode(): Mode      │
                │                              │ + getPaymentDate(): Date      │
                │ managed by                   └───────────────────────────────┘
                │                                          │
                ▼                                          │ managed by
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 9: MANAGERS (Depend on Strategies & Entities)                        │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌────────────────────────────────────┐      ┌─────────────────────────────────┐
    │       BillManager                  │      │      PaymentManager             │
    ├────────────────────────────────────┤      ├─────────────────────────────────┤
    │ Dependencies:                      │      │ Dependencies:                   │
    │ - BillingStrategy (HAS-A)          │      │ - PaymentStrategy (HAS-A)       │
    │ - Bill (stores)                    │      │ - Payment (stores)              │
    │ - Reservation (parameter)          │      │ - Bill (parameter)              │
    ├────────────────────────────────────┤      ├─────────────────────────────────┤
    │ - billingStrategy: BillingStrategy │      │ - paymentStrategy: PayStrategy  │
    │ - bills: Map<Int,Bill>             │      │ - payments: Map<Int,Payment>    │
    ├────────────────────────────────────┤      ├─────────────────────────────────┤
    │ + generateBill(res): Bill          │      │ + makePayment(b,amt): Payment   │
    │ + getBill(billId): Optional<Bill>  │      │ + getPaymentsForBill(id): List  │
    │ + setBillingStrategy(s): void      │      │ + getPayment(id): Optional<Pay> │
    └────────────────────────────────────┘      │ + setPaymentStrategy(s): void   │
                                                 └─────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 10: STORE AGGREGATE ROOT (Depends on All Managers)                   │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────────┐
                │              Store                               │
                ├──────────────────────────────────────────────────┤
                │ Dependencies:                                    │
                │ - Location (HAS-A)                               │
                │ - VehicleInventoryManager (HAS-A)                │
                │ - ReservationManager (HAS-A)                     │
                │ - BillManager (HAS-A)                            │
                │ - PaymentManager (HAS-A)                         │
                │ - Vehicle (via inventory)                        │
                │ - Reservation (creates)                          │
                │ - Bill (creates)                                 │
                │ - Payment (creates)                              │
                │ - User (parameter)                               │
                ├──────────────────────────────────────────────────┤
                │ - storeId: int                                   │
                │ - storeLocation: Location                        │──────► HAS-A Location
                │ - inventory: VehicleInventoryManager             │──────► HAS-A VehicleInventoryManager
                │ - reservationManager: ReservationManager         │──────► HAS-A ReservationManager
                │ - billManager: BillManager                       │──────► HAS-A BillManager
                │ - paymentManager: PaymentManager                 │──────► HAS-A PaymentManager
                ├──────────────────────────────────────────────────┤
                │ + Store(storeId, location)                       │
                │ + getVehicles(type, from, to): List              │
                │ + createReservation(...): Reservation            │
                │ + cancelReservation(id): void                    │
                │ + startTrip(id): void                            │
                │ + submitVehicle(id): void                        │
                │ + generateBill(id, strategy): Bill               │
                │ + makePayment(bill, strategy, amt): Payment      │
                │ + getInventory(): VehicleInventoryManager        │
                │ + getStoreId(): int                              │
                └──────────────────────────────────────────────────┘
                                    │
                                    │ managed by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 11: SYSTEM FACADE (Depends on Store & User)                          │
└─────────────────────────────────────────────────────────────────────────────┘

                ┌──────────────────────────────────────────────────┐
                │         VehicleRentalSystem                      │
                ├──────────────────────────────────────────────────┤
                │ Dependencies:                                    │
                │ - Store (stores collection)                      │
                │ - User (stores collection)                       │
                ├──────────────────────────────────────────────────┤
                │ - storeList: List<Store>                         │──────► HAS-A List<Store>
                │ - userList: List<User>                           │──────► HAS-A List<User>
                ├──────────────────────────────────────────────────┤
                │ + getStore(storeId): Store                       │
                │ + getUser(userId): User                          │
                │ + addStore(store): void                          │
                │ + addUser(user): void                            │
                │ + removeStore(storeId): void                     │
                │ + removeUser(userId): void                       │
                └──────────────────────────────────────────────────┘
                                    │
                                    │ used by
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  LAYER 12: CLIENT (Application Entry)                                       │
└─────────────────────────────────────────────────────────────────────────────┘

                        ┌────────────────────────────────┐
                        │      Demo (Main)               │
                        ├────────────────────────────────┤
                        │ Dependencies: ALL LAYERS       │
                        │ - Creates all objects          │
                        │ - Wires dependencies           │
                        │ - Simulates full workflow      │
                        ├────────────────────────────────┤
                        │ + main(args): void             │
                        └────────────────────────────────┘
```

---

## Complete Dependency Graph (Bottom-Up)

```
LAYER 1: NO DEPENDENCIES
═════════════════════════
┌──────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────────┐  ┌─────────────┐
│ VehicleType  │  │ VehicleStatus   │  │ ReservationType │  │ReservationStatus │  │ PaymentMode │
└──────────────┘  └─────────────────┘  └─────────────────┘  └──────────────────┘  └─────────────┘


LAYER 2: BASIC ENTITIES (DEPEND ON LAYER 1 OR NONE)
════════════════════════════════════════════════════
┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  Location    │  │    User      │  │ DateInterval │  │   Vehicle    │
│ (independent)│  │(independent) │  │(independent) │  │      ↓       │
└──────────────┘  └──────────────┘  └──────────────┘  │ VehicleType  │
                                                       │ VehicleStatus│
                                                       └──────────────┘


LAYER 3: RESERVATION ENTITY (DEPENDS ON LAYER 1)
═════════════════════════════════════════════════
┌──────────────────────┐
│    Reservation       │
│         ↓            │
│ ReservationType      │
│ ReservationStatus    │
└──────────────────────┘


LAYER 4: REPOSITORIES (DEPEND ON LAYER 3)
══════════════════════════════════════════
┌─────────────────────────────┐
│  ReservationRepository      │
│           ↓                 │
│  Map<Int, Reservation>      │
└─────────────────────────────┘


LAYER 5: INVENTORY MANAGER (DEPENDS ON LAYERS 2, 4)
════════════════════════════════════════════════════
┌────────────────────────────────┐
│  VehicleInventoryManager       │
│             ↓                  │
│  - Vehicle                     │
│  - ReservationRepository       │
│  - DateInterval                │
│  - ReentrantLock               │
└────────────────────────────────┘


LAYER 6: RESERVATION MANAGER (DEPENDS ON LAYERS 2, 3, 4, 5)
════════════════════════════════════════════════════════════
┌─────────────────────────────────┐
│    ReservationManager           │
│            ↓                    │
│  - VehicleInventoryManager      │
│  - ReservationRepository        │
│  - User                         │
│  - Reservation                  │
└─────────────────────────────────┘


LAYER 7: STRATEGY INTERFACES (DEPEND ON RESERVATION, BILL)
═══════════════════════════════════════════════════════════
┌───────────────────┐     ┌──────────────────┐
│ BillingStrategy   │     │ PaymentStrategy  │
│       ↓           │     │       ↓          │
│   Reservation     │     │     Bill         │
│   Bill (returns)  │     │   Payment        │
└───────────────────┘     └──────────────────┘


LAYER 8: STRATEGY IMPLEMENTATIONS & BILL/PAYMENT ENTITIES
══════════════════════════════════════════════════════════
┌──────────────────────────┐  ┌──────────────────┐  ┌─────────────────┐
│ DailyBillingStrategy     │  │      Bill        │  │    Payment      │
│          ↓               │  │ (independent)    │  │       ↓         │
│ - BillingStrategy        │  └──────────────────┘  │  PaymentMode    │
│ - VehicleInventoryMgr    │                        └─────────────────┘
│ - Reservation            │  ┌──────────────────┐
└──────────────────────────┘  │UPIPaymentStrategy│
                              │       ↓          │
                              │ PaymentStrategy  │
                              │ Payment          │
                              │ Bill             │
                              └──────────────────┘


LAYER 9: MANAGERS (DEPEND ON STRATEGIES & ENTITIES)
════════════════════════════════════════════════════
┌─────────────────────────┐     ┌──────────────────────────┐
│    BillManager          │     │    PaymentManager        │
│         ↓               │     │          ↓               │
│  - BillingStrategy      │     │  - PaymentStrategy       │
│  - Bill                 │     │  - Payment               │
│  - Reservation          │     │  - Bill                  │
└─────────────────────────┘     └──────────────────────────┘


LAYER 10: STORE AGGREGATE ROOT (DEPENDS ON ALL MANAGERS)
═════════════════════════════════════════════════════════
┌────────────────────────────────┐
│          Store                 │
│            ↓                   │
│  - Location                    │
│  - VehicleInventoryManager     │
│  - ReservationManager          │
│  - BillManager                 │
│  - PaymentManager              │
│  - User (parameter)            │
└────────────────────────────────┘


LAYER 11: SYSTEM FACADE (DEPENDS ON STORE & USER)
══════════════════════════════════════════════════
┌──────────────────────────────┐
│   VehicleRentalSystem        │
│          ↓                   │
│  - Store                     │
│  - User                      │
└──────────────────────────────┘


LAYER 12: CLIENT (DEPENDS ON ALL)
══════════════════════════════════
┌────────────────────────────┐
│       Demo                 │
│         ↓                  │
│  ALL ABOVE CLASSES         │
└────────────────────────────┘
```

---

## Complete Object Creation Flow (From Demo.main)

```
main() starts
    │
    ├─→ new VehicleRentalSystem()
    │       │
    │       └─→ storeList = new ArrayList<>()
    │       └─→ userList = new ArrayList<>()
    │
    ├─→ new Location(45, "Area1", "City1", "State1", "India", 12345)
    │       [LAYER 2: Value Object]
    │
    ├─→ new Store(1001, store1Location)
    │       [LAYER 10: Aggregate Root]
    │       │
    │       └─→ Store Constructor:
    │           │
    │           ├─→ new VehicleInventoryManager()
    │           │       [LAYER 5: Creates empty ConcurrentHashMaps]
    │           │
    │           ├─→ new DailyBillingStrategy(inventory)
    │           │       [LAYER 8: Strategy Implementation]
    │           │
    │           ├─→ new BillManager(dailyBillingStrategy)
    │           │       [LAYER 9: Manager with injected strategy]
    │           │
    │           ├─→ new UPIPaymentStrategy()
    │           │       [LAYER 8: Strategy Implementation]
    │           │
    │           ├─→ new PaymentManager(upiPaymentStrategy)
    │           │       [LAYER 9: Manager with injected strategy]
    │           │
    │           └─→ new ReservationManager(inventory)
    │                   [LAYER 6: Manager]
    │                   │
    │                   └─→ new ReservationRepository()
    │                   │       [LAYER 4: Repository]
    │                   │
    │                   └─→ inventory.setReservationRepository(repo)
    │                           [Bidirectional link for date checking]
    │
    ├─→ rentalSystem.addStore(store1)
    │
    ├─→ new User(801, "SJ", "DL2022GDG556690")
    │       [LAYER 2: Entity]
    │
    ├─→ rentalSystem.addUser(user1)
    │
    ├─→ new Vehicle(1, "DL1234", VehicleType.FOUR_WHEELER)
    │       [LAYER 2: Entity]
    │       │
    │       └─→ vehicleStatus = VehicleStatus.AVAILABLE
    │
    ├─→ vehicle.setDailyRentalCost(1100)
    │
    ├─→ store1.getInventory().addVehicle(v1)
    │       [Adds to ConcurrentHashMap]
    │
    ├─→ selectedStore = rentalSystem.getStore(1001)
    │
    ├─→ selectedStore.getVehicles(VehicleType.FOUR_WHEELER, fromDate, toDate)
    │       [SEARCH FLOW - see detailed flow below]
    │
    ├─→ selectedStore.createReservation(vehicleId, user, from, to, type)
    │       [RESERVATION FLOW - see detailed flow below]
    │       │
    │       └─→ Returns Reservation with SCHEDULED status
    │
    ├─→ selectedStore.startTrip(reservationId)
    │       │
    │       └─→ Updates status to IN_USE
    │
    ├─→ selectedStore.submitVehicle(reservationId)
    │       │
    │       ├─→ Updates status to COMPLETED
    │       └─→ Releases vehicle (status → AVAILABLE if no more bookings)
    │
    ├─→ selectedStore.generateBill(reservationId, new DailyBillingStrategy(...))
    │       [BILLING FLOW - see detailed flow below]
    │       │
    │       └─→ Returns Bill
    │
    └─→ selectedStore.makePayment(bill, new UPIPaymentStrategy(), amount)
            [PAYMENT FLOW - see detailed flow below]
            │
            └─→ Returns Payment receipt
```

---

## Vehicle Search Flow (Complete Call Chain)

```
1. Client Code
   store.getVehicles(VehicleType.FOUR_WHEELER, fromDate, toDate)
       │
       ▼
2. Store
   getVehicles(type, from, to)
       │
       └─→ inventory.getAvailableVehicles(type, from, to)
           │
           ▼
3. VehicleInventoryManager
   getAvailableVehicles(type, from, to)
       │
       └─→ vehicles.values().stream()
           │
           ├─→ filter(v -> v.getVehicleType() == type)
           │       │
           │       └─→ Vehicle.getVehicleType()
           │           └─→ return vehicleType
           │
           └─→ filter(v -> isAvailable(v.getVehicleID(), from, to))
               │
               ▼
4. VehicleInventoryManager
   isAvailable(vehicleId, from, to)
       │
       ├─→ vehicle = vehicles.get(vehicleId)
       │
       ├─→ if (vehicle.getVehicleStatus() == VehicleStatus.MAINTENANCE)
       │       return false
       │
       ├─→ requested = new DateInterval(from, to)
       │       [LAYER 2: Value Object]
       │
       ├─→ reservationIDs = vehicleBookingIds.get(vehicleId)
       │
       └─→ for each reservationID:
           │
           └─→ reservation = reservationRepository.findById(reservationID)
               │
               └─→ bookedInterval = new DateInterval(
                       reservation.getDateBookedFrom(),
                       reservation.getDateBookedTo()
                   )
                   │
                   └─→ if bookedInterval.overlaps(requested):
                       │
                       └─→ DateInterval.overlaps(other)
                           │
                           └─→ return !(this.to < other.from || this.from > other.to)
                               │
                               └─→ [If overlaps] return false

   [Returns List<Vehicle> that passed all filters]
```

---

## Reservation Creation Flow (Complete Call Chain)

```
1. Client Code
   store.createReservation(vehicleId, user, from, to, ReservationType.DAILY)
       │
       ▼
2. Store
   createReservation(vehicleId, user, from, to, type)
       │
       └─→ reservationManager.createReservation(vehicleId, user, from, to, type)
           │
           ▼
3. ReservationManager
   createReservation(...)
       │
       ├─→ reservationId = reservationIdGenerator.getAndIncrement()
       │       [AtomicInteger: Thread-safe ID generation]
       │       [Returns: 20000, 20001, 20002...]
       │
       ├─→ reserved = inventory.reserve(vehicleId, reservationId, from, to)
       │       │
       │       ▼
4. VehicleInventoryManager
   reserve(vehicleId, reservationId, from, to)
       │
       ├─→ lock = lockForVehicle(vehicleId)
       │       │
       │       └─→ vehicleLocks.putIfAbsent(vehicleId, new ReentrantLock())
       │           └─→ return vehicleLocks.get(vehicleId)
       │
       ├─→ lock.lock()  [CRITICAL SECTION BEGINS]
       │
       ├─→ if (!isAvailable(vehicleId, from, to))
       │       return false
       │       [See isAvailable flow above]
       │
       ├─→ vehicleBookingIds.putIfAbsent(vehicleId, new ArrayList<>())
       │
       ├─→ vehicleBookingIds.get(vehicleId).add(reservationId)
       │       [Links vehicle to reservation]
       │
       ├─→ vehicles.get(vehicleId).setStatus(VehicleStatus.BOOKED)
       │       │
       │       └─→ Vehicle.setStatus(status)
       │           └─→ this.vehicleStatus = status
       │
       ├─→ lock.unlock()  [CRITICAL SECTION ENDS]
       │
       └─→ return true
       
   [Back to ReservationManager]
   
5. ReservationManager (continued)
   createReservation(...) continued
       │
       ├─→ if (!reserved) throw RuntimeException
       │
       ├─→ reservation = new Reservation(
       │       reservationId,
       │       vehicleId,
       │       user.getUserId(),
       │       from,
       │       to,
       │       type
       │   )
       │       │
       │       └─→ Reservation Constructor:
       │           │
       │           ├─→ Sets all final fields
       │           └─→ this.reservationStatus = ReservationStatus.SCHEDULED
       │
       ├─→ reservationRepository.save(reservation)
       │       │
       │       └─→ reservations.put(reservation.getReservationId(), reservation)
       │
       └─→ return reservation
```

---

## Billing Flow (Complete Call Chain)

```
1. Client Code
   store.generateBill(reservationId, new DailyBillingStrategy(inventory))
       │
       ▼
2. Store
   generateBill(reservationId, billingStrategy)
       │
       ├─→ r = reservationManager.findByID(reservationId)
       │       │
       │       └─→ reservationRepository.findById(reservationId)
       │           └─→ return Optional.ofNullable(reservations.get(reservationId))
       │
       ├─→ if (!r.isPresent()) throw RuntimeException
       │
       ├─→ billManager.setBillingStrategy(billingStrategy)
       │       │
       │       └─→ this.billingStrategy = billingStrategy
       │
       └─→ billManager.generateBill(r.get())
           │
           ▼
3. BillManager
   generateBill(reservation)
       │
       └─→ bill = billingStrategy.generateBill(reservation)
           │
           ▼
4. DailyBillingStrategy
   generateBill(reservation)
       │
       ├─→ days = ChronoUnit.DAYS.between(
       │       reservation.getDateBookedFrom(),
       │       reservation.getDateBookedTo()
       │   ) + 1
       │       [Inclusive calculation]
       │       [Example: Dec 5 to Dec 7 = 3 days]
       │
       ├─→ vehicle = vehicleInventoryManager.getVehicle(
       │       reservation.getVehicleId()
       │   ).get()
       │       │
       │       └─→ return Optional.ofNullable(vehicles.get(vehicleId))
       │
       ├─→ rate = vehicle.getDailyRentalCost()
       │       │
       │       └─→ return dailyRentalCost
       │
       ├─→ total = days × rate
       │       [Example: 3 days × 1100 = 3300]
       │
       └─→ return new Bill(
               billIdGenerator.getAndIncrement(),
               reservation.getReservationId(),
               total
           )
               │
               └─→ Bill Constructor:
                   │
                   ├─→ Sets billId, reservationId, totalBillAmount
                   └─→ this.billPaid = false

   [Back to BillManager]

5. BillManager (continued)
   generateBill() continued
       │
       ├─→ bills.put(bill.getBillId(), bill)
       │       [Stores in ConcurrentHashMap]
       │
       └─→ return bill
```

---

## Payment Flow (Complete Call Chain)

```
1. Client Code
   store.makePayment(bill, new UPIPaymentStrategy(), amount)
       │
       ▼
2. Store
   makePayment(bill, paymentStrategy, paymentAmount)
       │
       ├─→ paymentManager.setPaymentStrategy(paymentStrategy)
       │       │
       │       └─→ this.paymentStrategy = paymentStrategy
       │
       ├─→ payment = paymentManager.makePayment(bill, paymentAmount)
       │       │
       │       ▼
3. PaymentManager
   makePayment(bill, paymentAmount)
       │
       └─→ payment = paymentStrategy.processPayment(bill, paymentAmount)
           │
           ▼
4. UPIPaymentStrategy
   processPayment(bill, paymentAmount)
       │
       ├─→ payment = new Payment(
       │       paymentIdGenerator.getAndIncrement(),
       │       bill.getBillId(),
       │       paymentAmount,
       │       PaymentMode.UPI,
       │       new Date()
       │   )
       │       │
       │       └─→ Payment Constructor:
       │           └─→ Sets all final fields (immutable receipt)
       │
       ├─→ bill.setBillPaid(true)
       │       │
       │       └─→ Bill.setBillPaid(true)
       │           └─→ this.billPaid = true
       │
       └─→ return payment

   [Back to PaymentManager]

5. PaymentManager (continued)
   makePayment() continued
       │
       ├─→ payments.put(payment.getPaymentId(), payment)
       │       [Stores in ConcurrentHashMap]
       │
       └─→ return payment

   [Back to Store]

6. Store (continued)
   makePayment() continued
       │
       ├─→ if (!bill.isBillPaid())
       │       throw RuntimeException("Payment failed")
       │
       └─→ reservationManager.remove(bill.getReservationId())
           │
           └─→ reservationRepository.remove(reservationId)
               │
               └─→ reservations.remove(reservationId)
                   [Removes from ConcurrentHashMap]
```

---

## Vehicle Release Flow (Complete Call Chain)

```
1. Store.submitVehicle(reservationId)
   OR
   Store.cancelReservation(reservationId)
       │
       ▼
2. ReservationManager
   submitVehicle(reservationId) OR cancelReservation(reservationId)
       │
       ├─→ reservation = reservationRepository.findById(reservationId)
       │
       ├─→ reservation.setReservationStatus(COMPLETED or CANCELLED)
       │
       └─→ inventory.release(
               reservation.getVehicleId(),
               reservation.getReservationId()
           )
           │
           ▼
3. VehicleInventoryManager
   release(vehicleId, reservationId)
       │
       ├─→ lock = lockForVehicle(vehicleId)
       │
       ├─→ lock.lock()  [CRITICAL SECTION BEGINS]
       │
       ├─→ ids = vehicleBookingIds.get(vehicleId)
       │
       ├─→ ids.remove(Integer.valueOf(reservationId))
       │       [Removes this specific reservation]
       │
       ├─→ stillBooked = vehicleBookingIds.get(vehicleId)
       │
       ├─→ if (stillBooked == null || stillBooked.isEmpty())
       │       vehicles.get(vehicleId).setStatus(VehicleStatus.AVAILABLE)
       │       [Only set AVAILABLE if no more reservations]
       │
       └─→ lock.unlock()  [CRITICAL SECTION ENDS]
```

---

## Relationship Matrix (All 24 Classes)

| Class                        | Depends On                                    | Relationship Type           |
|------------------------------|-----------------------------------------------|-----------------------------|
| VehicleType (enum)           | None                                          | -                           |
| VehicleStatus (enum)         | None                                          | -                           |
| ReservationType (enum)       | None                                          | -                           |
| ReservationStatus (enum)     | None                                          | -                           |
| PaymentMode (enum)           | None                                          | -                           |
| Location                     | None                                          | -                           |
| User                         | None                                          | -                           |
| DateInterval                 | None                                          | -                           |
| Vehicle                      | VehicleType, VehicleStatus                    | HAS-A, HAS-A                |
| Reservation                  | ReservationType, ReservationStatus            | HAS-A, HAS-A                |
| ReservationRepository        | Reservation                                   | HAS-A (collection)          |
| BillingStrategy              | Reservation, Bill                             | Uses (interface)            |
| DailyBillingStrategy         | BillingStrategy, VehicleInventoryManager,     | IMPLEMENTS, HAS-A,          |
|                              | Reservation, Bill                             | Uses, Creates               |
| Bill                         | None                                          | -                           |
| BillManager                  | BillingStrategy, Bill, Reservation            | HAS-A, HAS-A, Uses          |
| PaymentStrategy              | Bill, Payment                                 | Uses (interface)            |
| UPIPaymentStrategy           | PaymentStrategy, Bill, Payment, PaymentMode   | IMPLEMENTS, Uses, Creates   |
| Payment                      | PaymentMode                                   | HAS-A                       |
| PaymentManager               | PaymentStrategy, Payment, Bill                | HAS-A, HAS-A, Uses          |
| VehicleInventoryManager      | Vehicle, ReservationRepository, DateInterval, | HAS-A (map), HAS-A,         |
|                              | VehicleType, VehicleStatus, ReentrantLock     | Uses, Uses, Uses, HAS-A     |
| ReservationManager           | VehicleInventoryManager, ReservationRepository| HAS-A, HAS-A,               |
|                              | Reservation, User, ReservationType            | Creates, Uses, Uses         |
| Store                        | Location, VehicleInventoryManager,            | HAS-A (all five),           |
|                              | ReservationManager, BillManager,              | Uses User, Reservation,     |
|                              | PaymentManager, User, Reservation, Bill,      | Bill, Payment, Strategies   |
|                              | Payment, BillingStrategy, PaymentStrategy     |                             |
| VehicleRentalSystem          | Store, User                                   | HAS-A (list), HAS-A (list)  |
| Demo                         | ALL ABOVE CLASSES                             | Creates, Wires, Uses        |

---

## Dependency Count by Layer

```
Layer 1 (Enums):              0 dependencies
Layer 2 (Basic Entities):     0-2 dependencies
Layer 3 (Reservation):        2 dependencies (enums)
Layer 4 (Repository):         1 dependency (Reservation)
Layer 5 (Inventory Manager):  5+ dependencies
Layer 6 (Reservation Manager):6+ dependencies
Layer 7 (Strategy Interfaces):1-2 dependencies
Layer 8 (Strategies & Entities): 2-4 dependencies
Layer 9 (Bill/Payment Managers): 3-4 dependencies
Layer 10 (Store):             15+ dependencies
Layer 11 (System):            2 dependencies (Store, User)
Layer 12 (Client):            ALL dependencies (23+ classes)
```

---

## Thread-Safety Chain

```
Multiple Threads Booking Same Vehicle
    │
    ├─→ Thread 1: store.createReservation(vehicleId=1, ...)
    │       │
    │       └─→ inventory.reserve(1, resId1, from1, to1)
    │           │
    │           └─→ lockForVehicle(1).lock()  [Thread 1 acquires lock]
    │               │
    │               ├─→ isAvailable(1, from1, to1)  [Check]
    │               ├─→ vehicleBookingIds.get(1).add(resId1)  [Reserve]
    │               ├─→ vehicle.setStatus(BOOKED)
    │               └─→ lock.unlock()
    │
    └─→ Thread 2: store.createReservation(vehicleId=1, ...) [SAME VEHICLE]
            │
            └─→ inventory.reserve(1, resId2, from2, to2)
                │
                └─→ lockForVehicle(1).lock()  [Thread 2 BLOCKS - waiting for lock]
                    │
                    └─→ [After Thread 1 releases lock]
                        │
                        ├─→ isAvailable(1, from2, to2)  [Check with Thread 1's reservation]
                        │   │
                        │   └─→ If overlaps: return false
                        │       [Prevents double-booking]
                        │
                        └─→ lock.unlock()

Note: Different vehicles (vehicleId=1 vs vehicleId=2) have SEPARATE locks
      → Can be booked simultaneously by different threads
```

---

## Strategy Pattern Flow

```
THREE Strategy Patterns in System:

1. Billing Strategy
   ═══════════════
   BillManager ──HAS-A──► BillingStrategy
                              │
                              ├─→ DailyBillingStrategy
                              ├─→ (Future: HourlyBillingStrategy)
                              ├─→ (Future: WeeklyBillingStrategy)
                              └─→ (Future: MembershipDiscountStrategy)

2. Payment Strategy
   ════════════════
   PaymentManager ──HAS-A──► PaymentStrategy
                                  │
                                  ├─→ UPIPaymentStrategy
                                  ├─→ (Future: CashPaymentStrategy)
                                  ├─→ (Future: CardPaymentStrategy)
                                  └─→ (Future: WalletPaymentStrategy)

3. No Lookup Strategy (Unlike Parking Lot)
   ═══════════════════════════════════════
   Inventory returns filtered stream of all available vehicles
   First-come-first-served based on filter results
```

---

## Key Design Insights

### Repository Pattern
```
ReservationRepository
    ↓
Abstracts Data Access Layer

Benefits:
  - Separates business logic from data storage
  - Easy to swap in-memory with database
  - Testable with mock repositories
  - Consistent interface for CRUD operations
```

### Aggregate Root Pattern (Store)
```
Store is the Aggregate Root that:
  - Owns: Inventory, Reservations, Bills, Payments
  - Coordinates: All rental operations
  - Enforces: Business rules (payment before reservation removal)
  - Ensures: Consistency across subsystems

External clients interact ONLY with Store, not with internal managers.
```

### Bidirectional Reference
```
VehicleInventoryManager ←──→ ReservationRepository

Why?
  - Inventory needs to check reservation dates for availability
  - Circular dependency resolved by setter injection
  - Allows decoupling while maintaining functionality
```

### Atomic Operations
```
PROBLEM: Race Condition
  - Thread 1 checks availability (TRUE)
  - Thread 2 checks availability (TRUE)  [Before Thread 1 books]
  - Thread 1 books vehicle
  - Thread 2 books same vehicle  [DOUBLE-BOOKING!]

SOLUTION: Per-Vehicle Lock
  lock.lock()
  try {
      check + reserve (atomic)
  } finally {
      lock.unlock()
  }

BENEFIT: Prevents time-of-check-to-time-of-use (TOCTOU) race condition
```

### Date Overlap Algorithm
```
Interval A: [from1, to1]
Interval B: [from2, to2]

NO Overlap when:
  - A ends before B starts: to1 < from2
  - OR A starts after B ends: from1 > to2

Overlap when:
  - NOT (to1 < from2 OR from1 > to2)
  - Equivalent: !(to1 < from2 || from1 > to2)

Examples:
  - [Dec 5, Dec 7] overlaps [Dec 6, Dec 10] → TRUE
  - [Dec 5, Dec 7] overlaps [Dec 8, Dec 10] → FALSE
  - [Dec 5, Dec 7] overlaps [Dec 7, Dec 10] → TRUE (inclusive)
```

### Open/Closed Principle
```
BillingStrategy (interface)
        │
        ├─→ Add hourly billing? Create HourlyBillingStrategy (EXTENDS)
        │
PaymentStrategy (interface)
        │
        ├─→ Add card payment? Create CardPaymentStrategy (EXTENDS)
        │
VehicleType (enum)
        │
        └─→ Add motorcycles? Add MOTORCYCLE to enum (EXTENDS)
            └─→ System handles automatically (filters, storage, etc.)

System is OPEN for extension, CLOSED for modification.
```

---

## Complete System at a Glance

```
24 Classes Total:
  - 5 Enums (VehicleType, VehicleStatus, ReservationType, ReservationStatus, PaymentMode)
  - 6 Entities (Location, User, Vehicle, Reservation, Bill, Payment)
  - 1 Value Object (DateInterval)
  - 2 Strategy Interfaces (BillingStrategy, PaymentStrategy)
  - 2 Strategy Implementations (DailyBillingStrategy, UPIPaymentStrategy)
  - 1 Repository (ReservationRepository)
  - 4 Managers (VehicleInventoryManager, ReservationManager, BillManager, PaymentManager)
  - 1 Aggregate Root (Store)
  - 1 System Facade (VehicleRentalSystem)
  - 1 Client (Demo)

Design Patterns:
  - Strategy Pattern (×2: Billing, Payment)
  - Repository Pattern
  - Facade Pattern (×2: Store, VehicleRentalSystem)
  - Aggregate Root Pattern (DDD)
  - Dependency Injection

Relationships:
  - 2 IMPLEMENTS (strategy implementations)
  - 25+ HAS-A (composition)
  - 40+ USES (dependency)

Thread Safety:
  - ReentrantLock per vehicle
  - ConcurrentHashMap for all collections
  - AtomicInteger for ID generation
  - Volatile for vehicle status
  - Atomic check-and-reserve operations
```

---

## System Workflow Sequence

```
1. System Setup
   ════════════
   VehicleRentalSystem created
       │
       └─→ Stores added with:
           - Location
           - Empty Inventory
           - Managers with default strategies
   
   Users registered

2. Inventory Setup
   ═══════════════
   Vehicles added to store inventory
   Daily rental costs configured

3. Search Phase
   ════════════
   User searches available vehicles
       │
       └─→ Filter by: Type, Date Range, Status
           └─→ Check date overlaps with existing reservations

4. Reservation Phase
   ═══════════════════
   Create reservation (SCHEDULED)
       │
       └─→ Atomic: Check + Reserve + Update Status
           └─→ Thread-safe with per-vehicle locks

5. Trip Phase
   ═══════════
   Start trip: SCHEDULED → IN_USE
   ...drive the vehicle...
   Submit vehicle: IN_USE → COMPLETED
       │
       └─→ Release vehicle (back to AVAILABLE if no more bookings)

6. Billing Phase
   ══════════════
   Generate bill
       │
       └─→ Strategy: Calculate days × daily rate

7. Payment Phase
   ═══════════════
   Process payment
       │
       ├─→ Strategy: Create payment receipt
       ├─→ Mark bill as paid
       └─→ Remove reservation from system
```

---

## Comparison with Parking Lot System

| Aspect                    | Parking Lot                    | Car Rental                    |
|---------------------------|--------------------------------|-------------------------------|
| **Primary Entity**        | ParkingSpot                    | Vehicle                       |
| **Allocation**            | Immediate (entry gate)         | Future (date-based booking)   |
| **Availability Logic**    | Boolean free/occupied          | Date range overlap checking   |
| **Pricing**               | Duration-based (entry to exit) | Fixed daily/hourly rate       |
| **Lifecycle**             | Entry → Exit (simple)          | Reserve → Trip → Return       |
| **Payment Timing**        | At exit                        | After vehicle return          |
| **Thread Safety**         | Per-manager locks              | Per-vehicle locks             |
| **Multi-Level**           | Yes (parking levels)           | No (flat inventory)           |
| **Repository Pattern**    | No                             | Yes (ReservationRepository)   |
| **Aggregate Root**        | ParkingLot (facade)            | Store (true DDD aggregate)    |
| **Strategy Patterns**     | 3 (Lookup, Pricing, Payment)   | 2 (Billing, Payment)          |
| **Complexity**            | Spatial (levels, spots)        | Temporal (dates, overlaps)    |

---

## Key Technical Highlights

### 1. Concurrent Booking Prevention
```java
// BEFORE: Race condition possible
if (isAvailable()) {
    // Another thread could book here!
    book();
}

// AFTER: Atomic operation
lock.lock();
try {
    if (isAvailable()) {
        book();  // No race condition
    }
} finally {
    lock.unlock();
}
```

### 2. Date Overlap Detection
```java
DateInterval bookedInterval = new DateInterval(Dec 5, Dec 7);
DateInterval requestedInterval = new DateInterval(Dec 6, Dec 10);

bookedInterval.overlaps(requestedInterval) → TRUE
// Cannot book vehicle for Dec 6-10 because Dec 6-7 overlaps
```

### 3. Per-Vehicle Granular Locking
```java
// NOT: Single global lock (would serialize all bookings)
// YES: Per-vehicle locks (parallel booking for different vehicles)

Vehicle 1: Thread A books ──► Lock 1 ──► No contention
Vehicle 2: Thread B books ──► Lock 2 ──► Parallel execution!
```

### 4. Strategy Runtime Switching
```java
// Can change strategies at runtime
store.generateBill(resId, new DailyBillingStrategy(...));  // Use daily
store.generateBill(resId, new HourlyBillingStrategy(...)); // Switch to hourly
```

### 5. Business Rule Enforcement
```java
// Payment MUST succeed before reservation removal
if (!bill.isBillPaid()) {
    throw RuntimeException("Payment failed");
}
// Only after payment success:
reservationManager.remove(reservationId);
```

---

## Extensibility Examples

### Adding Hourly Billing
```java
public class HourlyBillingStrategy implements BillingStrategy {
    
    @Override
    public Bill generateBill(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(
            reservation.getDateBookedFrom(),
            reservation.getDateBookedTo()
        );
        Vehicle vehicle = inventory.getVehicle(reservation.getVehicleId()).get();
        double hourlyRate = vehicle.getHourlyRentalCost();
        double total = hours * hourlyRate;
        return new Bill(generateId(), reservation.getReservationId(), total);
    }
}

// Usage: Just inject new strategy
store.generateBill(resId, new HourlyBillingStrategy(inventory));
```

### Adding Cash Payment
```java
public class CashPaymentStrategy implements PaymentStrategy {
    
    @Override
    public Payment processPayment(Bill bill, double amount) {
        Payment payment = new Payment(
            generateId(),
            bill.getBillId(),
            amount,
            PaymentMode.CASH,
            new Date()
        );
        bill.setBillPaid(true);
        return payment;
    }
}

// Usage: Just inject new strategy
store.makePayment(bill, new CashPaymentStrategy(), amount);
```

### Adding Motorcycle Type
```java
// 1. Add to enum
public enum VehicleType {
    TWO_WHEELER,
    FOUR_WHEELER,
    MOTORCYCLE  // NEW
}

// 2. That's it! System handles automatically:
//    - Storage (generic HashMap)
//    - Filtering (enum comparison)
//    - Availability checking (works for any type)
```

---

## Performance Characteristics

### Time Complexity
- **Add Vehicle**: O(1) - ConcurrentHashMap.put
- **Search Vehicles**: O(n) - Stream filter over all vehicles
- **Check Availability**: O(m) - m = number of reservations for that vehicle
- **Reserve Vehicle**: O(m) - Availability check + O(1) add
- **Release Vehicle**: O(m) - Remove from list
- **Generate Bill**: O(1) - Direct vehicle lookup
- **Process Payment**: O(1) - Create payment object

### Space Complexity
- **Vehicles**: O(n) - n = total vehicles
- **Reservations**: O(r) - r = total active reservations
- **Vehicle Booking Index**: O(v×r) - v vehicles × r reservations per vehicle
- **Locks**: O(v) - One lock per vehicle
- **Bills**: O(b) - b = total bills generated
- **Payments**: O(p) - p = total payments made

### Optimization Opportunities
1. **Search Index**: Add secondary index on VehicleType for faster filtering
2. **Date Index**: B-tree index on date ranges for faster overlap detection
3. **Completed Reservation Cleanup**: Archive old completed reservations
4. **Bill Archive**: Move old bills to separate storage
5. **Payment History Pagination**: Limit in-memory payment records
