package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory Pattern &mdash; Abstract Product: Car Interior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Declares the interface for a <b>family member</b>: the car's interior.
 * Each concrete factory will provide its own implementation (economy vs.
 * luxury).</p>
 */
public interface carInterior {

    /**
     * Describes the interior features of this car type.
     */
    void createInterior();
}
