package com.lld.creationalPatterns.ObjectPool.resource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>Object Pool Pattern &mdash; Pooled Resource</h2>
 *
 * <p><b>Pattern:</b> Object Pool (Creational)</p>
 *
 * <p>{@code DBConnection} represents an expensive-to-create resource (a
 * database connection). Creating and destroying connections on every request
 * wastes time and memory. The Object Pool pattern reuses a fixed set of
 * pre-created connections instead.</p>
 */
public class DBConnection {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;

    public DBConnection() {
        this.id = ID_GENERATOR.getAndIncrement();
        simulateExpensiveCreation();
    }

    private void simulateExpensiveCreation() {
        System.out.println("  [Expensive] Creating DBConnection-" + id);
    }

    public int getId() {
        return id;
    }

    /**
     * Simulates executing a query on this connection.
     *
     * @param query the SQL query to execute
     */
    public void executeQuery(String query) {
        System.out.println("  DBConnection-" + id + " executing: " + query);
    }

    @Override
    public String toString() {
        return "DBConnection{id=" + id + "}";
    }
}
