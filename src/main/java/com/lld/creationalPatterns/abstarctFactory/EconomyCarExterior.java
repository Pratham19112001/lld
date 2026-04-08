package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Product: Economy Car Exterior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Provides an economy-grade exterior: steel body, basic paint, standard
 * headlights.</p>
 */
public class EconomyCarExterior implements carExterior {

    @Override
    public void createExterior() {
        System.out.println("Economy Exterior: Steel body, Basic paint, Standard headlights");
    }
}
