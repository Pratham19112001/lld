package com.lld.creationalPatterns.factory;

/**
 * <h2>Factory Pattern &mdash; Concrete Product: Circle</h2>
 *
 * <p><b>Pattern:</b> Factory (Creational)</p>
 *
 * <p>Concrete implementation of {@link Shape}. Created by the factory;
 * the client never calls {@code new Circle()} directly.</p>
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }

    @Override
    public void computeArea() {
        System.out.println("Circle area: PI * r * r");
    }
}
