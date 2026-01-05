package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InsuranceDetails {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public InsuranceDetails(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Verifies that the passenger details page is opened by checking a unique element.
     */
    public InsuranceDetails verifyPageOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-hrl-bo='passengerDetailsTitle'], h1, h2") // adjust selector if needed
        ));
        return this;
    }

    // Example: You could add methods to fill in passenger info later
    public InsuranceDetails enterFirstName(String firstName) {
        driver.findElement(By.cssSelector("[data-hrl-bo='firstNameInput']")).sendKeys(firstName);
        return this;
    }

    public InsuranceDetails enterLastName(String lastName) {
        driver.findElement(By.cssSelector("[data-hrl-bo='lastNameInput']")).sendKeys(lastName);
        return this;
    }

    public InsuranceDetails selectGender(String gender) {
        driver.findElement(By.cssSelector("[data-hrl-bo='genderDropdown']")).click();
        driver.findElement(By.xpath("//li[text()='" + gender + "']")).click();
        return this;
    }

    // Add other passenger details actions here...
}
