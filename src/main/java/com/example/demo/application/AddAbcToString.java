package com.example.demo.application;

public class AddAbcToString extends AbstractApplicationFunction<String> {


    public AddAbcToString(String model) {
        super(model);
    }

    @Override
    public String function(String to) {
        return "ABC" + to;
    }

    @Override
    public boolean validate() {
        return model.charAt(model.length() - 1) == 'A';
    }
}
