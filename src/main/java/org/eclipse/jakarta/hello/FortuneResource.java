package org.eclipse.jakarta.hello;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RESTful resource for generating fortune cookie messages based on user input.
 * <p>
 * This class provides a REST endpoint that accepts user thoughts and generates
 * personalized fortune cookie messages either through OpenAI's GPT model or
 * using predefined mock responses when OpenAI integration is not available.
 * </p>
 * 
 * @author Fortune Cookie Generator Team
 * @version 1.0
 * @since May 2025
 */
@Path("fortune")
public class FortuneResource {

    /**
     * OpenAI API key retrieved from environment variables.
     * This key is required for connecting to OpenAI's services.
     * If not provided, the application will fall back to using mock fortunes.
     */
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

    /**
     * Processes user thoughts and generates a personalized fortune cookie message.
     * <p>
     * This endpoint accepts a JSON object containing the user's thoughts,
     * validates the input, and then either:
     * <ul>
     *   <li>Generates a fortune using OpenAI if an API key is available</li>
     *   <li>Returns a mock fortune if no API key is configured</li>
     * </ul>
     * </p>
     *
     * @param request The ThoughtsRequest object containing user input
     * @return Response containing the generated fortune cookie message or an error
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateFortune(ThoughtsRequest request) {
        try {
            if (request == null || request.getThoughts() == null || request.getThoughts().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(createErrorResponse("No thoughts provided"))
                        .build();
            }

            // If API key is not configured, return a mock fortune
            if (OPENAI_API_KEY == null || OPENAI_API_KEY.trim().isEmpty()) {
                return Response.ok(createFortuneResponse(getMockFortune(request.getThoughts()))).build();
            }

            // Generate a fortune using OpenAI
            String fortune = generateFortuneWithOpenAI(request.getThoughts());
            return Response.ok(createFortuneResponse(fortune)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(createErrorResponse("Error generating fortune: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Generates a fortune cookie message using OpenAI's GPT model.
     * <p>
     * This method creates a prompt for the AI model that instructs it to generate
     * a fortune cookie message based on the user's thoughts. The prompt is designed
     * to produce responses that are concise and styled like traditional fortune cookies.
     * </p>
     *
     * @param thoughts The user's thoughts or input text
     * @return A generated fortune cookie message
     */
    private String generateFortuneWithOpenAI(String thoughts) {
        OpenAiService service = new OpenAiService(OPENAI_API_KEY);
        
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", 
                "You are a fortune cookie generator. Generate a short, wise, and insightful fortune " +
                "cookie message based on the user's thoughts. Keep it under 100 characters if possible, " +
                "and make it sound like a traditional fortune cookie."));
        messages.add(new ChatMessage("user", thoughts));
        
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .maxTokens(100)
                .build();
        
        return service.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent().trim();
    }
    
    /**
     * Provides a predefined fortune cookie message when OpenAI integration is unavailable.
     * <p>
     * This method selects one of several predefined fortune messages based on a hash
     * of the user's input, ensuring that the same input consistently produces the same
     * fortune while still providing variety across different inputs.
     * </p>
     *
     * @param thoughts The user's thoughts or input text
     * @return A mock fortune cookie message
     */
    private String getMockFortune(String thoughts) {
        // Simple mock fortunes for when the API key isn't available
        String[] mockFortunes = {
            "The path to wisdom begins with understanding yourself.",
            "A journey of a thousand miles begins with a single step.",
            "New opportunities await you on the horizon.",
            "The best way to predict your future is to create it.",
            "Good things come to those who wait, but better things come to those who act."
        };
        
        // Use hashCode to select a mock fortune based on input
        int index = Math.abs(thoughts.hashCode() % mockFortunes.length);
        return mockFortunes[index];
    }
    
    /**
     * Creates a JSON response object containing the generated fortune.
     *
     * @param fortune The fortune cookie message
     * @return A Map that will be serialized to JSON by the JAX-RS runtime
     */
    private Map<String, Object> createFortuneResponse(String fortune) {
        Map<String, Object> response = new HashMap<>();
        response.put("fortune", fortune);
        return response;
    }
    
    /**
     * Creates a JSON error response object.
     *
     * @param errorMessage The error message
     * @return A Map that will be serialized to JSON by the JAX-RS runtime
     */
    private Map<String, Object> createErrorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", errorMessage);
        return response;
    }
}
