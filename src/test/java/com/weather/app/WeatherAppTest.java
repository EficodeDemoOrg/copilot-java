package com.weather.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
// Import main application classes correctly
import com.weather.app.WeatherApp;


/**
 * Integration tests for the WeatherApp main class
 */
public class WeatherAppTest {
    
    private final ByteArrayOutputStream logContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private static boolean originalExitOnError;
    
    private StreamHandler streamHandler;
    private Logger appLogger;
    
    @BeforeEach
    public void setUp() {
        // Set up logger to capture log output
        logContent.reset();
        appLogger = Logger.getLogger("com.weather.app.WeatherApp");
        
        // Create a formatter for the handler
        java.util.logging.SimpleFormatter formatter = new java.util.logging.SimpleFormatter();
        streamHandler = new StreamHandler(logContent, formatter);
        streamHandler.setLevel(Level.ALL);
        appLogger.addHandler(streamHandler);
        appLogger.setLevel(Level.ALL);
        
        // We still need to capture System.out/err for any non-logger output
        System.setOut(new PrintStream(logContent));
        System.setErr(new PrintStream(logContent));
        
        // Disable System.exit calls during tests
        originalExitOnError = true; // Assuming default is true
        WeatherApp.setExitOnError(false);
    }
    
    @AfterEach
    public void tearDown() {
        // Remove our test handler from the logger if it exists
        if (appLogger != null && streamHandler != null) {
            appLogger.removeHandler(streamHandler);
            streamHandler.close();
        }
        
        // Restore original System.out and System.err
        System.setOut(originalOut);
        System.setErr(originalErr);
        
        // Reset the exit behavior
        WeatherApp.setExitOnError(originalExitOnError);
    }
    
    // Note: The following tests would require mocking or a real API key
    // They are commented out since they're for demonstration purposes
    
    /*
    @Test
    public void testMainWithValidCity() {
        // Mock dependencies or use a real API key
        // This would be an integration test
        WeatherApp.main(new String[]{"London"});
        
        // Check that weather info is printed
        assertTrue(outContent.toString().contains("London"));
        assertTrue(outContent.toString().contains("Temperature"));
    }
    
    @Test
    public void testMainWithInvalidCity() {
        // Mock dependencies or use a real API key
        WeatherApp.main(new String[]{"InvalidCityName123456"});
        
        // Check that an error message is printed
        assertTrue(errContent.toString().contains("City not found"));
    }
    */
}
