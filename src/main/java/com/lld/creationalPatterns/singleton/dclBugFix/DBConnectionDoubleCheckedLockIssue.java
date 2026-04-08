package com.lld.creationalPatterns.singleton.dclBugFix;

/**
 * <h2>Singleton &mdash; Double-Checked Locking (BROKEN, without volatile)</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <h3>Problem Demonstrated</h3>
 * <p>This class demonstrates the <b>classic DCL bug</b>. Without marking the
 * {@code instance} field as {@code volatile}, the JVM may reorder the
 * instructions inside the constructor:</p>
 * <ol>
 *   <li>Allocate memory for the object.</li>
 *   <li>Assign the reference to {@code instance} (now non-null).</li>
 *   <li>Invoke the constructor (initialize fields).</li>
 * </ol>
 * <p>If step 2 happens before step 3, another thread can see a <b>non-null but
 * uninitialized</b> object, leading to unpredictable behaviour.</p>
 *
 * <h3>Fix</h3>
 * <p>Use {@code volatile} on the instance field. See
 * {@link DBConnectionDoubleCheckedLockFix} for the corrected version.</p>
 *
 * @see DBConnectionDoubleCheckedLockFix
 */
public class DBConnectionDoubleCheckedLockIssue {

    /** Missing {@code volatile} &mdash; this is the bug. */
    private static DBConnectionDoubleCheckedLockIssue instance;

    private String url;
    private int port;

    private DBConnectionDoubleCheckedLockIssue() {
        this.url = "jdbc:mysql://localhost";
        this.port = 3306;
    }

    /**
     * Double-checked locking <b>without volatile</b>.
     * A second thread may see a non-null but partially constructed instance.
     *
     * @return the singleton instance (possibly broken under concurrency)
     */
    public static DBConnectionDoubleCheckedLockIssue getInstance() {
        if (instance == null) {
            synchronized (DBConnectionDoubleCheckedLockIssue.class) {
                if (instance == null) {
                    instance = new DBConnectionDoubleCheckedLockIssue();
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
}
