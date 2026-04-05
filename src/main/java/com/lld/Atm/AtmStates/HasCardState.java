package com.lld.Atm.AtmStates;

import com.lld.Atm.AtmRoomComponents.Atm;
import com.lld.Atm.AtmRoomComponents.Card;

public class HasCardState extends AtmState {

    public HasCardState() {
        System.out.println("enter your card pin number");
    }

    @Override
    public void authenticatePin(Atm atm, Card card, int pin) {
        boolean isCorrectPinEntered = card.isCorrectPINEntered(pin);

        if (isCorrectPinEntered) {
            atm.setCurrentATMState(new SelectOperationState());
        } else {
            System.out.println("Invalid PIN Number");
            exit(atm);
        }
    }

    @Override
    public void exit(Atm atm) {
        returnCard();
        atm.setCurrentATMState(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard() {
        System.out.println("Please collect your card");
    }
}
