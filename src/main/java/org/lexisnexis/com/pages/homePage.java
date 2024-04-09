package org.lexisnexis.com.pages;

import org.apache.log4j.Logger;
import org.lexisnexis.com.utilities.uiActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class homePage {

    public static final Logger log = Logger.getLogger(homePage.class.getName());
    private WebDriver driver;
    private uiActions obj;

    private String financialServices = ".//h4[contains(text(),'";

    private String select_industry_text = ".//div[contains(text(),'";

    @FindBy(xpath = ".//a[contains(text(),'Choose Your Industry')]")
    private WebElement choose_your_industry_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/financial-crime-compliance')]")
    private WebElement financial_crime_compliance_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/fraud-and-identity-management')]")
    private WebElement fraud_and_identity_management_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/customer-data-management')]")
    private WebElement customer_data_management_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/credit-risk-assessment')]")
    private WebElement credit_risk_assessment_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/collections-and-recovery')]")
    private WebElement collections_and_recovery_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/tracing-and-investigations')]")
    private WebElement tracing_and_investigations_btn;

    @FindBy(xpath = ".//a[contains(@href,'/financial-services/risk-orchestration')]")
    private WebElement risk_orchestration_btn;


    @FindBy(xpath = ".//button[contains(text(),'Accept All Cookies')]")
    private WebElement acceptAllCookies;

    public homePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.obj = new uiActions(driver);
    }

    public void linkCheck(String link) {

        String temp = financialServices + link + "')]/..//a[contains(text(),'clickable area')]";


        try {
            obj.clickButton(acceptAllCookies);
        } catch (Exception e) {

        }

        log.info("checking :" + financialServices);
        if (obj.waitForVisibility(driver.findElement(By.xpath(temp)))) {

            if (driver.findElement(By.xpath(temp)).isEnabled()) {
                obj.clickButton(driver.findElement(By.xpath(temp)));
                log.info("Element displayed & clickable");
            }

        } else {
            log.debug("Element not visible even and scroll & polling for 10 seconds");
        }

    }

    public void choose_your_industry() {


        try {
            obj.clickButton(acceptAllCookies);
        } catch (Exception e) {

        }

        log.info("checking :" + choose_your_industry_btn);
        obj.clickButton(choose_your_industry_btn);

    }

    public void select_industry(String industry) {

        String temp = select_industry_text + industry + "')]";

        log.info("checking :" + temp);
        obj.clickButton(driver.findElement(By.xpath(temp)));
        log.info("checked" );


    }

    public void select_sub_industry(String sub_industry) {

        WebElement temp_element;

        switch (sub_industry) {
            case "Financial Crime Compliance":
                temp_element = financial_crime_compliance_btn;
                break;

            case "Fraud and Identity Management":
                temp_element = fraud_and_identity_management_btn;
                break;

            case "Customer Data Management":
                temp_element = customer_data_management_btn;
                break;

            case "Credit Risk Assessment":
                temp_element = credit_risk_assessment_btn;
                break;

            case "Collections and Recovery":
                temp_element = collections_and_recovery_btn;
                break;

            case "Investigations and Due Diligence":
                temp_element = tracing_and_investigations_btn;
                break;

            case "Risk Orchestration":
                temp_element = risk_orchestration_btn;
                break;

            default:
                temp_element = null;
                break;

        }
        log.info("checking :" + temp_element);
        if (obj.waitForVisibility(temp_element)) {

            if (temp_element.isEnabled()) {
                log.info("Element displayed & clickable");
//                obj.clickButton(temp_element);
            }

        } else {
            log.debug("Element not visible even and scroll & polling for 10 seconds");
        }

    }

}
