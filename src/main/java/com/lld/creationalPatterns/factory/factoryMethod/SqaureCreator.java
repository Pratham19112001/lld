package com.lld.creationalPatterns.factory.factoryMethod;

import com.lld.creationalPatterns.factory.Shape;
import com.lld.creationalPatterns.factory.Sqaure;

/**
 * <h2>Factory Method &mdash; Concrete Creator for Square</h2>
 *
 * <p><b>Pattern:</b> Factory Method (Creational)</p>
 *
 * <p>This creator always produces a {@link Sqaure}.</p>
 */
public class SqaureCreator implements ShapeFactory {

    @Override
    public Shape createShape() {
        return new Sqaure();
    }
}
