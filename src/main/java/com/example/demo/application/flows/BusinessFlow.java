package com.example.demo.application.flows;

import com.example.demo.application.functions.AbstractApplicationFunction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class BusinessFlow<T> {

    private final TreeNode<AbstractApplicationFunction<T>> root;
    @Getter
    T model;
    private TreeNode<AbstractApplicationFunction<T>> current;

    private boolean started = false;
    private boolean terminate = false;
    private TreeNode<AbstractApplicationFunction<T>> previousBuilderMethod;

    private BusinessFlow(T model) {
        this.model = model;
        root = new TreeNode<>(null);
        current = root;
    }

    public static <T> BusinessFlow<T> init(Class<T> tClass) {
        BusinessFlow<T> businessFlow = new BusinessFlow<>(null);
        return businessFlow;
    }

    public static <T> BusinessFlow<T> receive(T model, BusinessFlow<T> businessFlow) {
        businessFlow.setModel(model);
        return businessFlow;
    }

    public BusinessFlow<T> terminate() {
        previousBuilderMethod.setTerminal(true);
        return this;
    }

    private void setModel(T model) {
        this.model = model;
    }

    public BusinessFlow<T> startFunction(AbstractApplicationFunction<T> startFunction) {
        if (started) {
            throw new RuntimeException("Business Flow was already started.");
        }
        started = true;
        nextFunction(startFunction);
        return this;
    }

    public BusinessFlow<T> startFunction(Consumer<T> firstFunction) {
        if (started) {
            throw new RuntimeException("Business Flow was already started.");
        }
        started = true;
        nextFunction(firstFunction);
        return this;
    }

    public BusinessFlow<T> startFunction(Function<T, T> firstFunction, boolean useReturnValue) {
        if (started) {
            throw new RuntimeException("Business Flow was already started.");
        }
        started = true;
        nextFunction(firstFunction, useReturnValue);
        return this;
    }

    public BusinessFlow<T> whenTrueDo(AbstractApplicationFunction<T> trueFunction) {
        TreeNode<AbstractApplicationFunction<T>> trueNode = new TreeNode<>(trueFunction);
        current.setTrueChild(trueNode);
        trueNode.setParent(current);
        previousBuilderMethod = trueNode;
        return this;
    }

    public BusinessFlow<T> whenFalseDo(AbstractApplicationFunction<T> falseFunction) {
        TreeNode<AbstractApplicationFunction<T>> falseNode = new TreeNode<>(falseFunction);
        current.setFalseChild(falseNode);
        falseNode.setParent(current);
        previousBuilderMethod = falseNode;
        return this;
    }

    public BusinessFlow<T> nextFunction(AbstractApplicationFunction<T> nextFunction) {
        TreeNode<AbstractApplicationFunction<T>> nextNode = new TreeNode<>(nextFunction);
        current.setNextChild(nextNode);
        current = nextNode;
        nextNode.setParent(current);
        previousBuilderMethod = nextNode;
        return this;
    }


    public BusinessFlow<T> whenTrueDo(Consumer<T> trueConsumer) {
        TreeNode<AbstractApplicationFunction<T>> trueNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(trueConsumer));
        current.setTrueChild(trueNode);
        trueNode.setParent(current);
        previousBuilderMethod = trueNode;
        return this;
    }

    public BusinessFlow<T> whenFalseDo(Consumer<T> falseConsumer) {
        TreeNode<AbstractApplicationFunction<T>> falseNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(falseConsumer));
        current.setFalseChild(falseNode);
        falseNode.setParent(current);
        previousBuilderMethod = falseNode;
        return this;
    }

    public BusinessFlow<T> nextFunction(Consumer<T> nextConsumer) {
        TreeNode<AbstractApplicationFunction<T>> nextNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(nextConsumer));
        current.setNextChild(nextNode);
        current = nextNode;
        nextNode.setParent(current);
        previousBuilderMethod = nextNode;
        return this;
    }

    public BusinessFlow<T> whenTrueDo(Function<T, T> trueConsumer, boolean useReturnValue) {
        TreeNode<AbstractApplicationFunction<T>> trueNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(trueConsumer, useReturnValue));
        current.setTrueChild(trueNode);
        trueNode.setParent(current);
        previousBuilderMethod = trueNode;
        return this;
    }

    public BusinessFlow<T> whenFalseDo(Function<T, T> falseConsumer, boolean useReturnValue) {
        TreeNode<AbstractApplicationFunction<T>> falseNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(falseConsumer, useReturnValue));
        current.setFalseChild(falseNode);
        falseNode.setParent(current);
        previousBuilderMethod = falseNode;
        return this;
    }

    public BusinessFlow<T> nextFunction(Function<T, T> nextConsumer, boolean useReturnValue) {
        TreeNode<AbstractApplicationFunction<T>> nextNode = new TreeNode<>(new SimpleLambdaApplicationFunction<>(nextConsumer, useReturnValue));
        current.setNextChild(nextNode);
        current = nextNode;
        nextNode.setParent(current);
        previousBuilderMethod = nextNode;
        return this;
    }

    public void executeFlow() {
        executeNode(root.getNextChild());
    }

    private void executeNode(TreeNode<AbstractApplicationFunction<T>> node) {
        if (node != null && !terminate) {
            var applicationFunction = node.getData();
            applicationFunction.init(model);
            this.model = applicationFunction.execute();
            var optionalValidation = applicationFunction.trueFalseLogic();

            if (node.isTerminal()) {
                log.info("Terminating Businessflow.");
                terminate = true;
                return;
            }

            if (optionalValidation.isEmpty() && (node.getTrueChild() != null || node.getFalseChild() != null)) {
                log.error("Your BusinessFlow adds conditional methods to a source method \"" + node.getData().getClass().getSimpleName() + "\" that is not providing a boolean for trueFalse logic. ");
            }

            optionalValidation.ifPresent(validation -> {
                if (validation) {
                    executeNode(node.getTrueChild());
                } else {
                    executeNode(node.getFalseChild());
                }
            });

            executeNode(node.getNextChild());
        }

    }

//
//    @AllArgsConstructor
//    private static class SimpleLambdaApplicationConsumer<T> extends AbstractApplicationFunction<T> {
//
//        Consumer<T> lamdaConsumer;
//
//        @Override
//        public T execute() {
//            lamdaConsumer.accept(model);
//            return model;
//        }
//    }

    private static class SimpleLambdaApplicationFunction<T> extends AbstractApplicationFunction<T> {

        Consumer<T> lambdaConsumer;
        Function<T, T> lambdaFunction;
        boolean useReturnValue;
        boolean isConsumer;

        public SimpleLambdaApplicationFunction(Consumer<T> lambdaConsumer) {
            this.lambdaConsumer = lambdaConsumer;
            this.isConsumer = true;
        }

        public SimpleLambdaApplicationFunction(Function<T, T> lambdaFunction, boolean useReturnValue) {
            this.lambdaFunction = lambdaFunction;
            this.useReturnValue = useReturnValue;
            this.isConsumer = false;
        }

        @Override
        public T execute() {
            if (isConsumer) {
                lambdaConsumer.accept(model);
                return model;
            }
            if (useReturnValue) {
                return lambdaFunction.apply(model);
            } else {
                lambdaFunction.apply(model);
                return model;
            }

        }
    }
}