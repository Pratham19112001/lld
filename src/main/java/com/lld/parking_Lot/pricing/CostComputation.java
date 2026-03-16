package com.lld.parking_Lot.pricing;
import com.lld.parking_Lot.Entity.Ticket;

public class CostComputation {

    private pricingStrategy pricingStrategy;

    public CostComputation(pricingStrategy pricingStrategy){
        this.pricingStrategy = pricingStrategy;
    }

    public double computeCost(Ticket ticket){
        return pricingStrategy.computeCost(ticket);
    }

}
