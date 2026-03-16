package com.lld.parking_Lot.parkingLot;

import com.lld.parking_Lot.Entity.Vehicle;
import com.lld.parking_Lot.Entity.Ticket;

public class EntranceGate {

    public Ticket enter(ParkingBuilding building, Vehicle vehicle) {
        return building.allocate(vehicle);
    }
}
