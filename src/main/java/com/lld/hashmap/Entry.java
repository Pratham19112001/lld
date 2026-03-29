package com.lld.hashmap;

public class Entry<K, V> {

    K key;
    V value;
    Entry<K, V> next;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
