package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Factory: Luxury</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Produces a consistent <b>luxury-tier</b> family of products:
 * {@link LuxuryCarInterior} and {@link LuxuryCarExterior}.</p>
 */
public class LuxuryCarFactory implements carFactory {

    @Override
    public carInterior createInterior() {
        return new LuxuryCarInterior();
    }

    @Override
    public carExterior createExterior() {
        return new LuxuryCarExterior();
    }
}
