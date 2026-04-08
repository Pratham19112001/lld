package com.lld.creationalPatterns.factory;

/**
 * <h2>Factory Pattern &mdash; Concrete Product: Square</h2>
 *
 * <p><b>Pattern:</b> Factory (Creational)</p>
 *
 * <p>Concrete implementation of {@link Shape}. Created by the factory;
 * the client never calls {@code new Sqaure()} directly.</p>
 */
public class Sqaure implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing a Square");
    }

    @Override
    public void computeArea() {
        System.out.println("Square area: side * side");
    }
}
