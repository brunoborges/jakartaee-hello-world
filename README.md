# Fortune Cookie Generator

## Overview
The Fortune Cookie Generator is a web application built with Jakarta EE that generates personalized fortune cookie messages based on user input. The application uses AI-powered text generation to create unique, insightful fortunes tailored to users' thoughts and concerns.

This project demonstrates:
- Jakarta EE REST API implementation
- Frontend-backend integration using JavaScript fetch API
- Interactive UI with SVG graphics and CSS animations
- Asynchronous request handling

## Features
- Clean, responsive user interface
- AI-powered fortune generation
- Interactive fortune cookie display
- Smooth animations and transitions

## Technologies Used
- Jakarta EE 10+
- RESTful Web Services (JAX-RS)
- HTML5, CSS3, JavaScript
- GlassFish Server 7.0+

## Prerequisites
- Java SE 21 or later
- Eclipse IDE for Enterprise Java and Web Developers
- OmniFish GlassFish Tools plugin for Eclipse
- GlassFish Server 7.0 or newer

## Setup Instructions

### Installing Eclipse and GlassFish Tools

1. **Download and install Eclipse IDE for Enterprise Java and Web Developers**:
   - Visit [Eclipse Downloads](https://www.eclipse.org/downloads/) and select the appropriate package for your operating system

2. **Install the OmniFish GlassFish Tools plugin**:
   - Open Eclipse
   - Go to Help > Eclipse Marketplace
   - Search for "OmniFish GlassFish Tools"
   - Click "Install" and follow the installation wizard
   - Restart Eclipse when prompted

3. **Download GlassFish Server**:
   - Visit [GlassFish Downloads](https://glassfish.org/download)
   - Download GlassFish 7.0.x or newer
   - Extract the downloaded archive to a location of your choice (e.g., `D:\tools\glassfish-7.0.24`)

### Setting up the Project

1. **Clone or download the project**:
   ```
   git clone [repository-url]
   ```
   Or download and extract the project ZIP file

2. **Import the project into Eclipse**:
   - Go to File > Import
   - Select "Existing Maven Projects"
   - Browse to the project directory and select it
   - Click "Finish"

3. **Configure GlassFish Server in Eclipse**:
   - Go to Window > Show View > Servers
   - In the Servers view, right-click and select "New > Server"
   - Select "GlassFish" from the server types list
   - Click "Next" and browse to your GlassFish installation directory
   - Select the appropriate runtime (Jakarta EE 10) and click "Finish"

### Building and Running the Application

1. **Build the project**:
   - Right-click on the project in Project Explorer
   - Select Run As > Maven Build
   - Enter `clean package` in the Goals field
   - Click "Run"

2. **Deploy to GlassFish**:
   - Right-click on the GlassFish server in the Servers view
   - Select "Add and Remove"
   - Add the Fortune Cookie Generator application to the configured list
   - Click "Finish"
   - Start the server by right-clicking it and selecting "Start"

3. **Access the application**:
   - Open a web browser and navigate to http://localhost:8080/jakartaee-hello-world/
   - You should see the Fortune Cookie Generator interface

## Usage
1. Enter your thoughts, concerns, or questions in the text field
2. Click "Generate Fortune" to submit
3. Wait for the AI to generate your personalized fortune
4. Your fortune will appear over the fortune cookie image
5. Click on the fortune message to dismiss it

## Building from Command Line
You can also build the application by executing the following command from the project root directory:

```
./mvnw clean package
```

This will generate a file named `jakartaee-hello-world.war` in the `target` directory. You can deploy this WAR file to any Jakarta EE 10+ compatible server.

## License
This project is licensed under the Eclipse Public License.
