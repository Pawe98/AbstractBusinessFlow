package com.example.demo.application.flows;

import com.example.demo.application.functions.impl.AddABCConditionalFunction;
import com.example.demo.application.functions.impl.AddXYZSimpleFunction;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class ApplicationFlow {
    private final AddABCConditionalFunction addAbcConditionalFunction = new AddABCConditionalFunction();
    private final AddXYZSimpleFunction addXYZSimpleFunction = new AddXYZSimpleFunction();

    /**
     * The objective of the ApplicationFunction is to encapsulate the entirety of a function's logic,
     * including both its internal operations and the Yes/No/Always progression triggers. This makes a clear separation
     * between what the function does on its own and how it behaves when the BusinessFlow controls it.
     * By defining a clear boundary, this setup mitigates ambiguity in determining whether logic should reside within the
     * ApplicationFunction or the BusinessFlow. The distinction streamlines the codebase, offering clarity on where the
     * logic is housed and how it's conditionally invoked.
     */
    @EventListener(ContextRefreshedEvent.class)
    public void startApplicationFlow() {

        var myToA = "ThisEndsWithA";
        var myToB = "ThisEndsWithB";

        log.info("Starting BusinessFlow with '" + myToA + "'");

        BusinessFlow.init(myToA)
                .startFunction(addAbcConditionalFunction)
                .whenTrueDo(addXYZSimpleFunction)
                .whenFalseDo((myToABC) -> log.info("DEBUG: Does not end with A " + myToABC))
                .nextFunction((myToXYZ) -> myToXYZ + "ThisWasChangedWithLamda", true)
                .nextFunction((myToXYZ) -> log.info("DEBUG: final value " + myToXYZ)).executeFlow();

        log.info("Starting BusinessFlow with '" + myToA + "'");

        BusinessFlow.init(myToA)
                .startFunction(addAbcConditionalFunction)
                .whenTrueDo((myToXYZ) -> myToXYZ + "ThisWasChangedWithLamda", true)
                .whenFalseDo((myToABC) -> log.info("DEBUG: Does not end with A " + myToABC))
                .nextFunction((myToXYZ) -> log.info("DEBUG: final value " + myToXYZ)).executeFlow();


        log.info("Starting BusinessFlow with '" + myToB + "'");
        BusinessFlow.init(myToB)
                .startFunction(addXYZSimpleFunction)
                .whenTrueDo(addAbcConditionalFunction)
                .whenFalseDo((myToABC) -> log.info("DEBUG: Does not end with A " + myToABC))
                .nextFunction((myToXYZ) -> myToXYZ + "ThisWasChangedWithLamda", true)
                .nextFunction((myToXYZ) -> log.info("DEBUG: final value " + myToXYZ)).executeFlow();


    }
}
