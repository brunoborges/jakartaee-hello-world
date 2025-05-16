package org.eclipse.jakarta.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.ws.rs.core.Response;

/**
 * Integration tests for the Fortune Cookie Generator
 * 
 * These tests verify the complete flow of the application by testing 
 * the FortuneResource with various inputs and verifying the responses.
 */
@Tag("integration")
public class FortuneIntegrationTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "I am feeling anxious about my job interview tomorrow",
        "Should I pursue a new career path?",
        "Will I find happiness in my current relationship?",
        "I'm wondering if I should move to a new city"
    })
    @DisplayName("Test fortune generation with various thoughts")
    public void testFortuneGenerationWithVariousThoughts(String thoughts) {
        // Arrange
        FortuneResource resource = new FortuneResource();
        ThoughtsRequest request = new ThoughtsRequest(thoughts);
        
        // Act
        Response response = resource.generateFortune(request);
        
        // Assert
        assertEquals(200, response.getStatus(), "Response status should be 200 OK");
        Object entity = response.getEntity();
        assertNotNull(entity, "Response entity should not be null");
        
        @SuppressWarnings("unchecked")
        Map<String, String> fortuneResponse = (Map<String, String>) entity;
        
        String fortune = fortuneResponse.get("fortune");
        assertNotNull(fortune, "Response should contain a fortune");
        assertTrue(fortune.length() > 0, "Fortune should not be empty");
    }
    
    @Test
    @DisplayName("Test response format includes required fields")
    public void testResponseFormat() {
        // Arrange
        FortuneResource resource = new FortuneResource();
        ThoughtsRequest request = new ThoughtsRequest("Testing response format");
        
        // Act
        Response response = resource.generateFortune(request);
        
        // Assert
        assertEquals(200, response.getStatus(), "Response status should be 200 OK");
        
        @SuppressWarnings("unchecked")
        Map<String, String> fortuneResponse = (Map<String, String>) response.getEntity();
        
        // Verify the response contains the expected fields
        assertTrue(fortuneResponse.containsKey("fortune"), 
                "Response should contain a 'fortune' field");
    }
    
    @Test
    @DisplayName("Test fortune content is reasonable length")
    public void testFortuneLength() {
        // Arrange
        FortuneResource resource = new FortuneResource();
        ThoughtsRequest request = new ThoughtsRequest("Test the length of fortunes");
        
        // Act
        Response response = resource.generateFortune(request);
        
        // Assert
        assertEquals(200, response.getStatus(), "Response status should be 200 OK");
        
        @SuppressWarnings("unchecked")
        Map<String, String> fortuneResponse = (Map<String, String>) response.getEntity();
        
        String fortune = fortuneResponse.get("fortune");
        assertNotNull(fortune, "Fortune should not be null");
        
        // Fortune should be a reasonable length (not too short, not too long)
        assertTrue(fortune.length() >= 10, "Fortune should be at least 10 characters");
        assertTrue(fortune.length() <= 500, "Fortune should be no more than 500 characters");
    }
}