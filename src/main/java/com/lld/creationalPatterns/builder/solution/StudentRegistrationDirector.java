package com.lld.creationalPatterns.builder.solution;

/**
 * <h2>Builder Pattern &mdash; Director</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <p>The Director knows <b>how</b> to execute the build steps in the right
 * order. It works with any {@link StudentBuilder} subclass, so the same
 * registration process produces different student types (Engineering, MBA,
 * etc.).</p>
 *
 * <p>Separating the director from the builder means the build sequence can
 * be reused, and new student types can be added by creating a new builder
 * without modifying the director.</p>
 *
 * @see StudentBuilder
 */
public class StudentRegistrationDirector {

    private final StudentBuilder builder;

    public StudentRegistrationDirector(StudentBuilder builder) {
        this.builder = builder;
    }

    /**
     * Executes the full registration workflow using the injected builder.
     *
     * @param rollNumber  student's roll number
     * @param age         student's age
     * @param name        student's name
     * @param fatherName  father's name
     * @param motherName  mother's name
     * @return a fully constructed {@link Student}
     */
    public Student construct(int rollNumber, int age, String name,
                             String fatherName, String motherName) {
        return builder
                .setRollNumber(rollNumber)
                .setAge(age)
                .setName(name)
                .setFatherName(fatherName)
                .setMotherName(motherName)
                .setSubjectsAndBatch()
                .build();
    }
}
