package com.yu.util.java.erasure;

import java.lang.reflect.Method;

public class Erasure<T> {
    T object;

    public Erasure(T object) {
        this.object = object;
    }

    public void add(T object) {
    }

    public static void main(String[] args) {
        Erasure<String> erasure = new Erasure<>("hello");
        Class eclz = erasure.getClass();
        System.out.println("erasure class is:" + eclz.getName());
        Method[] methods = eclz.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(" method:" + m.toString());
        }
    }
}
