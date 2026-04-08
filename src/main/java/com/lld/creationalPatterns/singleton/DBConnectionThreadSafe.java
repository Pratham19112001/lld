package com.lld.creationalPatterns.singleton;

/**
 * <h2>Singleton Pattern &mdash; Thread-Safe (Synchronized Method)</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <p><b>Intent:</b> Make the lazy-initialization approach thread-safe by
 * synchronizing the entire {@link #getInstance()} method.</p>
 *
 * <h3>Approach</h3>
 * <p>The {@code synchronized} keyword on the method ensures that only one
 * thread at a time can execute {@code getInstance()}, preventing the race
 * condition found in {@link DBConnectionLazy}.</p>
 *
 * <h3>Problem</h3>
 * <p>Although this fixes the thread-safety issue, it introduces a
 * <b>performance bottleneck</b>: every call to {@code getInstance()} acquires
 * the class-level lock, even after the instance has been created. In
 * high-throughput applications this unnecessary locking hurts performance.</p>
 *
 * <h3>Pros</h3>
 * <ul>
 *   <li>Thread-safe &mdash; guaranteed single instance</li>
 *   <li>Lazy initialization</li>
 * </ul>
 *
 * <h3>Cons</h3>
 * <ul>
 *   <li>High synchronization overhead on every call</li>
 *   <li>Performance degrades under heavy concurrent access</li>
 * </ul>
 *
 * @see DBConnectionDoubleLocking
 */
public class DBConnectionThreadSafe {

    private static DBConnectionThreadSafe instance;

    private final String url;
    private final int port;

    private DBConnectionThreadSafe() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
        System.out.println("DBConnectionThreadSafe: Connection created (synchronized)");
    }

    /**
     * Returns the single instance. Thread-safe but slow because the entire
     * method is synchronized.
     *
     * @return the singleton instance
     */
    public static synchronized DBConnectionThreadSafe getInstance() {
        if (instance == null) {
            instance = new DBConnectionThreadSafe();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "DBConnectionThreadSafe{url='" + url + "', port=" + port + "}";
    }
}
