package com.lld.parking_Lot.LookupStrategy;
import java.util.List;

import com.lld.parking_Lot.Entity.ParkingSpot;

public interface ParkingSpotLookupStrategy{

    ParkingSpot selectSpot(List<ParkingSpot> spots);

}