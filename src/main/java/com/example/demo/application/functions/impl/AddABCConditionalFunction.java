package com.example.demo.application.functions.impl;

import com.example.demo.application.functions.AbstractApplicationFunction;

import java.util.Optional;


public class AddABCConditionalFunction extends AbstractApplicationFunction<String> {


    @Override
    public String execute() {
        return "ABC" + model;
    }

    @Override
    public Optional<Boolean> trueFalseLogic() {
        return Optional.of(model.charAt(model.length() - 1) == 'A');
    }
}
