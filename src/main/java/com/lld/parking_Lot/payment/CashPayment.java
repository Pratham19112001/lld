package com.lld.parking_Lot.payment;

public class CashPayment implements Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment of " + amount + " done via Cash");
        return true;
    }
    
}
