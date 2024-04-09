package org.lexisnexis.com.utilities;


import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class testConfig {

    public static final Logger log = Logger.getLogger(testConfig.class.getName());
    public static Scenario scenario;
    public static WebDriver driver = null;
    private static String browser = null;
    private static String AccessibilityTest = null;

    public static String getBrowser() throws Throwable {

        if (browser != null) {
            return browser;
        } else
            throw new RuntimeException("Browser parameter not set in TestNG.xml");
    }

    public static void setBrowser(String browserType) {
        browser = browserType;
    }

    public static String getAccessibilityTest() throws Throwable {

        if (AccessibilityTest != null) {
            return AccessibilityTest;
        } else
            throw new RuntimeException("Browser parameter not set in TestNG.xml");
    }

    public static void setAccessibilityTest(String Accessibility) {
        AccessibilityTest = Accessibility;
    }

    public static void takeScreenPrint(WebDriver driver) {
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        testConfig.scenario.attach(screenshot, "image/png", "Screenshot");
    }

//    public static WebDriver getDriver() {
//        return driver;
//    }
//
//    public static void setDriver(WebDriver driver) {
//        testConfig.driver = driver;
//    }


}

