package com.example.demo;

import com.example.demo.application.AddAbcToString;
import com.example.demo.application.AddXYZToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DemoApplication.class, args);


        //todo pawe: change wording -> validation should just be named trueFalseLogic
        // need an additional function type, that only allows an always() / .next(), where you dont need the trueFalseLogic
        /**
         * The objective of the AbstractApplicationFunction is to encapsulate the entirety of a function's logic,
         * including both its internal operations and the Yes/No/Always progression triggers. This makes a clear separation
         * between what the function does on its own and how it behaves when the BusinessFlow controls it.
         * By defining a clear boundary, this setup mitigates ambiguity in determining whether logic should reside within the
         * ApplicationFunction or the BusinessFlow. The distinction streamlines the codebase, offering clarity on where the
         * logic is housed and how it's conditionally invoked.
         */

        //Logic:
        //
        // adds ABC to the start of the String
        //          isTrue      [when the string ends with 'A']
        //          isFalse
        //          always -> adds XYZ to the start of the String
        //                          isTrue      [when the string ends with 'A']
        //                          isFalse
        //                          always

        var myTo = "IAMASTRING";

        //adds ABC to the start of the string, calls isTrue() when string ends with A
        new AddAbcToString(myTo)
                .isTrue((transformedABCTo) -> System.out.println("DEBUG: value ends with A " + transformedABCTo))
                .isFalse((transformedABCTo) -> System.out.println("DEBUG: Does not end with A"))
                .always((transformedABCTo) ->
                        //adds XYZ to the start of the string, calls isTrue() when string ends with A
                        new AddXYZToString(transformedABCTo)
                                .always((transformedXYZTo) -> System.out.println("DEBUG " + transformedXYZTo))
                                .execute())
                .execute();


    }

}

