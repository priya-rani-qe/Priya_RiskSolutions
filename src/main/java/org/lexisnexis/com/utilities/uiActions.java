package org.lexisnexis.com.utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class uiActions {

    public static final Logger log = Logger.getLogger(uiActions.class.getName());

    private WebDriver driver;
    private int timer = 5;

    public uiActions(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButton(WebElement element) {

        element.click();

    }

    public void enterText(WebElement element, String txt) {

        if (waitForVisibility(element)) {

            try {
                scrollToElement(element);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].value=" + txt + ";", element);

            } catch (JavascriptException js) {

                log.info("Js send text failed. Re-trying..");
                enterTextBackUp(element, txt);
            }

        }

    }

    public void enterTextBackUp(WebElement element, String txt) {

        Actions actn = new Actions(driver);
        actn.moveToElement(element).sendKeys(element, txt).build().perform();
    }

    public String screenScrape(WebElement element) {

        return element.getText();
    }


    public void scrollToElement(WebElement obj) {
        try {

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", obj);
        } catch (Exception e) {

            log.info("Scroll Failed");
        }
    }

    public boolean waitForVisibility(WebElement ele) {

        log.info("Checking if element is visible :" + ele);


        try {
            // will get an exception if web element doesn't exist
            // at this point due to page load or other reasons while checking

            if (ele.isDisplayed()) {
                log.info("Element displayed found");
                return true;
            } else {
                // checking if element is displayed after scroll

                scrollToElement(ele);
                if (ele.isDisplayed()) {
                    log.info("Element displayed found after scroll");
                    return true;
                } else {
                    //checking if element is displayed after wait for 10 seconds

                    try {
                        log.info("Element displayed not found after scroll. Polling for 10 seconds");
                        FluentWait<WebDriver> wait;
                        wait = new WebDriverWait(driver, Duration.ofSeconds(timer))
                                .pollingEvery(Duration.ofMillis(timer))
                                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
                        wait.until(ExpectedConditions.visibilityOf(ele));

                        return true;

                    } catch (TimeoutException time) {
                        log.error("Element timeout: waited 10 seconds for element to be displayed");
                        log.error(time.getMessage());

                        return false;
                    }
                }
            }

        } catch (Exception e) {
            log.warn("Element incorrect or not available to check");
            log.warn(e.getMessage());

            // got an exception while checking
            // at this point due to page load or other reasons.
            //checking if element still displayed after wait for 10 seconds
            try {
                FluentWait<WebDriver> wait;
                wait = new WebDriverWait(driver, Duration.ofSeconds(timer))
                        .pollingEvery(Duration.ofMillis(timer))
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
                wait.until(ExpectedConditions.visibilityOf(ele));

                return true;
            } catch (TimeoutException time) {
                log.error("Element timeout: waited 10 seconds for element to be displayed");
                log.error(time.getMessage());

                return false;
            }

        }
    }


}
