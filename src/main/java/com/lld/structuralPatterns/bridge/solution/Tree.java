package com.lld.structuralPatterns.bridge.solution;

/** Refined abstraction: tree uses a {@link BreathingProcess} (e.g. photosynthesis). */
public class Tree extends LivingThings {

    public Tree(BreathingProcess breathingProcess) {
        super(breathingProcess);
    }

    @Override
    public void breathe() {
        System.out.print("Tree: ");
        breathingProcess.breathe();
    }
}
