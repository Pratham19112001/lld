package com.lld.structuralPatterns.adapter;

/**
 * Adaptee: third-party imperial (pounds) weighing scale implementation.
 */
public class ImperialWeighingMachineImpl implements ImperialWeighingMachine {

    private double weightInPounds;

    public ImperialWeighingMachineImpl(double weighingScaleReading) {
        this.weightInPounds = weighingScaleReading;
    }

    @Override
    public double getWeightInPounds() {
        return weightInPounds;
    }
}
