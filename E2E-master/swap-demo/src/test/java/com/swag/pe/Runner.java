package com.swag.pe;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.swag.pe.definitions", "com.swag.pe.hooks"},
        tags ="@Suite"
)
public class Runner {
}
