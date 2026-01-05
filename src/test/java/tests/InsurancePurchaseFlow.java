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
    public void openLandingPageTest() {
        LandingPage landingPage = new LandingPage(driver);

        landingPage
                .open("https://digital.harel-group.co.il/travel-policy/")
                .clickFirstPurchaseButton()
                .selectAnyContinent();
        WebElement australiaElement = landingPage.getAustraliaElement();

        // Verify data-hrl-bo attribute
        String dataAttribute = australiaElement.getAttribute("data-hrl-bo");
        Assert.assertEquals(dataAttribute, "australia-selected",
                "Australia should have data-hrl-bo='australia-selected'");

        // Verify aria-checked attribute
        String ariaChecked = australiaElement.getAttribute("aria-checked");
        Assert.assertEquals(ariaChecked, "true",
                "Australia should have aria-checked='true'");
    }




    @Test
    public void shouldCompleteInsurancePurchaseFlow() {








//        LandingPage landingPage = new LandingPage(driver)
//                .open("https://YOUR_URL")
//                .clickFirstPurchaseButton()
//                .selectAnyContinent();
//
//        DatesPage datesPage = landingPage.goToDatesPage();
//
//        InsuranceDetails insuranceDetails = datesPage
//                .selectDepartureDatePlusDays(7)
//                .selectReturnDatePlusDays(30)
//                .verifyTotalDays()
//                .goToPassengerDetails();
//
//        insuranceDetails.verifyPageOpened();
    }
}
