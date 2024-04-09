package stepDef;


import com.deque.axe.AXE;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lexisnexis.com.pages.homePage;
import org.lexisnexis.com.utilities.testConfig;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

import static org.lexisnexis.com.utilities.testConfig.scenario;
import static org.lexisnexis.com.utilities.testConfig.takeScreenPrint;

public class stepDefs {

    public static final Logger log = Logger.getLogger(stepDefs.class.getName());
    private static final URL link = stepDefs.class.getResource("/axe.min.js");
    private static WebDriver driver;
    JSONObject response;
    JSONArray errors;
    String accessibility;

    @Given("I am on the LexisNexis website {string}")
    public void i_am_on_the_lexisnexis_website(String url) {
        driver.get(url);
    }

    @Then("I checked if the {string} is clickable")
    public void i_checked_if_the_link_is_clickable(String link) {

        homePage obj = new homePage(driver);
        obj.linkCheck(link);
        takeScreenPrint(driver);

        if (accessibility.equalsIgnoreCase("yes"))
            testAccessibility(link);
    }

    @When("I clicked on Choose Your Industry")
    public void i_clicked_on_choose_your_industry() {

        homePage obj = new homePage(driver);
        obj.choose_your_industry();

    }
    @Then("I clicked on {string}")
    public void i_clicked_on(String industry) {

        homePage obj = new homePage(driver);
        obj.select_industry(industry);

//        if (accessibility.equalsIgnoreCase("yes"))
//            testAccessibility(industry);

    }
    @And("Clickable checked for {string}")
    public void clickable_checked_for_subIndustry(String subIndustry) {

        homePage obj = new homePage(driver);
        obj.select_sub_industry(subIndustry);
    }

    @Before
    public void setUp(Scenario scenarios) throws Throwable {
        DesiredCapabilities capability = new DesiredCapabilities();

        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        scenario = scenarios;
        String browser = testConfig.getBrowser();
        accessibility = testConfig.getAccessibilityTest();


        if (browser.equals("chrome")) {
            capability.setPlatform(Platform.ANY);
            capability.setBrowserName("chrome");
            ChromeOptions opts = new ChromeOptions();
            opts.merge(capability);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capability);
            log.info("driver check :" + (driver == null));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }

        if (browser.equals("firefox")) {
            capability.setPlatform(Platform.ANY);
            capability.setBrowserName("firefox");
            FirefoxOptions opts = new FirefoxOptions();
            opts.merge(capability);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capability);
            log.info("driver check :" + (driver == null));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }

    }

    public void testAccessibility(String pageName) {

        response = new AXE.Builder(driver, link).analyze();
        errors = response.getJSONArray("violations");

        if (errors.length() > 0) {
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Accessibility Violation Found. Please refer to " +pageName+".json at project root");
            AXE.writeResults(pageName, response.toString(2));
            scenario.log("Accessibility Violation Found. Please refer to " +pageName+".json at project root");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        } else {
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("No accessibility error found based on guidelines set by deque university");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

            scenario.log("No accessibility error found based on guidelines set by deque university");
        }
    }


    @After
    public void teardown(Scenario scenario) throws Exception {


        //Unconditional wait applied to showcase/record the last navigated page
//        Thread.sleep(1000);

        driver.quit();
    }



}
