package com.example.demo.application;

import java.util.function.Consumer;

/**
 * An abstract class defining the structure for an application function, encapsulating the logic and execution flow.
 *
 * @param <T> The type of the model object used within the functions.
 */
public abstract class AbstractApplicationFunction<T> {
    T model;
    private boolean result;

    private Consumer<T> trueFunction = (model) -> {};
    private Consumer<T> falseFunction = (model) -> {};
    private Consumer<T> alwaysFunction = (model) -> {};

    /**
     * Constructor initializing the model object.
     *
     * @param model The model object used within the function.
     */
    public AbstractApplicationFunction(T model) {
        this.model = model;
    }

    /**
     * Executes the function, including the logic, validation, and defined functions for true, false, and always cases.
     */

    public final void execute() {
        model = function(model); // Save the result in the model variable
        result = validate(); //call the validation function
        if (result) { // call defined .isTrue(), .isFalse and .always() functions.
            trueFunction.accept(model);
        } else {
            falseFunction.accept(model);
        }
        alwaysFunction.accept(model);
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
    public abstract boolean validate();

    public AbstractApplicationFunction<T> isTrue(Consumer<T> trueFunction) {
        this.trueFunction = trueFunction;
        return this;
    }

    public AbstractApplicationFunction<T> isFalse(Consumer<T> falseFunction) {
        this.falseFunction = falseFunction;
        return this;
    }

    public AbstractApplicationFunction<T> always(Consumer<T> alwaysFunction) {
        this.alwaysFunction = alwaysFunction;
        return this;
    }


}
