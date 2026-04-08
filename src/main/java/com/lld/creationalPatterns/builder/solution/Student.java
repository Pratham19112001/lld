package com.lld.creationalPatterns.builder.solution;

import java.util.List;

/**
 * <h2>Builder Pattern &mdash; Product</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <h3>Solution</h3>
 * <p>Instead of a telescoping constructor, the {@code Student} object is built
 * step-by-step through a {@link StudentBuilder}. The builder exposes named
 * setter methods, making the construction call readable and type-safe.</p>
 *
 * <p>The constructor is package-private so that only the builder (and the
 * director) in the same package can create instances.</p>
 *
 * @see StudentBuilder
 * @see StudentRegistrationDirector
 */
public class Student {

    private int rollNumber;
    private int age;
    private String name;
    private String fatherName;
    private String motherName;
    private String batch;
    private List<String> subjects;

    Student(StudentBuilder builder) {
        this.rollNumber = builder.rollNumber;
        this.age = builder.age;
        this.name = builder.name;
        this.fatherName = builder.fatherName;
        this.motherName = builder.motherName;
        this.batch = builder.batch;
        this.subjects = builder.subjects;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return "Student{roll=" + rollNumber + ", age=" + age + ", name='" + name
                + "', father='" + fatherName + "', mother='" + motherName
                + "', batch='" + batch + "', subjects=" + subjects + "}";
    }
}
