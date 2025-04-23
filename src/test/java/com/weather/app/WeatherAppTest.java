package test.java.com.weather.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
// Import main application classes correctly
import com.weather.app.WeatherApp;


/**
 * Integration tests for the WeatherApp main class
 */
public class WeatherAppTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private static boolean originalExitOnError;
    
    @BeforeEach
    public void setUp() {
        // Capture System.out and System.err for assertions
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        
        // Disable System.exit calls during tests
        originalExitOnError = true; // Assuming default is true
        WeatherApp.setExitOnError(false);
    }
    
    @AfterEach
    public void tearDown() {
        // Restore original System.out and System.err
        System.setOut(originalOut);
        System.setErr(originalErr);
        
        // Reset the exit behavior
        WeatherApp.setExitOnError(originalExitOnError);
    }
    
    @Test
    public void testMainNoArguments() {
        // Call the main method with no arguments
        WeatherApp.main(new String[]{});
        
        // Check that usage instructions are printed
        assertTrue(errContent.toString().contains("Usage:") || 
                  outContent.toString().contains("Usage:"));
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
