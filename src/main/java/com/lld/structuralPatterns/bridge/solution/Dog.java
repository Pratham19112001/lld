package com.lld.structuralPatterns.bridge.solution;

/** Refined abstraction: dog uses a {@link BreathingProcess} (e.g. lungs). */
public class Dog extends LivingThings {

    public Dog(BreathingProcess breathingProcess) {
        super(breathingProcess);
    }

    @Override
    public void breathe() {
        System.out.print("Dog: ");
        breathingProcess.breathe();
    }
}
