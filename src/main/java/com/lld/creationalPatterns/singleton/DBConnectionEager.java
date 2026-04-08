package com.lld.creationalPatterns.singleton;

/**
 * <h2>Singleton Pattern &mdash; Eager Initialization</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <p><b>Intent:</b> Ensure a class has only one instance and provide a global
 * point of access to it.</p>
 *
 * <h3>Approach &mdash; Eager Initialization</h3>
 * <p>The instance is created at class-loading time, even if it is never used.
 * This is the simplest approach and is inherently thread-safe because the JVM
 * guarantees that static initializers run exactly once in a thread-safe manner.</p>
 *
 * <h3>Pros</h3>
 * <ul>
 *   <li>Thread-safe without any synchronization overhead</li>
 *   <li>Extremely simple to implement</li>
 * </ul>
 *
 * <h3>Cons</h3>
 * <ul>
 *   <li>Instance is created even if the application never uses it (wastes memory
 *       if creation is expensive and usage is conditional)</li>
 *   <li>No way to handle creation exceptions gracefully</li>
 * </ul>
 *
 * @see DBConnectionLazy
 * @see DBConnectionThreadSafe
 * @see DBConnectionDoubleLocking
 */
public class DBConnectionEager {

    private static final DBConnectionEager INSTANCE = new DBConnectionEager();

    private final String url;
    private final int port;

    private DBConnectionEager() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
        System.out.println("DBConnectionEager: Connection created eagerly");
    }

    /**
     * Returns the single instance of DBConnectionEager.
     *
     * @return the singleton instance
     */
    public static DBConnectionEager getInstance() {
        return INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "DBConnectionEager{url='" + url + "', port=" + port + "}";
    }
}
