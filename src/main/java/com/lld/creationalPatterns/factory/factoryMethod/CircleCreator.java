package com.lld.creationalPatterns.factory.factoryMethod;

import com.lld.creationalPatterns.factory.Circle;
import com.lld.creationalPatterns.factory.Shape;

/**
 * <h2>Factory Method &mdash; Concrete Creator for Circle</h2>
 *
 * <p><b>Pattern:</b> Factory Method (Creational)</p>
 *
 * <p>This creator always produces a {@link Circle}. To add a new shape,
 * create a new creator class &mdash; no existing code needs to change.</p>
 */
public class CircleCreator implements ShapeFactory {

    @Override
    public Shape createShape() {
        return new Circle();
    }
}
