package com.example.demo.application.functions.impl;

import com.example.demo.application.functions.SimpleApplicationFunction;
import org.springframework.stereotype.Component;

@Component
public class AddXYZToString extends SimpleApplicationFunction<String> {

    @Override
    public String function(String to) {
        return "XYZ" + to;
    }

}
