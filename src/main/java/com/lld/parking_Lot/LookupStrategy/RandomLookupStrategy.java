package com.lld.parking_Lot.LookupStrategy;
import java.util.List;

import com.lld.parking_Lot.Entity.ParkingSpot;

public class RandomLookupStrategy implements ParkingSpotLookupStrategy{

    @Override
    public ParkingSpot selectSpot(List<ParkingSpot> spots) {
        for(ParkingSpot spot : spots){
            if(spot.isSpotFree()){
                return spot;
            }
        }
        return null;
    }

}