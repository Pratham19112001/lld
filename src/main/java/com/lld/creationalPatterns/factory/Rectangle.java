package com.lld.creationalPatterns.factory;

/**
 * <h2>Factory Pattern &mdash; Concrete Product: Rectangle</h2>
 *
 * <p><b>Pattern:</b> Factory (Creational)</p>
 *
 * <p>Concrete implementation of {@link Shape}. Created by the factory;
 * the client never calls {@code new Rectangle()} directly.</p>
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }

    @Override
    public void computeArea() {
        System.out.println("Rectangle area: length * breadth");
    }
}
