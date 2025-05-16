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

@Path("fortune")
public class FortuneResource {

    // This would typically come from a configuration file or environment variable
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

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
    
    private Map<String, Object> createFortuneResponse(String fortune) {
        Map<String, Object> response = new HashMap<>();
        response.put("fortune", fortune);
        return response;
    }
    
    private Map<String, Object> createErrorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", errorMessage);
        return response;
    }
}