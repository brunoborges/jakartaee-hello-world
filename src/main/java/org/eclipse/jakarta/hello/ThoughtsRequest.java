package org.eclipse.jakarta.hello;

public class ThoughtsRequest {
    private String thoughts;

    // Default constructor for JAX-RS JSON deserialization
    public ThoughtsRequest() {
    }

    public ThoughtsRequest(String thoughts) {
        this.thoughts = thoughts;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }
}