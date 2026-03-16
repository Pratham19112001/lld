package com.lld.parking_Lot.pricing;

import com.lld.parking_Lot.Entity.Ticket;
public interface pricingStrategy {

    double computeCost(Ticket ticket);

}
