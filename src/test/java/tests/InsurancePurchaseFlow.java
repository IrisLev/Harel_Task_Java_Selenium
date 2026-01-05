package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DatesPage;
import pages.InsuranceDetails;
import pages.LandingPage;

public class InsurancePurchaseFlow extends BaseTest {


    @Test
    public void InsuranceProcessTest() {
        LandingPage landingPage = new LandingPage(driver);

        landingPage
                .open("https://digital.harel-group.co.il/travel-policy/")
                .clickFirstPurchaseButton()
                .selectContinent("australia");  // parameterized

        WebElement australiaElement = landingPage.getContinentElement("australia");

        Assert.assertEquals(australiaElement.getAttribute("data-hrl-bo"),
                "australia-selected", "Australia should have data-hrl-bo='australia-selected'");

        Assert.assertEquals(australiaElement.getAttribute("aria-checked"),
                "true", "Australia should have aria-checked='true'");
        DatesPage datesPage = landingPage.goToDatesPage();
        String expectedUrl = "https://digital.harel-group.co.il/travel-policy/wizard/date";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Should navigate to Dates page URL");
        datesPage
                .selectDepartureDatePlusDays(7)
                .selectReturnDatePlusDays(30)
                .verifyTotalDays(30); // confirm 30-day trip

        InsuranceDetails insuranceDetails = datesPage.goToPassengerDetails();
        insuranceDetails.verifyPageOpened();
    }
}
