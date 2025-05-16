package org.eclipse.jakarta.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ThoughtsRequest data model
 */
public class ThoughtsRequestTest {

    @Test
    @DisplayName("Test ThoughtsRequest getters and setters")
    public void testGetterAndSetter() {
        // Arrange
        ThoughtsRequest request = new ThoughtsRequest();
        String thoughts = "I am wondering about my future career";
        
        // Act
        request.setThoughts(thoughts);
        
        // Assert
        assertEquals(thoughts, request.getThoughts(), "Getter should return the value set by setter");
    }
    
    @Test
    @DisplayName("Test ThoughtsRequest constructor")
    public void testConstructor() {
        // Arrange
        String thoughts = "Will I find love this year?";
        
        // Act
        ThoughtsRequest request = new ThoughtsRequest(thoughts);
        
        // Assert
        assertEquals(thoughts, request.getThoughts(), "Constructor should set the thoughts field");
    }
    
    @Test
    @DisplayName("Test default constructor")
    public void testDefaultConstructor() {
        // Act
        ThoughtsRequest request = new ThoughtsRequest();
        
        // Assert
        assertNull(request.getThoughts(), "Default constructor should set thoughts to null");
    }
}