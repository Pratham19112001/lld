package com.lld.creationalPatterns.prototype.solution;

/**
 * <h2>Prototype Pattern &mdash; Prototype Interface</h2>
 *
 * <p><b>Pattern:</b> Prototype (Creational)</p>
 *
 * <h3>Solution</h3>
 * <p>Define a {@code clone()} contract so that every concrete class knows how
 * to copy itself. The client simply calls {@code clone()} without knowing the
 * internal structure of the object.</p>
 *
 * <h3>Benefits over the problem approach</h3>
 * <ul>
 *   <li>Client is decoupled from the concrete class's fields.</li>
 *   <li>Adding new fields requires changes only inside the class, not in every
 *       copy-site.</li>
 *   <li>Private fields are accessible inside the class, so deep copies are
 *       trivial.</li>
 * </ul>
 *
 * @see Student
 */
public interface StudentPrototype {

    /**
     * Creates and returns a deep copy of this object.
     *
     * @return a new object that is a copy of this instance
     */
    StudentPrototype clone();
}
