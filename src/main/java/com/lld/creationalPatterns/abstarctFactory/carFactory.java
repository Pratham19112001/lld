package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory Pattern &mdash; Abstract Factory</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <h3>What is Abstract Factory?</h3>
 * <p>Abstract Factory provides an interface for creating <b>families of related
 * objects</b> without specifying their concrete classes. In this example, a car
 * factory produces a matching interior + exterior pair.</p>
 *
 * <h3>Key Difference from Factory Method</h3>
 * <ul>
 *   <li><b>Factory Method</b>: creates a single product.</li>
 *   <li><b>Abstract Factory</b>: creates a <b>family</b> of related products
 *       (interior + exterior).</li>
 * </ul>
 *
 * @see EconomyCarFactory
 * @see LuxuryCarFactory
 */
public interface carFactory {

    /**
     * Creates the interior component of this car family.
     *
     * @return a car interior matching this factory's tier
     */
    carInterior createInterior();

    /**
     * Creates the exterior component of this car family.
     *
     * @return a car exterior matching this factory's tier
     */
    carExterior createExterior();
}
