package com.lld.creationalPatterns.builder.solution;

import java.util.List;

/**
 * <h2>Builder Pattern &mdash; Concrete Builder: MBA Student</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <p>Builds a {@link Student} configured for an MBA programme.
 * Sets the batch to "MBA" and subjects to Marketing, Finance, and HR.</p>
 *
 * @see EngineeringStudentBuilder
 * @see StudentRegistrationDirector
 */
public class MBAStudentBuilder extends StudentBuilder {

    /**
     * Configures MBA-specific fields.
     *
     * @return this builder for chaining
     */
    @Override
    public StudentBuilder setSubjectsAndBatch() {
        this.batch = "MBA";
        this.subjects = List.of("Marketing", "Finance", "Human Resources");
        return this;
    }
}
