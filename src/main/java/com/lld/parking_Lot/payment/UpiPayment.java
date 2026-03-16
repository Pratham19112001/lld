package com.lld.parking_Lot.payment;

public class UpiPayment implements Payment {

    @Override
    public boolean pay(double amount) {
        System.out.println("UPI paid: " + amount);
        return true;
    }
}
