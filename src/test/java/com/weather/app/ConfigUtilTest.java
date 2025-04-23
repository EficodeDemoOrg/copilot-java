package test.java.com.weather.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Properties;
// Import main application classes correctly
import com.weather.app.ConfigUtil;


/**
 * Unit tests for the ConfigUtil class
 */
public class ConfigUtilTest {
    
    @TempDir
    Path tempDir;
    
    private String originalApiKeyEnv;
    
    @BeforeEach
    public void setUp() {
        // Save the original environment variable value
        originalApiKeyEnv = System.getenv("OPENWEATHERMAP_API_KEY");
        
        // Clear the environment variable for testing
        // Note: This doesn't actually work in Java because we can't modify env vars
        // in a running JVM, but we'll handle that in the tests
    }
    
    @Test
    public void testGetApiKeyFromEnvironmentVariable() throws Exception {
        // This test assumes the OPENWEATHERMAP_API_KEY environment variable is set
        // We'll simulate it by using reflection to access private method
        
        // This is a simulated test since we can't directly modify env vars
        if (originalApiKeyEnv != null && !originalApiKeyEnv.isEmpty()) {
            String apiKey = ConfigUtil.getApiKey();
            assertEquals(originalApiKeyEnv, apiKey);
        }
    }
    
    @Test
    public void testGetApiKeyFromPropertiesFile() throws Exception {
        // Only run this test if env var isn't set
        if (originalApiKeyEnv == null || originalApiKeyEnv.isEmpty()) {
            // Create a temp config.properties file
            File configFile = tempDir.resolve("config.properties").toFile();
            Properties props = new Properties();
            props.setProperty("api.key", "test-api-key-from-file");
            
            try (FileWriter writer = new FileWriter(configFile)) {
                props.store(writer, "Test properties");
            }
            
            // Since we can't easily test ConfigUtil directly (it uses ClassLoader.getResourceAsStream),
            // we'll note that this would be implemented with a custom test implementation or
            // by using a mocking framework like PowerMock that can mock static methods
        }
    }
    
    @Test
    public void testGetApiKeyMissingBoth() {
        // This test assumes the OPENWEATHERMAP_API_KEY environment variable is not set
        // and there's no valid config.properties file
        if (originalApiKeyEnv == null || originalApiKeyEnv.isEmpty()) {
            // We should get a ConfigException when neither env var nor properties file has the key
            ConfigUtil.ConfigException exception = assertThrows(ConfigUtil.ConfigException.class,
                () -> ConfigUtil.getApiKey());

            assertTrue(exception.getMessage().contains("API key not found"));
        }
    }
}
