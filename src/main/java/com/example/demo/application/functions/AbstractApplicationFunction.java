package com.example.demo.application.functions;

import java.util.Optional;

/**
 * An abstract class defining the structure for an application function.
 *
 * @param <T> The type of the model object used within the functions.
 */
public abstract class AbstractApplicationFunction<T> implements ApplicationFunction<T> {

    protected T model;


    /**
     * Initializing the model object.
     *
     * @param model The model object used within the function.
     */
    public AbstractApplicationFunction<T> init(T model) {
        this.model = model;
        return this;
    }


    public Optional<Boolean> trueFalseLogic() {
        return Optional.empty();
    }

    /**
     * Used to create AbstractApplicationFunctions via a lambda expression that
     */
    public abstract T execute();
}
