package com.lld.Atm.enums;

public enum TranscationType {

    CASH_WITHDRAWAL,
    BALANCE_CHECK;

    public static void showAllTransactionTypes() {

        for (TranscationType type : TranscationType.values()) {
            System.out.println(type.name());
        }
    }
}
