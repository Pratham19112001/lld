package com.lld.creationalPatterns.abstarctFactory;

/**
 * <h2>Abstract Factory &mdash; Factory Provider</h2>
 *
 * <p><b>Pattern:</b> Abstract Factory (Creational)</p>
 *
 * <p>Acts as a <b>factory of factories</b>. Given a car tier (e.g. "economy"
 * or "luxury"), it returns the appropriate concrete factory. This keeps the
 * client completely decoupled from concrete factory classes.</p>
 */
public class carFactoryProvider {

    /**
     * Returns the appropriate car factory for the given tier.
     *
     * @param type car tier &mdash; "economy" or "luxury"
     * @return the matching concrete factory
     * @throws IllegalArgumentException if the type is unknown
     */
    public static carFactory getCarFactory(String type) {
        return switch (type.toLowerCase()) {
            case "economy" -> new EconomyCarFactory();
            case "luxury"  -> new LuxuryCarFactory();
            default -> throw new IllegalArgumentException("Unknown car type: " + type);
        };
    }
}
