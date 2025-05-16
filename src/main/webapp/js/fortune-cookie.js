/**
 * Fortune Cookie Generator JavaScript
 * Simplified with animation for fortune message display
 */

/**
 * Client-side logging utility
 */
const FortuneCookieLogger = {
    // Log levels
    LEVELS: {
        DEBUG: 1,
        INFO: 2,
        WARN: 3,
        ERROR: 4
    },
    
    // Current logging level
    currentLevel: 2, // Default to INFO
    
    // Enable console output
    enableConsole: true,
    
    /**
     * Set the logging level
     * @param {number} level - The log level to set
     */
    setLevel: function(level) {
        this.currentLevel = level;
    },
    
    /**
     * Format a log message with timestamp
     * @param {string} level - The log level name
     * @param {string} message - The message to log
     * @returns {string} - Formatted log message
     */
    formatMessage: function(level, message) {
        const now = new Date();
        const timestamp = now.toISOString();
        return `[${timestamp}] [${level}] ${message}`;
    },
    
    /**
     * Log a debug message
     * @param {string} message - The message to log
     */
    debug: function(message) {
        if (this.currentLevel <= this.LEVELS.DEBUG && this.enableConsole) {
            console.debug(this.formatMessage('DEBUG', message));
        }
    },
    
    /**
     * Log an info message
     * @param {string} message - The message to log
     */
    info: function(message) {
        if (this.currentLevel <= this.LEVELS.INFO && this.enableConsole) {
            console.info(this.formatMessage('INFO', message));
        }
    },
    
    /**
     * Log a warning message
     * @param {string} message - The message to log
     */
    warn: function(message) {
        if (this.currentLevel <= this.LEVELS.WARN && this.enableConsole) {
            console.warn(this.formatMessage('WARN', message));
        }
    },
    
    /**
     * Log an error message
     * @param {string} message - The message to log
     * @param {Error} [error] - Optional error object
     */
    error: function(message, error) {
        if (this.currentLevel <= this.LEVELS.ERROR && this.enableConsole) {
            if (error) {
                console.error(this.formatMessage('ERROR', message), error);
            } else {
                console.error(this.formatMessage('ERROR', message));
            }
        }
    }
};

document.addEventListener('DOMContentLoaded', function() {
    FortuneCookieLogger.info('Fortune Cookie application initialized');
    
    // Get references to DOM elements
    const thoughtForm = document.getElementById('thoughtForm');
    const fortuneCookie = document.getElementById('fortuneCookie');
    const fortuneMessage = document.getElementById('fortuneMessage');
    const loadingIndicator = document.getElementById('loadingIndicator');
    const cookieContainer = document.querySelector('.fortune-cookie-container');

    FortuneCookieLogger.debug('DOM elements retrieved');
    
    // Add event listener to reset message when clicked
    fortuneMessage.addEventListener('click', function() {
        hideFortuneMessage(fortuneCookie, fortuneMessage);
        FortuneCookieLogger.debug('Fortune message hidden on click');
    });

    // Add event listener to the form
    thoughtForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        FortuneCookieLogger.info('Form submitted');

        // Get user input
        const thoughts = document.getElementById('userThoughts').value;
        FortuneCookieLogger.debug(`User input length: ${thoughts.length} characters`);
        
        // Reset the fortune message
        hideFortuneMessage(fortuneCookie, fortuneMessage);
        
        // Show loading indicator
        loadingIndicator.style.display = 'block';
        FortuneCookieLogger.debug('Loading indicator displayed');
        
        // Scroll to the fortune cookie if not in view
        cookieContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
        FortuneCookieLogger.debug('Scrolled to cookie container');

        try {
            FortuneCookieLogger.info('Sending request to backend');
            // Send request to our backend
            const response = await fetch('rest/fortune', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ thoughts: thoughts })
            });

            if (!response.ok) {
                throw new Error(`Server responded with status: ${response.status}`);
            }

            FortuneCookieLogger.info('Response received from backend');
            const data = await response.json();
            FortuneCookieLogger.debug('Response data parsed successfully');

            // Hide loading indicator
            loadingIndicator.style.display = 'none';
            
            // Update and show the fortune message with animation
            showFortuneMessage(fortuneCookie, fortuneMessage, data.fortune);
            FortuneCookieLogger.info('Fortune displayed to user');
            
        } catch (error) {
            FortuneCookieLogger.error('Error during fortune generation', error);
            console.error('Error:', error);
            
            // Hide loading indicator
            loadingIndicator.style.display = 'none';
            
            // Show error message
            showFortuneMessage(fortuneCookie, fortuneMessage, 'Sorry, we encountered an error. Please try again.');
            FortuneCookieLogger.warn('Error message displayed to user');
        }
    });
});

/**
 * Shows the fortune message with animation
 * @param {HTMLElement} cookieElement - The cookie element
 * @param {HTMLElement} messageElement - The fortune message element
 * @param {string} message - The message text to display
 */
function showFortuneMessage(cookieElement, messageElement, message) {
    // Update message text
    messageElement.textContent = message;
    
    // Add subtle shake animation to the cookie
    cookieElement.animate([
        { transform: 'rotate(-1deg)' },
        { transform: 'rotate(1deg)' },
        { transform: 'rotate(0deg)' }
    ], {
        duration: 300,
        easing: 'ease-out'
    });
    
    // Add dim effect to cookie
    cookieElement.classList.add('dimmed');
    
    // Show the message with animation
    setTimeout(() => {
        messageElement.classList.add('visible');
    }, 100);
}

/**
 * Hides the fortune message
 * @param {HTMLElement} cookieElement - The cookie element
 * @param {HTMLElement} messageElement - The fortune message element
 */
function hideFortuneMessage(cookieElement, messageElement) {
    // Hide the message
    messageElement.classList.remove('visible');
    
    // Remove dim effect from cookie
    cookieElement.classList.remove('dimmed');
}
