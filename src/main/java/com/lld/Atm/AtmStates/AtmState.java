package com.lld.Atm.AtmStates;

import com.lld.Atm.AtmRoomComponents.Atm;
import com.lld.Atm.AtmRoomComponents.Card;
import com.lld.Atm.enums.TranscationType;

public abstract class AtmState {

    public void insertCard(Atm atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void authenticatePin(Atm atm, Card card, int pin) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void selectOperation(Atm atm, Card card, TranscationType txnType) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void cashWithdrawal(Atm atm, Card card, int withdrawAmount) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void displayBalance(Atm atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void returnCard() {
        System.out.println("OOPS!! Something went wrong");
    }

    public void exit(Atm atm) {
        System.out.println("OOPS!! Something went wrong");
    }
}