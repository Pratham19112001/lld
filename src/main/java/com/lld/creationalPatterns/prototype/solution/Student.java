package com.lld.creationalPatterns.prototype.solution;

/**
 * <h2>Prototype Pattern &mdash; Concrete Prototype</h2>
 *
 * <p><b>Pattern:</b> Prototype (Creational)</p>
 *
 * <h3>Solution</h3>
 * <p>The {@code Student} class implements {@link StudentPrototype} and knows
 * how to copy itself via the {@link #clone()} method. The copy constructor is
 * private, so the only way to obtain a copy is through the prototype
 * interface.</p>
 *
 * <p>This keeps the cloning logic <b>inside the class</b> where it belongs,
 * and new fields added in the future only need to be handled in one place.</p>
 *
 * @see StudentPrototype
 * @see DemoSolution
 */
public class Student implements StudentPrototype {

    private int age;
    private String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    /** Copy constructor &mdash; used internally by {@link #clone()}. */
    private Student(Student source) {
        this.age = source.age;
        this.name = source.name;
    }

    /**
     * Creates a deep copy of this Student.
     *
     * @return a new Student with the same field values
     */
    @Override
    public StudentPrototype clone() {
        return new Student(this);
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
