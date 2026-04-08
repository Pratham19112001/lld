package com.lld.creationalPatterns.builder.solution;

import java.util.List;

/**
 * <h2>Builder Pattern &mdash; Concrete Builder: Engineering Student</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <p>Builds a {@link Student} configured for an engineering programme.
 * Sets the batch to "Engineering" and subjects to DSA, OS, and DBMS.</p>
 *
 * @see MBAStudentBuilder
 * @see StudentRegistrationDirector
 */
public class EngineeringStudentBuilder extends StudentBuilder {

    /**
     * Configures engineering-specific fields.
     *
     * @return this builder for chaining
     */
    @Override
    public StudentBuilder setSubjectsAndBatch() {
        this.batch = "Engineering";
        this.subjects = List.of("DSA", "Operating Systems", "DBMS", "Computer Networks");
        return this;
    }
}
