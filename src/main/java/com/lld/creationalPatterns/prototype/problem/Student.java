package com.lld.creationalPatterns.prototype.problem;

/**
 * <h2>Prototype Pattern &mdash; Problem</h2>
 *
 * <p><b>Pattern:</b> Prototype (Creational)</p>
 *
 * <h3>Problem</h3>
 * <p>When we need to create a copy of an existing object, we must manually copy
 * every field. This has several issues:</p>
 * <ul>
 *   <li>The client must <b>know every field</b> of the class.</li>
 *   <li>If a new field is added to the class, every copy-site must be updated.</li>
 *   <li>Private fields cannot be accessed from outside, so a true deep copy is
 *       impossible from the client.</li>
 *   <li>The client is <b>tightly coupled</b> to the concrete class.</li>
 * </ul>
 *
 * <p>See the {@code solution} package for the Prototype-based fix.</p>
 *
 * @see com.lld.creationalPatterns.prototype.solution.Student
 */
public class Student {

    private int age;
    private String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{age=" + age + ", name='" + name + "'}";
    }
}
