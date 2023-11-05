package com.example.demo.application.flows;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class ApplicationFlow {

    /**
     * The objective of the ApplicationFunction is to encapsulate the entirety of a function's logic,
     * including both its internal operations and the Yes/No/Always progression triggers. This makes a clear separation
     * between what the function does on its own and how it behaves when the BusinessFlow controls it.
     * By defining a clear boundary, this setup mitigates ambiguity in determining whether logic should reside within the
     * ApplicationFunction or the BusinessFlow. The distinction streamlines the codebase, offering clarity on where the
     * logic is housed and how it's conditionally invoked.
     */

    BusinessFlow<String> myBusinessFlow;

    @PostConstruct
    void setup() {
        myBusinessFlow = BusinessFlow.init(String.class).startFunction((str) -> System.out.println(str))
                .nextFunction((str) -> str += "Welcome", true).nextFunction((str) -> System.out.println(str));
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startApplicationFlow() {

        String myTestString = "";
        BusinessFlow.receive(myTestString, myBusinessFlow);

        myBusinessFlow.executeFlow();


    }

}
