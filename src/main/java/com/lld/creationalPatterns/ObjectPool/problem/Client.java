package com.lld.creationalPatterns.ObjectPool.problem;

import com.lld.creationalPatterns.ObjectPool.resource.DBConnection;

/**
 * <h2>Object Pool Pattern &mdash; Problem Demo</h2>
 *
 * <p><b>Pattern:</b> Object Pool (Creational)</p>
 *
 * <h3>Problem demonstrated</h3>
 * <p>Each call to {@code getConnection()} creates a new connection object.
 * Under real-world load this quickly exhausts resources.</p>
 *
 * <p>Compare with the solution at
 * {@link com.lld.creationalPatterns.ObjectPool.solution.Client}.</p>
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("===== Object Pool — PROBLEM =====\n");

        DBConnectionPoolManager manager = new DBConnectionPoolManager();

        for (int i = 0; i < 5; i++) {
            DBConnection conn = manager.getConnection();
            conn.executeQuery("SELECT * FROM users WHERE id = " + i);
            // Connection is discarded — never reused
        }

        System.out.println("\nProblem: 5 new connections created and discarded.");
    }
}
