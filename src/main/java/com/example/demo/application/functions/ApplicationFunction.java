package com.example.demo.application.functions;

public interface ApplicationFunction<T> {
    /**
     * Override the method to return a boolean to define what ApplicationFunction should be executed next in the BusinessFlow.
     */
    T execute();
}
