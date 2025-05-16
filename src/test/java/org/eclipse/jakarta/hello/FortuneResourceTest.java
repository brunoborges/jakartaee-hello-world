package org.eclipse.jakarta.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.Map;

/**
 * Test class for the FortuneResource REST endpoint
 */
@ExtendWith(MockitoExtension.class)
public class FortuneResourceTest {

    @Test
    @DisplayName("Test generateFortune with valid input")
    public void testGenerateFortuneWithValidInput() {
        // Arrange
        FortuneResource resource = new FortuneResource();
        ThoughtsRequest request = new ThoughtsRequest("I wonder what my future holds");
        
        // Act
        Response response = resource.generateFortune(request);
        
        // Assert
        assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Response status should be 200 OK");
        Object entity = response.getEntity();
        assertNotNull(entity, "Response entity should not be null");
        assertTrue(entity instanceof Map, "Response entity should be a Map");
        
        @SuppressWarnings("unchecked")
        Map<String, String> fortuneResponse = (Map<String, String>) entity;
        assertNotNull(fortuneResponse.get("fortune"), "Response should contain a fortune");
    }
    
    @Test
    @DisplayName("Test generateFortune with null request")
    public void testGenerateFortuneWithNullRequest() {
        // Arrange
        FortuneResource resource = new FortuneResource();
        
        // Act
        Response response = resource.generateFortune(null);
        
        // Assert
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus(), 
                "Response status should be 400 Bad Request for null input");
    }
    
    @Test
    @DisplayName("Test generateFortune with empty thoughts")
    public void testGenerateFortuneWithEmptyThoughts() {
        // Arrange
        FortuneResource resource = new FortuneResource();
        ThoughtsRequest request = new ThoughtsRequest("");
        
        // Act
        Response response = resource.generateFortune(request);
        
        // Assert
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus(), 
                "Response status should be 400 Bad Request for empty thoughts");
    }
    
    @Test
    @DisplayName("Test error handling when exception occurs")
    public void testGenerateFortuneWithException() {
        // This test uses a static mock to simulate an exception during processing
        try (MockedStatic<System> mockedSystem = mockStatic(System.class)) {
            // Arrange
            mockedSystem.when(() -> System.getenv("OPENAI_API_KEY"))
                     .thenThrow(new RuntimeException("Simulated error"));
            
            FortuneResource resource = new FortuneResource();
            ThoughtsRequest request = new ThoughtsRequest("Will this cause an error?");
            
            // Act
            Response response = resource.generateFortune(request);
            
            // Assert
            assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus(), 
                    "Response status should be 500 Internal Server Error when exception occurs");
        }
    }
}