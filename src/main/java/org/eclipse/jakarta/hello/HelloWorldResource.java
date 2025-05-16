package org.eclipse.jakarta.hello;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * RESTful resource that provides a simple "Hello World" service.
 * <p>
 * This class implements a basic REST endpoint that returns a greeting. It accepts
 * an optional name parameter and returns a personalized greeting or defaults to
 * "world" if no name is provided.
 * </p>
 * 
 * @author Fortune Cookie Generator Team
 * @version 1.0
 * @since May 2025
 */
@Path("hello")
public class HelloWorldResource {

    /**
     * Handles GET requests to the /hello endpoint.
     * <p>
     * This method processes incoming requests, extracts the optional name parameter,
     * and returns a Hello object containing either the provided name or "world" as
     * a default value if no name is specified.
     * </p>
     *
     * @param name An optional query parameter specifying the name to greet
     * @return A Hello object containing the name (provided or default)
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }
}
