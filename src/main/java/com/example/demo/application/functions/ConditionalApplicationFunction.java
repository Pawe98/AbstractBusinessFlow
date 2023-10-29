package com.example.demo.application.functions;

import java.util.function.Consumer;

/**
 * An abstract class defining the structure for a conditional application function, encapsulating the logic and execution flow.
 *
 * @param <T> The type of the model object used within the functions.
 */
public abstract class ConditionalApplicationFunction<T> extends ApplicationFunction<T> {

    private Consumer<T> trueFunction = (model) -> {
    };
    private Consumer<T> falseFunction = (model) -> {
    };
    private Consumer<T> nextFunction = (model) -> {
    };

    /**
     * Initializing the model object.
     *
     * @param model The model object used within the function.
     */
    public ConditionalApplicationFunction<T> init(T model) {
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

        this.model = function(model); // Save the result in the model variable
        if (trueFalseLogic()) { // call defined .isTrue(), .isFalse and .always() functions.
            trueFunction.accept(this.model);
        } else {
            falseFunction.accept(this.model);
        }
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
     * Defines logic that decides if isTrue or isFalse should be called.
     */
    public abstract boolean trueFalseLogic();

    public ConditionalApplicationFunction<T> isTrue(Consumer<T> trueFunction) {
        this.trueFunction = trueFunction;
        return this;
    }

    public ConditionalApplicationFunction<T> isFalse(Consumer<T> falseFunction) {
        this.falseFunction = falseFunction;
        return this;
    }

    public ConditionalApplicationFunction<T> next(Consumer<T> nextFunction) {
        this.nextFunction = nextFunction;
        return this;
    }


}
