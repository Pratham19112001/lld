package com.lld.hashmap;

public class HashMapDemo {

    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>();

        System.out.println("=== Custom HashMap Demo ===\n");

        System.out.println("1. Adding elements:");
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Cherry", 30);
        map.display();

        System.out.println("2. Getting elements:");
        System.out.println("  get('Apple') = " + map.get("Apple"));
        System.out.println("  get('Mango') = " + map.get("Mango"));

        System.out.println("\n3. Updating existing key:");
        map.put("Apple", 100);
        System.out.println("  put('Apple', 100)");
        System.out.println("  get('Apple') = " + map.get("Apple"));

        System.out.println("\n4. Removing element:");
        System.out.println("  remove('Cherry') = " + map.remove("Cherry"));
        map.display();

        System.out.println("5. Testing collisions (capacity 4):");
        MyHashMap<Integer, String> intMap = new MyHashMap<>();
        intMap.put(1, "One");
        intMap.put(5, "Five");
        intMap.put(9, "Nine");
        System.out.println("  Keys 1, 5, 9 may collide");
        intMap.display();

        System.out.println("=== Demo Complete ===");
    }
}
