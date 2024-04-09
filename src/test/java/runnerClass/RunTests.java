package runnerClass;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.lexisnexis.com.utilities.testConfig;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

@CucumberOptions(
        glue = {"stepDef"},
        features = {"src/test/resources/features/"},
        plugin = {
                "pretty",
                "html:reports/tests/cucumber/cucumber-pretty.html",
                "testng:reports/tests/cucumber/testng/cucumber.xml",
                "json:reports/tests/cucumber/json/cucumberTestReport.json"
        }
//        , publish = true
)

public class RunTests extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    public Object[][] runner() {

        return super.scenarios();
    }

    @BeforeTest
    @Parameters({"browser", "accessibilityTest"})
//    @Parameters("accessibilityTest"={"accessibilityTest"})
    public void browserSetup(String browser, String Test) throws Throwable {
        testConfig.setBrowser(browser);
        testConfig.setAccessibilityTest(Test);
//        System.out.println(" test :" + Test);
    }

}
