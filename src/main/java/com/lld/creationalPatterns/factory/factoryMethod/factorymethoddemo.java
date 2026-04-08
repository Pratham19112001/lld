package com.lld.creationalPatterns.factory.factoryMethod;

import com.lld.creationalPatterns.factory.Shape;
import com.lld.creationalPatterns.factory.ShapeType;

/**
 * <h2>Factory Method Pattern &mdash; Demo</h2>
 *
 * <p><b>Pattern:</b> Factory Method (Creational)</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem (Simple Factory):</b> A single factory class uses a switch
 *       to decide which object to create. Adding a new product means modifying
 *       the factory, violating the Open/Closed Principle.</li>
 *   <li><b>Solution (Factory Method):</b> Each product type gets its own
 *       creator class. Adding a new shape means adding a new creator &mdash;
 *       <b>zero changes</b> to existing code.</li>
 * </ul>
 */
public class factorymethoddemo {

    public static void main(String[] args) {
        System.out.println("======= Factory Method Design Pattern ======");

        ShapeType shapeType = ShapeType.SQUARE;
        Shape shape = getShapeInstance(shapeType);
        shape.draw();
        shape.computeArea();
    }

    private static Shape getShapeInstance(ShapeType shapeType) {
        if (shapeType == null) {
            return null;
        }
        ShapeFactory creator = switch (shapeType) {
            case CIRCLE    -> new CircleCreator();
            case RECTANGLE -> new RectangleCreator();
            case SQUARE    -> new SqaureCreator();
        };
        return creator.createShape();
    }
}
