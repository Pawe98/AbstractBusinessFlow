package com.example.demo.application.functions.impl;

import com.example.demo.application.functions.ConditionalApplicationFunction;
import org.springframework.stereotype.Component;

@Component
public class AddAbcToString extends ConditionalApplicationFunction<String> {

    @Override
    public String function(String to) {
        return "ABC" + to;
    }

    @Override
    public boolean trueFalseLogic() {
        return model.charAt(model.length() - 1) == 'A';
    }
}
