//package com.example.demo.application.functions;
//
//import java.util.function.Consumer;
//import java.util.function.Function;
//
///**
// * An abstract class defining the structure for a conditional application function, encapsulating the logic and execution flow.
// *
// * @param <T> The type of the model object used within the functions.
// */
//public abstract class ConditionalApplicationFunction<T> extends ApplicationFunction<T> {
//
//    private Consumer<T> trueConsumer = (model) -> {
//    };
//    private Consumer<T> falseConsumer = (model) -> {
//    };
//    private Consumer<T> nextConsumer = (model) -> {
//    };
//
//    boolean trueFunctionUpdatesModel;
//    private Function<T,T> trueFunction = (model) -> { return model;
//    };
//    boolean falseFunctionUpdatesModel;
//    private Function<T,T> falseFunction = (model) -> { return model;
//    };
//
//    /**
//     * Initializing the model object.
//     *
//     * @param model The model object used within the function.
//     */
//    public ConditionalApplicationFunction<T> init(T model) {
//        this.model = model;
//        initialized = true;
//        return this;
//    }
//
//    /**
//     * Executes the function, including the logic, validation, and defined functions for true, false, and always cases.
//     */
//
//    public final void execute() {
//
//        if (!initialized) {
//            throw new RuntimeException("Method is not initialized, call init() before execute()");
//        }
//
//        this.model = function(model); // Save the result in the model variable
//        if (trueFalseLogic()) { // call defined .isTrue(), .isFalse and .always() functions.
//            trueConsumer.accept(this.model);
//            this.model = trueFunction.apply(this.model);
//        } else {
//            falseConsumer.accept(this.model);
//            this.model = falseFunction.apply(this.model);
//        }
//        nextConsumer.accept(this.model);
//    }
//
//    /**
//     * Defines the logic within the function. Modifies the model value and returns the modified object for succeeding functions.
//     *
//     * @param to The input model.
//     * @return Modified model object (T).
//     */
//    public abstract T function(T to);
//
//    /**
//     * Defines logic that decides if isTrue or isFalse should be called.
//     */
//    public abstract boolean trueFalseLogic();
//
//    public ConditionalApplicationFunction<T> isTrue(Consumer<T> trueFunction) {
//        this.trueConsumer = trueFunction;
//        return this;
//    }
//
//    public ConditionalApplicationFunction<T> isFalse(Consumer<T> falseFunction) {
//        this.falseConsumer = falseFunction;
//        return this;
//    }
//
//    public ConditionalApplicationFunction<T> next(Consumer<T> nextFunction) {
//        this.nextConsumer = nextFunction;
//        return this;
//    }
//
//
//    public ConditionalApplicationFunction<T> isTrue(Function<T,T> trueFunction, boolean useReturnValue) {
//        this.trueFunction = trueFunction;
//        this.trueFunctionUpdatesModel = useReturnValue;
//        return this;
//    }
//
//    public ConditionalApplicationFunction<T> isFalse(Function<T,T> falseFunction, boolean useReturnValue) {
//        this.falseFunction = falseFunction;
//        this.falseFunctionUpdatesModel = useReturnValue;
//        return this;
//    }
//
//
//}
