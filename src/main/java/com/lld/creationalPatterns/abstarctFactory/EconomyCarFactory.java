package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Factory: Economy</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Produces a consistent <b>economy-tier</b> family of products:
 * {@link EconomyCarInterior} and {@link EconomyCarExterior}.</p>
 *
 * <p>Swapping this factory for {@link LuxuryCarFactory} changes the entire
 * product family without modifying client code.</p>
 */
public class EconomyCarFactory implements carFactory {

    @Override
    public carInterior createInterior() {
        return new EconomyCarInterior();
    }

    @Override
    public carExterior createExterior() {
        return new EconomyCarExterior();
    }
}
