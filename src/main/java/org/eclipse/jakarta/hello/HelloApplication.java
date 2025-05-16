package org.eclipse.jakarta.hello;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

/**
 * Main application class that configures the Jakarta REST (JAX-RS) resources.
 * <p>
 * This class serves as the entry point for the Jakarta REST application.
 * It defines the base path for all REST resources in the application
 * through the {@code @ApplicationPath} annotation. All REST endpoints
 * will be accessible under the "/rest" path.
 * </p>
 * <p>
 * The application includes:
 * <ul>
 *   <li>HelloWorldResource - Basic greeting service</li>
 *   <li>FortuneResource - Fortune cookie generator service</li>
 * </ul>
 * </p>
 * 
 * @author Fortune Cookie Generator Team
 * @version 1.0
 * @since May 2025
 * @see org.eclipse.jakarta.hello.HelloWorldResource
 * @see org.eclipse.jakarta.hello.FortuneResource
 */
@ApplicationPath("rest")
public class HelloApplication extends Application {
    // Needed to enable Jakarta REST and specify path.
}
