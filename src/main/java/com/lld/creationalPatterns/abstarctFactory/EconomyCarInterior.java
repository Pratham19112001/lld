package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Product: Economy Car Interior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Provides an economy-grade interior: fabric seats, manual AC, basic
 * infotainment.</p>
 */
public class EconomyCarInterior implements carInterior {

    @Override
    public void createInterior() {
        System.out.println("Economy Interior: Fabric seats, Manual AC, Basic Infotainment");
    }
}
