package com.example.demo.application.functions;

/**
 * An abstract class defining the structure for an application function.
 *
 * @param <T> The type of the model object used within the functions.
 */
abstract class ApplicationFunction<T> {

    protected T model;
    boolean initialized = false;

    /**
     * Initializing the model object.
     *
     * @param model The model object used within the function.
     */
    public ApplicationFunction<T> init(T model) {
        this.model = model;
        initialized = true;
        return this;
    }

}
