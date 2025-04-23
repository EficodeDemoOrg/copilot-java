package test.java.com.weather.app;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite that runs all tests for the Weather application
 */
@Suite
@SelectPackages("test.java.com.weather.app")
public class WeatherAppTestSuite {
    // This class serves as a test suite marker
    // JUnit will automatically discover and run all test classes in the specified package
}
