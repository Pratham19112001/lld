package com.lld.parking_Lot;

import com.lld.parking_Lot.Entity.ParkingSpot;
import com.lld.parking_Lot.Entity.Vehicle;
import com.lld.parking_Lot.Entity.Ticket;
import com.lld.parking_Lot.LookupStrategy.ParkingSpotLookupStrategy;
import com.lld.parking_Lot.LookupStrategy.RandomLookupStrategy;
import com.lld.parking_Lot.enums.VehicleType;
import com.lld.parking_Lot.parkingLot.*;
import com.lld.parking_Lot.payment.CashPayment;
import com.lld.parking_Lot.payment.UpiPayment;
import com.lld.parking_Lot.pricing.CostComputation;
import com.lld.parking_Lot.pricing.FixedPricingStrategy;
import com.lld.parking_Lot.spotManagers.FourWheelerSpotManager;
import com.lld.parking_Lot.spotManagers.ParkingSpotManager;
import com.lld.parking_Lot.spotManagers.TwoWheelerSpotManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingClient {

    public static void main(String[] args) {

        ParkingSpotLookupStrategy strategy = new RandomLookupStrategy();

        Map<VehicleType, ParkingSpotManager> levelOneManagers = new HashMap<>();
        levelOneManagers.put(VehicleType.TWO_WHEELER,
                new TwoWheelerSpotManager(List.of(new ParkingSpot("L1-S1"),
                        new ParkingSpot("L1-S2")), strategy));

        levelOneManagers.put(VehicleType.FOUR_WHEELER,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L1-S3")), strategy));

        ParkingLevel level1 = new ParkingLevel(
                1, levelOneManagers
        );

        Map<VehicleType, ParkingSpotManager> levelTwoManagers = new HashMap<>();
        levelTwoManagers.put(VehicleType.TWO_WHEELER,
                new TwoWheelerSpotManager(List.of(new ParkingSpot("L2-S1")), strategy));

        levelTwoManagers.put(VehicleType.FOUR_WHEELER,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L2-S2"),
                        new ParkingSpot("L2-S3")), strategy));


        ParkingLevel level2 = new ParkingLevel(
                2, levelTwoManagers
        );

        ParkingBuilding parkingBuilding =
                new ParkingBuilding(
                        List.of(level1, level2),
                        new CostComputation(new FixedPricingStrategy())
                );

        ParkingLot parkingLot = new ParkingLot(
                parkingBuilding,
                new EntranceGate(),
                new ExitGate(new CostComputation(new FixedPricingStrategy()))
        );


        Vehicle bike = new Vehicle("BIKE-101", VehicleType.TWO_WHEELER);
        Vehicle car = new Vehicle("CAR-201", VehicleType.FOUR_WHEELER);

        Ticket t1 = parkingLot.vehicleArrives(bike);
        Ticket t2 = parkingLot.vehicleArrives(car);

        parkingLot.vehicleExits(t1, new CashPayment());
        parkingLot.vehicleExits(t2, new UpiPayment());
    }
}

