package com.lld.creationalPatterns.singleton;

import com.lld.creationalPatterns.singleton.dclBugFix.DBConnectionDoubleCheckedLockFix;

/**
 * <h2>Singleton Pattern &mdash; Demo</h2>
 *
 * <p><b>Pattern:</b> Singleton (Creational)</p>
 *
 * <p><b>What is Singleton?</b><br>
 * Singleton ensures a class has <b>only one instance</b> and provides a global
 * access point to it. Common use-cases include database connection pools,
 * loggers, and configuration managers.</p>
 *
 * <h3>Variants demonstrated (Problem &rarr; Solution evolution)</h3>
 * <ol>
 *   <li><b>Eager</b> ({@link DBConnectionEager}) &mdash; simplest, but wastes
 *       memory if never used.</li>
 *   <li><b>Lazy</b> ({@link DBConnectionLazy}) &mdash; <b>Problem:</b> not
 *       thread-safe; race condition creates multiple instances.</li>
 *   <li><b>Synchronized method</b> ({@link DBConnectionThreadSafe}) &mdash;
 *       fixes thread safety but adds lock overhead on every call.</li>
 *   <li><b>Double-Checked Locking without volatile</b>
 *       ({@link DBConnectionDoubleLocking}) &mdash; <b>Problem:</b> JVM
 *       instruction reordering can expose a half-built object.</li>
 *   <li><b>Double-Checked Locking with volatile</b>
 *       ({@link DBConnectionDoubleCheckedLockFix}) &mdash; <b>Solution:</b>
 *       the correct, high-performance, thread-safe lazy singleton.</li>
 * </ol>
 *
 * <p>Run this class to see each variant in action.</p>
 */
public class singletonDemo {

    public static void main(String[] args) {
        System.out.println("===== Singleton Pattern Demo =====\n");

        // 1. Eager
        System.out.println("--- Eager Initialization ---");
        DBConnectionEager eager1 = DBConnectionEager.getInstance();
        DBConnectionEager eager2 = DBConnectionEager.getInstance();
        System.out.println("Same instance? " + (eager1 == eager2));
        System.out.println(eager1);

        // 2. Lazy (not thread-safe)
        System.out.println("\n--- Lazy Initialization (NOT thread-safe) ---");
        DBConnectionLazy lazy1 = DBConnectionLazy.getInstance();
        DBConnectionLazy lazy2 = DBConnectionLazy.getInstance();
        System.out.println("Same instance? " + (lazy1 == lazy2));
        System.out.println(lazy1);

        // 3. Thread-safe synchronized
        System.out.println("\n--- Thread-Safe (synchronized method) ---");
        DBConnectionThreadSafe ts1 = DBConnectionThreadSafe.getInstance();
        DBConnectionThreadSafe ts2 = DBConnectionThreadSafe.getInstance();
        System.out.println("Same instance? " + (ts1 == ts2));
        System.out.println(ts1);

        // 4. Double-Checked Locking (volatile fix)
        System.out.println("\n--- Double-Checked Locking (volatile fix) ---");
        DBConnectionDoubleCheckedLockFix dcl1 = DBConnectionDoubleCheckedLockFix.getInstance();
        DBConnectionDoubleCheckedLockFix dcl2 = DBConnectionDoubleCheckedLockFix.getInstance();
        System.out.println("Same instance? " + (dcl1 == dcl2));
        System.out.println(dcl1);

        System.out.println("\n===== All singletons verified =====");
    }
}
