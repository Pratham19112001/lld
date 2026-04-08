package com.lld.creationalPatterns.factory;

/**
 * <h2>Factory Pattern &mdash; Product Interface</h2>
 *
 * <p><b>Pattern:</b> Factory (Creational)</p>
 *
 * <p>{@code Shape} is the common product interface. All concrete shapes
 * (Circle, Rectangle, Square) implement this interface so that the factory
 * can return them through a single abstraction.</p>
 *
 * <p>The client programs to this interface, never to a concrete shape class,
 * which makes the code open for extension and closed for modification.</p>
 */
public interface Shape {

    /**
     * Draws this shape (simulated with console output).
     */
    void draw();

    /**
     * Computes and prints the area of this shape.
     */
    void computeArea();
}
