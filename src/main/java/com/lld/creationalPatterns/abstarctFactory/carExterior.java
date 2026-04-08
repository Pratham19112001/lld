package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory Pattern &mdash; Abstract Product: Car Exterior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Declares the interface for a <b>family member</b>: the car's exterior.
 * Concrete factories produce matching interior + exterior pairs.</p>
 */
public interface carExterior {

    /**
     * Describes the exterior features of this car type.
     */
    void createExterior();
}
