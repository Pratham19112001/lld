package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Product: Luxury Car Interior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Provides a luxury-grade interior: leather seats, climate control,
 * premium infotainment with surround sound.</p>
 */
public class LuxuryCarInterior implements carInterior {

    @Override
    public void createInterior() {
        System.out.println("Luxury Interior: Leather seats, Climate control, Surround sound");
    }
}
