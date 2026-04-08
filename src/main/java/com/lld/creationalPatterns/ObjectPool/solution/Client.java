package com.lld.creationalPatterns.ObjectPool.solution;

import com.lld.creationalPatterns.ObjectPool.resource.DBConnection;

/**
 * <h2>Object Pool Pattern &mdash; Solution Demo</h2>
 *
 * <p><b>Pattern:</b> Object Pool + Singleton (Creational)</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem:</b> Creating a new expensive resource (DB connection) for
 *       every request wastes time and memory, with no upper bound.
 *       (see {@link com.lld.creationalPatterns.ObjectPool.problem.Client})</li>
 *   <li><b>Solution:</b> A singleton pool pre-creates connections and grows on
 *       demand up to a maximum. Clients borrow and return connections.
 *       Creation cost is amortised, and resource usage is bounded.</li>
 * </ul>
 *
 * <p>Run this class to see the pool in action.</p>
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("===== Object Pool — SOLUTION =====\n");

        DBConnectionPoolManager pool = DBConnectionPoolManager.getInstance();

        // Borrow connections (initial pool size = 3)
        DBConnection c1 = pool.getDBConnection();
        DBConnection c2 = pool.getDBConnection();
        DBConnection c3 = pool.getDBConnection();

        // Pool is empty but below MAX, so a new one is created on the fly
        DBConnection c4 = pool.getDBConnection();

        // Use a connection
        if (c1 != null) {
            c1.executeQuery("SELECT * FROM orders");
        }

        // Return a connection
        pool.releaseDBConnection(c1);

        // Now another request can reuse it
        DBConnection c5 = pool.getDBConnection();
        if (c5 != null) {
            c5.executeQuery("INSERT INTO logs VALUES (...)");
            pool.releaseDBConnection(c5);
        }

        // Cleanup
        pool.releaseDBConnection(c2);
        pool.releaseDBConnection(c3);
        pool.releaseDBConnection(c4);

        System.out.println("\nSolution: Connections are reused, creation is bounded.");
    }
}
