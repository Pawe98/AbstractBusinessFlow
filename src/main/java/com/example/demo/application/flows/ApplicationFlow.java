package com.example.demo.application.flows;

import com.example.demo.application.functions.impl.AddABCConditionalFunction;
import com.example.demo.application.functions.impl.AddXYZSimpleFunction;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationFlow {
    private AddABCConditionalFunction addAbcConditionalFunction;
    private AddXYZSimpleFunction addXYZSimpleFunction;

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

        //todo pawe Move .next() out of function into a BusinessFlow implementation, where a chain of functions is established and then run.
        //Logic:
        //
        // adds ABC to the start of the String
        //          isTrue      [when the string ends with 'A']
        //          isFalse
        //          always -> adds XYZ to the start of the String
        //                          isTrue      [when the string ends with 'A']
        //                          isFalse
        //                          always

        var myToA = "ThisEndsWithA";
        var myToB = "ThisEndsWithB";

        System.out.println("Starting BusinessFlow with '" + myToA + "'");


        //adds ABC to the start of the string, calls isTrue() when string ends with A
        addAbcConditionalFunction.init(myToA)
                .isTrue((myToABC) -> System.out.println("DEBUG: value ends with A " + myToABC))
                .isFalse((myToABC) -> System.out.println("DEBUG: Does not end with A " + myToABC))
                .next((myToABC) ->
                        //adds XYZ to the start of the string, calls isTrue() when string ends with A
                        addXYZSimpleFunction.init(myToABC)
                                .next((myToXYZ) -> System.out.println("DEBUG: final value " + myToXYZ))
                                .execute())
                .execute();


        System.out.println("Starting BusinessFlow with '" + myToB + "'");


        //adds ABC to the start of the string, calls isTrue() when string ends with A
        addAbcConditionalFunction.init(myToB)
                .isTrue((transformedABCTo) -> System.out.println("DEBUG: value ends with A " + transformedABCTo))
                .isFalse((transformedABCTo) -> System.out.println("DEBUG: Does not end with A " + transformedABCTo))
                .next((transformedABCTo) ->
                        //adds XYZ to the start of the string, calls isTrue() when string ends with A
                        addXYZSimpleFunction.init(transformedABCTo)
                                .next((transformedXYZTo) -> System.out.println("DEBUG: final value " + transformedXYZTo))
                                .execute())
                .execute();


    }
}
