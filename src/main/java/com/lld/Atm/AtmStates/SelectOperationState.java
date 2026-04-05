package com.lld.Atm.AtmStates;

import com.lld.Atm.AtmRoomComponents.Atm;
import com.lld.Atm.AtmRoomComponents.Card;
import com.lld.Atm.enums.TranscationType;

public class SelectOperationState extends AtmState {

    public SelectOperationState() {
        showOperations();
    }

    @Override
    public void selectOperation(Atm atmObject, Card card, TranscationType txnType) {
        switch (txnType) {
            case CASH_WITHDRAWAL:
                atmObject.setCurrentATMState(new CashWithdrawalState());
                break;
            case BALANCE_CHECK:
                atmObject.setCurrentATMState(new CheckBalanceState());
                break;
            default: {
                System.out.println("Invalid Option");
                exit(atmObject);
            }
        }
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

    private void showOperations() {
        System.out.println("Please select the Operation");
        TranscationType.showAllTransactionTypes();
    }
}