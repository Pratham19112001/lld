package com.lld.Atm.AtmRoomComponents;

import com.lld.Atm.enums.TranscationType;

public class AtmRoom {
    Atm atm;
    User user;

    public static void main(String[] args) {

        AtmRoom atmRoom = new AtmRoom();
        atmRoom.initialize();

        atmRoom.atm.printCurrentATMStatus();
        atmRoom.atm.getCurrentATMState().insertCard(atmRoom.atm, atmRoom.user.getCard());
        atmRoom.atm.getCurrentATMState().authenticatePin(atmRoom.atm, atmRoom.user.getCard(), 112211);
        atmRoom.atm.getCurrentATMState().selectOperation(atmRoom.atm, atmRoom.user.getCard(), TranscationType.CASH_WITHDRAWAL);
        atmRoom.atm.getCurrentATMState().cashWithdrawal(atmRoom.atm, atmRoom.user.getCard(), 2700);
        atmRoom.atm.printCurrentATMStatus();

    }

    private void initialize() {

        // create ATM
        atm = Atm.getATMObject();
        atm.setAtmBalance(3500, 1, 2, 5);

        // create User
        this.user = createUser();
    }

    private User createUser() {

        User user = new User();
        user.setCard(createCard());
        return user;
    }

    private Card createCard() {

        Card card = new Card();
        card.setBankAccount(createBankAccount());
        return card;
    }

    private UserBankAccount createBankAccount() {

        UserBankAccount bankAccount = new UserBankAccount();
        bankAccount.setBalance(3000);

        return bankAccount;
    }

}