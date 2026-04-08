package com.lld.creationalPatterns.factory.simplefactory;

import com.lld.creationalPatterns.factory.Shape;
import com.lld.creationalPatterns.factory.ShapeType;

/**
 * <h2>Simple Factory &mdash; Demo</h2>
 *
 * <p><b>Pattern:</b> Simple Factory (Creational)</p>
 *
 * <h3>Problem &rarr; Solution context</h3>
 * <p>This demo shows the <b>Simple Factory</b> approach where a single static
 * method decides which concrete class to instantiate. It solves the problem
 * of clients depending on concrete classes, but it still violates the
 * <b>Open/Closed Principle</b> because adding a new shape requires modifying
 * the factory.</p>
 *
 * <p>For the OCP-compliant approach, see the Factory Method demo at
 * {@link com.lld.creationalPatterns.factory.factoryMethod.factoryMethodDemo}.</p>
 */
public class SimpleFactoryDemo {

    public static void main(String[] args) {
        System.out.println("===== Simple Factory Demo =====\n");

        Shape circle    = ShapeFactory.createShape(ShapeType.CIRCLE);
        Shape rectangle = ShapeFactory.createShape(ShapeType.RECTANGLE);
        Shape square    = ShapeFactory.createShape(ShapeType.SQUARE);

        circle.draw();
        rectangle.draw();
        square.draw();

        System.out.println("\nProblem: Adding a new shape requires changing ShapeFactory.");
    }
}
