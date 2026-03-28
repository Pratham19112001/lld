package com.lld.carRental.payment;

import com.lld.carRental.Bill.Bill;

public interface PaymentStrategy {

    Payment processPayment(Bill bill, double paymentAmount);
}

