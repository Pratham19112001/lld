package com.lld.creationalPatterns.factory.factoryMethod;

import com.lld.creationalPatterns.factory.Shape;

/**
 * <h2>Factory Method Pattern &mdash; Creator (Abstract Factory)</h2>
 *
 * <p><b>Pattern:</b> Factory Method (Creational)</p>
 *
 * <h3>What is Factory Method?</h3>
 * <p>Factory Method defines an interface for creating an object but lets
 * <b>subclasses</b> decide which class to instantiate. Each concrete creator
 * overrides the factory method to produce a specific product.</p>
 *
 * <h3>Solution &mdash; why this is better than Simple Factory</h3>
 * <p>Adding a new shape requires only a <b>new creator subclass</b>; the
 * existing code is never modified. This satisfies the <b>Open/Closed
 * Principle</b>.</p>
 *
 * <h3>Participants</h3>
 * <ul>
 *   <li><b>Creator</b> (this interface) &mdash; declares the factory method</li>
 *   <li><b>ConcreteCreator</b> ({@link CircleCreator}, {@link RectangleCreator},
 *       {@link SqaureCreator}) &mdash; implements the factory method</li>
 *   <li><b>Product</b> ({@link Shape}) &mdash; interface of objects created</li>
 *   <li><b>ConcreteProduct</b> (Circle, Rectangle, Sqaure) &mdash; concrete
 *       objects</li>
 * </ul>
 */
public interface ShapeFactory {

    /**
     * Factory method &mdash; subclasses decide which concrete shape to create.
     *
     * @return a new Shape instance
     */
    Shape createShape();
}
