package com.lld.creationalPatterns.factory.simplefactory;

import com.lld.creationalPatterns.factory.*;

/**
 * <h2>Simple Factory</h2>
 *
 * <p><b>Pattern:</b> Simple Factory (Creational)</p>
 *
 * <h3>What is Simple Factory?</h3>
 * <p>Simple Factory is <b>not</b> a formal GoF design pattern but a common
 * idiom. A single factory class contains a static (or instance) method with a
 * {@code switch}/{@code if-else} that decides which concrete product to
 * create.</p>
 *
 * <h3>Problem</h3>
 * <p>Every time a new shape is added, the factory's {@code switch} must be
 * modified, violating the <b>Open/Closed Principle</b>. For the OCP-compliant
 * version, see the Factory Method approach in the {@code factoryMethod}
 * sub-package.</p>
 *
 * <h3>Pros</h3>
 * <ul>
 *   <li>Centralises object creation in one place</li>
 *   <li>Client is decoupled from concrete classes</li>
 * </ul>
 *
 * <h3>Cons</h3>
 * <ul>
 *   <li>Violates Open/Closed Principle &mdash; must modify factory for new
 *       types</li>
 *   <li>Can become a "God class" as the number of products grows</li>
 * </ul>
 *
 * @see com.lld.creationalPatterns.factory.factoryMethod.ShapeFactory
 */
public class ShapeFactory {

    /**
     * Creates a {@link Shape} based on the given type.
     *
     * @param type the desired shape type
     * @return a concrete Shape instance
     * @throws IllegalArgumentException if the type is unknown
     */
    public static Shape createShape(ShapeType type) {
        return switch (type) {
            case CIRCLE    -> new Circle();
            case RECTANGLE -> new Rectangle();
            case SQUARE    -> new Sqaure();
        };
    }
}
