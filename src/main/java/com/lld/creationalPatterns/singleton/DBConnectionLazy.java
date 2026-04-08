package com.lld.creationalPatterns.singleton;

/**
 * <h2>Singleton Pattern &mdash; Lazy Initialization</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <p><b>Intent:</b> Delay the creation of the singleton instance until it is
 * first requested, saving resources if the instance is never needed.</p>
 *
 * <h3>Approach &mdash; Lazy Initialization</h3>
 * <p>The instance is created only when {@link #getInstance()} is called for the
 * first time. This avoids wasting memory when the object is never used.</p>
 *
 * <h3>Problem</h3>
 * <p>This implementation is <b>NOT thread-safe</b>. If two threads call
 * {@code getInstance()} simultaneously while the instance is still {@code null},
 * both may enter the {@code if} block and create two separate instances,
 * violating the singleton contract.</p>
 *
 * <h3>Pros</h3>
 * <ul>
 *   <li>Instance is created only when needed (lazy)</li>
 *   <li>Simple to understand</li>
 * </ul>
 *
 * <h3>Cons</h3>
 * <ul>
 *   <li><b>Not thread-safe</b> &mdash; race condition on first access</li>
 *   <li>Unsuitable for multi-threaded applications</li>
 * </ul>
 *
 * @see DBConnectionThreadSafe
 * @see DBConnectionDoubleLocking
 */
public class DBConnectionLazy {

    private static DBConnectionLazy instance;

    private final String url;
    private final int port;

    private DBConnectionLazy() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
        System.out.println("DBConnectionLazy: Connection created lazily");
    }

    /**
     * Returns the single instance. <b>Not thread-safe.</b>
     *
     * <p>Race condition: two threads may both see {@code instance == null} and
     * each create a new object.</p>
     *
     * @return the singleton instance
     */
    public static DBConnectionLazy getInstance() {
        if (instance == null) {
            instance = new DBConnectionLazy();
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
        return "DBConnectionLazy{url='" + url + "', port=" + port + "}";
    }
}
