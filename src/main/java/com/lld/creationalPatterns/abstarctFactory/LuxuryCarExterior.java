package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Concrete Product: Luxury Car Exterior</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Provides a luxury-grade exterior: aluminium body, metallic paint,
 * LED matrix headlights.</p>
 */
public class LuxuryCarExterior implements carExterior {

    @Override
    public void createExterior() {
        System.out.println("Luxury Exterior: Aluminium body, Metallic paint, LED matrix headlights");
    }
}
