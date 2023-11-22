package edu.uiuc.cs427app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SignupTest.class,
        LoginTest.class,
        AddANewCityTest.class,
        RemoveCityTest.class,
        LogoffTest.class,
        WeatherFeatureTest.class,
        LocationFeatureTest.class,
        MockingLocationTest.class
})
public class OrderedTestSuite {
}
