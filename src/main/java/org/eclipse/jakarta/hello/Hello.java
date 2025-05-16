package org.eclipse.jakarta.hello;

/**
 * A simple model class that encapsulates a name for the Hello World example.
 * <p>
 * This class is used as a response object for the HelloWorldResource RESTful service,
 * representing a basic greeting concept that stores a name and provides access to it.
 * </p>
 * 
 * @author Fortune Cookie Generator Team
 * @version 1.0
 * @since May 2025
 */
public class Hello {

    /** The name to be used in the greeting */
    private String name;

    /**
     * Constructs a new Hello instance with the specified name.
     *
     * @param name The name to be stored and returned by the greeting
     */
    public Hello(String name) {
        this.name = name;
    }

    /**
     * Returns the name stored in this Hello instance.
     * <p>
     * This method is used to retrieve the name for creating a greeting message.
     * </p>
     *
     * @return The name as a String
     */
    public String getHello(){
        return name;
    }
}
