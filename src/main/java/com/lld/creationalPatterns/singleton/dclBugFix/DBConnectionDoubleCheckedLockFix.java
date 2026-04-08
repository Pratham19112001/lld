package com.lld.creationalPatterns.singleton.dclBugFix;

/**
 * <h2>Singleton &mdash; Double-Checked Locking (FIXED, with volatile)</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <h3>Solution</h3>
 * <p>Adding {@code volatile} to the {@code instance} field prevents instruction
 * reordering. The JVM inserts a <i>memory barrier</i> that guarantees:</p>
 * <ul>
 *   <li>All writes to the object's fields are completed <b>before</b> the
 *       reference is published to {@code instance}.</li>
 *   <li>Any thread that reads a non-null {@code instance} is guaranteed to see
 *       a fully constructed object.</li>
 * </ul>
 *
 * <h3>Why this is the recommended approach</h3>
 * <ul>
 *   <li>Lazy initialization &mdash; instance created only on first use</li>
 *   <li>Thread-safe &mdash; volatile + synchronized block</li>
 *   <li>High performance &mdash; lock acquired only once (on first creation)</li>
 * </ul>
 *
 * @see DBConnectionDoubleCheckedLockIssue
 */
public class DBConnectionDoubleCheckedLockFix {

    /** {@code volatile} ensures happens-before ordering for this reference. */
    private static volatile DBConnectionDoubleCheckedLockFix instance;

    private final String url;
    private final int port;

    private DBConnectionDoubleCheckedLockFix() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
    }

    /**
     * Returns the single instance using double-checked locking with
     * {@code volatile}. This is the correct and recommended approach.
     *
     * @return the singleton instance
     */
    public static DBConnectionDoubleCheckedLockFix getInstance() {
        if (instance == null) {
            synchronized (DBConnectionDoubleCheckedLockFix.class) {
                if (instance == null) {
                    instance = new DBConnectionDoubleCheckedLockFix();
                }
            }
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
        return "DBConnectionDoubleCheckedLockFix{url='" + url + "', port=" + port + "}";
    }
}
