package org.eclipse.jakarta.hello;

/**
 * Represents a request containing user thoughts for fortune cookie generation.
 * <p>
 * This class is used to deserialize JSON requests from the client when 
 * submitting thoughts to the Fortune Cookie Generator. It encapsulates
 * the user's input that will be used to generate a personalized fortune.
 * </p>
 * 
 * @author Fortune Cookie Generator Team
 * @version 1.0
 * @since May 2025
 */
public class ThoughtsRequest {
    /** The user's thoughts or input text used to generate a fortune */
    private String thoughts;

    /**
     * Default no-argument constructor required for JAX-RS JSON deserialization.
     * This allows the framework to instantiate the class when creating objects
     * from incoming JSON payloads.
     */
    public ThoughtsRequest() {
    }

    /**
     * Constructs a ThoughtsRequest with the specified thoughts.
     *
     * @param thoughts The user's thoughts or input text
     */
    public ThoughtsRequest(String thoughts) {
        this.thoughts = thoughts;
    }

    /**
     * Gets the user's thoughts.
     *
     * @return The user's thoughts as a String
     */
    public String getThoughts() {
        return thoughts;
    }

    /**
     * Sets the user's thoughts.
     *
     * @param thoughts The thoughts to set
     */
    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }
    
}
