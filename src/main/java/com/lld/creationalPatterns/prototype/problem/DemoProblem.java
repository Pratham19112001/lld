package com.lld.creationalPatterns.prototype.problem;

/**
 * <h2>Prototype Pattern &mdash; Problem Demo</h2>
 *
 * <p><b>Pattern:</b> Prototype (Creational)</p>
 *
 * <h3>Problem demonstrated</h3>
 * <p>To copy a {@link Student} object we have to manually read every field and
 * create a new instance. This approach:</p>
 * <ul>
 *   <li>Violates the <b>Open/Closed Principle</b> &mdash; adding a field means
 *       changing every copy-site.</li>
 *   <li>Cannot copy <b>private</b> fields from outside the class.</li>
 *   <li>Forces the client to depend on the <b>concrete class</b>.</li>
 * </ul>
 *
 * <p>Run this class to see the manual-copy approach in action, then compare
 * with the solution in
 * {@link com.lld.creationalPatterns.prototype.solution.DemoSolution}.</p>
 */
public class DemoProblem {

    public static void main(String[] args) {
        System.out.println("===== Prototype Pattern — PROBLEM =====\n");

        Student original = new Student(21, "Pratham");
        System.out.println("Original : " + original);

        // Manual copy — client must know every field
        Student copy = new Student(original.getAge(), original.getName());
        System.out.println("Copy     : " + copy);

        // Modifying the copy does not affect the original
        copy.setName("Shrayansh");
        System.out.println("\nAfter modifying copy:");
        System.out.println("Original : " + original);
        System.out.println("Copy     : " + copy);

        System.out.println("\nProblem: client must know all fields to copy.");
        System.out.println("If a new field is added, every copy-site must change.");
    }
}
