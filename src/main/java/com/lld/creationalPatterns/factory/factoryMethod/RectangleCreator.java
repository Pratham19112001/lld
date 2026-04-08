package com.lld.creationalPatterns.factory.factoryMethod;

import com.lld.creationalPatterns.factory.Rectangle;
import com.lld.creationalPatterns.factory.Shape;

/**
 * <h2>Factory Method &mdash; Concrete Creator for Rectangle</h2>
 *
 * <p><b>Pattern:</b> Factory Method (Creational)</p>
 *
 * <p>This creator always produces a {@link Rectangle}.</p>
 */
public class RectangleCreator implements ShapeFactory {

    @Override
    public Shape createShape() {
        return new Rectangle();
    }
}
