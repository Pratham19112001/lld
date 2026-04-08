package com.lld.creationalPatterns.singleton;

/**
 * <h2>Singleton Pattern &mdash; Double-Checked Locking (DCL)</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <p><b>Intent:</b> Combine lazy initialization with minimal synchronization
 * overhead by checking the instance twice &mdash; once without a lock and once
 * inside the synchronized block.</p>
 *
 * <h3>Approach</h3>
 * <ol>
 *   <li>First check: if instance is not null, return it immediately (no lock).</li>
 *   <li>If null, enter a synchronized block and check again (second check).</li>
 *   <li>Create the instance only if it is still null after acquiring the lock.</li>
 * </ol>
 *
 * <h3>Problem</h3>
 * <p>Without the {@code volatile} keyword on the instance field, the JVM is
 * free to reorder instructions during object construction. Thread A may publish
 * a partially-constructed object reference that Thread B then reads, leading to
 * subtle bugs. See the {@code dclBugFix} sub-package for the corrected version
 * using {@code volatile}.</p>
 *
 * <h3>Pros</h3>
 * <ul>
 *   <li>Lazy initialization</li>
 *   <li>Synchronization cost paid only on the first access</li>
 * </ul>
 *
 * <h3>Cons</h3>
 * <ul>
 *   <li><b>Broken without {@code volatile}</b> &mdash; instruction reordering
 *       can expose a half-constructed object</li>
 *   <li>More complex than other approaches</li>
 * </ul>
 *
 * @see dclBugFix.DBConnectionDoubleCheckedLockIssue
 * @see dclBugFix.DBConnectionDoubleCheckedLockFix
 */
public class DBConnectionDoubleLocking {

    private static DBConnectionDoubleLocking instance;

    private final String url;
    private final int port;

    private DBConnectionDoubleLocking() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
        System.out.println("DBConnectionDoubleLocking: Connection created (DCL without volatile)");
    }

    /**
     * Returns the single instance using double-checked locking.
     *
     * <p><b>Warning:</b> This version is broken without {@code volatile}.
     * Another thread may see a non-null but partially constructed instance.</p>
     *
     * @return the singleton instance
     */
    public static DBConnectionDoubleLocking getInstance() {
        if (instance == null) {
            synchronized (DBConnectionDoubleLocking.class) {
                if (instance == null) {
                    instance = new DBConnectionDoubleLocking();
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
        return "DBConnectionDoubleLocking{url='" + url + "', port=" + port + "}";
    }
}
