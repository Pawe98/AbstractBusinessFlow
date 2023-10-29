package com.example.demo.application;

public class AddXYZToString extends AbstractApplicationFunction<String>{


    public AddXYZToString(String model) {
        super(model);
    }

    @Override
    public String function(String to) {
        return "XYZ" + to;
    }

    @Override
    public boolean validate() {
        return model.charAt(model.length() - 1) == 'A';
    }
}
