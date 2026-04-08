package com.lld.creationalPatterns.ObjectPool.problem;

import com.lld.creationalPatterns.ObjectPool.resource.DBConnection;

/**
 * <h2>Object Pool Pattern &mdash; Problem</h2>
 *
 * <p><b>Pattern:</b> Object Pool (Creational)</p>
 *
 * <h3>Problem</h3>
 * <p>Without a pool, every request creates a <b>new</b> DBConnection and
 * throws it away after use. This is extremely wasteful because:</p>
 * <ul>
 *   <li>Connection creation is expensive (network handshake, authentication).</li>
 *   <li>Under heavy load, thousands of connections may be created
 *       simultaneously, exhausting system resources.</li>
 *   <li>There is no upper bound on the number of open connections.</li>
 * </ul>
 *
 * <p>See the {@code solution} package for the pool-based fix.</p>
 *
 * @see com.lld.creationalPatterns.ObjectPool.solution.DBConnectionPoolManager
 */
public class DBConnectionPoolManager {

    /**
     * Creates a brand-new connection for every call &mdash; expensive and
     * unbounded.
     *
     * @return a new DBConnection (never reused)
     */
    public DBConnection getConnection() {
        System.out.println("[Problem] Creating a NEW connection every time...");
        return new DBConnection();
    }
}
