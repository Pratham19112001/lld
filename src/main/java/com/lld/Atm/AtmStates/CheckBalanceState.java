package com.lld.Atm.AtmStates;

import com.lld.Atm.AtmRoomComponents.Atm;
import com.lld.Atm.AtmRoomComponents.Card;

public class CheckBalanceState extends AtmState {

    public CheckBalanceState() {
    }

    @Override
    public void displayBalance(Atm atm, Card card) {
        System.out.println("Your Balance is: " + card.getBankBalance());
        exit(atm);
    }

    @Override
    public void exit(Atm atmObject) {
        returnCard();
        atmObject.setCurrentATMState(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard() {
        System.out.println("Please collect your card");
    }
}