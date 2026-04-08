package com.lld.creationalPatterns.builder.solution;

import java.util.List;

/**
 * <h2>Builder Pattern &mdash; Abstract Builder</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <h3>What is the Builder Pattern?</h3>
 * <p>Builder separates the <b>construction</b> of a complex object from its
 * <b>representation</b>. The same construction process can create different
 * representations by using different concrete builders.</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem:</b> Telescoping constructors are hard to read, and
 *       swapping positional arguments causes silent bugs.</li>
 *   <li><b>Solution:</b> A builder provides <b>named methods</b> for each
 *       field. A director orchestrates the build steps. The result is a
 *       readable, type-safe, step-by-step construction.</li>
 * </ul>
 *
 * <h3>Participants</h3>
 * <ul>
 *   <li><b>Builder</b> (this class) &mdash; declares build steps</li>
 *   <li><b>ConcreteBuilder</b> ({@link EngineeringStudentBuilder},
 *       {@link MBAStudentBuilder}) &mdash; implements steps for a specific
 *       variant</li>
 *   <li><b>Director</b> ({@link StudentRegistrationDirector}) &mdash;
 *       orchestrates the build order</li>
 *   <li><b>Product</b> ({@link Student}) &mdash; the complex object being
 *       built</li>
 * </ul>
 *
 * @see EngineeringStudentBuilder
 * @see MBAStudentBuilder
 * @see StudentRegistrationDirector
 */
public abstract class StudentBuilder {

    int rollNumber;
    int age;
    String name;
    String fatherName;
    String motherName;
    String batch;
    List<String> subjects;

    public StudentBuilder setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
        return this;
    }

    public StudentBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public StudentBuilder setMotherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    /**
     * Sets the batch and subjects specific to the student type.
     * Each concrete builder provides its own implementation.
     *
     * @return this builder for chaining
     */
    public abstract StudentBuilder setSubjectsAndBatch();

    /**
     * Builds and returns the final {@link Student} object.
     *
     * @return a fully constructed Student
     */
    public Student build() {
        return new Student(this);
    }
}
