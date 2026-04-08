package com.lld.creationalPatterns.builder.problem;

import java.util.List;

/**
 * <h2>Builder Pattern &mdash; Problem</h2>
 *
 * <p><b>Pattern:</b> Builder (Creational)</p>
 *
 * <h3>Problem</h3>
 * <p>When a class has many fields (some optional), the constructor becomes
 * unwieldy &mdash; this is known as the <b>Telescoping Constructor
 * Anti-pattern</b>.</p>
 * <ul>
 *   <li>Multiple overloaded constructors for every combination of optional
 *       fields.</li>
 *   <li>The caller must remember the <b>position</b> of each argument &mdash;
 *       error-prone and hard to read.</li>
 *   <li>No compile-time safety: swapping two {@code int} arguments silently
 *       produces a wrong object.</li>
 * </ul>
 *
 * <p>See the {@code solution} package for the Builder-based fix.</p>
 *
 * @see com.lld.creationalPatterns.builder.solution.Student
 * @see com.lld.creationalPatterns.builder.solution.StudentBuilder
 */
public class Student {

    private int rollNumber;
    private int age;
    private String name;
    private String fatherName;
    private String motherName;
    private String batch;
    private List<String> subjects;

    /**
     * Telescoping constructor &mdash; hard to read, easy to misuse.
     * Swapping rollNumber and age at the call-site compiles fine but
     * produces an incorrect Student.
     */
    public Student(int rollNumber, int age, String name, String fatherName,
                   String motherName, String batch) {
        this.rollNumber = rollNumber;
        this.age = age;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Student{roll=" + rollNumber + ", age=" + age + ", name='" + name
                + "', father='" + fatherName + "', mother='" + motherName
                + "', batch='" + batch + "'}";
    }
}
