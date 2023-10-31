package com.example.demo.application.functions.impl;

import com.example.demo.application.functions.AbstractApplicationFunction;

public class AddXYZSimpleFunction extends AbstractApplicationFunction<String> {


    @Override
    public String execute() {
        return "XYZ" + model;
    }
}
