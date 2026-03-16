package com.lld.parking_Lot.parkingLot;

import com.lld.parking_Lot.Entity.ParkingSpot;
import com.lld.parking_Lot.enums.VehicleType;
import com.lld.parking_Lot.spotManagers.ParkingSpotManager;

import java.util.Map;

public class ParkingLevel {

    private final int levelNumber;
    private final Map<VehicleType, ParkingSpotManager> managers;


    public ParkingLevel(int levelNumber,
                        Map<VehicleType, ParkingSpotManager> managers) {
        this.levelNumber = levelNumber;
        this.managers = managers;
    }

    public boolean hasAvailability(VehicleType type) {
        ParkingSpotManager manager = managers.get(type);
        return manager != null && manager.hasFreeSpot();
    }

    public ParkingSpot park(VehicleType type) {
        ParkingSpotManager manager = managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException(
                    "No parking manager for vehicle type: " + type);
        }
        return manager.park();
    }

    public void unPark(VehicleType type, ParkingSpot spot) {
        ParkingSpotManager manager = managers.get(type);
        if (manager != null) {
            manager.unPark(spot);
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
