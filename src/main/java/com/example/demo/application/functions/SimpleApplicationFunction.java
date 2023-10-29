package com.example.demo.application.functions;

import java.util.function.Consumer;

/**
 * An abstract class defining the structure for a simple application function, providing a next() to chain methods.
 *
 * @param <T> The type of the model object used within the functions.
 */
public abstract class SimpleApplicationFunction<T> extends ApplicationFunction<T> {


    private Consumer<T> nextFunction = (model) -> {
    };

    /**
     * Initializing the model object.
     *
     * @param model The model object used within the function.
     */
    public SimpleApplicationFunction<T> init(T model) {
        this.model = model;
        initialized = true;
        return this;
    }

    /**
     * Executes the function, including the logic, validation, and defined functions for true, false, and always cases.
     */

    public final void execute() {

        if (!initialized) {
            throw new RuntimeException("Method is not initialized, call init() before execute()");
        }
        this.model = function(this.model); // Save the result in the model variable
        nextFunction.accept(this.model);
    }

    /**
     * Defines the logic within the function. Modifies the model value and returns the modified object for succeeding functions.
     *
     * @param to The input model.
     * @return Modified model object (T).
     */
    public abstract T function(T to);

    /**
     * Defines the validation within the function.
     */


    public SimpleApplicationFunction<T> next(Consumer<T> nextFunction) {
        this.nextFunction = nextFunction;
        return this;
    }


}
