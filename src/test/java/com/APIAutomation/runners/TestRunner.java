package com.APIAutomation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "com.APIAutomation.stepdefinitions"
    },
    plugin = {
        "pretty",
       "html:target/reports/report.html",
            "json:target/cucumber.json",
            "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
        tags = " @CreateBook or @GetAllBooks or @GetBookByID or @UpdateBookMember1 or @UpdateBookMember2 or @DeleteBooks"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}