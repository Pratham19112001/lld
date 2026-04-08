package com.lld.creationalPatterns.builder.solution;

/**
 * <h2>Builder Pattern &mdash; Demo</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem:</b> The
 *       {@link com.lld.creationalPatterns.builder.problem.Student} class uses
 *       a telescoping constructor with many positional arguments. It is hard
 *       to read, easy to misuse, and does not scale when new fields are
 *       added.</li>
 *   <li><b>Solution:</b> A {@link StudentBuilder} provides named setters, and
 *       a {@link StudentRegistrationDirector} orchestrates the build steps.
 *       Different builders ({@link EngineeringStudentBuilder},
 *       {@link MBAStudentBuilder}) produce different student types without
 *       duplicating the construction logic.</li>
 * </ul>
 *
 * <p>Run this class to see the builder in action.</p>
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("===== Builder Pattern Demo =====\n");

        // Engineering student via director
        System.out.println("--- Engineering Student ---");
        StudentRegistrationDirector engDirector =
                new StudentRegistrationDirector(new EngineeringStudentBuilder());
        Student engStudent = engDirector.construct(101, 20, "Pratham", "Mr. Agarwal", "Mrs. Agarwal");
        System.out.println(engStudent);

        // MBA student via director
        System.out.println("\n--- MBA Student ---");
        StudentRegistrationDirector mbaDirector =
                new StudentRegistrationDirector(new MBAStudentBuilder());
        Student mbaStudent = mbaDirector.construct(201, 23, "Shrayansh", "Mr. Jain", "Mrs. Jain");
        System.out.println(mbaStudent);

        // Direct builder usage (without director)
        System.out.println("\n--- Direct Builder (no director) ---");
        Student custom = new EngineeringStudentBuilder()
                .setRollNumber(301)
                .setAge(21)
                .setName("Custom Student")
                .setSubjectsAndBatch()
                .build();
        System.out.println(custom);

        System.out.println("\nSolution: Named methods, step-by-step construction, no positional confusion.");
    }
}
