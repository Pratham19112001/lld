package com.lld.parking_Lot.pricing;

import com.lld.parking_Lot.Entity.Ticket;
public class FixedPricingStrategy implements pricingStrategy{
    @Override
    public double computeCost(Ticket ticket) {
        return 100;
    }
}
