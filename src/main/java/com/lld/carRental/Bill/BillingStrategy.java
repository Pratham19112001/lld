package com.lld.carRental.Bill;
import com.lld.carRental.reservation.Reservation;

public interface BillingStrategy {

    Bill generateBill(Reservation reservation);
}
