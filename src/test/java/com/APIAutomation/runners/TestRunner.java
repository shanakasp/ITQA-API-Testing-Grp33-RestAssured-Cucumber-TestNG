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
        "json:target/cucumber.json"
    },
        tags = " @UpdateBookMember1"
//@CreateBook or @GetAllBooks or @GetBookByID or @UpdateBook or @DeleteBooks
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}