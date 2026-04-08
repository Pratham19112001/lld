package com.lld.creationalPatterns.factory;

/**
 * <h2>Factory Pattern &mdash; Shape Type Enum</h2>
 *
 * <p><b>Pattern:</b> Factory (Creational)</p>
 *
 * <p>Type-safe enumeration of all available shapes. Used by the Simple Factory
 * to decide which concrete {@link Shape} to instantiate.</p>
 */
public enum ShapeType {
    CIRCLE,
    RECTANGLE,
    SQUARE
}
