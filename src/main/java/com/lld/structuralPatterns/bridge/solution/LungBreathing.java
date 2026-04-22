package com.lld.structuralPatterns.bridge.solution;

/**
 * Concrete implementor: breathing with lungs (mammals, birds, etc.).
 */
public class LungBreathing implements BreathingProcess {

    @Override
    public void breathe() {
        System.out.println("Breathing through lungs.");
    }
}
