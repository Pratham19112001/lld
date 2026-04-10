package com.lld.creationalPatterns.ObjectPool.solution;

import com.lld.creationalPatterns.ObjectPool.resource.DBConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Object Pool Pattern &mdash; Solution (Singleton Connection Pool)</h2>
 *
 * <p><b>Pattern:</b> Object Pool + Singleton (Creational)</p>
 *
 * <h3>What is the Object Pool Pattern?</h3>
 * <p>Object Pool maintains a set of <b>pre-initialized, reusable</b> objects.
 * Instead of creating and destroying expensive objects on every request, the
 * pool lends them out and takes them back when the client is done.</p>
 *
 * <h3>Problem &rarr; Solution</h3>
 * <ul>
 *   <li><b>Problem:</b> Creating a new {@link DBConnection} for every request
 *       is expensive (network handshake, authentication). Under load, unbounded
 *       creation exhausts system resources.
 *       (see {@link com.lld.creationalPatterns.ObjectPool.problem.DBConnectionPoolManager})</li>
 *   <li><b>Solution:</b> A pool pre-creates {@code INITIAL_POOL_SIZE}
 *       connections. Clients borrow via {@link #getDBConnection()} and return
 *       via {@link #releaseDBConnection(DBConnection)}. The pool grows on
 *       demand up to {@code MAX_POOL_SIZE}, then refuses new requests.</li>
 * </ul>
 *
 * <h3>Why Singleton?</h3>
 * <p>The pool itself is a singleton (double-checked locking with
 * {@code synchronized}) so that the entire application shares one pool
 * instance and one bounded set of connections.</p>
 *
 * <h3>Thread Safety</h3>
 * <p>{@code getDBConnection()} and {@code releaseDBConnection()} are
 * {@code synchronized}, ensuring that the free/in-use lists are never
 * modified concurrently.</p>
 *
 * @see com.lld.creationalPatterns.ObjectPool.problem.DBConnectionPoolManager
 * @see DBConnection
 */
public class DBConnectionPoolManager {

    private static DBConnectionPoolManager dbConnectionPoolManagerInstance = null;

    private final List<DBConnection> freeConnections = new ArrayList<>();
    private final List<DBConnection> inUseConnections = new ArrayList<>();
    private final int INITIAL_POOL_SIZE = 3;
    private final int MAX_POOL_SIZE = 6;

    private DBConnectionPoolManager() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            freeConnections.add(new DBConnection());
        }
    }

    /**
     * Returns the singleton pool instance using double-checked locking.
     *
     * @return the single {@code DBConnectionPoolManager} instance
     */
    public static DBConnectionPoolManager getInstance() {
        if (dbConnectionPoolManagerInstance == null) {
            // why synchronized is used ?
            //  it is used to lock a class as there can be a case 2 threads entering this condition so both can create an instance of it so to block that we use this
            // thread 1 = enters after seeing as null
            // thread 2 = enters after seeing as null both can create an instance
            synchronized (DBConnectionPoolManager.class)
            {
                if (dbConnectionPoolManagerInstance == null)
                {
                    dbConnectionPoolManagerInstance = new DBConnectionPoolManager();
                }
            }
        }
        return dbConnectionPoolManagerInstance;
    }

    //steps

    // Step 1
    // T1 → sees null → enters
    // T2 → sees null → enters

    // Step 2
    // T1 → enters synchronized
    // T2 → waits

    // Step 3
    // T1 → second check → still null → creates object ✅
    // T1 → exits

    // Step 4
    // T2 → enters synchronized

    // Step 5
    // if (instance == null)  // ❌ false
    // T2 DOES NOT create new object ✅

    // Step 6
    // Only ONE object created ✔

    // Timeline
    // T1: check null ✔ → lock → check null ✔ → create → unlock
    // T2: check null ✔ → wait → lock → check null ❌ → skip

    /**
     * Borrows a connection from the pool.
     *
     * <p>If the free list is empty but the pool has not reached
     * {@code MAX_POOL_SIZE}, a new connection is created on the fly.
     * If the pool is full, returns {@code null}.</p>
     *
     * @return an available {@link DBConnection}, or {@code null} if the pool is exhausted
     */
    public synchronized DBConnection getDBConnection() {
        if (freeConnections.isEmpty() && inUseConnections.size() < MAX_POOL_SIZE) {
            freeConnections.add(new DBConnection());
            System.out.println("New DBConnection created and added to freeConnections list.");
            System.out.println("freeConnections size: " + freeConnections.size());
            System.out.println("inUseConnections size: " + inUseConnections.size());
        } else if (freeConnections.isEmpty() && inUseConnections.size() >= MAX_POOL_SIZE) {
            System.out.println("Pool is full. Cannot create new DBConnection.");
            return null;
        }

        DBConnection dbConnection = freeConnections.remove(freeConnections.size() - 1);
        inUseConnections.add(dbConnection);
        System.out.println("DBConnection retrieved from freeConnections list and added to inUseConnections list.");
        System.out.println("freeConnections size: " + freeConnections.size());
        System.out.println("inUseConnections size: " + inUseConnections.size());
        return dbConnection;
    }

    /**
     * Returns a connection back to the pool for reuse.
     *
     * @param dbConnection the connection to release (ignored if {@code null})
     */
    public synchronized void releaseDBConnection(DBConnection dbConnection) {
        if (dbConnection != null) {
            inUseConnections.remove(dbConnection);
            freeConnections.add(dbConnection);
            System.out.println("DBConnection released from inUseConnections list and added to freeConnections list.");
            System.out.println("freeConnections size: " + freeConnections.size());
            System.out.println("inUseConnections size: " + inUseConnections.size());
        } else {
            System.out.println("DBConnection is null. Cannot release.");
        }
    }
}
