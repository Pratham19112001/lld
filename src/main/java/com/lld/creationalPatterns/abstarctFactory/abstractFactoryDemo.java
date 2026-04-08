package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory Pattern &mdash; Demo</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <h3>What is Abstract Factory?</h3>
 * <p>Abstract Factory provides an interface for creating <b>families of
 * related or dependent objects</b> without specifying their concrete classes.
 * Each concrete factory guarantees that the products it creates are compatible
 * with each other (e.g. an economy interior always pairs with an economy
 * exterior).</p>
 *
 * <h3>How it differs from Factory Method</h3>
 * <ul>
 *   <li><b>Factory Method</b> &mdash; creates a <i>single</i> product.</li>
 *   <li><b>Abstract Factory</b> &mdash; creates a <i>family</i> of related
 *       products.</li>
 * </ul>
 *
 * <h3>Real-world analogy</h3>
 * <p>A car manufacturer (factory) produces a matching set of interior +
 * exterior parts. An economy factory produces budget parts; a luxury factory
 * produces premium parts. The assembly line (client) never needs to know which
 * tier it is building &mdash; it just calls the factory methods.</p>
 *
 * <p>Run this class to see both economy and luxury families in action.</p>
 */
public class abstractFactoryDemo {

    public static void main(String[] args) {
        System.out.println("===== Abstract Factory Demo =====\n");

        // Economy family
        System.out.println("--- Economy Car ---");
        carFactory economyFactory = carFactoryProvider.getCarFactory("economy");
        carInterior economyInterior = economyFactory.createInterior();
        carExterior economyExterior = economyFactory.createExterior();
        economyInterior.createInterior();
        economyExterior.createExterior();

        // Luxury family
        System.out.println("\n--- Luxury Car ---");
        carFactory luxuryFactory = carFactoryProvider.getCarFactory("luxury");
        carInterior luxuryInterior = luxuryFactory.createInterior();
        carExterior luxuryExterior = luxuryFactory.createExterior();
        luxuryInterior.createInterior();
        luxuryExterior.createExterior();

        System.out.println("\nKey benefit: Swapping the factory changes the ENTIRE product family.");
    }
}
