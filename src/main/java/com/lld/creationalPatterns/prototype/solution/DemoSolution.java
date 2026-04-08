package com.lld.creationalPatterns.prototype.solution;

/**
 * <h2>Prototype Pattern &mdash; Solution Demo</h2>
 *
 * <p><b>Pattern:</b> Prototype (Creational)</p>
 *
 * <h3>What is the Prototype Pattern?</h3>
 * <p>Prototype lets you create new objects by <b>cloning</b> an existing object
 * (the prototype) instead of using {@code new}. The cloning logic lives inside
 * the object itself, so the client doesn't need to know the class's internals.</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem:</b> Manually copying every field is fragile and exposes
 *       internal structure
 *       (see {@link com.lld.creationalPatterns.prototype.problem.DemoProblem}).</li>
 *   <li><b>Solution:</b> Each class implements a {@code clone()} method that
 *       creates its own copy, hiding all fields from the caller.</li>
 * </ul>
 *
 * <p>Run this class to see the prototype-based copy in action.</p>
 */
public class DemoSolution {

    public static void main(String[] args) {
        System.out.println("===== Prototype Pattern — SOLUTION =====\n");

        Student original = new Student(21, "Pratham");
        System.out.println("Original : " + original);

        // Clone via prototype — client doesn't need to know internal fields
        Student copy = (Student) original.clone();
        System.out.println("Clone    : " + copy);

        // Modifying the clone does not affect the original
        copy.setName("Shrayansh");
        copy.setAge(22);
        System.out.println("\nAfter modifying clone:");
        System.out.println("Original : " + original);
        System.out.println("Clone    : " + copy);

        System.out.println("\nSolution: clone() encapsulates the copy logic inside the class.");
        System.out.println("Adding new fields only requires updating the copy constructor.");
    }
}
